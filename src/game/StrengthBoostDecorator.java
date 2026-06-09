package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class StrengthBoostDecorator extends PowerUpDecorator {
    public StrengthBoostDecorator(Character character, long duration) {
        super(character, duration);
    }

    @Override
    public int getStrength() {
        return character.getStrength() * 2;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        character.draw(g2d);
        g2d.setColor(new Color(239, 68, 68));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.fillRect((int)(x - 5), (int)(y - 5), width + 10, height + 10);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    
    @Override
    public double getSpeed() { return character.getSpeed(); }
    
    @Override
    public boolean hasShield() { return character.hasShield(); }
}
