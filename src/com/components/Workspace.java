package com.components;

import com.data.Cache;
import com.ui.MainFrame;
import com.ui.WelcomeFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
//        Cache cache = new Cache(DatabaseManager.getInstance());
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        JFrame welcome = new WelcomeFrame();
        welcome.setVisible(true);
    }

    public static void launch() {
        Cache cache = new Cache(DatabaseManager.getInstance());
        mainFrame = new MainFrame(cache);
        mainFrame.setVisible(true);
    }

    public static String askPath(JDialog dialog){
        String path = null;
        if (System.getProperty("os.name").equals("Mac OS X")) {
            System.setProperty("apple.awt.fileDialogForDirectories", "true");
            FileDialog filePicker = new FileDialog(dialog, "Select workspace");
            filePicker.setVisible(true);
            path = filePicker.getDirectory() + filePicker.getFile();
            System.setProperty("apple.awt.fileDialogForDirectories", "false");
        } else {
            JFileChooser filePicker = new JFileChooser();
            filePicker.setAcceptAllFileFilterUsed(false);
            filePicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filePicker.setCurrentDirectory(new File("."));
            if (filePicker.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION){
                path = filePicker.getSelectedFile().getAbsolutePath();
            }
        }
        return path;
    }

    public static String askPath(JFrame dialog){
        String path = null;
        if (System.getProperty("os.name").equals("Mac OS X")) {
            System.setProperty("apple.awt.fileDialogForDirectories", "true");
            FileDialog filePicker = new FileDialog(dialog, "Select workspace");
            filePicker.setVisible(true);
            path = filePicker.getDirectory() + filePicker.getFile();
            System.setProperty("apple.awt.fileDialogForDirectories", "false");
        } else {
            JFileChooser filePicker = new JFileChooser();
            filePicker.setAcceptAllFileFilterUsed(false);
            filePicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filePicker.setCurrentDirectory(new File("."));
            if (filePicker.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION){
                path = filePicker.getSelectedFile().getAbsolutePath();
            }
        }
        return path;
    }

}
