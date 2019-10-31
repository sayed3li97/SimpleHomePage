package com.example.simplehomepage.Model;

public class Items {
    private String label;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Items(String label, String img) {
        this.label = label;
        this.img = img;
    }

    public Items(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
