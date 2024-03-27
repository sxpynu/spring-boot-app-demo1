package ynu.sxp.demo.common;

import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.UUID;

public class BaseController {

    @Resource
    protected ModelMapper modelMapper;

    public BaseController() {
    }

    protected UUID getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.isTrue(authentication!=null && authentication.isAuthenticated(), "用户未登录!");
        return UUID.fromString(authentication.getName());
    }

}
