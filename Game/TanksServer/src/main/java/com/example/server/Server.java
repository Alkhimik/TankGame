package com.example.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    public void startListening() throws IOException {
        int playerId = 1;
        Socket[] socket = new Socket[2];
        Thread first = new Thread();
        Thread second = new Thread();
        Boolean started = false;
        while(true){
            socket[playerId-1] = serverSocket.accept();
            if(playerId == 2){
                started = true;
                first = new ClientHandler(socket[0],new PrintWriter(socket[1].getOutputStream(),true), new Stats(1,0,0,0));
                second = new ClientHandler(socket[1],new PrintWriter(socket[0].getOutputStream(),true), new Stats(2,0,0,0));
                first.start();
                second.start();
            }
            playerId++;
            if(playerId-1 > 2){
                System.out.println("Too many players");
                break;
            }
            if((!first.isAlive() || !second.isAlive()) && started){
               break;
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting server...Choose port:");
        Scanner scanner = new Scanner(System.in);
        ServerSocket server;
        Socket freePortCheck;
        int port = 8008;
        while(true){
            try {
                port = scanner.nextInt();
                if(port >= 908654){
                    throw new Exception("port out of range");
                }
                freePortCheck = new Socket("localhost", port);
                System.out.println("This port is already taken");

            }catch (IOException e){
                System.out.println("Port "+ port + " is free.");
                Thread.sleep(1300);
                break;
            }catch (InputMismatchException et){
                scanner.next();
                System.out.println("Incorrect port value");
            }catch (NoSuchElementException e){
                System.out.println(e.getMessage());
                scanner.next();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
            System.out.println("Server deployed.");
            Server serv = new Server(new ServerSocket(port));
            serv.startListening();
            scanner.close();
    }
}