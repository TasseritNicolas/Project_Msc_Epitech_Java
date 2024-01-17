package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;


public class Coin {
    private final float x;
    private final float y;
    private int time;
    private final Texture texture = new Texture("Texture/coin.png");
    public Coin(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {return this.x;}

    public float getY() {return this.y;}

    public Texture getTexture() {return texture;}

    public int getTime() {return this.time;}
    public void setTime(int time) {this.time = time;}
}
