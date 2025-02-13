package com.SystemSecurity;

import com.Database.Sql;
import com.Database.DBconnect;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.Database.Sql.execute;
import java.util.ArrayList;

public final class Security {

    public static final Connection CONN = DBconnect.CONN;
    private List<String> data = new ArrayList<>();
    private String Password;
    private String user_name;
    private String Question;
    private String Answer;

    public Security(String user) {
        List<Object> row = Sql.getRow("SELECT * FROM security WHERE Username=" + Sql.getCovered(user), CONN);
        this.user_name = user;
        if (row != null) {
            for (Object d : row) {
                data.add(d.toString());
            }
            user_name = data.get(0);
            Password = data.get(1);
            Question = data.get(2);
            Answer = data.get(3);
        } else {
            for (int i = 0; i < 4; i++) {
                data.add(null);
                setUsername(user);
            }
        }
    }

    public void update() {
        try {
            execute("INSERT OR REPLACE INTO security VALUES(" + Sql.getCovered(data.get(0)) + "," + Sql.getCovered(data.get(1)) + "," + Sql.getCovered(data.get(2)) + "," + Sql.getCovered(data.get(3)) + ")", CONN);
        } catch (Exception ex) {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> all) {
        data = all;
    }

    public String getPassword() {
        return new Encryption().decrypt(Password);
    }

    public void setPassword(String password) {
        Password = new Encryption().encrypt(password);
        data.set(1, new Encryption().encrypt(password));
    }

    public String getUsername() {
        return user_name;
    }

    public void setUsername(String userName) {
        user_name = userName;
        data.set(0, userName);
    }

    public String getQuestion() {
        return new Encryption().decrypt(Question);
    }

    public void setQuestion(String question) {
        Question = new Encryption().encrypt(question);
        data.set(2, new Encryption().encrypt(question));
    }

    public String getAnswer() {
        return new Encryption().decrypt(Answer);
    }

    public void setAnswer(String answer) {
        Answer = new Encryption().encrypt(answer);
        data.set(3, new Encryption().encrypt(answer));
    }

    public static boolean isSingedUp(String userName) {
        return Sql.getValue("SELECT * FROM security WHERE Username=" + Sql.getCovered(userName), CONN) != null;
    }
}
