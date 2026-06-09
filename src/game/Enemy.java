package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Enemy extends GameComponent {
    private double x, y;
    private String type;
    private int width = 25, height = 25;
    private double velocityX;
    private int direction = 1;
    private boolean alive = true;

    public Enemy(double x, double y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.velocityX = "fast".equals(type) ? 2 : 1;
    }

    @Override
    public void update() {
        if (!alive) return;
        
        x += velocityX * direction;
        
        if (x < 50 || x > 750) {
            direction *= -1;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!alive) return;
        
        g2d.setColor("fast".equals(type) ? new Color(239, 68, 68) : new Color(249, 115, 22));
        g2d.fillRect((int)x, (int)y, width, height);
        
        // Eyes
        g2d.setColor(Color.WHITE);
        g2d.fillRect((int)(x + 5), (int)(y + 8), 5, 5);
        g2d.fillRect((int)(x + 15), (int)(y + 8), 5, 5);
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
