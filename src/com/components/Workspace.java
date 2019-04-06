package com.components;

import com.ui.MainFrame;
import com.ui.WelcomeFrame;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;

public class Workspace {

    public static void main(String[] args) {
        /*try{
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        }catch (UnsupportedLookAndFeelException e){
            e.printStackTrace();
        }*/
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    //Depreciated code:
    /*@Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseManager manager = DatabaseManager.getInstance();
        Logger log = new Logger("Workspace");

        manager.setPath("/Users/user/Documents/Development/Study/Java P2/L2/test_data", (Boolean a) ->{
            log.info("setPath:" + a);
        });
        manager.getAll((ProductGroup[] a) -> {
            log.info(Arrays.toString(a));
        });
    }*/
  
}
