package com.example;

import javax.swing.*;
import java.io.*;
import java.net.Socket;


public class ClientConnection {

    private int playerId;
    private Socket clientSocket;
    private PrintWriter out;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public BufferedReader getIn() {
        return in;
    }

    private BufferedReader in;

    public Boolean startConnection(Socket socket, Tank enemyTank) {
        try{
            clientSocket = socket;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            playerId = Integer.parseInt(in.readLine());
            System.out.println("connected with id:" + playerId);

            Thread thread = new Thread(new ServerHandler(in, playerId, enemyTank));
            thread.start();

            return true;
        } catch (IOException d){return false;}

    }
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public String sendMessage(String msg)  {
        try{
            out.println(msg);
            return null;
        } catch (Exception e){
            System.out.println("Message was not sent");
            return null;
        }
    }
    }
