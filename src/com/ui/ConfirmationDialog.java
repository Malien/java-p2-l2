package com.ui;

import javax.swing.*;
import java.awt.*;

public class ConfirmationDialog extends JDialog {

    private boolean isConfirmationGot;
    private Frame parentFrame;
    private JPanel mainPanel;
    private JButton cancelButton;
    private JButton confirmButton;
    private JLabel textLabel;

    ConfirmationDialog(Frame parentFrame, String text) {
        super(parentFrame, Dialog.ModalityType.APPLICATION_MODAL);
        this.parentFrame = parentFrame;

        textLabel.setText(text);

        this.setPreferredSize(new Dimension(400, 150));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }


    public boolean isConfirmationGot() {
        return isConfirmationGot;
    }

    private void addListeners(){
        cancelButton.addActionListener(e->{
            isConfirmationGot = false;
            dispose();
        });

        confirmButton.addActionListener(e->{
            isConfirmationGot = true;
            dispose();
        });
    }
}
