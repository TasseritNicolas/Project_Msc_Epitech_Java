package com.mygdx.game;
public class Boss extends Monster {
    protected int element;

    public Boss(int life, int damage, int xp, int element) {
        super(life, damage, xp);
        this.element = element;
    }

    public int getElement() {
        return this.element;
    }

    @Override
    public void dealElement(Hero hero) {

    }

}
