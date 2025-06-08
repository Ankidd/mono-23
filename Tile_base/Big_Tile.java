package Tile_base;
import Define.Define;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
public class Big_Tile extends Tile{
    public Big_Tile(int x, int y, String name, BufferedImage img) {
        super(null, x, y, name, 0, img);
    }

     public void drawBigTile(Graphics2D g, int width, int height, double rotation) {
        BufferedImage tileImage = renderTileImage(width, height);
        drawRotated(g, tileImage, width, height, rotation);
    }

    // Tạo hình ảnh của ô tile
    private BufferedImage renderTileImage(int width, int height) {
        BufferedImage contentSurface = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gContent = contentSurface.createGraphics();

        drawImage(gContent, width, height);
        drawText(gContent, width, height);

        gContent.dispose();
        return contentSurface;
    }

    private void drawBackground(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(Define.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(Define.BLACK);
        g.drawRect(x, y, width - 1, height - 1);
    }


    private void drawImage(Graphics2D g, int width, int height) {
    if (img != null) {
        int imgSize = 80;
        int imgX = width/2 -imgSize/2;
        int imgY = (height/ 2) - imgSize / 2-10; 
        g.drawImage(img.getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH), imgX, imgY, null);
    }
}   

   private void drawText(Graphics2D g, int width, int height) {
    g.setColor(Define.BLACK);
    Font font = new Font("Arial", Font.BOLD, 25);
    g.setFont(font);
    FontMetrics metrics = g.getFontMetrics();

    String[] words = name.split(" ");
    StringBuilder line = new StringBuilder();
    int yOffset = height - 20; // Khoảng cách cách đáy một chút

    for (String word : words) {
        String testLine = line.length() == 0 ? word : line + " " + word;
        if (metrics.stringWidth(testLine) <= width - 8) {
            line = new StringBuilder(testLine);
        } else {
            g.drawString(line.toString(), (width - metrics.stringWidth(line.toString())) / 2, yOffset);
            yOffset += metrics.getHeight();
            line = new StringBuilder(word);
        }
    }

    if (line.length() > 0) {
        g.drawString(line.toString(), (width - metrics.stringWidth(line.toString())) / 2, yOffset);
    }
    }

    private void drawRotated(Graphics2D g, BufferedImage contentImage, int width, int height, double rotation) {
        // Bước 1: Vẽ viền ngoài và nền KHÔNG XOAY
        drawBackground(g, x, y, width, height);

        // Bước 2: Xoay nội dung (hình + text)
        if (rotation != 0) {
            AffineTransform transform = new AffineTransform();
            transform.translate(x + width / 2.0, y + height / 2.0); // tâm
            transform.rotate(Math.toRadians(rotation));
            transform.translate(-width / 2.0, -height / 2.0);
            g.drawImage(contentImage, transform, null);
        } else {
            g.drawImage(contentImage, x, y, null);
        }
    }
}
