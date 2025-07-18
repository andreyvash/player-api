package com.player.api;

import com.player.api.dto.CreatePlayerRequest;
import com.player.api.dto.PatchPlayerRequest;
import com.player.api.dto.PlayerResponse;
import com.player.api.dto.UpdatePlayerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    
    @Autowired
    private PlayerService playerService;
    
    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getAllPlayers() {
        List<PlayerResponse> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }
    
    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(@RequestBody CreatePlayerRequest request) {
        PlayerResponse createdPlayer = playerService.createPlayer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable Long id, 
                                                       @RequestBody UpdatePlayerRequest request) {
        PlayerResponse updatedPlayer = playerService.updatePlayer(id, request);
        return ResponseEntity.ok(updatedPlayer);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<PlayerResponse> patchPlayer(@PathVariable Long id,
                                                      @RequestBody PatchPlayerRequest request) {
        PlayerResponse updatedPlayer = playerService.patchPlayer(id, request);
        return ResponseEntity.ok(updatedPlayer);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
} 