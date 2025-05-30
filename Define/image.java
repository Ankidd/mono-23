package Define;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class image {
    public static BufferedImage houseImg;
    public static BufferedImage festivalImg;
    public static BufferedImage scaleFace1;
    public static BufferedImage scaleFace2;
    public static BufferedImage scaleFace3;
    public static BufferedImage scaleFace4;
    public static BufferedImage scaleFace5;
    public static BufferedImage scaleFace6;

    static {
        final String PATH_IMAGE = "assets/images/";

        try {
            // Load ảnh gốc
            BufferedImage houseOriginal = ImageIO.read(new File(PATH_IMAGE + "house.png"));
            BufferedImage festivalOriginal = ImageIO.read(new File(PATH_IMAGE + "festival.png"));

            BufferedImage diceface1 = ImageIO.read(new File(PATH_IMAGE + "face1.png"));
            BufferedImage diceface2 = ImageIO.read(new File(PATH_IMAGE + "face2.png"));
            BufferedImage diceface3 = ImageIO.read(new File(PATH_IMAGE + "face3.png"));
            BufferedImage diceface4 = ImageIO.read(new File(PATH_IMAGE + "face4.png"));
            BufferedImage diceface5 = ImageIO.read(new File(PATH_IMAGE + "face5.png"));
            BufferedImage diceface6 = ImageIO.read(new File(PATH_IMAGE + "face6.png"));
            // Scale và convert lại thành BufferedImage
            houseImg = toBufferedImage(houseOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            festivalImg = toBufferedImage(festivalOriginal.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

            scaleFace1 = toBufferedImage(diceface1.getScaledInstance(50,50,Image.SCALE_SMOOTH));
            scaleFace2 = toBufferedImage(diceface2.getScaledInstance(50,50,Image.SCALE_SMOOTH));
            scaleFace3 = toBufferedImage(diceface3.getScaledInstance(50,50,Image.SCALE_SMOOTH));
            scaleFace4 = toBufferedImage(diceface4.getScaledInstance(50,50,Image.SCALE_SMOOTH));
            scaleFace5 = toBufferedImage(diceface5.getScaledInstance(50,50,Image.SCALE_SMOOTH));
            scaleFace6 = toBufferedImage(diceface6.getScaledInstance(50,50,Image.SCALE_SMOOTH));
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

    public static List<BufferedImage> diceList(){
        List<BufferedImage> diceList=new ArrayList<>();
        diceList.add(scaleFace1);
        diceList.add(scaleFace2);
        diceList.add(scaleFace3);
        diceList.add(scaleFace4);
        diceList.add(scaleFace5);
        diceList.add(scaleFace6);
        return diceList;
    }
}