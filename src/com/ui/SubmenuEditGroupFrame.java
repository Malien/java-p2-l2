package com.ui;

import com.data.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class SubmenuEditGroupFrame extends JFrame {
    private JPanel mainPanel;
    private JButton submitButton;
    private JTextField groupNameTextField;
    private JTextField groupDescTextField;
    private ProductGroup productGroup;
    private JButton saveChangesButton;
    private JTextField nameTextField;
    private JTextArea descTextArea;

    SubmenuEditGroupFrame(ProductGroup productGroup){
        this.productGroup = productGroup;
        this.setPreferredSize(new Dimension(300,300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void setup(){
        nameTextField.setText(productGroup.getName());
        descTextArea.setText(productGroup.getDesc());
    }
}
