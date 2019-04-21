package com.ui;

import com.data.Cache;
import com.data.Product;
import com.util.StringRegExChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class AddItemDialog extends JDialog {
    private JButton submitButton;
    private JTextField productNameTextField;
    private JTextField productManufacturerTextField;
    private JTextField productPriceTextField;
    private JTextField productCountTextField;
    private JTextPane productDescTextPane;
    private JPanel mainPanel;
    private Cache cache;
    private MainFrame parentFrame;

    public AddItemDialog(MainFrame parentFrame, Cache cache) {
        super(parentFrame, Dialog.ModalityType.APPLICATION_MODAL);
        this.cache = cache;
        this.parentFrame = parentFrame;
        this.setPreferredSize(new Dimension(300, 400));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    @SuppressWarnings("Duplicates")
    private void addListeners() {
        submitButton.addActionListener(e -> {
            String newProductName = productNameTextField.getText();
            if (!newProductName.isEmpty()) {
                if (!productManufacturerTextField.getText().isEmpty()) {
                    if (StringRegExChecker.checkDouble(productPriceTextField.getText())) {
                        if (cache.prodNameIsUnique(newProductName)) {
                            if (StringRegExChecker.checkInteger(productCountTextField.getText())) {
                                parentFrame.getCurrentGroup().add(new Product(newProductName, productDescTextPane.getText(),
                                        productManufacturerTextField.getText(), Integer.parseInt(productCountTextField.getText()),
                                        Float.parseFloat(productPriceTextField.getText())));
                                dispose();
                                cache.set(parentFrame.getCurrentGroup());
                                parentFrame.reload();
                            } else
                                JOptionPane.showMessageDialog(null, "Помилка в кількості товару", "Помилка!",
                                        JOptionPane.ERROR_MESSAGE);
                        } else
                            JOptionPane.showMessageDialog(null, "Продукт з даним ім'ям вже існує!", "Помилка!",
                                    JOptionPane.ERROR_MESSAGE);
                    } else
                        JOptionPane.showMessageDialog(null, "Помилка в ціні товару", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(null, "Помилка в назві виробника товару", "Помилка!",
                            JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Помилка в імені товару", "Помилка!",
                        JOptionPane.ERROR_MESSAGE);
        });
    }

    private void createUIComponents() {
        productPriceTextField = new JTextField() {
            @Override
            public void processKeyEvent(KeyEvent ev) {
                char ch = ev.getKeyChar();
                if (Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE ||
                        ch == KeyEvent.VK_PERIOD) {
                    super.processKeyEvent(ev);
                }
                ev.consume();
                return;
            }
        };

        productCountTextField = new JTextField() {
            @Override
            public void processKeyEvent(KeyEvent ev) {
                char ch = ev.getKeyChar();
                if (Character.isDigit(ch) || ch == KeyEvent.VK_BACK_SPACE) {
                    super.processKeyEvent(ev);
                }
                ev.consume();
                return;
            }
        };
    }
}
