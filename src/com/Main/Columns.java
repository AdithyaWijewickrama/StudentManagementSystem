/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Main;

import com.Communication.TrayMessage;
import java.awt.AWTException;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;

public class Columns {

    public JCheckBoxMenuItem guardian;
    public JCheckBoxMenuItem name;
    public JCheckBoxMenuItem medium;
    public JCheckBoxMenuItem grade;
    public JCheckBoxMenuItem subGrade;
    public JCheckBoxMenuItem All;
    public JCheckBoxMenuItem id;
    public JCheckBoxMenuItem birth;
    public JCheckBoxMenuItem gender;
    public JCheckBoxMenuItem address;
    public JCheckBoxMenuItem email;
    public JCheckBoxMenuItem phone;

    public Columns() {
        init();
    }

    public JCheckBoxMenuItem[] getChecks() {
        return new JCheckBoxMenuItem[]{
            id,
            name,
            grade,
            subGrade,
            medium,
            birth,
            gender,
            phone,
            address,
            guardian,
            email
        };
    }

    public void setSelected(boolean[] s) {
        int y = 0;
        for (int i = 0; i < s.length; i++) {
            JCheckBoxMenuItem c = getChecks()[i];
            if (c.isEnabled()) {
                c.setSelected(s[i]);
                y += s[i] ? 1 : 0;
            }
        }
        All.setSelected(y >= s.length);
    }

    public void deselectAll() {
        boolean[] b = new boolean[getChecks().length];
        Arrays.fill(b, false);
        setSelected(b);
    }

    public void selectAll() {
        boolean[] b = new boolean[getChecks().length];
        Arrays.fill(b, true);
        setSelected(b);
    }

    public boolean[] getSelectedItems() {
        boolean[] r = new boolean[getChecks().length];
        int i = 0;
        for (JCheckBoxMenuItem c : getChecks()) {
            r[i] = c.isSelected();
            i++;
        }
        return r;
    }

    void init() {
        this.All = new javax.swing.JCheckBoxMenuItem();
        this.id = new javax.swing.JCheckBoxMenuItem();
        this.name = new javax.swing.JCheckBoxMenuItem();
        this.grade = new javax.swing.JCheckBoxMenuItem();
        this.subGrade = new javax.swing.JCheckBoxMenuItem();
        this.medium = new javax.swing.JCheckBoxMenuItem();
        this.birth = new javax.swing.JCheckBoxMenuItem();
        this.gender = new javax.swing.JCheckBoxMenuItem();
        this.address = new javax.swing.JCheckBoxMenuItem();
        this.phone = new javax.swing.JCheckBoxMenuItem();
        this.guardian = new javax.swing.JCheckBoxMenuItem();
        this.email = new javax.swing.JCheckBoxMenuItem();
        All.setSelected(true);
        All.setText("All");

        id.setSelected(true);
        id.setText("Admission Number");

        name.setSelected(true);
        name.setText("Name");

        grade.setSelected(true);
        grade.setText("Grade");

        subGrade.setSelected(true);
        subGrade.setText("Sub grade");

        medium.setSelected(true);
        medium.setText("Medium");

        birth.setSelected(true);
        birth.setText("Date of birth");

        gender.setSelected(true);
        gender.setText("Gender");

        address.setSelected(true);
        address.setText("Address");

        phone.setSelected(true);
        phone.setText("Phone Number");

        guardian.setSelected(true);
        guardian.setText("Guardian");

        email.setSelected(true);
        email.setText("Email");

        All.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (All.isSelected()) {
                    selectAll();
                } else {
                    deselectAll();
                }
            }
        });
        for (JCheckBoxMenuItem c : getChecks()) {
            if (c.equals(All)) {
                continue;
            }
            c.addActionListener((ActionEvent e) -> {
                int i = 0;
                int i2 = 0;
                for (JCheckBoxMenuItem c1 : getChecks()) {
                    i += c1.isSelected() ? 1 : 0;
                    i2++;
                }
                All.setSelected(i == i2);
                if (i == 0) {
                    c.setSelected(true);
                    new TrayMessage("Cannot have 0 columns", "", TrayIcon.MessageType.ERROR).display();
                }
            });
        }
    }

}
