package com.MarksReport;

import static com.Database.DBconnect.CONN;
import com.Database.Sql;
import static com.ui.frames.UserFrame.ID_NO;
import static com.MarksReport.ALevel.getStream;
import static com.MarksReport.Subjects.PRIMARY;
import static com.MarksReport.Subjects.ODINARYLEVEL;
import static com.MarksReport.Subjects.ADVANCEDLEVEL;
import static com.MarksReport.Subjects.JUNIOR;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Marks {

    public static final String MAINGRADE = "MainGrade";
    public static final String SUBGRADE = "SubGrade";
    public static final String[] EXAMS = {"exam_05", "AL", "OL"};
    public static final String[] TERMS = {"First Term", "Second Term", "Third Term", "Unit Test 01", "Unit Test 02", "Unit Test 03", "Unit Test 04", "Unit Test 05", "Unit Test 06", "Unit Test 07", "Unit Test 08", "Unit Test 09"};

    public static String getps(Double d) {
        return (d % 1 == 0.0 ? d.intValue() + "" : d) + "";
    }
    private String id;
    private String term;
    private int grade;
    private ArrayList<Double> marks = new ArrayList<>();
    private List<Object> data = null;

    public Marks(String id, String term, int grade) {
        this.id = id;
        this.term = term;
        this.grade = grade;
        selectMarks();
    }

    public void selectMarks() {
        switch (term) {
            case "OL":
                data = Sql.getRow("SELECT * FROM ordinary_level WHERE ID='" + id + "'", CONN);
                if (data != null) {
                    data.remove(0);
                    data.remove(data.size() - 1);
                }
                break;
            case "AL":
                data = Sql.getRow("SELECT * FROM advanced_level WHERE ID='" + id + "'", CONN);
                if (data != null) {
                    data.remove(0);
                    data.remove(data.size() - 1);
                }
                break;
            case "exam_05":
                data = Sql.getRow("SELECT marks FROM exam_05 WHERE ID='" + id + "'", CONN);
                break;
            default:
                data = Sql.getRow("SELECT * FROM " + getTable(grade) + " WHERE ID='" + id + "' AND Term='" + term + "'", CONN);
                if (data != null) {
                    for (Object m : data) {
                        if (!(m instanceof Number) || m instanceof String || m.equals(data.get(data.size() - 1))) {
                            continue;
                        }
                        marks.add(new Double(m + ""));
                    }
                }
                break;
        }
    }

    public Double getTotal() {
        Double tot = 0.;
        for (Double mark : marks) {
            tot += mark;
        }
        return tot;
    }

    public Double getAverage() {
        return getTotal() / marks.size();
    }

    public static String getAlStream(String id, int grade) {
        return Sql.getValueAsString("SELECT Stream FROM advanced_level WHERE ID='" + id + "'", CONN);
    }

    public boolean hasData() {
        selectMarks();
        return data != null;
    }

    public static String getTable(int grade) {
        return String.format("grade%02d", grade);
    }

    /**
     * School term/monthly tests
     *
     * @param m
     *
     */
    public void setMarks(ArrayList<Double> m) {
        this.marks = m;
    }

    /**
     * Odinary level Advanced level exam_05
     *
     * @param m
     *
     */
    public void setMarks(List<Object> m) {
        this.data = m;
    }

    public void updateMarks() throws Exception {
        if (term.equals("OL") || term.equals("Al") || term.equals("Exams_05")) {
            updateExam();
        } else {
            Marks.updateMarks(id, term, grade, marks);
        }
    }

    public void updateExam() throws SQLException {
        String table = term.equals("OL") ? "ordinary_level" : term.equals("AL") ? "advanced_level" : term.equals("exam_05") ? "exam_05" : null;
        if (table == null) {
            return;
        }
        String[] sm = new String[data.size()];
        try {
            Sql.execute("DELETE FROM \"" + table + "\" WHERE ID='" + id + "'", CONN);
        } catch (SQLException ex) {
            Logger.getLogger(Marks.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "INSERT INTO \"" + table + "\" VALUES ('" + id + "','";
        int i = 0;
        for (Object o : data) {
            sm[i] = o + "";
            i++;
        }
        sql = sql.concat(String.join("','", sm)).concat("'");
        if (term.equals("OL")) {
            int sum = 0;
            for (Object o : data) {
                sum += (o.equals('A') ? 75
                        : o.equals('B') ? 65
                        : o.equals('C') ? 45
                        : o.equals('S') ? 35
                        : 0);
            }
            sql = sql.concat(",'" + sum + "')");
        } else if (term.equals("exam_05")) {
            sql = sql.concat(")");
        }
        Sql.execute(sql, CONN);
    }

    public void updateId(String newId) throws Exception {
        Marks.updateId(grade, id, newId);
    }

    public ArrayList<Double> getMarks() {
        return marks;
    }

    public static List<List<Double>> getMarks(String[] id, String term, int grade) {
        List<List<Double>> marks = new ArrayList<>();
        for (int i = 0; i < marks.size(); i++) {
            marks.set(i, new Marks(id[i], term, grade).getMarks());
        }
        return marks;
    }

    public static ArrayList<Double> getMarks(String id, String term, int grade) {
        return new Marks(id, term, grade).getMarks();
    }

    public List<Object> getPossitions() {
        return getPossitions(id, term, grade);
    }

    public static List<Object> getSumMarks(String term, int grade) {
        return Sql.getColumn("SELECT m.total FROM " + getTable(grade) + " m INNER JOIN student s WHERE m.ID=s.\"" + ID_NO + "\""
                + " AND m.Term='" + term + "' AND s.Grade='" + grade + "' ORDER BY m.total DESC", 0, CONN);
    }

    public static List<Object> getSumIds(String term, int grade) {
        return Sql.getColumn("SELECT m.ID FROM " + getTable(grade) + " m INNER JOIN student s WHERE m.ID=s.\"" + ID_NO + "\""
                + " AND m.Term='" + term + "' AND s.Grade='" + grade + "' ORDER BY m.total DESC", 0, CONN);
    }

    public static List<Object> getSumMarks(String term, int grade, String sgrade) {
        return Sql.getColumn("SELECT m.total FROM " + getTable(grade) + " m INNER JOIN student s WHERE m.ID=s.\"" + ID_NO + "\""
                + " AND m.Term='" + term + "' AND s.Grade='" + grade + "' AND s.\"Sub grade\"='" + sgrade + "' ORDER BY m.total DESC", 0, CONN);
    }

    public static List<Object> getSumIds(String term, int grade, String sgrade) {
        return Sql.getColumn("SELECT m.ID FROM " + getTable(grade) + " m INNER JOIN student s WHERE m.ID=s.\"" + ID_NO + "\""
                + " AND m.Term='" + term + "' AND s.Grade='" + grade + "' AND s.\"Sub grade\"='" + sgrade + "' ORDER BY m.total DESC", 0, CONN);
    }

    public static List<Object> getPossitions(String id, String term, int grade) {
        List<Object> totals = getSumMarks(term, grade);
        List<Object> ids = getSumIds(term, grade);
        List<Object> posses = new ArrayList<>();
        int p = ids.indexOf(id);
        if (p == -1) {
            return null;
        }
        posses.add(p+1);
        double tot = new Double(totals.get(p) + "");
        for (int i = 0; i < totals.size(); i++) {
            if (new Double(totals.get(i) + "") == tot && !ids.get(p).equals(ids.get(i))) {
                posses.add(ids.get(i));
            }
        }
        return posses;
    }

    public static List<Object> getPossitions(String id, String term, int grade, String sgrade) {
        List<Object> totals = getSumMarks(term, grade, sgrade);
        List<Object> ids = getSumIds(term, grade, sgrade);
        List<Object> posses = new ArrayList<>();
        int p = ids.indexOf(id);
        if (p == -1) {
            return null;
        }
        posses.add(p+1);
        double tot = Double.parseDouble(totals.get(p) + "");
        for (int i = 0; i < totals.size(); i++) {
            if (Double.parseDouble(totals.get(i) + "") == tot && !ids.get(p).equals(ids.get(i))) {
                posses.add(ids.get(i));
            }
        }
        return posses;
    }

    public static String[] getStudentId(int possition, int grade, String term) {
        possition--;
        List<Object> ids = getSumIds(term, grade);
        List<Object> possitions = getPossitions((String) ids.get(possition), term, grade);
        if (possitions.size() == 1) {
            return new String[]{(String) ids.get(possition)};
        } else if (possitions.size() > 1) {
            String[] ps = new String[possitions.size()];
            for (int i = 1; i < ps.length; i++) {
                ps[i] = possitions.get(i).toString();
            }
            return ps;
        } else {
            return null;
        }
    }

    public static String[] getStudentId(int possition, int grade, String sgrade, String term) {
        possition--;
        List<Object> ids = getSumIds(term, grade, sgrade);
        List<Object> possitions = getPossitions((String) ids.get(possition), term, grade, sgrade);
        if (possitions.size() == 1) {
            return new String[]{(String) ids.get(possition)};
        } else if (possitions.size() > 1) {
            String[] ps = new String[possitions.size()];
            for (int i = 1; i < ps.length; i++) {
                ps[i] = possitions.get(i).toString();
            }
            return ps;
        } else {
            return null;
        }
    }

    public static void updateMarks(String id, String term, int grade, List<Double> marks) throws Exception {
        String sql = "INSERT INTO \"" + getTable(grade) + "\" VALUES ('" + id + "','" + term + "','";
        String[] m = new String[marks.size() + (grade == 13 || grade == 12?2:1)];
        int i = 0;
        for (Double r : marks) {
            if (grade == 13 || grade == 12&&i==4) {
                m[4] = getStream(id, grade, term);
            } else {
                m[i] = ((r % 1 == 0.0 ? r.intValue() + "" : r) + "");
            }
            i++;
        }
        Double tot = 0.;
        for (Double mark : marks) {
            tot += mark;
        }
        m[m.length - 1] = (tot % 1 == 0 ? tot.intValue() : tot) + "";
        sql = sql.concat(String.join("','", m));
        sql = sql.concat("')");
        System.out.println(sql);
        Sql.execute("DELETE FROM \"" + getTable(grade) + "\" WHERE ID='" + id + "' AND term='" + term + "'", CONN);
        Sql.execute(sql, CONN);
    }

    public static void updateId(int grade, String oldId, String newId) throws Exception {
        Sql.execute("UPDATE \"" + getTable(grade) + "\" SET ID='" + newId + "' WHERE ID='" + oldId + "'", CONN);
    }

    public static char[] getGrades(ArrayList<Double> Marks) {
        char Grades[] = new char[Marks.size()];
        for (int Index = 0; Index < Marks.size(); Index++) {
            double Mark = Marks.get(Index);
            if (Mark >= 75) {
                Grades[Index] = 'A';
            } else if (Mark >= 65) {
                Grades[Index] = 'B';
            } else if (Mark >= 55) {
                Grades[Index] = 'C';
            } else if (Mark >= 35) {
                Grades[Index] = 'S';
            } else if (Mark > 0) {
                Grades[Index] = 'F';
            } else {
                Grades[Index] = ' ';
            }
        }
        return Grades;
    }

    public static String[] getSubjects(int grade) {
        String[] Subjects = null;
        if (grade < 6) {
            Subjects = PRIMARY;
        } else if (grade < 10) {
            Subjects = JUNIOR;
        } else if (grade < 12) {
            Subjects = ODINARYLEVEL;
        } else if (grade < 14) {
            Subjects = ADVANCEDLEVEL;
        }
        return Subjects;
    }

    public static List<Object> getWrittenExams(String id, int grade) {
        return Sql.getColumn("SELECT Term FROM " + Sql.getCoveredTable(getTable(grade)) + " WHERE ID=" + Sql.getCovered(id), 0, CONN);
    }

    public static void main(String[] args) throws SQLException {
        Marks m = new Marks("1694", "OL", 12);
        ArrayList<Object> m1 = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            m1.add('A');
        }
        m.setMarks(m1);
        m.updateExam();
    }

}
