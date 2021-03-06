package com.ui;

import com.data.Cache;
import com.data.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EditGroupsFrame extends JFrame {
    private JPanel mainPanel;
    private JButton addGroupButton;
    private JButton removeSelectedGroupButton;
    private JButton editSelectedGroupButton;
    JList list;
    private Cache cache;
    private MainFrame mainFrame;

    EditGroupsFrame(MainFrame mainFrame,Cache cache) {
        this.cache = cache;
        this.mainFrame = mainFrame;
        setTitle("Редагування групи");
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
                String groupName = (String) list.getSelectedValue();
                SubmenuEditGroupDialog editGroupsFrame =
                        new SubmenuEditGroupDialog(this, cache, cache.get(groupName));
                editGroupsFrame.setVisible(true);
            } else
                JOptionPane.showMessageDialog(null, "Вам необхідно вибрати групу, перед тим як редагувати її");
        });

        removeSelectedGroupButton.addActionListener(e -> {
            if (list.getSelectedIndex() != -1) {
                cache.remove((String) list.getSelectedValue());
                cache.reload();
                mainFrame.setCurrentGroup(new ProductGroup("не вибрана", "empty group"));
                mainFrame.cleanTable();
                listRefresh();
            } else
                JOptionPane.showMessageDialog(null, "Вам необхідно вибрати групу, перед тим як видалити її");
        });
    }

    void listRefresh() {
        ArrayList<String> listData = new ArrayList<>(cache.getCache().size());

        for (ProductGroup group : cache) {
            listData.add(group.getName());
        }

        list.setListData(listData.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
