package manager;

import java.util.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import Property.Property;
import player.Player;
import Define.Define;

public class UIManager {
    private JPanel panel;
    private Image backgroundImage;
    private Rectangle imageRect;
    private List<Property> properties;
    private List<Player> players;
    private GameManager gameManager;

    private Rectangle buyButton, skipButton, upgradeButton, skipUpgradeButton;
    private Property selectedProperty;
    private Player currentPlayer;
    private boolean showMenu = false;
    private boolean upgradeMenu = false;
    private boolean infoMode = false;
    private int scrollOffset = 0;
    private final int SCROLL_STEP = 30;

    private Rectangle infoButton = new Rectangle(Define.WIDTH - 255, Define.HEIGHT - 150, 160, 50);
    private Rectangle closeButton = new Rectangle(Define.WIDTH / 2 + 165, (int)(Define.HEIGHT / 3.5) + 10, 50, 40);

    private long startTime;
    private final int countdownDuration = 60 * 60 * 1000;

    private boolean sellMode = false;
    private Player sellPlayer = null;
    private int sellTargetMoney = 0;
    private List<SellButton> sellButtons = new ArrayList<>();

    public UIManager(JPanel panel, Image image, Rectangle imageRect, List<Property> properties, List<Player> players, GameManager gameManager) {
        this.panel = panel;
        this.backgroundImage = image;
        this.imageRect = imageRect;
        this.properties = properties;
        this.players = players;
        this.gameManager = gameManager;
        this.startTime = System.currentTimeMillis();
    }

    public void showSellMenu(Graphics g, Player player) {
        sellButtons.clear();
        Font font = new Font("Arial", Font.PLAIN, 18);
        g.setFont(font);

        String title = player.getName() + ", choose a property to sell";
        List<String> wrappedTitle = wrapText(title, font, 290, g);

        int titleHeight = wrappedTitle.size() * 45;
        int numProps = player.getOwnedProperties().size();
        int menuHeight = 50 + titleHeight + numProps * 60 + 60;
        Rectangle menuBg = new Rectangle(Define.WIDTH / 2 - 200, Define.HEIGHT / 2 - menuHeight / 2, 400, menuHeight);

        g.setColor(Color.GREEN);
        g.fillRoundRect(menuBg.x, menuBg.y, menuBg.width, menuBg.height, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(menuBg.x, menuBg.y, menuBg.width, menuBg.height, 10, 10);

        for (int i = 0; i < wrappedTitle.size(); i++) {
            g.drawString(wrappedTitle.get(i), menuBg.x + 20, menuBg.y + 20 + i * 45);
        }

        int startY = menuBg.y + 30 + titleHeight;
        for (Property prop : player.getOwnedProperties()) {
            Rectangle propRect = new Rectangle(menuBg.x + 20, startY, 360, 45);
            g.setColor(Color.WHITE);
            g.fillRoundRect(propRect.x, propRect.y, propRect.width, propRect.height, 6, 6);
            g.setColor(Color.BLACK);
            g.drawRoundRect(propRect.x, propRect.y, propRect.width, propRect.height, 6, 6);

            String name = prop.getName() + " - Sell for $" + (prop.getValue() / 2);
            g.drawString(name, propRect.x + 10, propRect.y + 30);

            sellButtons.add(new SellButton(propRect, prop));
            startY += 60;
        }

        Rectangle doneRect = new Rectangle(menuBg.x + menuBg.width / 2 - 60, menuBg.y + menuHeight - 55, 120, 40);
        g.setColor(Color.GREEN);
        g.fillRoundRect(doneRect.x, doneRect.y, doneRect.width, doneRect.height, 8, 8);
        g.setColor(Color.BLACK);
        g.drawRoundRect(doneRect.x, doneRect.y, doneRect.width, doneRect.height, 8, 8);
        g.drawString("DONE", doneRect.x + 30, doneRect.y + 28);
        sellButtons.add(new SellButton(doneRect, "DONE"));
    }

    public void drawCountdownTimer(Graphics g) {
        long currentTime = System.currentTimeMillis();
        long timeLeft = Math.max(0, countdownDuration - (currentTime - startTime));
        int minutes = (int)(timeLeft / 60000);
        int seconds = (int)((timeLeft % 60000) / 1000);
        String timerText = String.format("%02d:%02d", minutes, seconds);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
        g.drawString(" " + timerText, 150, 150);
    }

    public void showMessage(Graphics g, String text, int delay) {
        Font font = new Font("Arial", Font.BOLD, 40);
        FontMetrics fm = g.getFontMetrics(font);
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        Rectangle rect = new Rectangle(Define.WIDTH / 2 - width / 2, Define.HEIGHT / 2 - height / 2, width, height);

        Rectangle bg = new Rectangle(rect.x - 30, rect.y - 30, rect.width + 60, rect.height + 60);

        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < delay) {
            g.setColor(Color.GREEN);
            g.fillRoundRect(bg.x, bg.y, bg.width, bg.height, 20, 20);
            g.setColor(Color.BLACK);
            g.drawRoundRect(bg.x, bg.y, bg.width, bg.height, 20, 20);
            g.setFont(font);
            g.drawString(text, rect.x, rect.y + height - 10);
            panel.repaint();
            try {
                Thread.sleep(40);
            } catch (InterruptedException ignored) {}
        }
    }

    // Helper method to wrap text within maxWidth
    private List<String> wrapText(String text, Font font, int maxWidth, Graphics g) {
        List<String> lines = new ArrayList<>();
        FontMetrics fm = g.getFontMetrics(font);
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String testLine = line + word + " ";
            if (fm.stringWidth(testLine) > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word + " ");
            } else {
                line.append(word).append(" ");
            }
        }
        lines.add(line.toString());
        return lines;
    }

    // You can implement showPlayerInfo, showPurchaseMenu, showUpgradeMenu, etc. similarly
    // following the pattern of rendering UI elements using Graphics.

    // Define inner helper class to hold sell buttons
    static class SellButton {
        Rectangle rect;
        Object target;  // Either Property or "DONE"

        SellButton(Rectangle rect, Object target) {
            this.rect = rect;
            this.target = target;
        }
    }
    public void setSellMode(boolean bool){
        this.sellMode=bool;
    }

    public void setSellPlayer(Player player){
        this.sellPlayer=player;
    }

    public void setSellTargetMoney(int amount){
        this.sellTargetMoney=amount;
    }
}
