package com.Printing;

import com.Database.DBconnect;
import com.Database.Database;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class Ireport {
    
    public static final String STUDENT_DETAILS=new File("src\\Print Details\\Student Details.jrxml").getAbsolutePath();
    public static final String STUDENT_DETAILS_XLSX=new File("src\\Print Details\\Student Details_xlsx.jrxml").getAbsolutePath();
    public static final String SINGLE_STUDENT_DETAILS=new File("src\\Print Details\\Single Student Details_Images.jrxml").getAbsolutePath();
    public static final String SINGLE_STUDENT_DETAILS_NOIMAGE=new File("src\\Print Details\\Single Student Details_NoImages.jrxml").getAbsolutePath();
    public static final String SINGLE_STUDENT_DETAILS_XLSX=new File("src\\Print Details\\Student Details_xlsx.jrxml").getAbsolutePath();

    private static JasperPrint getPrint(String name, HashMap<String, Object> map, Connection connectPrint) throws JRException {
        return JasperFillManager.fillReport(JasperCompileManager.compileReport(name), map, connectPrint);
    }

    private static JasperPrint getPrint(String name, Connection connectPrint) throws JRException {
        return JasperFillManager.fillReport(JasperCompileManager.compileReport(name), null, connectPrint);
    }

    public static void pdf(String name, Connection connectPrint, String out) throws JRException {
        JasperExportManager.exportReportToPdfFile(getPrint(name, connectPrint), out);
    }

    public static void html(String name, Connection connectPrint, String out) throws JRException {
        JasperExportManager.exportReportToHtmlFile(getPrint(name, connectPrint), out);
    }

    public static void xml(String name, Connection connectPrint, String out) throws JRException {
        JasperExportManager.exportReportToXmlFile(getPrint(name, connectPrint), out, true);
    }

    public static void xlsx(String jrxmlFilePath, Connection conn, String out) throws IOException, JRException {
        xlsx(jrxmlFilePath, null, conn, out);
    }

    public static void docx(String jrxmlFilePath, Connection conn, String out) throws IOException, JRException {
        docx(jrxmlFilePath, null, conn, out);
    }

    public static void pptx(String jrxmlFilePath, Connection conn, String out) throws IOException, JRException {
        pptx(jrxmlFilePath, null, conn, out);
    }

    public static void pdf(String name, HashMap<String, Object> map, Connection connectPrint, String out) throws JRException {
        JasperExportManager.exportReportToPdfFile(getPrint(name, map, connectPrint), out);
    }

    public static void html(String name, HashMap<String, Object> map, Connection connectPrint, String out) throws JRException {
        JasperExportManager.exportReportToHtmlFile(getPrint(name, map, connectPrint), out);
    }

    public static void xml(String name, HashMap<String, Object> map, Connection connectPrint, String out) throws JRException {
        JasperExportManager.exportReportToXmlFile(getPrint(name, map, connectPrint), out, true);
    }

    public static void xlsx(String jrxmlFilePath, HashMap<String, Object> map, Connection conn, String out) throws IOException, JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
        System.out.println(jasperPrint);
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        File outputFile = new File(out);
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        configuration.setIgnoreGraphics(true);
        configuration.setIgnoreCellBackground(Boolean.TRUE);
        configuration.setWhitePageBackground(Boolean.FALSE);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

    public static void docx(String jrxmlFilePath, HashMap<String, Object> map, Connection conn, String out) throws IOException, JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        File outputFile = new File(out);
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
        SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

    public static void rtf(String jrxmlFilePath, Connection conn, String out) throws IOException, JRException {
        rtf(jrxmlFilePath, null, conn, out);
    }

    public static void rtf(String jrxmlFilePath, HashMap<String, Object> map, Connection conn, String out) throws IOException, JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
        JRRtfExporter exporter = new JRRtfExporter();
        exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        exporter.exportReport();
    }

    public static void pptx(String jrxmlFilePath, HashMap<String, Object> map, Connection conn, String out) throws IOException, JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
        JRPptxExporter exporter = new JRPptxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        File outputFile = new File(out);
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
        exporter.exportReport();
    }
}

class ter {

    public static void main(String[] args) {
        Connection connectPrint = DBconnect.connect(Database.PRINTER);
        String name = new File(Marks.SINGLE_WITHOUTIMAGES).getAbsolutePath();
        try {
            Ireport.pdf(name, connectPrint, "sample.pdf");
        } catch (JRException ex) {
            Logger.getLogger(ter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
