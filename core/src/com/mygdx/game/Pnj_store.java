package com.mygdx.game;
import java.util.*;

public class Pnj_store extends Pnj {

    public Pnj_store(Float coX, Float coY, String name) {
        super(coX, coY, name);
    }

    @Override
    public void talk(Hero hero) {
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        while (choix != 3) {
            System.out.println("Bonjour, je suis le marchand, que voulez vous acheter ?");
            System.out.println("1 - Potion de vie (10$)");
            System.out.println("2 - Epee (20$)");
            System.out.println("3 - Quitter");
            choix = sc.nextInt();

            switch (choix) {
                case 1:
                    if (hero.getMoney() >= 10) {
                        if (hero.addPotion()) {
                            hero.setMoney(hero.getMoney() - 10);
                            System.out.println(
                                    "Vous avez " + hero.getMoney() + " $" + " et " + hero.getPotion() + " potions");
                        }

                    } else {
                        System.out.println("Vous n'avez pas assez d'argent");
                    }
                    break;
                case 2:
                    if (hero.getMoney() >= 20 && hero.haveSword() == false) {
                        hero.setMoney(hero.getMoney() - 20);
                        hero.addSword();
                    } else {
                        System.out.println("Vous n'avez pas assez d'argent");
                    }
                    break;
                case 3:
                    System.out.println("Au revoir");
                    break;

                default:
                    System.out.println("Choix invalide");
                    sc.nextInt();
            }
        }
    }

}
