package Tile_base;
import Define.Define;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

abstract class Tile {
    protected Color color;
    protected int x, y;
    protected String name;
    protected int price;
    protected BufferedImage img;

    public Tile(Color color, int x, int y, String name, int price, BufferedImage img) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.name = name;
        this.price = price;
        this.img = img;
    }

     public void draw(Graphics2D g, int width, int height, double rotation) {
        BufferedImage tileImage = renderTileImage(width, height);
        drawRotated(g, tileImage, width, height, rotation);
    }

    // Tạo hình ảnh của ô tile
    private BufferedImage renderTileImage(int width, int height) {
        BufferedImage tileSurface = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = tileSurface.createGraphics();;


        drawBackground(g2, width, height);
        drawColorBar(g2, width,height);
        drawImage(g2, width,height);
        drawText(g2, width, height);

        g2.dispose();
        return tileSurface;
    }

    private void drawBackground(Graphics2D g2, int width, int height) {
        g2.setColor(Define.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Define.BLACK);
        g2.drawRect(0, 0, width - 1, height - 1);
    }

    private void drawColorBar(Graphics2D g2, int width,int height) {
        if (color != null) {
            g2.setColor(color);
            g2.fillRect(0, 0, width, 20);
        }
        g2.setColor(Define.BLACK);  // 
        g2.drawRect(0, 0, width - 1, height - 1);
    }

    private void drawImage(Graphics2D g2, int width, int height) {
    if (img != null) {
        int imgSize = 40;
        int imgX = width/2 -imgSize/2;
        int imgY = height/2 - imgSize / 2; 
        g2.drawImage(img.getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH), imgX, imgY, null);
    }
}   

   private void drawText(Graphics2D g2, int width, int height) {
    Font font = new Font("Arial", Font.BOLD, 12);
    g2.setFont(font);
    FontMetrics metrics = g2.getFontMetrics();

    String[] words = name.split(" ");
    StringBuilder line = new StringBuilder();
    int yOffset = height - 20; // Khoảng cách cách đáy một chút

    for (String word : words) {
        String testLine = line.length() == 0 ? word : line + " " + word;
        if (metrics.stringWidth(testLine) <= width - 8) {
            line = new StringBuilder(testLine);
        } else {
            g2.drawString(line.toString(), (width - metrics.stringWidth(line.toString())) / 2, yOffset);
            yOffset += metrics.getHeight();
            line = new StringBuilder(word);
        }
    }

    if (line.length() > 0) {
        g2.drawString(line.toString(), (width - metrics.stringWidth(line.toString())) / 2, yOffset);
    }
    }

    private void drawRotated(Graphics2D g, BufferedImage image, int width, int height, double rotation) {
        if (rotation != 0) {
            AffineTransform transform = new AffineTransform();
            transform.translate(x + width / 2.0, y + height / 2.0);
            transform.rotate(Math.toRadians(rotation));
            transform.translate(-width / 2.0, -height / 2.0);
            g.drawImage(image, transform, null);
        } else {
            g.drawImage(image, x, y, null);
        }
    }
}

