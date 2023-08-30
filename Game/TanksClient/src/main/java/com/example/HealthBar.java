package com.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class HealthBar extends Rectangle {
    public HealthBar(int x, int y, int width, int height){
        super(x,y,width,height);
        setFill(Color.rgb(209,82,82));
    }

    public void damage(double power){
       setWidth(getWidth()-power);
    }

}
