package manager;

import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import player.Player;
import Property.Property;
import Define.Dice;
import Define.image;
import main.GamePanel;
import main.Main;
import main.MainBoard;
import manager.UIManager;
import card.card;

public class GameManager {
    private List<Player> players;
    private List<Property> properties;
    private int currentPlayerIndex;
    private int turnCounter;
    private String activeEvent;
    private UIManager uiManager;
    private MainBoard mainBoard;
    private JFrame frame;
    private Random random=new Random();
    private List<BufferedImage> diceIcons=image.diceList();


    public GameManager(List<Player> players, List<Property> properties,UIManager uiManager,MainBoard mainBoard,JFrame frame) {
        this.players = players;
        this.properties = properties;
        this.currentPlayerIndex = 0;
        this.turnCounter = 0;
        this.activeEvent = null;
        this.uiManager=uiManager;
        this.mainBoard=mainBoard;
        this.frame=frame;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    // public void nextTurn(UIManager uiManager) {
    //     currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    //     turnCounter++;
    //     if (turnCounter % 5 == 0) {
    //         triggerRandomEvent(uiManager);
    //     }
    // }

    // public boolean handleInsufficientFunds(Player player, int amountNeeded, UIManager uiManager) {
    //     uiManager.showMessage(player.getName() + " does not have enough money. Please sell property", 1500);

    //     int totalAsset = player.getMoney();
    //     for (Property p : player.getOwnedProperties()) {
    //         totalAsset += p.getValue() / 2;
    //     }

    //     if (totalAsset < amountNeeded) {
    //         uiManager.pauseAndShowMessage(player.getName() + " bankrup!");
    //         removePlayer(player);
    //         if (isGameOver()) {
    //             Player winner = getWinner();
    //             uiManager.pauseAndShowMessage(winner.getName() + " WIN!", 3000);
    //             System.exit(0);
    //         }
    //         return true;
    //     }

    //     uiManager.setSellMode(true);
    //     uiManager.setSellPlayer(player);
    //     uiManager.setSellTargetMoney(amountNeeded);
    //     return false;
    // }

    public void removePlayer(Player player) {
        for (Property p : player.getOwnedProperties()) {
            p.setOwned(false);
            p.setOwner(null);
        }
        players.remove(player);
    }

    public boolean isGameOver() {
        return players.size() == 1;
    }

    public Player getWinner() {
        return isGameOver() ? players.get(0) : null;
    }

    // public void triggerRandomEvent(UIManager uiManager) {
    //     String[] events = {"storm", "inflation", "crisis", "boom"};
    //     Random random = new Random();
    //     activeEvent = events[random.nextInt(events.length)];

    //     uiManager.playEventAnimation(activeEvent);

    //     switch (activeEvent) {
    //         case "storm":
    //             for (Property tile : tiles) {
    //                 tile.setValue((int)(tile.getValue() * 0.5));
    //             }
    //             break;
    //         case "inflation":
    //             for (Property tile : tiles) {
    //                 tile.setValue((int)(tile.getValue() * 1.3));
    //             }
    //             break;
    //         case "crisis":
    //             for (Player p : players) {
    //                 p.chargeMoney((int)(0.5 * p.getMoney()));
    //             }
    //             break;
    //         case "boom":
    //             for (Player p : players) {
    //                 p.addMoney((int)(0.1 * p.getMoney()));
    //             }
    //             break;
    //     }
    // }
    
    public void movePlayer(int steps,MainBoard mainBoard,UIManager uiManager,GamePanel gamePanel){
        Player player = getCurrentPlayer();
        player.move(steps,mainBoard,uiManager,gamePanel,this);
    }

        public void buyProperty(Property landedProperty,Player player){
            javax.swing.SwingUtilities.invokeLater(() -> {
                uiManager.drawBuyPropertyMenu(player, landedProperty);
            });
        }

        public void payRent(Property landedProperty,Player player){
            if(!landedProperty.getOwner().getName().equals(player.getName())){
                Player owner=landedProperty.getOwner();
                player.payPlayer(owner, landedProperty.getRent());
            }
        }

        public void processCurrentProperty() {
            Player player = getCurrentPlayer();
            int index = player.getIndex();
            Property landedProperty = this.properties.get(index);

            if (!landedProperty.isOwned()) {
                buyProperty(landedProperty, player);
            } else if (!landedProperty.getOwner().getName().equals(player.getName())) {
                // Nếu có chủ khác -> trả tiền thuê
                payRent(landedProperty,player);
            }
        }


        public void processUpgradeProperty(){
            Player player = getCurrentPlayer();
            int index = player.getIndex();  
            Property landedProperty = this.properties.get(index);

            if(landedProperty.isOwned()&&landedProperty.getOwner().getName().equals(player.getName())){
                uiManager.drawUpgradeMenu(player,landedProperty);
            }
        } 

        public void cardDealing(List<card> cards) {
            Player player = getCurrentPlayer();
            card card = cards.get(new Random().nextInt(cards.size()));
            card.applyEffect(player);
        }

        public void taxDealing(){
            Player player = getCurrentPlayer();
            int total_value=0;
            for(Property prop:player.getOwnedProperties()){
                total_value+=prop.getValue();
            }
            player.chargeMoney(total_value);
            JOptionPane.showMessageDialog(frame,"you have paid tax $"+total_value);
        }

        public void bigTileDealing(){
             Player player = getCurrentPlayer();
             int index=player.getIndex();
             if(properties.get(index).getName().equals("START")){
                player.addMoney(3000);
                JOptionPane.showMessageDialog(frame, "you go through START and get $3000");
             }
             else if (properties.get(index).getName().equals("PRISON")){
                JOptionPane.showMessageDialog(frame, "you are in prison");
                player.setJailStatus(true);
                player.setInJail(true);
                player.setJailTurnLeft(3);
             }
        }

        public void moveProcess(){
            Player player = getCurrentPlayer();
            int index=player.getIndex();
            if(properties.get(index).getName().equals("CHANCE")){
                        cardDealing(card.chanceCard());
                    }
                    else if(properties.get(index).getName().equals("COMMUNITY CHEST")){
                        cardDealing(card.communityCard());
                    }
                    else if(index==0 || index==7 || index==21 || index==28){
                        bigTileDealing();
                    }
                    else if(properties.get(index).getName().equals("TAX")){
                        taxDealing();
                    }
                    else{
                        processCurrentProperty();
                        processUpgradeProperty();
                    }
        }

        public void prisonProcess(){
            Player player = getCurrentPlayer();
            Dice dice = new Dice();
            dice.roll();
            JOptionPane.showMessageDialog(null, player.getName() + " rolled " + dice.getDie1() + " and " + dice.getDie2());

            if (dice.isDouble()) {
                player.setJailStatus(false);
                player.setInJail(false);
                player.setJailTurnLeft(0);
                JOptionPane.showMessageDialog(null, player.getName() + " rolled a double and is free!");
            } else {
                // Vẫn bị tù
                int turnsLeft = player.getJailTurnLeft() - 1;
                player.setJailTurnLeft(turnsLeft);
                JOptionPane.showMessageDialog(null, player.getName() + " stays in jail. Turns left: " + turnsLeft);

                if (turnsLeft <= 0) {
                    // Hết lượt, trả tiền để ra
                    player.chargeMoney(500);
                    player.setJailStatus(false);
                    player.setInJail(false);
                    JOptionPane.showMessageDialog(null, player.getName() + " paid $500 and is free!");
                }
            }
        }

        public void rollProcess(JLabel diceLabel1, JLabel diceLabel2, GamePanel gamePanel) {
                Dice dice = new Dice(); 
                Map<Integer, ImageIcon> map = Dice.diceMap();
                Random rand = new Random();
                long startTime = System.currentTimeMillis();

                Timer timer = new Timer(200, null);
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long elapsed = System.currentTimeMillis() - startTime;

                        if (elapsed >= 1000) {
                            ((Timer) e.getSource()).stop();

                            dice.roll();
                            int die1 = dice.getDie1();
                            int die2 = dice.getDie2();

                            diceLabel1.setIcon(map.get(die1));
                            diceLabel2.setIcon(map.get(die2));

                            int steps = die1 + die2;
                            movePlayer(steps, mainBoard, uiManager, gamePanel);
                        } else {
                            int temp1 = 1 + rand.nextInt(6);
                            int temp2 = 1 + rand.nextInt(6);
                            diceLabel1.setIcon(map.get(temp1));
                            diceLabel2.setIcon(map.get(temp2));
                        }
                    }
                });

                timer.start();
            }


    // public void animateTransfer(Player fromPlayer, Player toPlayer, String propertyName, DrawBoard drawBoard, Player player1, Player player2, UIManager uiManager) {
    //     String text = fromPlayer.getName() + " sold " + propertyName + " to " + toPlayer.getName();
    //     long startTime = System.currentTimeMillis();
    //     while (System.currentTimeMillis() - startTime < 1500) {
    //         drawBoard.draw(tiles, Arrays.asList(player1, player2), uiManager);
    //         for (Player p : players) {
    //             p.drawWithAnimation();
    //         }
    //         System.out.println(text);  // Thay thế cho vẽ GUI thực tế
    //         try {
    //             Thread.sleep(50);
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
}