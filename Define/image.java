package Define;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

public class image {
    public static BufferedImage houseImg;
    public static BufferedImage festivalImg;
    public static BufferedImage chanceIcon;
    public static BufferedImage CommunityChestIcon;
    public static BufferedImage StartPointImg;
    public static BufferedImage PrisonPointImg;
    public static BufferedImage visitPrisonImg;
    public static BufferedImage ParkImg;
    public static BufferedImage Taximg;
    public static BufferedImage MonopolyImg;


    public static BufferedImage scaleFace1;
    public static BufferedImage scaleFace2;
    public static BufferedImage scaleFace3;
    public static BufferedImage scaleFace4;
    public static BufferedImage scaleFace5;
    public static BufferedImage scaleFace6;

    public static Map<String, List<BufferedImage>> spriteImages1 = new HashMap<>();
    public static Map<String, List<BufferedImage>> spriteImages2 = new HashMap<>();
    public static Map<String, List<BufferedImage>> spriteImages3 = new HashMap<>();
    public static Map<String, List<BufferedImage>> spriteImages4 = new HashMap<>();

    static {
        final String PATH_IMAGE = "assets/images/";

        try {
            // Load và scale ảnh nhà, lễ hội
            houseImg = scaleImage(ImageIO.read(new File(PATH_IMAGE + "house.png")), 16, 16);
            festivalImg = scaleImage(ImageIO.read(new File(PATH_IMAGE + "festival.png")), 50, 50);
            chanceIcon = scaleImage(ImageIO.read(new File(PATH_IMAGE + "monopoly_chance_.png")), 50, 50);
            CommunityChestIcon = scaleImage(ImageIO.read(new File(PATH_IMAGE + "monopoly_community_chest_.png")), 50, 50);
            StartPointImg = scaleImage(ImageIO.read(new File(PATH_IMAGE + "start_point_.png")), 50, 50);
            PrisonPointImg = scaleImage(ImageIO.read(new File(PATH_IMAGE + "monopoly_go_to_jail_point_.png")), 50, 50);
            visitPrisonImg = scaleImage(ImageIO.read(new File(PATH_IMAGE + "monopoly_prison_point_.png")), 50, 50);
            ParkImg = scaleImage(ImageIO.read(new File(PATH_IMAGE + "monopoly_free_parking_point_.png")), 50, 50);
            Taximg = scaleImage(ImageIO.read(new File(PATH_IMAGE + "monopoly_tax_ring_.png")), 50, 50);
            MonopolyImg = scaleImage(ImageIO.read(new File(PATH_IMAGE + "icon1.png")), 50, 50);
            
            

            // Load và scale xúc xắc
            scaleFace1 = scaleImage(ImageIO.read(new File(PATH_IMAGE + "face1.png")), 50, 50);
            scaleFace2 = scaleImage(ImageIO.read(new File(PATH_IMAGE + "face2.png")), 50, 50);
            scaleFace3 = scaleImage(ImageIO.read(new File(PATH_IMAGE + "face3.png")), 50, 50);
            scaleFace4 = scaleImage(ImageIO.read(new File(PATH_IMAGE + "face4.png")), 50, 50);
            scaleFace5 = scaleImage(ImageIO.read(new File(PATH_IMAGE + "face5.png")), 50, 50);
            scaleFace6 = scaleImage(ImageIO.read(new File(PATH_IMAGE + "face6.png")), 50, 50);

            // Load sprite animation cho Player 1
            spriteImages1.put("up", loadFrames(PATH_IMAGE,
                "Idle1_up.png", "up1_1.png", "up1_2.png", "up1_3.png",
                "up1_4.png", "up1_5.png", "up1_6.png", "up1_7.png", "up1_8.png"
            ));

            spriteImages1.put("right", loadFrames(PATH_IMAGE,
                "Idle1_right.png", "right1_1.png", "right1_2.png", "right1_3.png",
                "right1_4.png", "right1_5.png", "right1_6.png", "right1_7.png", "right1_8.png"
            ));

            spriteImages1.put("down", loadFrames(PATH_IMAGE,
                "Idle1_down.png", "down1_1.png", "down1_2.png", "down1_3.png",
                "down1_4.png", "down1_5.png", "down1_6.png", "down1_7.png", "down1_8.png"
            ));

            spriteImages1.put("left", loadFrames(PATH_IMAGE,
                "Idle1_left.png", "left1_1.png", "left1_2.png", "left1_3.png",
                "left1_4.png", "left1_5.png", "left1_6.png", "left1_7.png", "left1_8.png"
            ));


            spriteImages2.put("up", loadFrames(PATH_IMAGE,
                "Idle2_up.png", "up2_1.png", "up2_2.png", "up2_3.png",
                "up1_4.png", "up2_5.png", "up2_6.png", "up2_7.png", "up2_8.png"
            ));

            spriteImages2.put("right", loadFrames(PATH_IMAGE,
                "Idle2_right.png", "right2_1.png", "right2_2.png", "right2_3.png",
                "right2_4.png", "right2_5.png", "right2_6.png", "right2_7.png", "right2_8.png"
            ));

            spriteImages2.put("down", loadFrames(PATH_IMAGE,
                "Idle2_down.png", "down2_1.png", "down2_2.png", "down2_3.png",
                "down2_4.png", "down2_5.png", "down2_6.png", "down2_7.png", "down2_8.png"
            ));

            spriteImages2.put("left", loadFrames(PATH_IMAGE,
                "Idle2_left.png", "left2_1.png", "left2_2.png", "left2_3.png",
                "left2_4.png", "left2_5.png", "left2_6.png", "left2_7.png", "left2_8.png"
            ));

            spriteImages3.put("up", loadFrames(PATH_IMAGE,
                "Idle3_up.png", "up3_1.png", "up3_2.png", "up3_3.png",
                "up3_4.png", "up3_5.png", "up3_6.png", "up3_7.png", "up3_8.png"
            ));

            spriteImages3.put("right", loadFrames(PATH_IMAGE,
                "Idle3_right.png", "right3_1.png", "right3_2.png", "right3_3.png",
                "right3_4.png", "right3_5.png", "right3_6.png", "right3_7.png", "right3_8.png"
            ));

            spriteImages3.put("down", loadFrames(PATH_IMAGE,
                "Idle3_down.png", "down3_1.png", "down3_2.png", "down3_3.png",
                "down3_4.png", "down3_5.png", "down3_6.png", "down3_7.png", "down3_8.png"
            ));

            spriteImages3.put("left", loadFrames(PATH_IMAGE,
                "Idle3_left.png", "left3_1.png", "left3_2.png", "left3_3.png",
                "left3_4.png", "left3_5.png", "left3_6.png", "left3_7.png", "left3_8.png"
            ));

            spriteImages4.put("up", loadFrames(PATH_IMAGE,
                "Idle4_up.png", "up4_1.png", "up4_2.png", "up4_3.png",
                "up4_4.png", "up4_5.png", "up4_6.png", "up4_7.png", "up4_8.png"
            ));

            spriteImages4.put("right", loadFrames(PATH_IMAGE,
                "Idle4_right.png", "right4_1.png", "right4_2.png", "right4_3.png",
                "right4_4.png", "right4_5.png", "right4_6.png", "right4_7.png", "right4_8.png"
            ));

            spriteImages4.put("down", loadFrames(PATH_IMAGE,
                "Idle4_down.png", "down4_1.png", "down4_2.png", "down4_3.png",
                "down4_4.png", "down4_5.png", "down4_6.png", "down4_7.png", "down4_8.png"
            ));

            spriteImages4.put("left", loadFrames(PATH_IMAGE,
                "Idle4_left.png", "left4_1.png", "left4_2.png", "left4_3.png",
                "left4_4.png", "left4_5.png", "left4_6.png", "left4_7.png", "left4_8.png"
            ));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ Hàm scale + convert sang BufferedImage
    private static BufferedImage scaleImage(Image img, int width, int height) {
        return toBufferedImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    // ✅ Hàm convert Image → BufferedImage
    private static BufferedImage toBufferedImage(Image img) {
        BufferedImage bimage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bimage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return bimage;
    }

    // ✅ Hàm load nhiều ảnh từ tên file
    private static List<BufferedImage> loadFrames(String basePath, String... fileNames) throws IOException {
        List<BufferedImage> frames = new ArrayList<>();
        for (int i = 0; i < fileNames.length; i++) {
            frames.add(scaleImage(ImageIO.read(new File(basePath + fileNames[i])), 50, 50));
        }
        return frames;
    }

    // ✅ Trả về danh sách xúc xắc
    public static List<BufferedImage> diceList() {
        return Arrays.asList(scaleFace1, scaleFace2, scaleFace3, scaleFace4, scaleFace5, scaleFace6);
    }

    public static BufferedImage getScaledImage(BufferedImage original, int width, int height) {
         BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();

        // Chống mờ ảnh
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();
        return resized;
    }
}
