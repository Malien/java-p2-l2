package com.components;

import com.data.Cache;
import com.ui.MainFrame;

public class Workspace {

    public static void main(String[] args) {
        String separtor = System.getProperty("file.separator");
        DatabaseManager.getInstance().setPath("/Users/user/Documents/Development/Study/Java P2/L2/test_data");
        Cache cache = new Cache(DatabaseManager.getInstance());
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        MainFrame frame = new MainFrame(cache);
        frame.setVisible(true);
    }

}
