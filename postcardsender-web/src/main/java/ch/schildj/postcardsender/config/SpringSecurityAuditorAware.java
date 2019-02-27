package ch.schildj.postcardsender.config;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;

/*
* The allow to track of who created or changed an entity
 */

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private static final String SYSTEM_USERNAME = "SYSTEM";

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> usernameO = getName();
        if (!usernameO.isPresent()){
            return Optional.of(SYSTEM_USERNAME);
        }
        return usernameO;
    }

    private Optional<String> getName() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .flatMap(securityContext -> Optional.ofNullable(securityContext.getAuthentication()))
            .map(authentication -> authentication.getName());
    }

}

