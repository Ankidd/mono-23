package Tile_base;
import Define.Define;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Special_Tile extends Tile{
    public Special_Tile(int x, int y, String name, BufferedImage img) {
        super(Define.WHITE, x, y, name, 0, img);
    }

    public void drawUpSpecialTile(Graphics2D g) {
        draw(g, Define.SMALL_TILE_SIZE_X, Define.SMALL_TILE_SIZE_Y, 180);
    }

    public void drawDownSpecialTile(Graphics2D g) {
        draw(g, Define.SMALL_TILE_SIZE_X, Define.SMALL_TILE_SIZE_Y, 0);
    }

    public void drawRightSpecialTile(Graphics2D g) {
        draw(g, Define.SMALL_TILE_SIZE_X, Define.SMALL_TILE_SIZE_Y, 90);
    }

    public void drawLeftSpecialTile(Graphics2D g) {
        draw(g, Define.SMALL_TILE_SIZE_X, Define.SMALL_TILE_SIZE_Y, 270);
    }
}
