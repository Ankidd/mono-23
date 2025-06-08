package card;
import javax.swing.*;
import java.awt.*;
import player.Player;

public class CardDialog {
    public static void showCardEffect(JFrame parent, card drawnCard, Player player) {
        JDialog dialog = new JDialog(parent, "Bạn rút được một thẻ", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(350, 180);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JLabel label = new JLabel("<html><div style='text-align: center;'>" + drawnCard.getDescription() + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        dialog.add(label, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true); 
    }
}
