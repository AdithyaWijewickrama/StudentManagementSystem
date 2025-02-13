package com.ui.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

public class SideButton {

    public JButton f;
    public Color fcolor;
    public Color bcolor;

    public SideButton(JButton f, Color fcolor, Color bcolor) {
        this.f = f;
        this.fcolor = fcolor;
        this.bcolor = bcolor;
//        f.setBorder(new LineBorder(fcolor, 1, true));
        f.setBackground(bcolor);
        f.setForeground(fcolor);
        setButton();
    }

    public void setButton() {
        f.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!f.isSelected()) {
                    f.setBackground(fcolor);
                    f.setForeground(bcolor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!f.isSelected()) {
                    f.setBackground(bcolor);
                    f.setForeground(fcolor);
                }
            }
        });
        f.addActionListener((e) -> {
            f.setBackground(fcolor);
            f.setForeground(bcolor);
        });
        f.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("khjfh");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!f.isSelected()) {
                    f.setBackground(bcolor);
                    f.setForeground(fcolor);
                }
            }
        });
    }

}
