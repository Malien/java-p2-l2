package com.components;

import com.data.Product;
import com.data.ProductGroup;
import com.requests.Callback;
import com.requests.DatabaseRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class DatabaseManager implements Runnable, IDatabase {

    /* ****File structure****
    workspacePath:
        group1.prg -- product group 1
        group2.prg -- product group 2

    .prg file structure:
    String(description)|Product|Product|... EOF

    Product
    parameters: ||  amount ||  price  || produced||   sold  ||written off||   name    |  0x0   || manufacturer |  0x0   || description |  0x0   ||
    block len:  || 4 bytes || 4 bytes || 4 bytes || 4 bytes ||  4 bytes  || variable  | 1 byte ||   variable   | 1 byte ||   variable  | 1 byte ||
     */

    private static final DatabaseManager instance = new DatabaseManager();
    private Thread dataThread = new Thread(this, "DatabaseManager");
    private String systemSeparator = System.getProperty("file.separator");
    private String workspacePath = systemSeparator;

    //TODO: think about that arbitrary 512 queue depth
    private LinkedBlockingQueue<DatabaseRequest> queue = new LinkedBlockingQueue<>(512);

    private DatabaseManager() {
        dataThread.start();
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private File[] workspaceFiles() {
        FileFilter filter = (File pathname) -> pathname.getName().endsWith(".prg") && pathname.isFile();
        File workspaceFolder = new File(workspacePath);
        return workspaceFolder.listFiles(filter);
    }

    private boolean changeDir(String path) {
        File wrksp = new File(path);
        if (!wrksp.isDirectory()) return false;
        workspacePath = path;
        return true;
    }

    public String path(){
        return workspacePath;
    }

    private ProductGroup[] getProductGroups() throws IOException {
        ArrayList<ProductGroup> groups = new ArrayList<>();
        for (File file : workspaceFiles()) {
            groups.add(getProductGroup(file));
        }
        return groups.toArray(new ProductGroup[0]);
    }

    private ProductGroup getProductGroup(File file) throws IOException {
        FileInputStream fileStream = new FileInputStream(file);
        DataInputStream data = new DataInputStream(fileStream);
        String filename = file.getName();
        String groupDescription = data.readUTF();
        //TODO: fix for changed ProductGroup constructor needed
        ProductGroup group = new ProductGroup(filename.substring(0, filename.length() - 4),groupDescription);
        while (true) {
            try {
                int amount = data.readInt();
                float price = data.readFloat();
                int produced = data.readInt();
                int sold = data.readInt();
                int writtenOff = data.readInt();
                String name = data.readUTF();
                String manufacturer = data.readUTF();
                String description = data.readUTF();
                Product prd = new Product(name, description, manufacturer, amount, price, produced, sold, writtenOff);
                group.add(prd);
            } catch (EOFException e) {
                break;
            }
        }
        data.close();
        return group;
    }

    private ProductGroup getProductGroup(String path) throws IOException {
        try {
            File groupFile = new File(workspacePath + systemSeparator + path + ".prg");
            return getProductGroup(groupFile);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private boolean setProductGroup(String path, ProductGroup group) throws IOException {
        File found = null;
        for (File file : workspaceFiles()) {
            if (file.getName().equals(path + ".prg")) {
                found = file;
            }
        }

        //TODO: Write something more efficient than just deleting and recreating whole file (path isn't used properly btw)
        if (group == null) {
            found.delete();
            return true;
        }
        if (found == null) {
            found = new File(workspacePath + systemSeparator + path + ".prg");
            found.createNewFile();
        }

        DataOutputStream stream = new DataOutputStream(new FileOutputStream(found));
        stream.writeUTF(group.getDesc());
        for (Product product : group) {
            stream.writeInt(product.getCount());
            stream.writeFloat(product.getPrice());
            stream.writeInt(product.getProduced());
            stream.writeInt(product.getSold());
            stream.writeInt(product.getWrittenOff());
            stream.writeUTF(product.getName());
            stream.writeUTF(product.getManufacturer());
            stream.writeUTF(product.getDescription());
        }
        stream.close();
        return true;
    }

    public void get(String path, Callback<ProductGroup> callback){
        try {
            queue.put(DatabaseRequest.get(path, callback));
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void getAll(Callback<ProductGroup[]> callback){
        try {
            queue.put(DatabaseRequest.getAll(callback));
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void set(ProductGroup group, Callback<Boolean> callback){
        try {
            queue.put(DatabaseRequest.set(group, callback));
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void set(ProductGroup group){
        try {
            queue.put(DatabaseRequest.set(group));
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void setPath(String path, Callback<Boolean> callback){
        try {
            queue.put(DatabaseRequest.setPath(path, callback));
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void setPath(String path){
        try {
            queue.put(DatabaseRequest.setPath(path));
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void delete(String path, Callback<Boolean> callback){
        try {
            queue.put(DatabaseRequest.delete(path, callback));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(String path){
        try {
            queue.put(DatabaseRequest.delete(path));
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (dataThread.isAlive()) {
            try {
                DatabaseRequest request = queue.take();
                switch (request.type()) {
                    case DatabaseRequest.GET:
                        request.callback().call(getProductGroup(request.path()));
                        break;
                    case DatabaseRequest.GET_ALL:
                        request.callback().call(getProductGroups());
                        break;
                    case DatabaseRequest.SET:
                        request.callback().call(setProductGroup(request.payload().getName(), request.payload()));
                        break;
                    case DatabaseRequest.SET_PATH:
                        request.callback().call(changeDir(request.path()));
                        break;
                    case DatabaseRequest.DELETE:
                        request.callback().call(setProductGroup(request.path(), null));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
