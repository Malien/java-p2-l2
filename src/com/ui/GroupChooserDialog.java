package com.ui;

import com.data.Cache;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GroupChooserDialog extends JDialog {
    private Cache cache;
    private JButton submitButton;
    private JTable table;
    private JPanel mainPanel;
    private MainFrame parentFrame;

    GroupChooserDialog(MainFrame parentFrame, Cache cache) {
        super(parentFrame, ModalityType.APPLICATION_MODAL);
        this.parentFrame = parentFrame;
        this.cache = cache;
        setTitle("Вибір групи");
        this.setPreferredSize(new Dimension(320, 300));
        this.setMinimumSize(new Dimension(320, 300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setup();
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        submitButton.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                parentFrame.setCurrentGroup(cache.get(table.getSelectedRow()));
                dispose();
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });
    }

    private void setup() {
        String[] column = {"Назва", "Опис"};
        String[][] data = new String[cache.getCache().size()][2];

        for (int i = 0; i < cache.getCache().size(); i++) {
            data[i][0] = cache.get(i).getName();
            data[i][1] = cache.get(i).getDesc();
        }

        DefaultTableModel model = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(model);
    }
}
