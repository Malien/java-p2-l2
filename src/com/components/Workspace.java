package com.components;

import com.data.ProductGroup;
import com.util.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;

public class Workspace extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseManager manager = DatabaseManager.getInstance();
        Logger log = new Logger("Workspace");

        manager.setPath("/Users/user/Documents/Development/Study/Java P2/L2/test_data", (Boolean a) ->{
            log.info("setPath:" + a);
        });
        manager.getAll((ProductGroup[] a) -> {
            log.info(Arrays.toString(a));
        });

        //TODO: set up application
    }
}
