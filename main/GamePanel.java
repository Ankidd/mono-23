package main;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



import Property.Property;
import player.Player;
import manager.GameManager;
import manager.UIManager;
import Define.Dice;
import Define.GameState;
import Define.keyHandler;
import Define.Define;


public class GamePanel extends JPanel implements Runnable {
    private MainBoard mainBoard;
    private GameManager gameManager;
    private UIManager uiManager;
    private List<Property> properties;
    private List<Player> players;
    private Thread gameThread;
    private keyHandler keyH = new keyHandler();
    private Timer gameTimer;
    private GameState gameState = GameState.IDLE;
    private int stepsRemaining = 0;
    private Map<Integer,ImageIcon> map=Dice.diceMap();
    private JLabel diceLabel1 = new JLabel(); 
    private JLabel diceLabel2 = new JLabel(); 
    private JButton nextTurnButton;
    private JButton ShowPlayerInformation;
    private JButton ShowUnownedProperty;
    private JButton ShowSellMenu;
    private JButton ShowDialogTable;


    public GamePanel(List<Property> properties, List<Player> players, GameManager gameManager, UIManager uiManager,JLabel diceLabel1, JLabel diceLabel2) {
        this.properties = properties;
        this.players = players;
        this.gameManager = gameManager;
        this.uiManager = uiManager;
        this.diceLabel1 = diceLabel1;
        this.diceLabel2 = diceLabel2;
        this.nextTurnButton = new JButton("Next Turn");
        this.nextTurnButton.setEnabled(false);
        nextTurnButton.setBounds(Define.WIDTH/2+200, Define.HEIGHT-Define.SMALL_TILE_SIZE_Y-50, 120, 40);
        this.ShowPlayerInformation = new JButton("Show player's information");
        ShowPlayerInformation.setBounds(Define.WIDTH/2-300, Define.HEIGHT-Define.SMALL_TILE_SIZE_Y-50, 120, 40);
         this.ShowUnownedProperty = new JButton("Show available property");
        ShowUnownedProperty.setBounds(Define.WIDTH/2-500, Define.HEIGHT-Define.SMALL_TILE_SIZE_Y-50, 120, 40);
        this.ShowSellMenu = new JButton("sell property");
        ShowSellMenu.setBounds(Define.WIDTH/2+400, Define.HEIGHT-Define.SMALL_TILE_SIZE_Y-50, 120, 40);
        this.ShowDialogTable = new JButton("show dialog");
        ShowDialogTable.setBounds(Define.WIDTH/2- 60, Define.HEIGHT-Define.SMALL_TILE_SIZE_Y-50, 120, 40);

        this.mainBoard = MainBoard.getInstance();
        this.setLayout(null);
        mainBoard.setBounds(0,0,Define.WIDTH,Define.HEIGHT);
        this.add(nextTurnButton);
        this.add(ShowSellMenu);
        this.add(ShowDialogTable);
        this.add(ShowPlayerInformation);
        this.add(ShowUnownedProperty);
        this.add(mainBoard);
        this.addKeyListener(keyH);
        
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.handleNextTurn(GamePanel.this, nextTurnButton);
            }
        });

        ShowPlayerInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameManager.handleShowPlayerInformation(GamePanel.this, ShowPlayerInformation);
            }
        });

        ShowUnownedProperty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameManager.handleShowUnownedProperties(GamePanel.this, ShowUnownedProperty);
            }
        });

        ShowSellMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                uiManager.showSellPropertyMenu(gameManager.getCurrentPlayer());
            }
        });

        ShowDialogTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameManager.handleShowDialogTable(GamePanel.this);
            }
        });

        gameTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameUpdate();
                mainBoard.repaint();
            }
        });
        gameTimer.start();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            gameUpdate();
            mainBoard.repaint();
        }
    }

   public void gameUpdate() {
        Player player = gameManager.getCurrentPlayer();

        if (keyH.roll_button && gameState == GameState.IDLE) {
            keyH.roll_button = false;
            gameState=GameState.ROLLING;

            
            gameManager.rollProcess(diceLabel1, diceLabel2, this);
        }

        if (gameState == GameState.WAITING_FOR_PROPERTY_ACTION) {
            nextTurnButton.setEnabled(true);
            // gameState = GameState.IDLE;
        }

        if (keyH.exit) {
            System.exit(0);
        }
    }
    
    public void setGameState(GameState state){
            gameState=state;
        }
    
    public GameState getGameState(){
        return gameState;
    }

    }
