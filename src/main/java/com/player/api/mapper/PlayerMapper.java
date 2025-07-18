package com.player.api.mapper;

import com.player.api.Player;
import com.player.api.dto.CreatePlayerRequest;
import com.player.api.dto.PatchPlayerRequest;
import com.player.api.dto.PlayerResponse;
import com.player.api.dto.UpdatePlayerRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerMapper {

    public Player toEntity(CreatePlayerRequest request) {
        if (request == null) {
            return null;
        }
        
        return Player.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .position(request.getPosition())
                .team(request.getTeam())
                .build();
    }

    public Player toEntity(UpdatePlayerRequest request) {
        if (request == null) {
            return null;
        }
        
        return Player.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .position(request.getPosition())
                .team(request.getTeam())
                .build();
    }

    public Player toEntity(PatchPlayerRequest request) {
        if (request == null) {
            return null;
        }
        
        return Player.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .position(request.getPosition())
                .team(request.getTeam())
                .build();
    }

    public PlayerResponse toResponse(Player entity) {
        if (entity == null) {
            return null;
        }
        
        return PlayerResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .position(entity.getPosition())
                .team(entity.getTeam())
                .build();
    }

    public List<PlayerResponse> toResponseList(List<Player> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromPatch(Player entity, PatchPlayerRequest request) {
        if (entity == null || request == null) {
            return;
        }
        
        if (request.getFirstName() != null) {
            entity.setFirstName(request.getFirstName());
        }
        
        if (request.getLastName() != null) {
            entity.setLastName(request.getLastName());
        }
        
        if (request.getPosition() != null) {
            entity.setPosition(request.getPosition());
        }
        
        if (request.getTeam() != null) {
            entity.setTeam(request.getTeam());
        }
    }

    public Player updateEntityFromRequest(Player existingEntity, UpdatePlayerRequest request) {
        if (existingEntity == null || request == null) {
            return existingEntity;
        }
        
        return Player.builder()
                .id(existingEntity.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .position(request.getPosition())
                .team(request.getTeam())
                .build();
    }
} 