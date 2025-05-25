package main;

import javax.swing.*;
import java.awt.*;
import java.util.List;


import Property.Property;
import Tile_base.*;
import Define.*;
import player.Player;


public class MainBoard extends JPanel {

    private List<Property> properties;
    private Player player;
    // private List<Player> players;
    // private BufferedImage houseImage;
    // private BufferedImage festivalImage;

    public MainBoard(List<Property> properties,Player player) {
        this.properties = properties;
        this.player=player;
        // this.players = players;
        // loadImages();
         this.setBackground(Color.WHITE);
    }

    // private void loadImages() {
    //     try {
    //         houseImage = ImageIO.read(getClass().getResource("/images/house.png"));
    //         festivalImage = ImageIO.read(getClass().getResource("/images/festival.png"));
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard((Graphics2D) g);
    }

    private void drawBoard(Graphics2D g) {
        for (Property prop : properties) {
            Small_Tile SmallTile= new Small_Tile(prop.getColor(),prop.getX(),prop.getY(),prop.getName(),prop.getValue());
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

            Big_Tile Start=new Big_Tile(0, 0,"START",null);
            Start.drawBigTile(g,Define.BIG_TILE_SIZE , Define.BIG_TILE_SIZE, 135);

            Big_Tile prison=new Big_Tile(Define.WIDTH-Define.BIG_TILE_SIZE, 0,"PRISON",null);
            prison.drawBigTile(g,Define.BIG_TILE_SIZE , Define.BIG_TILE_SIZE, 135);

            Big_Tile park=new Big_Tile(Define.WIDTH-Define.BIG_TILE_SIZE, Define.HEIGHT-Define.BIG_TILE_SIZE,"PARK",null);
            park.drawBigTile(g,Define.BIG_TILE_SIZE , Define.BIG_TILE_SIZE, 135);
            
            Big_Tile visit=new Big_Tile(0, Define.HEIGHT-Define.BIG_TILE_SIZE,"VISIT",null);
            visit.drawBigTile(g,Define.BIG_TILE_SIZE , Define.BIG_TILE_SIZE, 135);

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
        if(player!=null){
            player.DrawPlayer(g);
        }
    }
}

