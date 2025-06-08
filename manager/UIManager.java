package manager;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import Property.Property;
import player.Player;
import Define.DecisionMenuPanel;



public class UIManager {
    private List<Property> properties;
    private List<Player> players;
    private long startTime;
    private final int countdownDuration = 60 * 60 * 1000;

    private boolean sellMode = false;
    private int sellTargetMoney = 0;
    private JFrame frame;
    private GameLogDialog log;

    public UIManager(List<Property> properties, List<Player> players,JFrame frame) {
        this.properties = properties;
        this.players = players;
        this.startTime = System.currentTimeMillis();
        this.frame=frame;
        this.log=GameLogDialog.getInstance(frame);
    }

    public void showSellPropertyMenu(Player player) {
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
                    log.log(player.getName()+ "sold "+ selected.getName());

                    // Nếu cần ép bán tiếp
                    if (player.getMoney() < sellTargetMoney && sellMode && player.getOwnedProperties().size() > 0) {
                        showSellPropertyMenu(player);
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
                    property.setOwner(player);
                    JOptionPane.showMessageDialog(frame, "You bought " + property.getName() + "!");
                    log.log(player.getName()+ " bought "+property.getName());
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
            baseValue += baseValue * 0.3; 
            int rent = (int)(baseValue * 0.1); 
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
                        log.log(player.getName()+ " upgraded "+property.getName()+" to level"+property.getLevel());
                    } else {
                        JOptionPane.showMessageDialog(frame, "Max level reached.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Not enough money to upgrade.");
                }
            },
            e -> {
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


    static class SellButton {
        Rectangle rect;
        Object target;  

        SellButton(Rectangle rect, Object target) {
            this.rect = rect;
            this.target = target;
        }
    }
    public void setSellMode(boolean bool){
        this.sellMode=bool;
    }



    public void setSellTargetMoney(int amount){
        this.sellTargetMoney=amount;
    }

}
