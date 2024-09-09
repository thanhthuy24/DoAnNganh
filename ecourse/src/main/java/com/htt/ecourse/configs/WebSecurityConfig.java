package com.htt.ecourse.configs;

import com.htt.ecourse.filters.JwtTokenFilter;
import com.htt.ecourse.pojo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.HttpMethod.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    "api/users/login",
                                    "api/users/register")
                            .permitAll()

                            .requestMatchers(GET, "api/assignments/**").permitAll()
                            .requestMatchers(POST, "api/assignments/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/assignments/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/assignments/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/categories/**").permitAll()
                            .requestMatchers(POST, "api/categories/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/categories/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/categories/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/courses/**").permitAll()
                            .requestMatchers(POST, "api/courses/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/courses/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/courses/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/lessons/**").hasAnyRole(Role.USER, Role.ADMIN)
                            .requestMatchers(POST, "api/lessons/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(POST, "api/lessons/uploads/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/lessons/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/lessons/**/active").hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/lessons/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/teachers/**").permitAll()
                            .requestMatchers(POST, "api/teachers/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/teachers/**").hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(DELETE, "api/teachers/**").hasAnyRole(Role.ADMIN)

//                            .requestMatchers(GET, "api/users/**").permitAll()
                            .requestMatchers(POST, "api/users/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/users/update-user/**").hasAnyRole(Role.ADMIN, Role.USER)
                            .requestMatchers(DELETE, "api/users/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(POST, "api/tags").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/receipts/**").hasAnyRole(Role.USER)
                            .anyRequest().authenticated();
                });
        return http.build();
    }
}
