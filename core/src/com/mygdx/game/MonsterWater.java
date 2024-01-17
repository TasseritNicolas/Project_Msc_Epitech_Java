package com.mygdx.game;
public class MonsterWater extends Monster {
    public MonsterWater(int life, int damage, int xp, int element) {
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
