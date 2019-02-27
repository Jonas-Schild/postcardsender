package ch.schildj.postcardsender.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configurations
 *
 */

@Configuration
@EntityScan(basePackages = {"ch.schildj.postcardsender.domain.model"})
@EnableJpaRepositories(basePackages = {"ch.schildj.postcardsender.domain.repository"})
public class DomainConfiguration {
}
