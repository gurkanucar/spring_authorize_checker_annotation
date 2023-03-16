package com.gucardev.spring_boot_authorize_checker.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.headers()
        .frameOptions()
        .disable()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .userDetailsService(userDetailsService())
        .httpBasic(withDefaults())
        .build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user =
        User.withDefaultPasswordEncoder().username("grkn").password("123").roles("ADMIN").build();
    return new InMemoryUserDetailsManager(List.of(user));
  }
}
