package ynu.sxp.demo.auth.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ynu.sxp.demo.auth.entity.LoginAttemptEntity;
import ynu.sxp.demo.auth.repository.ILoginAttemptRepository;
import ynu.sxp.demo.auth.vo.LoginRO;
import ynu.sxp.demo.captcha.service.CaptchaService;
import ynu.sxp.demo.common.exception.LoginCaptchaException;
import ynu.sxp.demo.user.entity.UserEntity;
import ynu.sxp.demo.user.service.UserService;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;

// 登录上下文
@Data
@RequiredArgsConstructor
class LoginContext {
    private final LoginRO loginRO;
    private UserEntity user;
    private LoginAttemptEntity attemptEntity;
    private boolean isValidated = false;
}

interface ILoginFilter {
    void doFilter(LoginContext context) throws LoginException;
}

class LoginValidatePipe {
    private final ILoginFilter[] filters;

    public LoginValidatePipe(ILoginFilter... filters) {
        this.filters = filters;
    }

    public void execute(LoginContext context) throws LoginException {
        for (var filter : filters) {
            filter.doFilter(context);
        }
    }
}

// 用户存在性检查
class UserExistFilter implements ILoginFilter {
    private final UserService userService;

    public UserExistFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    @SneakyThrows
    public void doFilter(LoginContext context) {
        UserEntity userEntity = userService.getUserByCode(context.getLoginRO().username).orElseThrow(() -> new LoginException("用户不存在！"));
        context.setUser(userEntity);
    }
}

// loginAttemptEntity检查
class AttemptEntityFilter implements ILoginFilter {
    private final ILoginAttemptRepository loginLockerRepository;

    public AttemptEntityFilter(ILoginAttemptRepository loginAttemptRepository) {
        this.loginLockerRepository = loginAttemptRepository;
    }

    @Override
    public void doFilter(LoginContext context) {
        var attemptEntity = loginLockerRepository.findByUserId(context.getUser().getId());
        attemptEntity.ifPresent(context::setAttemptEntity);
    }
}

// 用户锁定检查
class UserLockFilter implements ILoginFilter {
    private final int maxAttemptCount;
    private final int lockDuration;

    public UserLockFilter(int maxAttemptCount, int lockDuration) {
        this.maxAttemptCount = maxAttemptCount;
        this.lockDuration = lockDuration;
    }

    @Override
    @SneakyThrows
    public void doFilter(LoginContext context) {
        LoginAttemptEntity loginAttemptEntity = context.getAttemptEntity();
        if (loginAttemptEntity == null) return;
        if (loginAttemptEntity.getAttemptCount() >= maxAttemptCount) {
            if (loginAttemptEntity.getLastAttemptTime().plusMinutes(lockDuration).isAfter(LocalDateTime.now())) {
                throw new LoginException("登录失败次数过多,请在 " + lockDuration + " 分钟后再试！");
            } else {
                System.out.println("解锁");
            }
        }
    }
}

// 验证码检查
class CaptchaValidateFilter implements ILoginFilter {
    private final CaptchaService captchaService;

    CaptchaValidateFilter(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    @SneakyThrows
    public void doFilter(LoginContext context) {
        var loginAttemptEntity = context.getAttemptEntity();
        if (loginAttemptEntity == null) return;
        if (loginAttemptEntity.getAttemptCount() < 3) return;

        if (context.getLoginRO().captcha == null) {
            throw new LoginCaptchaException("需要输入验证码！");
        }
        if (!captchaService.validateCaptcha(context.getLoginRO().captcha)) {
            throw new LoginCaptchaException("验证码错误！");
        }
    }
}

// 密码验证
class PasswordValidateFilter implements ILoginFilter {
    private final UserService userService;

    public PasswordValidateFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    @SneakyThrows
    public void doFilter(LoginContext context) {
        if (!userService.validateUserPassword(context.getUser(), context.getLoginRO().password)) {
            throw new LoginException("密码错误！");
        }
    }
}


@Service
public class LoginService {

    @Value("${app.login.max-attempt-count:5}")  // 最大尝试次数，从配置文件中读取
    private int maxAttemptCount;
    @Value("${app.login.lock-duration:120}")    // 锁定时长(单位：分钟)，从配置文件中读取
    private int lockDuration;
    private final UserService userService;
    private final ILoginAttemptRepository loginAttemptRepository;
    private final CaptchaService captchaService;

    private LoginValidatePipe loginValidatePipe;

    public LoginService(UserService userService, ILoginAttemptRepository loginAttemptRepository, CaptchaService captchaService) {
        this.userService = userService;
        this.loginAttemptRepository = loginAttemptRepository;
        this.captchaService = captchaService;
    }

    private LoginValidatePipe createLoginValidatePipe() {
        return new LoginValidatePipe(
                new UserExistFilter(userService),
                new AttemptEntityFilter(loginAttemptRepository),
                new UserLockFilter(maxAttemptCount, lockDuration),
                new CaptchaValidateFilter(captchaService),
                new PasswordValidateFilter(userService)
        );
    }

    @Transactional(noRollbackFor = LoginException.class)
    public UserEntity login(LoginRO ro) throws LoginException {
        if (loginValidatePipe == null) {
            loginValidatePipe = createLoginValidatePipe();
        }
        LoginContext loginContext = new LoginContext(ro);
        try {
            loginValidatePipe.execute(loginContext);
            if (loginContext.getAttemptEntity() != null) {
                loginAttemptRepository.deleteByUserId(loginContext.getUser().getId());
            }
            return loginContext.getUser();
        } catch (LoginException e) {
            LoginAttemptEntity attemptEntity = loginContext.getAttemptEntity();
            if (attemptEntity == null) {
                if (loginContext.getUser() == null) throw e;

                attemptEntity = new LoginAttemptEntity();
                attemptEntity.setUserId(loginContext.getUser().getId());
            }
            attemptEntity.increaseAttemptCount();
            attemptEntity.setLastAttemptTime(LocalDateTime.now());
            loginAttemptRepository.save(attemptEntity);
            throw e;
        }
    }

}
