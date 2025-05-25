package Define;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class image {
     public static BufferedImage houseImg;
    public static BufferedImage festivalImg;

    static {
        final String PATH_IMAGE = "assets/images/";

        try {
            // Load ảnh gốc
            BufferedImage houseOriginal = ImageIO.read(new File(PATH_IMAGE + "house.png"));
            BufferedImage festivalOriginal = ImageIO.read(new File(PATH_IMAGE + "festival.png"));

            // Scale và convert lại thành BufferedImage
            houseImg = toBufferedImage(houseOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            festivalImg = toBufferedImage(festivalOriginal.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm chuyển Image → BufferedImage
    private static BufferedImage toBufferedImage(Image img) {
        BufferedImage bimage = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = bimage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return bimage;
    }
}