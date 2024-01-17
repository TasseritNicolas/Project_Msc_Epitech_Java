package com.mygdx.game;
public class Houses {
    protected boolean open=false;
    protected float coX, coY;

    public Houses(float coX, float coY, boolean open){
        this.coX = coX;
        this.coY = coY;
        this.open = open;
    }
    public boolean isOpen(){return this.open;}
}
