package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Platform extends GameComponent {
    public double x, y, width, height;
    private Color color;

    public Platform(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect((int)x, (int)y, (int)width, (int)height);
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }
}
