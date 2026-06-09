package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.swing.JPanel;
import javax.swing.Timer;

import ui.Logger;

public class Game {

	private GameState state = new MenuState(this);
    public Character player = new BaseCharacter(100, 300);
    private Character decoratedPlayer = player;
    private List<PowerUpDecorator> activeDecorators = new ArrayList<>();
    private CompositeLevel level = new CompositeLevel();
    public int score = 0;
    private Set<String> keys = new HashSet<>();
    private Timer gameTimer;
    private long lastUpdate = 0;

    public Game(JPanel panel) {
        initLevel();
        Logger.log("INFO", "Game initialized");
        
        gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastUpdate > 16) {
                    update();
                    panel.repaint();
                    lastUpdate = currentTime;
                }
            }
        });
        gameTimer.start();
    }

    private void initLevel() {
        // Platforms
        level.add(EntityFactory.createPlatform(0, 550, 800, 50));
        level.add(EntityFactory.createPlatform(200, 450, 150, 20));
        level.add(EntityFactory.createPlatform(400, 350, 150, 20));
        level.add(EntityFactory.createPlatform(100, 250, 100, 20));
        level.add(EntityFactory.createPlatform(600, 250, 150, 20));

        // Enemies
        level.add(EntityFactory.createEnemy("normal", 250, 420));
        level.add(EntityFactory.createEnemy("fast", 450, 320));
        level.add(EntityFactory.createEnemy("normal", 650, 220));

        // Power-ups
        level.add(EntityFactory.createPowerUp("speed", 300, 400));
        level.add(EntityFactory.createPowerUp("shield", 500, 300));
        level.add(EntityFactory.createPowerUp("strength", 150, 200));
    }

    public void setState(GameState state) {
        if (this.state != null) this.state.exit();
        this.state = state;
        this.state.enter();
    }

    public void handleInput(String key) {
    	if ("R".equals(key)) {
            reset();
            setState(new PlayingState(this));
            return;
        }
    	
        this.state.handleInput(key);
    }

    public void update() {
        state.update();

        if (!(state instanceof PlayingState)) return;

        // Update decorators
        activeDecorators.removeIf(decorator -> {
            if (decorator.isExpired()) {
                decorator.remove();
                return true;
            }
            return false;
        });

        if (activeDecorators.isEmpty()) {
            decoratedPlayer = player;
        } else {
            decoratedPlayer = activeDecorators.get(activeDecorators.size() - 1);
        }

        // Update player
        List<Platform> platforms = level.getPlatforms();
        decoratedPlayer.update(keys, platforms);

        // Update level components
        level.update();

        // Collision detection
        checkCollisions();
    }

    private void checkCollisions() {
        Rectangle2D playerBounds = new Rectangle2D.Double(
            player.getX(), player.getY(), player.getWidth(), player.getHeight()
        );

        for (GameComponent component : level.getComponents()) {
            if (component instanceof Enemy enemy && enemy.isAlive()) {
                Rectangle2D bounds = enemy.getBounds();
                if (playerBounds.intersects(bounds)) {
                    if (decoratedPlayer.hasShield()) {
                        Logger.log("EVENT", "Shield blocked enemy damage");
                    } else {
                        player.health--;
                        Logger.log("EVENT", String.format("Player hit by enemy! Health: %d", player.getHealth()));
                        player.x = 100;
                        player.y = 300;
                    }
                    enemy.kill();
                    level.remove(enemy);
                }
            } else if (component instanceof PowerUp powerUp && !powerUp.isCollected()) {
                Rectangle2D bounds = powerUp.getBounds();
                if (playerBounds.intersects(bounds)) {
                    powerUp.collect();
                    applyPowerUp(powerUp.getType());
                    score += 100;
                    Logger.log("EVENT", String.format("Power-up collected: %s. Score: %d", powerUp.getType(), score));
                    level.remove(powerUp);
                }
            }
        }
    }

    private void applyPowerUp(String type) {
        Character target = activeDecorators.isEmpty() ? player : activeDecorators.get(activeDecorators.size() - 1);
        PowerUpDecorator decorator;
        switch(type) {
            case "speed":
                decorator = new SpeedBoostDecorator(target, 5000);
                break;
            case "shield":
                decorator = new ShieldDecorator(target, 8000);
                break;
            case "strength":
                decorator = new StrengthBoostDecorator(target, 6000);
                break;
            default:
                return;
        }
        activeDecorators.add(decorator);
    }

    public void reset() {
        Logger.log("INFO", "Game reset");
        score = 0;
        player.reset();
        decoratedPlayer = player;
        activeDecorators.clear();
        level = new CompositeLevel();
        initLevel();
    }

    public void draw(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(15, 23, 42));
        g2d.fillRect(0, 0, width, height);

        level.draw(g2d);
        decoratedPlayer.draw(g2d);

        // HUD
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 10, 30);
        
        String hearts = "♥".repeat(player.getHealth());
        g2d.drawString("Health: " + hearts, 10, 60);

        // Active power-ups
        int yOffset = 90;
        for (PowerUpDecorator decorator : activeDecorators) {
            long remaining = (decorator.duration - (System.currentTimeMillis() - decorator.startTime)) / 1000;
            String name = decorator.decoratorName.replace("Decorator", "");
            g2d.drawString(name + ": " + remaining + "s", 10, yOffset);
            yOffset += 25;
        }

        // State overlay
        if (state instanceof MenuState) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, 800, 600);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            drawCenteredText(g2d, "MINI PLATFORMER", 400, 250);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            drawCenteredText(g2d, "Press ENTER to Start", 400, 300);
            drawCenteredText(g2d, "Arrow Keys: Move | Space: Jump", 400, 350);
        } else if (state instanceof PausedState) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, 800, 600);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            drawCenteredText(g2d, "PAUSED", 400, 280);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            drawCenteredText(g2d, "Press ESC to Resume", 400, 330);
        } else if (state instanceof GameOverState) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, 800, 600);
            g2d.setColor(new Color(239, 68, 68));
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            drawCenteredText(g2d, "GAME OVER", 400, 250);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            drawCenteredText(g2d, "Final Score: " + score, 400, 300);
            drawCenteredText(g2d, "Press R to Restart", 400, 350);
        } else if (state instanceof VictoryState) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, 800, 600);
            g2d.setColor(new Color(74, 222, 128));
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            drawCenteredText(g2d, "VICTORY!", 400, 250);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            drawCenteredText(g2d, "Final Score: " + score, 400, 300);
            drawCenteredText(g2d, "Press R to Restart", 400, 350);
        }
    }

    private void drawCenteredText(Graphics2D g2d, String text, int x, int y) {
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, x - textWidth / 2, y);
    }

    public void setKeyPressed(String key) {
        keys.add(key);
    }

    public void setKeyReleased(String key) {
        keys.remove(key);
    }

    public GameState getState() {
        return state;
    }
}
