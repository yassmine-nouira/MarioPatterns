package game;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Set;

import ui.Logger;

public abstract class Character {
    protected double baseX, baseY;
    protected double x, y;
    protected int width = 30, height = 30;
    protected int health = 3;
    protected double velocityX = 0, velocityY = 0;
    protected boolean onGround = false;
    protected int direction = 1;
    protected PlayerState state = new IdleState(this);

    public Character(double x, double y) {
        this.baseX = this.x = x;
        this.baseY = this.y = y;
    }

    public void setState(PlayerState state) {
        if (this.state != null) this.state.exit();
        this.state = state;
        this.state.enter();
    }

    public abstract double getSpeed();
    public abstract int getStrength();
    public abstract boolean hasShield();
    
    public void update(Set<String> keys, List<Platform> platforms) {
        state.handleInput(keys);
        state.update();

        velocityY += 0.5; // Gravity
        x += velocityX;
        y += velocityY;

        // Platform collision
        onGround = false;
        for (Platform platform : platforms) {
            if (x + width > platform.x &&
                x < platform.x + platform.width &&
                y + height > platform.y &&
                y + height < platform.y + 20 &&
                velocityY > 0) {
                y = platform.y - height;
                velocityY = 0;
                onGround = true;
            }
        }

        // Boundaries
        if (x < 0) x = 0;
        if (x > 770) x = 770;
        if (y > 600) {
            health = 0;
            Logger.log("EVENT", "Player fell off the map");
        }
    }

    public abstract void draw(Graphics2D g2d);
    
    public void reset() {
        x = baseX;
        y = baseY;
        health = 3;
        velocityX = 0;
        velocityY = 0;
        setState(new IdleState(this));
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getHealth() { return health; }
}