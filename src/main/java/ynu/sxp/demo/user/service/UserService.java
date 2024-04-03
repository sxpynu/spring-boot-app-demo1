package ynu.sxp.demo.user.service;

import lombok.extern.apachecommons.CommonsLog;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ynu.sxp.demo.admin.vo.NewUserRO;
import ynu.sxp.demo.common.exception.BusinessException;
import ynu.sxp.demo.user.entity.UserEntity;
import ynu.sxp.demo.user.repository.IUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@CommonsLog
public class UserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    UserService(IUserRepository userRepository, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.roleService = roleService;
    }

    public boolean validateUserPassword(UserEntity user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Transactional
    public UserEntity addUser(NewUserRO ro){
        Assert.isTrue(userRepository.findByCode(ro.code).isEmpty(), "用户代码重复！");

        UserEntity user = modelMapper.map(ro, UserEntity.class);
        user.setPassword(passwordEncoder.encode(ro.password));
        user.getRoles().add(roleService.getUserRole()); // 默认角色为普通用户

        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(UserEntity user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public Optional<UserEntity> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void deleteUserById(UUID userId) {
        var user = this.getUserById(userId).orElseThrow(() -> new RuntimeException("用户不存在"));
        Assert.isTrue(!user.getCode().equals("admin"), "不能删除管理员！");
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserEntity updateUser(UserEntity user) {
        userRepository.findByCode(user.getCode()).ifPresentOrElse(
            u -> Assert.isTrue(u.getId().equals(user.getId()), "用户代码重复！"),
            () -> { }
        );
        return userRepository.save(user);
    }

    public Optional<UserEntity> getUserByCode(String userCode) {
        return userRepository.findByCode(userCode);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public void addRoleToUser(UUID userId, UUID roleId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new BusinessException("用户不存在"));
        var role = roleService.getRoleById(roleId).orElseThrow(() -> new BusinessException("角色不存在"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public void removeRoleFromUser(UUID userId, UUID roleId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new BusinessException("用户不存在"));
        var role = roleService.getRoleById(roleId).orElseThrow(() -> new BusinessException("角色不存在"));
        Assert.isTrue(!(user.getCode().equals("admin") && role.getCode().equals("admin")), "不能删除admin用户的管理员角色！");
        user.getRoles().remove(role);
        userRepository.save(user);
    }

}
