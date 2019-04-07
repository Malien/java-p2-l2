package com.ui;

import com.data.FrontBackConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditGroupsFrame extends JFrame {
    private JPanel mainPanel;
    private JButton addGroupButton;
    private JButton removeSelectedGroupButton;
    private JButton editSelectedGroupButton;
    private JList list;
    private FrontBackConnection conn;

    EditGroupsFrame(FrontBackConnection conn) {
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
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGroupFrame addGroupFrame = new AddGroupFrame();
                addGroupFrame.setVisible(true);
            }
        });
        editSelectedGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubmenuEditGroupFrame editGroupsFrame = new SubmenuEditGroupFrame(conn.getGroupList().get(list.getSelectedIndex()));
                editGroupsFrame.setVisible(true);
            }
        });
        removeSelectedGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void setup() {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (int i = 0; i < conn.getGroupList().size(); i++) {
            listModel.addElement(conn.getGroupList().get(i).getName());
        }

        list.setModel(listModel);
    }
}
