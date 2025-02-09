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

                            .requestMatchers(GET, "api/assignments?**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(POST, "api/assignments/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/assignments/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/assignments/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/assignments/lesson/**").permitAll()

                            .requestMatchers(GET, "api/assignment-done/**").hasAnyRole(Role.USER, Role.TEACHER)
                            .requestMatchers(POST, "api/assignment-done/**").hasAnyRole(Role.USER)

                            .requestMatchers(GET, "api/answer-choices").hasAnyRole(Role.USER, Role.TEACHER)
                            .requestMatchers(POST, "api/answer-choices/**").hasAnyRole(Role.USER)

                            .requestMatchers(GET, "api/categories/**").permitAll()
                            .requestMatchers(POST, "api/categories/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/categories/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/categories/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/courses/**").permitAll()
                            .requestMatchers(POST, "api/courses/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/courses/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/courses/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/certificate/**").hasAnyRole(Role.USER)

//                            .requestMatchers(GET, "api/rating/**").hasAnyRole(Role.ADMIN, Role.USER)
                            .requestMatchers(GET, "api/rating/**").permitAll()
                            .requestMatchers(POST, "api/rating/**").hasAnyRole(Role.USER)

                            .requestMatchers(POST, "api/choices/**").hasAnyRole(Role.TEACHER, Role.ADMIN)
                            .requestMatchers(PUT, "api/choices/**").hasAnyRole(Role.TEACHER, Role.ADMIN)

                            .requestMatchers(GET, "api/comments/**").hasAnyRole(Role.USER, Role.TEACHER, Role.ADMIN)
                            .requestMatchers(POST, "api/comments").hasAnyRole(Role.USER, Role.TEACHER)

                            .requestMatchers(GET, "api/reply/**").hasAnyRole(Role.USER, Role.ADMIN)
                            .requestMatchers(POST, "api/reply/**").hasAnyRole(Role.USER, Role.TEACHER)

                            .requestMatchers(GET, "api/enrollments/**").hasAnyRole(Role.USER, Role.TEACHER)
                            .requestMatchers(POST, "api/enrollments").hasAnyRole(Role.USER)

                            .requestMatchers(POST, "api/email").hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(GET, "api/essays/**").hasAnyRole(Role.TEACHER, Role.ADMIN, Role.USER)
                            .requestMatchers(POST, "api/essays/**").hasAnyRole(Role.USER)
                            .requestMatchers(PUT, "api/essays/**").hasAnyRole(Role.USER)

                            .requestMatchers(GET, "api/lessons").hasAnyRole(Role.USER, Role.ADMIN, Role.TEACHER)
                            .requestMatchers(GET, "api/lessons/auth/**").hasAnyRole(Role.USER, Role.ADMIN, Role.TEACHER)
                            .requestMatchers(GET, "api/lessons/course/**").hasAnyRole(Role.USER, Role.ADMIN, Role.TEACHER)
                            .requestMatchers(GET, "api/lessons/get-first-lesson/course/**").permitAll()

                            .requestMatchers(POST, "api/lessons/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(POST, "api/lessons/uploads/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/lessons/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/lessons/**/active").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/lessons/update-video/").hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE, "api/lessons/**").hasAnyRole(Role.ADMIN)


                            .requestMatchers(GET, "api/like/**").hasAnyRole(Role.USER, Role.TEACHER)
                            .requestMatchers(POST, "api/like").hasAnyRole(Role.USER, Role.TEACHER)

                            .requestMatchers(POST, "api/payment/update-payment").hasAnyRole(Role.USER)
                            .requestMatchers(POST, "api/paypal/**").hasAnyRole(Role.USER)
                            .requestMatchers(GET, "api/paypal/**").hasAnyRole(Role.USER)

                            .requestMatchers(POST, "api/progress/**").hasAnyRole(Role.USER, Role.TEACHER, Role.ADMIN)

                            .requestMatchers(GET, "api/questions/count/assignment/**").permitAll()
                            .requestMatchers(GET, "api/questions/assignment/").hasAnyRole(Role.TEACHER, Role.USER)
                            .requestMatchers(POST, "api/questions/**").hasAnyRole(Role.TEACHER, Role.ADMIN)
                            .requestMatchers(PUT, "api/questions/**").hasAnyRole(Role.TEACHER, Role.ADMIN)

                            .requestMatchers(POST, "api/receipts/create-payment").hasAnyRole(Role.USER)

                            .requestMatchers(POST, "api/register/").hasAnyRole(Role.USER)
                            .requestMatchers(GET, "api/register/**").hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            .requestMatchers(PATCH, "api/register/update/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(POST, "api/score/**").hasAnyRole(Role.TEACHER, Role.ADMIN, Role.USER)
                            .requestMatchers(GET, "api/score/**").hasAnyRole(Role.USER, Role.TEACHER, Role.ADMIN)

                            .requestMatchers(POST, "api/token").permitAll()

                            .requestMatchers(GET, "api/teachers/**").permitAll()
                            .requestMatchers(POST, "api/teachers/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/teachers/**").hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(DELETE, "api/teachers/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/users/**").hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            .requestMatchers(POST, "api/users/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT, "api/users/update-user/**").hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            .requestMatchers(DELETE, "api/users/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET, "api/tags").hasAnyRole(Role.ADMIN)
                            .requestMatchers(POST, "api/tags").hasAnyRole(Role.ADMIN)

                            .requestMatchers(PUT, "api/receipts/**").hasAnyRole(Role.USER)

                            .requestMatchers(PUT, "api/videos/update/").hasAnyRole(Role.ADMIN)
                            .requestMatchers(GET, "api/videos/count/lesson/").permitAll()


                            .requestMatchers(GET, "api//**").hasAnyRole(Role.USER, Role.ADMIN, Role.TEACHER)
                            .requestMatchers(POST, "api/video-completed/").hasAnyRole(Role.USER)

                            .anyRequest().authenticated();
                });
        return http.build();
    }
}
