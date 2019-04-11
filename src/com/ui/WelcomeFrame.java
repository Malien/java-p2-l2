package com.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WelcomeFrame extends JFrame {
    private JButton startButton;
    private JPanel welcomePanel;
    private JComboBox pathComboBox;
    private JButton quitButton;
    private JButton button1;
    ArrayList<String> pathCache;

    public WelcomeFrame() {
        setupFrame();
        setupComboBox();
        setupButtons();
    }

    private void setupButtons() {
    }

    private void setupComboBox() {
        pathComboBox.setEditable(true);
        String pathCacheTest = "test1";
        pathComboBox.addItem(pathCacheTest);
    }

    private void setupFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 130));

        this.add(welcomePanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
