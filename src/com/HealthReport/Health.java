package com.HealthReport;

import com.Codes.Commons;
import com.Database.DBconnect;
import com.Database.Sql;
import java.util.List;

public class Health {

    public static final String NORMAL = "Normal";
    public static final String NOTINFECTED = "Normal";
    public static final String INFECTED = "Infected";
    public static final String ABNORMAL = "Notinfected";
    public static final String WEAK = "Weak";
    public static final String VERYWEAK = "Very weak";
    public static final String GOOD = "Good";

    public static List<Object> getDefaults(Types type) {
        return Sql.getColumn("SELECT Value FROM health_defaults WHERE Type='" + type.getName() + "'", 0, DBconnect.CONN);
    }

    public static void setDefault(Types type, String val) throws Exception {
        Sql.execute("INSERT INTO health_defaults VALUES ('" + type.getName() + "','" + val + "')", DBconnect.CONN);
    }

    public static void removeDefault(Types type, String val) throws Exception {
        Sql.execute("DELETE FROM health_defaults WHERE Type='" + type.getName() + "' AND Value='" + val + "'", DBconnect.CONN);
    }

    public static String getStatus(double per) {
        if (per == 0) {
            return Commons.EMPTYSTRING;
        } else if (per < 30) {
            return VERYWEAK;
        } else if (per < 50) {
            return WEAK;
        } else if (per < 85) {
            return NORMAL;
        } else {
            return GOOD;
        }
    }

    public static void main(String[] args) {

    }
}
