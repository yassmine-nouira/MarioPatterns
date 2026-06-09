package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class PowerUp extends GameComponent {
    private double x, y;
    private String type;
    private int width = 20, height = 20;
    private boolean collected = false;

    public PowerUp(double x, double y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g2d) {
        if (collected) return;
        
        Color color = "speed".equals(type) ? new Color(251, 191, 36) :
                     "shield".equals(type) ? new Color(59, 130, 246) : new Color(239, 68, 68);
        g2d.setColor(color);
        g2d.fillOval((int)(x + 5), (int)(y + 5), 10, 10);
        
        // Simple icon
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(type.substring(0,1).toUpperCase(), (int)(x + 9), (int)(y + 14));
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public void collect() {
        collected = true;
    }

    public String getType() {
        return type;
    }

    public boolean isCollected() {
        return collected;
    }
}

