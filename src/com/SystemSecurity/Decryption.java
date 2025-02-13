package com.SystemSecurity;

import com.Codes.Commons;
import com.Database.Sql;
import com.Main.Defaults;
import java.sql.Connection;
import static com.ui.frames.UserFrame.ID_NO;
import com.Database.DBconnect;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Decryption {

    String id;
    static Connection conn = DBconnect.CONN;
    public static List<Object> All = Sql.getColumn("SELECT Value FROM Decryption", 0, conn);
    public static List<Object> Data;
    public static char Character_Seq = Commons.getDefault(Defaults.Character_Seq, conn).charAt(0);
    public static String Id = All.get(0).toString();
    public static String Name = All.get(1).toString();
    public static String Age = All.get(2).toString();
    public static String Grade = All.get(3).toString();
    public static String Medium = All.get(4).toString();
    public static String Dateofbirth = All.get(5).toString();
    public static String Gender = All.get(6).toString();
    public static String Telephone = All.get(7).toString();
    public static String HomeAddress = All.get(8).toString();
    public static String Guardian = All.get(9).toString();
    public static String Email = All.get(10).toString();
    public static String SchoolName = All.get(11).toString();
    public static String Enter = All.get(12).toString();

    static void setAll() {
        try {
            Sql.insertData("INSERT OR REPLACE INTO ", "Decryption", new String[]{Id, Name, Age, Grade, Medium, Dateofbirth, Gender, Telephone, HomeAddress, Guardian, Email, SchoolName, Enter}, conn);
        } catch (Exception ex) {
            Logger.getLogger(Decryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Object> getData() {
        return Data;
    }

    public static void setData(List<Object> Data) {
        Decryption.Data = Data;
    }

    public static char getCharacter_Seq() {
        return Character_Seq;
    }

    public static void setCharacter_Seq(char Character_Seq) {
        Decryption.Character_Seq = Character_Seq;
        Commons.setDefault(Defaults.Character_Seq, Character.toString(Character_Seq), conn);
    }

    public static String getId() {
        return Id;
    }

    public static void setId(String Id) {
        Decryption.Id = Id;
        setAll();
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String Name) {
        Decryption.Name = Name;
        setAll();
    }

    public static String getAge() {
        return Age;
    }

    public static void setAge(String Age) {
        Decryption.Age = Age;
        setAll();
    }

    public static String getGrade() {
        return Grade;
    }

    public static void setGrade(String Grade) {
        Decryption.Grade = Grade;
        setAll();
    }

    public static String getMedium() {
        return Medium;
    }

    public static void setMedium(String Medium) {
        Decryption.Medium = Medium;
        setAll();
    }

    public static String getDateofbirth() {
        return Dateofbirth;
    }

    public static void setDateofbirth(String Dateofbirth) {
        Decryption.Dateofbirth = Dateofbirth;
        setAll();
    }

    public static String getGender() {
        return Gender;
    }

    public static void setGender(String Gender) {
        Decryption.Gender = Gender;
        setAll();
    }

    public static String getTelephone() {
        return Telephone;
    }

    public static void setTelephone(String Telephone) {
        Decryption.Telephone = Telephone;
        setAll();
    }

    public static String getHomeAddress() {
        return HomeAddress;
    }

    public static void setHomeAddress(String HomeAddress) {
        Decryption.HomeAddress = HomeAddress;
        setAll();
    }

    public static String getGuardian() {
        return Guardian;
    }

    public static void setGuardian(String Guardian) {
        Decryption.Guardian = Guardian;
        setAll();
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String Email) {
        Decryption.Email = Email;
        setAll();
    }

    public static String getSchoolName() {
        return SchoolName;
    }

    public static void setSchoolName(String SchoolName) {
        Decryption.SchoolName = SchoolName;
        setAll();
    }

    public Decryption(String id) {
        conn = DBconnect.CONN;
        Data = Sql.getRow("SELECT * FROM student WHERE \"" + ID_NO + "\"='" + id + "'", conn);
        Decryption.Character_Seq = Commons.getDefault(Defaults.Character_Seq, conn).charAt(0);
        All = Sql.getColumn("SELECT Value FROM Decryption", 0, conn);
        this.id = id;
    }

    public String[][] getDecrypted(String[][] s) {
        for (int i = 0; i < s.length; i++) {
            s[i] = Decryption.this.getDecrypted(s[i]);
        }
        return s;
    }

    public String[] getDecrypted(String[] s) {
        for (int i = 0; i < s.length; i++) {
            s[i] = getDecrypted(s[i]);
        }
        return s;
    }

    public static String getDecrypted(String s, String id) {
        return new Decryption(id).getDecrypted(s);
    }

    public static String[] getDecrypted(String[] s, String id) {
        return new Decryption(id).getDecrypted(s);
    }

    public static String[][] getDecrypted(String[][] s, String id) {
        return new Decryption(id).getDecrypted(s);
    }

    public String getDecrypted(String s) {
        char[] d = s.toCharArray();
        String rv = "";
        for (int i = 0; i < d.length; i++) {
            char c = d[i];
            if (c == Character_Seq) {
                for (Object a : All) {
                    if (d.length > i + a.toString().length()) {
                        String dt = String.copyValueOf(d, i + 1, a.toString().length());
                        if (a.equals(dt)) {
                            rv = rv.concat(Data.get(All.indexOf(a)).toString());
                            i += a.toString().length();
                        }
                    }
                }
            } else {
                rv = rv.concat(String.valueOf(c));
            }
        }
        return rv;
    }

}