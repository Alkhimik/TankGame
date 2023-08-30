package com.example;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Controller {
    public Controller(Tank tank, Group root, Scene scene, ClientConnection connect){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.A){
                    if(tank.getX()-10 > 0){
                        connect.sendMessage(String.valueOf("TankX:"+ (tank.getX()-10)) + ":TankGunX:"+ (tank.rectGun.getX()-10));
                        tank.setX(tank.getX()-10);
                        tank.rectGun.setX(tank.rectGun.getX()-10);
                    }
                }
                if(keyEvent.getCode() == KeyCode.D){
                    if(tank.getX()+10 < 700){
                        connect.sendMessage(String.valueOf("TankX:"+ (tank.getX()+10)) + ":TankGunX:"+ (tank.rectGun.getX()+10));
                        tank.setX(tank.getX()+10);
                        tank.rectGun.setX(tank.rectGun.getX()+10);
                    }
                }
                if(keyEvent.getCode() == KeyCode.SPACE && !root.getChildren().contains(tank.ball)){
                    connect.sendMessage(String.valueOf("Ball:fire"));
                    tank.ball.setX((int)tank.getX()+50);
                    root.getChildren().add(tank.ball);
                    tank.ball.startMove = true;
                }
            }
        });
    }

}
