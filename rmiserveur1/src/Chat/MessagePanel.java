package Chat;
import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {
    private String message;
    private Color backgroundColor;
    private Color textColor;

    public MessagePanel(String message, Color backgroundColor, Color textColor) {
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        setOpaque(false);
        setPreferredSize(new Dimension(300, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int arcSize = 10;

        g.setColor(backgroundColor);
        g.fillRoundRect(0, 0, width, height, arcSize, arcSize);
        g.setColor(textColor);
        g.drawString(message, 10, height / 2);
    }
}