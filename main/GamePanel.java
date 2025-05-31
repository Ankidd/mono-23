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


    public GamePanel(List<Property> properties, List<Player> players, GameManager gameManager, UIManager uiManager,JLabel diceLabel1, JLabel diceLabel2) {
        this.properties = properties;
        this.players = players;
        this.gameManager = gameManager;
        this.uiManager = uiManager;
        this.diceLabel1 = diceLabel1;
        this.diceLabel2 = diceLabel2;
        this.nextTurnButton = new JButton("Next Turn");
        this.nextTurnButton.setEnabled(false);
        nextTurnButton.setBounds(300, 600, 120, 40);

        this.mainBoard = new MainBoard(properties, players);
        this.setLayout(null);
        mainBoard.setBounds(0,0,Define.WIDTH,Define.HEIGHT);
        this.add(nextTurnButton);
        this.add(mainBoard);
        this.addKeyListener(keyH);
        
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player currentPlayer = gameManager.getCurrentPlayer();
                String name = currentPlayer.getName(); // nếu có getName()
                JOptionPane.showMessageDialog(GamePanel.this,
                        name + " đã kết thúc lượt.",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);

                gameManager.nextTurn();
                setGameState(GameState.IDLE);         
                nextTurnButton.setEnabled(false);

                GamePanel.this.requestFocusInWindow();
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

            if (player.isInJail()) {
                gameManager.prisonProcess();
                System.out.println("in jail");
            } else {
                gameManager.rollProcess(diceLabel1, diceLabel2, this);
            }
        }

        if (gameState == GameState.WAITING_FOR_PROPERTY_ACTION) {
            gameState = GameState.IDLE;
            nextTurnButton.setEnabled(true);
        }

        if (keyH.exit) {
            System.exit(0);
        }
    }

        public void setGameState(GameState state){
            gameState=state;
        }
    }
