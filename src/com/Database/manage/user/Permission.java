package com.Database.manage.user;

import com.Database.manage.db.NewQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author AW Developer
 */
public class Permission {

        public final int ACT_EDIT_DATA = 1;
        public final int ACT_ADD_DATA = 2;
        public final int ACT_DELETE_DATA = 3;
        public final int ACT_CREATE_PROFILE = 4;
        public final int TYPE_USER = 1;
        public final int TYPE_SUB_USERS = 2;
        public final int TYPE_DOWN_SUB_USERS = 2;
        public final int TYPE_USER_AND_SUB_USERS = 3;

        private User to;
        
        public Permission(User to) {
            this.to=to;
        }
        public void givePermission(User from,int type,int action,long timePeriod) throws Exception{
            if(from.getUserLevel().compareTo(to.getUserLevel())>0){
            String sql="INSERT INTO Permission VALUES(?,?,?,?,?,?)";
            new NewQuery(sql,
                    from.getUserId(),
                    to.getUserId(),
                    type,
                    action,
                    genarateCode(),
                    timePeriod).excecute();
            }else{
                throw new Exception("User level of \"from user\" must be greater than \"to user\"");
            }
        }
        public boolean hasPermission(int type,String code) throws SQLException{
            ArrayList<Object> row = new NewQuery("SELECT * FROM Permission WHERE ToUser=? AND Type=? AND Code=?",to.getUserId(),type,code).getRow();
            return false;
            
        }
        public static String genarateCode(){
            String s="";
            Random r=new Random();
            for (int i = 0; i < 10; i++) {
                s+=((char)r.nextInt(65, 116));
            }
            return s;
        }
    }