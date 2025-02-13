package com.Database.manage.user;

import static com.Database.Sql.imageToByteArray;
import com.Database.manage.db.NewQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author AW Developer
 */
public abstract class User {

    protected int userId;
    protected String userName;
    protected String password;
    protected UserLevel userLevel;
    protected ImageIcon profileImage;
    protected ArrayList<Object> data;

    public User(int userId) {
        this.userId=userId;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ImageIcon getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageIcon profileImage) {
        this.profileImage = profileImage;
    }

    public static boolean isSingedUp(int userId) throws SQLException {
        return new NewQuery("SELECT UserName FROM User WHERE UserId=?", userId).getString() != null;
    }

    public abstract void createUserProfile(Permission per) throws Exception;

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public User loadData() throws SQLException {
        if (isSingedUp(userId)) {
            data = getData();
            int i=0;
            
            userId = (int) data.get(i++);
            userName = (String) data.get(i++);
            password = (String) data.get(i++);
            userLevel = UserLevel.valueOf((String) data.get(i++));
            profileImage = new ImageIcon((byte[]) data.get(i++));
        }
        return this;
    }

    public ArrayList<Object> getData() throws SQLException {
        if (isSingedUp(userId)) {
            return new NewQuery("SELECT * FROM User WHERE UserId=?", userName).getRow();
        }
        return null;
    }

    public void updateData() throws SQLException {
        if (isSingedUp(userId)) {
            new NewQuery("UPDATE User SET UserName=?,Password=?,UserLevel=?,ProfileImage=? WHERE UserName=?", userName, password, userLevel, imageToByteArray(profileImage)).excecute();
        }
    }
}
