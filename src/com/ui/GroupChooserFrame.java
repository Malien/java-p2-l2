package com.ui;

import com.data.FrontBackConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GroupChooserFrame extends JFrame {
    private FrontBackConnection conn;
    private JButton submitButton;
    private JTable table;
    private JPanel mainPanel;
    private MainFrame parentFrame;

    GroupChooserFrame(MainFrame parentFrame, FrontBackConnection conn) {
        this.parentFrame = parentFrame;
        this.conn = conn;
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
            parentFrame.setCurrentGroup(conn.getGroupList().get(table.getSelectedRow()));
            dispose();
        });
    }

    private void setup() {
        String[] column = {"Назва", "Опис"};
        String[][] data = new String[conn.getGroupList().size()][2];

        for (int i = 0; i < conn.getGroupList().size(); i++) {
            data[i][0] = conn.getGroupList().get(i).getName();
            data[i][1] = conn.getGroupList().get(i).getDesc();
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
