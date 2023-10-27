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
        return http
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(cfg ->
                        cfg.authenticationEntryPoint(restAuthenticationEntryPoint)
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/auth/user"))
                        .permitAll()

                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/antifraud/transaction/**"))
                        .hasRole("MERCHANT")

                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/auth/list"))
                        .hasAnyRole("ADMINISTRATOR", "SUPPORT")

                        .requestMatchers(
                                mvc.pattern(HttpMethod.DELETE, "/api/auth/user/*"),
                                mvc.pattern(HttpMethod.PUT, "/api/auth/role/**"),
                                mvc.pattern(HttpMethod.PUT, "/api/auth/access/**")
                        ).hasRole("ADMINISTRATOR")

                        .requestMatchers(
                                mvc.pattern(HttpMethod.POST, "/api/antifraud/stolencard"),
                                mvc.pattern(HttpMethod.DELETE, "/api/antifraud/stolencard/*"),
                                mvc.pattern(HttpMethod.GET, "/api/antifraud/stolencard"),
                                mvc.pattern(HttpMethod.POST, "/api/antifraud/suspicious-ip"),
                                mvc.pattern(HttpMethod.DELETE, "/api/antifraud/suspicious-ip/*"),
                                mvc.pattern(HttpMethod.GET, "/api/antifraud/suspicious-ip")
                        ).hasRole("SUPPORT")

                        // For Hyperskill automated tests (not intended for production)
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/actuator/shutdown")).permitAll()

                        // For H2 console (not intended for production)
                        .requestMatchers(PathRequest.toH2Console()).permitAll()

                        .anyRequest().permitAll()
                )
                .sessionManagement(cfg ->
                        cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .userDetailsService(userDetailsService)
                // Disable CSRF protection to facilitate testing (not intended for production)
                .csrf(CsrfConfigurer::disable)
                // Disable frame options to allow H2 console (not intended for production)
                .headers(cfg ->
                        cfg.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
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
