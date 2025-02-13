package com.Codes;

import com.Database.Sql;
import com.Main.Defaults;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static com.Database.Sql.execute;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Commons {

    public static Date date = Calendar.getInstance().getTime();
    public static final String EMPTYSTRING = "";
    public static final String APPNAME = "Student Management System";

    public static ByteArrayOutputStream getBytes(File f) throws FileNotFoundException, IOException {
        if (f != null) {
            FileInputStream in = new FileInputStream(f);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int r; (r = in.read(buf)) != -1;) {
                bout.write(buf, 0, r);
            }
            return bout;
        } else {
            return null;
        }
    }

    public static void copyToClipboard(String text) {
        java.awt.datatransfer.Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection sel = new StringSelection(text);
        clip.setContents(sel, sel);
    }

    public static int showConfMsg(String e) {
        Frame f = new Frame();
        f.setIconImage(AppConfig.APPICON.getImage());
        return JOptionPane.showConfirmDialog(f, e, "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrMsg(String e) {
        Frame f = new Frame();
        f.setIconImage(AppConfig.APPICON.getImage());
        JOptionPane.showMessageDialog(f, e, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showMsg(Exception e) {
        showErrMsg(e.getLocalizedMessage());
    }

    public static void showMsg(String e) {
        Frame f = new Frame();
        f.setIconImage(AppConfig.APPICON.getImage());
        JOptionPane.showMessageDialog(f, e, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String getDefault(String id, Connection conn) {
        return (String) Sql.getValue("SELECT Value FROM Defaults WHERE ID='" + id + "'", conn);
    }

    public static void setDefault(String ID, String val, Connection conn) {
        try {
            execute("INSERT OR REPLACE INTO Defaults (ID,Value) VALUES (\"" + ID + "\",\"" + val + "\")", conn);
        } catch (Exception ex) {
            Logger.getLogger(Commons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getDefault(Defaults id, Connection conn) {
        return (String) Sql.getValue("SELECT Value FROM Defaults WHERE ID='" + id + "'", conn);
    }

    public static void setDefault(Defaults ID, String val, Connection conn) {
        try {
            execute("INSERT OR REPLACE INTO Defaults (ID,Value) VALUES (\"" + ID + "\",\"" + val + "\")", conn);
        } catch (Exception ex) {
            Logger.getLogger(Commons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void copyFile(String currentPath, String toPath) {
        try {
            FileInputStream in = new FileInputStream(currentPath);
            FileOutputStream out = new FileOutputStream(toPath);
            BufferedOutputStream bout;
            try (BufferedInputStream bin = new BufferedInputStream(in)) {
                bout = new BufferedOutputStream(out);
                int bi = 0;
                while (bi != -1) {
                    bi = bin.read();
                    bout.write(bi);
                }
            }
            bout.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static String join(String del, List<Object> li) {
        String r = "";
        for (int i = 0; i < li.size(); i++) {
            r = r.concat(li.get(i) + (i == li.size() - 1 ? "" : del));
        }
        return r;
    }

    public static void openFile(String Path_Name) throws IOException {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + Path_Name);
    }

    public static ImageIcon getImage(String ImagePath, int Width, int Height) {
        return getImage(new ImageIcon(ImagePath), Width, Height);
    }

    public static ImageIcon getImage(ImageIcon image, int Width, int Height) {
        if (image == null) {
            return null;
        }
        if (Width == 0) {
            Width = image.getIconWidth();
        }
        if (Height == 0) {
            Height = image.getIconHeight();
        }
        BufferedImage buff = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buff.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_BASE);
        g2d.drawImage(image.getImage(), 0, 0, Width, Height, null);
        g2d.dispose();
        return new ImageIcon(buff);
    }

    public static String showDate(Date d) {
        return String.format("%04d-%02d-%02d", d.getYear() + 1900, d.getMonth() + 1, d.getDate());
    }

    public static String showDate(int y, int m, int d) {
        return String.format("%04d-%02d-%02d", y, m, d);
    }

    public static String ShowTime(int h, int min, int s) {
        return String.format("%02d:%02d:%02d", h, min, s);
    }

    public static String getCurrentDateString() {
        return showDate(date);
    }

    public static Date getCurrentDate() {
        return date;
    }

    public static String getCurrentTime() {
        return String.format("%02d:%02d:%02d", date.getHours(), date.getMinutes(), date.getSeconds());
    }

    public static String getApplicationDir() {
        return new File("").getAbsolutePath();
    }

    public static void main(String[] args) {
        showMsg("SAA");
        showErrMsg("SAA");
        showConfMsg("SAA");
    }
}
