package com.Database.manage.db;

import com.Database.DBconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author AW Developer
 */
public final class NewQuery {

    private static Connection conn = DBconnect.CONN;
    private PreparedStatement prepareStatement;
    private ResultSet resultSet;

    private List dataList;

    String query;
    private ResultSetMetaData metaData;

    public NewQuery(String q) {
        query = q;
        try {
            setQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(NewQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public NewQuery(String q, Object... data) {
        query = q;
        try {
            setQuery(query, data);
        } catch (SQLException ex) {
            Logger.getLogger(NewQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setQuery(String query) throws SQLException {
        this.query = query;
        getPrepared();
        excuteQuery();
    }

    public void setQuery(String query, Object[] data) throws SQLException {
        this.query = query;
        getPrepared();
        for (int i = 0; i < data.length; i++) {
            Object object = data[i];
            prepareStatement.setObject(i, object);
        }
        excuteQuery();
    }

    public void getPrepared() throws SQLException {
        prepareStatement = conn.prepareStatement(query);
    }

    public void excuteQuery() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        dataList = DbUtils.resultSetToNestedList(prepareStatement.executeQuery());
        resultSet = prepareStatement.executeQuery();
        metaData = resultSet.getMetaData();
    }

    public void excecute() throws SQLException {
        prepareStatement.execute();
    }

    public String getString() throws SQLException {
        return String.valueOf(getObject());
    }

    public Object getObject() throws SQLException {
        if (dataList != null) {
            return ((List) dataList.get(0)).get(0);
        } else {
            return null;
        }
    }

    public List<List> getTableList() throws SQLException {
        List<List> rows = new ArrayList<>();
        for (Object row : getList()) {
            rows.add((List<Object>) row);
        }
        return rows;
    }

    public ArrayList<Object> getList() throws SQLException {
        return new ArrayList<>(dataList);
    }

    public ArrayList<Object> getRow() throws SQLException {
        return getRow(0);
    }
    public ArrayList<Object> getRow(int rowIndex) throws SQLException {
        return new ArrayList<>(getTableList().get(rowIndex));
    }

    public ArrayList getColumn(int columnIndex) throws SQLException {
        List<List> tableList = getTableList();
        ArrayList<Object> column = new ArrayList<>();
        for (List<Object> list : tableList) {
            column.add(list.get(columnIndex));
        }
        return column;
    }

    public ArrayList getColumn(String columnName) throws SQLException {
        for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
            if (metaData.getColumnName(i).equals(columnName)) {
                return getColumn(i - 1);
            }
        }
        throw new SQLException("No such column named " + columnName);
    }

    public static void main(String[] args) {
        NewQuery q = new NewQuery("select * from security");
        try {
            System.out.println(q.getList());
            System.out.println(q.getList());
            System.out.println(q.getColumn("Username"));
        } catch (SQLException ex) {
            Logger.getLogger(NewQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
