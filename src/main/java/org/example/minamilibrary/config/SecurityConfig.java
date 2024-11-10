package org.example.minamilibrary.config;

import org.example.minamilibrary.model.User;
import org.example.minamilibrary.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final CustomAuthenticationSuccessHandler successHandler;
	
	public SecurityConfig(UserDetailsService userDetailsService, CustomAuthenticationSuccessHandler successHandler) {
		this.userDetailsService = userDetailsService;
		this.successHandler = successHandler;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/user/**").hasRole("USER")
						.requestMatchers("/login", "/css/**", "/js/**").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginPage("/login")
//						.defaultSuccessUrl("/admin/dashboard", true)
						.successHandler(successHandler)
						.failureUrl("/login?error=true")
						.permitAll()
				)
				.logout(logout -> logout
						.logoutSuccessUrl("/login?logout=true")
						.permitAll()
				)
				.csrf(csrf -> csrf.disable()); // Disable CSRF protection if necessary
		
		return http.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
