package com.ui.dialogs.security;

import com.Codes.AppConfig;
import com.Codes.Commons;
import com.SystemSecurity.Security;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class Recovery extends javax.swing.JDialog {

    Security security;

    public Recovery(String username) {
        initComponents();
        security = new Security(username);
        setData();
    }

    void setData() {
        u.setText(security.getUsername());
        p.setText(security.getPassword());
        qbox.setSelectedItem(security.getQuestion());
        abox.setText(security.getAnswer());
    }

    public void Update() {
        try {
            security.setAnswer(u.getText());
            security.setPassword(String.valueOf(p.getPassword()));
            security.setQuestion(qbox.getSelectedItem().toString());
            security.setAnswer(String.valueOf(abox.getPassword()));
            security.update();
            Commons.showMsg("Changes saved!\nNEW QUESTION\t:'" + security.getQuestion() + "'\nNew answer\t:'" + security.getAnswer() + "'");
        } catch (Exception e) {
            Commons.showErrMsg(e.getLocalizedMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        qbox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        u = new javax.swing.JTextField();
        p = new javax.swing.JPasswordField();
        abox = new javax.swing.JPasswordField();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Password Recovery Information");
        setIconImage(AppConfig.SECUREICON.getImage());
        setMinimumSize(new java.awt.Dimension(450, 370));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        qbox.setEditable(true);
        qbox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        qbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "What is your First School?", "What is your Fathers Name?", "What is your favorite Movie?", "What is your best friends Name?", "What is your favorite Subject?", "Who is your favorite Author?", "Who is your favorite Person?" }));
        qbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qboxActionPerformed(evt);
            }
        });
        qbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qboxKeyTyped(evt);
            }
        });
        getContentPane().add(qbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 321, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Answer");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 130, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Question");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 130, -1));

        jButton1.setText("?");
        jButton1.setToolTipText("HELP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        jButton3.setBackground(new java.awt.Color(27, 110, 240));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("OK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Password");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 130, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("User Name");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 130, -1));

        u.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        u.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                uKeyTyped(evt);
            }
        });
        getContentPane().add(u, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 321, -1));

        p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pKeyTyped(evt);
            }
        });
        getContentPane().add(p, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 320, -1));
        getContentPane().add(abox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 320, -1));

        jCheckBox2.setText("Show password");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, -1));

        jCheckBox1.setText("show answer");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, -1));

        jLabel1.setIcon(Commons.getImage("src/Images/Logo-security-large.png", 280, 280));
        jLabel1.setToolTipText("");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, 20, 280, 290));

        setSize(new java.awt.Dimension(457, 354));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void qboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qboxActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int t = Commons.showConfMsg("Are you sure want to change security Q & A");
        try {
            if (t == 0) {
                Update();
                this.dispose();
            }
        } catch (Exception e) {
            Commons.showErrMsg("The entered Password or User name is incorrect");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JOptionPane.showMessageDialog(this, "SECURITY QUESTION:\nIf you forgot your user name or password, to change it we ask you an qustion.\nAnd you've to answer that question correctly.\nChoose a question and type a suitable answer for that question that you won't forget!\n\nEX:\nQuestion:> What is your Fathers Name?\nAnswer:> Edward Mc.Donald");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void uKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_uKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            p.requestFocus();
        }
    }//GEN-LAST:event_uKeyTyped

    private void pKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            qbox.requestFocus();
        }
    }//GEN-LAST:event_pKeyTyped

    private void qboxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qboxKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            abox.requestFocus();
        }
    }//GEN-LAST:event_qboxKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        p.setEchoChar(jCheckBox2.isSelected() ? (char) 0 : '•');
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        abox.setEchoChar(jCheckBox1.isSelected() ? (char) 0 : '•');
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Recovery("SMS24").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField abox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField p;
    private javax.swing.JComboBox<String> qbox;
    private javax.swing.JTextField u;
    // End of variables declaration//GEN-END:variables
}
