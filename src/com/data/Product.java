package com.data;

public class Product {

    private String name;
    private String description;
    private String manufacturer;
    private int count;
    private float price;
    private int produced;
    private int sold;
    private int writtenOff;

    public Product(String name, String description, String manufacturer, int count, float price) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.count = count;
        this.price = price;
    }

    public Product(String name, String description, String manufacturer, int count, float price, int produced, int sold, int writtenOff) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.count = count;
        this.price = price;
        this.produced = produced;
        this.sold = sold;
        this.writtenOff = writtenOff;
    }

    public String getName() {
        return name;
    }

    public void productRefactor(String name, String description, String manufacturer, float price) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void incrementCount(){
        count++;
    }

    public void incrementCount(int inc){
        count+=inc;
    }
    public void decrementCount(){
        count--;
    }

    public boolean ableToSubtract(int subs){
        return count >= subs;
    }

    public void decrementCount(int dec){
        count -= dec;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addProduced(int produced){
        this.produced += produced;
    }

    public void addSold(int sold){
        this.sold +=sold;
    }

    public void addWrittenOff(int writtenOff){
        this.writtenOff += writtenOff;
    }

    public int getProduced(){
        return produced;
    }

    public int getSold(){
        return sold;
    }

    public int getWrittenOff(){
        return writtenOff;
    }

    public void resetStats() {
        produced = 0;
        sold = 0;
        writtenOff = 0;
    }
}
