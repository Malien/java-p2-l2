package com.ui;

import com.data.Cache;
import com.data.Product;
import com.data.ProductGroup;
import com.util.StringRegExChecker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainFrame extends JFrame implements Reloader {
    private JPanel mainPanel;

    private JButton choseGroupButton;
    private JButton editGroupsButton;
    private JButton addItemsButton;
    private JButton writeOffButton;
    private JButton currentGroupDescButton;
    private JButton editItemButton;
    private JButton removeItemButton;
    private JButton itemDescButton;
    private JButton searchMenuButton;

    private JTextField searchMenuTextField;

    private JMenuBar menuBar;
    private JMenuItem reloadMenuItem;
    private JMenuItem changeWorkspaceMenuItem;
    private JMenuItem statisticsMainStorageMenuItem;
    private JMenuItem statisticsGroupMenuItem;
    private JMenuItem showStorageStatistics;
    private JMenu showGroupStatistics;

    private JScrollPane tableScrollPane;
    private JTable table;
    private JLabel currentGroupLabel;
    private JButton addProductButton;
    private JButton sellProductButton;
    private JTextField numberChangeTextField;
    private JTextField searchField;
    private JButton searchButton;
    Cache cache;
    private ProductGroup currentGroup;
    private DefaultTableModel tableModel;

    public MainFrame(Cache cache) {
        this.cache = cache;
        this.cache.setUI(this);
        this.currentGroup = new ProductGroup("null", "empty group");
        this.setResizable(false);
        setupMenuBar();
        addButtonListeners();
        addMenuListeners();
        setupFrame();

        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    AtomicBoolean lock = new AtomicBoolean(false);
                    cache.removeStats(lock);
                    while (!lock.get()){
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void reload() {
        String[] column = {"Назва", "Виробник", "Кількість на складі", "Ціна за одиницю"};
        int index = cache.indexOf(currentGroup);
        if (index != -1) {
            currentGroup = cache.get(index);
            ArrayList<String[]> data = new ArrayList<>();

            String searchFilter = searchField.getText();
            for (Product product : currentGroup) {
                if (product.getName().contains(searchFilter)
                        || product.getDescription().contains(searchFilter)
                        || product.getManufacturer().contains(searchFilter)
                ) {
                    String[] row = new String[] {
                            product.getName(),
                            product.getManufacturer(),
                            String.valueOf(product.getCount()),
                            String.valueOf(product.getPrice())
                    };
                    data.add(row);
                }
            }
            tableModel = new DefaultTableModel(data.toArray(new String[0][]), column) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setModel(tableModel);
        }
    }

    private void addMenuListeners() {
        reloadMenuItem.addActionListener(e -> {
            cache.reload();
        });

        changeWorkspaceMenuItem.addActionListener(e -> {
            JDialog pathChanger = new PathChangeDialog(cache.getDb());
            pathChanger.setVisible(true);
            cache.reload();
        });

        showStorageStatistics.addActionListener(e -> {
            StorageStatistics sst = new StorageStatistics(cache);
        });

        numberChangeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER) {
                    if (!currentGroup.getName().equals("null")) {
                        if (table.getSelectedRow() != -1) {
                            currentGroup.get(table.getSelectedRow()).setCount(Integer.valueOf(numberChangeTextField.getText()));
                            cache.set(currentGroup);
                            reload();
                        } else
                            JOptionPane.showMessageDialog(null, "Виберіть товар!");
                    } else
                        JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
                } else if (!(Character.isDigit(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_BACK_SPACE))
                    e.consume();
            }
        });
    }

    @SuppressWarnings("Duplicates")
    private void addButtonListeners() {
        choseGroupButton.addActionListener(e -> {
            GroupChooserDialog groupChooserDialog = new GroupChooserDialog(this, cache);
            groupChooserDialog.setVisible(true);
        });

        editGroupsButton.addActionListener(e -> {
            EditGroupsFrame editGroupsFrame = new EditGroupsFrame(cache);
            editGroupsFrame.setVisible(true);
        });

        addProductButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                AddItemDialog addItemDialog = new AddItemDialog(this, cache);
                addItemDialog.setVisible(true);
            } else
                JOptionPane.showMessageDialog(null, "Виберіть спочатку групу!");
        });

        addItemsButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                if (table.getSelectedRow() != -1) {
                    if (StringRegExChecker.checkIntegerWithoutZero(numberChangeTextField.getText())) {
                        currentGroup.get(table.getSelectedRow()).incrementCount(Integer.valueOf(numberChangeTextField.getText()));
                        currentGroup.get(table.getSelectedRow()).addProduced(Integer.valueOf(numberChangeTextField.getText()));
                        cache.set(currentGroup);
                        cache.reload();
                    } else
                        JOptionPane.showMessageDialog(null, "Помилка у введеному числі");
                } else
                    JOptionPane.showMessageDialog(null, "Виберіть товар!");
            } else
                JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
        });

        sellProductButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                if (table.getSelectedRow() != -1) {
                    Product tempProd = currentGroup.get(table.getSelectedRow());
                    int tempInt = Integer.valueOf(numberChangeTextField.getText());
                    if (tempProd.ableToSubtract(tempInt)) {
                        tempProd.decrementCount(tempInt);
                        tempProd.addSold(tempInt);
                        cache.set(currentGroup);
                        cache.reload();
                    } else {
                        JOptionPane.showMessageDialog(null, "There are no as many products.");
                    }
                } else
                    JOptionPane.showMessageDialog(null, "Виберіть товар!");
            } else
                JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
            //TODO use this separation in statistics; think about duplication
        });

        writeOffButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                if (table.getSelectedRow() != -1) {
                    Product tempProd = currentGroup.get(table.getSelectedRow());
                    int tempInt = Integer.valueOf(numberChangeTextField.getText());
                    if (tempProd.ableToSubtract(tempInt)) {
                        tempProd.decrementCount(tempInt);
                        tempProd.addWrittenOff(tempInt);
                        cache.set(currentGroup);
                        cache.reload();
                    } else {
                        JOptionPane.showMessageDialog(null, "Тут нема стільк продуктів.");
                    }
                } else
                    JOptionPane.showMessageDialog(null, "Виберіть товар!");
            } else
                JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
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
                    cache.set(currentGroup);
                    cache.reload();
                } else
                    JOptionPane.showMessageDialog(null, "Необхідно вибрати продукт!");
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        itemDescButton.addActionListener(e -> {
            if (currentGroup != null) {
                if (table.getSelectedRow() != -1) {
                    Product currentProduct = currentGroup.get(table.getSelectedRow());
                    DescDialog descDialog = new DescDialog(this, "Товар: " + currentProduct.getName(),
                            currentProduct.getDescription());
                    descDialog.setVisible(true);
                } else
                    JOptionPane.showMessageDialog(null, "Необхідно вибрати продукт!");
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        currentGroupDescButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                DescDialog descDialog = new DescDialog(this, "Група: " + currentGroup.getName(), currentGroup.getDesc());
                descDialog.setVisible(true);
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        searchButton.addActionListener(e -> {
            reload();
        });

        searchField.addActionListener(e -> {
            reload();
        });
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        addDataBaseMenu();
        addStatisticsMenu();
        this.setJMenuBar(menuBar);
    }

    private void addDataBaseMenu() {
        JMenu dataBaseMenu = new JMenu("База даних");
        reloadMenuItem = new JMenuItem("Оновити");
        changeWorkspaceMenuItem = new JMenuItem("Змінити робочу область...");
        dataBaseMenu.add(reloadMenuItem);
        dataBaseMenu.add(changeWorkspaceMenuItem);
        menuBar.add(dataBaseMenu);
    }

    private void addStatisticsMenu() {
        JMenu statisticsMenu = new JMenu("Статистика");

        showStorageStatistics = new JMenuItem("На складі");
        showGroupStatistics = new GroupMenu("У групі...", cache);

        statisticsMenu.add(showStorageStatistics);
        statisticsMenu.add(showGroupStatistics);

        menuBar.add(statisticsMenu);
    }

    private void setupFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    void setCurrentGroup(ProductGroup group) {
        currentGroup = group;
        reload();
        currentGroupLabel.setText("Поточна група: " + currentGroup.getName());
    }

    public ProductGroup getCurrentGroup() {
        return currentGroup;
    }
}