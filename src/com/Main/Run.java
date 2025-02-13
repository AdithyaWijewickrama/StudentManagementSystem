package com.Main;

import com.Database.DBconnect;
import com.Database.Sql;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Run {

    public static void main(String[] args) {
        try {
            Sql.execute("update student set `Date of birth`='2012-05-23' where `Date of birth`='0000-00-00'", DBconnect.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
