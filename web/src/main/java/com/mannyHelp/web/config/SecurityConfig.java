package com.mannyHelp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()


                        .requestMatchers("/", "/mainPage", "/browser-services", "/users-list", "/service/**", "/providers/**").permitAll()
                        .requestMatchers("/login", "/register").permitAll()


                        .requestMatchers("/book-service", "/programari/**", "/booking/**").authenticated()
                        .requestMatchers("/chat/**", "/api/chat/**").authenticated()
                        .requestMatchers("/services/new", "/services/edit/**", "/users/edit/**").authenticated()
                        .requestMatchers("/add-review", "/reviews/**").authenticated()


                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/mainPage", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/mainPage")
                        .permitAll()
                );

        return http.build();
    }
}