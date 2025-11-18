package com.mymusic.dto;

import java.util.List;

import com.mymusic.entity.Song;

import lombok.Data;

@Data
public class PlayListDTO {
	private Long playListId;
	private String name;
	private List<Song> songs;

	public PlayListDTO(Long playListId, String name, List<Song> songs) {
		this.playListId = playListId;
		this.name = name;
		this.songs = songs;
	}
}
