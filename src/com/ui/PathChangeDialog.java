package com.ui;

import com.components.IDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PathChangeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField pathField;
    private JButton filePickerButton;

    private IDatabase db;

    public PathChangeDialog(IDatabase db) {
        setContentPane(contentPane);
        pack();
        setPreferredSize(new Dimension(500, 130));
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.db = db;

        buttonOK.addActionListener(e -> {
            onOK();
        });

        buttonCancel.addActionListener(e -> {
            onCancel();
        });

        filePickerButton.addActionListener(e -> {
            System.setProperty("apple.awt.fileDialogForDirectories", "true");
            FileDialog filePicker = new FileDialog(this, "Select workspace");
            filePicker.setVisible(true);
            pathField.setText(filePicker.getDirectory() + filePicker.getFile());
            System.setProperty("apple.awt.fileDialogForDirectories", "false");
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        db.setPath(pathField.getText(), valid -> {
            if (valid) dispose();
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
