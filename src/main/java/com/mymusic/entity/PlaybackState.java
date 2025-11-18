package com.mymusic.entity;

import java.time.Instant;

import lombok.Data;

@Data
public class PlaybackState {

	private Song song;
	private String playingState;
	private Instant startedAt;
	private long positionSeconds;

}
