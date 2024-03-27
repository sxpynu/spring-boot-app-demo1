package ynu.sxp.demo.common.exception;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.security.auth.login.LoginException;

/** 全局异常处理 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /** 处理参数校验异常 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("调用参数错误：");

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append("\"").append(fieldError.getField()).append("\"")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }

        return new ResponseEntity<>(new ExceptionVO(ExceptionType.VALIDATION_ERROR, errorMessage.toString()), HttpStatus.BAD_REQUEST);
    }

    /** 处理登录异常 */
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handleLoginException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.Login_ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** 处理业务异常 */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handleBusinessException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.BUSINESS_ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** 处理数据持久化异常 */
    @ExceptionHandler(PersistenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handlePersistenceException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.PERSISTENCE_ERROR, "持久化操作错误!", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** 处理数据完整性异常 */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handleDataAccessException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.DATA_ACCESS_ERROR, "数据库操作错误!", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** 处理调用参数异常 */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handleAssertException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.ASSERT_ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** 处理拒绝访问异常 */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handleAccessDeniedException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.ACCESS_DENIED_ERROR, "权限不足，不能调用此接口", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** 处理资源不存在异常 */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handleNoResourceFoundException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.NO_RESOURCE_FOUND_ERROR, "你要访问的资源不存在，请检查URL是否正确！", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** 处理请求数据读取异常（通常是请求中有错误格式的 JSON 或非 JSON 数据） */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionVO> handleHttpMessageConversionException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.HTTP_MESSAGE_ERROR, "数据无法正确读取或解析！", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /** 处理认证失败异常 */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionVO> handleAuthenticationException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.AUTHENTICATION_ERROR, "认证失败！", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /** 处理服务内部异常 */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionVO> handleException(Exception e) {
        return new ResponseEntity<>(new ExceptionVO(ExceptionType.INTERNAL_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
