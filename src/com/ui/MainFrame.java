package com.ui;

import com.data.Cache;
import com.data.Product;
import com.data.ProductGroup;
import com.util.UIConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


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
    private JButton addItemPlusButton;
    private JButton addItemMinusButton;
    private JButton writeOffPlusButton;
    private JButton writeOffMinusButton;
    private JButton searchMenuButton;

    private JTextField addItemValTextField;
    private JTextField writeOffValTextField;
    private JTextField searchMenuTextField;

    private JMenuBar menuBar;
    private JMenuItem reloadMenuItem;
    private JMenuItem changeWorkspaceMenuItem;
    private JMenuItem statisticsWarehouseMenuItem;
    private JMenuItem helpMenuItem;
    private JMenuItem statisticsGroupMenuItem;
    private JMenuItem countWarehousePriceMenuItem;
    private JMenuItem countGroupPriceMenuItem;

    private JScrollPane tableScrollPane;
    private JTable table;
    private JLabel currentGroupLabel;
    private JButton addProductButton;
    private Cache cache;
    private ProductGroup currentGroup;
    private DefaultTableModel tableModel;
    private Product currentProduct;
    private JMenu searchMenu;

    public JTextField getAddItemValTextField() {
        return addItemValTextField;
    }

    public JTextField getWriteOffValTextField() {
        return writeOffValTextField;
    }

    public MainFrame(Cache cache) {
        this.cache = cache;
        this.cache.setUI(this);
        this.currentGroup = new ProductGroup("null", "empty group");
        addTestGroupList();
        setupUILook();
        setupMenuBar();
        addButtonListeners();
        addMenuListeners();
        setupFrame();
        //this.cache.reload();
    }

    @SuppressWarnings("Duplicates")
    private void setupUILook() {
        choseGroupButton.setBackground(UIConstants.MaterialBlue);
        choseGroupButton.setForeground(Color.WHITE);
        choseGroupButton.setBorderPainted(false);

        addProductButton.setBackground(UIConstants.MaterialBlue);
        addProductButton.setForeground(Color.WHITE);
        addProductButton.setBorderPainted(false);

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

        addItemPlusButton.setBackground(UIConstants.MaterialBlue);
        addItemPlusButton.setForeground(Color.WHITE);
        addItemPlusButton.setBorderPainted(false);
        addItemPlusButton.setMargin(new Insets(0, 0, 1, 0));

        addItemMinusButton.setBackground(UIConstants.MaterialBlue);
        addItemMinusButton.setForeground(Color.WHITE);
        addItemMinusButton.setBorderPainted(false);
        addItemMinusButton.setMargin(new Insets(0, 0, 1, 1));

        writeOffPlusButton.setBackground(UIConstants.MaterialBlue);
        writeOffPlusButton.setForeground(Color.WHITE);
        writeOffPlusButton.setBorderPainted(false);
        writeOffPlusButton.setMargin(new Insets(0, 0, 1, 0));

        writeOffMinusButton.setBackground(UIConstants.MaterialBlue);
        writeOffMinusButton.setForeground(Color.WHITE);
        writeOffMinusButton.setBorderPainted(false);
        writeOffMinusButton.setMargin(new Insets(0, 0, 1, 1));
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

    private void addTestGroupList() {
          cache.set(new ProductGroup("first group", "fdfd"));
          cache.set(new ProductGroup("second group", "someDesk"));
          cache.get(0).add(new Product("product 1.1", "desc for 1.1", "man", 1, 10));
          cache.get(0).add(new Product("product 1.2", "desc for 1.2", "manuf", 2, 12));
          cache.get(1).add(new Product("product 2.1", "desc for 2.1", "manufac", 3, 15));
          cache.get(1).add(new Product("product 2.2", "desc for 2.2", "manufacturer", 4, 23));
    }

    private void addMenuListeners() {
        reloadMenuItem.addActionListener(e -> {
            cache.reload();
        });

        changeWorkspaceMenuItem.addActionListener(e -> {
            //TODO: ask user new workspace path
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
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    currentProduct = currentGroup.get(table.getSelectedRow());
                    String temp = String.valueOf(currentGroup.get(table.getSelectedRow()).getCount());
                    addItemValTextField.setText(temp);
                    writeOffValTextField.setText(temp);
                }
            }
        });


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

        addItemPlusButton.addActionListener(e -> {
            if (!addItemValTextField.getText().equals("")) {
                int addItemValInt = Integer.parseInt(addItemValTextField.getText());
                addItemValInt++;
                addItemValTextField.setText(String.valueOf(addItemValInt));
            }
        });

        addItemMinusButton.addActionListener(e -> {
            if (!addItemValTextField.getText().equals("")) {
                int addItemValInt = Integer.parseInt(addItemValTextField.getText());
                if (addItemValInt > currentProduct.getCount()) {
                    addItemValInt--;
                    addItemValTextField.setText(String.valueOf(addItemValInt));
                }
            }
        });

        writeOffButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                if (table.getSelectedRow() != -1) {
                    if (!writeOffValTextField.getText().equals("")) {
                        int temp = Integer.parseInt(writeOffValTextField.getText());
                        currentGroup.get(table.getSelectedRow()).setCount(temp);
                        reload();
                    } else
                        JOptionPane.showMessageDialog(null, "Ви нічого не ввели в поле!");
                } else
                    JOptionPane.showMessageDialog(null, "Виберіть товар!");
            } else
                JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
        });

        writeOffPlusButton.addActionListener(e -> {
            if (!writeOffValTextField.getText().equals("")) {
                int writeOffPlusButtonInt = Integer.parseInt(writeOffValTextField.getText());
                if (writeOffPlusButtonInt < currentProduct.getCount()) {
                    writeOffPlusButtonInt++;
                    writeOffValTextField.setText(String.valueOf(writeOffPlusButtonInt));
                }
            }
        });

        writeOffMinusButton.addActionListener(e -> {
            if (!writeOffValTextField.getText().equals("")) {
                int writeOffMinusButtonVal = Integer.parseInt(writeOffValTextField.getText());
                if (writeOffMinusButtonVal > 0)
                    writeOffMinusButtonVal--;
                writeOffValTextField.setText(String.valueOf(writeOffMinusButtonVal));
            }
        });

        addItemsButton.addActionListener(e -> {
            if (!currentGroup.getName().equals("null")) {
                if (table.getSelectedRow() != -1) {
                    if (!writeOffValTextField.getText().equals("")) {
                        int temp = Integer.parseInt(addItemValTextField.getText());
                        currentGroup.get(table.getSelectedRow()).setCount(temp);
                        reload();
                    } else
                        JOptionPane.showMessageDialog(null, "Ви нічого не ввели в поле!");
                } else
                    JOptionPane.showMessageDialog(null, "Виберіть товар!");
            } else
                JOptionPane.showMessageDialog(null, "Виберіть спочатку групу !");
        });

        editItemButton.addActionListener(e -> {
            if (currentGroup != null) {
                if (table.getSelectedRow() != -1) {
                    EditProductDialog editProductDialog =
                            new EditProductDialog(this, currentGroup, currentGroup.get(table.getSelectedRow()));
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
                    reload();
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
                ArrayList risky = cache.findProductByName(productName);
                ProductGroup foundGroup = (ProductGroup) risky.get(0);
                Product foundProduct = (Product) risky.get(1);
                this.setCurrentGroup(foundGroup);
                table.setRowSelectionInterval(foundGroup.indexOf(foundProduct), foundGroup.indexOf(foundProduct));
            }catch (Exception exc) {
                JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
//TODO think about focus
        searchMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                searchMenuTextField.requestFocusInWindow();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });
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
        System.out.println(currentGroup);
        reload();
        currentGroupLabel.setText("Поточна група: " + currentGroup.getName());
    }

    private void createUIComponents() {
        writeOffValTextField = new JTextField() {
            public void setBorder(Border border) {
            }

            @Override
            public void processKeyEvent(KeyEvent ev) {
                if (Character.isDigit(ev.getKeyChar()) || ev.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    super.processKeyEvent(ev);
                }
                ev.consume();
                return;
            }

            Long getNumber() {
                Long result = null;
                String text = getText();
                if (text != null && !"".equals(text)) {
                    result = Long.valueOf(text);
                }
                return result;
            }
        };


        addItemValTextField = new JTextField() {
            @Override
            public void setBorder(Border border) {
            }

            @Override
            public void processKeyEvent(KeyEvent ev) {
                if (Character.isDigit(ev.getKeyChar()) || ev.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    super.processKeyEvent(ev);
                }
                ev.consume();
                return;
            }

            Long getNumber() {
                Long result = null;
                String text = getText();
                if (text != null && !"".equals(text)) {
                    result = Long.valueOf(text);
                }
                return result;
            }
        };
    }

    public ProductGroup getCurrentGroup() {
        return currentGroup;
    }


}
