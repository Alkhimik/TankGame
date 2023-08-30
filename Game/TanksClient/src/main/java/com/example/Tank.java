package com.example;

import javafx.scene.shape.Rectangle;

public class Tank  extends Rectangle {

    public volatile Rectangle rectGun = new Rectangle(390,500,20,70);
    public volatile int health = 100;

    public int power = 5;

    public volatile Boolean enemyWon = false;
    public Tank(int x, int y, int x2, int y2){
        super(x,y,x2,y2);
    }

    public volatile Ball ball = new Ball((int)this.getX()+50,515,10);

    public void hit() {
       health -= power;
        System.out.println("HIT! health = " + health);
    }

}
