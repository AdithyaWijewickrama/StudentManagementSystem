package com.HealthReport;

import com.Database.Sql;
import static com.HealthReport.Health.INFECTED;
import static com.HealthReport.Health.NOTINFECTED;
import com.Database.DBconnect;
import java.sql.Connection;
import java.util.List;

public class Desseases {

    static Connection CONN = DBconnect.CONN;
    String id;

    public Desseases(String id) {
        this.id = id;
    }

    public static void addNew(String val) throws Exception {
        Health.setDefault(Types.Desseases, val);
    }

    public boolean hasData(String type) {
        return Sql.getValueAsString("SELECT Status FROM lab_reports WHERE ID='" + id + "' AND Type='" + type + "'", CONN) != null;
    }

    public static boolean hasData(String id, String type) {
        return Sql.getValue("SELECT ID FROM Desseases WHERE ID='" + id + "' AND Type='" + type + "'", CONN) != null;
    }

    public static List<Object> getTypes() {
        List<Object> s = Sql.getColumn("SELECT Value FROM Defaults WHERE Type='" + Types.Desseases + "'", 0, CONN);
        return s;
    }

    public String getStatus(String type) {
        String s = Sql.getValue("SELECT Status FROM Desseases WHERE ID='" + id + "' AND Type='" + type + "'", CONN).toString();
        return s != null ? s : "";
    }

    public String getInfo(String type) {
        Object s = Sql.getValue("SELECT Info FROM Desseases WHERE ID='" + id + "' AND Type='" + type + "'", CONN);
        return s != null ? s.toString() : "";
    }

    public Boolean isNotinfected(String type) {
        String s = getStatus(type);
        if (s.equals(INFECTED)) {
            return false;
        } else if (s.equals(NOTINFECTED)) {
            return true;
        }
        return null;
    }

    public void delete(String type) throws Exception {
        Sql.execute("DELETE FROM Desseases WHERE ID='" + type + "'", CONN);
    }

    public Boolean isInfected(String type) {
        return !isNotinfected(type);
    }

    public void insertData(String type, String status, String info) throws Exception {
        Sql.execute(!hasData(id) ? "INSERT INTO Desseases (ID,Type,Status,Info) VALUES ('" + id + "','" + type + "','" + status + "','" + info + "')" : "UPDATE Desseases SET Type='" + type + "',Status='" + status + "',Info='" + info + "' WHERE ID='" + id + "'", CONN);
    }
}

class test3 {

    public void main(String[] args) {
    }
}
