package com.ui;

import com.data.Product;

import javax.swing.*;
import java.awt.*;

public class EditProductDialog extends JDialog {
    private MainFrame parentFrame;
    private Product currentProduct;
    private JButton submitChangesButton;
    private JPanel mainPanel;

    EditProductDialog(MainFrame parentFrame, Product currentProduct) {
        super(parentFrame, ModalityType.APPLICATION_MODAL);
        this.parentFrame = parentFrame;
        this.currentProduct = currentProduct;
        this.add(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(300, 300));
        this.setLocationRelativeTo(null);
    }

}
