package com.mymusic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mymusic.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

	List<Song> findByTitleContainingIgnoreCase(String title);

	List<Song> findByArtistContainingIgnoreCase(String artist);

	List<Song> findByGenreContainingIgnoreCase(String genre);

	@Query("SELECT s FROM Song s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(s.artist) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(s.genre) LIKE LOWER(CONCAT('%', :q, '%'))")
	List<Song> searchAll(@Param("q") String q);

}
