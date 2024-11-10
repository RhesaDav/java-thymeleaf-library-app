package org.example.minamilibrary.service;

import jakarta.transaction.Transactional;
import org.example.minamilibrary.model.User;
import org.example.minamilibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User updateUser(Long id, User updatedUser) {
		User user = findById(id);
		if (user != null) {
			user.setUsername(updatedUser.getUsername());
			user.setRole(updatedUser.getRole());
			if (updatedUser.getPassword() != null) {
				user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
			}
			return userRepository.save(user);
		}
		return null;
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
