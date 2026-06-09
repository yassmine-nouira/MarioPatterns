package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class ShieldDecorator extends PowerUpDecorator {
    public ShieldDecorator(Character character, long duration) {
        super(character, duration);
    }

    @Override
    public boolean hasShield() {
        return true;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        character.draw(g2d);
        g2d.setColor(new Color(59, 130, 246));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval((int)(x - 5), (int)(y - 5), 40, 40);
    }

    @Override
    public double getSpeed() { return character.getSpeed(); }
    
    @Override
    public int getStrength() { return character.getStrength(); }
	
}