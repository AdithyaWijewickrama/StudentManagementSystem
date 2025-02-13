package com.HealthReport;

import com.Database.Sql;
import static com.Database.Sql.getRow;
import static com.HealthReport.Health.getStatus;
import com.Database.DBconnect;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.Database.Sql.execute;

public class Vision {

    public int denom;
    private String id;
    public static final String DATATABLE = "vission";
    public static final String FAR = "far";
    public static final String NEAR = "near";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String BOTH = "both";
    public static final String DENOMINATOR = "denominator";
    private static final Connection CONN = DBconnect.CONN;

    public Vision(String AdmissionNo) {
        this.id = AdmissionNo;
    }

    public boolean hasData(String nf) {
        return Sql.getValue("SELECT ID FROM " + getTable(nf) + " WHERE ID='" + id + "'", CONN) != null;
    }

    public static boolean hasData(String id, String nf) {
        return Sql.getValue("SELECT ID FROM " + getTable(nf) + " WHERE ID='" + id + "'", CONN) != null;
    }

    public static String getTable(String NF) {
        return ("'" + DATATABLE + "_" + NF + "'");
    }

    public void setAdmissionNo(String AdmissionNo) {
        this.id = AdmissionNo;
    }

    public void setVissions(int left, int right, int both,int denom, String NearORFar) {
        setVissions(id, left, right, both, denom, NearORFar);
    }

    public ArrayList<Object> getVissions(String NearORFar) {
        return getVissions(id, NearORFar);
    }

    public static void setVissions(String id, int left, int right, int both, int denom, String NearORFar) {
        try {
            String q = !hasData(id, NearORFar) ? "INSERT INTO " + getTable(NearORFar) + " (ID,Left,Right,Both,Denominator) VALUES ('" + id + "','" + left + "','" + right + "','" + both + "','" + denom + "')" : "UPDATE " + getTable(NearORFar) + " SET ID='" + id + "',Left='" + left + "',Right='" + right + "',Both='" + both + "',Denominator='" + denom + "'";
            execute(q, CONN);
        } catch (Exception ex) {
            Logger.getLogger(Vision.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<Object> getVissions(String ID, String NearORFar) {
        List<Object> row = getRow("SELECT * FROM " + getTable(NearORFar) + " WHERE ID='" + ID + "'", CONN);
        if(row!=null){
            row.remove(0);
        }else
            return null;
        return (ArrayList<Object>) row;
    }

    public static double getPersentage(Integer val, Integer denom) {
        if (val > denom) {
            return 0;
        }
        return (val.doubleValue() / denom.doubleValue()) * 100;
    }

    public String[] getStatuses(String NearORFar) {
        ArrayList<Object> vis = getVissions(id, NearORFar);
        if (vis != null) {
            int left = (int)vis.get(0);
            int right = (int)vis.get(1);
            int both = (int)vis.get(2);
            int denom = (int)vis.get(3);
            return new String[]{getStatus(getPersentage(left, denom)), getStatus(getPersentage(right, denom)), getStatus(getPersentage(both, denom))};
        } else {
            return null;
        }
    }

    public static String getStatuses(int l, int denom) {
        return getStatus(getPersentage(l, denom));
    }

    public static String getFullStatus(int denom, int[] vals) {
        double per = 0;
        for (int val : vals) {
            per += getPersentage(val, denom);
        }
        return getStatus(per / vals.length);
    }
}

class test {

    public static void main(String[] args) {
        System.out.println(Vision.getFullStatus(9, new int[]{7, 7, 6}));
    }
}
