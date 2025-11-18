package com.mymusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mymusic.dto.SongRequest;
import com.mymusic.entity.Song;
import com.mymusic.entity.User;
import com.mymusic.services.SongService;
import com.mymusic.services.UserService;

@RestController
public class AdminController {

	private final SongService songService;
	private final UserService userService;

	@Autowired
	public AdminController(SongService songService, UserService userService) {
		this.songService = songService;
		this.userService = userService;
	}

	@PostMapping("/addsong/{userId}")
	public ResponseEntity<?> addSong(@PathVariable("userId") Long userId, @RequestBody SongRequest songRequest) {

		User user = userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.status(401).body("Invalid User ID");
		}

		if (!userService.isAdmin(user)) {
			return ResponseEntity.status(403).body("You are not authorized. Only Admin can add songs.");
		}

		Song song = new Song();
		song.setTitle(songRequest.getTitle());
		song.setArtist(songRequest.getArtist());
		song.setGenre(songRequest.getGenre());

		return ResponseEntity.ok(songService.addSong(song));
	}

}
