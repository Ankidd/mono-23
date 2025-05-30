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
    private List<Player> players;

    public MainBoard(List<Property> properties, List<Player> players) {
        this.properties = properties;
        this.players = players;
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard((Graphics2D) g);
    }

    private void drawBoard(Graphics2D g) {
        for (Property prop : properties) {
            Small_Tile SmallTile = new Small_Tile(prop.getColor(), prop.getX(), prop.getY(), prop.getName(), prop.getValue(), null);

            if (prop.getId() >= 1 && prop.getId() <= 6) {
                SmallTile.drawLeftTile(g);
            } else if (prop.getId() >= 8 && prop.getId() <= 20) {
                SmallTile.drawUpTile(g);
            } else if (prop.getId() >= 22 && prop.getId() <= 27) {
                SmallTile.drawRightTile(g);
            } else if (prop.getId() >= 29 && prop.getId() <= 41) {
                SmallTile.drawDownTile(g);
            } else {
                Big_Tile bigTile = new Big_Tile(prop.getX(), prop.getY(), prop.getName(), image.scaleFace1 );
                bigTile.drawBigTile(g, Define.BIG_TILE_SIZE, Define.BIG_TILE_SIZE, 0);
            }

            for (int i = 0; i < prop.getLevel(); i++) {
                g.drawImage(image.houseImg, prop.getX() + 5 + i * 18, prop.getY() + 5, null);
            }

            if (prop.hasFestival()) {
                int posX = prop.getX() + (Define.SMALL_TILE_SIZE_X - image.festivalImg.getWidth()) / 2;
                int posY = prop.getY() + (Define.SMALL_TILE_SIZE_Y - image.festivalImg.getHeight()) / 2 + 5;
                g.drawImage(image.festivalImg, posX, posY, null);
                g.setColor(Color.RED);
            }
        }

        if (players != null) {
            for (Player player : players) {
                player.DrawPlayer(g);
            }
        }
        
    }
}

