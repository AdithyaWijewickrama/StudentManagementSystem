package com.ManageDetails.school;

import com.Codes.Commons;
import com.Database.Sql;
import static com.Database.DBconnect.CONN;
import static com.Database.Sql.getCovered;
import java.awt.Rectangle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import jpaint.image.CropImage;

public class School {

    private ImageIcon profileImage;
    private String name;
    private String email;
    private String emailPassword;
    private String address;
    private String district;
    private ImageIcon badge;
    private String telephone;
    private String userName;

    public School(String username) {
        this.userName = username;
        if (isSingedUp(username)) {
            loadDetails(username);
        }
    }

    public final void loadDetails(String username1) {
        profileImage = Sql.getImage("SELECT Image FROM schooldetails WHERE Username='" + username1 + "'", CONN);
        name = Sql.getValueAsString("SELECT Name FROM schooldetails  WHERE Username='" + username1 + "'", CONN);
        email = Sql.getValueAsString("SELECT Email FROM schooldetails  WHERE Username='" + username1 + "'", CONN);
        emailPassword = Sql.getValueAsString("SELECT EmailPassword FROM schooldetails  WHERE Username='" + username1 + "'", CONN);
        address = Sql.getValueAsString("SELECT Address FROM schooldetails  WHERE Username='" + username1 + "'", CONN);
        district = Sql.getValueAsString("SELECT District FROM schooldetails  WHERE Username='" + username1 + "'", CONN);
        badge = Sql.getImage("SELECT Logo FROM schooldetails WHERE Username='" + username1 + "'", CONN);
        telephone = Sql.getValueAsString("SELECT Telephone FROM schooldetails  WHERE Username='" + username1 + "'", CONN);
    }

    public void updateDetails() {
        if (!isSingedUp(userName)) {
            try {
                Sql.execute("INSERT INTO schooldetails values(" + getCovered(userName) + ("," + Sql.EMPTY).repeat(8) + ")", CONN);
            } catch (SQLException ex) {
                Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Sql.execute("UPDATE schooldetails SET "
                    + "Name=" + getCovered(name) + ","
                    + "Email=" + getCovered(email) + ","
                    + "EmailPassword=" + getCovered(emailPassword) + ","
                    + "Address=" + getCovered(address) + ","
                    + "Telephone=" + getCovered(telephone) + ","
                    + "District=" + getCovered(district) + " WHERE UserName=" + getCovered(userName), CONN);
            if (profileImage != null) {
                Sql.insertImage("UPDATE schooldetails SET Image=? WHERE UserName=" + getCovered(userName), profileImage, CONN);
            } else {
                Sql.execute("UPDATE schooldetails SET Image=NULL WHERE UserName=" + getCovered(userName), CONN);
            }
            if (badge != null) {
                Sql.insertImage("UPDATE schooldetails SET Logo=? WHERE UserName=" + getCovered(userName), badge, CONN);
            } else {
                Sql.execute("UPDATE schooldetails SET Logo=NULL WHERE UserName=" + getCovered(userName), CONN);
            }
        } catch (SQLException ex) {
            Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ImageIcon getRoundImage() {
        if (profileImage == null) {
            return null;
        }
        return new ImageIcon(new CropImage(profileImage.getImage(), new Rectangle(
                profileImage.getIconWidth() > profileImage.getIconHeight() ? (profileImage.getIconWidth() - profileImage.getIconHeight()) / 2 : 0,
                profileImage.getIconWidth() < profileImage.getIconHeight() ? (profileImage.getIconHeight() - profileImage.getIconWidth()) / 2 : 0,
                profileImage.getIconWidth() > profileImage.getIconHeight() ? profileImage.getIconHeight() : profileImage.getIconWidth(),
                profileImage.getIconWidth() > profileImage.getIconHeight() ? profileImage.getIconHeight() : profileImage.getIconWidth())).cropEllips());
    }

    public Object[] getAll() {
        return new Object[]{name, address, telephone, badge, profileImage, email, emailPassword};
    }

    public void setAll(String[] data) {
        try {
            Sql.insertData("INSERT INTO ", "SchoolDetails", data, CONN);
        } catch (Exception ex) {
            Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String val) {
        name = val;
        Commons.setDefault("School_name", val, CONN);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String val) {
        email = val;
        Commons.setDefault("School_email", val, CONN);
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String val) {
        emailPassword = val;
        Commons.setDefault("School_email_password", val, CONN);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String val) {
        address = val;
        Commons.setDefault("School_address", val, CONN);
    }

    public ImageIcon getBadge() {
        return badge;
    }

    public void setLogo(ImageIcon badge) {
        this.badge = badge;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String val) {
        telephone = val;
        Commons.setDefault("School_phone", val, CONN);
    }

    public ImageIcon getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageIcon profImage) {
        this.profileImage = profImage;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) throws SQLException {
        Sql.execute("UPDATE schooldetails SET UserName=" + getCovered(userName) + " WHERE UserName=" + getCovered(this.userName), CONN);
        this.userName = userName;
    }

    public static boolean isSingedUp(String userName) {
        String s = "SELECT UserName FROM schooldetails WHERE UserName=" + getCovered(userName);
        return Sql.getValueAsString(s, CONN) != null;
    }
}
