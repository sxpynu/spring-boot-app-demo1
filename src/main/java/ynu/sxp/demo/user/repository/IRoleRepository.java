package ynu.sxp.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ynu.sxp.demo.user.entity.RoleEntity;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByCode(String code);
}
