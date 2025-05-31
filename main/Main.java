package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Define.*;
import Property.Property;
import manager.GameManager;
import player.Player;
import manager.UIManager;
import Define.image;


public class Main {
    public static void main(String[] args) {
        System.out.println(Define.WIDTH + " " + Define.HEIGHT);

        JFrame frame = new JFrame("Monopoly Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Define.WIDTH, Define.HEIGHT);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);

        // Tạo JLayeredPane để quản lý nhiều lớp giao diện
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, Define.WIDTH, Define.HEIGHT);
        layeredPane.setLayout(null);

        // Tạo dicePanel riêng biệt
        JPanel dicePanel = new JPanel();
        dicePanel.setBackground(new Color(0, 0, 0, 0)); // trong suốt
        dicePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // dicePanel.setOpaque(false);

        JLabel diceLabel1 = new JLabel();
        JLabel diceLabel2 = new JLabel();

        // Gán icon mặc định cho diceLabels
        diceLabel1.setIcon(Dice.diceMap().get(1));
        diceLabel2.setIcon(Dice.diceMap().get(2));

        dicePanel.add(diceLabel1);
        dicePanel.add(diceLabel2);

        // Đặt kích thước và vị trí cho dicePanel (đảm bảo không bị che)
        dicePanel.setBounds(Define.WIDTH/2-100, Define.HEIGHT-Define.SMALL_TILE_SIZE_Y-100, 150, 70);

        // Khởi tạo các thành phần khác
        List<Property> properties = Property.createProperties();
        List<Player> players = new ArrayList<>();
        Map<String,List<BufferedImage>> spriteImage1=image.spriteImages1;
        Map<String,List<BufferedImage>> spriteImage2=image.spriteImages2;
        Map<String,List<BufferedImage>> spriteImage3=image.spriteImages3;
        Map<String,List<BufferedImage>> spriteImage4=image.spriteImages4;
        players.add(new Player("An", 1, Color.RED, properties,spriteImage1));
        players.add(new Player("Khoi", 1, Color.RED, properties,spriteImage1));
        players.add(new Player("Khai", 1, Color.RED, properties,spriteImage1));
        players.add(new Player("Hoan", 1, Color.RED, properties,spriteImage1));
        MainBoard board = new MainBoard(properties, players);
        board.setBounds(0, 0, 900, 760);

        UIManager uiManager = new UIManager(null, null, null, properties, players, frame);
        GameManager gameManager = new GameManager(players, properties, uiManager, board, frame);

        // Truyền đúng diceLabel vào GamePanel
        GamePanel gamePanel = new GamePanel(properties, players, gameManager, uiManager, diceLabel1, diceLabel2);
        gamePanel.setBounds(0, 0, Define.WIDTH, Define.HEIGHT);

        // Thêm các panel vào JLayeredPane với thứ tự lớp
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // gamePanel ở dưới
        layeredPane.add(dicePanel, JLayeredPane.PALETTE_LAYER);  // dicePanel ở trên

        // Thay thế content pane của frame bằng layeredPane
        frame.setContentPane(layeredPane);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Đảm bảo gamePanel nhận focus để xử lý input
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());

        gamePanel.startGameThread();
    }
}
