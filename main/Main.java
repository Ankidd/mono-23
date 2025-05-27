package main;

import javax.swing.JFrame;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Define.*;
import Property.Property;
import manager.GameManager;
import player.Player;
import manager.UIManager;



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
        MainBoard board = new MainBoard(properties, players);

        UIManager uiManager=new UIManager(null,null,null,properties,players,frame);
        GameManager gameManager = new GameManager(players, properties,uiManager,board);

        
        GamePanel gamePanel = new GamePanel(properties, players, gameManager, uiManager);
       
        frame.add(gamePanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGameThread(); 
    }
}
