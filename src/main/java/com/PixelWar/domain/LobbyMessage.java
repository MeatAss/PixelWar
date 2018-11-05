package com.PixelWar.domain;

public class LobbyMessage {
    private Long id;
    private String message;

    public LobbyMessage() {
    }

    public LobbyMessage(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
