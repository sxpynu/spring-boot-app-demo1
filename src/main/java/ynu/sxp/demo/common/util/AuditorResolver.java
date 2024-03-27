package ynu.sxp.demo.common.util;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * 用于给实体审计信息提供获取当前用户的 ID
 */
@Component
public class AuditorResolver implements AuditorAware<UUID> {

    @Override @NonNull
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }else{
            return Optional.of(UUID.fromString(authentication.getName()));
        }
    }
}