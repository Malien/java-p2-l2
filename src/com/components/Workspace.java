package com.components;

import com.data.FrontBackConnection;
import com.ui.MainFrame;

public class Workspace {

    public static void main(String[] args) {
        FrontBackConnection conn = new FrontBackConnection();
        MainFrame frame = new MainFrame(conn);
        frame.setVisible(true);
    }

}
