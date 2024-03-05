package com.example.immobilier;

public class Tag {
    private String name;
    private boolean selected;

    public Tag(String name) {
        this.name = name;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
