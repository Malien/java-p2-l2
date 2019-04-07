package com.components;

import com.data.FrontBackConnection;
import com.ui.MainFrame;

public class Workspace {

    public static void main(String[] args) {
        FrontBackConnection conn = new FrontBackConnection();
        MainFrame frame = new MainFrame(conn);
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
