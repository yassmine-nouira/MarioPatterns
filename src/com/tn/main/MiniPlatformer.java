package com.tn.main;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import ui.GamePanel;

public class MiniPlatformer extends JFrame {
    public MiniPlatformer() {
        setTitle("Mini Platformer - Design Patterns");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveLogsItem = new JMenuItem("Save Logs...");
        saveLogsItem.addActionListener(e -> gamePanel.saveLogs());
        JMenuItem clearLogsItem = new JMenuItem("Clear Logs");
        clearLogsItem.addActionListener(e -> gamePanel.clearLogs());
        fileMenu.add(saveLogsItem);
        fileMenu.add(clearLogsItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        gamePanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MiniPlatformer::new);
    }
}
