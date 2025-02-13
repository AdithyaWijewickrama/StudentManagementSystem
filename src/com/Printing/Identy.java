package com.Printing;

import com.Codes.Commons;
import com.ui.frames.UserFrame;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import static com.Database.Sql.execute;

public class Identy extends Print {

    public static final String IDENTY_CARD = new File("src\\Print Details\\Identy Card.jrxml").getAbsolutePath();

    public Identy(String jrxml, HashMap map, com.Printing.Extention extention) {
        super(jrxml, map, extention);
        this.QRCode = map.get("QR_CODE").toString();
        this.applicationDir = map.get("APPLICATION_DIR").toString();
    }

    public void setDetails(String[][] data, Connection conn) throws Exception {
        deleteAllData("\"Identy Card\"", conn);
        for (String[] data1 : data) {
            execute("INSERT INTO \"Identy Card\" VALUES ('" + data1[0] + "','" + data1[1] + "','" + data1[2] + "')", conn);
        }
    }

    private String QRCode;
    private String applicationDir;
    private String image;

    public String getBarCode() {
        return QRCode;
    }

    public void setBarCode(String barCode) {
        this.QRCode = barCode;
    }

    public String getApplicationDir() {
        return applicationDir;
    }

    public void setApplicationDir(String applicationDir) {
        this.applicationDir = applicationDir;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

class re {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("QR_CODE", new File("Bar Codes of Students\\1.png").getAbsolutePath());
        map.put("APPLICATION_DIR", Commons.getApplicationDir());
        Identy i = new Identy(Identy.IDENTY_CARD, map, Extention.PDF);
        i.setOutputFile("out");
        try {
            i.Print();
            Commons.openFile(new File("out.pdf").getAbsolutePath());
        } catch (JRException | IOException ex) {
            Logger.getLogger(re.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
