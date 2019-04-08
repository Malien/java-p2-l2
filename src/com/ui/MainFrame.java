package com.ui;

import com.data.FrontBackConnection;
import com.data.Product;
import com.data.ProductGroup;

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
    private JTextField addItemValTextField;
    private JButton addItemPlusButton;
    private JButton addItemMinusButton;
    private JLabel currentGroupLabel;
    private JButton writeOffPlusButton;
    private JButton writeOffMinusButton;
    private JTextField writeOffValTextField;
    private JScrollPane tableScrollPane;
    private JTable table;
    private JMenuBar menuBar;
    private JTextField searchMenuTextField;
    private JButton searchMenuButton;
    private JMenuItem dataBaseMenuItem;
    private JMenuItem statisticsWarehouseMenuItem;
    private JMenuItem helpMenuItem;
    private JMenuItem statisticsGroupMenuItem;
    private JMenuItem countWarehousePriceMenuItem;
    private JMenuItem countGroupPriceMenuItem;
    private FrontBackConnection conn;
    private ProductGroup currentGroup;
    private DefaultTableModel tableModel;

    public MainFrame(FrontBackConnection conn) {
        this.conn = conn;
        addTestGroupList();
        setupMenuBar();
        addButtonListeners();
        addMenuListeners();
        setupFrame();
    }

    private void addTestGroupList() {
        conn.getGroupList().add(new ProductGroup("first group", "fdfd"));
        conn.getGroupList().add(new ProductGroup("second group", ""));
        conn.getGroupList().get(0).add(new Product("product 1.1", "desc for 1.1", "man", 1, 10));
        conn.getGroupList().get(0).add(new Product("product 1.2", "desc for 1.2", "manuf", 2, 12));
        conn.getGroupList().get(1).add(new Product("product 2.1", "desc for 2.1", "manufac", 3, 15));
        conn.getGroupList().get(1).add(new Product("product 2.1", "desc for 2.2", "manufacturer", 4, 23));
    }

    @SuppressWarnings("Duplicates")
    private void addMenuListeners() {
        dataBaseMenuItem.addActionListener(e -> {

        });

        statisticsWarehouseMenuItem.addActionListener(e -> {

        });

        helpMenuItem.addActionListener(e -> {

        });

        statisticsGroupMenuItem.addActionListener(e -> {

        });

        countWarehousePriceMenuItem.addActionListener(e -> {

        });

        countGroupPriceMenuItem.addActionListener(e -> {

        });
    }

    @SuppressWarnings("Duplicates")
    private void addButtonListeners() {
        choseGroupButton.addActionListener(e -> {
            GroupChooserFrame groupChooserFrame = new GroupChooserFrame(this, conn);
            groupChooserFrame.setVisible(true);
        });

        editGroupsButton.addActionListener(e -> {
            EditGroupsFrame editGroupsFrame = new EditGroupsFrame(conn);
            editGroupsFrame.setVisible(true);
        });

        addItemsButton.addActionListener(e -> {

        });

        addItemPlusButton.addActionListener(e -> {

        });

        addItemMinusButton.addActionListener(e -> {

        });

        writeOffItemsButton.addActionListener(e -> {

        });

        writeOffPlusButton.addActionListener(e -> {

        });

        writeOffMinusButton.addActionListener(e -> {

        });

        editItemButton.addActionListener(e -> {
            if (currentGroup != null) {
                if (table.getSelectedRow() != -1) {
                    EditProductDialog editProductDialog =
                            new EditProductDialog(this,currentGroup.get(table.getSelectedRow()));
                    editProductDialog.setVisible(true);
                } else
                    JOptionPane.showMessageDialog(null, "Необхідно вибрати продукт!");
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        removeItemButton.addActionListener(e -> {
            if (currentGroup != null) {
                if (table.getSelectedRow() != -1) {
                    currentGroup.remove(table.getSelectedRow());
                    refreshTableModel();
                } else
                    JOptionPane.showMessageDialog(null, "Необхідно вибрати продукт!");
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        itemDescButton.addActionListener(e -> {
            if (currentGroup != null) {
                if (table.getSelectedRow() != -1) {
                    Product currentProduct = currentGroup.get(table.getSelectedRow());
                    DescFrame descFrame = new DescFrame("Товар: " + currentProduct.getName(),
                            currentProduct.getDescription());
                    descFrame.setVisible(true);
                } else
                    JOptionPane.showMessageDialog(null, "Необхідно вибрати продукт!");
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        currentGroupDescButton.addActionListener(e -> {
            if (currentGroup != null) {
                DescFrame descFrame = new DescFrame("Група: " + currentGroup.getName(), currentGroup.getDesc());
                descFrame.setVisible(true);
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        searchMenuButton.addActionListener(e -> {

        });
    }

    //FIXME: Costyl
    private void refreshTableModel() {
        String[] column = {"Назва", "Виробник", "Кількість на складі", "Ціна за одиницю"};
        String[][] data = new String[currentGroup.getProducts().length][4];

        for (int i = 0; i < currentGroup.getProducts().length; i++) {
            Product tempProduct = currentGroup.get(i);
            data[i][0] = tempProduct.getName();
            data[i][1] = tempProduct.getManufacturer();
            data[i][2] = String.valueOf(tempProduct.getCount());
            data[i][3] = String.valueOf(tempProduct.getPrice());
        }
        tableModel = new DefaultTableModel(data, column);
        table.setModel(tableModel);
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

    void setCurrentGroup(ProductGroup group) {
        currentGroup = group;
        //TODO: cleanup and remove test
        System.out.println(currentGroup);
        refreshTableModel();
        currentGroupLabel.setText("Поточна група: " + currentGroup.getName());
    }
}
