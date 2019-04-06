package com.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WelcomeFrame extends JFrame {
    private JButton startButton;
    private JPanel welcomePanel;
    private JComboBox pathComboBox;
    private JButton quitButton;
    ArrayList<String> pathCache;

    public WelcomeFrame() {
        setupFrame();
        setupComboBox();
        setupButtons();
    }

    private void setupButtons() {
        //TODO: For Yaroslav
        // - change font of text in both buttons
    }

    private void setupComboBox() {
        pathComboBox.setEditable(true);
        String pathCacheTest = "test1";
        pathComboBox.addItem(pathCacheTest);
        //TODO: For Andrew
        // - add all previous path locations to the combo box
        // - add method for adding and removing path cache
    }

    private void setupFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 130));

        this.add(welcomePanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
