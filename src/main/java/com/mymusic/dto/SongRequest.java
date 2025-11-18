package com.mymusic.dto;

import lombok.Data;

@Data
public class SongRequest {
	private String title;
	private String artist;
	private String genre;
}