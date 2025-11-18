package com.mymusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mymusic.services.SongService;

@RestController
@RequestMapping("/api/user")
public class SongController {

	private final SongService songService;

	@Autowired
	public SongController(SongService songService) {
		this.songService = songService;
	}

	@GetMapping("/allSongs/{userId}")
	public ResponseEntity<?> listAll(@PathVariable("userId") Long userId) {
		try {
			return ResponseEntity.ok(songService.findAll(userId));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@GetMapping("/{userId}/{songId}")
	public ResponseEntity<?> getById(@PathVariable("userId") Long userId, @PathVariable("songId") Long songId) {
		try {
			return ResponseEntity.ok(songService.getById(userId, songId));
		} catch (Exception e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@GetMapping("/search/{userId}")
	public ResponseEntity<?> search(@PathVariable("userId") Long userId,
			@RequestParam(value = "q", required = false) String q) {
		try {
			return ResponseEntity.ok(songService.search(userId, q));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
