package com.Printing;

import com.Database.DBconnect;
import com.Database.Database;
import com.Database.Sql;
import static com.Printing.Extention.PDF;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;

public class Print {

    private String Jrxml = null;
    private String Output = null;
    
    private Extention Extention = PDF;
    public static Connection CONN = DBconnect.connect(Database.PRINTER);
    HashMap map;

    public void setJrxml(String aJrxml) {
        Jrxml = aJrxml;
    }

    public Print(String jrxml, HashMap map, Extention extention) {
        Jrxml = jrxml;
        Extention = extention;
        this.map = map;
    }

    public void setOutputFile(String aOutput) {
        Output = aOutput;
    }

    public void setExtention(Extention aExtention) {
        Extention = aExtention;
    }

    public String getJrxml() {
        return Jrxml;
    }

    public String getOutput() {
        return Output.concat("." + Extention.getEXT());
    }
    
    public String getAbsoluteOutput() {
        return new File(Output.concat("." + Extention.getEXT())).getAbsolutePath();
    }

    public Extention getExtention() {
        return Extention;
    }

    public void Print() throws JRException, IOException {
        String ExtentionName = this.Extention.getEXT();
        System.out.println(Output.concat("." + ExtentionName));
        switch (this.Extention) {
            case PDF:
                Ireport.pdf(Jrxml, map, CONN, Output.concat("." + ExtentionName));
                break;
            case DOCX:
                Ireport.docx(Jrxml, map, CONN, Output.concat("." + ExtentionName));
                break;
            case HTML:
                Ireport.html(Jrxml, map, CONN, Output.concat("." + ExtentionName));
                break;
            case XLSX:
                Ireport.xlsx(Jrxml, map, CONN, Output.concat("." + ExtentionName));
                break;
            case PPTX:
                Ireport.pptx(Jrxml, map, CONN, Output.concat("." + ExtentionName));
                break;
            case RTF:
                Ireport.rtf(Jrxml, map, CONN, Output.concat("." + ExtentionName));
                break;
            case XML:
                Ireport.xml(Jrxml, map, CONN, Output.concat("." + ExtentionName));
                break;
            default:
                break;
        }
    }
    
    public static void deleteAllData(String tableName, Connection conn) throws Exception {
        Sql.execute("DELETE FROM \"" + tableName + "\"", conn);
    }

}