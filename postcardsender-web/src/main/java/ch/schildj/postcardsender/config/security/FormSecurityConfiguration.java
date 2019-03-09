package ch.schildj.postcardsender.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Profile("security-form")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class FormSecurityConfiguration extends AbstractSecurityConfiguration {

    @Value("${security.admin.username}")
    private String adminUsername;
    @Value("${security.admin.password}")
    private String adminPassword;
    @Value("${security.guest.username}")
    private String guestUsername;
    @Value("${security.guest.password}")
    private String guestPassword;


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser(adminUsername).password(adminPassword).authorities("ROLE_ADMIN").and()
                .withUser(guestUsername).password(guestPassword).authorities("ROLE_GUEST");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureCommonAntMatchers(http);

        http
                .exceptionHandling()
                .authenticationEntryPoint(this.http401UnauthorizedEntryPoint())
                .and().authorizeRequests()
                .and().formLogin()
                .successHandler(this.ajaxAuthenticationSuccessHandler())
                .failureHandler(this.ajaxAuthenticationFailureHandler())
                .loginProcessingUrl("/auth/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().logoutUrl("/auth/logout").logoutSuccessHandler(this.ajaxLogoutSuccessHandler());
        ;


        // headers
        http.headers()
                .cacheControl().and()
                .contentTypeOptions().and()
                .httpStrictTransportSecurity().maxAgeInSeconds(31536000).and()
                .xssProtection().and()
                .frameOptions().sameOrigin();

        // csrf
        http
                .csrf().csrfTokenRepository(csrfTokenRepository()).requireCsrfProtectionMatcher(csrfRequestMatcherPut()).and()
                .csrf().csrfTokenRepository(csrfTokenRepository()).requireCsrfProtectionMatcher(csrfRequestMatcherPost()).and()
                .csrf().csrfTokenRepository(csrfTokenRepository()).requireCsrfProtectionMatcher(csrfRequestMatcherDelete()).and()
                .csrf().csrfTokenRepository(csrfTokenRepository()).requireCsrfProtectionMatcher(csrfRequestMatcherPatch());

    }
}
