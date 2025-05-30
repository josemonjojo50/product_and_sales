package com.retail.productsales.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		var user = User.withUsername("user").password("{noop}password").roles("USER").build();
		var admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests(auth -> auth

				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/products/**","/api/**","/favicon.ico",                 
		                "/css/**", "/js/**", "/images/**").permitAll()

				.requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")

				.requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")

				.anyRequest().authenticated()).httpBasic()

				.and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				.accessDeniedHandler(new AccessDeniedHandlerImpl()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}
}
