package player;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import Define.Define;
import Property.Property;

public class Player {
    private int index;
    private String name;
    private int playerNumber;
    private int money;
    private boolean jailStatus;
    private boolean hasOutJailCard;
    private boolean inJail;
    private int jailTurnsLeft;
    private boolean canRollAfterJail;
    private boolean bankruptcyStatus;

    private Color color;
    private int x, y;
    private List<Property> ownedProperties;
    private List<Property> properties;

    public Player(String name, int playerNumber, Color color, List<Property> properties, int startIndex) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.color = color;
        this.index = startIndex;
        this.money = 10000;
        this.jailStatus = false;
        this.hasOutJailCard = false;
        this.inJail = false;
        this.jailTurnsLeft = 0;
        this.canRollAfterJail = false;
        this.bankruptcyStatus = false;
        this.ownedProperties = new ArrayList<>();
        this.properties = properties;
        setPosition();
    }

    private void setPosition() {
        Property prop = properties.get(index);
        this.x = prop.getX() + Define.SMALL_TILE_SIZE_X / 2 - Define.PlayerRadius / 2;
        this.y = prop.getY() + Define.SMALL_TILE_SIZE_Y / 2 - Define.PlayerRadius / 2;
    }

    public boolean isBankrupt() {
        int totalVal = money;
        for (Property prop : ownedProperties) {
            totalVal += prop.getValue();
        }
        this.bankruptcyStatus = totalVal <= 0;
        return bankruptcyStatus;
    }

    public void checkStatus() {
        System.out.println("Player Name: " + name);
        System.out.println("Player ID: " + playerNumber);
        System.out.println("Player Position: " + index);
        System.out.println("Wallet Amount: " + money);
        System.out.print("Properties Held: ");
        if (ownedProperties.isEmpty()) {
            System.out.println("none");
        } else {
            for (Property prop : ownedProperties) {
                System.out.print(prop.getName() + ", ");
            }
            System.out.println();
        }
        System.out.println("Total Net Worth: " + (money + ownedProperties.stream().mapToInt(Property::getValue).sum()));
    }

    public void addProperty(Property property) {
        ownedProperties.add(property);
        property.setOwner(this);
        property.setOwned(true);
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public void chargeMoney(int amount) {
        this.money -= amount;
    }

    public void payPlayer(Player other, int amount) {
        if (this.money >= amount) {
            this.money -= amount;
            other.addMoney(amount);
        } else {
            System.out.println(name + " does not have enough money to pay " + amount + " to " + other.name);
        }
    }

    public void goToJail() {
        this.jailStatus = true;
        this.index = 10;
        setPosition();
    }

    public void move(int steps) {
        for (int i = 0; i < steps; i++) {
            this.index = (this.index + 1) % properties.size();
            setPosition();
            // You would update UI or animation here in actual game loop
        }
    }

    public void DrawPlayer(Graphics2D g2){
        int radius=Define.PlayerRadius;
        g2.setColor(this.color);

        g2.fillOval(this.x-radius,this.y-radius,radius*2,radius*2);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(this.x-radius,this.y-radius,radius*2,radius*2);
    }


    // Getters for drawing and interaction
    public int getX() { return x; }
    public int getY() { return y; }
    public String getName() { return name; }
    public int getIndex() { return index; }
    public Color getColor() { return color; }
    public boolean isInJail() { return inJail; }
    public int getMoney() { return money; }
    public List<Property> getOwnedProperties() { return ownedProperties; }
    public boolean hasOutJailCard() { return hasOutJailCard; }
    public void setOutJailCard(boolean status) { this.hasOutJailCard = status; }

    public void setIndex(int index){
        this.index=index;
    }


}