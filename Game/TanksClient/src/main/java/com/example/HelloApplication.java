package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelloApplication extends Application {
    private Tank tank = new Tank(350,515,100,70);
    private Tank enemyTank = new Tank(350,15,100,70);
    private static Menu menu;
    private static WaitingFrame waitingFrame;
    private static Socket socket;


    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        ClientConnection connect = new ClientConnection();
        Boolean bool = false;
        while(bool == false){
            bool = connect.startConnection(socket,enemyTank);
        }
        waitingFrame.dispose();
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.rgb(233,216,216));

        stage.setTitle("World of Tanks!");

        tank.rectGun.setFill(Color.DARKGREEN);

        enemyTank.rectGun.setY(390);
        enemyTank.rectGun.setY(34);
        enemyTank.rectGun.setWidth(20);
        enemyTank.rectGun.setHeight(70);

        enemyTank.ball.speed = -7;
        enemyTank.ball.ORIGINAL_Y = 70;
        enemyTank.ball.reset();

        enemyTank.rectGun.setFill(Color.DARKGREEN);

        tank.setFill(Color.CORAL);
        tank.setStrokeWidth(5);
        tank.setStroke(Color.BLACK);

        enemyTank.setFill(Color.CORAL);
        enemyTank.setStrokeWidth(5);
        enemyTank.setStroke(Color.BLACK);


        HealthBar myHealth = new HealthBar(5,585,100,10);

        HealthBar enemyHealth = new HealthBar(5,5,100,10);

       root.getChildren().add(enemyHealth);
       root.getChildren().add(myHealth);
       root.getChildren().add(tank);
       root.getChildren().add(enemyTank);
       root.getChildren().add(tank.rectGun);
       root.getChildren().add(enemyTank.rectGun);


        Controller controler = new Controller(tank,root, scene,connect);



        new AnimationTimer(){
            @Override
            public void handle(long now) {
                try {
                    if(tank.ball.startMove){
                        tank.ball.move();
                        connect.sendMessage("BalX: " + tank.ball.getX() + ":BalY: " + tank.ball.getY());
                    }

                    if(enemyTank.ball.startMove){
                        if(!root.getChildren().contains(enemyTank.ball)){
                            root.getChildren().add(enemyTank.ball);
                        }
                        enemyTank.ball.move();
                    }

                    if(enemyTank.ball.intersects(tank.getX(), tank.getY(),tank.getWidth(), tank.getHeight())){
                        myHealth.damage(enemyTank.power);
                        root.getChildren().remove(enemyTank.ball);
                        enemyTank.ball.reset();
                        enemyTank.ball.startMove=false;

                    }

                    if(enemyTank.ball.startMove == false){
                        if(root.getChildren().contains(enemyTank.ball)){
                            root.getChildren().remove(enemyTank.ball);
                        }
                    }
                    if(tank.ball.intersects(enemyTank.getX(),enemyTank.getY(),enemyTank.getWidth(),enemyTank.getHeight())){
                        enemyHealth.damage(tank.power);
                        enemyTank.hit();
                        root.getChildren().remove(tank.ball);
                        connect.sendMessage("Ball:hit");
                        tank.ball.reset();
                        tank.ball.startMove=false;

                        if(enemyTank.health == 0){
                            System.out.println("WIN");
                            connect.sendMessage("WIN");
                            stop();
                        }
                    }
                    if(tank.ball.getY() <= 0){
                        connect.sendMessage("Ball:false");
                        root.getChildren().remove(tank.ball);
                        tank.ball.reset();
                        tank.ball.startMove=false;
                    }

                    if(enemyTank.ball.getY() >= 530){
                        root.getChildren().remove(enemyTank.ball);
                        enemyTank.ball.reset();
                        enemyTank.ball.startMove=false;
                    }
                    if(enemyTank.enemyWon){
                        System.out.println("ENEMY WON");
                        connect.sendMessage("LOSE");
                        stop();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        menu = new Menu();
        ExecutorService exec = Executors.newFixedThreadPool(1);
        Future<Socket> port = exec.submit(menu);
        socket = port.get();
        menu.dispose();

        waitingFrame = new WaitingFrame();
        waitingFrame.run();
        Thread.sleep(500);
        launch(args);
    }

}