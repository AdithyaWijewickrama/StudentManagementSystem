package com.ui.internalFrames;

import com.ManageDetails.student.NewStudent;
import com.ManageDetails.student.DeleteStudent;
import com.ManageDetails.student.UpdateStudent;
import com.ManageDetails.student.Student;
import Standard.array;
import static Standard.array.isEqual;
import com.Codes.Commons;
import com.Database.Sql;
import com.ui.components.TextField;
import com.ui.CommonUi;
import static com.ui.CommonUi.getToolBarColor;
import static com.ui.CommonUi.gsi;
import com.Codes.AppConfig;
import com.Communication.TrayMessage;
import com.Main.Defaults;
import static com.ui.frames.UserFrame.ID_NO;
import static com.ui.frames.UserFrame.getBorW;
import com.Main.Selection;
import com.ui.dialogs.security.PasswordOptions;
import com.Database.DBconnect;
import com.ManageDetails.Columns;
import com.ManageDetails.Images;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.HeadlessException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static com.Database.Sql.getCoveredTable;
import com.ui.frames.UserLoginFrame;
import com.ui.CommonUi;
import com.ui.components.TextField;
import java.awt.event.MouseAdapter;

public class Manage_frame extends javax.swing.JInternalFrame {

    public JInternalFrame currentFrame;
    public String currentImagePath = null;
    public Selection selection;
    public static final int FRAMEWIDTH = 970;
    public static final int FRAMEHEIGHT = 530;
    public static Connection CONN = DBconnect.CONN;
    private String gender = "Male";
    private Object lastImagePath;
    public String[] selectedIds;
    public JButton[] buttonGroup;
    public String SYSTEMIMAGE = "System generated image";
    public static final String DEFVALS = "<different values>";
    Type current = Type.SELECTION;

    public Manage_frame(Selection selectionFrame) {
        new File(SYSTEMIMAGE + ".png").deleteOnExit();
        initComponents();
        selection = selectionFrame;
        selection.setResizable(true);
        currentFrame = selection;
        CommonUi.setOkButton(aboutNext_btn);
        CommonUi.setOkButton(schoolNext_btn);
        CommonUi.setOkButton(register_btn);
        new TextField(phone).setAsStandardNumberField();
        male.setIgnoreRepaint(true);
        female.setIgnoreRepaint(false);
        update_btn.setEnabled(false);
        delete_btn.setEnabled(false);
        setSelection(selection);
        buttonGroup = new JButton[]{
            select_btn,
            Add_btn,
            update_btn,};
        selectFrame(current);
    }

    public void setSelection(Selection selectionFrame) {
        if (this.selection != null) {
            this.selection.dispose();
        }
        this.selection=selectionFrame;
        selection.setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
        selection.setFrameIcon(null);
        selection.Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                selectedIds = selection.getSelectedIds();
                update_btn.setEnabled(true);
                delete_btn.setEnabled(true);
            }
        });
    }

    void selectFrame(Type t) {
        mainBackground.validate();
        mainBackground.doLayout();
        JInternalFrame fr = null;
        mainBackground.removeAll();
        JButton btn = Add_btn;
        switch (t) {
            case ABOUT:
                fr = about;
                break;
            case IMAGESELECTION:
                fr = imageSelection;
                break;
            case ADD:
                btn = Add_btn;
                fr = about;
                break;
            case SCHOOLDETAILS:
                fr = school;
                break;
            case SELECTION:
                btn = select_btn;
                fr = selection;
                break;
        }
        if (!t.toString().contains("UPDATE")) {
            mainBackground.setPreferredSize(fr.getMinimumSize());
            int w = fr.preferredSize().width;
            int h = fr.preferredSize().height;
            fr.setVisible(true);
            if (!fr.equals(selection)) {
                CommonUi.genarateCenter(fr, mainBackground, w, h);
            }
            mainBackground.add(fr);
            currentFrame = fr;
            register_btn.setText("Register");
            ImageChoose_btn.setEnabled(true);
            imageEdit_btn.setEnabled(true);
        } else {
            btn = update_btn;
            JInternalFrame[] frs = new JInternalFrame[3];
            switch (t) {
                case UPDATE:
                case UPDATE_ABOUT:
                    frs[0] = about;
                    frs[1] = school;
                    frs[2] = imageSelection;
                    register_btn.setText("Update");
                    break;
                case UPDATE_IMAGESELECTION:
                    frs[2] = school;
                    frs[1] = about;
                    frs[0] = imageSelection;
                    break;
                case UPDATE_SCHOOLDETAILS:
                    frs[0] = school;
                    frs[2] = about;
                    frs[1] = imageSelection;
                    break;
            }
            mainBackground.add(frs[0]);
            mainBackground.add(frs[1]);
            mainBackground.add(frs[2]);
            mainBackground.setPreferredSize(new Dimension(frs[0].getWidth(), frs[0].getHeight() + frs[1].getHeight() + frs[2].getHeight() + 20));
            if (t.equals(Type.UPDATE)) {
                mainBackground.setLayout(new BoxLayout(mainBackground, BoxLayout.PAGE_AXIS));
                JScrollBar bar = new JScrollBar();
                bar.setUnitIncrement(500);
                jScrollPane1.setVerticalScrollBar(bar);
            }
        }
        btn.setSelected(true);
        for (JButton jButton : buttonGroup) {
            if (jButton.equals(btn)) {
                continue;
            }
            btn.setSelected(false);
        }
        mainBackground.validate();
        mainBackground.repaint();
    }

    public void updateFrameLoad(String[] ids) {
        String sql = "SELECT * FROM student WHERE ";
        String idCnt = "";
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            idCnt = idCnt.concat("" + getCoveredTable(ID_NO) + "='" + id + "' ").concat(i == ids.length - 1 ? "" : "OR ");
        }
        try {
            List<List<Object>> datalist = Sql.getNestedList(CONN.prepareStatement(sql.concat(idCnt)).executeQuery());
//            String[][] datalist =new String[datalist.size()][10];
            //<editor-fold defaultstate="collapsed" desc="Student data">
            boolean[] i = {isEqual(array.getColumn(datalist, 0)),
                isEqual(array.getColumn(datalist, 1)),
                isEqual(array.getColumn(datalist, 2)),
                isEqual(array.getColumn(datalist, 3)),
                isEqual(array.getColumn(datalist, 4)),
                isEqual(array.getColumn(datalist, 5)),
                isEqual(array.getColumn(datalist, 6)),
                isEqual(array.getColumn(datalist, 7)),
                isEqual(array.getColumn(datalist, 8)),
                isEqual(array.getColumn(datalist, 9)),
                isEqual(array.getColumn(datalist, 10))};
            List<Object> data = new ArrayList<>();
            data.add(i[Student.ID] ? datalist.get(0).get(Student.ID) : DEFVALS);
            data.add(i[Student.NAME] ? datalist.get(0).get(Student.NAME) : DEFVALS);
            data.add(i[Student.GRADE] ? datalist.get(0).get(Student.GRADE) : DEFVALS);
            data.add(i[Student.SUBGRADE] ? datalist.get(0).get(Student.SUBGRADE) : DEFVALS);
            data.add(i[Student.MEDIUM] ? datalist.get(0).get(Student.MEDIUM) : DEFVALS);
            data.add(i[Student.DATEOFBIRTH] ? Commons.showDate(new Date((long) datalist.get(0).get(Student.DATEOFBIRTH))) : DEFVALS);
            data.add(i[Student.GENDER] ? datalist.get(0).get(Student.GENDER) : DEFVALS);
            data.add(i[Student.HOMEADDRESS] ? datalist.get(0).get(Student.HOMEADDRESS) : DEFVALS);
            data.add(i[Student.TELEPHONE] ? datalist.get(0).get(Student.TELEPHONE) : DEFVALS);
            data.add(i[Student.GUARDIAN] ? datalist.get(0).get(Student.GUARDIAN) : DEFVALS);
            data.add(i[Student.EMAIL] ? datalist.get(0).get(Student.EMAIL) : DEFVALS);
//            System.out.println(Arrays.toString(data));
            List<Object> dateAdded = Sql.getColumn("SELECT Date FROM date_added WHERE " + idCnt.replace("Admission No.", "ID"), 0, CONN);
            List<Object> lastUpdate = Sql.getColumn("SELECT Date FROM last_update WHERE " + idCnt.replace("Admission No.", "ID"), 0, CONN);
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="seting Data">
            admission.setEditable(i[Student.ID]);
            setData(data);
            setGender((String) data.get(Student.GENDER));
            ((JTextField) this.dateAdded.getDateEditor().getUiComponent()).setText(isEqual(dateAdded) ? Commons.showDate(new Date((long) dateAdded.get(0))) : DEFVALS);
            try {
                ((JTextField) this.lastUpdate.getDateEditor().getUiComponent()).setText(isEqual(lastUpdate) ? Commons.showDate(new Date((long) lastUpdate.get(0))) : DEFVALS);
            } catch (Exception e) {

            }
            if (ids.length == 1) {
                if (Student.hasImage(ids[0])) {
                    imageLabel.setIcon(Images.LOADING);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            setImage(Student.getImage(ids[0]));
                            imagePath.setText(Student.getImageName(ids[0]));
                        }
                    }).start();
                } else {
                    setImage(null);
                }
                ImageChoose_btn.setEnabled(true);
                imageEdit_btn.setEnabled(true);
            } else {
                ImageChoose_btn.setEnabled(false);
                imageEdit_btn.setEnabled(false);
                imagePath.setText(DEFVALS);
                imageLabel.setIcon(null);
                backImage.setIcon(Student.EMPTY);
            }
//</editor-fold>
        } catch (Exception e) {
            Commons.showMsg(e);
        }
    }

    public void update(String[] ids) {
        ArrayList<Columns> cols = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        String[] d = getData();
        Columns[] c = Columns.values();
        if (ids.length == 1) {
            if (!d[0].equals(ids[0])) {
                cols.add(c[0]);
                data.add(d[0]);
            }
        }
        for (int i = 1; i < d.length; i++) {
            if (!d[i].equals(DEFVALS)) {
                cols.add(c[i]);
                data.add(d[i]);
            }
        }
        UpdateStudent us = new UpdateStudent(ids, cols, data, false);
        try {
            us.update(CommonUi.getText(lastUpdate));
            new TrayMessage("Updating info was successfull", "", AppConfig.APPICON_50.getImage(), MessageType.INFO).display();
        } catch (SQLException ex) {
            new TrayMessage("Failed to update info", ex.getLocalizedMessage(), AppConfig.APPICON_50.getImage(), MessageType.ERROR).display();
            Commons.showMsg(ex);
        }
    }

    public String[] getData() {
        return new String[]{
            admission.getText(),
            name.getText(),
            gsi(grade),
            gsi(subGrade),
            gsi(medium),
            CommonUi.getText(dateofbirth),
            gender,
            phone.getText(),
            address.getText(),
            gsi(guardian),
            email.getText()
        };
    }

    public void setData(List<Object> data) {
        admission.setText((String) data.get(0));
        name.setText((String) data.get(1));
        CommonUi.setText(grade, data.get(2) + "");
        CommonUi.setText(subGrade, data.get(3) + "");
        CommonUi.setText(medium, data.get(4) + "");
        CommonUi.setText(dateofbirth, data.get(5) + "");
        setGender(data.get(6) + "");
        phone.setText(data.get(7) + "");
        address.setText(data.get(8) + "");
        CommonUi.setText(guardian, data.get(9) + "");
        email.setText(data.get(10) + "");
    }

    public void setGender(String gend) {
        switch (gend) {
            case "Male":
                if (!male.isSelected()) {
                    male.doClick(0);
                }
                break;
            case "Female":
                if (!female.isSelected()) {
                    female.doClick(0);
                }
                break;
            case "Other":
                if (!other.isSelected()) {
                    other.doClick(0);
                }
                break;
            default:
                gender = DEFVALS;
                male.setSelected(false);
                female.setSelected(false);
                other.setSelected(false);
        }
    }

    public void setImage(ImageIcon img) {
        if (img != null) {
            if (img.getIconHeight() >0) {
                imageLabel.setIcon(img.getIconHeight() > img.getIconWidth() ? CommonUi.getVertFitImage(img, imageLabel.getHeight()) : CommonUi.getHorzFitImage(img, imageLabel.getWidth()));
                backImage.setIcon(CommonUi.getHorzFitImage(img, backImage.getWidth()));
                imagePath.setText("");
            }
        } else {
            imageLabel.setIcon(null);
            backImage.setIcon(Student.EMPTY);
            imagePath.setText("");
        }
    }

    public void showMessage(String msg, String subMsg, MessageType t) {
        setMessage(msg, subMsg, t).display();
    }

    public TrayMessage setMessage(String msg, String subMsg, MessageType t) {
        return new TrayMessage(msg, subMsg, AppConfig.APPICON_50.getImage(), t);
    }

    enum Type {
        ABOUT,
        SCHOOLDETAILS,
        IMAGESELECTION,
        UPDATE,
        UPDATE_ABOUT,
        UPDATE_SCHOOLDETAILS,
        UPDATE_IMAGESELECTION,
        ADD, SELECTION,
    }

    private void setFrame(Type t) {
        switch (t) {
            case ABOUT:
                break;
            case SCHOOLDETAILS:
                break;
            case UPDATE:
                schoolBack_btn.setText("Cancel");
                break;
            case IMAGESELECTION:
                break;
        }
    }

    public void newStudent() {
        try {
            CommonUi.clearAll(about);
            CommonUi.clearAll(school);
            CommonUi.clearAll(imageSelection);
            CommonUi.setImage(Student.EMPTY, imageLabel);
        } catch (Exception ex) {
            Commons.showMsg(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        about = new javax.swing.JInternalFrame();
        address = new javax.swing.JTextField();
        dateofbirth = new com.toedter.calendar.JDateChooser();
        male = new javax.swing.JRadioButton();
        female = new javax.swing.JRadioButton();
        other = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        aboutNext_btn = new javax.swing.JButton();
        aboutCancel_btn = new javax.swing.JButton();
        email = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        guardian = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        school = new javax.swing.JInternalFrame();
        jLabel35 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        subGrade = new javax.swing.JComboBox<>();
        medium = new javax.swing.JComboBox<>();
        jLabel119 = new javax.swing.JLabel();
        admission = new javax.swing.JTextField();
        grade = new javax.swing.JComboBox<>();
        schoolNext_btn = new javax.swing.JButton();
        schoolBack_btn = new javax.swing.JButton();
        dateAdded = new com.toedter.calendar.JDateChooser();
        jLabel40 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        lastUpdate = new com.toedter.calendar.JDateChooser();
        imageSelection = new javax.swing.JInternalFrame();
        imageLabel = new javax.swing.JLabel();
        backImage = new javax.swing.JLabel();
        ImageRemove_btn = new javax.swing.JButton();
        imagePath = new javax.swing.JTextField();
        register_btn = new javax.swing.JButton();
        imageBack_btn = new javax.swing.JButton();
        ImageChoose_btn = new javax.swing.JButton();
        imageEdit_btn = new javax.swing.JButton();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        Add_btn = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        select_btn = new javax.swing.JButton();
        clear_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainBackground = new javax.swing.JDesktopPane();

        about.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        about.setTitle("About");
        about.setFrameIcon(null);
        about.setMaximumSize(new java.awt.Dimension(490, 530));
        about.setMinimumSize(new java.awt.Dimension(490, 530));
        about.setNormalBounds(new java.awt.Rectangle(0, 0, 490, 530));
        about.setPreferredSize(new java.awt.Dimension(490, 530));
        about.setVisible(true);
        about.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aboutMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                aboutMouseReleased(evt);
            }
        });
        about.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        address.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        about.getContentPane().add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 450, 24));

        dateofbirth.setDateFormatString("yyyy-MM-dd");
        dateofbirth.setDoubleBuffered(false);
        dateofbirth.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        dateofbirth.setMaxSelectableDate(Commons.getCurrentDate());
        dateofbirth.setPreferredSize(new java.awt.Dimension(120, 25));
        dateofbirth.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateofbirthPropertyChange(evt);
            }
        });
        about.getContentPane().add(dateofbirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 170, -1));

        male.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        male.setText("MALE");
        male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleActionPerformed(evt);
            }
        });
        about.getContentPane().add(male, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        female.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        female.setText("FEMALE");
        female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleActionPerformed(evt);
            }
        });
        about.getContentPane().add(female, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, -1, -1));

        other.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        other.setText("OTHER");
        other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherActionPerformed(evt);
            }
        });
        about.getContentPane().add(other, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel34.setText("Full Name");
        about.getContentPane().add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel32.setText("Date of birth");
        about.getContentPane().add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 174, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel33.setText("Age");
        about.getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel26.setText("Gender");
        about.getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel30.setText("Telephone");
        about.getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 70, -1));

        name.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        about.getContentPane().add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 450, 24));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel36.setText("Home Address");
        about.getContentPane().add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 100, -1));

        phone.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        about.getContentPane().add(phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 450, 24));

        aboutNext_btn.setText("Next");
        aboutNext_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutNext_btnActionPerformed(evt);
            }
        });
        about.getContentPane().add(aboutNext_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 450, -1, -1));

        aboutCancel_btn.setText("Cancel");
        aboutCancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutCancel_btnActionPerformed(evt);
            }
        });
        about.getContentPane().add(aboutCancel_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        email.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        about.getContentPane().add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 450, 24));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel37.setText("Email");
        about.getContentPane().add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel39.setText("Guardian");
        about.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        guardian.setEditable(true);
        guardian.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        guardian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mother", "Father", "Orphanage", "Other" }));
        guardian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardianActionPerformed(evt);
            }
        });
        about.getContentPane().add(guardian, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));
        about.getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 180, -1));

        school.setTitle("School details");
        school.setFrameIcon(null);
        school.setMaximumSize(new java.awt.Dimension(435, 465));
        school.setMinimumSize(new java.awt.Dimension(435, 465));
        school.setNormalBounds(new java.awt.Rectangle(0, 0, 420, 465));
        school.setPreferredSize(new java.awt.Dimension(435, 465));
        school.setVisible(true);

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel35.setText("Medium");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel21.setText("Admission Number");

        subGrade.setEditable(true);
        subGrade.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        subGrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));

        medium.setEditable(true);
        medium.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        medium.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sinhala", "English", "Tamil", "French", "Japan", "Korean" }));
        medium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediumActionPerformed(evt);
            }
        });

        jLabel119.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel119.setText("Grade");

        admission.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        admission.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                admissionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                admissionKeyReleased(evt);
            }
        });

        grade.setEditable(true);
        grade.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        grade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));

        schoolNext_btn.setText("Next");
        schoolNext_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        schoolNext_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schoolNext_btnActionPerformed(evt);
            }
        });

        schoolBack_btn.setText("Back");
        schoolBack_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schoolBack_btnActionPerformed(evt);
            }
        });

        dateAdded.setDate(Commons.getCurrentDate());
        dateAdded.setDateFormatString("yyyy-mm-dd");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel40.setText("Date added");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/emptyImage.png"))); // NOI18N
        jLabel1.setAutoscrolls(true);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel41.setText("Last update");

        lastUpdate.setDate(Commons.getCurrentDate());
        lastUpdate.setDateFormatString("yyyy-mm-dd");

        javax.swing.GroupLayout schoolLayout = new javax.swing.GroupLayout(school.getContentPane());
        school.getContentPane().setLayout(schoolLayout);
        schoolLayout.setHorizontalGroup(
            schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schoolLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(schoolLayout.createSequentialGroup()
                        .addComponent(schoolBack_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(schoolNext_btn)
                        .addContainerGap())
                    .addGroup(schoolLayout.createSequentialGroup()
                        .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(grade, 0, 92, Short.MAX_VALUE)
                            .addComponent(jLabel119, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addComponent(subGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, schoolLayout.createSequentialGroup()
                        .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(admission, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(schoolLayout.createSequentialGroup()
                        .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(schoolLayout.createSequentialGroup()
                                .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(medium, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(73, 73, 73))
                            .addGroup(schoolLayout.createSequentialGroup()
                                .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateAdded, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lastUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                                .addGap(50, 50, 50)))
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
        );
        schoolLayout.setVerticalGroup(
            schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schoolLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel21)
                .addGap(1, 1, 1)
                .addComponent(admission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel119)
                .addGap(1, 1, 1)
                .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subGrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(schoolLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel35)
                        .addGap(1, 1, 1)
                        .addComponent(medium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel40)
                        .addGap(1, 1, 1)
                        .addComponent(dateAdded, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel41)
                        .addGap(1, 1, 1)
                        .addComponent(lastUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, schoolLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(schoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(schoolNext_btn)
                            .addComponent(schoolBack_btn))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        imageSelection.setFrameIcon(null);
        imageSelection.setMaximumSize(new java.awt.Dimension(670, 460));
        imageSelection.setMinimumSize(new java.awt.Dimension(670, 460));
        imageSelection.setPreferredSize(new java.awt.Dimension(670, 460));
        imageSelection.setVisible(true);
        imageSelection.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageLabelMouseClicked(evt);
            }
        });
        imageSelection.getContentPane().add(imageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 3, 220, 282));

        backImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/emptyImage.png"))); // NOI18N
        backImage.setEnabled(false);
        backImage.setFocusable(false);
        backImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        imageSelection.getContentPane().add(backImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 640, 290));

        ImageRemove_btn.setText("Remove");
        ImageRemove_btn.setToolTipText("Remove");
        ImageRemove_btn.setMaximumSize(new java.awt.Dimension(43, 43));
        ImageRemove_btn.setMinimumSize(new java.awt.Dimension(43, 43));
        ImageRemove_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ImageRemove_btnMouseReleased(evt);
            }
        });
        ImageRemove_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImageRemove_btnActionPerformed(evt);
            }
        });
        imageSelection.getContentPane().add(ImageRemove_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, 78, 29));

        imagePath.setEditable(false);
        imagePath.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        imagePath.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        imagePath.setBorder(null);
        imagePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagePathActionPerformed(evt);
            }
        });
        imageSelection.getContentPane().add(imagePath, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 560, 30));

        register_btn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        register_btn.setText("Register");
        register_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register_btnActionPerformed(evt);
            }
        });
        imageSelection.getContentPane().add(register_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 383, -1, -1));

        imageBack_btn.setText("Back");
        imageBack_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageBack_btnActionPerformed(evt);
            }
        });
        imageSelection.getContentPane().add(imageBack_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        ImageChoose_btn.setBackground(new java.awt.Color(0, 102, 153));
        ImageChoose_btn.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        ImageChoose_btn.setForeground(new java.awt.Color(204, 204, 204));
        ImageChoose_btn.setText("Upload");
        ImageChoose_btn.setToolTipText("Select Image");
        ImageChoose_btn.setMaximumSize(new java.awt.Dimension(43, 43));
        ImageChoose_btn.setMinimumSize(new java.awt.Dimension(43, 43));
        ImageChoose_btn.setPreferredSize(new java.awt.Dimension(43, 43));
        ImageChoose_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ImageChoose_btnMouseReleased(evt);
            }
        });
        ImageChoose_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImageChoose_btnActionPerformed(evt);
            }
        });
        imageSelection.getContentPane().add(ImageChoose_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 190, 30));

        imageEdit_btn.setText("Edit");
        imageEdit_btn.setToolTipText("Remove");
        imageEdit_btn.setMaximumSize(new java.awt.Dimension(43, 43));
        imageEdit_btn.setMinimumSize(new java.awt.Dimension(43, 43));
        imageEdit_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                imageEdit_btnMouseReleased(evt);
            }
        });
        imageEdit_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageEdit_btnActionPerformed(evt);
            }
        });
        imageSelection.getContentPane().add(imageEdit_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 319, 78, 29));

        jMenuItem1.setText("New student");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setResizable(true);
        setTitle("Manage Details");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        Add_btn.setBackground(getToolBarColor());
        Add_btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Add_btn.setIcon(com.Codes.Commons.getImage("src\\Images\\Toolbar\\AddStudents-"+getBorW()+".png", 40,40));
        buttonGroup1.add(Add_btn);
        Add_btn.setFocusable(false);
        Add_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Add_btn.setMaximumSize(new java.awt.Dimension(60, 60));
        Add_btn.setMinimumSize(new java.awt.Dimension(60, 60));
        Add_btn.setPreferredSize(new java.awt.Dimension(45, 45));
        Add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_btnActionPerformed(evt);
            }
        });

        update_btn.setBackground(getToolBarColor());
        update_btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        update_btn.setIcon(com.Codes.Commons.getImage("src\\Images\\Toolbar\\ManageDetailes\\Edit-"+getBorW()+".png",40,40));
        buttonGroup1.add(update_btn);
        update_btn.setFocusable(false);
        update_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        update_btn.setMaximumSize(new java.awt.Dimension(60, 60));
        update_btn.setMinimumSize(new java.awt.Dimension(60, 60));
        update_btn.setPreferredSize(new java.awt.Dimension(45, 45));
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        select_btn.setBackground(getToolBarColor());
        select_btn.setIcon(com.Codes.Commons.getImage("src\\Images\\Toolbar\\Select-70px-"+getBorW()+".png", 40,40));
        buttonGroup1.add(select_btn);
        select_btn.setFocusable(false);
        select_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        select_btn.setMaximumSize(new java.awt.Dimension(60, 60));
        select_btn.setMinimumSize(new java.awt.Dimension(60, 60));
        select_btn.setPreferredSize(new java.awt.Dimension(45, 45));
        select_btn.setSelected(true);
        select_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        select_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                select_btnActionPerformed(evt);
            }
        });

        clear_btn.setBackground(getToolBarColor());
        clear_btn.setIcon(com.Codes.Commons.getImage("src\\Images\\Toolbar\\ManageDetailes\\Clear-"+getBorW()+".png", 40,40));
        clear_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clear_btn.setMaximumSize(new java.awt.Dimension(60, 60));
        clear_btn.setMinimumSize(new java.awt.Dimension(60, 60));
        clear_btn.setPreferredSize(new java.awt.Dimension(45, 45));
        clear_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_btnActionPerformed(evt);
            }
        });

        delete_btn.setBackground(getToolBarColor());
        delete_btn.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        delete_btn.setIcon(com.Codes.Commons.getImage("src\\Images\\Toolbar\\ManageDetailes\\Delete-"+getBorW()+".png",40,40));
        delete_btn.setToolTipText("Delete");
        delete_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        delete_btn.setMaximumSize(new java.awt.Dimension(60, 60));
        delete_btn.setMinimumSize(new java.awt.Dimension(60, 60));
        delete_btn.setPreferredSize(new java.awt.Dimension(45, 45));
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(Add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clear_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(select_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(select_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                .addComponent(clear_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainBackground.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                mainBackgroundComponentResized(evt);
            }
        });
        mainBackground.setLayout(new javax.swing.BoxLayout(mainBackground, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(mainBackground);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleActionPerformed
        if (male.isSelected()) {
            gender = "Male";
            female.setSelected(false);
            other.setSelected(false);
        }
    }//GEN-LAST:event_maleActionPerformed

    private void femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleActionPerformed
        if (female.isSelected()) {
            gender = "female";
            male.setSelected(false);
            other.setSelected(false);
        }
    }//GEN-LAST:event_femaleActionPerformed

    private void otherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherActionPerformed
        if (other.isSelected()) {
            gender = "Other";
            female.setSelected(false);
            male.setSelected(false);
        }
    }//GEN-LAST:event_otherActionPerformed

    private void admissionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admissionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_admissionKeyPressed

    private void admissionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admissionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_admissionKeyReleased

    private void mediumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mediumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mediumActionPerformed

    private void aboutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutMouseEntered

    }//GEN-LAST:event_aboutMouseEntered

    private void aboutMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutMouseReleased
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(about, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_aboutMouseReleased

    private void imagePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imagePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imagePathActionPerformed

    private void ImageChoose_btnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImageChoose_btnMouseReleased
        imageSelection.repaint();
    }//GEN-LAST:event_ImageChoose_btnMouseReleased

    private void ImageChoose_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImageChoose_btnActionPerformed
        JFileChooser jfc = CommonUi.chooseImage();
        switch (jfc.showDialog(null, "Select image")) {
            case JFileChooser.APPROVE_OPTION:
                File f = jfc.getSelectedFile();
                String imgPath = f.getAbsolutePath();
                currentImagePath = imgPath;
                setImage(new ImageIcon(imgPath));
                lastImagePath = imgPath;
                imagePath.setText(imgPath);
                Commons.setDefault(Defaults.MD_ImagePath, jfc.getCurrentDirectory().getAbsolutePath(), CONN);
                break;
            case JFileChooser.CANCEL_OPTION:
            case JFileChooser.ERROR_OPTION:
                jfc.setVisible(false);
                break;
        }

    }//GEN-LAST:event_ImageChoose_btnActionPerformed

    private void ImageRemove_btnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImageRemove_btnMouseReleased
    }//GEN-LAST:event_ImageRemove_btnMouseReleased

    private void ImageRemove_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImageRemove_btnActionPerformed
        setImage(null);
    }//GEN-LAST:event_ImageRemove_btnActionPerformed

    private void select_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_select_btnActionPerformed
        current = Type.SELECTION;
        selectFrame(current);
    }//GEN-LAST:event_select_btnActionPerformed

    private void Add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_btnActionPerformed
        if (current.equals(Type.UPDATE)) {
            newStudent();
        }
        current = Type.ADD;
        selectFrame(Type.ADD);
    }//GEN-LAST:event_Add_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
        current = Type.UPDATE;
        selectFrame(current);
        updateFrameLoad(selectedIds);
    }//GEN-LAST:event_update_btnActionPerformed

    private void clear_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_btnActionPerformed
        if (!current.equals(Type.SELECTION)) {
            try {
                CommonUi.clearAll(about);
                CommonUi.clearAll(school);
                CommonUi.clearAll(imageSelection);
                setImage(null);
            } catch (Exception ex) {
                Commons.showMsg(ex);
            }
        } else {
            selection.tableLoad();
            update_btn.setEnabled(false);
        }
    }//GEN-LAST:event_clear_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
        PasswordOptions dialog = new PasswordOptions(UserLoginFrame.security.getUsername(), new JFrame(), true) {
            @Override
            public void actionTaken(int action) {
                if (action == PasswordOptions.MATCHED) {
                    DeleteStudent ds = new DeleteStudent(DeleteStudent.MOVETOBIN);
                    ds.setIds(Arrays.asList(selection.getSelectedIds()));
                    ds.deleteAccounts();
                }
            }
        };
        CommonUi.genarateCenter(dialog, dialog.getWidth(), dialog.getHeight());
        dialog.setVisible(true);
    }//GEN-LAST:event_delete_btnActionPerformed

    private void guardianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardianActionPerformed

    private void aboutNext_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutNext_btnActionPerformed
        Type t = current.equals(Type.ADD) ? Type.SCHOOLDETAILS : Type.UPDATE_SCHOOLDETAILS;
        selectFrame(t);
    }//GEN-LAST:event_aboutNext_btnActionPerformed

    private void aboutCancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutCancel_btnActionPerformed
        selectFrame(Type.SELECTION);
    }//GEN-LAST:event_aboutCancel_btnActionPerformed

    private void schoolNext_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schoolNext_btnActionPerformed
        Type t = current.equals(Type.ADD) ? Type.IMAGESELECTION : Type.UPDATE_IMAGESELECTION;
        selectFrame(t);
    }//GEN-LAST:event_schoolNext_btnActionPerformed

    private void imageBack_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageBack_btnActionPerformed
        Type t = current.equals(Type.ADD) ? Type.SCHOOLDETAILS : Type.UPDATE_SCHOOLDETAILS;
        selectFrame(t);
    }//GEN-LAST:event_imageBack_btnActionPerformed

    private void schoolBack_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schoolBack_btnActionPerformed
        Type t = current.equals(Type.ADD) ? Type.ABOUT : Type.UPDATE_ABOUT;
        selectFrame(t);
    }//GEN-LAST:event_schoolBack_btnActionPerformed

    private void register_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register_btnActionPerformed
        if (current.equals(Type.ADD)) {
            try {
                NewStudent rs = new NewStudent(new Object[]{admission.getText(),
                    name.getText(),
                    Integer.valueOf(CommonUi.gsi(grade)),
                    CommonUi.gsi(subGrade),
                    gsi(medium),
                    dateofbirth.getDate(),
                    gender,
                    address.getText(),
                    phone.getText(),
                    gsi(guardian),
                    email.getText()});
                rs.checkData();
                rs.add(dateAdded.getDate());
                if (!imagePath.getText().equals(lastImagePath)) {
                    if (!Student.hasImage(admission.getText()) && !imagePath.getText().equals("")) {
                        if (!imagePath.getText().equals(SYSTEMIMAGE)) {
                            rs.addImage(imagePath.getText());
                        } else {
//                            ImageWriter.write(ImageWriter.getImage(im.cropedImages.get(0).getPrefferedImage()), "png", new File(SYSTEMIMAGE + ".png"));
                            rs.addImage(SYSTEMIMAGE + ".png");
                        }
                    } else {
                        throw new Exception("Image already exists in database");
                    }
                } else {
                    JOptionPane.showConfirmDialog(this, "Selected image is equals to preveously registered student\nAre you sure want to continue");
                }
                new TrayMessage("Adding information was successful", AppConfig.APPICON_50.getImage(), String.format("Admission number\t%s\nName\t%s", admission.getText(), name.getText()), "Successful", TrayIcon.MessageType.INFO).display();
            } catch (Exception ex) {
                new TrayMessage(ex.toString().contains("Null") ? "Reqiured fields" : ex.toString(), AppConfig.APPICON_50.getImage(), ex.getLocalizedMessage(), "Error", TrayIcon.MessageType.ERROR).display();
            }
        } else if (current.equals(Type.UPDATE)) {
            int conf = JOptionPane.NO_OPTION;
            String imgPath = imagePath.getText();
            if (selectedIds.length > 1 && imgPath.equals("")) {
                conf = JOptionPane.showConfirmDialog(this, "You have removed the Images of these students"
                        + "\nThose images will be removed permenantly from the database"
                        + "\nAre you sure want to delete them!",
                        "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (conf == JOptionPane.CANCEL_OPTION) {
                    imagePath.setText(DEFVALS);
                }
            } else if (selectedIds.length == 1) {
                String id = selectedIds[0];
                try {
                    if (Student.hasImage(id)) {
                        if (imgPath.equals("")) {
                            conf = JOptionPane.showConfirmDialog(this, "You have removed the Image of this student"
                                    + "\nThat image will be removed permenantly from the database"
                                    + "\nAre you sure want to delete it!",
                                    "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (conf == JOptionPane.CANCEL_OPTION) {
                                setImage(Student.getImage(id));
                            }
                        } else {
                            if (!imgPath.equals(Student.getImageName(id))) {
                                conf = JOptionPane.showConfirmDialog(this, "You have updated the Image of this student"
                                        + "\nprevious image will be removed permenantly from the database"
                                        + "\nAre you sure want to update it!",
                                        "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (conf == JOptionPane.NO_OPTION) {
                                    setImage(Student.getImage(id));
                                } else if (conf == JOptionPane.YES_OPTION && imgPath.equals(SYSTEMIMAGE)) {
//                                    ImageWriter.write(ImageWriter.getImage(im.cropedImages.get(0).getPrefferedImage()), "png", new File(SYSTEMIMAGE + ".png"));
                                    UpdateStudent.updateImage(id, SYSTEMIMAGE + ".png");
                                }
                            }
                        }
                    } else {
                        if (new File(imgPath).exists()) {
                            NewStudent.addImage(id, imgPath);
                        } else if (imgPath.equals(SYSTEMIMAGE)) {
//                            ImageWriter.write(ImageWriter.getImage(im.cropedImages.get(0).getPrefferedImage()), "png", new File(SYSTEMIMAGE + ".png"));
//                            File i = new File(SYSTEMIMAGE + ".png");
//                            UpdateStudent.updateImage(id, i.getAbsolutePath());
                        }
                        conf = 1;
                    }
                } catch (HeadlessException | SQLException | IOException ex) {
                    Commons.showMsg(ex);
                }
            }
            switch (conf) {
                case JOptionPane.OK_OPTION:
                    if (selectedIds.length > 1) {
                        for (String id : selectedIds) {

                        }
                    } else if (selectedIds.length != 0) {
                        try {
                            if (imagePath.getText().equals("")) {
                                DeleteStudent.removeImage(selectedIds[0]);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                case JOptionPane.NO_OPTION:
                    update(selectedIds);
                    break;
                case JOptionPane.CANCEL_OPTION: {
                    new TrayMessage("Update canceled").display();
                }
                break;
            }
        }
    }//GEN-LAST:event_register_btnActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        newStudent();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (currentFrame != null) {
            CommonUi.genarateCenter(currentFrame, mainBackground, currentFrame.getPreferredSize().width, currentFrame.getPreferredSize().height);
        }
    }//GEN-LAST:event_formComponentResized

    private void imageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageLabelMouseClicked
        if (imageLabel.getToolTipText() != null) {
            try {
                Commons.openFile(imageLabel.getToolTipText());
            } catch (IOException ex) {
                Commons.showMsg(ex);
            }
        }
    }//GEN-LAST:event_imageLabelMouseClicked

    private void imageEdit_btnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageEdit_btnMouseReleased

    }//GEN-LAST:event_imageEdit_btnMouseReleased

    private void imageEdit_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageEdit_btnActionPerformed
        String imgP = this.imagePath.getText();
        String id = admission.getText();
        boolean vi = false;
        ImageIcon img = null;
        String fileName = "";
        if (current.equals(Type.ADD)) {
            if (new File(imgP).exists()) {
                img = new ImageIcon(imgP);
                fileName = imgP;
                vi = true;
            } else {
                showMessage("File does not exists", imgP, MessageType.ERROR);
            }
        } else if (current.equals(Type.UPDATE)) {
            if (imgP.equals(Student.getImageName(id)) && Student.hasImage(id)) {
                img = Student.getImage(id);
            } else if (new File(imgP).exists()) {
                img = new ImageIcon(imgP);
            }
            vi = true;
        }
//        if (vi) {
//            im = new ImageVeiwerDialog(img, fileName);
//            CommonUi.genarateCenter(im, 600, 500);
//            im.setModal(true);
//            im.setSize(600, 500);
//            JButton b = new JButton("OK");
//            b.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if (im.cropedImages.size() == 1) {
//                        Image img = im.cropedImages.get(0).getPrefferedImage();
//                        setImage(new ImageIcon(img));
//                        imagePath.setText(SYSTEMIMAGE);
//                        im.dispose();
//                    }
//                }
//            });
//            CommonUi.setOkButton(b);
//            im.addActionButtons(0, b);
//            im.setMaxCropes(1);
//            im.setVisible(true);
//        }
    }//GEN-LAST:event_imageEdit_btnActionPerformed

    private void mainBackgroundComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_mainBackgroundComponentResized
        if (currentFrame.equals(selection)) {
            currentFrame.setBounds(4, 4, mainBackground.getWidth() - 4, mainBackground.getHeight() - 4);
        }
    }//GEN-LAST:event_mainBackgroundComponentResized

    private void dateofbirthPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateofbirthPropertyChange
        if (dateofbirth.getDate() != null) {
            int[] i = Student.getAgeConf(dateofbirth.getDate(), Commons.date);
            jTextField1.setText(String.format("%d years %d weeks %d days", i[0], i[1], i[2]));
        }
    }//GEN-LAST:event_dateofbirthPropertyChange
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Commons.showMsg(ex);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 1100, 620);
        JDesktopPane pane = new JDesktopPane();
        pane.setBounds(10, 10, 1080, 600);
        Manage_frame hf = new Manage_frame(new Selection());
        frame.add(hf);
        hf.addNotify();
        hf.setBounds(10, 10, 1050, 590);
        hf.setVisible(true);
        frame.setVisible(true);
        frame.validate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_btn;
    private javax.swing.JButton ImageChoose_btn;
    private javax.swing.JButton ImageRemove_btn;
    private javax.swing.JInternalFrame about;
    private javax.swing.JButton aboutCancel_btn;
    private javax.swing.JButton aboutNext_btn;
    private javax.swing.JTextField address;
    private javax.swing.JTextField admission;
    private javax.swing.JLabel backImage;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clear_btn;
    private com.toedter.calendar.JDateChooser dateAdded;
    private com.toedter.calendar.JDateChooser dateofbirth;
    private javax.swing.JButton delete_btn;
    private javax.swing.JTextField email;
    private javax.swing.JRadioButton female;
    private javax.swing.JComboBox<String> grade;
    private javax.swing.JComboBox<String> guardian;
    private javax.swing.JButton imageBack_btn;
    private javax.swing.JButton imageEdit_btn;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JTextField imagePath;
    private javax.swing.JInternalFrame imageSelection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private com.toedter.calendar.JDateChooser lastUpdate;
    private javax.swing.JDesktopPane mainBackground;
    private javax.swing.JRadioButton male;
    private javax.swing.JComboBox<String> medium;
    private javax.swing.JTextField name;
    private javax.swing.JRadioButton other;
    private javax.swing.JTextField phone;
    private javax.swing.JButton register_btn;
    private javax.swing.JInternalFrame school;
    private javax.swing.JButton schoolBack_btn;
    private javax.swing.JButton schoolNext_btn;
    private javax.swing.JButton select_btn;
    private javax.swing.JComboBox<String> subGrade;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
}
