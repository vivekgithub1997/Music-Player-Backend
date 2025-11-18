package com.mymusic.dto;

import lombok.Data;

@Data
public class RegisterRequest {

	private String username;
	private String password;
	private boolean admin;

}
