package com.mymusic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mymusic.entity.Song;
import com.mymusic.entity.User;
import com.mymusic.repository.SongRepository;

@Service
public class SongService {

	private final SongRepository songRepository;

	private final UserService userService;

	@Autowired
	public SongService(SongRepository songRepository, UserService userService) {
		this.songRepository = songRepository;
		this.userService = userService;
	}

	public Song addSong(Song song) {
		return songRepository.save(song);
	}

	public List<Song> findAll(Long userId) {
		User user = userService.getUserById(userId);

		if (user == null) {
			throw new RuntimeException("Invalid User ID");
		}

		return songRepository.findAll();
	}

	public Song getById(Long userId, long songId) {
		User user = userService.getUserById(userId);

		if (user == null) {
			throw new RuntimeException("Invalid User ID");
		}

		return songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song Not Found"));
	}

	public List<Song> search(Long userId, String q) {
		User user = userService.getUserById(userId);

		if (user == null) {
			throw new RuntimeException("Invalid User ID");
		}

		return songRepository.searchAll(q == null ? "" : q);
	}

}