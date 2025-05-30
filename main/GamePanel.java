package main;

import javax.swing.*;
import java.util.List;
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


    public GamePanel(List<Property> properties, List<Player> players, GameManager gameManager, UIManager uiManager) {
        this.properties = properties;
        this.players = players;
        this.gameManager = gameManager;
        this.uiManager = uiManager;

        this.mainBoard = new MainBoard(properties, players);
        this.setLayout(new BorderLayout());
        this.add(mainBoard, BorderLayout.CENTER);

        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();

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
                Dice dice = new Dice();
                dice.roll();
                gameState = GameState.MOVING;
                gameManager.movePlayer(7, mainBoard, uiManager, this);
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
