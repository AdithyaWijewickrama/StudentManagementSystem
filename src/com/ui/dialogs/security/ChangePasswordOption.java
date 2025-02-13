package com.ui.dialogs.security;

import com.Codes.Commons;
import com.SystemSecurity.PasswordValidator;
import com.SystemSecurity.Security;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public abstract class ChangePasswordOption extends javax.swing.JDialog {

    int value = -1;
    public static int CLOSED = -1;
    public static int MATCHED = 0;
    public static int NOTMATCHED = 1;
    public static int CANCEL = 2;
    private Security security;
    private final PasswordValidator passwordValidator;
    private final int STRNGTH = 6;

    public ChangePasswordOption(JFrame owner, boolean model) {
        super(owner, model);
        passwordValidator = new PasswordValidator();
        initComponents();
    }

    public String getPassword() {
        return jPasswordField1.getText();
    }

    public abstract void passwordChanged();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        userNamePanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        questionPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        newPasswordPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        newPasswordField = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        newPasswordField1 = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Security");
        setMinimumSize(new java.awt.Dimension(400, 450));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        jPanel6.setMaximumSize(new java.awt.Dimension(400, 330));
        jPanel6.setMinimumSize(new java.awt.Dimension(400, 330));
        jPanel6.setPreferredSize(new java.awt.Dimension(400, 330));
        jPanel6.setLayout(new java.awt.CardLayout(10, 10));

        jLayeredPane1.setAlignmentX(0.0F);
        jLayeredPane1.setAlignmentY(0.0F);
        jLayeredPane1.setLayout(new java.awt.CardLayout());

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setText("Enter the User name");

        jTextField1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout userNamePanelLayout = new javax.swing.GroupLayout(userNamePanel);
        userNamePanel.setLayout(userNamePanelLayout);
        userNamePanelLayout.setHorizontalGroup(
            userNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userNamePanelLayout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jTextField1)
        );
        userNamePanelLayout.setVerticalGroup(
            userNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userNamePanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );

        jLayeredPane1.add(userNamePanel, "card4");

        questionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Enter the answer");
        questionPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 92, -1, -1));

        jCheckBox1.setText("show answer");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        questionPanel.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 157, -1, -1));

        jPasswordField1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });
        questionPanel.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 117, 360, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("What...?");
        questionPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 370, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel4.setText("Security Question");
        questionPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 210, -1));

        jLayeredPane1.add(questionPanel, "card2");

        newPasswordPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("New password");
        newPasswordPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 100, -1));

        newPasswordField.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        newPasswordField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        newPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPasswordFieldActionPerformed(evt);
            }
        });
        newPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                newPasswordFieldKeyTyped(evt);
            }
        });
        newPasswordPanel.add(newPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 360, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setText("User name");
        newPasswordPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 130, -1));

        jCheckBox2.setText("show password");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        newPasswordPanel.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, -1, -1));
        newPasswordPanel.add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 167, -1));

        jLabel3.setText("Strength");
        newPasswordPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 64, 20));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Retype password");
        newPasswordPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 120, -1));

        newPasswordField1.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        newPasswordField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        newPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPasswordField1ActionPerformed(evt);
            }
        });
        newPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                newPasswordField1KeyTyped(evt);
            }
        });
        newPasswordPanel.add(newPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 360, -1));

        jLayeredPane1.add(newPasswordPanel, "card3");

        jPanel6.add(jLayeredPane1, "card2");

        getContentPane().add(jPanel6);

        jPanel4.setLayout(new java.awt.CardLayout(10, 10));

        jPanel3.setAlignmentX(0.0F);
        jPanel3.setLayout(new java.awt.GridLayout(2, 0, 10, 10));

        jButton1.setText("Next");
        jButton1.setAlignmentY(1.0F);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jPanel4.add(jPanel3, "card2");

        getContentPane().add(jPanel4);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton2.setText("Back");
        JPanel p = (JPanel) jLayeredPane1.getComponent(0);
        if (p.equals(userNamePanel)) {
            String username = jTextField1.getText();
            if (Security.isSingedUp(username)) {
                security = new Security(username);
                p = questionPanel;
                jLabel1.setText(security.getQuestion());
            } else {
                Commons.showErrMsg("User name is not valid");
            }
        } else if (p.equals(questionPanel)) {
            String answer = String.valueOf(jPasswordField1.getPassword());
            if (security.getAnswer().equals(answer)) {
                jLabel7.setText(security.getUsername());
                p = newPasswordPanel;
            } else {
                Commons.showErrMsg("Question is wrong");
            }
        } else if (p.equals(newPasswordPanel)) {
            String newPassword = String.valueOf(newPasswordField.getPassword());
            String retypePassword = String.valueOf(newPasswordField1.getPassword());
            if (passwordValidator.isValid(PasswordValidator.StrengthType.MIXED_NUMARIC)) {
                if (newPassword.equals(retypePassword)) {
                    security.setPassword(newPassword);
                    security.update();
                    passwordChanged();
                } else {
                    Commons.showErrMsg("Retyped passwords do not match");
                }
            } else {
                Commons.showErrMsg("Password is not valid");
            }
        }
        jLayeredPane1.removeAll();
        jLayeredPane1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JPanel p = (JPanel) jLayeredPane1.getComponent(0);
        if (p.equals(userNamePanel)) {
            dispose();
        } else if (p.equals(questionPanel)) {
            jButton2.setText("Cancel");
            p = userNamePanel;
        } else if (p.equals(newPasswordPanel)) {
            p = questionPanel;
        }
        jLayeredPane1.removeAll();
        jLayeredPane1.add(p);
        p.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        value = -1;
    }//GEN-LAST:event_formWindowClosing

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        jButton1.doClick(200);
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        jPasswordField1.setEchoChar(jCheckBox1.isSelected() ? (char) 0 : '•');
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void newPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPasswordFieldActionPerformed
        newPasswordField.transferFocus();
    }//GEN-LAST:event_newPasswordFieldActionPerformed

    private void newPasswordFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newPasswordFieldKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            jButton1.requestFocus();
        }
        passwordValidator.setPassword(String.valueOf(newPasswordField.getPassword()));
        jProgressBar1.setValue((int) (Double.valueOf(passwordValidator.getStrength()) / STRNGTH * 100));
        int val = jProgressBar1.getValue();
        if (val > 66) {
            jProgressBar1.setForeground(Color.GREEN);
        } else if (val > 33) {
            jProgressBar1.setForeground(Color.YELLOW);
        } else if (val > 0) {
            jProgressBar1.setForeground(Color.RED);
        }
    }//GEN-LAST:event_newPasswordFieldKeyTyped

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        newPasswordField.setEchoChar(jCheckBox2.isSelected() ? (char) 0 : '•');
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        security = new Security(jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyTyped

    private void newPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newPasswordField1ActionPerformed

    private void newPasswordField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newPasswordField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_newPasswordField1KeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ChangePasswordOption.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChangePasswordOption dialog = new ChangePasswordOption(new JFrame("dsasad"), true) {
                    @Override
                    public void passwordChanged() {
                    }
                };
                dialog.setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JCheckBox jCheckBox1;
    public javax.swing.JCheckBox jCheckBox2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JLayeredPane jLayeredPane1;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPasswordField jPasswordField1;
    public javax.swing.JProgressBar jProgressBar1;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JPasswordField newPasswordField;
    public javax.swing.JPasswordField newPasswordField1;
    public javax.swing.JPanel newPasswordPanel;
    public javax.swing.JPanel questionPanel;
    public javax.swing.JPanel userNamePanel;
    // End of variables declaration//GEN-END:variables
}
