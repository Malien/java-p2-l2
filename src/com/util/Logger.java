package com.util;

public class Logger {

    private String name;

    public Logger(String name) {
        this.name = name;
    }

    public void info(String msg){
        System.out.println("["+name+"] - INFO: "+msg);
    }
    public void warn(String msg){
        System.out.println("["+name+"] - WARN: "+msg);
    }
    public void error(String msg){
        System.out.println("["+name+"] - ERROR: "+msg);
    }
    public void fatal(String msg){
        System.out.println("["+name+"] - FATAL: "+msg);
    }
}
