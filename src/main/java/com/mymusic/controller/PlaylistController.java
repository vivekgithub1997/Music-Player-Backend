package com.mymusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mymusic.services.PlayListService;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

	@Autowired
	private PlayListService playlistService;

	@PostMapping("/createplaylist")
	public ResponseEntity<?> createPlaylist(@RequestParam("userId") Long userId,
			@RequestParam("playListName") String name) {

		try {
			String playlist = playlistService.createPlaylist(userId, name);
			return ResponseEntity.ok(playlist);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/myplaylist/{userId}")
	public ResponseEntity<?> myPlaylists(@RequestParam("userId") Long userId) {
		try {
			return ResponseEntity.ok(playlistService.getByOwner(userId));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/{playlistId}/add/{songId}/{userId}")
	public ResponseEntity<?> addSong(@PathVariable("playlistId") long playlistId, @PathVariable("songId") long songId,
			@RequestParam("userId") Long userId) {

		return playlistService.addSongToPlaylist(playlistId, songId, userId)
				.map(p -> ResponseEntity.ok().body((Object) p))
				.orElse(ResponseEntity.badRequest().body("Cannot add song. Not owner or invalid IDs."));
	}

	@DeleteMapping("/{playlistId}/remove/{songId}/{userId}")
	public ResponseEntity<?> removeSong(@PathVariable("playlistId") long playlistId,
			@PathVariable("songId") long songId, @RequestParam("userId") Long userId) {

		return playlistService.removeSongFromPlaylist(playlistId, songId, userId)
				.map(p -> ResponseEntity.ok().body((Object) p))
				.orElse(ResponseEntity.badRequest().body("Cannot remove song. Not owner or invalid IDs."));
	}

}
