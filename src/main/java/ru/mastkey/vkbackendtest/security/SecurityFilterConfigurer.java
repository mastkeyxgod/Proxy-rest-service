package ru.mastkey.vkbackendtest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import ru.mastkey.vkbackendtest.entity.User;
import ru.mastkey.vkbackendtest.repositories.AuditRepository;
import ru.mastkey.vkbackendtest.repositories.RoleRepository;
import ru.mastkey.vkbackendtest.repositories.UserRepository;


import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterConfigurer {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                 .requestMatchers(GET,"/api/v1/jph/users/**").hasAuthority("READ_USERS_PRIVILEGE")
                                 .requestMatchers(POST,"/api/v1/jph/users/**").hasAuthority("CREATE_USERS_PRIVILEGE")
                                 .requestMatchers(DELETE, "/api/v1/jph/users/**").hasAuthority("DELETE_USERS_PRIVILEGE")
                                 .requestMatchers(PUT, "/api/v1/jph/users/**").hasAuthority("UPDATE_USERS_PRIVILEGE")
                                 .requestMatchers(PATCH, "/api/v1/jph/users/**").hasAuthority("UPDATE_USERS_PRIVILEGE")

                                 .requestMatchers(GET, "/api/v1/jph/posts/**").hasAuthority("READ_POSTS_PRIVILEGE")
                                 .requestMatchers(POST,"/api/v1/jph/posts/**").hasAuthority("CREATE_POSTS_PRIVILEGE")
                                 .requestMatchers(DELETE, "/api/v1/jph/posts/**").hasAuthority("DELETE_POSTS_PRIVILEGE")
                                 .requestMatchers(PUT, "/api/v1/jph/posts/**").hasAuthority("UPDATE_POSTS_PRIVILEGE")
                                 .requestMatchers(PATCH, "/api/v1/jph/posts/**").hasAuthority("UPDATE_POSTS_PRIVILEGE")

                                 .requestMatchers(GET, "/api/v1/jph/albums/**").hasAuthority("READ_ALBUMS_PRIVILEGE")
                                 .requestMatchers(POST,"/api/v1/jph/albums/**").hasAuthority("CREATE_ALBUMS_PRIVILEGE")
                                 .requestMatchers(DELETE, "/api/v1/jph/albums/**").hasAuthority("DELETE_ALBUMS_PRIVILEGE")
                                 .requestMatchers(PUT, "/api/v1/jph/albums/**").hasAuthority("UPDATE_ALBUMS_PRIVILEGE")
                                 .requestMatchers(PATCH, "/api/v1/jph/albums/**").hasAuthority("UPDATE_ALBUMS_PRIVILEGE")

                                 .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                                 .requestMatchers("/api/v1/registration").permitAll()

                                 .requestMatchers("/ws").hasAnyAuthority("WEBSOCKETS_PRIVILEGE")

                                 .anyRequest().permitAll()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy =
                        "ROLE_ADMIN > ROLE_USERS_EDITOR\n" +
                        "ROLE_ADMIN > ROLE_POSTS_EDITOR\n" +
                        "ROLE_ADMIN > ROLE_ALBUMS_EDITOR\n" +
                        "ROLE_ADMIN > ROLE_WEBSOCKETS\n" +
                        "ROLE_USERS_EDITOR > ROLE_USERS_VIEWER\n" +
                        "ROLE_POSTS_EDITOR > ROLE_POSTS_VIEWER\n" +
                        "ROLE_ALBUMS_EDITOR > ROLE_ALBUMS_VIEWER\n" +
                        "ROLE_VIEWER > ROLE_USERS_VIEWER\n" +
                        "ROLE_VIEWER > ROLE_POSTS_VIEWER\n" +
                        "ROLE_VIEWER > ROLE_ALBUMS_VIEWER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> expressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
