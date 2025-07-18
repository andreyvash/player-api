package com.player.api;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
    
    public PlayerNotFoundException(Long id) {
        super("Player not found with id: " + id);
    }
} 