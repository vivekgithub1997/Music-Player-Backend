package com.mymusic.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mymusic.dto.LoginRequest;
import com.mymusic.dto.LoginResponse;
import com.mymusic.dto.RegisterRequest;
import com.mymusic.dto.RegisterResponse;
import com.mymusic.entity.User;
import com.mymusic.repository.UserRepository;
import com.mymusic.util.Role;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final Map<String, Long> tokens = new HashMap<>();

	@PostConstruct
	public void init() {
		Optional<User> admin = userRepository.findByUsername("admin");
		if (!admin.isPresent()) {
			User u = new User();
			u.setUsername("admin");
			u.setPassword(passwordEncoder.encode("adminpass"));
			u.setRole(Role.ADMIN);
			userRepository.save(u);
		}
	}

	public RegisterResponse register(RegisterRequest req) {

		Optional<User> existingUser = userRepository.findByUsername(req.getUsername());
		RegisterResponse response = new RegisterResponse();
		if (existingUser.isPresent()) {
			response.setMessage("Username already exists");

			return response;
		}

		User u = new User();
		u.setUsername(req.getUsername());
		u.setPassword(passwordEncoder.encode(req.getPassword()));
		u.setRole(req.isAdmin() ? Role.ADMIN : Role.USER);

		userRepository.save(u);
		response.setMessage("Register Successfully");

		return response;

	}

	public LoginResponse login(LoginRequest req) {

		LoginResponse resp = new LoginResponse();

		Optional<User> userOpt = userRepository.findByUsername(req.getUsername());

		if (!userOpt.isPresent()) {
			resp.setMsg("User Not Found");
			return resp;
		}

		User user = userOpt.get();

		if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			resp.setMsg("userName password Incorrect");
			return resp;
		}

		resp.setMsg("Login Success");
		resp.setUserId(user.getId());
		resp.setUsername(user.getUsername());

		return resp;
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public boolean isAdmin(User user) {
		return user != null && user.getRole() == Role.ADMIN;
	}

}
