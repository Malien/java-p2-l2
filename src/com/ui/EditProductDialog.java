package com.ui;

import com.data.Product;
import com.util.StringRegExChecker;

import javax.swing.*;
import java.awt.*;

public class EditProductDialog extends JDialog {
    private MainFrame parentFrame;
    private Product currentProduct;
    private JButton submitButton;
    private JPanel mainPanel;
    private JTextField productNameTextField;
    private JTextField productManufacturerTextField;
    private JTextField productPriceTextField;
    private JTextArea productDescTextArea;

    EditProductDialog(MainFrame parentFrame, Product currentProduct) {
        super(parentFrame, ModalityType.APPLICATION_MODAL);
        this.parentFrame = parentFrame;
        this.currentProduct = currentProduct;
        setupTextFields();
        this.add(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 600));
        this.setLocationRelativeTo(null);
        addListener();
    }

    private void addListener() {
        submitButton.addActionListener(e -> {
            String newProductName = productNameTextField.getText();
            if (!productNameTextField.getText().isEmpty()) {
                parentFrame.getCurrentGroup().remove(currentProduct);
                if (parentFrame.cache.prodNameIsUnique(newProductName)) {
                    if (!productManufacturerTextField.getText().isEmpty()) {
                        parentFrame.getCurrentGroup().add(currentProduct);
                        if (StringRegExChecker.checkDouble(productPriceTextField.getText())) {
                            currentProduct.productRefactor(newProductName, productDescTextArea.getText(),
                                    productManufacturerTextField.getText(), Float.parseFloat(productPriceTextField.getText()));
                            dispose();
                            parentFrame.cache.set(parentFrame.getCurrentGroup());
                            parentFrame.reload();
                        } else
                            JOptionPane.showMessageDialog(null, "Помилка в ціні товару", "Помилка!",
                                    JOptionPane.ERROR_MESSAGE);
                    } else
                        JOptionPane.showMessageDialog(null, "Помилка в імені виробника товару", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(null, "Товар з даним іменем вже існує", "Помилка!",
                            JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Помилка в імені товару", "Помилка!",
                        JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setupTextFields() {
        productNameTextField.setText(currentProduct.getName());
        productDescTextArea.setText(currentProduct.getDescription());
        productManufacturerTextField.setText(currentProduct.getManufacturer());
        productPriceTextField.setText(String.valueOf(currentProduct.getPrice()));
    }
}