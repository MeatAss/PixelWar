package com.PixelWar.domain;

import javax.persistence.*;

@Entity
@Table(name= "imageTable")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    private String imageData;

    public Image() {
    }

    public Image(String imageData) {
        this.imageData = imageData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
