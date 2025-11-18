package com.mymusic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mymusic.dto.PlayListDTO;
import com.mymusic.entity.PlayList;
import com.mymusic.entity.Song;
import com.mymusic.entity.User;
import com.mymusic.repository.PlayListRepository;
import com.mymusic.repository.SongRepository;

@Service
public class PlayListService {

	@Autowired
	private PlayListRepository playListRepository;

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private UserService userService;

	public String createPlaylist(Long userId, String name) {
		User owner = userService.getUserById(userId);
		if (owner == null)
			throw new RuntimeException("Invalid User ID");

		PlayList p = new PlayList();
		p.setName(name);
		p.setOwner(owner);

		playListRepository.save(p);

		return "Play List Added :" + name + " ✅";
	}

	public List<PlayListDTO> getByOwner(Long userId) {

		User owner = userService.getUserById(userId);
		if (owner == null)
			throw new RuntimeException("Invalid User ID");

		List<PlayList> playlists = playListRepository.findByOwner(owner);

		return playlists.stream().map(p -> new PlayListDTO(p.getPlayListId(), p.getName(), p.getSongs())).toList();
	}

	public Optional<PlayList> addSongToPlaylist(long playlistId, long songId, Long userId) {

		User user = userService.getUserById(userId);
		if (user == null)
			return Optional.empty();

		Optional<PlayList> pOpt = playListRepository.findById(playlistId);
		Optional<Song> sOpt = songRepository.findById(songId);

		if (!pOpt.isPresent() || !sOpt.isPresent())
			return Optional.empty();

		PlayList playlist = pOpt.get();
		Song song = sOpt.get();

		boolean exists = playlist.getSongs().stream().anyMatch(s -> s.getId() == songId);

		if (exists) {

			return Optional.of(playlist);
		}

		playlist.getSongs().add(song);

		return Optional.of(playListRepository.save(playlist));
	}

	public Optional<PlayList> removeSongFromPlaylist(long playlistId, long songId, Long userId) {

		User user = userService.getUserById(userId);
		if (user == null)
			return Optional.empty();

		Optional<PlayList> pOpt = playListRepository.findById(playlistId);
		if (!pOpt.isPresent())
			return Optional.empty();

		PlayList playlist = pOpt.get();

		playlist.getSongs().removeIf(s -> s.getId() == songId);

		return Optional.of(playListRepository.save(playlist));
	}

}
