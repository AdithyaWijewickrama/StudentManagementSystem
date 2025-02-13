package com.HealthReport;

import static com.Codes.Commons.EMPTYSTRING;
import com.Database.Sql;
import static com.Database.Sql.getRow;
import static com.HealthReport.Health.getStatus;
import static com.Database.DBconnect.CONN;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.Database.Sql.execute;

public class Hearing {

    public int denom;
    private String id;
    public static final String DATATABLE = "hearing";
    public static final String FAR = "far";
    public static final String NEAR = "near";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String BOTH = "both";
    public static final String DENOMINATOR = "denominator";

    public Hearing(String AdmissionNo, int denom) {
        this.id = AdmissionNo;
        this.denom = denom;
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

    public void setHearings(int left, int right, int both, String NearORFar) {
        setHearings(id, left, right, both, denom, NearORFar);
    }

    public ArrayList<Object> getHearings(String NearORFar) {
        return getHearings(id, NearORFar);
    }

    public static void setHearings(String id, int left, int right, int both, int denom, String nof) {
        try {
            String q = hasData(id, nof) ? "INSERT INTO " + getTable(nof) + " (ID,Left,Right,Both,Denominator) VALUES ('" + id + "','" + left + "','" + right + "','" + both + "','" + denom + "')" : "INSERT INTO " + getTable(nof) + " ID='" + id + "',Left='" + left + "',Right='" + right + "',Both='" + both + "',Denominator='" + denom + "'";
            execute(q, CONN);
        } catch (Exception ex) {
            Logger.getLogger(Hearing.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<Object> getHearings(String ID, String NearORFar) {
        List<Object> row = getRow("SELECT * FROM " + getTable(NearORFar) + " WHERE ID='" + ID + "'", CONN);
        if(row!=null){
            row.remove(0);
        }else
            return null;
        return (ArrayList<Object>) row;
    }

    public static double getPersentage(Integer val, Integer denom) {
        return (val.doubleValue() / denom.doubleValue()) * 100;
    }

    public String[] getStatuses(String NearORFar) {
        ArrayList<Object> vis = getHearings(id, NearORFar);
        if (vis != null) {
            int left = (int) vis.get(0);
            int right = (int) vis.get(1);
            int both = (int) vis.get(2);
            int denom = (int) vis.get(3);
            return new String[]{getStatus(getPersentage(left, denom)), getStatus(getPersentage(right, denom)), getStatus(getPersentage(both, denom))};
        } else {
            return null;
        }
    }

    public static String getStatuses(int l, int denom) {
        return getStatus(getPersentage(l, denom));
    }

    public static String getFullStatus(int denom, int[] vals) {
        if (vals.length == 0) {
            return EMPTYSTRING;
        }
        double per = 0;
        for (int val : vals) {
            per += val / (denom == 0 ? 1 : denom);
        }
        return getStatus(per * 100);
    }
}
