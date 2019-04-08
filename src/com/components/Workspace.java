package com.components;

import com.data.DataBaseFunctions;
import com.ui.MainFrame;

public class Workspace {

    public static void main(String[] args) {
        DataBaseFunctions conn = new DataBaseFunctions();
        MainFrame frame = new MainFrame(conn);
        frame.setVisible(true);
    }

}
