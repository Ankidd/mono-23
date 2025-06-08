package Define;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
public class Define {
    public static final Color WHITE = Color.WHITE;
    public static final Color BLACK = Color.BLACK;

    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WIDTH = screenSize.width;
    public static final int HEIGHT = screenSize.height;
    public static final int PlayerRadius=15;

    public static final int BIG_TILE_SIZE = 150;
    public static final int NUM_VERTICAL_TILES_UP = 7;
    public static final int NUM_HORIZONTAL_TILES_RIGHT = 14;
    public static final int NUM_VERTICAL_TILES_DOWN = 7;
    public static final int NUM_HORIZONTAL_TILES_LEFT = 14;
    public static final int SMALL_TILE_SIZE_X=95;
    public static final int SMALL_TILE_SIZE_Y=130;

    public static final Color PASTEL_PINK       = Color.decode("#FFD1DC"); 
    public static final Color PASTEL_ORANGE     = Color.decode("#FFD8B1");
    public static final Color PASTEL_YELLOW     = Color.decode("#FFFFB3");
    public static final Color LAVENDER_MIST     = Color.decode("#E6E6FA");
    public static final Color LIGHT_PEACH       = Color.decode("#FFE5B4");
    public static final Color PASTEL_GREEN      = Color.decode("#77DD77");
    public static final Color PASTEL_BLUE       = Color.decode("#AEC6CF");
    public static final Color PALE_AQUA         = Color.decode("#BCD4E6");
    public static final Color PASTEL_PURPLE     = Color.decode("#CBAACB");
    public static final Color LIGHT_CORAL       = Color.decode("#F08080");
    public static final Color LIGHT_PERIWINKLE  = Color.decode("#C5CBE1");
    public static final Color PASTEL_BEIGE      = Color.decode("#F5F5DC");
    public static final Color MINT_CREAM        = Color.decode("#F5FFFA");
    public static final Color PASTEL_MINT       = Color.decode("#AAF0D1");

    public static final Map<Color, Integer> colorSetSizeMap = new HashMap<>();
    static {
        colorSetSizeMap.put(Define.PASTEL_ORANGE, 3);
        colorSetSizeMap.put(Define.PASTEL_BLUE, 3);
        colorSetSizeMap.put(Define.PASTEL_PURPLE, 3);
        colorSetSizeMap.put(Define.LIGHT_PERIWINKLE, 3);
        colorSetSizeMap.put(Define.PASTEL_MINT, 3);
        
        colorSetSizeMap.put(Define.PASTEL_PINK, 2);
        colorSetSizeMap.put(Define.PASTEL_YELLOW, 2);
        colorSetSizeMap.put(Define.LAVENDER_MIST, 2);
        colorSetSizeMap.put(Define.LIGHT_PEACH, 2);
        colorSetSizeMap.put(Define.LIGHT_CORAL, 2);
        colorSetSizeMap.put(Define.PASTEL_GREEN, 2);
        colorSetSizeMap.put(Define.PALE_AQUA, 2);
        colorSetSizeMap.put(Define.PASTEL_BEIGE, 2);
        colorSetSizeMap.put(Define.MINT_CREAM, 2);
    }
    
    public static int getSetSizeForColor(Color color) {
        return colorSetSizeMap.getOrDefault(color, 3);
    }
}
