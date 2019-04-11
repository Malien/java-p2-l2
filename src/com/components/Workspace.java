package com.components;

import com.data.Cache;
import com.ui.MainFrame;

public class Workspace {

    public static void main(String[] args) {
        String separtor = System.getProperty("file.separator");
        System.out.println(System.getProperty("os.name"));
        switch (System.getProperty("os.name")) {
            case "Mac OS X":
                DatabaseManager.getInstance().setPath("/Users/user/Documents/Development/Study/Java P2/L2/test_data");
                break;
            case "Unix":
                DatabaseManager.getInstance().setPath("home/yaroslav/cache");
                break;
            case "Windows":
                DatabaseManager.getInstance().setPath("C:\\Cache");
                break;
        }
        Cache cache = new Cache(DatabaseManager.getInstance());
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        MainFrame frame = new MainFrame(cache);
        frame.setVisible(true);
    }

}
