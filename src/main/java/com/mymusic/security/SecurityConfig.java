package com.mymusic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.csrf(cs -> cs.disable())

				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/register", "/api/auth/login", "/swagger-ui/**", "/v3/api-docs/**",
								"/swagger-ui.html")
						.permitAll().requestMatchers("/addsong/**").permitAll().requestMatchers("/api/user/**")
						.permitAll().requestMatchers("/api/playlists/**").permitAll().requestMatchers("/api/playback/**")
						.permitAll().anyRequest().authenticated())

				.userDetailsService(userDetailsService).build();

	}

}
