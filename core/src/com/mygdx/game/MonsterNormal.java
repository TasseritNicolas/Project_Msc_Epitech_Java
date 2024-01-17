package com.mygdx.game;
public class MonsterNormal extends Monster {
    public MonsterNormal(int life, int damage, int xp, int element) {
        super(life, damage, xp);
        this.element = element;
    }

    public int getElement() {
        return this.element;
    }

    @Override
    public void dealElement(Hero hero) {
        if (hero.haveSword()) {
            System.out.println(hero.getName() + " subit " + this.getElement(this.getElement()) + " degats");
            this.dealDamage(hero);
        } else {
            System.out.println("I can't deal damage to you because you don't have a sword");
        }

    }

}
