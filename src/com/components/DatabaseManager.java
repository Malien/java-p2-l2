package com.components;

import com.requests.DatabaseRequest;
import com.util.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseManager implements Runnable {

    /* ****File structure****
    workspacePath:
        group1.prg -- product group 1
        group2.prg -- product group 2

    .prg file structure:
    |Product|Product|... EOF

    Product
    parameters: ||  amount ||  price  ||   name    |  0x0   || manufacturer |  0x0   || description |  0x0   ||
    block len:  || 4 bytes || 4 bytes || variable  | 1 byte ||   variable   | 1 byte ||   variable  | 1 byte ||
     */

    private static final DatabaseManager instance = new DatabaseManager();
    private Thread dataThread = new Thread(this);
    private Logger log = new Logger("DatabaseManager");
    private String workspacePath = "/";

    //TODO: think about that arbitrary 512 queue depth
    private LinkedBlockingQueue<DatabaseRequest> queue = new LinkedBlockingQueue<>(512);

    private DatabaseManager() {
        dataThread.start();
    }

    public static DatabaseManager getInstance(){
        return instance;
    }

    private void changeDir(String path) throws InvalidRequestException {
        if (path == null) throw new InvalidRequestException("Dir change requested, but no path provided");
        File wrksp = new File(path);
        if (!wrksp.isDirectory()) throw new InvalidRequestException("Provided workspace path is not a directory");
        workspacePath = path;
    }

    private ProductGroup[] getProductGroups() throws InvalidRequestException, IOException {
        ArrayList<ProductGroup> groups = new ArrayList<>();
        File folder = new File(workspacePath);
        File[] folderContents = folder.listFiles();
        if (folderContents == null) return null;

        for (File file : folderContents){
            if (file.getName().endsWith(".prg") && file.isFile()){
                groups.add(getProductGroup(file));
            }
        }

        return (ProductGroup[]) groups.toArray();
    }

    private ProductGroup getProductGroup(File file) throws IOException {
        FileInputStream fileStream = new FileInputStream(file);
        DataInputStream data = new DataInputStream(fileStream);
        String filename = file.getName();
        ProductGroup group = new ProductGroup(filename.substring(0, filename.length()-5));
        while (true){
            try {
                int amount = data.readInt();
                float price = data.readFloat();
                String name = data.readUTF();
                String manufacturer = data.readUTF();
                String description = data.readUTF();
                Product prd = new Product(name, manufacturer, description, amount, price);
                group.add(prd);
            } catch (EOFException e) {
                break;
            }
        }

        return group;
    }

    private ProductGroup getProductGroup(String path) throws InvalidRequestException, IOException {
        String[] pathArgs = path.split("/");
        if (pathArgs.length != 1) throw new InvalidRequestException("Path provided in invalid format");

        try{
            File groupFile = new File(workspacePath + "/" + pathArgs[0] + ".prg");
            return getProductGroup(groupFile);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public void run() {

    }
}
