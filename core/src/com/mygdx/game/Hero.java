package com.mygdx.game;
public class Hero extends Character {
    private int life = 20;
    private int damage = 2;
    private int xp = 0;
    private int level = 0;
    private int potion = 0;
    private boolean sword = false;
    private int maxLife = 20;
    private boolean bourse = false;
    private int money = 10;

    private int level_exp = 10;

    public Hero(Float coX, Float coY, String name) {
        super(coX, coY, name);
    }

    public int getLife() {
        return this.life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getMaxLife() {
        return this.maxLife;
    }

    public int getPotion() {
        return this.potion;
    }

    public boolean addPotion() {
        if (!(this.potion >= 3)) {
            this.potion += 1;
            System.out.println("Vous avez " + getPotion() + " potions");
            return true;
        } else {
            System.out.println("Vous ne pouvez pas avoir plus de 3 potions");
            return false;
        }

    }

    public void Heal() {
        if (this.getPotion() > 0 && this.getLife() < this.getMaxLife()) {
            this.setLife(this.getLife() + 10);
            this.potion -= 1;
            System.out.println("Vous avez " + this.getLife() + " Life et " + this.getPotion() + " potions");
        } else {
            String res = this.getPotion() <= 0 ? "Vous n'avez pas de potion" : "Vous avez deja toute votre vie";
            System.out.println(res);
        }
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getXp() {
        return this.xp;
    }
    public void setPotion(int nb){this.potion = nb;}

    public void setXp(int xp, int money) {
        if ((this.getXp() + xp) >= this.level_exp) {
            this.level += 1;
            this.level_exp += 10;
            this.damage += 2;
            this.setMaxLife(this.getMaxLife() + 10);
            // !
            this.setLife(this.getMaxLife());
            this.xp = 0;
            System.out.println("Vous avez gagné un niveau");
        } else
            this.xp += xp;
    }

    public int getLvl() {
        return this.level;
    }

    public boolean haveSword() {
        return this.sword;
    }

    public void addSword() {
        if (!this.sword) {
            this.sword = true;
            System.out.println("J'ai une epee");
        } else
            System.out.println("Vous avez deja une epee");
    }

    public int getDamage() {
        return this.damage;
    }

    // Damage that i will cause to the monster
    public void dealDamage(Monster monster) {
        monster.setLife(monster.getLife() - getDamage());
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // ?:haveBourse for what?
    public boolean haveBourse() {
        return this.bourse;
    }

    public void addBourse(boolean bourse) {
        this.bourse = bourse;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        if(this.money<=0)
            return 0;
        else
            return this.money;
    }
    // test
}
// # add if hero doesnt have sword cant attack monsters