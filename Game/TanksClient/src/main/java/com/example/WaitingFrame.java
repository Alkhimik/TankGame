package com.example;

import javax.swing.*;
import java.awt.*;

public class WaitingFrame extends JFrame implements Runnable{

    @Override
    public void run() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel waitMessage = new JLabel("                                       WAIT FOR ANOTHER PLAYER");
        getContentPane().setBackground(Color.DARK_GRAY);
        waitMessage.setForeground(Color.green);
        getContentPane().add(waitMessage);
        setSize(new Dimension(500,300));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        }

}
