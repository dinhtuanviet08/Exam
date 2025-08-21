package com.example.playereval.repo;

import com.example.playereval.entity.PlayerIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerIndexRepository extends JpaRepository<PlayerIndex, Integer> {
    List<PlayerIndex> findByPlayer_PlayerId(Integer playerId);
    void deleteByPlayer_PlayerId(Integer playerId);
}
