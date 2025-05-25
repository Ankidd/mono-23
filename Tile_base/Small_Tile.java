package Tile_base;
import Define.Define;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Small_Tile extends Tile{
      public Small_Tile(Color color, int x, int y, String name, int price,BufferedImage img) {
        super(color, x, y, name, price, img);
    }

    public void drawDownTile(Graphics2D g) {
        draw(g, Define.SMALL_TILE_SIZE_X, Define.SMALL_TILE_SIZE_Y, 0);
    }

    public void drawUpTile(Graphics2D g) {
        draw(g, Define.SMALL_TILE_SIZE_X, Define.SMALL_TILE_SIZE_Y, 180);
    }

    public void drawRightTile(Graphics2D g) {
        draw(g, Define.SMALL_TILE_SIZE_X, Define.SMALL_TILE_SIZE_Y, 270);
    }

    public void drawLeftTile(Graphics2D g) {
        draw(g, Define.SMALL_TILE_SIZE_X, Define.SMALL_TILE_SIZE_Y,90);
    }
}
