package manager;

import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
 import java.awt.Color;

import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import player.Player;
import Property.Property;
import Define.Dice;
import Define.GameState;
import Define.image;
import main.GamePanel;
import Define.Define;
import main.Main;
import main.MainBoard;
import manager.UIManager;
import card.card;
import card.CardDialog;
import manager.GameLogDialog;
import observer.Observer;

public class GameManager implements Observer{
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
    private GameLogDialog log= GameLogDialog.getInstance(frame);



    public GameManager(List<Player> players, List<Property> properties,UIManager uiManager,MainBoard mainBoard,JFrame frame) {
        this.players = players;
        this.properties = properties;
        this.currentPlayerIndex = 0;
        this.turnCounter = 0;
        this.activeEvent = null;
        this.uiManager=uiManager;
        this.mainBoard=mainBoard;
        this.frame=frame;
        for (Player p : players) {
            p.registerObserver(this); 
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    
    public void nextTurn() { 
        if (isGameOver()) return;

        Player playerBeforeCheck = getCurrentPlayer();

        boolean removed = handleRemovingPlayer(); // có thể xóa người chơi hiện tại
        System.out.println(removed);
        if (!removed) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            // Người chơi hiện tại đã bị remove, không tăng currentPlayerIndex vì danh sách đã co lại
            // currentPlayerIndex sẽ tự trỏ tới người chơi kế tiếp vì người trước bị xóa
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0; // tránh vượt index
            }
            System.out.println(currentPlayerIndex);
        }

        turnCounter++;

        if (players.isEmpty()) return; // nếu tất cả bị xoá

        Player nextPlayer = getCurrentPlayer(); 
        JOptionPane.showMessageDialog(frame, "Next turn: " + nextPlayer.getName());
        log.log("----- " + nextPlayer.getName() + "'s turn -----");

        if (turnCounter % 5 == 0) {
            triggerRandomEvent();
        }
    }

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

    
    public void movePlayer(int steps,MainBoard mainBoard,UIManager uiManager,GamePanel gamePanel){
        Player player = getCurrentPlayer();
        player.move(steps,mainBoard,uiManager,gamePanel,this);
    }

        public void buyProperty(Property landedProperty,Player player){
            javax.swing.SwingUtilities.invokeLater(() -> {
                uiManager.drawBuyPropertyMenu(player, landedProperty);
            });
        }

        public void payRentProcess(Property landedProperty,Player player){
            if(!landedProperty.getOwner().getName().equals(player.getName())){
                Player owner=landedProperty.getOwner();
                if(player.getMoney()<landedProperty.getRent()){
                    uiManager.showSellPropertyMenu(player);
                    System.out.println("pay rent");
                }
                else{
                    player.payPlayer(owner, landedProperty.getRent());
                    JOptionPane.showMessageDialog(frame, player.getName()+" paid rent to "+ owner.getName() + ":$"+landedProperty.getRent());
                    log.log(player.getName()+" paid rent to "+ owner.getName() + ":$"+landedProperty.getRent());
                }
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
                payRentProcess(landedProperty,player);
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
            CardDialog.showCardEffect(frame, card, player);
            card.applyEffect(player,this); 
            log.log(player.getName()+" enter chance and get a "+card.getDescription()+"");
        }

        public void taxDealing(){
            Player player = getCurrentPlayer();
            int total_value=0;
            for(Property prop:player.getOwnedProperties()){
                total_value+=prop.getValue();
            }
            int chargeAmount=(int)(total_value*0.06);
            player.chargeMoney(chargeAmount);
            JOptionPane.showMessageDialog(frame,"you have paid tax $"+chargeAmount);
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
            System.out.println("helloo");
            JOptionPane.showMessageDialog(frame, player.getName() + " rolled " + dice.getDie1() + " and " + dice.getDie2());

            if (dice.isDouble()) {
                player.setJailStatus(false);
                player.setInJail(false);
                player.setJailTurnLeft(0);
                JOptionPane.showMessageDialog(frame, player.getName() + " rolled a double and is free!");
            } else {
                // Vẫn bị tù
                int turnsLeft = player.getJailTurnLeft() - 1;
                player.setJailTurnLeft(turnsLeft);
                JOptionPane.showMessageDialog(frame, player.getName() + " stays in jail. Turns left: " + turnsLeft);
                System.out.println("may van bi tu");
                if (turnsLeft <= 0) {
                    // Hết lượt, trả tiền để ra
                    player.chargeMoney(500);
                    log.log(player.getName()+ "pay $500 to be free from prison");
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
                    Player player=getCurrentPlayer();


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

                                int steps =dice.getTotal();
                                if(player.isInJail()){
                                    prisonProcess();
                                    System.out.println("in jail");
                                    gamePanel.setGameState(GameState.WAITING_FOR_PROPERTY_ACTION);
                                }
                                else {movePlayer(1, mainBoard, uiManager, gamePanel);}
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

        public void triggerRandomEvent() {
            String[] events = {"storm", "inflation", "crisis", "boom"};
            activeEvent = events[random.nextInt(events.length)];


            switch (activeEvent) {
                case "storm":
                    for (Property tile : properties) {
                        tile.setValue((int)(tile.getValue() * 0.5));
                    }
                    break;
                case "inflation":
                    for (Property tile : properties) {
                        tile.setValue((int)(tile.getValue() * 1.3));
                    }
                    break;
                case "crisis":
                    for (Player p : players) {
                        p.chargeMoney((int)(0.5 * p.getMoney()));
                    }
                    break;
                case "boom":
                    for (Player p : players) {
                        p.addMoney((int)(0.1 * p.getMoney()));
                    }
                    break;
            }

            JOptionPane.showMessageDialog(frame, "Event: " + activeEvent.toUpperCase() + " occurred!");
        }

        public void handleNextTurn(GamePanel gamePanel, JButton nextTurnButton) {
            Player currentPlayer = getCurrentPlayer();
            String name = currentPlayer.getName();

            JOptionPane.showMessageDialog(gamePanel,
                    name + " đã kết thúc lượt.",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

            nextTurn();
            gamePanel.setGameState(GameState.IDLE);         
            nextTurnButton.setEnabled(false);

            gamePanel.requestFocusInWindow();
        }

        public void handleShowPlayerInformation(GamePanel gamePanel,JButton ShowPlayerInformation){
            StringBuilder sb = new StringBuilder();
            Player currentPlayer = getCurrentPlayer();
            for(Property prop:currentPlayer.getOwnedProperties()){
                sb.append(prop.getName()).append(currentPlayer.getOwnedProperties().size()==1?"":",");
            }
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(gamePanel), "Thông tin người chơi", true);
            dialog.setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // dọc xuống

            JPanel playerCard = new JPanel();

            playerCard.setLayout(new BoxLayout(playerCard, BoxLayout.Y_AXIS));
            playerCard.setBorder(BorderFactory.createTitledBorder(currentPlayer.getName()));

            JLabel nameLabel= new JLabel("Player:"+currentPlayer.getName());
            JLabel moneyLabel = new JLabel("💰 Money: " + currentPlayer.getMoney());
            JLabel propertyLabel = new JLabel("🏠 Property: " + sb.toString());

            playerCard.add(nameLabel);
            playerCard.add(moneyLabel);
            playerCard.add(propertyLabel);
            playerCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            contentPanel.add(playerCard);
            contentPanel.add(Box.createVerticalStrut(10));  // khoảng cách giữa các thẻ
        

            JScrollPane scrollPane = new JScrollPane(contentPanel);
            dialog.add(scrollPane, BorderLayout.CENTER);

            JButton closeButton = new JButton("Đóng");
            closeButton.addActionListener(e -> dialog.dispose());

            JPanel bottomPanel = new JPanel();
            bottomPanel.add(closeButton);

            dialog.add(bottomPanel, BorderLayout.SOUTH);
            dialog.setSize(300, 200);
            dialog.setLocationRelativeTo(gamePanel);
            dialog.setVisible(true);

            gamePanel.requestFocusInWindow(); // để focus lại phím
        }

        public void handleShowUnownedProperties(GamePanel gamePanel, JButton showUnownedButton) {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(gamePanel), "Bất động sản chưa sở hữu", true);
            dialog.setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            boolean hasUnowned = false;

            for (Property property : properties) {
                if (property.getOwner() == null && !property.getName().equals("START")
                 && !property.getName().equals("PRISON") 
                 &&!property.getName().equals("PARK")
                 &&!property.getName().equals("VISIT")
                 &&!property.getName().equals("CHANCE")
                 &&!property.getName().equals("COMMUNITY CHEST")
                 &&!property.getName().equals("TAX")) {
                    hasUnowned = true;

                    JPanel propPanel = new JPanel(new BorderLayout());
                    propPanel.setBorder(BorderFactory.createTitledBorder(property.getName()));

                    JLabel infoLabel = new JLabel("💲 Giá: " + property.getValue());
                    propPanel.add(infoLabel, BorderLayout.CENTER);

                    contentPanel.add(propPanel);
                    contentPanel.add(Box.createVerticalStrut(10));
                }
            }

            if (!hasUnowned) {
                contentPanel.add(new JLabel("🎉 Tất cả tài sản đã được mua!"));
            }

            JScrollPane scrollPane = new JScrollPane(contentPanel);
            dialog.add(scrollPane, BorderLayout.CENTER);

            JButton closeButton = new JButton("Đóng");
            closeButton.addActionListener(e -> dialog.dispose());

            JPanel bottomPanel = new JPanel();
            bottomPanel.add(closeButton);

            dialog.add(bottomPanel, BorderLayout.SOUTH);
            dialog.setSize(300, 400);
            dialog.setLocationRelativeTo(gamePanel);
            dialog.setVisible(true);

            gamePanel.requestFocusInWindow(); // để focus lại phím
        }

        public void handleShowDialogTable(GamePanel gamePanel){
            GameLogDialog log= GameLogDialog.getInstance(frame);
            log.show();
            gamePanel.requestFocusInWindow();
        }


        public boolean handleRemovingPlayer(){
            Player player = getCurrentPlayer();
            if (player.isBankrupt()) {
                removePlayer(player);
                JOptionPane.showMessageDialog(frame, player.getName() + " ran out of money and lost the game");
                player.notifyObservers();
                return true;
            }
            return false;
        }

        @Override
        public void handleWinCondition(Player player) {
             if (isGameOver()) return;

            if (hasThreeFullColorSets(player)) {
                JOptionPane.showMessageDialog(frame, player.getName() + " thắng với 3 bộ màu đầy đủ!");
                return;
            }

            if (players.size() == 1) {
                Player winner = players.get(0);
                JOptionPane.showMessageDialog(frame, winner.getName() + " là người chiến thắng cuối cùng!");
            }
        }

        private boolean hasThreeFullColorSets(Player player) {
            Map<Color, Integer> colorCount = new HashMap<>();
            for (Property p : player.getOwnedProperties()) {
                colorCount.put(p.getColor(), colorCount.getOrDefault(p.getColor(), 0) + 1);
            }

            int fullSets = 0;
            for (Color color : colorCount.keySet()) {
                int required = Define.getSetSizeForColor(color);
                if (colorCount.get(color) >= required) {
                    fullSets++;
                }
            }

            return fullSets >= 3;
        }

        
}