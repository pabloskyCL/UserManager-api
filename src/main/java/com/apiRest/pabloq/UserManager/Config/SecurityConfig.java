package com.apiRest.pabloq.UserManager.Config;

import com.apiRest.pabloq.UserManager.Jwt.JwtAuthenticationEntryPoint;
import com.apiRest.pabloq.UserManager.Jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(AuthenticationProvider authProvider, JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.authProvider = authProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/user/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/user/block/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .exceptionHandling( exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authProvider)
                .build();
    }

}
