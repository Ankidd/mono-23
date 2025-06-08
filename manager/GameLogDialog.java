package manager;

import javax.swing.*;
import java.awt.*;

public class GameLogDialog {
    private static GameLogDialog instance;
    private final JDialog dialog;
    private final JTextArea logArea;

    private GameLogDialog(Frame owner) {
        dialog = new JDialog(owner, "Nhật ký trò chơi", false);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(owner);
        dialog.setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        dialog.add(new JScrollPane(logArea), BorderLayout.CENTER);

        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(e -> dialog.setVisible(false));
        dialog.add(closeButton, BorderLayout.SOUTH);
    }

    public static GameLogDialog getInstance(Frame owner) {
        if (instance == null) {
            instance = new GameLogDialog(owner);
        }
        return instance;
    }

    public void show() {
        dialog.setVisible(true);
    }

    public void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public void clear() {
        logArea.setText("");
    }
}
