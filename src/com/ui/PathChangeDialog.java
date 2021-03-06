package com.ui;

import com.components.DatabaseManager;
import com.components.Workspace;
import com.data.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class PathChangeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField pathField;
    private JButton filePickerButton;
    private MainFrame parentFrame;

    public PathChangeDialog(MainFrame parentFrame) {
        super(parentFrame, ModalityType.APPLICATION_MODAL);
        this.parentFrame = parentFrame;
        setContentPane(contentPane);
        pack();
        setTitle("Зміна робочої області");
        setPreferredSize(new Dimension(500, 130));
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
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
        File dir = new File(pathField.getText());
        if (dir.exists() && dir.isDirectory()) {
            parentFrame.cache.removeStats();
            DatabaseManager.getInstance().setPath(pathField.getText(), valid -> {
                if (valid) {
                    dispose();
                    parentFrame.dispose();
                    Workspace.launch();
                    parentFrame.setCurrentGroup(new ProductGroup("не вибрана", "empty group"));
                    parentFrame.reload();
                } else JOptionPane.showMessageDialog(null, "Хибний шлях!", "Помилка", JOptionPane.ERROR_MESSAGE);
            });
        } else {
            JOptionPane.showMessageDialog(null, "Хибний шлях!", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }

}
