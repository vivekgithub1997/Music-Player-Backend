package com.mymusic.dto;

import lombok.Data;

@Data
public class LoginResponse {
	private String msg;
	private long userId;
	private String username;
}