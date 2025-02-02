package com.JWT.Authentication.config;

import com.JWT.Authentication.service.JWTAutenticationEntrypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
 private    AuthenticationProvider authenticationProvider;

    @Autowired
   private JWTAutenticationEntrypoint jwtAutenticationEntrypoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Configuring security filter chain
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Allow unauthenticated access to /auth/** routes
                        .anyRequest().authenticated() // All other routes require authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set session creation policy to stateless
                )
                .authenticationProvider(authenticationProvider) // Inject the authentication provider
                .addFilterBefore( jwtAutenticationEntrypoint, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
                .build();
    }
}
