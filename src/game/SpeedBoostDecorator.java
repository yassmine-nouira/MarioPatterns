package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class SpeedBoostDecorator extends PowerUpDecorator {
    public SpeedBoostDecorator(Character character, long duration) {
        super(character, duration);
    }
    @Override
    public boolean hasShield() {
        return character.hasShield();
    }
    @Override
    public double getSpeed() {
        return character.getSpeed() * 2;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        character.draw(g2d);
        g2d.setColor(new Color(251, 191, 36));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect((int)(x - 2), (int)(y - 2), width + 4, height + 4);
    }
    @Override
    public int getStrength() { return character.getStrength(); }
}