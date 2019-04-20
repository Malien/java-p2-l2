package com.ui;

import com.data.Cache;
import com.data.ProductGroup;
import com.util.StringRegExChecker;

import javax.swing.*;
import java.awt.*;


public class SubmenuEditGroupFrame extends JFrame {
    private JPanel mainPanel;
    private ProductGroup productGroup;
    private JButton saveChangesButton;
    private JTextField nameTextField;
    private JTextArea descTextArea;
    private Cache cache;
    private EditGroupsFrame parentFrame;

    SubmenuEditGroupFrame(JFrame parentFrame, Cache cache, ProductGroup productGroup) {
        this.productGroup = productGroup;
        this.cache = cache;
        setPreferredSize(new Dimension(300, 300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        saveChangesButton.addActionListener(e -> {
            if (StringRegExChecker.checkName(nameTextField.getText())) {
                cache.remove(productGroup);
                cache.set(new ProductGroup(nameTextField.getText(), descTextArea.getText()));
                cache.reload();
                parentFrame.listRefresh();
                dispose();
            } else
                JOptionPane.showMessageDialog(null, "Помилка в імені групи", "Помилка!",
                        JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setup() {
        nameTextField.setText(productGroup.getName());
        descTextArea.setText(productGroup.getDesc());
    }
}
