package com.mymusic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mymusic.dto.LoginRequest;
import com.mymusic.dto.LoginResponse;
import com.mymusic.dto.RegisterRequest;
import com.mymusic.dto.RegisterResponse;
import com.mymusic.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginRegisterController {

	private UserService userService;

	public LoginRegisterController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

		RegisterResponse register = userService.register(req);

		return new ResponseEntity<>(register, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req) {

		LoginResponse login = userService.login(req);

		if ("Login Success".equals(login.getMsg())) {
			return ResponseEntity.ok(login);
		}

		if ("User Not Found".equals(login.getMsg())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(login.getMsg());
		}

		if ("userName password Incorrect".equals(login.getMsg())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(login.getMsg());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
	}

}