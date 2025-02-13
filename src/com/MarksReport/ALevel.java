package com.MarksReport;

import Standard.array;
import static com.Database.DBconnect.CONN;
import com.Database.Sql;
import java.sql.SQLException;
import java.util.List;

public class ALevel {

    String id;

    public ALevel(String id) {
        this.id = id;
    }

    public static List<Object> getSubs(String streamName) {
        List<Object> list = Sql.getRow("SELECT * FROM ALevel_Streams WHERE FieldName ='" + streamName + "'", CONN);
        if (list == null) {
            return null;
        }
        list.remove(0);
        return list;
    }

    public static void setStream(String id, String stream) throws SQLException {
        Sql.execute("UPDATE AdvancedLevel SET Stream=[" + stream + "] WHERE ID='" + id + "'", CONN);
    }

    public static void deleteStream(String name) throws SQLException {
        Sql.execute("DELETE FROM ALevel_Streams WHERE FieldName='" + name + "'", CONN);
    }

    public static List<Object> getStreams() {
        return Sql.getColumn("SELECT FieldName FROM ALevel_Streams", 0, CONN);
    }

    public static String getStream(String id, int grade, String term) {
        Object s = Sql.getValue("SELECT Stream FROM \"" + Marks.getTable(grade) + "\" WHERE ID='" + id + "' AND Term='" + term + "'", CONN);
        if (s == null) {
            return null;
        }
        return s.toString();
    }

    public static void setStream(String[] id, int grade, String term,String stream) throws SQLException {
        String is = "";
        if (id.length > 0) {
            is = is.concat(" ID = '" + id[0] + "'");
        }
        if (id.length > 1) {
            is = is.concat(" OR ID='").concat(String.join(" OR ID='", array.concatEach(array.remove(id, 0), "'")));
        }
        Sql.execute("UPDATE [" + Marks.getTable(grade) + "] SET Stream='"+stream+"' WHERE " + is + " AND Term='" + term + "'", CONN);
    }

    public static String getStream(String id) {
        Object s = Sql.getValue("SELECT Stream FROM AdvancedLevel WHERE ID='" + id + "'", CONN);
        if (s == null) {
            return null;
        }
        return s.toString();
    }

    public static void addStream(String name, String s1, String s2, String s3) throws SQLException {
        Sql.execute("INSERT INTO ALevel_Streams VALUES([" + name + "],[" + s1 + "],[" + s2 + "],[" + s3 + "])", CONN);
    }

    public static void main(String[] args) {
    }
}
