package com.Database.manage.user;

/**
 *
 * @author AW Developer
 */
public enum UserLevel {

    SCHOOL_BOARD(0,"School Board"),
    SUPERINTENDENT(1,"Superintendent"),
    PRINCIPLE(2,"Principle"),
    VICE_PRINCIPLE(3,"Vice Principle"),
    DEPARTMENT_HEAD(4,"Department Head"),
    TEACHER(6,"Teacher"),
    STUDENT(7,"Student");

    public final int level;
    public final String name;

    private UserLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }
    
 
    
    
    public int getLevel() {
        return ordinal();
    }
}
