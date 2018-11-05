package com.PixelWar.domain;

import javax.persistence.*;

@Entity
@Table(name= "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nameImg;

    @Lob
    private String dataImg;

    public Image() {
    }

    public Image(String nameImg, String dataImg) {
        this.nameImg = nameImg;
        this.dataImg = dataImg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    public String getDataImg() {
        return dataImg;
    }

    public void setDataImg(String dataImg) {
        this.dataImg = dataImg;
    }
}
