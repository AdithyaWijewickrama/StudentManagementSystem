package com.ui.frames;

import com.ui.frames.UserFrame;
import static com.Codes.Commons.showMsg;
import static com.ui.frames.UserFrame.getBorW;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

public final class LoadFrame extends javax.swing.JFrame {

    int load = 0;

    public LoadFrame() {
        fill();
    }

    public static Color getTransparentColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(0, 0, 0, 100) : new Color(255, 255, 255, 100);
    }

    public static Color getBottomColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(60, 63, 65) : new Color(255, 255, 255);
    }

    public static Color getBarColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(51, 51, 51) : new Color(204, 204, 204);
    }

    private void fill() {
        initComponents();
        logo.setIcon(setImage("src\\Images\\Logo - Loader.png", 100, 100));
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                switch (load) {
                    case 20:
                        bar.setValue(load);
                        Text.setText("Initializing Components...");
                        break;
                    case 40:
                        bar.setValue(load);
                        Text.setText("Done Initializing Components...");
                        break;
                    case 50:
                        bar.setValue(load);
                        Text.setText("Connecting to Database...");
                        break;
                    case 70:
                        bar.setValue(load);
                        Text.setText("Connection Succesfull...");
                         {
                            try {
                                UserFrame mf = new UserFrame(LoadFrame.this);
                                mf.setVisible(true);
                            } catch (Exception e) {
                                showMsg(e);
                            }
                        }
                        break;
                }

                if (load == 100) {
                    Text.setText("Please wait starting application...");
                    bar.setValue(load);
                    startApplication();
                    cancel();
                }
                load++;
            }
        };
        timer.schedule(task, 0, 30);
    }

    public void startApplication() {
        Text.setText("Please wait starting application...");
        bar.setValue(100);
    }

    public void close() {
        this.dispose();
    }

    public ImageIcon setImage(String ImagePath, int Width, int Height) {
        ImageIcon Imge;
        Imge = new ImageIcon(ImagePath);
        java.awt.Image img = Imge.getImage();
        java.awt.Image newimg = img.getScaledInstance(Width, Height, 25);
        ImageIcon i = new ImageIcon(newimg);
        return i;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bar = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Text = new javax.swing.JLabel();
        SchoolImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System");
        setBackground(new java.awt.Color(102, 0, 0));
        setFocusCycleRoot(false);
        setFocusable(false);
        setForeground(new java.awt.Color(255, 51, 153));
        setUndecorated(true);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bar.setBackground(new java.awt.Color(102, 102, 102));
        bar.setForeground(new java.awt.Color(0, 8, 43));
        bar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                barMouseExited(evt);
            }
        });
        getContentPane().add(bar, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 378, 610, 20));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(com.Codes.Commons.getImage("src\\Images\\LoadingClose.png", 15, 15));
        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 20, 20));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(com.Codes.Commons.getImage("src\\Images\\Minimize-"+getBorW()+".png", 15, 15));
        jButton2.setBorder(null);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 20, 20));

        jPanel1.setBackground(getTransparentColor());
        jPanel1.setMinimumSize(new java.awt.Dimension(591, 378));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 206, 118));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 92));
        jLabel1.setText("System");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 206, 118));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 92));
        jLabel2.setText("Student Management");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, -1, -1));
        jPanel1.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 110, 110));

        jLabel5.setBackground(new java.awt.Color(0, 4, 40));
        jLabel5.setForeground(new java.awt.Color(0, 4, 40));
        jLabel5.setText("© Copyright 2022 Adithya Wijewickrama . All rights reserved");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 570, 10));

        Text.setBackground(new java.awt.Color(255, 255, 255));
        Text.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        Text.setForeground(new java.awt.Color(0, 0, 51));
        Text.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Text.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TextMouseEntered(evt);
            }
        });
        jPanel1.add(Text, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 570, 20));

        SchoolImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SchoolImage.setIcon(com.Codes.Commons.getImage("src\\Images\\LoadingBackground.jpg", 590, 424));
        SchoolImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(SchoolImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 380));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 380));

        setSize(new java.awt.Dimension(590, 383));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
    }//GEN-LAST:event_formMouseEntered

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
    }//GEN-LAST:event_jPanel1MouseEntered

    private void TextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TextMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TextMouseEntered

    private void barMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_barMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoadFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoadFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoadFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoadFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new LoadFrame().setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SchoolImage;
    private javax.swing.JLabel Text;
    private javax.swing.JProgressBar bar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
