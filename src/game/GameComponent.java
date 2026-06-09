package game;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

abstract class GameComponent {
    public abstract void update();
    public abstract void draw(Graphics2D g2d);
    public abstract Rectangle2D getBounds();
}
