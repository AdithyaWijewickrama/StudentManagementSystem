package com.ui.internalFrames;

import com.ui.CommonUi;
import static com.ui.CommonUi.gsi;
import com.Codes.Commons;
import static com.Codes.Commons.getImage;
import static com.Codes.Commons.setDefault;
import static com.Codes.Commons.showMsg;
import com.Codes.ImageWriter;
import com.Communication.Mail;
import com.ManageDetails.student.CurrentStudent;
import com.Main.Defaults;
import static com.ui.frames.UserFrame.getDecrypted;
import com.Main.Selection;
import com.ManageDetails.student.Student;
import static com.MarksReport.Marks.TERMS;
import static com.Printing.Print.CONN;
import com.Database.Sql;
import com.formdev.flatlaf.FlatLightLaf;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import static com.ManageDetails.student.Student.getQRCodeName;
import static com.MarksReport.Marks.getps;
import static com.ui.frames.UserLoginFrame.school;
import com.Database.DBconnect;
import com.Printing.Extention;
import com.Printing.Identy;
import com.Printing.Ireport;
import com.Printing.Marks;
import com.Printing.Print;
import com.ui.CommonUi;
import com.ui.frames.UserFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class Print_frame extends javax.swing.JInternalFrame {

    private String SetDirectory;
    public String[] selectedIds;
    private JTable table;
    public CurrentStudent cs;
    public Selection sel;

    public Print_frame(CurrentStudent cs, Selection sel) {
        initComponents();
        this.cs = cs;
        this.sel = sel;
        table = this.sel.Table;
        updateAppDir();
        setTablesDefaults();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setEmail = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        Email_subj = new javax.swing.JTextArea();
        jLabel213 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        Email_body = new javax.swing.JTextArea();
        jLabel214 = new javax.swing.JLabel();
        jButton68 = new javax.swing.JButton();
        jButton69 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel215 = new javax.swing.JLabel();
        jLabel216 = new javax.swing.JLabel();
        setEmail_Email = new javax.swing.JTextField();
        jLabel217 = new javax.swing.JLabel();
        jLabel218 = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        AL = new javax.swing.JCheckBox();
        OL = new javax.swing.JCheckBox();
        Scship = new javax.swing.JCheckBox();
        exams = new javax.swing.JCheckBox();
        jPanel16 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        gradeC = new javax.swing.JComboBox<>();
        TermC = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        RImage = new javax.swing.JLabel();
        jButton60 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        Right = new javax.swing.JComboBox<>();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jPanel23 = new javax.swing.JPanel();
        LImage = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        Left = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        PD_title = new javax.swing.JTable();
        jButton63 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jButton62 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        PD_desc = new javax.swing.JTable();
        jLabel129 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jButton64 = new javax.swing.JButton();
        jButton66 = new javax.swing.JButton();
        jScrollPane19 = new javax.swing.JScrollPane();
        PD_summery = new javax.swing.JTable();
        jLabel212 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        PrintButton = new javax.swing.JButton();
        PrintButton1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel130 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        FileName = new javax.swing.JTextField();
        idbox1 = new javax.swing.JTextField();
        jLabel127 = new javax.swing.JLabel();
        SaveDir = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        FileExtention = new javax.swing.JComboBox<>();
        jLabel210 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();

        setEmail.setTitle("Set Email");
        setEmail.setMinimumSize(new java.awt.Dimension(490, 406));
        setEmail.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setEmail.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setEmail.setResizable(false);
        setEmail.setSize(new java.awt.Dimension(500, 420));
        setEmail.setType(java.awt.Window.Type.UTILITY);
        setEmail.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Email_subj.setColumns(20);
        Email_subj.setRows(5);
        jScrollPane5.setViewportView(Email_subj);

        setEmail.getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 91, 460, 94));

        jLabel213.setText("Subject");
        setEmail.getContentPane().add(jLabel213, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 69, -1, -1));

        Email_body.setColumns(20);
        Email_body.setRows(5);
        jScrollPane6.setViewportView(Email_body);

        setEmail.getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 213, 460, 94));

        jLabel214.setText("Body");
        setEmail.getContentPane().add(jLabel214, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 191, -1, -1));

        jButton68.setBackground(new java.awt.Color(0, 102, 255));
        jButton68.setText("Ok");
        jButton68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton68ActionPerformed(evt);
            }
        });
        setEmail.getContentPane().add(jButton68, new org.netbeans.lib.awtextra.AbsoluteConstraints(429, 313, -1, -1));

        jButton69.setText("Cancel");
        jButton69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton69ActionPerformed(evt);
            }
        });
        setEmail.getContentPane().add(jButton69, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 313, -1, -1));

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Marks Report(Single)", "Marks Report(Multiple)", "ID Card", "Students Detailes", "Caracter Certificate", "Leaving Report" }));
        jComboBox3.setToolTipText("Document type");
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        setEmail.getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 36, 356, -1));

        jLabel215.setText("Report type");
        jLabel215.setToolTipText("select the report type to load the latest data");
        setEmail.getContentPane().add(jLabel215, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 36, -1, -1));

        jLabel216.setText("Emai");
        setEmail.getContentPane().add(jLabel216, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 10, -1, -1));

        setEmail_Email.setText("$e");
        setEmail.getContentPane().add(setEmail_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 6, 356, -1));

        jLabel217.setText("Important make sure that your computer is connected to the enternet & ");
        setEmail.getContentPane().add(jLabel217, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        jLabel218.setText("your virus guerd is not blocking the SMTP server otherwise sending mail");
        setEmail.getContentPane().add(jLabel218, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        jLabel219.setText("wont work cirrectly");
        setEmail.getContentPane().add(jLabel219, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        setResizable(true);
        setTitle("Print");

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AL.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AL.setText("Advanced Level Examination");
        AL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        AL.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        AL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ALActionPerformed(evt);
            }
        });
        jPanel1.add(AL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        OL.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        OL.setText("Odinary Level Examination");
        OL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        OL.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        OL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OLActionPerformed(evt);
            }
        });
        jPanel1.add(OL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        Scship.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Scship.setText("Scholorship-05");
        Scship.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Scship.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Scship.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScshipActionPerformed(evt);
            }
        });
        jPanel1.add(Scship, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        exams.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        exams.setSelected(true);
        exams.setText("Examinations");
        exams.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        exams.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        exams.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                examsActionPerformed(evt);
            }
        });
        jPanel1.add(exams, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setText("Grade");
        jPanel16.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, -1));

        gradeC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13" }));
        gradeC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeCActionPerformed(evt);
            }
        });
        jPanel16.add(gradeC, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 110, -1));

        TermC.setModel(new DefaultComboBoxModel<>(TERMS));
        jPanel16.add(TermC, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 110, -1));

        jLabel28.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel28.setText("Term");
        jPanel16.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 60, -1));

        jPanel1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 210, 90));

        jTabbedPane1.addTab("Examinations", jPanel1);

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2MouseExited(evt);
            }
        });

        jButton60.setText("Attach");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        jButton59.setText("Remove");
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });

        Right.setEditable(true);
        Right.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Student Qr Code", "Student image", "School badge", "School image" }));
        Right.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RightActionPerformed(evt);
            }
        });
        Right.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                RightKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Right, 0, 0, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(Right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jButton60)
                        .addGap(8, 8, 8)
                        .addComponent(jButton59)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RImage, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jCheckBox12.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jCheckBox12.setSelected(true);
        jCheckBox12.setText("Left/Bottom");
        jCheckBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox12ActionPerformed(evt);
            }
        });

        jCheckBox13.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jCheckBox13.setSelected(true);
        jCheckBox13.setText("Right/Top");
        jCheckBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox13ActionPerformed(evt);
            }
        });

        jButton8.setText("Attach");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Remove");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        Left.setEditable(true);
        Left.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        Left.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Student image", "Student Qr Code", "School badge", "School image" }));
        Left.setRequestFocusEnabled(true);
        Left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeftActionPerformed(evt);
            }
        });
        Left.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                LeftKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(LImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LImage, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jButton8)
                        .addGap(8, 8, 8)
                        .addComponent(jButton9))))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox12)
                    .addComponent(jCheckBox13)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jCheckBox12)
                .addGap(5, 5, 5)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jCheckBox13)
                .addGap(5, 5, 5)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        jTabbedPane1.addTab("Images", jPanel2);

        jButton17.setText("Remove");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton16.setText("Add");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        PD_title.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Title"
            }
        ));
        PD_title.setRowMargin(3);
        PD_title.getTableHeader().setReorderingAllowed(false);
        PD_title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PD_titleMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(PD_title);

        jButton63.setText("▼");
        jButton63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton63ActionPerformed(evt);
            }
        });

        jLabel3.setText("Title");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton17)
                .addGap(4, 4, 4)
                .addComponent(jButton16))
            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton63)
                        .addComponent(jLabel3))
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jButton62.setText("Remove");
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });

        jButton15.setText("Add");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        PD_desc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Field Name", "Value"
            }
        ));
        PD_desc.getTableHeader().setReorderingAllowed(false);
        PD_desc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PD_descMouseClicked(evt);
            }
        });
        jScrollPane21.setViewportView(PD_desc);

        jLabel129.setText("Description");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel129)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton62)
                .addGap(4, 4, 4)
                .addComponent(jButton15))
            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel129))
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jButton64.setText("Remove");
        jButton64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton64ActionPerformed(evt);
            }
        });

        jButton66.setText("Add");
        jButton66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton66ActionPerformed(evt);
            }
        });

        PD_summery.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Field Name", "Value"
            }
        ));
        PD_summery.getTableHeader().setReorderingAllowed(false);
        PD_summery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PD_summeryMouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(PD_summery);

        jLabel212.setText("Summery");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel212)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton64)
                .addGap(4, 4, 4)
                .addComponent(jButton66))
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton64, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel212))
                    .addComponent(jButton66, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
        );

        PrintButton.setText("Print");
        PrintButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PrintButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PrintButtonMouseEntered(evt);
            }
        });
        PrintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintButtonActionPerformed(evt);
            }
        });

        PrintButton1.setText("Print and send");
        PrintButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PrintButton1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PrintButton1MouseEntered(evt);
            }
        });
        PrintButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintButton1ActionPerformed(evt);
            }
        });

        jButton1.setText("?");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PrintButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PrintButton1)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PrintButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PrintButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Marks Report(Single)", "Marks Report(Multiple)", "ID Card", "Students Details(Single)", "Students Details(Multyple)" }));
        jComboBox1.setToolTipText("Document type");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel130.setText("Admission Number");

        jLabel128.setText("File Name");

        FileName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        FileName.setText("{id}-{name}");
        FileName.setToolTipText("File name");

        idbox1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        idbox1.setText("{id}");
        idbox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idbox1ActionPerformed(evt);
            }
        });
        idbox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idbox1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idbox1KeyReleased(evt);
            }
        });

        jLabel127.setText("File Path");

        SaveDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveDirActionPerformed(evt);
            }
        });

        jButton10.setText("SELECT");
        jButton10.setBorder(null);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        FileExtention.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        FileExtention.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pdf", "Xlsx", "Rtf", "Html", "Xml" }));

        jLabel210.setText("Report type");

        jLabel211.setText("File type");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FileExtention, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, 0, 244, Short.MAX_VALUE)
                            .addComponent(idbox1)))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel130))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel128))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel127))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel210))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel211)))
                .addGap(14, 14, 14))
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel28Layout.createSequentialGroup()
                        .addComponent(FileName)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(SaveDir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel130)
                .addGap(4, 4, 4)
                .addComponent(idbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel210)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel211)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FileExtention, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel128)
                .addGap(1, 1, 1)
                .addComponent(FileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel127)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SaveDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void selectInExams(JCheckBox cb) {
        for (JCheckBox cb1 : getMarksCB()) {
            if (cb.equals(cb1)) {
                continue;
            }
            cb1.setSelected(false);
        }
    }

    private JCheckBox[] getMarksCB() {
        JCheckBox CheckBox[] = {OL, AL, Scship, exams};
        return CheckBox;
    }

    void loadTables(JTable table, String sqlTable, Connection conn) {
        CommonUi.tableload("SELECT * FROM \"" + sqlTable + "\"", table, conn);
    }

    public static String getTableModel(JTable t) {
        String d = "";
        for (int i = 0; i < t.getRowCount(); i++) {
            for (int j = 0; j < t.getColumnCount(); j++) {
                d = d.concat((t.getValueAt(i, j) == null ? "" : t.getValueAt(i, j)) + (j == t.getColumnCount() - 1 ? "\n" : "\t"));
            }
        }
        return d;
    }

    public static TableModel getTableModel(String t, Object[] h) {
        Object[][] data = t == null ? new Object[][]{{null, null}} : new Object[t.split("\n").length][t.split("\n")[0].split("\t").length];
        if (t != null) {
            int r = 0;
            for (String row : t.split("\n")) {
                int c = 0;
                for (String col : row.split("\t")) {
                    data[r][c] = col;
                    c++;
                }
                r++;
            }
        }
        DefaultTableModel m = new DefaultTableModel(data, h);
        return m;
    }

    public void setTablesDefaults() {
        PD_title.setModel(getTableModel(Commons.getDefault(gsi(jComboBox1) + "_title", DBconnect.CONN), new Object[]{"Title"}));
        PD_desc.setModel(getTableModel(Commons.getDefault(gsi(jComboBox1) + "_description", DBconnect.CONN), gsi(jComboBox1).equals("ID Card") ? new Object[]{"Front field", "Back field", "Back description"} : new Object[]{"Field Name", "Value"}));
        PD_summery.setModel(getTableModel(Commons.getDefault(gsi(jComboBox1) + "_summery", DBconnect.CONN), new Object[]{"Field Name", "Value"}));
    }

    public void updateTableDefaults() {
        Commons.setDefault(gsi(jComboBox1) + "_title", getTableModel(PD_title), DBconnect.CONN);
        Commons.setDefault(gsi(jComboBox1) + "_description", getTableModel(PD_desc), DBconnect.CONN);
        Commons.setDefault(gsi(jComboBox1) + "_summery", getTableModel(PD_summery), DBconnect.CONN);
    }

    void loadTables() {
        loadTables(PD_title, "Title", CONN);
        loadTables(PD_desc, "Description", CONN);
        loadTables(PD_summery, "Summery", CONN);
    }

    void setEditingTable(JTable table) {
        JTable[] t = new JTable[]{PD_title, PD_summery, PD_desc};
        for (JTable jTable : t) {
            if (table.equals(jTable)) {
                continue;
            }
            jTable.getSelectionModel().setSelectionInterval(-1, -1);
        }
    }

    void selectReport(String selection) {
        HashMap<JTable, String> m = new HashMap();
        switch (selection) {
            case "Marks Report(Single)":
            case "Marks Report(Multiple)":
                m.put(PD_title, "Title");
                m.put(PD_summery, "Summery");
                m.put(PD_desc, "Description");
                break;
            case "Common Detailes":
                break;
            case "ID Card":
                m.put(PD_title, "Title");
                m.put(PD_summery, "Summery");
                m.put(PD_desc, "Identy Card");
                break;
        }
        m.entrySet().forEach((set) -> {
            JTable Jtable = set.getKey();
            String sqlTable = set.getValue();
            loadTables(Jtable, sqlTable, CONN);
        });
//marks Report(Single)
//Marks Report(Multiple)
//Common Detailes
//Common Detailes
//Caracter Certificate
//Leaving Report
//ID Card
    }

    public com.Printing.Marks printMarksSingle(String[] title, String rImage, String lImage, String[][] desc, String id, int grade, String term, String[][] Summery, String outFile, Extention ext) throws Exception {
        com.Printing.Marks m = printMarksSingle(title, desc, id, grade, term, Summery, outFile, ext);
        m.setJrxml(com.Printing.Marks.SINGLE_WITHIMAGES);
        com.Printing.Print.deleteAllData("Images", CONN);
        Sql.execute("INSERT INTO Images VALUES('" + rImage + "','" + lImage + "')", CONN);
        return m;
    }

    public com.Printing.Marks printMarksSingle(String[] title, String[][] desc, String id, int grade, String term, String[][] Summery, String outFile, Extention ext) throws Exception {
        com.Printing.Print.deleteAllData("Description", CONN);
        com.Printing.Print.deleteAllData("Summery", CONN);
        com.Printing.Print.deleteAllData("Title", CONN);
        com.Printing.Print.deleteAllData("Marks", CONN);
        for (String t : title) {
            Sql.execute("INSERT INTO Title VALUES('" + getDecrypted(t, id) + "')", CONN);
        }
        for (String[] s : desc) {
            Sql.execute("INSERT INTO Description VALUES('" + getDecrypted(String.join("','", s), id) + "')", CONN);
        }

        for (String[] s : Summery) {
            Sql.execute("INSERT INTO Summery VALUES('" + getDecrypted(String.join("','", s), id) + "')", CONN);
        }
        com.MarksReport.Marks mp = new com.MarksReport.Marks(id, term, grade);
        List<Object> subs = new ArrayList<>();
        subs.addAll(Arrays.asList(com.MarksReport.Marks.getSubjects(grade)));
        List<Object> grades = new ArrayList<>();
        for (char h : com.MarksReport.Marks.getGrades(mp.getMarks())) {
            grades.add(h);
        }
        List<Object> marks = new ArrayList<>();
        for (Double mark : mp.getMarks()) {
            marks.add(mark);
        }
        for (int i = 0; i < marks.size(); i++) {
            Sql.execute("INSERT INTO Marks VALUES('" + subs.get(i) + "','" + marks.get(i) + "','" + grades.get(i) + "')", CONN);
        }
        com.Printing.Marks m = new Marks(com.Printing.Marks.SINGLE_WITHOUTIMAGES, null, ext);
        m.setOutputFile(outFile);
        m.setTitle(title, CONN);
        return m;
    }

    public com.Printing.Identy printIdenty(String title, String topImage, String bottomImage, String QrCode, String[][] desc, String out, Extention ext) throws Exception {
        HashMap map = new HashMap();
        map.put("APPLICATION_DIR", Commons.getApplicationDir());
        map.put("QR_CODE", QrCode);
        com.Printing.Print.deleteAllData("Title", CONN);
        com.Printing.Print.deleteAllData("Images", CONN);
        com.Printing.Print.deleteAllData("Identy Card", CONN);
        Sql.execute("INSERT INTO Images VALUES('" + topImage + "','" + bottomImage + "')", CONN);
        Sql.execute("INSERT INTO Title VALUES('" + title + "')", CONN);
        for (String[] s : desc) {
            Sql.execute("INSERT INTO `Identy Card` VALUES('" + String.join("','", s) + "')", CONN);
        }
        com.Printing.Identy ic = new Identy(Identy.IDENTY_CARD, map, ext);
        ic.setOutputFile(out);
        return ic;
    }

    @Deprecated
    public static void createQRCode(String id, String out, BarcodeFormat bf) throws WriterException, IOException {
        Student.createQRCode(id, out, bf);
    }

    public com.Printing.Marks printMarksMulti(String[] title, String[] IDs, int grade, String term, String outFile, Extention ext) throws JRException, Exception {
        String[] AllSubjects = null;
        String table = null;
        if (grade < 6) {
            AllSubjects = com.MarksReport.Subjects.PRIMARY;
            table = "Grade 01-05";
        } else if (grade < 10) {
            AllSubjects = com.MarksReport.Subjects.JUNIOR;
            table = "Grade 06-09";
        } else if (grade < 12) {
            AllSubjects = com.MarksReport.Subjects.ODINARYLEVEL;
            table = "Grade 10-11";
        } else if (grade < 14) {
            AllSubjects = com.MarksReport.Subjects.ADVANCEDLEVEL;
            table = "Grade 12-13";
        } else {
            throw new IllegalArgumentException("Grade connot be above 13");
        }
        List<List<Object>> sqlArr = new ArrayList<>();
        for (int i = 0; i < IDs.length; i++) {
            sqlArr.add(new ArrayList<>());
            com.MarksReport.Marks marks = new com.MarksReport.Marks(IDs[i], term, grade);
            sqlArr.get(i).add(IDs[i]);
            sqlArr.get(i).add(Student.getName(IDs[i]));
            for (Double m : marks.getMarks()) {
                sqlArr.get(i).add(getps(m));
            }
            sqlArr.get(i).add(getps(marks.getTotal()));
            sqlArr.get(i).add(getps(marks.getAverage()));
        }
        System.out.println(Arrays.deepToString(sqlArr.toArray()));
        com.Printing.Print.deleteAllData(table, CONN);
        Sql.insertData("INSERT INTO ", sqlArr, table, CONN);
        HashMap<String, Object> map = new HashMap();
        map.put("MARKS_DIR", com.Printing.Marks.getXlsx(grade));
        map.put("APPLICATION_DIR", new File("src").getAbsolutePath());
        com.Printing.Marks m = new Marks(com.Printing.Marks.XLSX, map, ext);
        m.setOutputFile(outFile);
        m.setTitle(title, CONN);
        return m;
    }

    void printWhole(String id, String selection, String out, Extention ext) throws Exception {
        String title[];
        String desc[][];
        String Smr[][];
        int grade = Integer.parseInt(String.copyValueOf(gradeC.getSelectedItem().toString().toCharArray(), 0, 2));
        String term = TermC.getSelectedItem().toString();
        File rImage = setImage(RImage, gsi(Right), id);
        File lImage = setImage(LImage, gsi(Left), id);
        File barCode = new File(getQRCodeName(id));
        createQRCode(id, getQRCodeName(id), BarcodeFormat.QR_CODE);
//            System.out.println("Read = " + BarCode.read(barCode.getAbsolutePath()));
        desc = getDecrypted(CommonUi.getArray(PD_desc), id);
        Smr = getDecrypted(CommonUi.getArray(PD_summery), id);
        title = getDecrypted(CommonUi.getArray(0, PD_title), id);
        boolean Confirm;

        File file = new File(out);
        if (file.exists()) {
            getToolkit().beep();
            Confirm = JOptionPane.showConfirmDialog(null, "The file already exists!\nDo you want to replace it!", "File Exists", 0, 2) == 0;
        } else {
            Confirm = true;
        }
        com.Printing.Marks m;
        if (Confirm) {
            switch (selection) {
                case "Marks Report(Single)" : {
                    if (jCheckBox12.isSelected() || jCheckBox13.isSelected()) {
                        m = printMarksSingle(title, jCheckBox12.isSelected() ? lImage.getAbsolutePath() : "", jCheckBox13.isSelected() ? rImage.getAbsolutePath() : "", desc, id, grade, term, Smr, out, ext);
                    } else {
                        m = printMarksSingle(title, desc, id, grade, term, Smr, out, ext);
                    }
                    m.Print();
                }
                case "Marks Report(Multiple)" : {
                    m = printMarksMulti(title, CommonUi.getSelectedArray(0, table), grade, term, out, ext);
                    m.Print();
                }
                case "ID Card" : {
                    Identy idc = printIdenty(title[0], jCheckBox12.isSelected() ? lImage.getAbsolutePath() : "", jCheckBox13.isSelected() ? rImage.getAbsolutePath() : "", barCode.getAbsolutePath(), desc, out, ext);
                    idc.Print();
                }
                case "Students Details(Multiple)" : {
                    for (Object o : DbUtils.resultSetToNestedList(DBconnect.CONN.prepareStatement("SELECT * FROM student WHERE `Admission No.`='" + String.join("' OR `Admission No.`='", CommonUi.getSelectedArray(0, table)) + "'").executeQuery())) {
                        List<Object> o1 = (List<Object>) o;
                        Sql.execute("INSERT INTO student VALUES('" + Commons.join("','", o1) + "')", CONN);
                    }
                    HashMap map = new HashMap();
                    File f2 = File.createTempFile("SMS Temps", ".jpg");
                    f2.deleteOnExit();
                    ImageIO.write(ImageWriter.getImage(school.getBadge()), "jpg", f2);
                    map.put("SCHOOL_NAME", school.getName());
                    map.put("BADGE", f2.getAbsolutePath());
                    String jrxml = ext.equals(Extention.XLSX) ? Ireport.STUDENT_DETAILS_XLSX : Ireport.STUDENT_DETAILS;
                    Print pr = new Print(jrxml, map, ext);
                    pr.setOutputFile(SaveDir.getText() + "\\" + selection);
                    pr.Print();
                }
                case "Students Details(Single)" : {
                    boolean b = false;
                    File f2;
                    if (jCheckBox12.isSelected()) {
                        f2 = File.createTempFile("SMS Temps", ".jpg");
                        f2.deleteOnExit();
                        ImageIO.write(ImageWriter.getImage(school.getBadge()), "jpg", f2);
                        b = true;
                    }
                    if (jCheckBox13.isSelected()) {
                        f2 = File.createTempFile("SMS Temps", ".jpg");
                        f2.deleteOnExit();
                        ImageIO.write(ImageWriter.getImage(school.getBadge()), "jpg", f2);
                        b = true;
                    }
                    com.Printing.Print.deleteAllData("Description", CONN);
                    com.Printing.Print.deleteAllData("Title", CONN);
                    for (String t : title) {
                        Sql.execute("INSERT INTO Title VALUES('" + getDecrypted(t, id) + "')", CONN);
                    }
                    for (String[] s : desc) {
                        Sql.execute("INSERT INTO Description VALUES('" + getDecrypted(String.join("','", s), id) + "')", CONN);
                    }
                    String jrxml = ext.equals(Extention.XLSX) ? Ireport.SINGLE_STUDENT_DETAILS_XLSX : b ? Ireport.SINGLE_STUDENT_DETAILS : Ireport.SINGLE_STUDENT_DETAILS_NOIMAGE;
                    HashMap map = new HashMap();
                    map.put("SUBREPORT_DIR", new File("src").getAbsolutePath());
                    Print pr = new Print(jrxml, map, ext);
                    pr.setOutputFile(SaveDir.getText() + "\\" + getDecrypted(FileName.getText(), id));
                    pr.Print();
                }
            }

//                TrayMessage msg = new TrayMessage(("Could not save" + getDecrypted(FileName.getText(), idbox1.getText()) + "." + FileExtention.getSelectedItem().toString()), AppConfig.APPICON_50, AppConfig.APPNAME, "Error", TrayIcon.MessageType.ERROR);
//                try {
//                    msg.display();
//                } catch (AWTException | MalformedURLException ex1) {
//                    Logger.getLogger(Print_frame.class.getName()).log(Level.SEVERE, null, ex1);
//                }
        }
    }

    public void load(String id) {
        load(new String[]{id});
    }

    public void load(String[] ids) {
        selectedIds = ids;
        String id = "";
        String fileName = "";
        if (ids.length == 1) {
            id = ids[0];
            fileName = id + "-" + Student.getName(id);
            setImage(LImage, gsi(Left), id);
            setImage(RImage, gsi(Right), id);
        } else if (ids.length > 1) {
            id = "{id}";
            fileName = "{id}-{name}";
            setImage(LImage, gsi(Left), ids[0]);
            setImage(RImage, gsi(Right), ids[0]);
        }
        idbox1.setText(id);
        FileName.setText(fileName);
    }

    private void ALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ALActionPerformed
        selectInExams(AL);        // TODO add your handling code here:
    }//GEN-LAST:event_ALActionPerformed

    private void OLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OLActionPerformed
        selectInExams(OL);
    }//GEN-LAST:event_OLActionPerformed

    private void ScshipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScshipActionPerformed
        selectInExams(Scship);        // TODO add your handling code here:
    }//GEN-LAST:event_ScshipActionPerformed

    private void examsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examsActionPerformed
        boolean b = exams.isSelected() && exams.isEnabled();
        for (Component c : jPanel16.getComponents()) {
            c.setEnabled(b);
        }
        selectInExams(exams);
    }//GEN-LAST:event_examsActionPerformed

    private void gradeCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeCActionPerformed

    }//GEN-LAST:event_gradeCActionPerformed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered

    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
        JPanel p = jPanel1;
        p.setBorder(null);
    }//GEN-LAST:event_jPanel1MouseExited

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setApproveButtonText("Select");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("All Image Types", "png", "jpg", "jpeg", "gif");
        jfc.setFileFilter(filter);
        int approve = jfc.showSaveDialog(null);
        switch (approve) {
            case JFileChooser.APPROVE_OPTION:
                File f = jfc.getSelectedFile();
                String CurrentPath = f.getAbsolutePath();
                RImage.setToolTipText(CurrentPath);
                RImage.setIcon(getImage(CurrentPath, RImage.getWidth(), RImage.getHeight()));
                break;
            case JFileChooser.CANCEL_OPTION:
                jfc.setVisible(false);
                break;
            case JFileChooser.ERROR_OPTION:
                jfc.setVisible(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        RImage.setToolTipText("");
        RImage.setIcon(null);
    }//GEN-LAST:event_jButton59ActionPerformed

    private void RightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RightActionPerformed
        try {
            File img = new File(getDecrypted(Right.getSelectedItem().toString(), selectedIds[0]));
            if (img.exists()) {
                CommonUi.setImage(img.getAbsolutePath(), RImage);
            } else {
                RImage.setIcon(null);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }//GEN-LAST:event_RightActionPerformed

    private void RightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RightKeyReleased

    }//GEN-LAST:event_RightKeyReleased

    private void jCheckBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox12ActionPerformed
        for (Component c : jPanel23.getComponents()) {
            c.setEnabled(jCheckBox12.isSelected());
        }
    }//GEN-LAST:event_jCheckBox12ActionPerformed

    private void jCheckBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox13ActionPerformed
        for (Component c : jPanel22.getComponents()) {
            c.setEnabled(jCheckBox13.isSelected());
        }
    }//GEN-LAST:event_jCheckBox13ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setApproveButtonText("Select");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("All Image Types", "png", "jpg", "jpeg", "gif");
        jfc.setFileFilter(filter);
        int approve = jfc.showSaveDialog(null);
        switch (approve) {
            case JFileChooser.APPROVE_OPTION:
                File f = jfc.getSelectedFile();
                String CurrentPath = f.getAbsolutePath();
                LImage.setToolTipText(CurrentPath);
                LImage.setIcon(getImage(CurrentPath, LImage.getWidth(), LImage.getHeight()));
                break;
            case JFileChooser.CANCEL_OPTION:
                jfc.setVisible(false);
                break;
            case JFileChooser.ERROR_OPTION:
                jfc.setVisible(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        LImage.setToolTipText("");
        LImage.setIcon(null);
    }//GEN-LAST:event_jButton9ActionPerformed

    public File setImage(JLabel l, String s, String id) {
        File r = null;
        try {
            File i = new File(s);
            ImageIcon img = null;
            switch (s) {
                case "Student image": {
                    if (Student.hasImage(id)) {
                        img = Student.getImage(id);
                    }
                }break;
                case "Student Qr Code":
                    img = Student.getQRCode(id);
                    break;
                case "school badge":
                    img = school.getBadge();
                    break;
                case "school image":
                    img = school.getProfileImage();
                    break;
                default: {
                    if (i.exists()) {
                        img = new ImageIcon(i.getAbsolutePath());
                    }
                }
            }
            if (!i.exists() && img != null) {
                r = File.createTempFile(s, ".jpg");
                r.deleteOnExit();
                BufferedImage originalImage = ImageWriter.getImage(img);
                BufferedImage newBufferedImage = new BufferedImage(
                        originalImage.getWidth(),
                        originalImage.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
                newBufferedImage.createGraphics()
                        .drawImage(originalImage,
                                0,
                                0,
                                Color.WHITE,
                                null);
                ImageIO.write(newBufferedImage, "jpg", r);
                CommonUi.setImage(img, l);
            } else {
                r = i;
            }
        } catch (IOException ex) {
            Logger.getLogger(Print_frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    private void LeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeftActionPerformed
        setImage(LImage, gsi(Left), selectedIds[0]);
    }//GEN-LAST:event_LeftActionPerformed

    private void LeftKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeftKeyReleased

    }//GEN-LAST:event_LeftKeyReleased

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered

    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseExited
        JPanel p = jPanel2;
        p.setBorder(null);
    }//GEN-LAST:event_jPanel2MouseExited

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        JTable table = PD_title;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int row : table.getSelectedRows()) {
            if (row == 0) {
                continue;
            }
            model.removeRow(row);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        JTable table = PD_title;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{""});
    }//GEN-LAST:event_jButton16ActionPerformed

    private void PD_titleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PD_titleMouseClicked
        setEditingTable(PD_title);
    }//GEN-LAST:event_PD_titleMouseClicked

    private void jButton63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton63ActionPerformed
        updateTableDefaults();
    }//GEN-LAST:event_jButton63ActionPerformed

    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
        JTable table = PD_desc;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int row : table.getSelectedRows()) {
            if (row == 0) {
                continue;
            }
            model.removeRow(row);
        }
    }//GEN-LAST:event_jButton62ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        JTable table = PD_desc;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"", ""});
    }//GEN-LAST:event_jButton15ActionPerformed

    private void PD_descMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PD_descMouseClicked
        setEditingTable(PD_desc);
    }//GEN-LAST:event_PD_descMouseClicked

    private void jButton64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton64ActionPerformed
        JTable table = PD_summery;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int row : table.getSelectedRows()) {
            if (row == 0) {
                continue;
            }
            model.removeRow(row);
        }
    }//GEN-LAST:event_jButton64ActionPerformed

    private void jButton66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton66ActionPerformed
        JTable table = PD_summery;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"", ""});
    }//GEN-LAST:event_jButton66ActionPerformed

    private void PD_summeryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PD_summeryMouseClicked
        setEditingTable(PD_summery);
    }//GEN-LAST:event_PD_summeryMouseClicked

    private void PrintButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrintButtonMouseClicked
    }//GEN-LAST:event_PrintButtonMouseClicked

    private void PrintButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrintButtonMouseEntered
    }//GEN-LAST:event_PrintButtonMouseEntered

    public void setInProgress(boolean t) {
        Container[] c = {jPanel24, jPanel25, jPanel26, jPanel27, jPanel28};
        for (Container container : c) {
            container.setEnabled(t);
            for (Component c1 : container.getComponents()) {
                c1.setEnabled(t);
            }
        }
    }

    private void PrintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintButtonActionPerformed
        updateTableDefaults();
        if (selectedIds != null) {
            setEnabled(false);
            new Thread(() -> {
                for (String Id : selectedIds) {
                    try {
                        load(Id);
                        String out = SaveDir.getText().concat("\\" + getDecrypted(FileName.getText(), idbox1.getText()));
                        String selection = jComboBox1.getSelectedItem().toString();
                        Extention ext = Extention.valueOf(FileExtention.getSelectedItem().toString().toUpperCase());
                        printWhole(Id, selection, out, ext);
                    } catch (Exception e) {
                        showMsg(e);
                    }
                    if (gsi(jComboBox1).equals("Marks Report(Multiple)") || gsi(jComboBox1).equals("Students Details(Multyple)")) {
                        break;
                    }
                }

                setEnabled(true);
            }
            ).start();
        }
    }//GEN-LAST:event_PrintButtonActionPerformed

    private void PrintButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrintButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_PrintButton1MouseClicked

    private void PrintButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrintButton1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_PrintButton1MouseEntered

    private void PrintButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintButton1ActionPerformed
        setEmail.show();
    }//GEN-LAST:event_PrintButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
//Marks Report(Single)
//Marks Report(Multiple)
//ID Card
//Students Detailes
//Caracter Certificate
//Leaving Report
        setTablesDefaults();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void idbox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idbox1ActionPerformed
        String id = idbox1.getText();
        if (!id.equals("")) {
            load(id);
        }
    }//GEN-LAST:event_idbox1ActionPerformed

    private void idbox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idbox1KeyPressed

    }//GEN-LAST:event_idbox1KeyPressed

    private void idbox1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idbox1KeyReleased

    }//GEN-LAST:event_idbox1KeyReleased

    private void SaveDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SaveDirActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save Location");
        if (SetDirectory != null) {
            jfc.setCurrentDirectory(new File(SetDirectory));
        }
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int approve = jfc.showDialog(null, "SELECT PATH");
        switch (approve) {
            case JFileChooser.APPROVE_OPTION:
                SetDirectory = jfc.getSelectedFile().toString();
                SaveDir.setText(SetDirectory);
                setDefault(Defaults.PD_SavePath, SetDirectory, DBconnect.CONN);
                jfc.setVisible(false);
                break;
            case JFileChooser.CANCEL_OPTION:
                jfc.setVisible(false);
                break;
            case JFileChooser.ERROR_OPTION:
                jfc.setVisible(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    public void updateAppDir() {
        try {
            Sql.execute("UPDATE others set Value='" + new File("").getAbsolutePath() + "' WHERE ID='APPLICATION_DIR'", CONN);
            Sql.execute("UPDATE others set Value='" + new File("src\\Print Details").getAbsolutePath() + "' WHERE ID='PRINTDETAILS_DIR'", CONN);

        } catch (SQLException ex) {
            Logger.getLogger(Print_frame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton68ActionPerformed
        String sel = jComboBox3.getSelectedItem().toString();
        String selS = "SetEmailSubject_".concat(sel).replace(' ', '_').replace(')', '_').replace('(', '_');
        String selB = "SetEmailBody_".concat(sel).replace(' ', '_').replace(')', '_').replace('(', '_');
        Commons.setDefault(selS, Email_subj.getText(), DBconnect.CONN);
        Commons.setDefault(selB, Email_body.getText(), DBconnect.CONN);
        setEmail.hide();
        String subject = Email_subj.getText();
        String Body = Email_body.getText();
        Extention ext = Extention.valueOf(FileExtention.getSelectedItem().toString().toUpperCase());
        selectedIds = CommonUi.getSelectedArray(0, table);
        for (String Id : selectedIds) {
            String out = SaveDir.getText().concat("\\" + getDecrypted(FileName.getText(), Id));
            String selection = jComboBox1.getSelectedItem().toString();
            if (gsi(jComboBox1).equals("Marks Report(Multiple)") || gsi(jComboBox1).equals("Student Details(Multiple)")) {
                try {
                    printWhole(Id, selection, out, ext);
                } catch (Exception ex) {
                    int option = JOptionPane.showConfirmDialog(this,
                            (selectedIds.length == 1 ? "Couldn't complete the printing prosses\n" + ex.getMessage()
                                    : "Couldn't complete the printing prosses\n" + ex.getMessage() + "\nDo you want to continue?"),
                            "Error",
                            (selectedIds.length == 1 ? JOptionPane.OK_CANCEL_OPTION
                                    : JOptionPane.CANCEL_OPTION),
                            JOptionPane.ERROR_MESSAGE);
                    if (option == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
            }
            try {
                Mail.sendWithAttachment(school.getEmail(), school.getEmailPassword(), getDecrypted(setEmail_Email.getText(), Id), getDecrypted(subject, Id), getDecrypted(Body, Id), out.concat("." + ext.getEXT()));
//                new TrayMessage("Mail sent", AppConfig.APPICON.getImage(), "Reciever: " + getDecrypted(setEmail_Email.getText(), Id)).display();
            } catch (Exception ex) {
                System.out.println(ex);
                int option = JOptionPane.showConfirmDialog(this,
                        (selectedIds.length == 1 ? "Couldn't complete the sending prosses\n" + ex.getMessage()
                                : "Couldn't complete the sending prosses\n" + ex.getMessage() + "\nDo you want to continue?"),
                        "Error",
                        (selectedIds.length == 1 ? JOptionPane.OK_CANCEL_OPTION
                                : JOptionPane.CANCEL_OPTION),
                        JOptionPane.ERROR_MESSAGE);
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
        }
    }//GEN-LAST:event_jButton68ActionPerformed

    private void jButton69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton69ActionPerformed
        setEmail.hide();
    }//GEN-LAST:event_jButton69ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        String sel = jComboBox3.getSelectedItem().toString();
        String selS = "SetEmailSubject_".concat(sel).replace(' ', '_').replace(')', '_').replace('(', '_');
        String selB = "SetEmailBody_".concat(sel).replace(' ', '_').replace(')', '_').replace('(', '_');
        String sbj = Commons.getDefault(selS, CONN);
        String body = Commons.getDefault(selB, CONN);
        Email_subj.setText(sbj);
        Email_body.setText(body);
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String s = "For character sequence of details of student use";
        for (int i = 0; i < UserFrame.dc.length; i++) {
            s = s.concat("\n" + UserFrame.dc[i].replace("{", "").replace("}", "") + " " + UserFrame.dc[i]);
        }
        Commons.showMsg(s);
    }//GEN-LAST:event_jButton1ActionPerformed
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Manage_frame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 1460, 680);
        JDesktopPane pane = new JDesktopPane();
        pane.setBounds(5, 5, 1450, 670);
        Selection s = new Selection();
        CurrentStudent cs = new CurrentStudent();
        s.setResizable(true);
        Print_frame hf = new Print_frame(cs, s);
        s.Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hf.selectedIds = s.getSelectedIds();
            }

            public void mouseReleased(MouseEvent e) {
                hf.selectedIds = s.getSelectedIds();
                hf.load(s.getSelectedIds());
            }
        });
        hf.setBounds(0, 0, 1050, 590);
        s.setBounds(1050, 0, 260, 590);
        cs.setBounds(0, 0, 260, 200);
        hf.setVisible(true);
        s.setVisible(true);
        pane.add(hf);
        pane.add(s);
        pane.add(cs);
        frame.getContentPane().add(pane);
        frame.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox AL;
    public javax.swing.JTextArea Email_body;
    public javax.swing.JTextArea Email_subj;
    public javax.swing.JComboBox<String> FileExtention;
    public javax.swing.JTextField FileName;
    public javax.swing.JLabel LImage;
    public javax.swing.JComboBox<String> Left;
    public javax.swing.JCheckBox OL;
    public javax.swing.JTable PD_desc;
    public javax.swing.JTable PD_summery;
    public javax.swing.JTable PD_title;
    public javax.swing.JButton PrintButton;
    public javax.swing.JButton PrintButton1;
    public javax.swing.JLabel RImage;
    public javax.swing.JComboBox<String> Right;
    public javax.swing.JTextField SaveDir;
    public javax.swing.JCheckBox Scship;
    public javax.swing.JComboBox<String> TermC;
    public javax.swing.JCheckBox exams;
    public javax.swing.JComboBox<String> gradeC;
    public javax.swing.JTextField idbox1;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    public javax.swing.JButton jButton15;
    public javax.swing.JButton jButton16;
    public javax.swing.JButton jButton17;
    public javax.swing.JButton jButton59;
    public javax.swing.JButton jButton60;
    public javax.swing.JButton jButton62;
    public javax.swing.JButton jButton63;
    public javax.swing.JButton jButton64;
    public javax.swing.JButton jButton66;
    public javax.swing.JButton jButton68;
    public javax.swing.JButton jButton69;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    public javax.swing.JCheckBox jCheckBox12;
    public javax.swing.JCheckBox jCheckBox13;
    public javax.swing.JComboBox<String> jComboBox1;
    public javax.swing.JComboBox<String> jComboBox3;
    public javax.swing.JLabel jLabel127;
    public javax.swing.JLabel jLabel128;
    public javax.swing.JLabel jLabel129;
    public javax.swing.JLabel jLabel130;
    public javax.swing.JLabel jLabel210;
    public javax.swing.JLabel jLabel211;
    public javax.swing.JLabel jLabel212;
    public javax.swing.JLabel jLabel213;
    public javax.swing.JLabel jLabel214;
    public javax.swing.JLabel jLabel215;
    public javax.swing.JLabel jLabel216;
    public javax.swing.JLabel jLabel217;
    public javax.swing.JLabel jLabel218;
    public javax.swing.JLabel jLabel219;
    public javax.swing.JLabel jLabel28;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel16;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel22;
    public javax.swing.JPanel jPanel23;
    public javax.swing.JPanel jPanel24;
    public javax.swing.JPanel jPanel25;
    public javax.swing.JPanel jPanel26;
    public javax.swing.JPanel jPanel27;
    public javax.swing.JPanel jPanel28;
    public javax.swing.JScrollPane jScrollPane19;
    public javax.swing.JScrollPane jScrollPane20;
    public javax.swing.JScrollPane jScrollPane21;
    public javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JDialog setEmail;
    public javax.swing.JTextField setEmail_Email;
    // End of variables declaration//GEN-END:variables
}
