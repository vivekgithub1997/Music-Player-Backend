package com.mymusic.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PlayList {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long playListId;
	private String name;
	@ManyToOne
	@JoinColumn(name = "owner_id")
	@JsonIgnore
	private User owner;

	@ManyToMany
	@JoinTable(name = "playlist_songs", joinColumns = @JoinColumn(name = "playlist_id"), inverseJoinColumns = @JoinColumn(name = "song_id"))
	private List<Song> songs = new ArrayList<>();

}
