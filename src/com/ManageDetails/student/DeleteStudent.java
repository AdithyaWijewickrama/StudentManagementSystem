package com.ManageDetails.student;

import Standard.array;
import com.ui.frames.UserFrame;
import static com.ui.frames.UserFrame.ID_NO;
import com.Database.DBconnect;
import com.Database.Database;
import com.Database.Sql;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.Database.Sql.getCoveredTable;

public class DeleteStudent {

    List<String> ids=new ArrayList<>();
    public static ArrayList<String> tables;
    int deletionType;
    public int ID;
    public static final int MOVETOBIN = 0;
    public static final int PERMENATLY_DELETE = 1;
    public static final Connection CONN = DBconnect.CONN;
    public static final Connection CONN1 = DBconnect.CONN;

    public DeleteStudent(int deletionType) {
        this.deletionType = deletionType;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
    
    public static void removeImage(String id) throws SQLException {
        Sql.execute("DELETE FROM Images WHERE ID='" + id + "'", CONN1);
    }

    public int getId() {
        return ID;
    }

    public void deleteAccounts() {
        for (Object id : ids) {
            List<String> q = new ArrayList<>();
            q.add("DELETE FROM student WHERE \"" + ID_NO + "\"='" + id + "'");
            for (Object tname : Sql.getColumn("SELECT name FROM sqlite_master WHERE type='table'", 0, CONN)) {
                q.add("DELETE FROM \"" + tname + "\" WHERE ID='" + id + "'");
            }
            for (String query : q) {
                try {
                    Sql.execute(query, CONN);
                    System.out.println("deleted '" + id + "'");
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        }
    }

    /**
     * Move to bin folder
     *
     * @param id - Admission number
     * @param inf - Database and the tables
     * @throws java.sql.SQLException
     */
    public void moveToBin(String id, HashMap<Database, String[]> inf) throws SQLException {
        inf.entrySet().forEach((einf) -> {
            Connection conn = DBconnect.connect(einf.getKey());
            String[] tables = einf.getValue();
            for (String table : tables) {
                try {
                    ArrayList data = (ArrayList) Arrays.asList(array.getStr(Sql.getRow("SELECT * FROM [" + table + "] WHERE " + UserFrame.ID_NO + "='" + id + "'", conn)));
                    data.add(data.size() - 1, data.get(0));
                    data.add(0, ID);
                    Sql.insertData("INSERT INTO ", table, data.toArray(), CONN);
                    Sql.execute("DELETE FROM " + getCoveredTable(table) + " WHERE " + UserFrame.ID_NO + "='" + id + "'", conn);
                } catch (SQLException ex) {
                    Logger.getLogger(DeleteStudent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DeleteStudent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
