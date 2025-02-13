package com.Codes;

import static com.ui.frames.UserFrame.getBorW;
import javax.swing.ImageIcon;

public class AppConfig {

    public static final String APPNAME = "Student Management System";
    public static final ImageIcon APPICON = new ImageIcon("src\\Images\\Logo.png");

    public static final ImageIcon SECUREICON = new ImageIcon("src\\Images\\Logo-security-large.png");
    public static final ImageIcon D = Commons.getImage("src\\Images\\Toolbar\\Single-" + getBorW() + ".png", 28, 28);
    public static final ImageIcon APPICON_28 = Commons.getImage(APPICON, 20, 20);
    public static final ImageIcon APPICON_32 = Commons.getImage(APPICON, 32, 32);
    public static final ImageIcon APPICON_50 = Commons.getImage(APPICON, 50, 50);

    public static void main(String[] args) {
    }
}
