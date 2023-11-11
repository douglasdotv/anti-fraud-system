package br.com.dv.antifraud.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(cfg -> cfg.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/actuator/shutdown")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/auth/user")).permitAll()
                        .requestMatchers(mvc.pattern("/api/antifraud/suspicious-ip/**")).hasRole("SUPPORT")
                        .requestMatchers(mvc.pattern("/api/antifraud/stolencard/**")).hasRole("SUPPORT")
                        .anyRequest().authenticated()
                )
                .sessionManagement(cfg -> cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable)
                .headers(cfg -> cfg.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
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
