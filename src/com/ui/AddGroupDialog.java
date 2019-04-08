package com.ui;

import com.data.FrontBackConnection;
import com.data.ProductGroup;
import com.util.NameChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddGroupDialog extends JDialog {
    private JPanel mainPanel;
    private JButton submitButton;
    private JTextField groupNameTextField;
    private JTextField groupDescTextField;
    private FrontBackConnection conn;
    private EditGroupsFrame parentFrame;

    AddGroupDialog(EditGroupsFrame parentFrame, FrontBackConnection conn) {
        super(parentFrame,ModalityType.APPLICATION_MODAL);
        this.conn = conn;
        this.parentFrame=parentFrame;
        this.setPreferredSize(new Dimension(300, 300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        submitButton.addActionListener(e -> {
            if (NameChecker.check(groupNameTextField.getText())) {
                conn.getGroupList().add(new ProductGroup(groupNameTextField.getText(), groupDescTextField.getText()));
                dispose();
            } else
                JOptionPane.showMessageDialog(null, "Помилка в імені групи", "Помилка!",
                        JOptionPane.ERROR_MESSAGE);
        });

        //TODO: KOSTYL, NADO FIXIT PAZANY
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parentFrame.listModel.addElement(conn.getGroupList().get(conn.getGroupList().size()-1).getName());
            }
        });
    }
}
