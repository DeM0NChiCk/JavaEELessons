package ru.itis.lessonservlet.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.itis.lessonservlet.exception.CustomAccessDeniedHandler;
import ru.itis.lessonservlet.exception.CustomAuthenticationFailureHandler;

@Configuration
@Order(1)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/web/**", "/signIn", "/signUp", "/admin/**", "/login", "/logout", "/swagger-ui/**", "/oauth/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/signUp",
                                "/signIn",
                                "/admin/signIn",
                                "/oauth/**",

                                "/css/**",
                                "/image/**",
                                "/js/**",

                                "/error-auth",
                                
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"


                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("admin")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/signIn")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/web/main", true)
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }
}
