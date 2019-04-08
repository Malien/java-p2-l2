package com.components;

import com.data.Cache;
import com.ui.MainFrame;

import java.io.File;

public class Workspace {

    public static void main(String[] args) {
        DatabaseManager.getInstance().setPath(System.getProperty("user.home")+ File.separator+"Cache");
        Cache cache = new Cache(DatabaseManager.getInstance());
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        MainFrame frame = new MainFrame(cache);
        frame.setVisible(true);
    }

}
