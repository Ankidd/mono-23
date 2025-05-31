package main;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



import Property.Property;
import player.Player;
import manager.GameManager;
import manager.UIManager;
import Define.Dice;
import Define.GameState;
import Define.keyHandler;
import Define.GameState;
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
    Map<Integer,ImageIcon> map=Dice.diceMap();
    private JLabel diceLabel1 = new JLabel(); 
    private JLabel diceLabel2 = new JLabel(); 


    public GamePanel(List<Property> properties, List<Player> players, GameManager gameManager, UIManager uiManager,JLabel diceLabel1, JLabel diceLabel2) {
        this.properties = properties;
        this.players = players;
        this.gameManager = gameManager;
        this.uiManager = uiManager;
        this.diceLabel1 = diceLabel1;
        this.diceLabel2 = diceLabel2;
        // this.diceLabel1.setBounds(100, 600, 64, 64);
        // this.diceLabel2.setBounds(180, 600, 64, 64);

        this.mainBoard = new MainBoard(properties, players);
        this.setLayout(null);
        // this.add(diceLabel1);
        // this.add(diceLabel2);
        mainBoard.setBounds(0,0,Define.WIDTH,Define.HEIGHT);
        this.add(mainBoard);

        this.addKeyListener(keyH);
        // this.setFocusable(true);
        // this.requestFocusInWindow();

        gameTimer = new Timer(16, new ActionListener() {
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

            if (player.isInJail()) {
                gameManager.prisonProcess();
                System.out.println("in jail");
            } else {
                // Dice dice = new Dice();
                // dice.roll();
                // gameState = GameState.MOVING;
                // gameManager.movePlayer(7, mainBoard, uiManager, this);
                gameManager.rollProcess(diceLabel1, diceLabel2, this);
            }
        }

        if (gameState == GameState.WAITING_FOR_PROPERTY_ACTION) {
            gameState = GameState.IDLE;
        }

        if (keyH.exit) {
            System.exit(0);
        }
    }

        public void setGameState(GameState state){
            gameState=state;
        }
    }
