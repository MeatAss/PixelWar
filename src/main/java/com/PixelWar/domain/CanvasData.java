package com.PixelWar.domain;

public class CanvasData {
    private Long id;
    private int[] arrLineTo;
    private int[] arrMoveTo;
    private String size;
    private String color;

    public CanvasData() {
    }

    public CanvasData(Long id, int[] arrLineTo, int[] arrMoveTo, String size, String color) {
        this.id = id;
        this.arrLineTo = arrLineTo;
        this.arrMoveTo = arrMoveTo;
        this.size = size;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int[] getArrLineTo() {
        return arrLineTo;
    }

    public void setArrLineTo(int[] arrLineTo) {
        this.arrLineTo = arrLineTo;
    }

    public int[] getArrMoveTo() {
        return arrMoveTo;
    }

    public void setArrMoveTo(int[] arrMoveTo) {
        this.arrMoveTo = arrMoveTo;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
