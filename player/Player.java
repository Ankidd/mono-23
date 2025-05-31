package player;

import java.awt.*;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import manager.GameManager;
import manager.UIManager;
import Define.Define;
import Define.GameState;
import Property.Property;
import main.MainBoard;
import main.GamePanel;
import card.card;

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
    private boolean is_moving;

    private Color color;
    private int x, y;
    private List<Property> ownedProperties;
    private List<Property> properties;

    private String direction ; // "up", "down", "left", "right"
    private int currentFrameIndex ;
    private Timer animationTimer;
    private Map<String,List<BufferedImage>> spriteImages;

    public Player(String name, int playerNumber, Color color, List<Property> properties,Map<String,List<BufferedImage>> spriteImages) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.color = color;
        this.index = 1;
        this.money = 10000;
        this.jailStatus = false;
        this.hasOutJailCard = false;
        this.jailTurnsLeft = 0;
        this.bankruptcyStatus = false;
        this.ownedProperties = new ArrayList<>();
        this.properties = properties;
        setPosition();
        this.is_moving=false;
        this.direction="up";
        this.currentFrameIndex=0;
        this.spriteImages = spriteImages;
    }

    private void setPosition() {
        Property prop = properties.get(index);
        if(index==0 ||index==7 ||index==21 ||index==28){
        this.x = prop.getX() + Define.BIG_TILE_SIZE / 2 ;
        this.y = prop.getY() + Define.BIG_TILE_SIZE / 2 ;
        }
        else{
        this.x = prop.getX() + Define.SMALL_TILE_SIZE_X / 2 ;
        this.y = prop.getY() + Define.SMALL_TILE_SIZE_Y / 2 ;
    }
    }

    public boolean isBankrupt() {
        int totalVal = money;
        for (Property prop : ownedProperties) {
            totalVal += prop.getValue();
        }
        this.bankruptcyStatus = totalVal <= 0;
        return bankruptcyStatus;
    }

    public void setJailStatus(boolean check){
        this.jailStatus=check;
    }

    public void setJailTurnLeft(int turn){
        this.jailTurnsLeft=turn;
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


    public void move(int steps, MainBoard mainBoard, UIManager uiManager, GamePanel gamePanel, GameManager gameManager) {
        if (is_moving) return;
        is_moving = true;

        final int[] currentStep = {0};
        animationTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep[0] < steps) {
                    int prev = index;
                    index = (index + 1) % properties.size();
                    updateDirection(prev, index); // cập nhật hướng di chuyển
                    setPosition();
                    currentFrameIndex = (currentFrameIndex + 1) % spriteImages.get(direction).size();
                    mainBoard.repaint();
                    currentStep[0]++;
                } else {
                    ((Timer) e.getSource()).stop();
                    is_moving = false;
                    gameManager.moveProcess();
                    gamePanel.setGameState(GameState.WAITING_FOR_PROPERTY_ACTION);
                }
            }
        });

        animationTimer.start();
    }


    public void DrawPlayer(Graphics2D g2){
        if (spriteImages == null || !spriteImages.containsKey(direction)) {
            g2.setColor(this.color);
            int radius = Define.PlayerRadius;
            g2.fillOval(this.x - radius, this.y - radius, radius * 2, radius * 2);
            return;
            }

        List<BufferedImage> frames = spriteImages.get(direction);
        BufferedImage frame = frames.get(currentFrameIndex % frames.size());
        g2.drawImage(frame, this.x-50, this.y-50, 100, 100, null);
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
    public boolean getJailStatus(){
        return this.jailStatus;
    }
    public void setInJail(boolean status){
        this.inJail=status;
    }
    public int getJailTurnLeft(){
        return this.jailTurnsLeft;
    }
    public void setIndex(int index){
        this.index=index;
    }

    private void updateDirection(int fromIndex, int toIndex) {
    if (fromIndex>=28 && toIndex<=41) direction = "left"; 
    else if(fromIndex>=0 && toIndex<=7) direction="up";
    else if(fromIndex>=7 && toIndex<=21) direction="right";
    else if(fromIndex>=21 && toIndex<=28) direction="down";
}

}