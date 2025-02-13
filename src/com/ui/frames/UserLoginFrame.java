package com.ui.frames;

import com.ui.dialogs.security.NewSignUp;
import com.ui.dialogs.security.ChangePasswordOption;
import com.Codes.Commons;
import com.ui.CommonUi;
import com.Codes.AppConfig;
import com.ManageDetails.school.School;
import com.ui.frames.LoadFrame;
import com.Database.DBconnect;
import com.Main.Defaults;
import com.SystemSecurity.Security;
import static com.ui.frames.UserFrame.getBorW;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.Enumeration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class UserLoginFrame extends javax.swing.JFrame {

    public static String username;
    public static Security security;
    public static School school;

    public UserLoginFrame() {
        initComponents();
        validate();
        repaint();
        doLayout();
        setIconImage(AppConfig.APPICON_32.getImage());
        unamebox.setBackground(CommonUi.getTransparentColor());
        passwordBox.setBackground(CommonUi.getTransparentColor());
        user.setIcon(Commons.getImage("src/Images/user-white.png", user.getHeight(), user.getHeight()));
        logo.setIcon(Commons.getImage("src/Images/Logo.png", logo.getWidth(), logo.getHeight()));
        unamebox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (unamebox.getText().equals("")) {
                    unplaceholder.setText("User Name");
                } else {
                    unplaceholder.setText("");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                insertUpdate(e);
            }
        });
        passwordBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (passwordBox.getText().equals("")) {
                    pwplaceholder.setText("Password");
                } else {
                    pwplaceholder.setText("");
                }
            }
        });
    }

    public UserLoginFrame(String laf) {
        this();
        switch (laf) {
            case "dark":
                try {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                } catch (UnsupportedLookAndFeelException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                break;
            case "light":
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                } catch (UnsupportedLookAndFeelException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                break;
            default:
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        unamebox = new javax.swing.JTextField();
        passwordBox = new javax.swing.JPasswordField();
        pwplaceholder = new javax.swing.JLabel();
        unplaceholder = new javax.swing.JLabel();
        user = new javax.swing.JLabel();
        loginbtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        image = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("System Manegement System - My Soft");
        setForeground(new java.awt.Color(51, 51, 51));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setBackground(new Color(0,0,0,100));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("x");
        jButton3.setBorder(null);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 20, 20));

        jButton4.setBackground(new Color(0,0,0,100));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("_");
        jButton4.setBorder(null);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 20, 20));

        unamebox.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        unamebox.setForeground(new java.awt.Color(255, 255, 255));
        unamebox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        unamebox.setBorder(null);
        unamebox.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        unamebox.setSelectedTextColor(new java.awt.Color(102, 102, 102));
        unamebox.setSelectionColor(new java.awt.Color(204, 204, 204));
        unamebox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                unameboxKeyTyped(evt);
            }
        });
        jPanel1.add(unamebox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 380, 40));

        passwordBox.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        passwordBox.setForeground(new java.awt.Color(255, 255, 255));
        passwordBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordBox.setBorder(null);
        passwordBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passwordBoxKeyTyped(evt);
            }
        });
        jPanel1.add(passwordBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 380, 40));

        pwplaceholder.setBackground(new java.awt.Color(102, 102, 102));
        pwplaceholder.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 18)); // NOI18N
        pwplaceholder.setForeground(new java.awt.Color(255, 255, 255));
        pwplaceholder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pwplaceholder.setText("Password");
        jPanel1.add(pwplaceholder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 380, 40));

        unplaceholder.setBackground(new java.awt.Color(102, 102, 102));
        unplaceholder.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 18)); // NOI18N
        unplaceholder.setForeground(new java.awt.Color(255, 255, 255));
        unplaceholder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        unplaceholder.setText("User name");
        jPanel1.add(unplaceholder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 380, 40));

        user.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        user.setForeground(new java.awt.Color(255, 255, 255));
        user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 380, 100));

        loginbtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        loginbtn.setText("LOG IN");
        loginbtn.setBorder(null);
        loginbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginbtnActionPerformed(evt);
            }
        });
        jPanel1.add(loginbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 350, 30));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("Change");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 80, -1));

        jLabel4.setBackground(new java.awt.Color(102, 102, 102));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Forgot password or user name?");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 200, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Click to change..");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 110, -1));

        jButton2.setText("Sign up");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, -1, -1));

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/login.jpg"))); // NOI18N
        jPanel1.add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 430));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 380, 430));

        jPanel2.setBackground(com.ui.CommonUi.getNavColor());
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(com.ui.CommonUi.getNavColor()));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel2MouseMoved(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 16, 37));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("System");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Student Mannagement");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));
        jPanel3.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 100, 100));

        jLabel8.setBackground(new java.awt.Color(153, 153, 153));
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Copyright 2022");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel9.setBackground(new java.awt.Color(153, 153, 153));
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Developed by Adithya Wijewickrama");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 430));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 450));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ChangePasswordOption changePassword = new ChangePasswordOption(this, true) {
            @Override
            public void passwordChanged() {
                dispose();
                Commons.showMsg("Password change successful");
            }
        };
        CommonUi.genarateCenter(changePassword, changePassword.getWidth(), changePassword.getHeight());
        changePassword.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loginbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginbtnActionPerformed
        String uname = unamebox.getText();
        String pword = passwordBox.getText();
        if (!uname.equals("") && !pword.equals("")) {
            if (Security.isSingedUp(uname)) {
                security = new Security(uname);
                if (security.getPassword().equals(pword)) {
                    username = uname;
                    school = new School(uname);
                    this.dispose();
                    LoadFrame formLoad = new LoadFrame();
                    formLoad.setVisible(true);
                } else {
                    Commons.showErrMsg("User name or Password is incorrect");
                }
            } else {
                Commons.showErrMsg("User name or Password is incorrect");
                unamebox.requestFocus();
            }
        } else {
            Commons.showErrMsg("User name & password can not be emty");
        }
    }//GEN-LAST:event_loginbtnActionPerformed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        this.setBounds(evt.getXOnScreen() - evt.getX(), evt.getYOnScreen() - evt.getY(), getWidth(), getHeight());
        validate();
        repaint();
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseMoved
        this.setBounds(evt.getXOnScreen() - evt.getX(), evt.getYOnScreen() - evt.getY(), getWidth(), getHeight());
        validate();
        repaint();
    }//GEN-LAST:event_jPanel2MouseMoved

    private void passwordBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordBoxKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            String uname = unamebox.getText();
            String pword = passwordBox.getText();
            if (!uname.equals("") && !pword.equals("")) {
                security = new Security(uname);
                String Uname = security.getUsername();
                String Pword = security.getPassword();
                if (Uname != null && Pword != null) {
                    this.dispose();
                    school = new School(uname);
                    LoadFrame formLoad = new LoadFrame();
                    formLoad.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "User name or Password is incorrect", "Incorrect input", JOptionPane.ERROR_MESSAGE);
                    unamebox.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "User name & password can not be emty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_passwordBoxKeyTyped

    private void unameboxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_unameboxKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            passwordBox.requestFocus();
        }
        security = new Security(unamebox.getText());
    }//GEN-LAST:event_unameboxKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        NewSignUp newSignUp = new NewSignUp(this, true);
        CommonUi.genarateCenter(newSignUp, newSignUp.getWidth(), newSignUp.getHeight());
        newSignUp.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Connection conn = DBconnect.CONN;
        switch (Commons.getDefault(Defaults.LookAndFeel, conn)) {
            case "dark":
                try {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    FlatLaf.updateUI();
                } catch (UnsupportedLookAndFeelException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                break;
            case "light":
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    FlatLaf.updateUI();
                } catch (UnsupportedLookAndFeelException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                break;
        }
        UIManager.put("InternalFrame.icon", AppConfig.APPICON_28);
        UIDefaults d = UIManager.getLookAndFeelDefaults();
        Enumeration e = d.keys();
        Enumeration e1 = d.elements();
        while (e.hasMoreElements()) {
            String s = e.nextElement().toString();
            String s1 = e1.nextElement().toString();
            if (s.contains("Dialog")) {
                System.out.println(s + "\t" + s1);
            }
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserLoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel image;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton loginbtn;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField passwordBox;
    private javax.swing.JLabel pwplaceholder;
    private javax.swing.JTextField unamebox;
    private javax.swing.JLabel unplaceholder;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
}
