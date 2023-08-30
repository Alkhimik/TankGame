package com.example.server;

import Data.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.TreeMap;

public class ClientHandler extends Thread{
    private Socket clientSocket;
    private PrintWriter out;

    PrintWriter ToanotherPlayer;
    private BufferedReader in;

    private Stats stat;


    public ClientHandler(Socket socket,PrintWriter ToanotherPlayer, Stats stat){
        this.ToanotherPlayer = ToanotherPlayer;
        this.stat = stat;
        clientSocket = socket;
    }

    public void run(){
        Repository rep = null;
        try {
            rep = new Repository();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rep.saveStats(stat);

        try {
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println(stat.id);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String connected;
        while(true) {
            try {
                if (!((connected = in.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(".".equals(connected)){
                out.println("server closed");
                break;
            }

         if(connected.equals("Ball:false")){
             stat.total_shots+=1;
             stat.missed+=1;
             rep.updateStats(stat);
         }
        else if(connected.equals("Ball:hit")){
             stat.total_shots+=1;
             stat.hits+=1;
             rep.updateStats(stat);
         }else if(connected.equals("WIN")){
             ToanotherPlayer.println("stop");
             TreeMap<String, Integer> stata = rep.getStats(stat.id);
             out.println("TOTAL_SHOTS:" + stata.get("total_shots") + ":HITS:" + stata.get("hits") + ":MISSED:" + stata.get("missed"));
         }else if(connected.equals("LOSE")){
             ToanotherPlayer.println("stop");
             TreeMap<String, Integer> stata = rep.getStats(stat.id);
             out.println("LoTOTAL_SHOTS:" + stata.get("total_shots") + ":HITS:" + stata.get("hits") + ":MISSED:" + stata.get("missed"));
         }else
            ToanotherPlayer.println(connected);
        }
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
