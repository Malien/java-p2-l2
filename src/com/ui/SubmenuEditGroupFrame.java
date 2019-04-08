package com.ui;

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

    SubmenuEditGroupFrame(ProductGroup productGroup) {
        this.productGroup = productGroup;
        this.setPreferredSize(new Dimension(300, 300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        saveChangesButton.addActionListener(e -> {
            if (StringRegExChecker.checkName(nameTextField.getText())) {
                productGroup.setName(nameTextField.getText());
                productGroup.setDesc(descTextArea.getText());
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
