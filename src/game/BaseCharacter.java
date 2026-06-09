package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class BaseCharacter extends Character {
    public BaseCharacter(double x, double y) { super(x, y); }

    @Override
    public double getSpeed() { return 3; }
    @Override
    public int getStrength() { return 1; }
    @Override
    public boolean hasShield() { return false; }
    
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(74, 222, 128));
        g2d.fillRect((int)x, (int)y, width, height);
        
        // Eyes
        g2d.setColor(Color.BLACK);
        int eyeY = (int)(y + 10);
        if (direction > 0) {
            g2d.fillRect((int)(x + 15), eyeY, 5, 5);
            g2d.fillRect((int)(x + 22), eyeY, 5, 5);
        } else {
            g2d.fillRect((int)(x + 3), eyeY, 5, 5);
            g2d.fillRect((int)(x + 10), eyeY, 5, 5);
        }
    }
}
