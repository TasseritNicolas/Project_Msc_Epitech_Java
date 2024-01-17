package com.mygdx.game;
import java.util.Random;

public abstract class Monster {
    protected int life;
    protected int damage;
    protected int xp;
    protected int element;
    protected int money;

    public Monster(int life, int damage, int xp) {
        Random r = new Random();
        this.life = life;
        this.damage = damage;
        this.xp = r.nextInt(xp);
        this.money = r.nextInt(5);
    }

    public int getLife() {
        return this.life;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getXp() {
        return this.xp;
    }

    public void dealDamage(Hero hero) {
        hero.setLife(hero.getLife() - this.damage);
    }

    public abstract void dealElement(Hero hero);

    public void setLife(int life) {
        this.life = life;
    }

    public String getElement(int x) {
        switch (x) {
            case 1:
                return "fire";
            case 2:
                return "water";
            case 3:
                return "electricity";

            default:
                return "normal";

        }
    }
}
