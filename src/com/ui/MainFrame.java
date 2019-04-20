package com.ui;

import com.data.Cache;
import com.data.Product;
import com.data.ProductGroup;
import com.data.Tuple;
import com.util.UIConstants;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


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
    private JButton productNumberPlusButton;
    private JButton productNumberMinusButton;
    private JButton searchMenuButton;

    private JTextField searchMenuTextField;

    private JMenuBar menuBar;
    private JMenuItem reloadMenuItem;
    private JMenuItem changeWorkspaceMenuItem;
    private JMenuItem statisticsMainStorageMenuItem;
    private JMenuItem helpMenuItem;
    private JMenuItem statisticsGroupMenuItem;
    private JMenuItem showStorageStatistics;
    private JMenu showGroupStatistics;

    private JScrollPane tableScrollPane;
    private JList allGroupsList;
    private JTable table;
    private JLabel currentGroupLabel;
    private JButton addProductButton;
    private JButton sellProductButton;
    private JTextField numberChangeTextField;
    Cache cache;
    private ProductGroup currentGroup;
    private DefaultTableModel tableModel;
    private JMenu searchMenu;

    public MainFrame(Cache cache) {
        this.cache = cache;
        this.cache.setUI(this);
        this.currentGroup = new ProductGroup("null", "empty group");
        this.setResizable(false);
        if (!System.getProperty("os.name").equals("Mac OS X")) {
            setupUILook();
        }
        setupMargins();
        setupMenuBar();
        addButtonListeners();
        addMenuListeners();
        setupFrame();
    }

    private void setupMargins() {
        productNumberPlusButton.setMargin(new Insets(0, 0, 1, 0));
        productNumberMinusButton.setMargin(new Insets(0, 0, 1, 1));
    }

    @SuppressWarnings("Duplicates")
    private void setupUILook() {
        choseGroupButton.setBackground(UIConstants.MaterialBlue);
        choseGroupButton.setForeground(Color.WHITE);
        choseGroupButton.setBorderPainted(false);

        addProductButton.setBackground(UIConstants.MaterialBlue);
        addProductButton.setForeground(Color.WHITE);
        addProductButton.setBorderPainted(false);

        sellProductButton.setBackground(UIConstants.MaterialBlue);
        sellProductButton.setForeground(Color.WHITE);
        sellProductButton.setBorderPainted(false);

        editGroupsButton.setBackground(UIConstants.MaterialBlue);
        editGroupsButton.setForeground(Color.WHITE);
        editGroupsButton.setBorderPainted(false);

        addItemsButton.setBackground(UIConstants.MaterialBlue);
        addItemsButton.setForeground(Color.WHITE);
        addItemsButton.setBorderPainted(false);

        writeOffButton.setBackground(UIConstants.MaterialBlue);
        writeOffButton.setForeground(Color.WHITE);
        writeOffButton.setBorderPainted(false);

        currentGroupDescButton.setBackground(UIConstants.MaterialBlue);
        currentGroupDescButton.setForeground(Color.WHITE);
        currentGroupDescButton.setBorderPainted(false);

        editItemButton.setBackground(UIConstants.MaterialBlue);
        editItemButton.setForeground(Color.WHITE);
        editItemButton.setBorderPainted(false);

        removeItemButton.setBackground(UIConstants.MaterialBlue);
        removeItemButton.setForeground(Color.WHITE);
        removeItemButton.setBorderPainted(false);

        itemDescButton.setBackground(UIConstants.MaterialBlue);
        itemDescButton.setForeground(Color.WHITE);
        itemDescButton.setBorderPainted(false);

        productNumberMinusButton.setBackground(UIConstants.MaterialBlue);
        productNumberMinusButton.setForeground(Color.WHITE);
        productNumberMinusButton.setBorderPainted(false);

        productNumberPlusButton.setBackground(UIConstants.MaterialBlue);
        productNumberPlusButton.setForeground(Color.WHITE);
        productNumberPlusButton.setBorderPainted(false);
    }

    @Override
    public void reload() {
        String[] column = {"Назва", "Виробник", "Кількість на складі", "Ціна за одиницю"};
        int index = cache.indexOf(currentGroup);
        if (index != -1) {
            currentGroup = cache.get(index);
            String[][] data = new String[currentGroup.getProducts().length][4];

            for (int i = 0; i < currentGroup.getProducts().length; i++) {
                Product tempProduct = currentGroup.get(i);
                data[i][0] = tempProduct.getName();
                data[i][1] = tempProduct.getManufacturer();
                data[i][2] = String.valueOf(tempProduct.getCount());
                data[i][3] = String.valueOf(tempProduct.getPrice());
            }
            tableModel = new DefaultTableModel(data, column) {
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
            System.out.println("showStorageStatistics");
        });

        numberChangeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER){
                    if (!currentGroup.getName().equals("null")) {
                        if (table.getSelectedRow() != -1) {
                            currentGroup.get(table.getSelectedRow()).setCount(Integer.valueOf(numberChangeTextField.getText()));
                            cache.reload();
                        } else
                            JOptionPane.showMessageDialog(null, "Виберіть товар!");
                    } else
                        JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
                }
                else if (!(Character.isDigit(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) e.consume();
            }
        });

        showGroupStatistics.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                allGroupsList.setListData(cache.getCache().toArray(new ProductGroup[0]));
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
    }

    @SuppressWarnings("Duplicates")
    private void addButtonListeners() {

        choseGroupButton.addActionListener(e -> {
            GroupChooserFrame groupChooserFrame = new GroupChooserFrame(this, cache);
            groupChooserFrame.setVisible(true);
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

        productNumberPlusButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                if (table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    currentGroup.get(table.getSelectedRow()).incrementCount();
                    cache.reload();
                    table.setRowSelectionInterval(selectedRow, selectedRow);
                } else
                    JOptionPane.showMessageDialog(null, "Виберіть товар!");
            } else
                JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
        });

        productNumberMinusButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                if (table.getSelectedRow() != -1) {
                    if (currentGroup.get(table.getSelectedRow()).getCount() != 0) {
                        int selectedRow = table.getSelectedRow();
                        currentGroup.get(table.getSelectedRow()).decrementCount();
                        cache.reload();
                        table.setRowSelectionInterval(selectedRow, selectedRow);
                    }
                } else
                    JOptionPane.showMessageDialog(null, "Виберіть товар!");
            } else
                JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
        });

        addItemsButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                if (table.getSelectedRow() != -1) {
                    currentGroup.get(table.getSelectedRow()).incrementCount(Integer.valueOf(numberChangeTextField.getText()));
                    cache.reload();

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
                    if (tempProd.ableToSubtract(tempInt)){
                        tempProd.decrementCount(tempInt);
                        cache.reload();
                    }
                    else{
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
                    if (tempProd.ableToSubtract(tempInt)){
                        tempProd.decrementCount(tempInt);
                        cache.reload();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "There are no as many products.");
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
                            new EditProductDialog(this, currentGroup.get(table.getSelectedRow()));
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
                    DescFrame descFrame = new DescFrame("Товар: " + currentProduct.getName(),
                            currentProduct.getDescription());
                    descFrame.setVisible(true);
                } else
                    JOptionPane.showMessageDialog(null, "Необхідно вибрати продукт!");
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        currentGroupDescButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                DescFrame descFrame = new DescFrame("Група: " + currentGroup.getName(), currentGroup.getDesc());
                descFrame.setVisible(true);
            } else
                JOptionPane.showMessageDialog(null, "Необхідно вибрати групу!");
        });

        searchMenuButton.addActionListener(e -> {
            String productName = searchMenuTextField.getText();
            try {
                Tuple productInfo = cache.findProductByName(productName);
                ProductGroup foundGroup = (ProductGroup) productInfo.first;
                Product foundProduct = (Product) productInfo.second;
                this.setCurrentGroup(foundGroup);
                table.setRowSelectionInterval(foundGroup.indexOf(foundProduct), foundGroup.indexOf(foundProduct));
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

//TODO think about focus
        searchMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                searchMenuTextField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                searchMenuTextField.requestFocus();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        addDataBaseMenu();
        addSearchMenu();
        addStatisticsMenu();
        this.setJMenuBar(menuBar);
    }

    private void addDataBaseMenu() {
        JMenu dataBaseMenu = new JMenu("База даних");
        reloadMenuItem = new JMenuItem("Reload");
        changeWorkspaceMenuItem = new JMenuItem("Change workspace...");
        dataBaseMenu.add(reloadMenuItem);
        dataBaseMenu.add(changeWorkspaceMenuItem);
        menuBar.add(dataBaseMenu);
    }

    private void addSearchMenu() {
        searchMenu = new JMenu("Пошук");
        searchMenu.setMnemonic('g');
        JPanel searchMenuPanel = new JPanel();
        searchMenuPanel.setPreferredSize(new Dimension(200, 25));
        searchMenuPanel.setLayout(new BorderLayout());
        searchMenuTextField = new JTextField();
        searchMenuButton = new JButton("Find");
        searchMenuButton.setMnemonic('f');
        searchMenuButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        searchMenuPanel.add(searchMenuTextField, BorderLayout.CENTER);
        searchMenuPanel.add(searchMenuButton, BorderLayout.EAST);
        searchMenuPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        searchMenu.add(searchMenuPanel);
        menuBar.add(searchMenu);
    }

    private void addStatisticsMenu() {
        JMenu statisticsMenu = new JMenu("Статистика");

        showStorageStatistics = new JMenuItem("На складі");
        showGroupStatistics = new JMenu("У групі...");

        statisticsMenu.add(showStorageStatistics);
        statisticsMenu.add(showGroupStatistics);

        allGroupsList = new JList(cache.getCache().toArray(new ProductGroup[0]));
        JScrollPane scroll = new JScrollPane(allGroupsList);
        scroll.setPreferredSize(new Dimension(120, 200));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        showGroupStatistics.add(scroll);

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
        System.out.println(currentGroup);
        reload();
        currentGroupLabel.setText("Поточна група: " + currentGroup.getName());
    }

    public ProductGroup getCurrentGroup() {
        return currentGroup;
    }


}
