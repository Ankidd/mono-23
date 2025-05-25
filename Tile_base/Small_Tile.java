package Tile_base;
import Define.Define;
import java.awt.*;


public class Small_Tile extends Tile{
      public Small_Tile(Color color, int x, int y, String name, int price) {
        super(color, x, y, name, price, null);
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
