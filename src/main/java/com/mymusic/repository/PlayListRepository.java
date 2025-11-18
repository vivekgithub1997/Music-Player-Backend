package com.mymusic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mymusic.entity.PlayList;
import com.mymusic.entity.User;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {
	List<PlayList> findByOwner(User owner);

}
