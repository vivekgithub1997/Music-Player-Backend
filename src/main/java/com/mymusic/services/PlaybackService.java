package com.mymusic.services;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mymusic.entity.PlaybackState;
import com.mymusic.entity.Song;
import com.mymusic.entity.User;
import com.mymusic.repository.SongRepository;

@Service
public class PlaybackService {

	private final Map<Long, PlaybackState> playbackMap = new HashMap<>();

	private final SongRepository songRepository;

	@Autowired
	public PlaybackService(SongRepository songRepository) {
		this.songRepository = songRepository;
	}

	public Optional<PlaybackState> play(User user, long songId) {
		Optional<Song> s = songRepository.findById(songId);
		if (!s.isPresent())
			return Optional.empty();
		PlaybackState st = new PlaybackState();
		st.setSong(s.get());
		st.setPlayingState("PLAYING");
		st.setStartedAt(Instant.now());
		st.setPositionSeconds(0);
		playbackMap.put(user.getId(), st);
		return Optional.of(st);
	}

	public Optional<PlaybackState> pause(User user) {
		PlaybackState st = playbackMap.get(user.getId());
		if (st == null)
			return Optional.empty();
		if (!"PLAYING".equals(st.getPlayingState()))
			return Optional.of(st);

		long elapsed = Instant.now().getEpochSecond() - st.getStartedAt().getEpochSecond();
		st.setPositionSeconds(st.getPositionSeconds() + elapsed);
		st.setPlayingState("PAUSED");
		return Optional.of(st);
	}

	public Optional<PlaybackState> resume(User user) {
		PlaybackState st = playbackMap.get(user.getId());
		if (st == null)
			return Optional.empty();
		if (!"PAUSED".equals(st.getPlayingState()))
			return Optional.of(st);
		st.setStartedAt(Instant.now());
		st.setPlayingState("PLAYING");
		return Optional.of(st);
	}

	public Optional<PlaybackState> stop(User user) {
		PlaybackState st = playbackMap.get(user.getId());
		if (st == null)
			return Optional.empty();
		st.setPlayingState("STOPPED");
		st.setPositionSeconds(0);
		playbackMap.remove(user.getId());
		return Optional.of(st);
	}

	public Optional<PlaybackState> current(User user) {
		return Optional.ofNullable(playbackMap.get(user.getId()));
	}
}