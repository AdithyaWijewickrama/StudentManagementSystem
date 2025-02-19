package com.ui.dialogs.security;

import com.Codes.Commons;
import com.ManageDetails.school.School;
import com.SystemSecurity.PasswordValidator;
import com.SystemSecurity.Security;
import com.ui.dialogs.SchoolDetails;
import com.formdev.flatlaf.FlatLightLaf;
import com.ui.CommonUi;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class NewSignUp extends javax.swing.JDialog {

    private Security security;
    private final PasswordValidator passwordValidator;
    private final int STRNGTH = 6;

    public NewSignUp(JFrame owner, boolean model) {
        super(owner, model);
        System.out.println(getParent().equals(owner));
        passwordValidator = new PasswordValidator();
        initComponents();
    }

    public String getPassword() {
        return jPasswordField1.getText();
    }

    private void allSet() {
        if (School.isSingedUp(security.getUsername())) {
            security.update();
            setPanel(allSetPanel);
            jLabel9.setIcon(Commons.getImage(new School(security.getUsername()).getProfileImage(), 100, 100));
            jLabel11.setText(security.getUsername());
            jButton1.setText("Finish");
            jButton2.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        userNamePanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        passwordPanel = new javax.swing.JPanel();
        newPasswordField = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        newPasswordField1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        questionPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        allSetPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
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

        userNamePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setText("Enter an User name");
        userNamePanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 17, 380, 35));

        jTextField1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        userNamePanel.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 78, 380, -1));

        jLayeredPane1.add(userNamePanel, "card4");

        passwordPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        passwordPanel.add(newPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 91, 377, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setText("Password");
        passwordPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 377, -1));

        jCheckBox2.setText("Show password");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        passwordPanel.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 185, -1, -1));
        passwordPanel.add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 220, 180, -1));

        jLabel3.setText("Strength");
        passwordPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 211, 64, 20));

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
        passwordPanel.add(newPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 149, 377, -1));

        jLabel5.setText("Retype password");
        passwordPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 127, -1, -1));

        jLabel6.setText("Enter password");
        passwordPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 69, -1, -1));

        jLayeredPane1.add(passwordPanel, "card3");

        questionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Enter the answer");
        questionPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 144, -1, -1));

        jCheckBox1.setText("show answer");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        questionPanel.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 209, -1, -1));

        jPasswordField1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });
        questionPanel.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 169, 377, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("Security Question");
        questionPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 371, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Enter the question");
        questionPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 75, -1, -1));

        jTextField2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        questionPanel.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 377, -1));

        jLayeredPane1.add(questionPanel, "card2");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel10.setText("You're All Set");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("username");

        javax.swing.GroupLayout allSetPanelLayout = new javax.swing.GroupLayout(allSetPanel);
        allSetPanel.setLayout(allSetPanelLayout);
        allSetPanelLayout.setHorizontalGroup(
            allSetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allSetPanelLayout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(allSetPanelLayout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        allSetPanelLayout.setVerticalGroup(
            allSetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allSetPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        jLayeredPane1.add(allSetPanel, "card5");

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
            if (username.isEmpty()) {
                Commons.showErrMsg("User name cannot be empty");
            } else if (Security.isSingedUp(username)) {
                Commons.showErrMsg("User name is already taken");
            } else {
                security = new Security(username);
                jLabel1.setText(username);
                p = passwordPanel;
            }
        } else if (p.equals(questionPanel)) {
            String question = jTextField2.getText();
            String answer = String.valueOf(jPasswordField1.getPassword());
            security.setQuestion(question);
            security.setAnswer(answer);
            SchoolDetails sd = new SchoolDetails(security.getUsername(), (Frame) this.getParent(), true) {
                public void applied() {
                    dispose();
                    allSet();
                }
            };
            CommonUi.genarateCenter(sd, sd.getWidth(), sd.getHeight());
            sd.setVisible(true);
            return;
        } else if (p.equals(allSetPanel)) {
            this.dispose();
        } else if (p.equals(passwordPanel)) {
            String newPassword = String.valueOf(newPasswordField.getPassword());
            String retypeNewPassword = String.valueOf(newPasswordField1.getPassword());
            if (passwordValidator.isValid(PasswordValidator.StrengthType.MIXED_NUMARIC)) {
                if (newPassword.equals(retypeNewPassword)) {
                    security.setPassword(newPassword);
                    p = questionPanel;
                } else {
                    Commons.showErrMsg("Retyped password does not match");
                }
            } else {
                Commons.showErrMsg("Password is not valid!");
            }
        }
        setPanel(p);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JPanel p = (JPanel) jLayeredPane1.getComponent(0);
        if (p.equals(userNamePanel)) {
            dispose();
        } else if (p.equals(questionPanel)) {
            p = passwordPanel;
        } else if (p.equals(passwordPanel)) {
            jButton2.setText("Cancel");
            p = userNamePanel;
        } else if (p.equals(allSetPanel)) {
            p = questionPanel;
        }
        setPanel(p);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void setPanel(JPanel p) {
        jLayeredPane1.removeAll();
        jLayeredPane1.add(p);
        p.setVisible(true);
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

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
        newPasswordField1.setEchoChar(jCheckBox2.isSelected() ? (char) 0 : '•');
    }//GEN-LAST:event_jCheckBox2ActionPerformed

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
            Logger.getLogger(NewSignUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewSignUp dialog = new NewSignUp(new JFrame("dsasad"), true);
                dialog.setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel allSetPanel;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JCheckBox jCheckBox1;
    public javax.swing.JCheckBox jCheckBox2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
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
    public javax.swing.JTextField jTextField2;
    public javax.swing.JPasswordField newPasswordField;
    public javax.swing.JPasswordField newPasswordField1;
    public javax.swing.JPanel passwordPanel;
    public javax.swing.JPanel questionPanel;
    public javax.swing.JPanel userNamePanel;
    // End of variables declaration//GEN-END:variables
}
