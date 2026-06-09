package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import java.io.*;
import java.text.SimpleDateFormat;


public class Logger {
    private static List<String> logs = new ArrayList<>();
    private static List<java.util.function.Consumer<List<String>>> listeners = new ArrayList<>(); 

    public static void log(String category, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry = String.format("[%s] [%s] %s", timestamp, category, message);
        logs.add(logEntry);
        notifyListeners();
        System.out.println(logEntry);
    }

    public static void subscribe(java.util.function.Consumer<List<String>> listener) {
        listeners.add(listener);
    }

    private static void notifyListeners() {
        for (java.util.function.Consumer<List<String>> listener : listeners) {
            listener.accept(new ArrayList<>(logs));
        }
    }

    public static List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    public static void clear() {
        logs.clear();
        notifyListeners();
    }


}


