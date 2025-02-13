package com.ManageDetails.student;

import com.SystemSecurity.Decryption;
import com.Communication.TrayMessage;
import com.Database.Sql;
import com.ui.frames.UserFrame;
import static com.ui.frames.UserFrame.ID_NO;
import com.Database.DBconnect;
import com.ManageDetails.Columns;
import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateStudent {

    private static final Connection CONN = DBconnect.CONN;
    public String[] ids;
    public ArrayList<Columns> cols;
    public ArrayList<String> data;
    public boolean decrypted;

    public UpdateStudent(String[] ids, ArrayList<Columns> cols, ArrayList<String> data, boolean decrypted) throws IndexOutOfBoundsException {
        this.ids = ids;
        this.cols = cols;
        this.data = data;
        this.decrypted = decrypted;
        checkData();
    }

    public static void updateImage(String id, String path) throws SQLException, IOException {
        DeleteStudent.removeImage(id);
        NewStudent.addImage(id, path);
        new TrayMessage(id + " image updated").display();
    }

    private void checkData() throws IndexOutOfBoundsException {
        if (cols.size() != data.size()) {
            throw new IndexOutOfBoundsException("number columns must be equals to number of data " + cols.size() + " != " + data.size());
        }
    }

    public void update(String lastUpdate) throws SQLException {
        if (decrypted) {
            for (String id : ids) {
                String sql = "UPDATE " + Sql.getCoveredTable(Student.SQLTABLE) + " SET ";
                for (int j = 0; j < cols.size(); j++) {
                    String col = cols.get(j).getName();
                    String dt = data.get(j);
                    sql = sql.concat(" " + Sql.getCoveredTable(col) + "=" + Sql.getCovered(decrypted ? new Decryption(id).getDecrypted(dt) : dt) + (j != cols.size() - 1 ? "," : ""));
                }
                sql = sql.concat(" WHERE " + Sql.getCovered(UserFrame.ID_NO) + "='" + id + "'");
                System.out.println(sql);
                Sql.execute(sql, CONN);
                sql = "UPDATE Last_Update SET Date='" + lastUpdate + "' WHERE " + Sql.getCovered(UserFrame.ID_NO) + "='" + id + "'";
                Sql.execute(sql, CONN);
            }
        } else {
            String sql = "UPDATE " + Sql.getCoveredTable(Student.SQLTABLE) + " SET ";
            for (int j = 0; j < cols.size(); j++) {
                String col = cols.get(j).getName();
                String dt = data.get(j);
                sql = sql.concat(" " + Sql.getCoveredTable(col) + "=" + Sql.getCovered(dt) + (j != cols.size() - 1 ? "," : ""));
            }
            sql = sql.concat(" WHERE ");
            String idCnt = "";
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                idCnt = idCnt.concat(Sql.getCoveredTable(ID_NO) + "='" + id + "' ").concat(i == ids.length - 1 ? "" : "OR ");
            }
            sql = sql.concat(idCnt);
            System.out.println(sql);
            Sql.execute(sql, CONN);
            sql = "UPDATE Last_Update SET Date='" + lastUpdate + "' WHERE " + idCnt;
            Sql.execute(sql, CONN);
        }
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public ArrayList<Columns> getCols() {
        return cols;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public boolean isDecrypted() {
        return decrypted;
    }

    public static void main(String[] args) {
        try {
            updateImage("1024", "System generated image.png");
        } catch (SQLException ex) {
            Logger.getLogger(UpdateStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
