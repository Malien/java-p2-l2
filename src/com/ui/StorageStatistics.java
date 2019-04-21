package com.ui;

import com.data.Cache;
import com.data.Product;
import com.data.ProductGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StorageStatistics extends JFrame{
    private JPanel rootPanel;
    private JPanel tablePanel;
    private JPanel totalCostPanel;
    private JPanel greetingsPanel;
    private JLabel topMessage;
    private JTable initTable;
    private JLabel productsCostLabel;
    private JScrollPane initTableScroll;
    private JTable contentTable;
    private Cache cache;
    private double totalCost;

    public StorageStatistics(Cache cache){
        this.cache = cache;
        setTitle("Статистика складу");
        getStatistics();
        setMinimumSize(new Dimension(700, 400));
        setResizable(true);
        add(rootPanel);
        pack();
        setLocationRelativeTo(null);
        productsCostLabel.setText("Вартість усієї продукції: " + totalCost);
        setVisible(true);
    }


    private void getStatistics() {
        initTable.setFillsViewportHeight(true);
        String[] forHeader = {"Назва товару", "Ціна", "На складі", "Вироблено", "Продано", "Списано", "Заробіток"};
        String[][] forBody = new String[cache.getNumOfProducts()][7];
        fillWithInfo(forBody);
        DefaultTableModel model = (DefaultTableModel) initTable.getModel();
        model.setDataVector(forBody, forHeader);
        initTable.getColumnModel().getColumn(0).setPreferredWidth(250);
    }

    @SuppressWarnings("Duplicates")
    private void fillWithInfo (String[][] content){
        int counter = 0;
        ArrayList<ProductGroup> groups = cache.getCache();
        for (int i = 0; i < groups.size(); i++){
            Product[] products = groups.get(i).getProducts();
            for(int j = 0; j < products.length; j++){
                Product prod = products[j];
                content[counter][0] = prod.getName();
                content[counter][1] = String.valueOf(prod.getPrice());
                content[counter][2] = String.valueOf(prod.getCount());
                content[counter][3] = String.valueOf(prod.getProduced());
                content[counter][4] = String.valueOf(prod.getSold());
                content[counter][5] = String.valueOf(prod.getWrittenOff());
                content[counter++][6] =  String.valueOf((prod.getSold()-prod.getWrittenOff())*prod.getPrice());
                totalCost += prod.getCount()*prod.getPrice();
            }
        }
    }

}
