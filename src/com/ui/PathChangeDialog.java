package com.ui;

import com.components.IDatabase;
import com.components.Workspace;

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
        setTitle("Зміна робочої області");
        setPreferredSize(new Dimension(500, 130));
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        this.db = db;

        buttonOK.addActionListener(e ->
            onOK()
        );

        buttonCancel.addActionListener(e ->
            onCancel()
        );

        filePickerButton.addActionListener(e -> {
            String path = Workspace.askPath(this);
            if (path != null) {
                pathField.setText(path);
            }
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
        db.setPath(pathField.getText(), valid -> {
            if (valid) dispose();
            else JOptionPane.showMessageDialog(null, "Хибний шлях!", "Помилка", JOptionPane.ERROR_MESSAGE);

        });
    }

    private void onCancel() {
        dispose();
    }

}
