package org.example.minamilibrary.config;

import org.example.minamilibrary.model.Role;
import org.example.minamilibrary.model.User;
import org.example.minamilibrary.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {
	@Bean
	public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("admin") == null) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("123456"));
				admin.setRole(Role.ADMIN);
				
				userRepository.save(admin);
				
				System.out.println("admin added");
			} else {
				System.out.println("admin already exists");
			}
		};
	}
}
