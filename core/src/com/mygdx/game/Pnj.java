package com.mygdx.game;
public abstract class Pnj extends Character {
    public Pnj(Float coX, Float coY, String name) {
        super(coX, coY, name);
    }

    protected String defaultDialogue;

    public abstract void talk(Hero hero);

}
