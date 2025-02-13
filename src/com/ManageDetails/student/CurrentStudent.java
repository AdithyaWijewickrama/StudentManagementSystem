package com.ManageDetails.student;

import static com.Codes.Commons.*;
import com.ui.CommonUi;
import com.Codes.AppConfig;
import com.Codes.Commons;
import com.ui.frames.UserFrame;
import com.formdev.flatlaf.FlatLightLaf;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import static javax.swing.UIManager.getInt;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Dell
 */
public class CurrentStudent extends javax.swing.JInternalFrame implements StudentDetails {

    public String id = null;
    public ImageIcon barCode;
    public ImageIcon idImage;
    public int BARCODELENGHT = 85;
    public boolean hasImage;

    public CurrentStudent() {
        initComponents();
    }

    public CurrentStudent(String id) {
        initComponents();
        idImage = new ImageIcon(getClass().getResource("/Images/loader.gif"));
        this.id = id;
        jMenuItem1.setIcon((AppConfig.APPICON_32));
        jMenuItem3.setIcon((AppConfig.APPICON_32));
        validate();
        repaint();
        load();
    }

    class setImages extends Thread {

        String id;

        public setImages(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            si.setIcon(new ImageIcon(getClass().getResource("/Images/loader.gif")));
            hasImage = Student.hasImage(id);
            barCode = Student.getQRCode(id);
            setbarCodeImage();
            idImage = Student.getImage(id);
            setImage();
        }
    }

    public void load() {
        new setImages(id).start();
        Student.setData(id, identy, name, grade, subGrade, medium, birth, gender, telephone, address, guardian, email);
        age.setText(new Student(id).getAge() + "");
        Date_Added.setText(Commons.showDate(Student.getDateAdded(id)));
        Last_Update.setText(Commons.showDate(Student.getLatestUpdate(id)));
    }

    public void load(String id) {
        this.id = id;
        si.setIcon(new ImageIcon());
        this.barCode = Student.getQRCode(id);
        load();
    }

    public void setImage() {
        ImageIcon img = hasImage ? idImage : Student.EMPTY;
        if (si.isDisplayable()) {
            si.setIcon(si.getWidth() > si.getHeight() ? CommonUi.getVertFitImage(img, si.getHeight()) : CommonUi.getHorzFitImage(img, si.getWidth()));
        }
    }

    public void setbarCodeImage() {
        ImageIcon img = barCode;
        if (bci.isDisplayable()) {
            bci.setIcon((bci.getWidth() > bci.getHeight() ? CommonUi.getVertFitImage(img, bci.getHeight()) : CommonUi.getHorzFitImage(img, bci.getWidth())));
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageIcon getBarCode() {
        return barCode;
    }

    public void setBarCode(ImageIcon barCode) {
        this.barCode = barCode;
        setbarCodeImage();
    }

    public ImageIcon getIdImage() {
        return idImage;
    }

    public void setIdImage(ImageIcon idImage) {
        this.idImage = idImage;
    }

    @Override
    public String getAdmissionNo() {
        return id;
    }

    @Override
    public int getAge() {
        return Integer.parseInt(age.getText());
    }

    @Override
    public int getGrade() {
        return getInt(grade.getText());
    }

    @Override
    public String getSubgrade() {
        return subGrade.getText();
    }

    @Override
    public Date getDateOfBirth() {
        try {
            return new SimpleDateFormat("yyyy-mm-dd").parse(birth.getText());
        } catch (ParseException e) {
            return getCurrentDate();
        }
    }

    @Override
    public String getTelephone() {
        return telephone.getText();
    }

    @Override
    public String getGender() {
        return gender.getText();
    }

    @Override
    public String getGuardian() {
        return guardian.getText();
    }

    @Override
    public String getEmail() {
        return email.getText();
    }

    @Override
    public String getAddress() {
        return address.getText();
    }

    @Override
    public String getMedium() {
        return medium.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton70 = new javax.swing.JButton();
        barCodePopup = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        imagePopup = new javax.swing.JPopupMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        gender = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        telephone = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        guardian = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        birth = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        medium = new javax.swing.JTextField();
        grade = new javax.swing.JTextField();
        identy = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        subGrade = new javax.swing.JTextField();
        age = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        ageConf = new javax.swing.JLabel();
        Date_Added = new javax.swing.JTextField();
        Last_Update = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        si = new javax.swing.JLabel();
        bci = new javax.swing.JLabel();

        jButton70.setText("Generate");
        jButton70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton70ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Open with Image Viewer");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        barCodePopup.add(jMenuItem1);

        jMenuItem2.setText("Generate");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        barCodePopup.add(jMenuItem2);

        barCodePopup.getAccessibleContext().setAccessibleParent(bci);

        jMenuItem3.setText("Open with Image Viewer");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        imagePopup.add(jMenuItem3);

        imagePopup.getAccessibleContext().setAccessibleParent(si);

        setResizable(true);
        setTitle("Current student");
        setFrameIcon(null);
        setMaximumSize(new java.awt.Dimension(2147483647, 510));
        setMinimumSize(new java.awt.Dimension(420, 250));
        setPreferredSize(new java.awt.Dimension(420, 250));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButton5.setText("Copy");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 455));

        jLabel16.setForeground(new java.awt.Color(158, 163, 165));
        jLabel16.setText("Telephone");
        jLabel16.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel16.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel16.setPreferredSize(new java.awt.Dimension(200, 16));

        gender.setEditable(false);
        gender.setMargin(new java.awt.Insets(4, 6, 4, 2));
        gender.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel25.setForeground(new java.awt.Color(158, 163, 165));
        jLabel25.setText("Guardian");
        jLabel25.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel25.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel25.setPreferredSize(new java.awt.Dimension(200, 16));

        telephone.setEditable(false);
        telephone.setMargin(new java.awt.Insets(4, 6, 4, 2));
        telephone.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel13.setForeground(new java.awt.Color(158, 163, 165));
        jLabel13.setText("Full Name");
        jLabel13.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel13.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel13.setPreferredSize(new java.awt.Dimension(200, 16));

        name.setEditable(false);
        name.setMargin(new java.awt.Insets(4, 6, 4, 2));
        name.setPreferredSize(new java.awt.Dimension(200, 24));

        guardian.setEditable(false);
        guardian.setMargin(new java.awt.Insets(4, 6, 4, 2));
        guardian.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel12.setForeground(new java.awt.Color(158, 163, 165));
        jLabel12.setText("Admission Number");
        jLabel12.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel12.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel12.setPreferredSize(new java.awt.Dimension(200, 16));

        address.setEditable(false);
        address.setMargin(new java.awt.Insets(4, 6, 4, 2));
        address.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel19.setForeground(new java.awt.Color(158, 163, 165));
        jLabel19.setText("Date of birth");
        jLabel19.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel19.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel19.setPreferredSize(new java.awt.Dimension(200, 16));

        jLabel15.setForeground(new java.awt.Color(158, 163, 165));
        jLabel15.setText("Gender");
        jLabel15.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel15.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel15.setPreferredSize(new java.awt.Dimension(200, 16));

        jLabel14.setForeground(new java.awt.Color(158, 163, 165));
        jLabel14.setText("Grade");
        jLabel14.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel14.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel14.setPreferredSize(new java.awt.Dimension(200, 16));

        birth.setEditable(false);
        birth.setMargin(new java.awt.Insets(4, 6, 4, 2));
        birth.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel17.setForeground(new java.awt.Color(158, 163, 165));
        jLabel17.setText("Home address");
        jLabel17.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel17.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel17.setPreferredSize(new java.awt.Dimension(200, 16));

        medium.setEditable(false);
        medium.setMargin(new java.awt.Insets(4, 6, 4, 2));
        medium.setPreferredSize(new java.awt.Dimension(200, 24));

        grade.setEditable(false);
        grade.setMargin(new java.awt.Insets(4, 6, 4, 2));
        grade.setPreferredSize(new java.awt.Dimension(200, 24));

        identy.setEditable(false);
        identy.setMargin(new java.awt.Insets(4, 6, 4, 2));
        identy.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel9.setForeground(new java.awt.Color(158, 163, 165));
        jLabel9.setText("Medium");
        jLabel9.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel9.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel9.setPreferredSize(new java.awt.Dimension(200, 16));

        jLabel26.setForeground(new java.awt.Color(158, 163, 165));
        jLabel26.setText("Email");
        jLabel26.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel26.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel26.setPreferredSize(new java.awt.Dimension(200, 16));

        email.setEditable(false);
        email.setMargin(new java.awt.Insets(4, 6, 4, 2));
        email.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel18.setForeground(new java.awt.Color(158, 163, 165));
        jLabel18.setText("Sub grade");
        jLabel18.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel18.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel18.setPreferredSize(new java.awt.Dimension(200, 16));

        subGrade.setEditable(false);
        subGrade.setMargin(new java.awt.Insets(4, 6, 4, 2));
        subGrade.setPreferredSize(new java.awt.Dimension(200, 24));

        age.setEditable(false);
        age.setMargin(new java.awt.Insets(4, 6, 4, 2));
        age.setPreferredSize(new java.awt.Dimension(200, 24));

        jLabel20.setForeground(new java.awt.Color(158, 163, 165));
        jLabel20.setText("Age");
        jLabel20.setMaximumSize(new java.awt.Dimension(200, 16));
        jLabel20.setMinimumSize(new java.awt.Dimension(200, 16));
        jLabel20.setPreferredSize(new java.awt.Dimension(200, 16));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(birth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(telephone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(medium, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(identy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(guardian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(grade, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(subGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ageConf, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 80, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(identy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subGrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageConf, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(medium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(telephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(guardian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        Date_Added.setEditable(false);
        Date_Added.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        Date_Added.setToolTipText("Date Added");
        Date_Added.setBorder(null);
        Date_Added.setMargin(new java.awt.Insets(4, 6, 4, 2));

        Last_Update.setEditable(false);
        Last_Update.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        Last_Update.setToolTipText("Last Update");
        Last_Update.setBorder(null);
        Last_Update.setMargin(new java.awt.Insets(4, 6, 4, 2));

        jLabel1.setText("Date added");

        jLabel2.setText("Last update");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Last_Update)
                    .addComponent(Date_Added, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Date_Added, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Last_Update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        jPanel2.setMaximumSize(new java.awt.Dimension(327, 327));
        jPanel2.setMinimumSize(new java.awt.Dimension(85, 205));
        jPanel2.setPreferredSize(new java.awt.Dimension(95, 205));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        si.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        si.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loader.gif"))); // NOI18N
        si.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        si.setMaximumSize(new java.awt.Dimension(500, 500));
        si.setMinimumSize(new java.awt.Dimension(85, 105));
        si.setPreferredSize(new java.awt.Dimension(85, 105));
        si.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                siMouseReleased(evt);
            }
        });
        jPanel2.add(si);

        bci.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bci.setMaximumSize(new java.awt.Dimension(500, 500));
        bci.setMinimumSize(new java.awt.Dimension(85, 85));
        bci.setPreferredSize(new java.awt.Dimension(85, 85));
        bci.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bciMouseReleased(evt);
            }
        });
        jPanel2.add(bci);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String Copy = "";
        for (int i = 0; i < getTextFieleds().length; i++) {
            Copy = Copy.concat(getTextFieleds()[i].getText() + "\n");
        }
        copyToClipboard(Copy);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton70ActionPerformed
        File path = new File(Student.getQRCodeName(id));
        try {
            Student.createQRCode(id, path.getAbsolutePath(), BarcodeFormat.QR_CODE);
            bci.setIcon(getImage(idImage, bci.getWidth(), bci.getHeight()));
        } catch (WriterException | IOException ex) {
            Logger.getLogger(UserFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton70ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            Student.createQRCode(id, Student.getQRCodeName(id), BarcodeFormat.QR_CODE);
            this.barCode = Student.getQRCode(id);
        } catch (WriterException | IOException ex) {
            Logger.getLogger(CurrentStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (idImage == null) {
            showMsg(id + " has no image uploaded");
        } else {
            
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void bciMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bciMouseReleased
        if (evt.isPopupTrigger()) {
            barCodePopup.show(bci, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_bciMouseReleased

    private void siMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_siMouseReleased
        if (evt.isPopupTrigger()) {
            imagePopup.show(si, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_siMouseReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (id != null) {
            if (idImage != null) {
                setImage();
            }
            setbarCodeImage();
        }
    }//GEN-LAST:event_formComponentResized
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CurrentStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1100, 600);
        JDesktopPane pane = new JDesktopPane();
        CurrentStudent hf = new CurrentStudent("1141");
        pane.setBounds(100, 100, 1000, 500);
        JButton btn = new JButton("New Currentstudent");
        btn.setBounds(0, 0, 100, 25);
        frame.add(btn);
        frame.add(pane);
        btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hf.load("1494");
                System.out.println("Loaded");
            }
        });
        pane.add(hf);
        hf.setBounds(10, 10, 1050, 560);
        hf.setVisible(true);
        frame.setVisible(true);
        frame.validate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Date_Added;
    private javax.swing.JTextField Last_Update;
    private javax.swing.JTextField address;
    private javax.swing.JTextField age;
    private javax.swing.JLabel ageConf;
    private javax.swing.JPopupMenu barCodePopup;
    private javax.swing.JLabel bci;
    private javax.swing.JTextField birth;
    private javax.swing.JTextField email;
    private javax.swing.JTextField gender;
    private javax.swing.JTextField grade;
    private javax.swing.JTextField guardian;
    private javax.swing.JTextField identy;
    private javax.swing.JPopupMenu imagePopup;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton70;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField medium;
    private javax.swing.JTextField name;
    private javax.swing.JLabel si;
    private javax.swing.JTextField subGrade;
    private javax.swing.JTextField telephone;
    // End of variables declaration//GEN-END:variables

    private JTextField[] getTextFieleds() {
        return new JTextField[]{
            identy, name, age, grade, subGrade, medium, birth, gender, telephone, address, guardian, email
        };
    }
}
