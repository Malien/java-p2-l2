package com.ui;

import com.data.Product;
import com.data.ProductGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShowGroupStatistics extends JFrame {
    private JLabel groupName;
    private JLabel totalCostGroup;
    private JTable contentTable;
    private JPanel rootPanel;
    private JScrollPane tableScroll;
    private double totalCost;

    public ShowGroupStatistics(ProductGroup pg){
        groupName.setText(pg.getName());
        contentTable.setFillsViewportHeight(true);
        String[] header = {"Назва товару", "Ціна", "На складі", "Вироблено", "Продано", "Списано", "Заробіток"};
        String[][] body = new String[pg.getProducts().length][7];
        fillWithInfo(body, pg);
        setLocationRelativeTo(null);
        DefaultTableModel model = (DefaultTableModel) contentTable.getModel();
        model.setDataVector(body, header);
        contentTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        setInit();
        setVisible(true);
    }

    @SuppressWarnings("Duplicates")
    private void fillWithInfo(String[][] info, ProductGroup pg) {
        int counter = 0;
        Product[] products = pg.getProducts();
        for (int j = 0; j < products.length; j++) {
            Product prod = products[j];
            info[counter][0] = prod.getName();
            info[counter][1] = String.valueOf(prod.getPrice());
            info[counter][2] = String.valueOf(prod.getCount());
            info[counter][3] = String.valueOf(prod.getProduced());
            info[counter][4] = String.valueOf(prod.getSold());
            info[counter][5] = String.valueOf(prod.getWrittenOff());
            info[counter++][6] = StorageStatistics.getCost((prod.getSold() - prod.getWrittenOff()) * prod.getPrice());
            totalCost += prod.getCount() * prod.getPrice();
        }
        totalCostGroup.setText("Загальна вартість: "+ StorageStatistics.getCost(totalCost));
    }

    private void setInit() {
        setSize(700, 400);
        add(rootPanel);
        setLocationRelativeTo(null);
        setResizable(true);
    }
}
