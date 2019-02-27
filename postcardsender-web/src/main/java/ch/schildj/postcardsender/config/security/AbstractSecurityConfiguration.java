package ch.schildj.postcardsender.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public abstract class AbstractSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationEntryPoint http401UnauthorizedEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Bean
    public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK);
    }

    @Bean
    public CookieCsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieHttpOnly(false);
        return cookieCsrfTokenRepository;
    }

    /* Check only REST Requests for CSRF */
    @Bean
    public AntPathRequestMatcher csrfRequestMatcherPost() {
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher("/api/**", "POST");
        return antPathRequestMatcher;
    }

    /* Check only REST Requests for CSRF */
    @Bean
    public AntPathRequestMatcher csrfRequestMatcherPut() {
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher("/api/**", "PUT");
        return antPathRequestMatcher;
    }

    /* Check only REST Requests for CSRF */
    @Bean
    public AntPathRequestMatcher csrfRequestMatcherPatch() {
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher("/api/**", "PATCH");
        return antPathRequestMatcher;
    }

    /* Check only REST Requests for CSRF */
    @Bean
    public AntPathRequestMatcher csrfRequestMatcherDelete() {
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher("/api/**", "DELETE");
        return antPathRequestMatcher;
    }

    /* Automatically receives AuthorizationEvent messages */
    @Bean
    public org.springframework.security.authentication.event.LoggerListener authenticationloggerListener() {
        return new org.springframework.security.authentication.event.LoggerListener();
    }

    /* Automatically receives AuthenticationEvent messages */
    @Bean
    public org.springframework.security.access.event.LoggerListener authorizationLoggerListener() {
        return new org.springframework.security.access.event.LoggerListener();
    }

    /*configure security-permission on paths*/
    void configureCommonAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Static resources
                .antMatchers("/",
                        "/*.*",
                        "/assets/**",
                        "/scripts/**/*.{js,html,css}").permitAll()
                // Swagger
                .antMatchers(
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**").permitAll()
                // REST endpoints
                .antMatchers("/api/config/**").permitAll()
                .antMatchers("/api/logs/**").permitAll()
                .antMatchers("/api/translations/**").permitAll()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/protected/**").fullyAuthenticated();
    }

}
