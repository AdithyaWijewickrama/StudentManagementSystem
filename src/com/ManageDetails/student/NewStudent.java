package com.ManageDetails.student;

import Standard.array;
import com.Codes.Commons;
import com.Database.Sql;
import static com.Database.Sql.getCovered;
import com.ui.frames.UserFrame;
import com.Database.DBconnect;
import static com.Database.DBconnect.CONN;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class NewStudent {

    public String id;
    public String name;
    public int grade;
    public String subGrade;
    public Date dateOfBirth;
    public String medium;
    public String gender;
    public String address;
    public String email;
    public String mobile;
    public String guardian;
    public Object[] data;

    /**
     * <html>
     * <pre>
     * 0    String id
     * 1    String name
     * 2    int age
     * 3    int grade
     * 4    String subGrade
     * 5    Date dateOfBirth
     * 6    String medium
     * 7    String gender
     * 8    String address
     * 9    String email
     * 10   String mobile
     * 11   String guardia
     * </pre>
     * </html>
     *
     * @param data
     * @throws java.text.ParseException
     */
    public NewStudent(Object[] data) throws Exception {
        this((String) data[0], (String) data[1], (int) data[2], (String) data[3], (String) data[4], (Date) data[5], (String) data[6], (String) data[7], (String) data[8], (String) data[9], (String) data[10]);
        setData();
    }

    public NewStudent(String id, String name, int grade, String subGrade, String medium, Date dateOfBirth, String gender, String mobile, String address, String guardian, String email) throws ParseException {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.subGrade = subGrade;
        this.dateOfBirth = dateOfBirth;
        this.medium = medium;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.guardian = guardian;
        setData();
    }

    public void setData() {
        this.data = new Object[]{
            this.id,
            this.name,
            this.grade,
            this.subGrade,
            this.medium,
            (String.format("%04d-%02d-%02d", dateOfBirth.getYear(), dateOfBirth.getMonth(), dateOfBirth.getDate())),
            this.gender,
            this.mobile,
            this.address,
            this.guardian,
            this.email
        };
    }

    public void checkData() throws Exception {
        for (Object object : getData()) {
            if (object == null || object.equals("")) {
                throw new NullPointerException("Required fields");
            }
        }
        SimpleDateFormat date = new SimpleDateFormat("yyy-mm-dd");
        date.setLenient(true);
        try {
            date.parse((String.format("%04d-%02d-%02d", dateOfBirth.getYear(), dateOfBirth.getMonth(), dateOfBirth.getDate())));
        } catch (ParseException ex) {
            Logger.getLogger(NewStudent.class.getName()).log(Level.SEVERE, null, ex);
            throw new ParseException("Date is not valid", 0);
        }
        if (!(email.contains("@") && email.endsWith(".com"))) {
            System.out.println(email);
            throw new ParseException("Email is not valid", 0);
        }
        if (Sql.getValue("SELECT " + getCovered(UserFrame.ID_NO) + " FROM " + getCovered(Student.SQLTABLE) + " WHERE " + getCovered(UserFrame.ID_NO) + "='" + id + "'", DBconnect.CONN) != null) {
            throw new Exception("There is a student registerrd Addded with " + id + " Admission number");
        }
    }

    public void add(Date dateAdded) throws Exception {
        Sql.execute("INSERT INTO student VALUES('"+String.join("','", array.getStr(data))+"')", CONN);
        Sql.execute("INSERT INTO Date_Added VALUES (" + getCovered(id) + ",'" + (String.format("%04d-%02d-%02d", dateAdded.getYear(), dateAdded.getMonth(), dateAdded.getDate())) + "')", DBconnect.CONN);
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    public void addImage(String img) throws SQLException, IOException {
        addImage(id,img);
    }

    public static void addImage(String id, String imgPath) throws SQLException, IOException {
        Sql.insertImage("INSERT INTO Images VALUES(" + Sql.getCovered(id) + ",?)", new ImageIcon(imgPath),CONN);
    }

    public static void main(String[] args) {
        try {
            ResultSet rs = Sql.ExecuteSQL("SELECT Date FROM Date_Added WHERE \"Admission No.\"='1'", DBconnect.CONN);
            System.out.println(rs.getDate(1));
        } catch (SQLException ex) {
            Logger.getLogger(NewStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
        Object[] data = new Object[]{"10464", "Adithya Wijewickrama", 11, 6, "A", new Date(2014, 6, 3), "Sinhala", "Male", "50, Madadeniya, Pujapitiya", "adithyawi3@gmail.com", "0704688470", "Mother"};
        System.out.println(Arrays.toString(array.getStr(data)));
        NewStudent a;
        try {
            a = new NewStudent(data);
            a.checkData();
            a.add(Commons.date);
        } catch (ParseException ex) {
            Logger.getLogger(NewStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NewStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
