package ynu.sxp.demo.user.service;

import org.springframework.stereotype.Service;
import ynu.sxp.demo.admin.vo.NewRoleRO;
import ynu.sxp.demo.admin.vo.NewUserRO;
import ynu.sxp.demo.user.entity.RoleEntity;
import ynu.sxp.demo.user.entity.UserEntity;

import java.util.List;

/**
 * 用户初始化服务
 */
@Service
public class UserInitService {
    private final UserService userService;
    private final RoleService roleService;

    public UserInitService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public void initBuildinRole() {
        List<RoleEntity> allRoles = roleService.getAllRoles();
        if (allRoles.stream().noneMatch(role -> "admin".equals(role.getCode()))) {
            roleService.addRole(new NewRoleRO("admin", "管理员"));
        }
        if (allRoles.stream().noneMatch(role -> "user".equals(role.getCode()))) {
            roleService.addRole(new NewRoleRO("user", "普通用户"));
        }
    }

    public void initBuildinUser(){
        if (userService.getUserByCode("admin").isEmpty()){
            NewUserRO newUserRO = new NewUserRO("admin", "管理员", "admin");
            UserEntity userEntity = userService.addUser(newUserRO);
            userService.addRoleToUser(userEntity.getId(), roleService.getAdminRole().getId());
        }

    }
}
