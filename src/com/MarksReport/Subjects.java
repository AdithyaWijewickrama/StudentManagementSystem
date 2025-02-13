package com.MarksReport;

import com.Database.DBconnect;
import java.sql.Connection;

public class Subjects {
    
    public static final Connection CONN = DBconnect.CONN;
    public static final String[] JUNIOR = {
        "Religion",
        "Primary Language",
        "English",
        "Mathamatics",
        "Science",
        "History",
        "Geography",
        "Civics",
        "Health",
        "Easthetic",
        "IT",
        "PTS",
        "Secondary Language"};
    public static final String[] PRIMARY = {
        "Religion",
        "Primary Language",
        "Mathamatics",
        "Environmental Studies",
        "English",
        "Secondary Language",
        "Practical Studies",
        "Reading",
        "Writing"};
    public static final String[] ODINARYLEVEL = {
        "Religion",
        "Primary Language",
        "English",
        "Mathamatics",
        "Science",
        "History",
        "Basket01",
        "Basket02",
        "Easthetic",};
    public static final String[] ADVANCEDLEVEL = {
        "Subject01",
        "Subject02",
        "Subject03",
        "English"
    };
    String[] subs;
    int grade;

    public Subjects(int grade) {
        this.grade = grade;
        if (grade < 6) {
            subs = PRIMARY;
        } else if (grade < 10) {
            subs = JUNIOR;
        } else if (grade < 12) {
            subs = ODINARYLEVEL;
        } else if (grade < 14) {
            subs = ADVANCEDLEVEL;
        } else {
            subs = null;
        }
    }

    public String[] getSubs() {
        return subs;
    }

    public int getGrade() {
        return grade;
    }
}
