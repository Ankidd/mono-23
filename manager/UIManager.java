package manager;

import java.util.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import Property.Property;
import main.MainBoard;
import player.Player;
import Define.ActionMenuPanel;
import Define.Define;
import Define.Dice;


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
    private JFrame frame;

    public UIManager(JPanel panel, Image image, Rectangle imageRect, List<Property> properties, List<Player> players,JFrame frame) {
        this.panel = panel;
        this.backgroundImage = image;
        this.imageRect = imageRect;
        this.properties = properties;
        this.players = players;
        // this.gameManager = gameManager;
        this.showMenu=false;
        this.startTime = System.currentTimeMillis();
        this.frame=frame;
    }

    // public void showSellMenu(Graphics g, Player player) {
    //     sellButtons.clear();
    //     Font font = new Font("Arial", Font.PLAIN, 18);
    //     g.setFont(font);

    //     String title = player.getName() + ", choose a property to sell";
    //     List<String> wrappedTitle = wrapText(title, font, 290, g);

    //     int titleHeight = wrappedTitle.size() * 45;
    //     int numProps = player.getOwnedProperties().size();
    //     int menuHeight = 50 + titleHeight + numProps * 60 + 60;
    //     Rectangle menuBg = new Rectangle(Define.WIDTH / 2 - 200, Define.HEIGHT / 2 - menuHeight / 2, 400, menuHeight);

    //     g.setColor(Color.GREEN);
    //     g.fillRoundRect(menuBg.x, menuBg.y, menuBg.width, menuBg.height, 10, 10);
    //     g.setColor(Color.BLACK);
    //     g.drawRoundRect(menuBg.x, menuBg.y, menuBg.width, menuBg.height, 10, 10);

    //     for (int i = 0; i < wrappedTitle.size(); i++) {
    //         g.drawString(wrappedTitle.get(i), menuBg.x + 20, menuBg.y + 20 + i * 45);
    //     }

    //     int startY = menuBg.y + 30 + titleHeight;
    //     for (Property prop : player.getOwnedProperties()) {
    //         Rectangle propRect = new Rectangle(menuBg.x + 20, startY, 360, 45);
    //         g.setColor(Color.WHITE);
    //         g.fillRoundRect(propRect.x, propRect.y, propRect.width, propRect.height, 6, 6);
    //         g.setColor(Color.BLACK);
    //         g.drawRoundRect(propRect.x, propRect.y, propRect.width, propRect.height, 6, 6);

    //         String name = prop.getName() + " - Sell for $" + (prop.getValue() / 2);
    //         g.drawString(name, propRect.x + 10, propRect.y + 30);

    //         sellButtons.add(new SellButton(propRect, prop));
    //         startY += 60;
    //     }

        

    //     Rectangle doneRect = new Rectangle(menuBg.x + menuBg.width / 2 - 60, menuBg.y + menuHeight - 55, 120, 40);
    //     g.setColor(Color.GREEN);
    //     g.fillRoundRect(doneRect.x, doneRect.y, doneRect.width, doneRect.height, 8, 8);
    //     g.setColor(Color.BLACK);
    //     g.drawRoundRect(doneRect.x, doneRect.y, doneRect.width, doneRect.height, 8, 8);
    //     g.drawString("DONE", doneRect.x + 30, doneRect.y + 28);
    //     sellButtons.add(new SellButton(doneRect, "DONE"));
    // }
    public void drawDicePanel(){

    }

    public void drawBuyPropertyMenu(Player player, Property property) {
        int choice = JOptionPane.showConfirmDialog(
            frame,
            "Do you want to buy " + property.getName() + " for $" + property.getValue() + "?",
            "Buy Property",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            if(player.getMoney()>=property.getValue()){
                player.chargeMoney(property.getValue());
                player.addProperty(property);
                JOptionPane.showMessageDialog(frame, "You bought " + property.getName() + "!");}
            else{
                JOptionPane.showMessageDialog(frame,"you do not have enough money to buy "+ property.getName());
            }
        } else {
            JOptionPane.showMessageDialog(frame, "You chose not to buy " + property.getName() + ".");
        }

    }

    public void drawUpgradeMenu(Player player, Property property) {
    if (property.getLevel() >= 5) {
        JOptionPane.showMessageDialog(frame, "You have reached the maximum level.");
        return;
    }

    String title = "Upgrade " + property.getName();
    String desc = "Level " + property.getLevel() + " → " + (property.getLevel() + 1)
                + " | Cost: $" + property.getUpgradeCost();

    ActionMenuPanel panel = new ActionMenuPanel(
        frame,
        title,
        desc,
        "Upgrade",
        e -> {
            if (player.getMoney() >= property.getUpgradeCost()) {
                player.chargeMoney(property.getUpgradeCost());
                property.setLevel(property.getLevel() + 1);
                JOptionPane.showMessageDialog(frame, "Upgraded to level " + property.getLevel());
            } else {
                JOptionPane.showMessageDialog(frame, "Not enough money to upgrade.");
            }
        }
    );

    panel.showDialog(frame, "Upgrade Property");
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
