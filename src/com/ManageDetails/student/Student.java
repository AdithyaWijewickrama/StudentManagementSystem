package com.ManageDetails.student;

import com.Database.Sql;
import static com.Database.Sql.getRow;
import com.ui.frames.UserFrame;
import com.Printing.BarCode;
import com.Database.DBconnect;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.Codes.Commons;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import static com.Database.Sql.getCoveredTable;

public class Student implements StudentDetails {

    public static final ImageIcon EMPTY = new ImageIcon("src\\Images\\EmptyImage.png");
    public static final int MAXAGE = 25;
    public static final int MINAGE = 5;
    public static int BARCODE_HEIGHT = 100;
    public static int BARCODE_WIDTH = 100;
    public static String[] GENDERS = {"Male", "Female", "Other"};
    public static String[] MEDIUMS = {"Sinhala", "Tamil", "English"};
    public static int[] AGES = {5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    public static int[] GRADES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13};

    public static void setData(String id, JTextField... fields) {
        ArrayList<Object> data = new Student(id).getAll();
        for (int i = 0; i < fields.length; i++) {
            JTextField field = fields[i];
            if (field != null) {
                field.setText(data.get(i).toString());
            }
        }
    }

    public static String getQRCodeName(String id) {
        return id + ".png";
    }

    public static ImageIcon getQRCode(String id) {
        String[] data = new Student(id).getAllS();
        String newdata = "";
        for (String data1 : data) {
            newdata = newdata.concat(data1 + "\n");
        }
        try {
            return new ImageIcon(BarCode.getBuffer(newdata, BarcodeFormat.QR_CODE, 200, 200));
        } catch (WriterException | IOException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void createQRCode(String id, String out, BarcodeFormat bf) throws WriterException, IOException {
        List<Object> data = getRow("SELECT * FROM student WHERE `Admission No.`='" + id + "'", DBconnect.CONN);
        String newdata = "";
        for (Object data1 : data) {
            newdata = newdata.concat(data1 + "\n");
        }
        BarCode.create(newdata, out, bf, BARCODE_WIDTH, BARCODE_HEIGHT);
    }

    public static boolean hasImage(String id) {
        return Sql.getValue("SELECT Image FROM images WHERE ID='" + id + "'", DBconnect.CONN) != null;
    }

    public static String getImageName(String id) {
        return id + ".png";
    }

    public static ImageIcon getImage(String id) {
        return Sql.getImage("SELECT image FROM images WHERE ID='" + id + "'", DBconnect.CONN);
    }

    Connection conn = DBconnect.CONN;
    String Id;
    ArrayList<Object> all;
    String Name;
    int Age;
    int Grade;
    String Subgrade;
    String Medium;
    Date birth;
    String Gender;
    String Telephone;
    String HomeAddress;
    String Guardian;
    String Email;
    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int GRADE = 2;
    public static final int SUBGRADE = 3;
    public static final int MEDIUM = 4;
    public static final int DATEOFBIRTH = 5;
    public static final int GENDER = 6;
    public static final int TELEPHONE = 7;
    public static final int HOMEADDRESS = 8;
    public static final int GUARDIAN = 9;
    public static final int EMAIL = 10;
    public static final String SQLTABLE = "student";

    public Student(String id) {
        this.Id = id;
        this.all = (ArrayList<Object>) Sql.getRow("SELECT * FROM student WHERE " + getCoveredTable(UserFrame.ID_NO) + "='" + id + "'", conn);
        this.Name = (String) all.get(1);
        this.Grade = (int) all.get(2);
        this.Subgrade = (String) all.get(3);
        this.Medium = (String) all.get(4);
        this.birth = new Date((long) all.get(5));
        this.Gender = (String) all.get(6);
        this.Telephone = (String) all.get(7);
        this.HomeAddress = (String) all.get(8);
        this.Guardian = (String) all.get(9);
        this.Email = (String) all.get(0);
        Age = (int) getDayAge() / 365;
    }

    public ArrayList<Object> getAll() {
        return all;
    }

    public String[] getAllS() {
        String[] s = new String[all.size()];
        for (int i = 0; i < all.size(); i++) {
            Object o = all.get(i);
            s[i] = o == null ? ""
                    : o instanceof String ? o.toString()
                            : o instanceof Date ? String.format("%04d-%02d-%02d", ((Date) o).getYear(), ((Date) o).getMonth(), ((Date) o).getDate())
                                    : o + "";
        }
        return s;
    }

    @Override
    public String getAdmissionNo() {
        return Id;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public int getAge() {
        return Age;
    }

    @Override
    public int getGrade() {
        return Grade;
    }

    @Override
    public String getSubgrade() {
        return Subgrade;
    }

    @Override
    public String getMedium() {
        return Medium;
    }

    @Override
    public Date getDateOfBirth() {
        return birth;
    }

    public String getDateOfBirthS() {
        return String.format("%04d-%02d-%02d", birth.getYear(), birth.getMonth(), birth.getDate());
    }

    @Override
    public String getGender() {
        return Gender;
    }

    @Override
    public String getTelephone() {
        return Telephone;
    }

    @Override
    public String getAddress() {
        return HomeAddress;
    }

    @Override
    public String getGuardian() {
        return Guardian;
    }

    public static Date getDateAdded(String id) {
        return new Date((long) Sql.getValue("SELECT Date FROM date_added WHERE ID='" + id + "'", DBconnect.CONN));
    }

    public static Date getLatestUpdate(String id) {
        return new Date((long) Sql.getValue("SELECT Date FROM last_update WHERE ID='" + id + "'", DBconnect.CONN));
    }

    @Override
    public String getEmail() {
        return Email;
    }

    public int getDayAge() {
        return (int) calcAge(new Date(birth.getYear(), birth.getMonth() - 1, birth.getDate()), Commons.date);
    }

    public static String getName(String id) {
        Student s = new Student(id);
        return s.getName();
    }

    public static int getAge(String id) {
        Student s = new Student(id);
        return s.getAge();
    }

    public static int getGrade(String id) {
        Student s = new Student(id);
        return s.getGrade();
    }

    public static String getSubgrade(String id) {
        Student s = new Student(id);
        return s.getSubgrade();
    }

    public static String getMedium(String id) {
        Student s = new Student(id);
        return s.getMedium();
    }

    public static String getDateofbirths(String id) {
        Student s = new Student(id);
        return s.getDateOfBirthS();
    }

    public static Date getDateofbirth(String id) {
        Student s = new Student(id);
        return s.getDateOfBirth();
    }

    public static String getGender(String id) {
        Student s = new Student(id);
        return s.getGender();
    }

    public static String getTelephone(String id) {
        Student s = new Student(id);
        return s.getTelephone();
    }

    public static String getHomeAddress(String id) {
        Student s = new Student(id);
        return s.getAddress();
    }

    public static String getGuardian(String id) {
        Student s = new Student(id);
        return s.getGuardian();
    }

    public static String getEmail(String id) {
        Student s = new Student(id);
        return s.getEmail();
    }

    public static String getGrade(int g, String sg) {
        return String.format("%02d%s", g, sg);
    }

    public static String getSubGrade(String grade) {
        return String.valueOf(grade.toCharArray(), 2, 1);
    }

    public static long calcAge(Date b, Date d) {
        return (d.getTime() - b.getTime()) / (1000 * 60 * 60 * 24);
    }

    public int[] getAgeConf() {
        int dates = getDayAge();
        int leap = 0;
        for (int i = birth.getYear(); i <= Commons.date.getYear(); i++) {
            leap += i % 4 == 0 ? 1 : 0;
        }
        dates -= leap;
        int years = dates / 365;
        int weaks = dates % 365 / 7;
        int days = dates % 365 % 7;
        return new int[]{years, weaks, days};
    }

    public static int[] getAgeConf(Date birth, Date cD) {
        int dates = (int) calcAge(birth, cD);
        int leap = 0;
        for (int i = birth.getYear(); i <= Commons.date.getYear(); i++) {
            leap += i % 4 == 0 ? 1 : 0;
        }
        dates -= leap;
        int years = dates / 365;
        int weaks = dates % 365 / 7;
        int days = dates % 365 % 7;
        return new int[]{years, weaks, days};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Student("1024").getAgeConf()));
    }
}
