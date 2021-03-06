package com.components;

import com.data.Cache;
import com.ui.MainFrame;
import com.ui.WelcomeFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@SuppressWarnings("Duplicates")
public class Workspace {

    private static JFrame mainFrame;

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        JFrame welcome = new WelcomeFrame();
        welcome.setVisible(true);
    }

    public static void launch() {
        Cache cache = new Cache(DatabaseManager.getInstance());
        mainFrame = new MainFrame(cache);
        mainFrame.setVisible(true);
    }

    public static String askPath(JDialog dialog) {
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
            if (filePicker.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                path = filePicker.getSelectedFile().getAbsolutePath();
            }
        }
        return path;
    }

    public static String askPath(JFrame frame) {
        String path = null;
        if (System.getProperty("os.name").equals("Mac OS X")) {
            System.setProperty("apple.awt.fileDialogForDirectories", "true");
            FileDialog filePicker = new FileDialog(frame, "Select workspace");
            filePicker.setVisible(true);
            path = filePicker.getDirectory() + filePicker.getFile();
            System.setProperty("apple.awt.fileDialogForDirectories", "false");
        } else {
            JFileChooser filePicker = new JFileChooser();
            filePicker.setAcceptAllFileFilterUsed(false);
            filePicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filePicker.setCurrentDirectory(new File("."));
            if (filePicker.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                path = filePicker.getSelectedFile().getAbsolutePath();
            }
        }
        return path;
    }

}
