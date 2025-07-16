package com.player.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;
    
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }
    
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
    
    public Player updatePlayer(Long id, Player playerDetails) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
        
        player.setFirstName(playerDetails.getFirstName());
        player.setLastName(playerDetails.getLastName());
        player.setPosition(playerDetails.getPosition());
        player.setTeam(playerDetails.getTeam());
        
        return playerRepository.save(player);
    }
    
    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
        playerRepository.delete(player);
    }
} 