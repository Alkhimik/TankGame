package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Menu extends JFrame implements Callable<Socket>{
    private int port;
    public Socket Gamesocket;
    public volatile Boolean success = false;

    public void createDefaultWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,1));
        setSize(new Dimension(500,300));

        addButtons();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void addButtons(){
        JButton bStart = new JButton("Start game");
        JButton bExit = new JButton("Exit");
        JTextField portText = new JTextField("Port:8080");
        JLabel labelWarning = new JLabel();
        getContentPane().add(bStart);
        getContentPane().add(portText);
        getContentPane().add(bExit);
        getContentPane().add(labelWarning);

        start(bStart, portText, labelWarning);
        exitButton(bExit);
    }

    private void exitButton(JButton bExit){
        bExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private boolean start(JButton bStart, JTextField portText, JLabel labelWarning){
        bStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] array;
                boolean bool = false;
                if(portText.getText().contains(":")){
                    array =  portText.getText().split(":");
                    if(array.length == 2 && array[1].matches("\\d++")){
                        port = Integer.parseInt(array[1]);
                        bool = true;
                    }
                }
                else if(portText.getText().matches("\\d++")) {
                    port = Integer.parseInt(portText.getText());
                    bool = true;}
                try {
                        Socket socket = new Socket("localhost",port);
                    labelWarning.setForeground(Color.green);
                    labelWarning.setText("success");
                    success = true;
                    Gamesocket = socket;
                    Thread.sleep(500);
                    } catch (IOException ex) {
                            labelWarning.setForeground(Color.red);
                            labelWarning.setText("    Wrong server port.");
                    } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return labelWarning.getText().matches("success");
    }

    public void waitWindow(int numDots){
        getContentPane().removeAll();
        if(numDots == 1){
            JLabel waitMessage = new JLabel("WAIT FOR ANOTHER PLAYER.");
            getContentPane().add(waitMessage);
        }

        if(numDots == 2){
            JLabel waitMessage = new JLabel("WAIT FOR ANOTHER PLAYER..");
            getContentPane().add(waitMessage);
        }

        if(numDots == 3){
            JLabel waitMessage = new JLabel("WAIT FOR ANOTHER PLAYER...");
            getContentPane().add(waitMessage);
        }

    }

    @Override
    public Socket call() throws Exception {
        createDefaultWindow();
        while(success == false){}
        return Gamesocket;
    }
}
