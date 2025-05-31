package Define;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DecisionMenuPanel extends JPanel {
    private JLabel titleLabel;
    private JTextArea descriptionArea;
    private JButton acceptButton;
    private JButton denyButton;
    private JDialog dialog;

    public DecisionMenuPanel(
        Frame owner,
        String title,
        String description,
        String acceptText,
        String denyText,
        ActionListener acceptAction,
        ActionListener denyAction
    ) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        descriptionArea = new JTextArea(description);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        add(descriptionArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        acceptButton = new JButton(acceptText);
        denyButton = new JButton(denyText);
        buttonPanel.add(acceptButton);
        buttonPanel.add(denyButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Gắn hành động
        acceptButton.addActionListener(e -> {
            if (acceptAction != null) acceptAction.actionPerformed(e);
            if (dialog != null) dialog.dispose();
        });

        denyButton.addActionListener(e -> {
            if (denyAction != null) denyAction.actionPerformed(e);
            if (dialog != null) dialog.dispose();
        });

        dialog = new JDialog(owner, title, true);
        dialog.setContentPane(this);
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
    }

    public void showDialog() {
        dialog.setVisible(true);
    }
}

