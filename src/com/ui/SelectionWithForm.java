package com.ui;

import com.ui.frames.UserFrame;
import com.ui.internalFrames.Health_frame;
import Standard.array;
import com.Codes.Commons;
import static com.Codes.Commons.getImage;
import static com.Database.Sql.getCovered;
import static com.Database.Sql.getCoveredLike;
import com.ManageDetails.student.Student;
import com.ui.CommonUi;
import static com.ui.CommonUi.getNavColor;
import static com.ui.frames.UserFrame.ADDRESS;
import static com.ui.frames.UserFrame.AGE;
import static com.ui.frames.UserFrame.BIRTH;
import static com.ui.frames.UserFrame.GENDER;
import static com.ui.frames.UserFrame.GRADE;
import static com.ui.frames.UserFrame.MEDIUM;
import static com.ui.frames.UserFrame.NAME;
import static com.ui.frames.UserFrame.SUBGRADE;
import static com.ui.frames.UserFrame.getBorW;
import com.Database.DBconnect;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SelectionWithForm extends javax.swing.JInternalFrame {

    public JPopupMenu GlobalSearchIn;
    public static final String[] COLUMNNAMES = {
        UserFrame.ID_NO,
        UserFrame.NAME,
        UserFrame.AGE,
        UserFrame.GRADE,
        UserFrame.SUBGRADE,
        UserFrame.MEDIUM,
        UserFrame.BIRTH,
        UserFrame.GENDER,
        UserFrame.PHONE,
        UserFrame.ADDRESS,
        UserFrame.GUARDIAN,
        UserFrame.EMAIL
    };
    public int noGender = 3;
    public int noMedium = 3;
    public String multy = "multy";
    Columns globalSearchs;
    Columns columns;
    public String or = "OR";
    public String all = "*All";
    Connection CONN = DBconnect.CONN;

    public SelectionWithForm() {
        initComponents();
        validate();
        globalSearchs = new Columns();
        globalSearchs.id.setEnabled(true);
        columns = new Columns();
        columns.id.setEnabled(false);
        GlobalSearchIn = new JPopupMenu();
        for (JCheckBoxMenuItem c : columns.getChecks()) {
            jMenu2.add(c);
        }
        CommonUi.setLimit(age1, Student.MINAGE, Student.MAXAGE);
        CommonUi.setLimit(age2, Student.MINAGE, Student.MAXAGE);
        try {
            tableLoad();
        } catch (Exception ex) {
            Logger.getLogger(SelectionWithForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTableConfig();
    }

    public void setTableConfig() {
        selectedRows.setText("" + Table.getSelectedRowCount());
        totalRows.setText("" + Table.getRowCount());
    }

    public static String gsi(JComboBox box) {
        return box.getSelectedItem().toString();
    }

    public void setGender(JCheckBox c) {
        noGender = 1;
        for (Component c1 : jPanel6.getComponents()) {
            JCheckBox check = (JCheckBox) c1;
            if (c.equals(check)) {
                if (!check.isSelected()) {
                    check.doClick(0);
                }
                if (check.equals(gendAll)) {
                    break;
                }
            } else {
                check.setSelected(false);
            }
        }
    }

    public void setMedium(JCheckBox c) {
        noMedium = 1;
        for (Component c1 : jPanel4.getComponents()) {
            JCheckBox check = (JCheckBox) c1;
            if (c.equals(check)) {
                check.setSelected(true);
            } else {
                check.setSelected(false);
            }
        }
    }

    public void tableLoad() {
        String[] cols = getColumns();
        for (int i = 0; i < cols.length; i++) {
            cols[i] = getCovered(cols[i]);
        }
        String sql = "SELECT " + String.join(",", cols) + " FROM " + Student.SQLTABLE + (getQuery().equals("") ? "" : " WHERE " + (jDialog1.isVisible() ? jTextArea1.getText() : getQuery()));
        System.out.println("SQL = " + sql);
        CommonUi.tableload(sql, Table, CONN);
        Table.setRowHeight(20);
    }

    public void setMessage(String msg, MessageType type) {

    }

    public String[] getColumns() {
        String[] COLUMNNAMES = {
            UserFrame.ID_NO,
            UserFrame.NAME,
            UserFrame.AGE,
            UserFrame.GRADE,
            UserFrame.SUBGRADE,
            UserFrame.MEDIUM,
            UserFrame.BIRTH,
            UserFrame.GENDER,
            UserFrame.PHONE,
            UserFrame.ADDRESS,
            UserFrame.GUARDIAN,
            UserFrame.EMAIL
        };
        boolean[] cols = columns.getSelectedItems();
        if (cols[0]) {
            return COLUMNNAMES;
        } else {
            ArrayList<String> col = new ArrayList<>();
            for (int i = 1; i < cols.length; i++) {
                if (cols[i]) {
                    col.add(COLUMNNAMES[i - 1]);
                }
            }
            return col.toArray(new String[col.size()]);
        }
    }

    public String getQuery() {
        String query = "";
        if (!n.getText().equals("")) {
            query = query.concat(" " + getCovered(NAME) + " LIKE " + getCoveredLike(n.getText()) + " " + or);
        }
        if (!gsi(ageSel).equals(all)) {
            int f = (int) age1.getValue();
            int s = (int) age2.getValue();
            switch (gsi(ageSel)) {
                case "From-To":
                    query = query.concat(" " + getCovered(AGE) + " BETWEEN " + getCovered(f + "") + " AND " + getCovered(s + "") + " " + or);
                    break;
                case "And":
                    query = query.concat(" " + getCovered(AGE) + " LIKE " + getCoveredLike(f + "") + " AND " + getCovered(AGE) + " LIKE " + getCoveredLike(s + "") + " " + or);
                    break;
            }
        }
        if (!gsi(gradeSel).equals(all)) {
            int f = Integer.parseInt(gsi(jComboBox3));
            int s = Integer.parseInt(gsi(jComboBox4));
            System.out.println("Grade f =" + f + "\n\ts = " + s + "\t selected = " + gsi(gradeSel));
            switch (gsi(gradeSel)) {
                case "From-To":
                    query = query.concat(" " + getCovered(GRADE) + " BETWEEN " + getCovered(f + "") + " AND " + getCovered((s + 1) + "") + or);
                    break;
                case "And":
                    query = query.concat(" " + getCovered(GRADE) + "=" + getCovered(f + "") + (gsi(subGrade).equals(all) ? "" : " AND " + getCovered(SUBGRADE) + "=" + getCovered(gsi(subGrade)) + " ") + " " + or + getCovered(GRADE) + "=" + getCovered(s + "") + (gsi(subGrade).equals(all) ? "" : " AND " + getCovered(SUBGRADE) + "=" + getCovered(gsi(subGrade)) + " "));
                    break;
                case "Equals":
                    query = query.concat(" " + getCovered(GRADE) + "=" + getCovered(s + "") + (gsi(subGrade).equals(all) ? "" : " AND " + getCovered(SUBGRADE) + "=" + getCovered(gsi(subGrade)) + " ") + " " + or);
                    break;
            }
        }
        if (!mediumAll.isSelected()) {
            query = query.concat((sinhala.isSelected() ? " " + getCovered(MEDIUM) + (noMedium > 1 ? " LIKE " : " = ") + getCovered("Sinhala") + " " + or : "") + (english.isSelected() ? " " + getCovered(MEDIUM) + (noMedium > 1 ? " LIKE " : " = ") + getCovered("English") + " " + or : "") + (tamil.isSelected() ? " " + getCovered(MEDIUM) + (noMedium > 1 ? " LIKE " : " = ") + getCovered("Tamil") + " " + or : ""));
        }
        if (!bod1.getDate().equals(bod1.getMinSelectableDate()) && !bod2.getDate().equals(bod2.getMaxSelectableDate())) {
            query = query.concat(" " + getCovered(BIRTH) + " BETWEEN " + getCovered(CommonUi.getDate(bod1)) + " AND " + getCovered(CommonUi.getDate(bod2)) + " " + or);
        }
        if (!gendAll.isSelected()) {
            query = query.concat((male.isSelected() ? " " + getCovered(GENDER) + (noGender > 1 ? " LIKE " : " = ") + getCovered("Male") + " " + or : "") + (female.isSelected() ? " " + getCovered(GENDER) + (noGender > 1 ? " LIKE " : " = ") + getCovered("Female") + " " + or : "") + (other.isSelected() ? " " + getCovered(GENDER) + (noGender > 1 ? " LIKE " : " = ") + getCovered("Other") + " " + or : ""));
        }
        if (!jTextField1.getText().equals("")) {
            query = query.concat(" " + getCovered(ADDRESS) + " LIKE " + getCoveredLike(jTextField1.getText()) + " ");
        }
        query = query.equals("") ? "" : new StringBuffer(query).substring(query.length() - or.length(), query.length()).equals(or) ? new StringBuffer(query).substring(0, query.length() - or.length()) : query;
        return query;
    }

    class Columns {

        public JCheckBoxMenuItem guardian;
        public JCheckBoxMenuItem name;
        public JCheckBoxMenuItem medium;
        public JCheckBoxMenuItem grade;
        public JCheckBoxMenuItem subGrade;
        public JCheckBoxMenuItem All;
        public JCheckBoxMenuItem id;
        public JCheckBoxMenuItem age;
        public JCheckBoxMenuItem birth;
        public JCheckBoxMenuItem gender;
        public JCheckBoxMenuItem address;
        public JCheckBoxMenuItem email;
        public JCheckBoxMenuItem phone;

        public Columns() {
            init();
        }

        public JCheckBoxMenuItem[] getChecks() {
            return new JCheckBoxMenuItem[]{
                All,
                id,
                name,
                age,
                grade,
                subGrade,
                medium,
                birth,
                gender,
                phone,
                address,
                guardian,
                email
            };
        }

        public void setSelected(boolean[] s) {
            for (int i = 0; i < s.length; i++) {
                JCheckBoxMenuItem c = getChecks()[i + 1];
                if (c.isEnabled()) {
                    c.setSelected(s[i]);
                }
            }
        }

        public boolean[] getSelectedItems() {
            boolean[] r = new boolean[getChecks().length];
            int i = 0;
            for (JCheckBoxMenuItem c : getChecks()) {
                r[i] = c.isSelected();
                i++;
            }
            return r;
        }

        void init() {
            this.All = new javax.swing.JCheckBoxMenuItem();
            this.id = new javax.swing.JCheckBoxMenuItem();
            this.name = new javax.swing.JCheckBoxMenuItem();
            this.grade = new javax.swing.JCheckBoxMenuItem();
            this.subGrade = new javax.swing.JCheckBoxMenuItem();
            this.medium = new javax.swing.JCheckBoxMenuItem();
            this.age = new javax.swing.JCheckBoxMenuItem();
            this.birth = new javax.swing.JCheckBoxMenuItem();
            this.gender = new javax.swing.JCheckBoxMenuItem();
            this.address = new javax.swing.JCheckBoxMenuItem();
            this.phone = new javax.swing.JCheckBoxMenuItem();
            this.guardian = new javax.swing.JCheckBoxMenuItem();
            this.email = new javax.swing.JCheckBoxMenuItem();
            All.setSelected(true);
            All.setText("All");

            id.setSelected(true);
            id.setText("Admission Number");

            name.setSelected(true);
            name.setText("Name");

            grade.setSelected(true);
            grade.setText("Grade");

            subGrade.setSelected(true);
            subGrade.setText("Sub grade");

            medium.setSelected(true);
            medium.setText("Medium");

            age.setSelected(true);
            age.setText("Age");

            birth.setSelected(true);
            birth.setText("Date of birth");

            gender.setSelected(true);
            gender.setText("Gender");

            address.setSelected(true);
            address.setText("Address");

            phone.setSelected(true);
            phone.setText("Phone Number");

            guardian.setSelected(true);
            guardian.setText("Guardian");

            email.setSelected(true);
            email.setText("Email");

            All.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean[] b = new boolean[getChecks().length - 1];
                    for (int i = 0; i < b.length; i++) {
                        b[i] = All.isSelected();
                    }
                    setSelected(b);
                }
            });
            for (JCheckBoxMenuItem c : getChecks()) {
                if (c.equals(All)) {
                    continue;
                }
                c.addActionListener((ActionEvent e) -> {
                    int i = 0;
                    int i2 = 0;
                    for (JCheckBoxMenuItem c1 : getChecks()) {
                        i += c1.isSelected() ? 1 : 0;
                        i2++;
                    }
                    All.setSelected(i == i2);
                });
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        n = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        age2 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        age1 = new javax.swing.JSpinner();
        ageSel = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        gradeSel = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        subGrade = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        tamil = new javax.swing.JCheckBox();
        english = new javax.swing.JCheckBox();
        mediumAll = new javax.swing.JCheckBox();
        sinhala = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        bod1 = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        bod2 = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        male = new javax.swing.JCheckBox();
        female = new javax.swing.JCheckBox();
        other = new javax.swing.JCheckBox();
        gendAll = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        topBar = new javax.swing.JToolBar();
        jToolBar2 = new javax.swing.JToolBar();
        SelectGrade = new javax.swing.JComboBox<>();
        SelectSubgrade = new javax.swing.JComboBox<>();
        SelectMedium = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        totalRows = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        selectedRows = new javax.swing.JLabel();
        searchbox = new javax.swing.JTextField();
        jButton24 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        sideBar = new javax.swing.JToolBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();

        jDialog1.setTitle("Filter properties");
        jDialog1.setIconImage(null);
        jDialog1.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jLabel11.setText("Filter");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(n, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(n, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Name", jPanel8);

        jLabel1.setText("From");

        jLabel2.setText("To");

        ageSel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*All", "From-To", "And" }));
        ageSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageSelActionPerformed(evt);
            }
        });

        jLabel3.setText("Selection type");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(412, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(6, 6, 6))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ageSel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(age2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(age1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ageSel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(age1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(age2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Age", jPanel2);

        jLabel4.setText("From");

        gradeSel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*All", "Equals", "From-To", "And" }));
        gradeSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeSelActionPerformed(evt);
            }
        });

        jLabel5.setText("Selection type");

        jLabel6.setText("To");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));

        jLabel7.setText("Sub Grade");

        subGrade.setEditable(true);
        subGrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*All", "A", "B", "C", "D", "E" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(15, 15, 15))
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 362, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(gradeSel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(subGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(1, 1, 1)
                        .addComponent(gradeSel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subGrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Grade", jPanel1);

        tamil.setText("Tamil");
        tamil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tamilActionPerformed(evt);
            }
        });

        english.setText("English");
        english.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                englishActionPerformed(evt);
            }
        });

        mediumAll.setText("All");
        mediumAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediumAllActionPerformed(evt);
            }
        });

        sinhala.setText("Sinhala");
        sinhala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sinhalaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mediumAll)
                    .addComponent(sinhala)
                    .addComponent(tamil)
                    .addComponent(english))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mediumAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sinhala)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tamil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(english)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Medium", jPanel4);

        bod1.setDate(bod1.getMinSelectableDate());
        bod1.setDateFormatString("yyyy-MM-dd");

        jLabel9.setText("From");

        jLabel10.setText("To");

        bod2.setDate(bod1.getMaxSelectableDate());
        bod2.setDateFormatString("yyyy-MM-dd");

        jButton3.setText("Reset");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bod2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addComponent(bod1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bod1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bod2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Date of birth", jPanel5);

        male.setText("Male");
        male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleActionPerformed(evt);
            }
        });

        female.setText("Female");
        female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleActionPerformed(evt);
            }
        });

        other.setText("Other");
        other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherActionPerformed(evt);
            }
        });

        gendAll.setText("All");
        gendAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gendAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gendAll)
                    .addComponent(male)
                    .addComponent(female)
                    .addComponent(other))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gendAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(male)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(female)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(other)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Gender", jPanel6);

        jLabel8.setText("Filter");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("HomeAddress", jPanel7);

        jButton1.setBackground(new java.awt.Color(0, 102, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton7.setText("Reset");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel12.setText("Sql");

        jButton8.setText("create");
        jButton8.setBorder(null);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jDialog1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton7))
                            .addComponent(jTabbedPane1)
                            .addGroup(jDialog1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))
                        .addGap(8, 8, 8))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addGap(10, 10, 10))))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(7, 7, 7)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        setTitle("Selection");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel3.setBackground(getNavColor());

        topBar.setRollover(true);

        jToolBar2.setToolTipText("Filter options");

        SelectGrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));
        SelectGrade.setToolTipText("Grade");
        jToolBar2.add(SelectGrade);

        SelectSubgrade.setEditable(true);
        SelectSubgrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*All", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
        SelectSubgrade.setToolTipText("Subgrade");
        jToolBar2.add(SelectSubgrade);

        SelectMedium.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*All", "Sinhala", "English", "Tamil" }));
        SelectMedium.setToolTipText("Medium");
        jToolBar2.add(SelectMedium);

        jComboBox1.setModel(new DefaultComboBoxModel<String>(array.join(new String[]{"*All"},Student.GENDERS)));
        jToolBar2.add(jComboBox1);

        jButton5.setIcon(Commons.getImage("src\\Images\\Toolbar\\Settings-"+getBorW()+".png",28,28));
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton5.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton5.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        topBar.add(jToolBar2);

        jButton4.setIcon(getImage("src\\Images\\Toolbar\\Refresh-"+getBorW()+".png",20,20));
        jButton4.setToolTipText("Load data");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton4.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton4.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton6.setIcon(getImage("src\\Images\\Toolbar\\SelectAll-"+getBorW()+".png",20,20));
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton6.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton6.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jPanel9.setPreferredSize(new java.awt.Dimension(150, 40));

        jLabel13.setText("Rows");
        jPanel9.add(jLabel13);
        jPanel9.add(totalRows);

        jLabel14.setText("Selected");
        jPanel9.add(jLabel14);
        jPanel9.add(selectedRows);

        jToolBar1.add(jPanel9);

        topBar.add(jToolBar1);

        searchbox.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        searchbox.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        searchbox.setToolTipText("Search in Home");
        searchbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchboxKeyReleased(evt);
            }
        });
        topBar.add(searchbox);

        jButton24.setIcon(getImage("src\\Images\\Toolbar\\Search-"+getBorW()+".png",38,38));
        jButton24.setFocusable(false);
        jButton24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton24.setMaximumSize(new java.awt.Dimension(40, 40));
        jButton24.setMinimumSize(new java.awt.Dimension(40, 40));
        jButton24.setPreferredSize(new java.awt.Dimension(40, 40));
        jButton24.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton24MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton24MouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton24MouseClicked(evt);
            }
        });
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        topBar.add(jButton24);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(topBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Database is empty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setGridColor(new java.awt.Color(0, 0, 0));
        Table.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        Table.setRowHeight(25);
        Table.getTableHeader().setReorderingAllowed(false);
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TableMouseReleased(evt);
            }
        });
        Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        jPanel10.add(jScrollPane1);

        sideBar.setOrientation(javax.swing.SwingConstants.VERTICAL);
        sideBar.setRollover(true);
        sideBar.setMaximumSize(new java.awt.Dimension(0, 2147483647));
        sideBar.setMinimumSize(new java.awt.Dimension(0, 0));
        sideBar.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel10.add(sideBar);

        jMenu2.setText("Columns");
        jMenuBar1.add(jMenu2);

        jMenu1.setText("Search");
        jMenu1.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu1MenuSelected(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchboxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchboxKeyReleased

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(Table.getModel());
        Table.setRowSorter(rowSorter);
        String text = searchbox.getText();
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            int[] id = new int[0];
            boolean[] b = globalSearchs.getSelectedItems();
            JCheckBoxMenuItem[] cs = globalSearchs.getChecks();
            for (int i = 1; i < b.length; i++) {
                if (b[i] && cs[i].isEnabled()) {
                    id = array.concat(id, i - 1);
                } else {
                    if (id.length > 0) {
                        id = array.concat(id, id[id.length - 1]);
                    }
                }
            }
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, id[0], id[1], id[2], id[3], id[4], id[5], id[6], id[7], id[8], id[9], id[10], id[11]));
        }
    }//GEN-LAST:event_searchboxKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int idx = SelectGrade.getSelectedIndex();
        if (idx == 0) {
            gradeSel.setSelectedIndex(0);
            gradeSelActionPerformed(null);
        } else {
            gradeSel.setSelectedItem("Equals");
            jComboBox4.setSelectedIndex(idx - 1);
            gradeSelActionPerformed(null);
        }
        idx = SelectSubgrade.getSelectedIndex();
        subGrade.setSelectedIndex(idx);
        idx = SelectMedium.getSelectedIndex();
        switch (idx) {
            case 0:
                if (!mediumAll.isSelected()) {
                    mediumAll.doClick(0);
                }
                break;
            case 1:
                setMedium(sinhala);
                break;
            case 2:
                setMedium(english);
                break;
            case 3:
                setMedium(tamil);
                break;
        }
        idx = jComboBox1.getSelectedIndex();
        switch (idx) {
            case 0:
                if (!gendAll.isSelected()) {
                    gendAll.doClick(0);
                }
                break;
            case 1:
                setGender(male);
                break;
            case 2:
                setGender(female);
                break;
            case 3:
                setGender(other);
                break;
        }
        or = "AND";
        tableLoad();
        totalRows.setText("" + Table.getRowCount());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (Table.getSelectedRowCount() == Table.getRowCount()) {
            Table.clearSelection();
            jButton6.setToolTipText("Select all");
        } else {
            Table.selectAll();
            jButton6.setToolTipText("Select none");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton24MouseClicked

    }//GEN-LAST:event_jButton24MouseClicked

    private void jButton24MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton24MouseEntered

    }//GEN-LAST:event_jButton24MouseEntered

    private void jButton24MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton24MouseExited
        int X = evt.getX();
        if (X < GlobalSearchIn.getX()) {
            GlobalSearchIn.setVisible(false);
        }
    }//GEN-LAST:event_jButton24MouseExited

    private void TableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseReleased
        selectedRows.setText("" + Table.getSelectedRowCount());
    }//GEN-LAST:event_TableMouseReleased

    private void TableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableKeyReleased
        selectedRows.setText("" + Table.getSelectedRowCount());
    }//GEN-LAST:event_TableKeyReleased

    private void gradeSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeSelActionPerformed
        String selection = gsi(gradeSel);
        String s1 = "From";
        String s2 = "To";
        boolean sg = true;
        boolean g = true;
        switch (selection) {
            case "*All":
                jComboBox3.setSelectedIndex(0);
                jComboBox4.setSelectedIndex(12);
                break;
            case "From-To":
                sg = false;
                break;
            case "And":
                s1 = "";
                s2 = "And";
                break;
            case "Equals":
                g = false;
                s1 = "";
                s2 = "Equals";
                break;
        }
        this.jComboBox3.setEnabled(g);
        this.subGrade.setEnabled(sg);
        jLabel4.setText(s1);
        jLabel6.setText(s2);
    }//GEN-LAST:event_gradeSelActionPerformed

    private void ageSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageSelActionPerformed
        String selection = gsi(ageSel);
        String s1 = "From";
        String s2 = "To";
        switch (selection) {
            case "*All":
                age1.setValue(Student.MINAGE);
                age2.setValue(Student.MAXAGE);
                break;
            case "From-To":
                break;
            case "And":
                s1 = "";
                s2 = "And";
                break;
        }
        jLabel1.setText(s1);
        jLabel2.setText(s2);
    }//GEN-LAST:event_ageSelActionPerformed

    private void gendAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gendAllActionPerformed
        for (Component c : jPanel6.getComponents()) {
            ((JCheckBox) c).setSelected(gendAll.isSelected());
        }
    }//GEN-LAST:event_gendAllActionPerformed

    private void mediumAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mediumAllActionPerformed
        for (Component c : jPanel4.getComponents()) {
            ((JCheckBox) c).setSelected(mediumAll.isSelected());
        }
    }//GEN-LAST:event_mediumAllActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        bod1.setDate(bod1.getMinSelectableDate());
        bod2.setDate(bod1.getMaxSelectableDate());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void sinhalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sinhalaActionPerformed
        int i = 0;
        for (Component c : jPanel4.getComponents()) {
            if (mediumAll.equals(c)) {
                continue;
            }
            i += ((JCheckBox) c).isSelected() ? 1 : 0;
        }
        noMedium = i;
        mediumAll.setSelected(i == 3);
    }//GEN-LAST:event_sinhalaActionPerformed

    private void tamilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tamilActionPerformed
        sinhalaActionPerformed(null);
    }//GEN-LAST:event_tamilActionPerformed

    private void englishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_englishActionPerformed
        sinhalaActionPerformed(null);
    }//GEN-LAST:event_englishActionPerformed

    private void maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleActionPerformed
        int i = 0;
        for (Component c : jPanel6.getComponents()) {
            if (gendAll.equals(c)) {
                continue;
            }
            i += ((JCheckBox) c).isSelected() ? 1 : 0;
        }
        noGender = i;
        gendAll.setSelected(i == 3);
    }//GEN-LAST:event_maleActionPerformed

    private void femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleActionPerformed
        maleActionPerformed(null);
    }//GEN-LAST:event_femaleActionPerformed

    private void otherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherActionPerformed
        maleActionPerformed(null);
    }//GEN-LAST:event_otherActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextArea1.setText(getQuery());
        try {
            tableLoad();
        } catch (Exception ex) {
            Logger.getLogger(SelectionWithForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        CommonUi.genarateCenter(jDialog1, 530, 395);
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenu1MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu1MenuSelected
        for (JCheckBoxMenuItem c : globalSearchs.getChecks()) {
            jMenu1.add(c);
        }
    }//GEN-LAST:event_jMenu1MenuSelected

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        n.setText("");
        ageSel.setSelectedIndex(0);
        ageSelActionPerformed(null);
        gradeSel.setSelectedIndex(0);
        gradeSelActionPerformed(null);
        if (!mediumAll.isSelected()) {
            mediumAll.doClick(0);
        }
        if (!gendAll.isSelected()) {
            gendAll.doClick(0);
        }
        jButton3.doClick(0);
        jTextField1.setText("");
        subGrade.setSelectedIndex(0);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jTextArea1.setText(getQuery());
    }//GEN-LAST:event_jButton8ActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int w = getWidth();
        int pw = 60;
        if (w < 620) {
            sideBar.setMinimumSize(new Dimension(pw, jScrollPane1.getHeight()));
            sideBar.setMaximumSize(new Dimension(pw, jScrollPane1.getHeight()));
            sideBar.setPreferredSize(new Dimension(pw, jScrollPane1.getHeight()));
            jToolBar2.setOrientation(JToolBar.VERTICAL);
            sideBar.add(jToolBar2);
            if (w < 350) {
                sideBar.add(jToolBar1);
                jToolBar1.setOrientation(JToolBar.VERTICAL);
                jPanel9.setPreferredSize(new Dimension(pw, 50));
            } else {
                topBar.add(jToolBar1);
                jToolBar1.setOrientation(JToolBar.HORIZONTAL);
                jPanel9.setPreferredSize(new Dimension(150, 40));
            }
        } else {
            topBar.add(jToolBar2);
            topBar.add(jToolBar1);
            jToolBar1.setOrientation(JToolBar.HORIZONTAL);
            jToolBar2.setOrientation(JToolBar.HORIZONTAL);
            sideBar.setMaximumSize(new Dimension(0, jScrollPane1.getHeight()));
            sideBar.setPreferredSize(new Dimension(0, jScrollPane1.getHeight()));
            sideBar.setMinimumSize(new Dimension(0, jScrollPane1.getHeight()));
        }
    }//GEN-LAST:event_formComponentResized

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        for (JCheckBoxMenuItem c : globalSearchs.getChecks()) {
            GlobalSearchIn.add(c);
        }
        GlobalSearchIn.show(jButton24, jButton24.getWidth() - 2, 0);
    }//GEN-LAST:event_jButton24ActionPerformed
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1100, 600);
        JDesktopPane pane = new JDesktopPane();
        pane.setBounds(10, 10, 1080, 580);
        SelectionWithForm hf = new SelectionWithForm();
        frame.add(hf);
        hf.addNotify();
        hf.setBounds(10, 10, 1050, 560);
        hf.setVisible(true);
        frame.setVisible(true);
        frame.validate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> SelectGrade;
    private javax.swing.JComboBox<String> SelectMedium;
    private javax.swing.JComboBox<String> SelectSubgrade;
    private javax.swing.JTable Table;
    private javax.swing.JSpinner age1;
    private javax.swing.JSpinner age2;
    private javax.swing.JComboBox<String> ageSel;
    private com.toedter.calendar.JDateChooser bod1;
    private com.toedter.calendar.JDateChooser bod2;
    private javax.swing.JCheckBox english;
    private javax.swing.JCheckBox female;
    private javax.swing.JCheckBox gendAll;
    private javax.swing.JComboBox<String> gradeSel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JCheckBox male;
    private javax.swing.JCheckBox mediumAll;
    private javax.swing.JTextField n;
    private javax.swing.JCheckBox other;
    private javax.swing.JTextField searchbox;
    private javax.swing.JLabel selectedRows;
    private javax.swing.JToolBar sideBar;
    private javax.swing.JCheckBox sinhala;
    private javax.swing.JComboBox<String> subGrade;
    private javax.swing.JCheckBox tamil;
    private javax.swing.JToolBar topBar;
    private javax.swing.JLabel totalRows;
    // End of variables declaration//GEN-END:variables
}
