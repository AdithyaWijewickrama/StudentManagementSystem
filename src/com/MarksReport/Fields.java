/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MarksReport;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Dell
 */
public class Fields {

    public HashMap<Container, ArrayList<JTextField>> fields;
    private ArrayList<JTextField> aLevel = null;
    private ArrayList<JTextField> oLevel = null;
    private ArrayList<JTextField> primary = null;
    private ArrayList<JTextField> junior = null;

    public Fields(int g, Container con) {
        this(g < 6 && g > 0 ? con : null, g < 10 && g > 5 ? con : null, g < 12 && g > 9 ? con : null, g < 14 && g > 11 ? con : null);
    }

    public Fields(Container con1, Container con6, Container con10, Container con12) {
        fields = new HashMap<>();
        if (con12 != null) {
            aLevel = getTextFields(con12, Subjects.ADVANCEDLEVEL.length);
            fields.put(con12, aLevel);
        }
        if (con10 != null) {
            oLevel = getTextFields(con10, Subjects.ODINARYLEVEL.length);
            fields.put(con10, oLevel);
        }
        if (con1 != null) {
            primary = getTextFields(con1, Subjects.PRIMARY.length);
            fields.put(con1, primary);
        }
        if (con6 != null) {
            junior = getTextFields(con6, Subjects.JUNIOR.length);
            fields.put(con6, junior);
        }
    }

    public static final ArrayList<JTextField> getTextFields(Container panel, int width) {
        ArrayList<JTextField> f = new ArrayList<>();
        if (panel instanceof JInternalFrame) {
            panel = ((JInternalFrame) panel).getContentPane();
        }
        int i = 0;
        for (Component c : panel.getComponents()) {
            if (i == width) {
                break;
            }
            if (c instanceof JTextField) {
                f.add((JTextField) c);
            }
            i++;
        }
        return f;
    }

    public static String[] getTexts(ArrayList<JTextField> a){
        String [] s=new String [a.size()];
        for (int i = 0; i < a.size(); i++) {
            s[i]=a.get(i).getText();
        }
        return s;
    }
    
    public static final ArrayList<JLabel> getLabels(Container panel, int width) {
        System.out.print("New field " + width);
        ArrayList<JLabel> f = new ArrayList<>();
        if (panel instanceof JInternalFrame) {
            panel = ((JInternalFrame) panel).getContentPane();
        }
        int i = 0;
        for (Component c : panel.getComponents()) {
            if (i == width) {
                break;
            }
            if (c instanceof javax.swing.JLabel) {
                f.add((JLabel) c);
                i++;
            }
        }
        System.out.print("\n");
        return f;
    }

}
