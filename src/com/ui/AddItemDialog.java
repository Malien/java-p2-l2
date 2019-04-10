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
            if (StringRegExChecker.checkName(newProductName)) {
                if (StringRegExChecker.checkDouble(productPriceTextField.getText())) {
                    if (cache.prodNameIsUnique(newProductName)) {
                        parentFrame.getCurrentGroup().add(new Product(newProductName, productDescTextPane.getText(),
                                productManufacturerTextField.getText(), Integer.parseInt(productCountTextField.getText()),
                                Double.parseDouble(productPriceTextField.getText())));
                        dispose();
                        parentFrame.reload();
                    } else
                        JOptionPane.showMessageDialog(null, "Продукт з даним ім'ям вже існує!", "Помилка!",
                                JOptionPane.ERROR_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(null, "Помилка в ціні продукта", "Помилка!",
                            JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Помилка в імені продукта", "Помилка!",
                        JOptionPane.ERROR_MESSAGE);
        });
    }

    @SuppressWarnings("Duplicates")
    private void createUIComponents() {
        productPriceTextField = new JTextField() {
            @Override
            public void processKeyEvent(KeyEvent ev) {
                if (Character.isDigit(ev.getKeyChar()) || ev.getKeyCode() == KeyEvent.VK_BACK_SPACE || ev.getKeyCode() == KeyEvent.VK_PERIOD) {
                    super.processKeyEvent(ev);
                }
                ev.consume();
                return;
            }

            Long getNumber() {
                Long result = null;
                String text = getText();
                if (text != null && !"".equals(text)) {
                    result = Long.valueOf(text);
                }
                return result;
            }
        };

        productCountTextField = new JTextField(){
            @Override
            public void processKeyEvent(KeyEvent ev) {
                if (Character.isDigit(ev.getKeyChar()) || ev.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    super.processKeyEvent(ev);
                }
                ev.consume();
                return;
            }

            Long getNumber() {
                Long result = null;
                String text = getText();
                if (text != null && !"".equals(text)) {
                    result = Long.valueOf(text);
                }
                return result;
            }
        };
    }
}
