package com.PixelWar.domain;

public class ImageMessage extends Image {
    private String countLobby;

    public ImageMessage(Image image, String countLobby) {
        setId(image.getId());
        setDataImg(image.getDataImg());
        setNameImg(image.getNameImg());
        this.countLobby = countLobby;
    }

    public String getCountLobby() {
        return countLobby;
    }

    public void setCountLobby(String countLobby) {
        this.countLobby = countLobby;
    }
}
