package com.Printing;

import com.Codes.Commons;
import com.Database.DBconnect;
import com.Database.Database;
import com.Database.Sql;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;

public class Marks extends Print {
    boolean inclImage = false;
    public static final String SINGLE_WITHIMAGES = new File("src\\Print Details\\Marks Report_Images.jrxml").getAbsolutePath();
    public static final String SINGLE_WITHOUTIMAGES = new File("src\\Print Details\\Marks Report_NoImages.jrxml").getAbsolutePath();
    public static final String XLSX = new File("src\\Print Details\\Marks Report_xlsx.jrxml").getAbsolutePath();
    public static final String XLSX_1 = new File("src\\Print Details\\Marks Reports_Marks_xlsx_1.jasper").getAbsolutePath();
    public static final String XLSX_6 = new File("src\\Print Details\\Marks Reports_Marks_xlsx_6.jasper").getAbsolutePath();
    public static final String XLSX_10 = new File("src\\Print Details\\Marks Reports_Marks_xlsx_10.jasper").getAbsolutePath();
    public static final String XLSX_12 = new File("src\\Print Details\\Marks Reports_Marks_xlsx_12.jasper").getAbsolutePath();
    private final HashMap HMAP;

    public Marks(String jrxml, HashMap<String, Object> map, com.Printing.Extention extention) {
        super(jrxml, map, extention);
        this.HMAP = map;
    }

    public static String getXlsx(int grade) {
        String file = XLSX_1;
        if (grade < 6) {
            file = XLSX_1;
        } else if (grade < 10) {
            file = XLSX_6;
        } else if (grade < 12) {
            file = XLSX_10;
        } else if (grade < 14) {
            file = XLSX_12;
        }
        return file;
    }

    public void removeImages() {
        inclImage = false;
        if (!getExtention().equals(Extention.XLSX)) {
            setJrxml(SINGLE_WITHOUTIMAGES);
        }
    }

    public void haveImages() {
        inclImage = true;
        if (!getExtention().equals(Extention.XLSX)) {
            setJrxml(SINGLE_WITHIMAGES);
        }
    }

    public void setTitle(String[] title, Connection conn) throws Exception {
        deleteAllData("Title", conn);
        for (String ttl : title) {
            Sql.execute("INSERT INTO Title (Field) VALUES ('" + ttl + "')", conn);
        }
    }

    public void setDescription(String[][] title, Connection conn) throws Exception {
        deleteAllData("Description", conn);
        for (String[] ttl : title) {
            Sql.execute("INSERT INTO Title ('Field Name',Value) VALUES ('" + ttl[0] + "','" + ttl[1] + "')", conn);
        }
    }

    public void setSummery(String[][] summery, Connection conn) throws Exception {
        deleteAllData("Description", conn);
        for (String[] ttl : summery) {
            Sql.execute("INSERT INTO Title ('Field Name',Value) VALUES ('" + ttl[0] + "','" + ttl[1] + "')", conn);
        }
    }

}

class te {

    public static void main(String[] args) throws Exception {
        HashMap<String, Object> map = new HashMap();
        map.put("MARKS_DIR", Marks.getXlsx(1));
        System.out.println(new File("").getAbsolutePath());
        map.put("APPLICATION_DIR", new File("").getAbsolutePath());
        Marks m = new Marks(Marks.XLSX, null, Extention.XLSX);
        m.setTitle(new String[]{"asdhsb"}, DBconnect.connect(Database.PRINTER));
        m.setOutputFile("Out");
        try {
            m.Print();
            if (JOptionPane.showConfirmDialog(null, m.getOutput()) == JOptionPane.OK_OPTION) {
                System.out.println(new File(m.getOutput()).getAbsolutePath());
                Commons.openFile(new File(m.getOutput()).getAbsolutePath());
            }
        } catch (JRException | IOException ex) {
            Logger.getLogger(te.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
