package com.components;

import com.data.Cache;
import com.ui.MainFrame;
import com.ui.WelcomeFrame;

import javax.swing.*;

public class Workspace {

    static JFrame mainFrame;

   public static void main(String[] args) {
//        String separtor = System.getProperty("file.separator");
//        System.out.println(System.getProperty("os.name"));
//        switch (System.getProperty("os.name")) {
//            case "Mac OS X":
//                DatabaseManager.getInstance().setPath("/Users/user/Documents/Development/Study/Java P2/L2/test_data");
//                break;
//            case "Unix":
//                DatabaseManager.getInstance().setPath("home/yaroslav/cache");
//                break;
//            case "Windows":
//                DatabaseManager.getInstance().setPath("C:\\Cache");
//                break;
//        }
//      Cache cache = new Cache(DatabaseManager.getInstance());


        System.setProperty("apple.laf.useScreenMenuBar", "true");
        JFrame welcome = new WelcomeFrame();
        welcome.setVisible(true);
    }

    public static void launch() {
        Cache cache = new Cache(DatabaseManager.getInstance());
        mainFrame = new MainFrame(cache);
        mainFrame.setVisible(true);
    }

}
