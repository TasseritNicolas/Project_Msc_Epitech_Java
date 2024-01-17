package com.mygdx.game;
public class TpHouses extends Houses {
    protected float coXCible, coYCible;

    public TpHouses(float coX, float coY, float coXCible, float coYCible, boolean open) {
        super(coX, coY, open);
        this.coXCible = coXCible;
        this.coYCible = coYCible;
    }

   /* public void tp(Hero hero) {
        if (this.open) {
            hero.setCo(this.coXCible, this.coYCible);
        }
    }*/

    /*public void rtp(Hero hero) {
        if (this.open) {
            hero.setCo(this.coX, this.coY);
        }
    }*/
}
