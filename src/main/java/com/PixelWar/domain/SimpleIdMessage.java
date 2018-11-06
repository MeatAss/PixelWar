package com.PixelWar.domain;

public class SimpleIdMessage extends SimpleMessage {
    private Long id;

    public SimpleIdMessage() {
        super();
    }

    public SimpleIdMessage(Long id) {
        super();
        this.id = id;
    }

    public SimpleIdMessage(String message, Long id) {
        super(message);
        this.id = id;
    }

    public SimpleIdMessage(Long message, Long id) {
        super(String.valueOf(message));
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
