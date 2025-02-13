package com.HealthReport;

import Convert.Length;
import Convert.Mass;
import com.ManageDetails.student.Student;
import com.Database.Sql;
import com.Database.DBconnect;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Growth {

    public static final String OBESITY = "Obese";
    public static final String OVERWEIGHT = "Over weight";
    public static final String THINNESS = "Thinness";
    public static final String SEVERE_THINNESS = "Severe thinness";
    public static final String NORMAL = "Normal";
    public String id;
    public String stat;
    public int age;

    public Growth(String id) {
        this.id = id;
        this.age = Student.getAge(id);
    }
    public void setStatus(String status){
        this.stat=status;
    }

    public static boolean hasData(String id) {
        return Sql.getValue("SELECT ID FROM Growth WHERE ID='" + id + "'", DBconnect.CONN) != null;
    }

    public List<Object> getAll() {
        return getAll(id);
    }

    public void setAll(double w, Mass wu, double h, Length hu) {
        try {
            Sql.execute(!hasData(id)?"INSERT INTO Growth (ID,Weight,weight_unit,Height,height_unit,Status) VALUES('"+id+"','"+w+"','"+wu+"','"+h+"','"+hu+"','"+stat+"')":"UPADATE Growth SET Weight='"+w+"',weight_unit='"+wu+"',Height=='"+h+"',height_unit='"+hu+"',Status='"+stat+"'", DBconnect.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(Growth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Object> getAll(String id) {
        return Sql.getRow("SELECT * FROM Growth WHERE ID='" + id + "'", DBconnect.CONN);
    }

    public static String getWeightStatus(double bmi, int age, String gender) {
        double s1 = 0;
        double s2 = 0;
        double s3 = 0;
        double s4 = 0;
        String stat;
        switch (gender) {
            case "Female":
                if (age < 7) {
                    s1 = 12;
                    s2 = 13;
                    s3 = 17;
                    s4 = 19;
                } else if (age < 13) {
                    s1 = 13;
                    s2 = 14;
                    s3 = 20;
                    s4 = 21;
                } else if (age < 18) {
                    s1 = 14.5;
                    s2 = 16;
                    s3 = 23;
                    s4 = 28;
                }
                break;
            case "Male":
                if (age < 7) {
                    s1 = 12;
                    s2 = 13;
                    s3 = 17.5;
                    s4 = 20;
                } else if (age < 13) {
                    s1 = 13;
                    s2 = 14.5;
                    s3 = 22;
                    s4 = 25;
                } else if (age < 18) {
                    s1 = 14.5;
                    s2 = 16;
                    s3 = 25;
                    s4 = 29;
                }
                break;
        }
        if (bmi < s1) {
            stat = SEVERE_THINNESS;
        } else if (bmi < s2) {
            stat = THINNESS;
        } else if (bmi < s3) {
            stat = NORMAL;
        } else if (bmi < s4) {
            stat = OVERWEIGHT;
        } else {
            stat = OBESITY;
        }
        return stat;
    }

    public static double getBmi(double weight, Mass w, double height, Length l) {
        return Convert.Convert.mass(weight, w, Mass.Kg) / Math.pow(Convert.Convert.length(height, l, Length.Meter), 2);
    }

}
