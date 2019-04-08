package com.ui;

import com.data.Cache;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GroupChooserFrame extends JFrame {
    private Cache cache;
    private JButton submitButton;
    private JTable table;
    private JPanel mainPanel;
    private MainFrame parentFrame;

    GroupChooserFrame(MainFrame parentFrame, Cache cache) {
        this.parentFrame = parentFrame;
        this.cache = cache;
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
                parentFrame.setCurrentGroup(cache.getGroupList().get(table.getSelectedRow()));
                dispose();
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });
    }

    private void setup() {
        String[] column = {"Назва", "Опис"};
        String[][] data = new String[cache.getGroupList().size()][2];

        for (int i = 0; i < cache.getGroupList().size(); i++) {
            data[i][0] = cache.getGroupList().get(i).getName();
            data[i][1] = cache.getGroupList().get(i).getDesc();
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
