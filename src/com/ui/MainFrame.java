package com.ui;

import com.data.FrontBackConnection;
import com.data.Product;
import com.data.ProductGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JTextField addItemValTextField;
    private JButton addItemPlusButton;
    private JButton addItemMinusButton;
    private JLabel currentGroupLabel;
    private JButton writeOffPlusButton;
    private JButton writeOffMinusButton;
    private JTextField writeOffValTextField;
    private JScrollPane tableScrollPane;
    private JMenuBar menuBar;
    private JButton exitButton;
    private JTextField searchMenuTextField;
    private JButton searchMenuButton;
    private JMenuItem dataBaseMenuItem;
    private JMenuItem statisticsWarehouseMenuItem;
    private JMenuItem helpMenuItem;
    private JMenuItem statisticsGroupMenuItem;
    private JMenuItem countWarehousePriceMenuItem;
    private JMenuItem countGroupPriceMenuItem;
    private FrontBackConnection conn;

    public MainFrame(FrontBackConnection conn) {
        this.conn = conn;
        addTestGroupList();
        setupMenuBar();
        setupTable();
        addButtonListeners();
        addMenuListeners();
        setupFrame();
    }

    private void addTestGroupList() {
        conn.getGroupList().add(new ProductGroup("first group","fdfd"));
        conn.getGroupList().add(new ProductGroup("second group",""));
        conn.getGroupList().get(0).add(new Product("product 1.1", "desc for 1.1", "man", 1, 10));
        conn.getGroupList().get(0).add(new Product("product 1.2", "desc for 1.2", "manuf", 2, 12));
        conn.getGroupList().get(1).add(new Product("product 2.1", "desc for 2.1", "manufac", 3, 15));
        conn.getGroupList().get(1).add(new Product("product 2.1", "desc for 2.2", "manufacturer", 4, 23));
    }

    private void addMenuListeners() {
        dataBaseMenuItem.addActionListener(actionEvent -> {

        });
        statisticsWarehouseMenuItem.addActionListener(actionEvent -> {

        });
        helpMenuItem.addActionListener(actionEvent -> {

        });
        statisticsGroupMenuItem.addActionListener(actionEvent -> {

        });
        countWarehousePriceMenuItem.addActionListener(actionEvent -> {

        });
        countGroupPriceMenuItem.addActionListener(actionEvent -> {

        });
    }

    @SuppressWarnings("Duplicates")
    private void addButtonListeners() {
        choseGroupButton.addActionListener(actionEvent -> {

        });
        editGroupsButton.addActionListener(actionEvent -> {
            EditGroupsFrame editGroupsFrame = new EditGroupsFrame(conn);
            editGroupsFrame.setVisible(true);
        });
        addItemsButton.addActionListener(actionEvent -> {

        });
        addItemPlusButton.addActionListener(actionEvent -> {

        });
        addItemMinusButton.addActionListener(actionEvent -> {

        });
        writeOffItemsButton.addActionListener(actionEvent -> {

        });
        writeOffPlusButton.addActionListener(actionEvent -> {

        });
        writeOffMinusButton.addActionListener(actionEvent -> {

        });
        editItemButton.addActionListener(actionEvent -> {

        });
        removeItemButton.addActionListener(actionEvent -> {

        });
        itemDescButton.addActionListener(actionEvent -> {

        });
        currentGroupDescButton.addActionListener(actionEvent -> {

        });
        searchMenuButton.addActionListener(actionEvent -> {

        });
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
        this.setJMenuBar(menuBar);
    }

    private void addDataBaseMenu() {
        JMenu dataBaseMenu = new JMenu("База даних");
        dataBaseMenuItem = new JMenuItem("sample");
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
        helpMenuItem = new JMenuItem("sample");
        helpMenu.add(helpMenuItem);
    }

    private void addStatisticsMenu() {
        JMenu statisticsMenu = new JMenu("Статистика");

        JMenu showSubmenu = new JMenu("Показати товари");
        statisticsMenu.add(showSubmenu);
        statisticsWarehouseMenuItem = new JMenuItem("На складі");
        showSubmenu.add(statisticsWarehouseMenuItem);
        statisticsGroupMenuItem = new JMenuItem("У групі");
        showSubmenu.add(statisticsGroupMenuItem);

        JMenu countPriceSubmenu = new JMenu("Порахувати");
        statisticsMenu.add(countPriceSubmenu);
        countWarehousePriceMenuItem = new JMenuItem("На складі");
        countPriceSubmenu.add(countWarehousePriceMenuItem);
        countGroupPriceMenuItem = new JMenuItem("У групі");
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
