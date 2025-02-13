package com.Database;

import com.Codes.Commons;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBconnect {

    public final static Connection CONN = connect(Database.STUDENT);

    public static Connection connect(Database db) {
        try {
            Connection conn;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + db.getName() + "");
            System.out.println("Connection succesful!");
            return conn;
        } catch (SQLException | ClassNotFoundException ex) {
            Commons.showMsg(ex);
            Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        return null;
    }
}
