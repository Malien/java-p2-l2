package com.ui;

import javax.swing.*;
import java.awt.*;

public class DescFrame extends JFrame {
    private JPanel mainPanel;
    private JButton exitButton;
    private JTextArea descTextArea;
    private JLabel itemTypeLabel;

    DescFrame(String typeOfItem, String desc) {
        this.setPreferredSize(new Dimension(320, 300));
        this.setMinimumSize(new Dimension(320, 300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup(typeOfItem, desc);
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    private void setup(String typeOfItem, String desc) {
        itemTypeLabel.setText(typeOfItem);
        descTextArea.setText(desc);
        descTextArea.setLineWrap(true);
        descTextArea.setEditable(false);
    }

    private void addListeners() {
        exitButton.addActionListener(e ->
                dispose()
        );
    }
}