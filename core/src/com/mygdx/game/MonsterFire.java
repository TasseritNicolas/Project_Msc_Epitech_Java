package com.mygdx.game;
public class MonsterFire extends Monster {
    public MonsterFire(int life, int damage, int xp, int element) {
        super(life, damage, xp);
        this.element = element;
    }

    @Override
    public void dealElement(Hero hero) {

    }

    public int getElement() {
        return this.element;
    }
}
