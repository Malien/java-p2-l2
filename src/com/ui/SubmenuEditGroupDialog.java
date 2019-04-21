package com.ui;

import com.data.Cache;
import com.data.ProductGroup;

import javax.swing.*;
import java.awt.*;


public class SubmenuEditGroupDialog extends JDialog {
    private JPanel mainPanel;
    private ProductGroup productGroup;
    private JButton saveChangesButton;
    private JTextField nameTextField;
    private JTextArea descTextArea;
    private Cache cache;
    private EditGroupsFrame parentFrame;

    SubmenuEditGroupDialog(EditGroupsFrame parentFrame, Cache cache, ProductGroup productGroup) {
        super(parentFrame,ModalityType.APPLICATION_MODAL);
        this.parentFrame = parentFrame;
        this.productGroup = productGroup;
        this.cache = cache;
        setTitle("Редагування групи " + productGroup.getName());
        setMinimumSize(new Dimension(300, 300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        saveChangesButton.addActionListener(e -> {
            if (!nameTextField.getText().isEmpty()) {
                java.util.List temp = productGroup.getProductList();
                cache.remove(productGroup);
                cache.set(new ProductGroup(nameTextField.getText(), descTextArea.getText(), temp));
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
