package com.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ball extends Rectangle {


    public volatile int ORIGINAL_Y = 515;
    public volatile int speed = 7;

    public volatile Boolean startMove = false;

    Ball(int x, int y, int ballSize){
        super(x,y,ballSize,ballSize);
        this.setFill(Color.RED);
    }

    public void move() throws InterruptedException {
        this.setY(this.getY() - speed);
    }

    public void reset(){
        setY(ORIGINAL_Y);
    }

}