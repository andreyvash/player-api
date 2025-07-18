package com.player.api;

import com.player.api.dto.CreatePlayerRequest;
import com.player.api.dto.PatchPlayerRequest;
import com.player.api.dto.PlayerResponse;
import com.player.api.dto.UpdatePlayerRequest;
import com.player.api.mapper.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private PlayerMapper playerMapper;
    
    public List<PlayerResponse> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return playerMapper.toResponseList(players);
    }
    
    public Optional<PlayerResponse> getPlayerById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Player ID must be a positive number");
        }
        
        return playerRepository.findById(id)
                .map(playerMapper::toResponse);
    }
    
    public PlayerResponse createPlayer(CreatePlayerRequest request) {
        validateCreateRequest(request);
        
        Player player = playerMapper.toEntity(request);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toResponse(savedPlayer);
    }
    
    public PlayerResponse updatePlayer(Long id, UpdatePlayerRequest request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Player ID must be a positive number");
        }
        
        validateUpdateRequest(request);
        
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        
        Player updatedPlayer = playerMapper.updateEntityFromRequest(existingPlayer, request);
        Player savedPlayer = playerRepository.save(updatedPlayer);
        return playerMapper.toResponse(savedPlayer);
    }
    
    public PlayerResponse patchPlayer(Long id, PatchPlayerRequest request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Player ID must be a positive number");
        }
        
        if (request == null) {
            throw new PlayerValidationException("Player update data cannot be null");
        }
        
        validatePatchRequest(request);
        
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        
        playerMapper.updateEntityFromPatch(existingPlayer, request);
        
        Player savedPlayer = playerRepository.save(existingPlayer);
        return playerMapper.toResponse(savedPlayer);
    }
    
    public void deletePlayer(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Player ID must be a positive number");
        }
        
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        playerRepository.delete(player);
    }
    
    private void validateCreateRequest(CreatePlayerRequest request) {
        if (request == null) {
            throw new PlayerValidationException("Player data cannot be null");
        }

        validateRequiredFields(request.getFirstName(), request.getLastName());
    }

    private void validateUpdateRequest(UpdatePlayerRequest request) {
        if (request == null) {
            throw new PlayerValidationException("Player data cannot be null");
        }
        
        validateRequiredFields(request.getFirstName(), request.getLastName());
    }
    
    private void validatePatchRequest(PatchPlayerRequest request) {
        validateRequiredFields(request.getFirstName(), request.getLastName());
    }

    private static void validateRequiredFields(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new PlayerValidationException("First name is required");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            throw new PlayerValidationException("Last name is required");
        }
    }

} 