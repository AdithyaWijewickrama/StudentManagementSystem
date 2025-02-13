/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.ui.internalFrames.Health_frame;
import com.Codes.Commons;
import static com.Database.Sql.ExecuteSQL;
import com.Main.Defaults;
import static com.ui.internalFrames.Manage_frame.CONN;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Dell
 */
public class CommonUi {

    public static final int HORIZONTALFIT = 0;
    public static final int VERTICALFIT = -1;

    public static void setLimit(JSpinner c, int min, int max) {
        c.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (((int) c.getValue()) < min) {
                    c.setValue(min);
                }
                if (((int) c.getValue()) > max) {
                    c.setValue(max);
                }
            }
        });
    }

    public static Image getImageForChars(char[] c, Color cl) {
        BufferedImage i = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = i.createGraphics();
        g.setColor(cl);
        g.drawChars(c, 0, c.length, 0, 0);
        return i;
    }

    public static String getDate(JDateChooser d) {
        return ((JTextField) d.getDateEditor().getUiComponent()).getText();
    }

    public static void clearAll(Container cont) throws Exception {
        for (Component c : cont.getComponents()) {
            if (c instanceof JTextComponent) {
                ((JTextComponent) c).setText("");
            } else if (c instanceof JComboBox) {
                ((JComboBox) c).setSelectedIndex(0);
            } else if (c instanceof JTable) {
                ((JTable) c).addRowSelectionInterval(-1, -1);
            } else if (c instanceof Container) {
                clearAll((Container) c);
            }
        }
    }

    public static ImageIcon getVertFitImage(ImageIcon img, int height) {
        if (img == null) {
            return null;
        }
        int width = Double.valueOf((double) img.getIconWidth() / img.getIconHeight() * height).intValue();
        return Commons.getImage(img, width, height);
    }

    public static ImageIcon getHorzFitImage(ImageIcon img, int width) {
        if (img == null) {
            return null;
        }
        int height = Double.valueOf((double) img.getIconHeight() / img.getIconWidth() * width).intValue();
        return Commons.getImage(img, width, height);
    }

    public static ImageIcon getVertFitImage(Image img, int height) {
        if (img == null) {
            return null;
        }
        int width = Double.valueOf((double) img.getWidth(null) / img.getHeight(null) * height).intValue();
        return Commons.getImage(new ImageIcon(img), width, height);
    }

    public static ImageIcon getHorzFitImage(Image img, int width) {
        if (img == null) {
            return null;
        }
        int height = Double.valueOf((double) img.getHeight(null) / img.getWidth(null) * width).intValue();
        return Commons.getImage(new ImageIcon(img), width, height);
    }

    public static Color getToolBarColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(0, 16, 37) : new Color(255, 255, 255);
    }

    public static Color getTransparentColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(0, 0, 0, 100) : new Color(255, 255, 255, 100);
    }

    public static Color getNavColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(0, 0, 0) : new Color(151, 151, 151);
    }

    public static void genarateCenter(JDialog dialog, int width, int height) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (size.height - height) / 2;
        int x = (size.width - width) / 2;
        dialog.setSize(width, height);
        dialog.setPreferredSize(new Dimension(width, height));
        dialog.setBounds(x, y, width, height);
        dialog.setLocation(x, y);
        dialog.validate();
        dialog.repaint();
    }

    public static void genarateCenter(JDialog dialog) {
        int width = dialog.getWidth();
        int height = dialog.getHeight();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (size.height - height) / 2;
        int x = (size.width - width) / 2;
        dialog.setSize(width, height);
        dialog.setPreferredSize(new Dimension(width, height));
        dialog.setBounds(x, y, width, height);
        dialog.setLocation(x, y);
        dialog.validate();
        dialog.repaint();
    }

    public static int getXOnParent(JComponent c) {
        int x = c.getX();
        if (c.getParent() != null) {
            c = (JComponent) c.getParent();
            x += c.getX();
        }
        return x;
    }

    public static int getYOnParent(JComponent c) {
        int y = c.getY();
        if (c.getParent() != null) {
            c = (JComponent) c.getParent();
            y += c.getY();
        }
        return y;
    }

    public static String verticalText(String s) {
        String r = "<html>";
        for (char c : s.toCharArray()) {
            r = r.concat(c + "<br>");
        }
        return r.concat("</html>");
    }

    public static void setOkButton(JButton b) {
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(0, 100, 205));
        b.repaint();
    }

    public static void checkBoxSet(JToggleButton... boxes) {
        for (JToggleButton box : boxes) {
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    box.setSelected(true);
                    for (JToggleButton box1 : boxes) {
                        if (box1.isSelected()) {
                            box.doClick(0);
                        }
                    }
                }
            });
        }
    }

    public static void checkBoxSet(JRadioButton... boxes) {
        for (JRadioButton box : boxes) {
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    box.setSelected(true);
                    for (JRadioButton box1 : boxes) {
                        if (box1.equals(box)) {
                            continue;
                        }
                        if (box1.isSelected()) {
                            box.doClick();
                        }
                    }
                }
            });
        }
    }

    /**
     * This method divides the button text into lines by applying html tags.
     * Only way to get multiple lines on a JButton.
     *
     * @param string
     * @param align
     * @param btn
     * @return
     */
    public static String wrapText(String string, String align, JButton btn) {
        //Return string initialized with opening html tag
        String returnString = "<html> <p align=\"" + align + "\">";
        int maxLineWidth = new ImageIcon("Images/buttonBackground.png").getIconWidth() - 10;

        //Create font metrics
        FontMetrics metrics = btn.getFontMetrics(new Font("Dialog", Font.PLAIN, 15));

        //Current line width
        int lineWidth = 0;

        //Iterate over string
        StringTokenizer tokenizer = new StringTokenizer(string, " ");
        while (tokenizer.hasMoreElements()) {
            String word = (String) tokenizer.nextElement();
            int stringWidth = metrics.stringWidth(word);

            //If word will cause a spill over max line width
            if (stringWidth + lineWidth >= maxLineWidth) {

                //Add a new line, add a break tag and add the new word
                returnString = (returnString + "<br>" + word);

                //Reset line width
                lineWidth = 0;
            } else {

                //No spill, so just add to current string
                returnString = (returnString + " " + word + "");
            }
            //Increase the width of the line
            lineWidth += stringWidth;
        }

        //Close html tag
        returnString = (returnString + "</p></html>");

        //Return the string
        return returnString;
    }

    public static String wrapText(String string, int maxLineWidth, String align, JLabel label) {
        //Return string initialized with opening html tag
        String returnString = "<html> <p align=\"" + align + "\">";
        //Create font metrics
        FontMetrics metrics = label.getFontMetrics(new Font("Dialog", Font.PLAIN, 15));

        //Current line width
        int lineWidth = 0;

        //Iterate over string
        StringTokenizer tokenizer = new StringTokenizer(string, " ");
        while (tokenizer.hasMoreElements()) {
            String word = (String) tokenizer.nextElement();
            int stringWidth = metrics.stringWidth(word);

            //If word will cause a spill over max line width
            if (stringWidth + lineWidth >= maxLineWidth) {

                //Add a new line, add a break tag and add the new word
                returnString = (returnString + "<br>" + word);

                //Reset line width
                lineWidth = 0;
            } else {

                //No spill, so just add to current string
                returnString = (returnString + " " + word + "");
            }
            //Increase the width of the line
            lineWidth += stringWidth;
        }

        //Close html tag
        returnString = (returnString + "</p></html>");

        //Return the string
        return returnString;
    }

    public static String[] getArray(JTextArea a) throws BadLocationException {
        if (a.getLineCount() == 0) {
            return new String[]{};
        }
        String[] text = new String[a.getLineCount()];
        text[0] = a.getText(0, a.getLineEndOffset(0));
        for (int i = 1; i < text.length; i++) {
            text[i] = a.getText(a.getLineEndOffset(i - 1), a.getLineEndOffset(i) - a.getLineEndOffset(i - 1) - (text.length - 1 == i ? 0 : 1));
        }
        return text;
    }

    public static String[] getArray(int column, JTable table) {
        String[] data = new String[table.getRowCount()];
        for (int i = 0; i < data.length; i++) {
            try {
                data[i] = table.getValueAt(i, column).toString();
            } catch (Exception e) {
                data[i] = "";
            }
        }
        return data;
    }

    public static String[][] getArray(JTable table) {
        String[][] data = new String[table.getRowCount()][table.getColumnCount()];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                try {
                    data[i][j] = table.getValueAt(i, j) + "";
                } catch (Exception e) {
                    data[i][j] = "";
                }
            }
        }
        return data;
    }

    public static String[] getSelectedArray(int column, JTable table) {
        String[] data = new String[table.getSelectedRowCount()];
        for (int i = 0; i < data.length; i++) {
            try {
                data[i] = table.getValueAt(table.getSelectedRows()[i], column).toString();
            } catch (Exception e) {
                data[i] = "";
            }
        }
        return data;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton btn = new JButton(verticalText("Adithya"));
        JEditorPane tf = new JEditorPane("text/html", verticalText("Adithya"));
        btn.setBounds(0, 0, 100, 100);
        tf.setBounds(0, 0, 100, 100);
//        frame.add(btn);
        frame.add(tf);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1100, 600);
        frame.setVisible(true);
        frame.validate();
    }

    public static void genarateCenter(JInternalFrame frame, Container parent, int w, int h) {
        frame.setBounds((parent.getWidth() - w) / 2, (parent.getHeight() - h) / 2, w, h);
    }

    public static String gsi(JComboBox box) {
        return box.getSelectedItem().toString();
    }

    public static boolean isEqual(ImageIcon i1, ImageIcon i2) {
        BufferedImage bi = new BufferedImage(VERTICALFIT, VERTICALFIT, VERTICALFIT);
        int[] rgbArray = new int[100 * 100];
        bi.getRGB(0, 0, 100, 100, rgbArray, VERTICALFIT, VERTICALFIT);
        return false;
    }

    public static void setImage(ImageIcon img, JLabel l) {
        l.setIcon((l.getWidth() > l.getHeight() ? CommonUi.getVertFitImage(img, l.getHeight()) : CommonUi.getHorzFitImage(img, l.getWidth())));
    }

    public static void setImage(String img, JLabel label) {
        setImage(new ImageIcon(img), label);
    }

    public static void setText(JComboBox c, String t) {
        if (c.isEditable()) {
            ((JTextField) c.getEditor().getEditorComponent()).setText(t);
        }
    }

    public static void setText(JDateChooser c, String t) {
        ((JTextField) c.getDateEditor().getUiComponent()).setText(t);
    }

    public static String getText(JComboBox c) {
        return ((JTextField) c.getEditor().getEditorComponent()).getText();
    }

    public static String getText(JDateChooser c) {
        return ((JTextField) c.getDateEditor().getUiComponent()).getText();
    }

    public static JFileChooser chooseImage(String title, String btext, int mode) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle(title);
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.setFileSelectionMode(mode);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("All Image Types", "png", "jpg", "jpeg");
        jfc.setFileFilter(filter);
        jfc.setApproveButtonText(btext);
        return jfc;
    }

    public static JFileChooser chooseImage() {
        JFileChooser jfc = new JFileChooser(Commons.getDefault(Defaults.MD_ImagePath, CONN));
        jfc.setDialogTitle("Choose an image");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("All Image Types", "png", "jpg", "jpeg");
        jfc.setFileFilter(filter);
        jfc.setApproveButtonText("Choose Image");
        return jfc;
    }

    public static void setToOnlyDigits(JTextField txt) {
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_DELETE && !Character.isDigit(key)) {
                    e.consume();
                }
            }
        });
    }

    public static void setToOnlyDigitsWITHb(JTextField txt, boolean b) {
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (b) {
                    e.consume();
                }
            }
        });
    }

    public static void setToOnlyDigitsORb(JTextField txt, boolean b) {
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (key != KeyEvent.VK_BACK_SPACE || key != KeyEvent.VK_DELETE || !Character.isDigit(key) || b) {
                    e.consume();
                }
            }
        });
    }

    public static void setHoverColor(JComponent comp, Color EntColor, Color ExtColor) {
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                comp.setBackground(EntColor);
                comp.setForeground(EntColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                comp.setBackground(ExtColor);
            }
        });
    }

    public static void tableload(String sql, JTable table, Connection conn) {
        try {
            table.setModel(DbUtils.resultSetToTableModel(ExecuteSQL(sql, conn)));
        } catch (SQLException ex) {
            System.err.println(sql);
            Logger.getLogger(Commons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setToConsume(JTextField txt, char c) {
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (key == c) {
                    e.consume();
                }
            }
        });
    }

    public static boolean tableIsSelected(JTable table) {
        if (table.getSelectedRow() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void enableAll(JComponent parent, boolean enable) {
        for (Component child : parent.getComponents()) {
            child.setEnabled(enable);
        }
    }
}
