package com.ui;

import com.components.DatabaseManager;
import com.components.Workspace;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
    private JButton startButton;
    private JPanel welcomePanel;
    private JButton quitButton;
    private JButton fileSelectorButton;
    private JTextField pathField;

    public WelcomeFrame() {
        setupFrame();
        setupButtons();
    }

    private void setupButtons() {
        fileSelectorButton.addActionListener( e -> {
            String path = Workspace.askPath(this);
            if (path != null) {
                pathField.setText(path);
            }
        });
        quitButton.addActionListener( e -> {
            dispose();
        });
        startButton.addActionListener( e -> {
            DatabaseManager.getInstance().setPath(pathField.getText(), valid -> {
                if (valid) {
                    Workspace.launch();
                } else {
                    //TODO: display path error to user
                }
            });
        });
    }

    private void setupFrame() {
        this.setTitle("Select workspace");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 130));

        this.add(welcomePanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
