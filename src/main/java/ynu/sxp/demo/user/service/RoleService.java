package ynu.sxp.demo.user.service;

import lombok.extern.apachecommons.CommonsLog;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ynu.sxp.demo.admin.vo.NewRoleRO;
import ynu.sxp.demo.admin.vo.UpdateRoleRO;
import ynu.sxp.demo.user.entity.RoleEntity;
import ynu.sxp.demo.user.repository.IRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@CommonsLog
public class RoleService {
    private final IRoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleService(IRoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public RoleEntity addRole(NewRoleRO ro) {
        var roleEntity = modelMapper.map(ro, RoleEntity.class);
        return roleRepository.save(roleEntity);
    }

    @Transactional
    public RoleEntity updateRole(UpdateRoleRO ro) {
        var roleEntity = getRoleById(ro.id).orElseThrow(() -> new RuntimeException("角色不存在"));
        modelMapper.map(ro, roleEntity);
        return roleRepository.save(roleEntity);
    }

    public RoleEntity getAdminRole() {
        return roleRepository.findByCode("admin").orElseThrow(() -> new RuntimeException("管理员角色不存在！"));
    }
    public RoleEntity getUserRole() {
        return roleRepository.findByCode("user").orElseThrow(() -> new RuntimeException("普通用户角色不存在！"));
    }

    public Optional<RoleEntity> getRoleById(UUID roleId) {
        return roleRepository.findById(roleId);
    }

    @Transactional
    public void deleteRoleById(UUID roleId) {
        var role = this.getRoleById(roleId).orElseThrow(() -> new RuntimeException("角色不存在!"));

        Assert.isTrue(!role.getCode().equals("admin"), "不能删除管理员角色！");
        Assert.isTrue(!role.getCode().equals("user"), "不能删除普通用户角色！");

        roleRepository.deleteById(roleId);
    }
}
