package com.ui;

import com.data.FrontBackConnection;

import javax.swing.*;
import java.awt.*;


public class EditGroupsFrame extends JFrame {
    private JPanel mainPanel;
    private JButton addGroupButton;
    private JButton removeSelectedGroupButton;
    private JButton editSelectedGroupButton;
    private JList list;
    private FrontBackConnection conn;
    public DefaultListModel<String> listModel;

    EditGroupsFrame(FrontBackConnection conn) {
        this.conn = conn;
        this.setPreferredSize(new Dimension(320, 300));
        this.setMinimumSize(new Dimension(320, 300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        listSetup();
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        addGroupButton.addActionListener(e -> {
            //FIXME: Bug found if you close the window with entering any info the last group will duplicate on the
            // screen
            AddGroupDialog addGroupDialog = new AddGroupDialog(this, conn);
            addGroupDialog.setVisible(true);
        });

        editSelectedGroupButton.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                System.out.println(conn.getGroupList().get(list.getSelectedIndex()));
                SubmenuEditGroupFrame editGroupsFrame = new SubmenuEditGroupFrame(conn.getGroupList().get(list.getSelectedIndex()));
                editGroupsFrame.setVisible(true);
            } else
                JOptionPane.showMessageDialog(null, "Вам необхідно вибрати групу, перед тим як редагувати її");
        });

        removeSelectedGroupButton.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                conn.getGroupList().remove(list.getSelectedIndex());
                //TODO: Need urgent fix, but I dunno how to auto-refresh JList (it has only addElement() method and
                // works not as JTable or JTree with models
                dispose();
            } else
                JOptionPane.showMessageDialog(null, "Вам необхідно вибрати групу, перед тим як видалити її");
        });
    }

    public void listSetup() {
        listModel = new DefaultListModel<>();

        for (int i = 0; i < conn.getGroupList().size(); i++) {
            listModel.addElement(conn.getGroupList().get(i).getName());
        }

        list.setModel(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }
}
