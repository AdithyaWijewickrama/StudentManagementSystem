package com.Database;

public enum Database {
    STUDENT("Student Management System Database.sqlite"),
    PRINTER("src\\Print Details\\Print Details.sqlite"),
    ;
    
    private final String PATH;
    private Database(String path) {
        PATH = path;
    }

    public String getName() {
        return PATH;
    }

}
