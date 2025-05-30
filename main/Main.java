package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Define.*;
import Property.Property;
import manager.GameManager;
import player.Player;
import manager.UIManager;



public class Main{
    public static void main(String[] args) {
        System.out.println(Define.WIDTH+" "+Define.HEIGHT);
        JFrame frame = new JFrame("Monopoly Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Define.WIDTH, Define.HEIGHT); 
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);

        // Tắt layout để tự do đặt tọa độ
        frame.setLayout(null);

        // Tạo panel dice riêng, bạn có thể tự đặt vị trí sau
        JPanel dicePanel = new JPanel();
        dicePanel.setLayout(new FlowLayout());
        dicePanel.setOpaque(false);  // Nếu muốn trong suốt background để thấy board dưới

        JLabel diceLabel1 = new JLabel();
        JLabel diceLabel2 = new JLabel();

        dicePanel.add(diceLabel1);
        dicePanel.add(diceLabel2);

        // Đặt kích thước và vị trí cụ thể cho dicePanel
        dicePanel.setBounds(900, 700, 150, 60);  // ví dụ: x=900, y=700, rộng=150, cao=60

        List<Property> properties = Property.createProperties();
        List<Player> players = new ArrayList<>();
        players.add(new Player("An", 1, Color.RED, properties, 0));
        MainBoard board = new MainBoard(properties, players);

        // Đặt kích thước và vị trí cho board
        board.setBounds(0, 0, 900, 760);  // bạn tùy chỉnh theo kích thước màn hình và mong muốn

        UIManager uiManager = new UIManager(null, null, null, properties, players, frame);
        GameManager gameManager = new GameManager(players, properties, uiManager, board, frame);

        // Chú ý truyền đúng diceLabel1, diceLabel2 vào GamePanel
        GamePanel gamePanel = new GamePanel(properties, players, gameManager, uiManager);

        // Với cách làm này, gamePanel nên có kích thước và vị trí đầy đủ
        // Nếu gamePanel chứa MainBoard rồi, bạn có thể set kích thước phù hợp
        gamePanel.setBounds(0, 0, Define.WIDTH, Define.HEIGHT);

        // Thêm gamePanel và dicePanel vào frame
        frame.add(dicePanel);
        frame.add(gamePanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGameThread();
    }
}

