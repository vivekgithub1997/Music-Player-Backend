package com.mymusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mymusic.entity.User;
import com.mymusic.services.PlaybackService;
import com.mymusic.services.UserService;

@RestController
@RequestMapping("/api/playback")
public class PlaybackController {

	private PlaybackService playbackService;

	private UserService userService;

	public PlaybackController(PlaybackService playbackService, UserService userService) {
		this.playbackService = playbackService;
		this.userService = userService;
	}

	@PostMapping("/play/{userId}/{songId}")
	public ResponseEntity<?> play(@RequestParam("userId") Long userId, @RequestParam("songId") long songId) {

		User user = userService.getUserById(userId);
		if (user == null)
			return ResponseEntity.badRequest().body("Invalid User");

		return playbackService.play(user, songId).<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().body("Invalid Song ID"));
	}

	@PostMapping("/pause")
	public ResponseEntity<?> pause(@RequestHeader("userId") Long userId) {

		User user = userService.getUserById(userId);
		if (user == null)
			return ResponseEntity.badRequest().body("Invalid User");

		return playbackService.pause(user).<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().body("Nothing is playing"));
	}

	@PostMapping("/resume")
	public ResponseEntity<?> resume(@RequestHeader("userId") Long userId) {

		User user = userService.getUserById(userId);
		if (user == null)
			return ResponseEntity.badRequest().body("Invalid User");

		return playbackService.resume(user).<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().body("Nothing to resume"));
	}

	@PostMapping("/stop")
	public ResponseEntity<?> stop(@RequestHeader("userId") Long userId) {

		User user = userService.getUserById(userId);
		if (user == null)
			return ResponseEntity.badRequest().body("Invalid User");

		return playbackService.stop(user).<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().body("Nothing to stop"));
	}

	@GetMapping("/current")
	public ResponseEntity<?> current(@RequestHeader("userId") Long userId) {

		User user = userService.getUserById(userId);
		if (user == null)
			return ResponseEntity.badRequest().body("Invalid User");

		return playbackService.current(user).<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.ok("No active playback"));
	}
}
