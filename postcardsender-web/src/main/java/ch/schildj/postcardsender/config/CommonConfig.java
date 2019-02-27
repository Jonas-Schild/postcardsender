package ch.schildj.postcardsender.config;

import ch.schildj.postcardsender.web.core.translation.SerializableResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Properties;


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CommonConfig {

    @Bean
    public SpringSecurityAuditorAware auditorAware() {
        return new SpringSecurityAuditorAware();
    }


    @Value("${ch.schildj.environment}")
    private String environment;

    @Value("${ch.schildj.container}")
    private String container;

    @Value("${ch.schildj.instance}")
    private String instance;

    @Value("${ch.schildj.host}")
    private String host;


    @Bean
    public MessageSource messageSource() {
        SerializableResourceBundleMessageSource messageSource = new SerializableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        Properties fileEncodings = new Properties();
        fileEncodings.setProperty("UTF-8", "");
        messageSource.setFileEncodings(fileEncodings);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }


}
