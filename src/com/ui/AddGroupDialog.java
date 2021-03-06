package com.ui;

import com.data.Cache;
import com.data.ProductGroup;

import javax.swing.*;

public class AddGroupDialog extends JDialog {
    private JPanel mainPanel;
    private JButton submitButton;
    private JTextField groupNameTextField;
    private JTextField groupDescTextField;
    private Cache cache;
    private EditGroupsFrame parentFrame;

    AddGroupDialog(EditGroupsFrame parentFrame, Cache cache) {
        super(parentFrame, ModalityType.APPLICATION_MODAL);
        setTitle("Додати групу");
        this.cache = cache;
        this.parentFrame = parentFrame;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        submitButton.addActionListener(e -> {
            String newGroupName = groupNameTextField.getText();
            if (cache.groupNameIsUnique(newGroupName)) {
                if (!newGroupName.isEmpty()) {
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