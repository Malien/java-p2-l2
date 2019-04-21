package com.ui;

import com.data.Cache;
import com.data.ProductGroup;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class GroupMenu extends JMenu implements Reloader {

    private Cache cache;

    public GroupMenu(String name, Cache cache){
        super(name);
        this.cache = cache;
        reload();
        addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                reload();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });
    }

    public void reload(){
        removeAll();
        for (ProductGroup group : cache){
            JMenuItem groupItem = new JMenuItem(group.getName());
            groupItem.addActionListener( e -> {
                ShowGroupStatistics groupStats = new ShowGroupStatistics(group);
                groupStats.setVisible(true);
            });
            add(groupItem);
        }
    }

}
