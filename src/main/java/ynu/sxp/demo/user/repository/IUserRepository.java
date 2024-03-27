package ynu.sxp.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ynu.sxp.demo.user.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByCode(String code);
}

