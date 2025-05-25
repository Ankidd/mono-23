package main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

import java.awt.*;
import java.util.List;
import java.awt.event.*;


import Property.Property;
import Tile_base.*;
import Define.*;
import player.Player;
import manager.GameManager;

public class MainBoard extends JPanel implements Runnable{

    private List<Property> properties;
    private GameManager gameManager;
    private List<Player> players;
    private Timer gameTimer;
    public Thread gameThread;
    private keyHandler KeyH=new keyHandler();


    public MainBoard(List<Property> properties,List<Player> players,GameManager gameManager) {
        this.properties = properties;
        this.players=players;
        this.gameManager=gameManager;
        this.setBackground(Color.WHITE);
        this.addKeyListener(KeyH);
        this.setFocusable(true);
        this.requestFocusInWindow();

         gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameUpdate();
                repaint();
            }
        });
        gameTimer.start();
    }

    public void StartGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){
        while (gameThread!=null){

            gameUpdate();

            repaint();
        }
    }

    public void gameUpdate() {
        Dice dice=new Dice();
        if(KeyH.roll_button){
            dice.roll();
            gameManager.movePlayer(dice.getTotal());
            KeyH.roll_button=false;
        }
        if(KeyH.exit){
            System.exit(0);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard((Graphics2D) g);
    }

    private void drawBoard(Graphics2D g) {
        for (Property prop : properties) {
            Small_Tile SmallTile= new Small_Tile(prop.getColor(),prop.getX(),prop.getY(),prop.getName(),prop.getValue(),null);
            if(prop.getId()>=1 && prop.getId()<=6){
                SmallTile.drawLeftTile(g);
            }
            else if(prop.getId()>=8 && prop.getId()<=20){
                SmallTile.drawUpTile(g);
            }
            else if(prop.getId()>=22 && prop.getId()<=27){
                if(prop.getId()==22){System.out.println(prop.getX()+" "+prop.getY());}
                SmallTile.drawRightTile(g);
            }
            else if(prop.getId()>=29 && prop.getId()<=41){
                SmallTile.drawDownTile(g);
            }
            else if(prop.getId()==0){
                Big_Tile start=new Big_Tile(prop.getX(), prop.getY(), prop.getName(),null);
                start.drawBigTile(g, Define.BIG_TILE_SIZE, Define.BIG_TILE_SIZE, 0);
            }
            else if(prop.getId()==7){
                Big_Tile prison=new Big_Tile(prop.getX(), prop.getY(), prop.getName(),null);
                prison.drawBigTile(g, Define.BIG_TILE_SIZE, Define.BIG_TILE_SIZE, 0);
            }
            else if(prop.getId()==21){
                Big_Tile park=new Big_Tile(prop.getX(), prop.getY(), prop.getName(),null);
                park.drawBigTile(g, Define.BIG_TILE_SIZE, Define.BIG_TILE_SIZE, 0);
            }
            else if(prop.getId()==28){
                Big_Tile visit=new Big_Tile(prop.getX(), prop.getY(), prop.getName(),null);
                visit.drawBigTile(g, Define.BIG_TILE_SIZE, Define.BIG_TILE_SIZE, 0);
            }

            for (int i = 0; i < prop.getLevel(); i++) {
                g.drawImage(image.houseImg, prop.getX()+5+i*18, prop.getY()+5, null);
            }
            // Draw festival
            if (prop.hasFestival()) {
                int posX = prop.getX() + (Define.SMALL_TILE_SIZE_X - image.festivalImg.getWidth()) / 2;
                int posY = prop.getY() + (Define.SMALL_TILE_SIZE_Y - image.festivalImg.getHeight()) / 2 + 5;
                g.drawImage(image.festivalImg, posX, posY, null);
                g.setColor(Color.RED);
            }
            // Draw rent
            // if (prop.isOwned()) {
            //     Point rentPosition = prop.getRentPosition();
            //     g.setColor(Color.BLACK);
            //     g.setFont(new Font("Arial", Font.BOLD, 20));
            //     g.drawString("$" + prop.getRent(), rentPosition.x, rentPosition.y);
            // }
        }
        if(players!=null){
            for(Player player:players){
            player.DrawPlayer(g);}
        }
    }

}

