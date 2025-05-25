package main;

import javax.swing.JFrame;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Define.*;
import Property.Property;
import manager.GameManager;
import player.Player;



public class Main{
    public static void main(String[] args) {
        System.out.println(Define.WIDTH+" "+Define.HEIGHT);
        JFrame frame = new JFrame("Monopoly Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Define.WIDTH, Define.HEIGHT); 
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);
        List<Property> properties = Property.createProperties();
        List<Player> players = new ArrayList<>();
        players.add(new Player("An", 1, Color.RED, properties, 0));
        GameManager gameManager = new GameManager(players, properties);

        
        MainBoard board = new MainBoard(properties, players, gameManager);
        frame.add(board);



        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}
