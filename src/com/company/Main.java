package com.company;


import java.util.Random;

public class Main {

    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {260, 250, 240, 400, 280, 300, 150, 100};
    public static int[] heroesDamage = {25, 20, 15, 0, 8, 5, 1, 15};
    public static String[] heroesAtackType = {"Physical", "Magic", "Kinetic", "Medical", "Berserk", "Golem", "Lucky", "Thor"};

    public static void changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAtackType.length);//0,1,2
        bossDefenceType = heroesAtackType[randomIndex];
        System.out.println("Boss choose" + bossDefenceType);
    }


    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }

        }

        if (allHeroesDead) {
            System.out.println("Boss woon");
        }
        return allHeroesDead;
    }

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }

    }


    public static void round() {
        if (bossHealth > 0) {
            changeBossDefence();
            bossHits();
        }
        heroesHits();
        printStatistics();
        medic();
        lucky();

        if (heroesHealth[5] > 0) {
            golem();
        }
        if (heroesHealth[4] > 0) {
            berserk();
        }
        if (bossDamage <0){
            thor();
        }

    }

    public static void printStatistics() {
        System.out.println("___________________________");
        System.out.println("boss health: " + bossHealth);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAtackType[i] + " health: " + heroesHealth[i]);

        }
        System.out.println("___________________________");
    }

    public static void bossHits() {
        Random r = new Random();
        int chance = r.nextInt(3);// 0,1,2
        double coeff = Math.random();
        if (chance == 0) {
            System.out.println("Boss became weaker " + (int) (bossDamage * coeff));
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHits() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0) {
                if (bossHealth > 0) {
                    if (bossDefenceType == heroesAtackType[i]) {
                        Random random = new Random();
                        int coeff = random.nextInt(10) + 2; // 2,3,4,5,6,7,8,9,10,11
                        System.out.println("Critical damage = " + heroesDamage[i] * coeff);
                        if (bossHealth - heroesDamage[i] * coeff < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i] * coeff;
                        }
                    } else {
                        if (bossHealth - heroesDamage[i] < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i];
                        }
                    }
                }
            }
        }
    }

    public static void medic() {
        int health = 10;
        Random random = new Random();
        int chance = random.nextInt(heroesDamage.length);
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[chance] > 0 && heroesHealth[chance] < 100 && heroesHealth[i] > 0) {
                heroesHealth[chance] = heroesHealth[chance] + health;
            }
        }
        System.out.println("медик вылечил " + heroesAtackType[chance]);
    }

    public static void golem() {
        int oneFifthOfTheBossDamage = bossDamage * 4 / 5;
        Random random = new Random();
        boolean golemProtection = random.nextBoolean();
        if (golemProtection) {
            for (int i = 0; i < heroesHealth.length; i++) {
                heroesHealth[i] = heroesHealth[i] + oneFifthOfTheBossDamage;
            }
        }
        System.out.println("Golem protection: ");
    }

    public static void berserk() {
        int berserkDamage = bossDamage + heroesDamage[4];
        Random random = new Random();
        boolean berserkCritical = random.nextBoolean();
        if (berserkCritical) {
            heroesHealth[4] = heroesHealth[4] + bossDamage;
            bossHealth = bossHealth - berserkDamage;
            System.out.println("Berserk helped!: ");
        }
    }
    public  static void lucky(){
        int luckyEvacion = bossDamage + heroesDamage[5];
        Random random = new Random();
        boolean luckyDamage =random.nextBoolean();
        if(luckyDamage){
            heroesAtackType[2]= heroesAtackType [2] + bossDamage;
            bossHealth = bossHealth - luckyEvacion;
            System.out.println("Lucky dodged the boss!: ");
        }
    }
    public static void thor(){
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAtackType.length);
        bossDefenceType = heroesAtackType[randomIndex];
        System.out.println("Thor stunned the boss: " + heroesAtackType);

    }

}
