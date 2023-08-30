package com.example;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerHandler implements Runnable{
    private BufferedReader in;
    private int playerid;
    private Tank enemyTank;

    public ServerHandler(BufferedReader in, int playerId, Tank enemyTank){
        this.in = in;
        this.playerid = playerid;
        this.enemyTank = enemyTank;
    }

    public void run(){
        String connected;
        while(true) {
            try {
                if (!((connected = in.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(".".equals(connected)){
                break;
            }
            if(connected.contains("Tank")){
                String[] array = connected.split(":");
                enemyTank.setX(Double.parseDouble(array[1]));
                enemyTank.rectGun.setX(Double.parseDouble(array[3]));

            }else if(connected.contains("BalX")){
                String[] array = connected.split(":");
               enemyTank.ball.setX(Double.parseDouble(array[1]));
            }else if(connected.equals("Ball:fire")){
                enemyTank.ball.startMove = true;
            }
            else if(connected.equals("Ball:false") || connected.equals("Ball:hit")){
                enemyTank.ball.reset();
                enemyTank.ball.startMove = false;
            }else if(connected.equals("stop")) {
                enemyTank.enemyWon = true;
            }
            else if(connected.contains("LoTOTAL_SHOTS:")){
                    System.out.println("Losser STAT: " + connected);
                    String[] array = connected.split(":");
                    EndScreen end = new EndScreen(Integer.parseInt(array[1]), Integer.parseInt(array[3]),Integer.parseInt(array[5]),"Loser!");
            }else if(connected.contains("TOTAL_SHOTS:")){
                System.out.println("WIN STAT: " + connected);
                String[] array = connected.split(":");
                EndScreen end = new EndScreen(Integer.parseInt(array[1]), Integer.parseInt(array[3]),Integer.parseInt(array[5]),"Winner!");
            }
        }
    }

}
