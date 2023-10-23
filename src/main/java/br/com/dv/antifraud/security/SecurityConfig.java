package br.com.dv.antifraud.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfig(UserDetailsService userDetailsService,
                          RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        /*
         * Set Http Basic Auth
         * Disable CSRF protection to facilitate testing (not intended for production)
         * Disable frame options to allow H2 console (not intended for production)
         * Handle authentication errors
         * Define authorization rules:
         *  - /api/auth/user POST: permit all
         *  - /api/auth/user DELETE, /api/auth/role PUT and /api/auth/access PUT: only ADMINISTRATOR
         *  - /api/auth/list GET: only ADMINISTRATOR and SUPPORT
         *  - /api/antifraud/transaction POST: only MERCHANT
         *  - /actuator/shutdown: permit all (for Hyperskill tests, not intended for production)
         *  - h2: permit all (not intended for production)
         *  - any other request: require authentication
         * Set stateless Basic Auth: no session
         * Set Spring Security to use UserDetailsService implementation
         */
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .exceptionHandling(cfg ->
                        cfg.authenticationEntryPoint(restAuthenticationEntryPoint)
                )
                .headers(cfg ->
                        cfg.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/auth/user"))
                        .permitAll()

                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/auth/user/*"))
                        .hasRole("ADMINISTRATOR")

                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/auth/role/**"))
                        .hasRole("ADMINISTRATOR")

                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/auth/access/**"))
                        .hasRole("ADMINISTRATOR")

                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/auth/list"))
                        .hasAnyRole("ADMINISTRATOR", "SUPPORT")

                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/antifraud/transaction/**"))
                        .hasRole("MERCHANT")

                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/actuator/shutdown"))
                        .permitAll()

                        .requestMatchers(PathRequest.toH2Console())
                        .permitAll()

                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(cfg ->
                        cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

}
