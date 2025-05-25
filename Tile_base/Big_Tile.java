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
        BufferedImage tileSurface = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = tileSurface.createGraphics();

        g2.setColor(Define.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Define.BLACK);
        g2.drawRect(0, 0, width - 1, height - 1);

        // Content rendering
        BufferedImage content = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gc = content.createGraphics();
        gc.setFont(new Font("Arial", Font.PLAIN, 16));
        FontMetrics metrics = gc.getFontMetrics();

        String[] words = name.split(" ");
        StringBuilder line = new StringBuilder();
        int yOffset = 30;

        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            if (metrics.stringWidth(testLine) <= width) {
                line = new StringBuilder(testLine);
            } else {
                gc.drawString(line.toString(), (width - metrics.stringWidth(line.toString())) / 2, yOffset);
                yOffset += metrics.getHeight();
                line = new StringBuilder(word);
            }
        }
        gc.drawString(line.toString(), (width - metrics.stringWidth(line.toString())) / 2, yOffset);

        // Draw image
        if (img != null) {
            Image newImg = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            gc.drawImage(newImg, (width - 70) / 2, yOffset + 5, null);
        }
        gc.dispose();

        // Rotate content
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(rotation), width / 2.0, height / 2.0);
        Graphics2D gRot = (Graphics2D) g.create();
        gRot.translate(x, y);
        gRot.setTransform(at);
        gRot.drawImage(content, 0, 0, null);
        gRot.dispose();

        // Final blit
        g.drawImage(tileSurface, x, y, null);
    }
}
