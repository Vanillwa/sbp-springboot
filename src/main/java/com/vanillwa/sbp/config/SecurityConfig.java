package com.vanillwa.sbp.config;

import com.vanillwa.sbp.jwt.CustomLogoutFilter;
import com.vanillwa.sbp.jwt.JWTFilter;
import com.vanillwa.sbp.jwt.JWTUtil;
import com.vanillwa.sbp.repository.RefreshRepository;
import com.vanillwa.sbp.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vanillwa.sbp.jwt.LoginFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
	private final RefreshRepository refreshRepository;
	private final CustomUserDetailsService customUserDetailsService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {

		http.cors((cors)->cors.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

						CorsConfiguration configuration = new CorsConfiguration();

						configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
						configuration.setAllowedMethods(Collections.singletonList("*"));
						configuration.setAllowCredentials(true);
						configuration.setAllowedHeaders(Collections.singletonList("*"));
						configuration.setMaxAge(3600L);
						configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
						configuration.setExposedHeaders(Collections.singletonList("access"));

						return configuration;
					}
				}))
		.csrf((csrf)->csrf.disable())
		.formLogin((form)->form.disable())
		.httpBasic((basic)->basic.disable())
		.authorizeHttpRequests((request)->request
				.requestMatchers("/","/login","/join").permitAll()
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/reissue").permitAll()
				.anyRequest().authenticated())
		.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
		.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class)
		.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


		return http.build();
	}


}
