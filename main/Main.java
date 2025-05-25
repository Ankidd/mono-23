package main;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

import Define.*;
import Property.Property;
import player.Player;

public class Main {
    public static void main(String[] args) {
        System.out.println(Define.WIDTH+" "+Define.HEIGHT);
        JFrame frame = new JFrame("Monopoly Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Define.WIDTH, Define.HEIGHT); 
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);
        frame.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);  
            }
        }
        });


        // Tạo MainBoard và thêm vào frame
        MainBoard board = new MainBoard(Property.createProperties());
        frame.add(board);

        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}
