package com.example;

import javax.swing.*;
import java.awt.*;

public class EndScreen extends JFrame {
    public EndScreen(int total_shots, int hits, int missed, String status){
        JLabel endStatus = new JLabel("   Winner!");
        if(status.equals("Winner!")){
            endStatus.setForeground(Color.green);
        } else{
            endStatus.setText("   Loser");
            endStatus.setForeground(Color.red);
        }

        JLabel statistics = new JLabel("total_shots     hits     missed");
        JLabel data = new JLabel("   " +total_shots + "                    " + hits + "            " + missed);
        getContentPane().add(endStatus);
        getContentPane().add(statistics);
        getContentPane().add(data);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,1));
        setSize(new Dimension(225,140));

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
