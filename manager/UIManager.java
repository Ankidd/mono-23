package manager;

import java.util.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import Property.Property;
import main.MainBoard;
import player.Player;
import Define.ActionMenuPanel;
import Define.DecisionMenuPanel;
import Define.Define;
import Define.Dice;
import Define.DecisionMenuPanel;


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
    public void showSellpropertyMenu(Player player) {
        List<Property> owned = player.getOwnedProperties();
        if (owned.isEmpty()) {
            JOptionPane.showMessageDialog(frame, player.getName() + " has no properties to sell!");
            return;
        }

        // Tạo combobox chọn property
        String[] propertyOptions = new String[owned.size()];
        for (int i = 0; i < owned.size(); i++) {
            Property p = owned.get(i);
            propertyOptions[i] = p.getName() + " (Sell for $" + (p.getValue() / 2) + ")";
        }

        JComboBox<String> comboBox = new JComboBox<>(propertyOptions);
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JLabel("Select a property to sell:"), BorderLayout.NORTH);
        contentPanel.add(comboBox, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
            frame,
            contentPanel,
            "Sell Property",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION && comboBox.getSelectedIndex() >= 0) {
            Property selected = owned.get(comboBox.getSelectedIndex());
            int sellPrice = selected.getValue() / 2;

            String title = "Sell Property: " + selected.getName();
            String description = "Do you want to sell " + selected.getName() + " for $" + sellPrice + "?";

            DecisionMenuPanel panel = new DecisionMenuPanel(
                frame,
                title,
                description,
                "Sell",
                "Cancel",
                e -> {
                    player.addMoney(sellPrice);
                    player.getOwnedProperties().remove(selected);
                    selected.setOwned(false);
                    selected.setOwner(null);

                    JOptionPane.showMessageDialog(frame, "You sold " + selected.getName() + " for $" + sellPrice + ".");

                    // Nếu cần ép bán tiếp
                    if (player.getMoney() < sellTargetMoney && sellMode && player.getOwnedProperties().size() > 0) {
                        showSellpropertyMenu(player);
                    }
                },
                e -> {
                    JOptionPane.showMessageDialog(frame, "Sell cancelled.");
                }
            );

            panel.showDialog();
        }
    }

    public void drawBuyPropertyMenu(Player player, Property property) {
        String title = "Buy Property: " + property.getName();
        StringBuilder descBuilder = new StringBuilder();
        int baseValue = property.getValue();
        descBuilder.append("Price: $" + property.getValue()+"\n");
        for (int i = 0; i <= 5; i++) {
            baseValue += baseValue * 0.3; // mỗi level tăng 30%
            int rent = (int)(baseValue * 0.1); // rent = 10% giá trị
            descBuilder.append("Rent (Level ").append(i).append("): $").append(rent).append("\n");
        }

        String description = descBuilder.toString();

        DecisionMenuPanel panel = new DecisionMenuPanel(
            frame,
            title,
            description,
            "Buy",
            "Cancel",
            e -> {
                if (player.getMoney() >= property.getValue()) {
                    player.chargeMoney(property.getValue());
                    player.addProperty(property);
                    JOptionPane.showMessageDialog(frame, "You bought " + property.getName() + "!");
                } else {
                    JOptionPane.showMessageDialog(frame, "You do not have enough money to buy " + property.getName());
                }
            },
            e -> {
                // Hành động khi nhấn Cancel (nếu cần)
                JOptionPane.showMessageDialog(frame, "You chose not to buy " + property.getName() + ".");
            }
        );

        panel.showDialog();
    }

    public void drawUpgradeMenu(Player player, Property property) {
        String title = "upgrade Property: " + property.getName();
        StringBuilder descBuilder = new StringBuilder();
        descBuilder.append("Upgrade Price: $").append(property.getUpgradeCost()).append("\n");

        int baseValue = property.getValue();
        for (int i = 0; i <= 5; i++) {
            baseValue += baseValue * 0.3; // mỗi level tăng 30%
            int rent = (int)(baseValue * 0.1); // rent = 10% giá trị
            descBuilder.append("Rent (Level ").append(i).append("): $").append(rent).append((property.getLevel()==i) ?"(now)\n":"\n");
        }

        String description = descBuilder.toString();
        DecisionMenuPanel panel = new DecisionMenuPanel(
            frame,
            title,
            description,
            "Upgrade",
            "Cancel",
            e -> {
                if (player.getMoney() >= property.getUpgradeCost()) {
                    player.chargeMoney(property.getUpgradeCost());
                    if (property.upgrade()) {
                        JOptionPane.showMessageDialog(frame, "Upgraded to level " + property.getLevel());
                    } else {
                        JOptionPane.showMessageDialog(frame, "Max level reached.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Not enough money to upgrade.");
                }
            },
            e -> {
                // Hành động khi nhấn Cancel (nếu cần)
            }
        );

        panel.showDialog();
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
