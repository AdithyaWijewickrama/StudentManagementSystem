package com.Database.manage.user;

import com.Database.Sql;
import com.Database.manage.db.NewQuery;
import java.util.ArrayList;

/**
 *
 * @author AW Developer
 */
public class Admin extends User{

    public Admin(int userId) {
        super(userId);
    }

    @Override
    public void createUserProfile(Permission per) throws Exception {
        new NewQuery("INSERT INTO User VALUES(?,?,?,?)",userName,password,userLevel,Sql.imageToByteArray(profileImage)).excecute();
    }

}
