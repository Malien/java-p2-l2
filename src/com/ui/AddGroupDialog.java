package com.ui;

import com.data.Cache;
import com.data.ProductGroup;
import com.util.StringRegExChecker;

import javax.swing.*;
import java.awt.*;

public class AddGroupDialog extends JDialog {
    private JPanel mainPanel;
    private JButton submitButton;
    private JTextField groupNameTextField;
    private JTextField groupDescTextField;
    private Cache cache;
    private EditGroupsFrame parentFrame;

    AddGroupDialog(EditGroupsFrame parentFrame, Cache cache) {
        super(parentFrame, ModalityType.APPLICATION_MODAL);

        this.cache = cache;
        this.parentFrame = parentFrame;

        this.setPreferredSize(new Dimension(300, 300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        submitButton.addActionListener(e -> {
            String newGroupName = groupNameTextField.getText();
            if (cache.groupNameIsUnique(newGroupName)) {
                if (StringRegExChecker.checkName(newGroupName)) {
                    cache.set(new ProductGroup(groupNameTextField.getText(), groupDescTextField.getText()));
                    dispose();
                    parentFrame.listRefresh();
                } else
                    JOptionPane.showMessageDialog(null, "Помилка в імені групи", "Помилка!",
                            JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Група з ім'ям вже існує!", "Помилка!",
                        JOptionPane.ERROR_MESSAGE);
        });
    }
}