package com.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JButton choseGroupButton;
    private JButton editGroupsButton;
    private JButton addItemsButton;
    private JButton writeOffItemsButton;
    private JButton currentGroupDescButton;
    private JButton editItemButton;
    private JButton removeItemButton;
    private JButton itemDescButton;
    private JTextField addItemValTextFieldTextField;
    private JButton addItemPlusButton;
    private JButton addItemMinusButton;
    private JPanel menuBarPanel;
    private JLabel currentGroupLabel;
    private JButton writeOffPlusButton;
    private JButton writeOffMinusButton;
    private JTextField writeOffValTextField;
    private JScrollPane tableScrollPane;
    private JMenuBar menuBar;
    private JButton exitButton;
    private JTextField searchMenuTextField;
    private JButton searchMenuButton;

    public MainFrame() {
        setupMenuBar();
        setupTable();
        setupFrame();
    }

    private void setupTable() {
        JTable table = new JTable(new DefaultTableModel());
        tableScrollPane.add(table);
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        addDataBaseMenu();
        addSearchMenu();
        addStatisticsMenu();
        addHelpMenu();

        // exitButton = new JButton("Вихід з програми");
        // menuBar.add(exitButton);

        this.setJMenuBar(menuBar);
    }

    private void addDataBaseMenu() {
        JMenu dataBaseMenu = new JMenu("База даних");
        JMenuItem dataBaseMenuItem = new JMenuItem("sample");
        dataBaseMenu.add(dataBaseMenuItem);
        menuBar.add(dataBaseMenu);
    }

    private void addSearchMenu() {
        JMenu searchMenu = new JMenu("Пошук");
        JPanel searchMenuPanel = new JPanel();
        searchMenuPanel.setPreferredSize(new Dimension(200, 25));
        searchMenuPanel.setLayout(new BorderLayout());
        searchMenuTextField = new JTextField();
        searchMenuButton = new JButton("ОК");
        //(int top, int left, int bottom, int right);
        searchMenuButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        searchMenuPanel.add(searchMenuTextField, BorderLayout.CENTER);
        searchMenuPanel.add(searchMenuButton, BorderLayout.EAST);
        searchMenuPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        searchMenu.add(searchMenuPanel);
        menuBar.add(searchMenu);
    }

    private void addHelpMenu() {
        JMenu helpMenu = new JMenu("Довідка");
        menuBar.add(helpMenu);
        JMenuItem helpMenuItem = new JMenuItem("sample");
        helpMenu.add(helpMenuItem);
    }

    private void addStatisticsMenu() {
        JMenu statisticsMenu = new JMenu("Статистика");

        JMenu showSubmenu = new JMenu("Показати товари");
        statisticsMenu.add(showSubmenu);
        JMenuItem statisticsWarehouseMenuItem = new JMenuItem("На складі");
        showSubmenu.add(statisticsWarehouseMenuItem);
        JMenuItem statisticsGroupMenuItem = new JMenuItem("У групі");
        showSubmenu.add(statisticsGroupMenuItem);

        JMenu countPriceSubmenu = new JMenu("Порахувати");
        statisticsMenu.add(countPriceSubmenu);
        JMenuItem countWarehousePriceMenuItem = new JMenuItem("На складі");
        countPriceSubmenu.add(countWarehousePriceMenuItem);
        JMenuItem countGroupPriceMenuItem = new JMenuItem("У групі");
        countPriceSubmenu.add(countGroupPriceMenuItem);

        menuBar.add(statisticsMenu);
    }

    private void setupFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
