package com.Database.manage.user;

/**
 *
 * @author AW Developer
 */
public class UserException extends Exception{

    String ex;
    public UserException(String ex) {
        super(ex);
        this.ex=ex;
    }

    @Override
    public String getMessage() {
        return ex;
    }
    
}
