package Define;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionMenuPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JButton confirmButton;
    private JButton cancelButton;
    private JDialog dialog;

    public ActionMenuPanel(
            JFrame parentFrame,
            String title,
            String description,
            String confirmText,
            ActionListener confirmAction
    ) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        descriptionLabel = new JLabel(description);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmButton = new JButton(confirmText);
        cancelButton = new JButton("Cancel");

        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(descriptionLabel);
        add(Box.createVerticalStrut(15));
        add(confirmButton);
        add(Box.createVerticalStrut(5));
        add(cancelButton);

        // Event handling
        confirmButton.addActionListener(e -> {
            confirmAction.actionPerformed(e);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());
    }

    public void showDialog(JFrame parentFrame, String windowTitle) {
        dialog = new JDialog(parentFrame, windowTitle, true);
        dialog.getContentPane().add(this);
        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
}

