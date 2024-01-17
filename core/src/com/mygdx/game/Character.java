package com.mygdx.game;
public abstract class Character {
    protected String name;
    protected Point co;

    public Character(Float coX, Float coY, String name) {
        this.name = name;
        this.co = new Point(coX, coY);
    }

    public Point getCo() {
        return this.co;
    }

    public String getName() {
        return this.name;
    }

    public void setCo(Float coX, Float coY) {
        this.co.setCo(coX, coY);
    }
}
