package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Game;

public class GamePanel extends JPanel implements KeyListener {
    private Game game;
    private List<String> logs = new ArrayList<>();
    private boolean showLogs = false;
    private Rectangle logsButtonBounds = new Rectangle();

    
    
    public GamePanel() {
        setPreferredSize(new Dimension(1200, 900));
        setBackground(new Color(15, 23, 42));
        setFocusable(true);
        addKeyListener(this);
        
        Logger.subscribe(logsList -> {
            this.logs = logsList;
            repaint();
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (logsButtonBounds.contains(e.getPoint())) {
                    showLogs = !showLogs;
                    repaint();
                }
            }
        });
        game = new Game(this);
    }

   
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int canvasX = 200;
        int canvasWidth = 600;
        int canvasHeight = 600;

        int offsetY = 220; // space for the title panel at the top
        g2d.translate(0, offsetY);       // move game drawing down
        game.draw(g2d, canvasWidth, canvasHeight);
        g2d.translate(0, -offsetY);      // reset transform
        // Draw UI panels
        drawUI(g2d, canvasX, canvasWidth, canvasHeight);

        g2d.dispose();
    }

    private void drawUI(Graphics2D g2d, int canvasX, int canvasWidth, int canvasHeight) {
        int uiWidth = getWidth() - canvasX - 20;
        
        // Title panel
        drawTitlePanel(g2d, canvasX, 20);
        
        // Controls panel
        drawControlsPanel(g2d, canvasX + canvasWidth + 20, 20);
        
        // Logs panel
        drawLogsPanel(g2d, canvasX + canvasWidth + 20, 250);
    }

    private void drawTitlePanel(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(30, 41, 59));
        g2d.fillRoundRect(x, y, 780, 200, 10, 10);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 26));
        drawText(g2d, "Mini Platformer", x + 20, y + 35);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 15));
        g2d.setColor(new Color(148, 163, 184));
        drawText(g2d, "Design Patterns Implementation Project", x + 20, y + 55);

        // Patterns grid
        String[] patterns = {"State Pattern", "Decorator Pattern", "Composite Pattern", "Factory Pattern"};
        String[] descriptions = {"Game & Player States", "Power-ups System", "Level Structure", "Entity Creation"};
        Color[] colors = {
            new Color(59, 130, 246), new Color(251, 191, 36), 
            new Color(168, 85, 247), new Color(34, 197, 94)
        };

        int patternX = x + 20;
        int patternY = y + 60;
        for (int i = 0; i < 4; i++) {
            g2d.setColor(new Color(51, 65, 85));
            g2d.fillRoundRect(patternX, patternY, 180, 60, 8, 8);
            
            g2d.setColor(colors[i]);
            g2d.drawRoundRect(patternX, patternY, 180, 60, 8, 8);
            
            g2d.setColor(new Color(148, 163, 184));
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            drawText(g2d, patterns[i], patternX + 10, patternY + 20);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            drawText(g2d, descriptions[i], patternX + 10, patternY + 38);
            
            patternX += 195;
            if (i == 1) {
                patternX = x + 20;
                patternY += 75;
            }
        }
    }

    private void drawControlsPanel(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(30, 41, 59));
        g2d.fillRoundRect(x, y, 350, 200, 10, 10);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        drawText(g2d, "Controls:", x + 10, y + 35);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.setColor(new Color(148, 163, 184));
        String[] controls = {
            "← → Arrow Keys: Move",
            "Space: Jump", 
            "Enter: Start Game",
            "Esc: Pause/Resume",
            "R: Restart",
            "Collect power-ups to win!"
        };
        for (int i = 0; i < controls.length; i++) {
            drawText(g2d, controls[i], x + 20, y + 70 + i * 25);
        }
    }

    private void drawLogsPanel(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(30, 41, 59));
        int logsHeight = showLogs ? 500 : 50;
        g2d.fillRoundRect(x, y, 350, logsHeight, 10, 10);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        drawText(g2d, "System Logs", x + 20, y + 25);
        
        if (showLogs) {
            g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
            g2d.setColor(new Color(148, 163, 184));
            
            int logY = y + 100;
            int maxLogs = 25;
            List<String> recentLogs = logs.subList(Math.max(0, logs.size() - maxLogs), logs.size());
            
            for (String log : recentLogs) {
                if (logY > y + logsHeight - 20) break;
                
                Color logColor = Color.WHITE;
                if (log.contains("[STATE]")) logColor = new Color(96, 165, 250);
                else if (log.contains("[DECORATOR]")) logColor = new Color(251, 191, 36);
                else if (log.contains("[EVENT]")) logColor = new Color(239, 68, 68);
                else if (log.contains("[FACTORY]")) logColor = new Color(34, 197, 94);
                else if (log.contains("[COMPOSITE]")) logColor = new Color(168, 85, 247);
                
                g2d.setColor(logColor);
                String displayLog = log.length() > 80 ? log.substring(0, 77) + "..." : log;
                drawText(g2d, displayLog, x + 15, logY, 320);
                logY += 50;
            }
        }
        
        // Log toggle button simulation
        int btnX = x + 250;
        int btnY = y + 10;
        int btnW = 90;
        int btnH = 30;

        g2d.setColor(showLogs ? new Color(37, 99, 235) : new Color(59, 130, 246));
        g2d.fillRoundRect(x + 250, y + 10, 90, 30, 6, 6);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        drawCenteredText(g2d, showLogs ? "Hide Logs" : "Show Logs", btnX + btnW / 2, btnY + 18, btnW);

     // Save bounds for mouse clicks
     logsButtonBounds.setBounds(btnX, btnY, btnW, btnH);
    }

    private void drawText(Graphics2D g2d, String text, int x, int y) {
        drawText(g2d, text, x, y, 0);
    }

    private void drawText(Graphics2D g2d, String text, int x, int y, int maxWidth) {
        if (maxWidth > 0) {
            FontMetrics fm = g2d.getFontMetrics();
            if (fm.stringWidth(text) > maxWidth) {
                int split = text.lastIndexOf(" ", maxWidth / 2);
                if (split > 0) {
                    drawText(g2d, text.substring(0, split), x, y, maxWidth);
                    drawText(g2d, text.substring(split + 1), x, y + 16, maxWidth);
                    return;
                }
            }
        }
        g2d.drawString(text, x, y);
        
    }

    private void drawCenteredText(Graphics2D g2d, String text, int centerX, int y, int width) {
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, centerX - textWidth / 2, y);
    }

    private void updateLogs(List<String> newLogs) {
        this.logs = newLogs;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String key = getKeyName(e.getKeyCode());
        if (key != null) {
            game.setKeyPressed(key);
            game.handleInput(key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String key = getKeyName(e.getKeyCode());
        if (key != null) {
            game.setKeyReleased(key);
        }
        if ("TAB".equals(key)) {
            showLogs = !showLogs;
            repaint();
        }
    }

    private String getKeyName(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT: return "ARROW_LEFT";
            case KeyEvent.VK_RIGHT: return "ARROW_RIGHT";
            case KeyEvent.VK_SPACE: return "SPACE";
            case KeyEvent.VK_ENTER: return "ENTER";
            case KeyEvent.VK_ESCAPE: return "ESCAPE";
            case KeyEvent.VK_R: return "R";
            case KeyEvent.VK_TAB: return "TAB";
            default: return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void saveLogs() {
        try (PrintWriter writer = new PrintWriter("game_logs.txt")) {
            for (String log : Logger.getLogs()) {
                writer.println(log);
            }
            JOptionPane.showMessageDialog(this, "Logs saved to game_logs.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void clearLogs() {
        Logger.clear();
    }
}

