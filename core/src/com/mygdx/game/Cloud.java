package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Cloud {
    private float x;
    private float y;
    private final Texture texture = new Texture("Texture/cloud1.png");

    public Cloud(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        x += 2;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }
}
