package com.HealthReport;

import com.Database.Sql;
import static com.HealthReport.Health.ABNORMAL;
import static com.HealthReport.Health.NORMAL;
import com.Database.DBconnect;
import java.sql.Connection;
import java.util.List;

public class LabReports {

    static Connection CONN = DBconnect.CONN;
    String id;

    public LabReports(String id) {
        this.id = id;
    }

    public boolean hasData(String type) {
        return Sql.getValueAsString("SELECT Status FROM lab_reports WHERE ID='" + id + "' AND Type='" + type + "'", CONN) != null;
    }

    public static void addNew(String val) throws Exception {
        Health.setDefault(Types.LabReports, val);
    }

    public static List<Object> getTypes() {
        List<Object> s = Sql.getColumn("SELECT Value FROM Defaults WHERE Type='" + Types.LabReports + "'", 0, CONN);
        return s;
    }

    public String getStatus(String type) {
        String s = Sql.getValueAsString("SELECT Status FROM lab_reports WHERE ID='" + id + "' AND Type='" + type + "'", CONN);
        return s != null ? s : "";
    }

    public String getInfo(String type) {
        String s = Sql.getValueAsString("SELECT Info FROM lab_reports WHERE ID='" + id + "' AND Type='" + type + "'", CONN);
        return s != null ? s : "";
    }

    public Boolean isNormal(String type) {
        String s = getStatus(type);
        if (s.equals(NORMAL)) {
            return true;
        } else if (s.equals(ABNORMAL)) {
            return false;
        }
        return null;
    }

    public void delete(String type) throws Exception {
        Sql.execute("DELETE FROM lab_reports WHERE ID='" + type + "'", CONN);
    }

    public Boolean isAbnormal(String type) {
        return !isNormal(type);
    }

    public void insertData(String type, String status, String info) throws Exception {
        Sql.execute(!hasData(id, type) ? "INSERT INTO lab_reports (ID,Type,Status,Info) VALUES ('" + id + "','" + type + "','" + status + "','" + info + "')" : "UPDATE lab_reports SET Type='" + type + "',Status='" + status + "',Info='" + info + "' WHERE ID='" + id + "'", CONN);
    }

    public static boolean hasData(String id, String type) {
        return Sql.getValue("SELECT ID FROM lab_reports WHERE ID='" + id + "' Type='" + type + "'", CONN) != null;
    }

}

class test2 {

    public static void main(String[] args) {
    }
}
