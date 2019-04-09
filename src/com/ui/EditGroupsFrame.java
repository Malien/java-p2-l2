package com.ui;

import com.data.Cache;

import javax.swing.*;
import java.awt.*;


public class EditGroupsFrame extends JFrame {
    private JPanel mainPanel;
    private JButton addGroupButton;
    private JButton removeSelectedGroupButton;
    private JButton editSelectedGroupButton;
    JList list;
    private Cache cache;
    public DefaultListModel<String> listModel = new DefaultListModel<>();

    EditGroupsFrame(Cache cache) {
        this.cache = cache;
        this.setPreferredSize(new Dimension(350, 300));
        this.setMinimumSize(new Dimension(350, 300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        listRefresh();
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners() {
        addGroupButton.addActionListener(e -> {
            AddGroupDialog addGroupDialog = new AddGroupDialog(this, cache);
            addGroupDialog.setVisible(true);
        });

        editSelectedGroupButton.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                System.out.println(cache.get(list.getSelectedIndex()));
                SubmenuEditGroupFrame editGroupsFrame = new SubmenuEditGroupFrame(cache.get(list.getSelectedIndex()));
                editGroupsFrame.setVisible(true);
            } else
                JOptionPane.showMessageDialog(null, "Вам необхідно вибрати групу, перед тим як редагувати її");
        });

        removeSelectedGroupButton.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                cache.remove(list.getSelectedIndex());
                listRefresh();
            } else
                JOptionPane.showMessageDialog(null, "Вам необхідно вибрати групу, перед тим як видалити її");
        });
    }

    //FIXME: Need fix, but I dunno how to auto-refresh JList
    void listRefresh() {
        String[] listData = new String[cache.getCache().size()];

        for (int i = 0; i < cache.getCache().size(); i++) {
            listData[i] = cache.get(i).getName();
        }

        list.setListData(listData);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
