package button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JComponent {
    private String text;
    private Color bgColor;
    private Color hoverColor = Color.GRAY;
    private Color textColor = Color.BLACK;
    private boolean hovered = false;

    public Button(String text, int x, int y, int width, int height,Color color) {
        this.text = text;
        this.bgColor=color;
        setBounds(x, y, width, height);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                onClick();
            }
        });
    }

    // Override this method to define button click behavior
    public void onClick() {
        System.out.println(text + " clicked!");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw button background
        g2.setColor(hovered ? hoverColor : bgColor);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw button border
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        // Draw button text centered
        g2.setColor(textColor);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        // int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 2;
        g2.drawString(text, x, y);
    }
}
