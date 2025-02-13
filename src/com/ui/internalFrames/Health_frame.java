package com.ui.internalFrames;

import Convert.Length;
import Convert.Mass;
import Standard.PlaceValue;
import Standard.RoundingOff;
import Standard.array;
import com.Codes.AppConfig;
import com.Codes.Commons;
import static com.Codes.Commons.EMPTYSTRING;
import com.ManageDetails.student.Student;
import com.ui.components.TextField;
import com.ui.CommonUi;
import com.Communication.Messages;
import com.Communication.TrayMessage;
import com.Database.DBconnect;
import static com.Database.DBconnect.CONN;
import com.Database.Sql;
import com.HealthReport.Desseases;
import com.HealthReport.Health;
import com.HealthReport.Hearing;
import com.HealthReport.Immunition;
import com.HealthReport.LabReports;
import com.HealthReport.Types;
import com.HealthReport.Vision;
import com.formdev.flatlaf.FlatLightLaf;
import com.ui.CommonUi;
import com.ui.components.TextField;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

public class Health_frame extends javax.swing.JInternalFrame {

    JTable table;
    public static final int BUTTONRADIUS = 85;
    public static final String ZERO = "0";
    public JInternalFrame[] frames = {};
    public JInternalFrame currentFrame;
    private final int FRAMEHEIGHT = 490;
    private final int FRAMEWIDTH = 390;
    public String[] selectedIds;
    HashMap<JCheckBox, JSpinner> near = new HashMap();
    HashMap<JCheckBox, JSpinner> far = new HashMap();
    VissionAndHearing visAndHear = new VissionAndHearing();
    LabAndDes labDes = new LabAndDes();
    growth growth = new growth();
    Immune immune = new Immune();
    public String Id = "1024";
    public int Age = 6;
    JButton[] buttons;

    public Health_frame(JTable table) {
        this.table = table;
        selectedIds = new String[]{Id};
        initComponents();
        buttons = new JButton[]{jButton28, jButton31, jButton71, jButton57};
        currentFrame = Immunity;
        visAndHear.setFrame();
        labDes.setFrame();
        immune.setFrame();
        for (JButton button : buttons) {
            button.setBackground(new Color(0, 0, 0, 0));
            button.setBorder(null);
            button.setRolloverIcon(Commons.getImage((ImageIcon) button.getIcon(), BUTTONRADIUS + 10, BUTTONRADIUS + 10));
            button.addMouseListener(new java.awt.event.MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button.validate();
                    button.repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    button.validate();
                    button.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    button.validate();
                    button.repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    button.validate();
                    button.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.validate();
                    jDialog1.setVisible(false);
                    button.repaint();
                }
            });
        }
        new TextField(height).setAsStandardNumberField();
        new TextField(weight).setAsStandardNumberField();
    }

    public void load(String id) {
        if (currentFrame == vissionAndHearingFrame) {
            visAndHear.load(id);
        } else if (currentFrame == labReportsAndDesseasesFrame) {
            labDes.load(id, Types.LabReports, lab.getSelectedItem().toString());
            labDes.load(id, Types.Desseases, des.getSelectedItem().toString());
        } else if (currentFrame == growthFrame) {
            growth.setGender(Student.getGender(id));
            growth.load(id);
        } else if (currentFrame == Immunity) {
            immune.load();
        }
    }

    class VissionAndHearing {

        public static final String STATUSFAR = "1";
        public static final String STATUSNEAR = "2";
        public static final String STATUSBOTH = "3";

        public int[] getFars() {
            try {
                return new int[]{farLeftCheck.isSelected() ? (int) farLeft.getValue() : -1,
                    farRightCheck.isSelected() ? (int) farRight.getValue() : -1,
                    farBothCheck.isSelected() ? (int) farBoth.getValue() : -1,
                    (int) farDenom.getValue()
                };
            } catch (Exception e) {
                return new int[4];
            }
        }

        public int[] getNears() {
            try {
                return new int[]{nearLeftCheck.isSelected() ? (int) nearLeft.getValue() : -1,
                    nearRightCheck.isSelected() ? (int) nearRight.getValue() : -1,
                    nearBothCheck.isSelected() ? (int) nearBoth.getValue() : -1,
                    (int) nearDenom.getValue()
                };
            } catch (Exception e) {
                return new int[4];
            }
        }

        public void setFrame() {
            far.put(farLeftCheck, farLeft);
            far.put(farRightCheck, farRight);
            far.put(farBothCheck, farBoth);
            near.put(nearLeftCheck, nearLeft);
            near.put(nearRightCheck, nearRight);
            near.put(nearBothCheck, nearBoth);
            near.entrySet().forEach((Near) -> {
                JCheckBox check = Near.getKey();
                JSpinner value = Near.getValue();
                check.addActionListener((ActionEvent e) -> {
                    value.setEnabled(check.isSelected());
                    value.setValue(0);
                });
                value.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        setFullStatus();
                        if ((int) value.getValue() < 1) {
                            value.setValue(1);
                        } else if ((int) value.getValue() > (int) nearDenom.getValue() && (int) nearDenom.getValue() != 1) {
                            value.setValue(nearDenom.getValue());
                        }
                    }
                });
            });
            far.entrySet().forEach((Far) -> {
                JCheckBox key = Far.getKey();
                JSpinner value = Far.getValue();
                key.addActionListener((ActionEvent e) -> {
                    value.setEnabled(key.isSelected());
                    value.setValue(0);
                });
                value.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        setFullStatus();
                        if ((int) value.getValue() < 1) {
                            value.setValue(1);
                        }
                        if ((int) value.getValue() > (int) farDenom.getValue() && (int) farDenom.getValue() != 1) {
                            value.setValue((int) farDenom.getValue());
                        }
                    }
                });
            });
        }

        int[] getStatus(int conf) {
            int[] vals = new int[4];
            switch (conf) {
                case 1:
                    vals = array.remove(getFars(), getFars().length - 1);
                    break;
                case 2:
                    vals = array.remove(getNears(), getNears().length - 1);
                    break;
                case 3:
                    vals = array.join(getStatus(1), getStatus(2));
                    break;
            }
            return vals;
        }

        public String getFullStatus(String type) {
            switch (type) {
                case "1":
                    return Vision.getFullStatus((int) farDenom.getValue(), getStatus(1));
                case "2":
                    return Vision.getFullStatus((int) nearDenom.getValue(), getStatus(2));
                case "3":
                    return Vision.getFullStatus(((int) farDenom.getValue() + (int) nearDenom.getValue()) / 2, getStatus(3));
                default:
                    return EMPTYSTRING;

            }
        }

        void setFullStatus() {
            near.entrySet().forEach((Near) -> {
                JCheckBox check = Near.getKey();
                JSpinner val = Near.getValue();
                if (check.isSelected()) {
                    val.setToolTipText(Vision.getStatuses((int) val.getValue(), (int) nearDenom.getValue()));
                }
            });
            far.entrySet().forEach((Far) -> {
                JCheckBox check = Far.getKey();
                JSpinner val = Far.getValue();
                if (check.isSelected()) {
                    val.setToolTipText(Vision.getStatuses((int) val.getValue(), (int) nearDenom.getValue()));
                }
            });
            farStatus.setText(getFullStatus(STATUSFAR));
            nearStatus.setText(getFullStatus(STATUSNEAR));
            overrollStatus.setText(getFullStatus(STATUSBOTH));
        }

        public void load(String id) {
            int selItm = VH_Select.getSelectedIndex();
            if (selItm == 0 ? Vision.hasData(id, "far") : Hearing.hasData(id, "far")) {
                ArrayList<Object> far = selItm == 0 ? Vision.getVissions(id, "far") : Hearing.getHearings(id, "far");
                farDenom.setValue(far.get(3));
                if ((int) far.get(0) != -1) {
                    farLeftCheck.setSelected(true);
                    farLeft.setValue(far.get(0));
                    farLeft.setEnabled(true);
                } else {
                    farLeftCheck.setSelected(false);
                    farLeft.setEnabled(false);
                }
                if ((int) far.get(1) != -1) {
                    farRightCheck.setSelected(true);
                    farRight.setValue(far.get(1));
                    farRight.setEnabled(true);
                } else {
                    farRightCheck.setSelected(false);
                    farRight.setEnabled(false);
                }
                if ((int) far.get(2) != -1) {
                    farBothCheck.setSelected(true);
                    farBoth.setValue(far.get(2));
                    farBoth.setEnabled(true);
                } else {
                    farBothCheck.setSelected(false);
                    farBoth.setEnabled(false);
                }
            }
            if (selItm == 0 ? Vision.hasData(id, "near") : Hearing.hasData(id, "near")) {
                ArrayList<Object> near = selItm == 0 ? Vision.getVissions(id, "near") : Hearing.getHearings(id, "near");
                nearDenom.setValue(near.get(3));
                if ((int) near.get(0) != -1) {
                    nearLeftCheck.setSelected(true);
                    nearLeft.setValue(near.get(0));
                    nearLeft.setEnabled(true);
                } else {
                    nearLeftCheck.setSelected(false);
                    nearLeft.setEnabled(false);
                }
                if ((int) near.get(1) != -1) {
                    nearRightCheck.setSelected(true);
                    nearRight.setValue(near.get(1));
                    nearRight.setEnabled(true);
                } else {
                    nearRightCheck.setSelected(false);
                    nearRight.setEnabled(false);
                }
                if ((int) near.get(2) != -1) {
                    nearBothCheck.setSelected(true);
                    nearBoth.setValue(near.get(2));
                    nearBoth.setEnabled(true);
                } else {
                    nearBothCheck.setSelected(false);
                    nearBoth.setEnabled(false);
                }
            }

        }
    }

    class LabAndDes {

        public void tableLoad(Types type) {
            switch (type) {
                case Desseases:
                    CommonUi.tableload("SELECT Value FROM health_defaults WHERE Type='" + type.getName() + "'", Desseases, DBconnect.CONN);
                    break;
                case LabReports:
                    CommonUi.tableload("SELECT Value FROM health_defaults WHERE Type='" + type.getName() + "'", LabReports, DBconnect.CONN);
                    break;
            }
        }

        public void setFrame() {
            tableLoad(Types.Desseases);
            tableLoad(Types.LabReports);
            lab.setModel(new DefaultComboBoxModel(Health.getDefaults(Types.LabReports).toArray()));
            des.setModel(new DefaultComboBoxModel(Health.getDefaults(Types.Desseases).toArray()));
        }

        public void load(String id, Types type, String typeOfType) {
            try {
                switch (type) {
                    case LabReports:
                        com.HealthReport.LabReports l = new com.HealthReport.LabReports(id);
                        if (l.hasData(typeOfType)) {
                            Info_Lab.setText(l.getInfo(typeOfType));
                            labNormal.setSelected(l.isNormal(typeOfType));
                            labAbnormal.setSelected(l.isAbnormal(typeOfType));
                        }
                        break;
                    case Desseases:
                        com.HealthReport.Desseases d = new com.HealthReport.Desseases(id);
                        if (d.hasData(typeOfType)) {
                            Info_des.setText(d.getInfo(typeOfType));
                            desNotInfected.setSelected(d.isNotinfected(typeOfType));
                            labAbnormal.setSelected(d.isInfected(typeOfType));
                        }
                        break;
                }
            } catch (Exception e) {
                Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    class growth {

        public String gender = "Male";
        public String preSelW = "Kilo grames";
        public String preSelL = "Centi meters";

        public double getBmi() {
            return com.HealthReport.Growth.getBmi(Double.valueOf(weight.getText()), getWeightUnit(weightUnit.getSelectedItem().toString()), Double.valueOf(height.getText()), getLengthUnit(heightUnit.getSelectedItem().toString()));
        }

        public void getStatus() {
            bmiStatus.setText(getStatus(Id));

        }

        public String getStatus(String id) {
            return com.HealthReport.Growth.getWeightStatus(Double.parseDouble(BMI.getText()), Student.getAge(id), Student.getGender(id));
        }

        public boolean checkBmi() {
            double bmi = getBmi();
            if (bmi > 3.) {
                setMessage("BMI is not valid", MessageType.ERROR, Messages.TIME);
            }
            return bmi != Double.NaN && Double.isFinite(bmi) && bmi > 3.;
        }

        public void setGender(String gender) {
            this.gender = gender;
            male.setSelected(gender.equals("Male"));
            maleActionPerformed(null);
        }

        public void setBmi() {
            BMI.setText(new RoundingOff(getBmi(), new PlaceValue(PlaceValue.PREFPLACE, 3)).getNumber() + "");
            bmiStatus.setText(getStatus(Id));
        }

        public void load(String id) {
            com.HealthReport.Growth student = new com.HealthReport.Growth(id);
            if (com.HealthReport.Growth.hasData(id)) {
                List<Object> data = student.getAll();
                weight.setText(data.get(1).toString());
                weightUnit.setSelectedItem(getLengthUnit(Length.valueOf(data.get(6).toString())));
                height.setText(data.get(2).toString());
                heightUnit.setSelectedItem(getWeightUnit(Mass.valueOf(data.get(5).toString())));
                bmiStatus.setText(data.get(3).toString());
                growth.setGender(Student.getGender(id));
            }
        }

    }

    class Immune {

        public void setFrame() {
            Object[] toArray = Immunition.gatNames().toArray();
            String[] arr = new String[toArray.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = toArray[i].toString();
            }
            jComboBox13.setModel(new DefaultComboBoxModel<>(arr));
            arr = new String[Immunition.getDosses((String) jComboBox13.getSelectedItem())];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (i + 1) + "";
            }
            jComboBox12.setModel(new DefaultComboBoxModel<>(arr));
            CommonUi.tableload("SELECT  name AS Name,doses AS Doses FROM immune_progs", ImmunityTable, CONN);
            load();
        }

        public void load() {
            List<Object> dates = Sql.getColumn("SELECT given_date FROM immunity WHERE id='" + String.join("' OR id='", selectedIds) + "'AND name='" + jComboBox13.getSelectedItem() + "'", 0, CONN);
            List<Object> doses = Sql.getColumn("SELECT given_date FROM immunity WHERE id='" + String.join("' OR id='", selectedIds) + "' AND name='" + jComboBox13.getSelectedItem() + "' AND dose='" + jComboBox12.getSelectedItem() + "'", 0, CONN);
            if (!dates.isEmpty()) {
                boolean eqlDates = false;
                Date d = null;
                for (Object date : dates) {
                    if (d != null) {
                        eqlDates = date == d;
                    }
                    date = d;
                }
                boolean eqlDoses = false;
                for (Object dose : doses) {
                    if (d != null) {
                        eqlDoses = dose == d;
                    }
                    dose = d;
                }
                if (eqlDates) {
                    jDateChooser3.setDate(new Date((long) dates.get(0)));
                } else if (!eqlDates) {
                    CommonUi.setText(jDateChooser3, "<Defferent vals>");
                }
                if (eqlDoses) {
                    jComboBox12.setSelectedIndex((int) doses.get(0));
                } else if (!eqlDoses) {
                    CommonUi.setText(jComboBox12, "<Defferent vals>");
                }
            }
        }
    }

    public void selectFrame(JInternalFrame frame) {
        initFrames.remove(currentFrame);
        initFrames.add(frame);
        genarateCenter(frame);
        frame.setVisible(true);
        currentFrame = frame;
    }

    public void genarateCenter(JInternalFrame frame) {
        frame.setBounds((initFrames.getWidth() - FRAMEWIDTH) / 2, (initFrames.getHeight() - FRAMEHEIGHT) / 2, FRAMEWIDTH, FRAMEHEIGHT);
    }

    private void setMessage(String string, MessageType messageType, int TIME) {

    }

    public static Length getLengthUnit(String len) {
        switch (len) {
            case "Centi meters":
                return Length.CM;
            case "Meters":
                return Length.Meter;
            case "Yards":
                return Length.Yards;
            case "Feets":
                return Length.Feets;
            case "Mili meters":
                return Length.MM;
        }
        return Length.valueOf(len);
    }

    public static String getLengthUnit(Length len) {
        switch (len) {
            case CM:
                return "Centi meters";
            case Meter:
                return "Meters";
            case Yards:
                return "Yards";
            case Feets:
                return "Feets";
            case MM:
                return "Mili meters";
        }
        return "Centi meters";
    }

    public static Mass getWeightUnit(String we) {
        switch (we) {
            case "Kilo grames":
                return Mass.Kg;
            case "Pounds":
                return Mass.pounds;
            case "Grames":
                return Mass.g;
            case "Stones":
                return Mass.stone;
        }
        return Mass.valueOf(we);
    }

    public static String getWeightUnit(Mass we) {
        switch (we) {
            case Kg:
                return "Kilo grames";
            case pounds:
                return "Pounds";
            case g:
                return "Grames";
            case stone:
                return "Stones";
        }
        return "Kilo grames";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vissionAndHearingFrame = new javax.swing.JInternalFrame();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel124 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        farLeftCheck = new javax.swing.JCheckBox();
        farRightCheck = new javax.swing.JCheckBox();
        farBothCheck = new javax.swing.JCheckBox();
        VH_Select = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        farDenom = new javax.swing.JSpinner();
        farRight = new javax.swing.JSpinner();
        farBoth = new javax.swing.JSpinner();
        nearLeftCheck = new javax.swing.JCheckBox();
        nearRightCheck = new javax.swing.JCheckBox();
        nearBothCheck = new javax.swing.JCheckBox();
        nearDenom = new javax.swing.JSpinner();
        nearLeft = new javax.swing.JSpinner();
        nearRight = new javax.swing.JSpinner();
        nearBoth = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel128 = new javax.swing.JLabel();
        HR_VH_Status7 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        farStatus = new javax.swing.JTextField();
        nearStatus = new javax.swing.JTextField();
        farLeft = new javax.swing.JSpinner();
        jLabel131 = new javax.swing.JLabel();
        overrollStatus = new javax.swing.JTextField();
        labReportsAndDesseasesFrame = new javax.swing.JInternalFrame();
        jLabel160 = new javax.swing.JLabel();
        lab = new javax.swing.JComboBox<>();
        labAbnormal = new javax.swing.JCheckBox();
        labNormal = new javax.swing.JCheckBox();
        des = new javax.swing.JComboBox<>();
        desInfected = new javax.swing.JCheckBox();
        desNotInfected = new javax.swing.JCheckBox();
        jButton56 = new javax.swing.JButton();
        jLabel161 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        Info_des = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        Info_Lab = new javax.swing.JTextArea();
        growthFrame = new javax.swing.JInternalFrame();
        jLabel138 = new javax.swing.JLabel();
        weight = new javax.swing.JTextField();
        jLabel139 = new javax.swing.JLabel();
        height = new javax.swing.JTextField();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        BMI = new javax.swing.JTextField();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        heightUnit = new javax.swing.JComboBox<>();
        weightUnit = new javax.swing.JComboBox<>();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        bmiStatus = new javax.swing.JTextField();
        weightStatIcon = new javax.swing.JLabel();
        newImmunitiom = new javax.swing.JDialog();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jButton48 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jScrollPane4 = new javax.swing.JScrollPane();
        ImmunityTable = new javax.swing.JTable();
        ManageLabAndDes = new javax.swing.JDialog();
        jLabel158 = new javax.swing.JLabel();
        jButton55 = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        LabReports = new javax.swing.JTable();
        jButton52 = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        Desseases = new javax.swing.JTable();
        jTextField23 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jLabel172 = new javax.swing.JLabel();
        jButton60 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jDialog1 = new javax.swing.JDialog();
        jTextField1 = new javax.swing.JTextField();
        jLabel164 = new javax.swing.JLabel();
        male = new javax.swing.JRadioButton();
        female = new javax.swing.JRadioButton();
        Immunity = new javax.swing.JInternalFrame();
        jLabel146 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jButton50 = new javax.swing.JButton();
        jLabel150 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jComboBox13 = new javax.swing.JComboBox<>();
        jLabel156 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton71 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        initFrames = new javax.swing.JDesktopPane();

        vissionAndHearingFrame.setClosable(true);
        vissionAndHearingFrame.setTitle("Vission & Hearing");
        vissionAndHearingFrame.setFrameIcon(null);
        vissionAndHearingFrame.setVisible(true);
        vissionAndHearingFrame.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        vissionAndHearingFrame.getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 280, 0));
        vissionAndHearingFrame.getContentPane().add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 280, 10));

        jLabel124.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("L");
        vissionAndHearingFrame.getContentPane().add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 40, -1));

        jLabel132.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setText("R");
        vissionAndHearingFrame.getContentPane().add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 40, -1));

        jLabel133.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel133.setText("BOTH");
        vissionAndHearingFrame.getContentPane().add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 70, -1));

        jLabel134.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel134.setText("FAR");
        vissionAndHearingFrame.getContentPane().add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jButton46.setBackground(new java.awt.Color(0, 102, 204));
        jButton46.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jButton46.setForeground(new java.awt.Color(255, 255, 255));
        jButton46.setText("Save");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });
        vissionAndHearingFrame.getContentPane().add(jButton46, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, -1, -1));

        jButton47.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jButton47.setText("Clear");
        vissionAndHearingFrame.getContentPane().add(jButton47, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, -1, -1));

        farLeftCheck.setSelected(true);
        vissionAndHearingFrame.getContentPane().add(farLeftCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 20, -1));

        farRightCheck.setSelected(true);
        vissionAndHearingFrame.getContentPane().add(farRightCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 20, -1));
        vissionAndHearingFrame.getContentPane().add(farBothCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 20, -1));

        VH_Select.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vission", "Hearing" }));
        VH_Select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VH_SelectActionPerformed(evt);
            }
        });
        vissionAndHearingFrame.getContentPane().add(VH_Select, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, -1));

        jLabel1.setText("Select the type");
        vissionAndHearingFrame.getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        farDenom.setValue(9);
        vissionAndHearingFrame.getContentPane().add(farDenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 90, -1));
        vissionAndHearingFrame.getContentPane().add(farRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 50, -1));

        farBoth.setEnabled(false);
        vissionAndHearingFrame.getContentPane().add(farBoth, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 50, -1));

        nearLeftCheck.setSelected(true);
        vissionAndHearingFrame.getContentPane().add(nearLeftCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 20, -1));

        nearRightCheck.setSelected(true);
        vissionAndHearingFrame.getContentPane().add(nearRightCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 20, -1));
        vissionAndHearingFrame.getContentPane().add(nearBothCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, 20, -1));

        nearDenom.setValue(9);
        vissionAndHearingFrame.getContentPane().add(nearDenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 90, -1));
        vissionAndHearingFrame.getContentPane().add(nearLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 50, -1));
        vissionAndHearingFrame.getContentPane().add(nearRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 50, -1));

        nearBoth.setEnabled(false);
        vissionAndHearingFrame.getContentPane().add(nearBoth, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 50, -1));

        jLabel4.setText("Tested times");
        vissionAndHearingFrame.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, -1, -1));

        jLabel127.setText("Status");
        vissionAndHearingFrame.getContentPane().add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));
        vissionAndHearingFrame.getContentPane().add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, 280, 10));

        jLabel128.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setText("L");
        vissionAndHearingFrame.getContentPane().add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 40, -1));
        vissionAndHearingFrame.getContentPane().add(HR_VH_Status7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 250, 15));

        jLabel169.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel169.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel169.setText("R");
        vissionAndHearingFrame.getContentPane().add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 40, -1));

        jLabel170.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel170.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel170.setText("BOTH");
        vissionAndHearingFrame.getContentPane().add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 70, -1));

        jLabel171.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel171.setText("NEAR");
        vissionAndHearingFrame.getContentPane().add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        jLabel5.setText("Tested times");
        vissionAndHearingFrame.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, -1, -1));

        jLabel130.setText("Status");
        vissionAndHearingFrame.getContentPane().add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        farStatus.setEditable(false);
        farStatus.setBorder(null);
        vissionAndHearingFrame.getContentPane().add(farStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 110, -1));

        nearStatus.setEditable(false);
        nearStatus.setBorder(null);
        vissionAndHearingFrame.getContentPane().add(nearStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, 110, -1));
        vissionAndHearingFrame.getContentPane().add(farLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 50, -1));

        jLabel131.setText("Overall Status");
        vissionAndHearingFrame.getContentPane().add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        overrollStatus.setEditable(false);
        overrollStatus.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        overrollStatus.setBorder(null);
        vissionAndHearingFrame.getContentPane().add(overrollStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 190, -1));

        labReportsAndDesseasesFrame.setClosable(true);
        labReportsAndDesseasesFrame.setFrameIcon(null);
        labReportsAndDesseasesFrame.setVisible(false);
        labReportsAndDesseasesFrame.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel160.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel160.setText("Laboratary results");
        labReportsAndDesseasesFrame.getContentPane().add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(lab, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 170, -1));

        labAbnormal.setText("Abnormal");
        labAbnormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labAbnormalActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(labAbnormal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, -1, -1));

        labNormal.setSelected(true);
        labNormal.setText("Normal");
        labNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labNormalActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(labNormal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, -1, -1));

        des.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        des.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(des, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 170, -1));

        desInfected.setText("Infected");
        desInfected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desInfectedActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(desInfected, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 340, -1, -1));

        desNotInfected.setSelected(true);
        desNotInfected.setText("Not Infected");
        desNotInfected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desNotInfectedActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(desNotInfected, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 310, -1, -1));

        jButton56.setIcon(com.Codes.Commons.getImage("src\\Images\\Toolbar\\Plus-white.png",20,20));
        jButton56.setToolTipText("Add Lab result type /dessease");
        jButton56.setBorder(null);
        jButton56.setMaximumSize(new java.awt.Dimension(30, 30));
        jButton56.setMinimumSize(new java.awt.Dimension(30, 30));
        jButton56.setPreferredSize(new java.awt.Dimension(30, 30));
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(jButton56, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 30, 30));

        jLabel161.setText("Select the type");
        labReportsAndDesseasesFrame.getContentPane().add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel162.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel162.setText("Desseases");
        labReportsAndDesseasesFrame.getContentPane().add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabel163.setText("More information");
        labReportsAndDesseasesFrame.getContentPane().add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel173.setText("Status");
        labReportsAndDesseasesFrame.getContentPane().add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, -1, -1));

        jLabel174.setText("Select the type");
        labReportsAndDesseasesFrame.getContentPane().add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel175.setText("Status");
        labReportsAndDesseasesFrame.getContentPane().add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, -1, -1));

        jLabel176.setText("More information");
        labReportsAndDesseasesFrame.getContentPane().add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, -1, -1));

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        labReportsAndDesseasesFrame.getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, -1, -1));

        Info_des.setColumns(20);
        Info_des.setRows(5);
        jScrollPane5.setViewportView(Info_des);

        labReportsAndDesseasesFrame.getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        Info_Lab.setColumns(20);
        Info_Lab.setRows(5);
        jScrollPane6.setViewportView(Info_Lab);

        labReportsAndDesseasesFrame.getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        growthFrame.setClosable(true);
        growthFrame.setFrameIcon(null);
        growthFrame.setVisible(false);
        growthFrame.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel138.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel138.setText("Weight");
        growthFrame.getContentPane().add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        weight.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        weight.setText("0");
        weight.setNextFocusableComponent(height);
        weight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weightActionPerformed(evt);
            }
        });
        weight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                weightKeyReleased(evt);
            }
        });
        growthFrame.getContentPane().add(weight, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 250, -1));

        jLabel139.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel139.setText("Value");
        growthFrame.getContentPane().add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        height.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        height.setText("0");
        height.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heightActionPerformed(evt);
            }
        });
        height.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                weightKeyReleased(evt);
            }
        });
        growthFrame.getContentPane().add(height, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 250, -1));

        jLabel140.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel140.setText("Value");
        growthFrame.getContentPane().add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel141.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel141.setText("BMI");
        growthFrame.getContentPane().add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        BMI.setEditable(false);
        BMI.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        BMI.setText("0");
        BMI.setBorder(null);
        BMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BMIActionPerformed(evt);
            }
        });
        growthFrame.getContentPane().add(BMI, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 340, -1));

        jLabel142.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel142.setText("Status");
        growthFrame.getContentPane().add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        jLabel143.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel143.setText("Height");
        growthFrame.getContentPane().add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        heightUnit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        heightUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Meters", "Centi meters", "Feets", "Yards" }));
        growthFrame.getContentPane().add(heightUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 110, -1));

        weightUnit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        weightUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kilo grames", "Pounds", "Stones" }));
        weightUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weightUnitActionPerformed(evt);
            }
        });
        growthFrame.getContentPane().add(weightUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 110, -1));

        jButton58.setBackground(new java.awt.Color(0, 102, 204));
        jButton58.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jButton58.setForeground(new java.awt.Color(255, 255, 255));
        jButton58.setText("Save");
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });
        growthFrame.getContentPane().add(jButton58, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, -1, -1));

        jButton59.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jButton59.setText("Clear");
        growthFrame.getContentPane().add(jButton59, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, -1, -1));

        bmiStatus.setEditable(false);
        growthFrame.getContentPane().add(bmiStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 120, -1));

        weightStatIcon.setToolTipText("Status");
        growthFrame.getContentPane().add(weightStatIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 360, 25, 25));

        newImmunitiom.setTitle("New Vaccine");
        newImmunitiom.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        newImmunitiom.setType(java.awt.Window.Type.UTILITY);

        jLabel144.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel144.setText("Vaccine Name");

        jLabel145.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel145.setText("Number of dosses");

        jButton48.setText("Add");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        jButton49.setText("Delete");
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });

        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        ImmunityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Vaccine", "Number of dosses"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(ImmunityTable);

        javax.swing.GroupLayout newImmunitiomLayout = new javax.swing.GroupLayout(newImmunitiom.getContentPane());
        newImmunitiom.getContentPane().setLayout(newImmunitiomLayout);
        newImmunitiomLayout.setHorizontalGroup(
            newImmunitiomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newImmunitiomLayout.createSequentialGroup()
                .addGroup(newImmunitiomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(newImmunitiomLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(newImmunitiomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newImmunitiomLayout.createSequentialGroup()
                                .addComponent(jLabel145)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 321, Short.MAX_VALUE))
                            .addComponent(jSpinner1)))
                    .addGroup(newImmunitiomLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(newImmunitiomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel144)
                            .addComponent(jTextField21, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)))
                    .addGroup(newImmunitiomLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(newImmunitiomLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton48)
                        .addGap(8, 8, 8)
                        .addComponent(jButton49)))
                .addGap(15, 15, 15))
        );
        newImmunitiomLayout.setVerticalGroup(
            newImmunitiomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newImmunitiomLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel144)
                .addGap(3, 3, 3)
                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel145)
                .addGap(3, 3, 3)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(newImmunitiomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton48)
                    .addComponent(jButton49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        ManageLabAndDes.setTitle("Add/Remove Lab Results & Desseases");
        ManageLabAndDes.setModalExclusionType(null);
        ManageLabAndDes.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        ManageLabAndDes.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                ManageLabAndDesWindowClosing(evt);
            }
        });

        jLabel158.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel158.setText("Laboratary Result");

        jButton55.setText("Delete");
        jButton55.setBorder(null);
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });

        LabReports.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        LabReports.setMinimumSize(new java.awt.Dimension(275, 375));
        jScrollPane14.setViewportView(LabReports);

        jButton52.setText("Add");
        jButton52.setBorder(null);
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton52ActionPerformed(evt);
            }
        });

        Desseases.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane16.setViewportView(Desseases);

        jLabel172.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel172.setText("Desseases");

        jButton60.setText("Add");
        jButton60.setBorder(null);
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        jButton61.setText("Delete");
        jButton61.setBorder(null);
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ManageLabAndDesLayout = new javax.swing.GroupLayout(ManageLabAndDes.getContentPane());
        ManageLabAndDes.getContentPane().setLayout(ManageLabAndDesLayout);
        ManageLabAndDesLayout.setHorizontalGroup(
            ManageLabAndDesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageLabAndDesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel158))
            .addGroup(ManageLabAndDesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTextField23)
                .addGap(0, 0, 0)
                .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
            .addGroup(ManageLabAndDesLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addGap(22, 22, 22))
            .addGroup(ManageLabAndDesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel172))
            .addGroup(ManageLabAndDesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTextField25)
                .addGap(0, 0, 0)
                .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
            .addGroup(ManageLabAndDesLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane16)
                .addGap(22, 22, 22))
        );
        ManageLabAndDesLayout.setVerticalGroup(
            ManageLabAndDesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageLabAndDesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel158)
                .addGap(3, 3, 3)
                .addGroup(ManageLabAndDesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jLabel172)
                .addGap(3, 3, 3)
                .addGroup(ManageLabAndDesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        jDialog1.setAutoRequestFocus(false);
        jDialog1.setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        jDialog1.setUndecorated(true);
        jDialog1.setType(java.awt.Window.Type.POPUP);

        jTextField1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDialog1.getAccessibleContext().setAccessibleParent(this);

        jLabel164.setText("Gender");

        male.setSelected(true);
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

        Immunity.setClosable(true);
        Immunity.setFrameIcon(null);
        Immunity.setPreferredSize(new java.awt.Dimension(390, 490));
        Immunity.setVisible(true);
        Immunity.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel146.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel146.setText("Vaccine/Drug");
        Immunity.getContentPane().add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel148.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel148.setText("Vaccination");
        Immunity.getContentPane().add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        Immunity.getContentPane().add(jComboBox12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 240, -1));

        jButton50.setIcon(com.Codes.Commons.getImage("src\\Images\\Toolbar\\Plus-white.png",20,20));
        jButton50.setToolTipText("Add Vaccines");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });
        Immunity.getContentPane().add(jButton50, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 25, 25));

        jLabel150.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel150.setText("Given Date");
        Immunity.getContentPane().add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));
        Immunity.getContentPane().add(jDateChooser3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 240, -1));

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        Immunity.getContentPane().add(jComboBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 240, -1));

        jLabel156.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel156.setText("Dose");
        Immunity.getContentPane().add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jButton3.setText("Clear");
        Immunity.getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, -1, -1));

        jButton4.setBackground(new java.awt.Color(0, 102, 204));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Save");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        Immunity.getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, -1, -1));

        jButton57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton57.setIcon(Commons.getImage("src\\Images\\Health\\Growth.png",BUTTONRADIUS,BUTTONRADIUS));
        jButton57.setToolTipText("Growth");
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        jButton31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton31.setIcon(Commons.getImage("src\\Images\\Health\\Lab Reports.png",BUTTONRADIUS,BUTTONRADIUS));
        jButton31.setToolTipText("Lab reports & Desseases");
        jButton31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton31MouseEntered(evt);
            }
        });
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton71.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton71.setIcon(Commons.getImage("src\\Images\\Health\\Vision & Hearing.png",BUTTONRADIUS,BUTTONRADIUS));
        jButton71.setToolTipText("Vission & hearing");
        jButton71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton71MouseEntered(evt);
            }
        });
        jButton71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton71ActionPerformed(evt);
            }
        });

        jButton28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton28.setIcon(Commons.getImage("src\\Images\\Health\\Immunity.png",BUTTONRADIUS,BUTTONRADIUS));
        jButton28.setToolTipText("Immunition");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        initFrames.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout initFramesLayout = new javax.swing.GroupLayout(initFrames);
        initFrames.setLayout(initFramesLayout);
        initFramesLayout.setHorizontalGroup(
            initFramesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );
        initFramesLayout.setVerticalGroup(
            initFramesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton57, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton71, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(initFrames)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(initFrames)
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton71, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 106, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        String sel = VH_Select.getSelectedItem().toString();
        String[] ids = {"1024", "1025"};
        int[] n = visAndHear.getNears();
        int[] f = visAndHear.getFars();
        for (String id : ids) {
            if (sel.equals("Vission")) {
                Vision vis = new Vision(id);
                vis.setVissions(f[0], f[1], f[2], f[3], Vision.FAR);
                vis = new Vision(id);
                vis.setVissions(n[0], n[1], n[2], f[3], Vision.NEAR);
            } else if (sel.equals("Hearing")) {
                Hearing vis = new Hearing(id, f[3]);
                vis.setHearings(f[0], f[1], f[2], Vision.FAR);
                vis = new Hearing(id, n[3]);
                vis.setHearings(n[0], n[1], n[2], Vision.NEAR);
            }
        }
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        selectFrame(labReportsAndDesseasesFrame);
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton52ActionPerformed
        String val = jTextField23.getText();
        try {
            Health.setDefault(Types.LabReports, val);
            labDes.tableLoad(Types.LabReports);
        } catch (Exception ex) {
            Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton52ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        for (int row : LabReports.getSelectedRows()) {
            try {
                Health.removeDefault(Types.LabReports, LabReports.getValueAt(row, 0).toString());
                labDes.tableLoad(Types.LabReports);
            } catch (Exception ex) {
                Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton55ActionPerformed

    private void labAbnormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labAbnormalActionPerformed
        labNormal.setSelected(!labAbnormal.isSelected());
    }//GEN-LAST:event_labAbnormalActionPerformed

    private void labNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labNormalActionPerformed
        labAbnormal.setSelected(!labNormal.isSelected());
    }//GEN-LAST:event_labNormalActionPerformed

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed
        CommonUi.genarateCenter(ManageLabAndDes, 500, 370);
        ManageLabAndDes.setVisible(true);
    }//GEN-LAST:event_jButton56ActionPerformed

    private void desInfectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desInfectedActionPerformed
        desNotInfected.setSelected(!desInfected.isSelected());
    }//GEN-LAST:event_desInfectedActionPerformed

    private void desNotInfectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desNotInfectedActionPerformed
        desInfected.setSelected(!desNotInfected.isSelected());
    }//GEN-LAST:event_desNotInfectedActionPerformed

    private void weightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightActionPerformed
        weight.transferFocus();
    }//GEN-LAST:event_weightActionPerformed

    private void heightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heightActionPerformed
        height.transferFocus();
    }//GEN-LAST:event_heightActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
        com.HealthReport.Growth gr = new com.HealthReport.Growth(Id);
        gr.setStatus(bmiStatus.getText());
        gr.setAll(Double.valueOf(weight.getText()), getWeightUnit(weightUnit.getSelectedItem().toString()), Double.valueOf(height.getText()), getLengthUnit(heightUnit.getSelectedItem().toString()));
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        CommonUi.genarateCenter(newImmunitiom, 370, 390);
        newImmunitiom.setVisible(true);
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jButton71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton71ActionPerformed
        selectFrame(vissionAndHearingFrame);
    }//GEN-LAST:event_jButton71ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        selectFrame(growthFrame);
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        selectFrame(Immunity);
    }//GEN-LAST:event_jButton28ActionPerformed

    private void VH_SelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VH_SelectActionPerformed
        String id = "1024";
        visAndHear.load(id);
        visAndHear.setFullStatus();
    }//GEN-LAST:event_VH_SelectActionPerformed

    private void jButton31MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton31MouseEntered
        validate();
        doLayout();
    }//GEN-LAST:event_jButton31MouseEntered

    private void jButton71MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton71MouseEntered
        validate();
        doLayout();
    }//GEN-LAST:event_jButton71MouseEntered

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        String val = jTextField25.getText();
        try {
            Health.setDefault(Types.Desseases, val);
            labDes.tableLoad(Types.Desseases);
        } catch (Exception ex) {
            Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        for (int row : Desseases.getSelectedRows()) {
            try {
                Health.removeDefault(Types.Desseases, Desseases.getValueAt(row, 0).toString());
                labDes.tableLoad(Types.Desseases);
            } catch (Exception ex) {
                Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton61ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for (String id : selectedIds) {
            String type = lab.getSelectedItem().toString();
            String status = labNormal.isSelected() ? Health.NORMAL : Health.ABNORMAL;
            String info = "";
            try {
                info = String.join("\n", CommonUi.getArray(Info_Lab));
            } catch (BadLocationException ex) {
                Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                new LabReports(id).insertData(type, status, info);
            } catch (Exception ex) {
                Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            type = des.getSelectedItem().toString();
            status = desInfected.isSelected() ? Health.INFECTED : Health.NOTINFECTED;
            info = "";
            try {
                info = String.join("\n", CommonUi.getArray(Info_des));
            } catch (BadLocationException ex) {
                Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                com.HealthReport.Desseases d = new Desseases(id);
                d.insertData(type, status, info);
            } catch (Exception ex) {
                Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged

    }//GEN-LAST:event_jSpinner1StateChanged

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        String name = jTextField21.getText();
        Object doses = jSpinner1.getValue();
        if (doses instanceof Number) {
            if ((int) doses > 1 && !name.equals("")) {
                try {
                    Immunition.addNew(name, (int) doses);
                    immune.setFrame();
                } catch (Exception ex) {
                    new TrayMessage(ex.getMessage(), AppConfig.APPNAME, TrayIcon.MessageType.ERROR).display();
                }
            }
        } else {
            setMessage("Number Of Dosses must be possitive integer", MessageType.ERROR, Messages.TIME);
        }
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        for (int row : ImmunityTable.getSelectedRows()) {
            Immunition.remove(ImmunityTable.getValueAt(row, 0).toString());
        }
    }//GEN-LAST:event_jButton49ActionPerformed

    private void ManageLabAndDesWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ManageLabAndDesWindowClosing
        labDes.setFrame();
    }//GEN-LAST:event_ManageLabAndDesWindowClosing

    private void labActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labActionPerformed
        String id = Id;
        labDes.load(id, Types.LabReports, lab.getSelectedItem().toString());
    }//GEN-LAST:event_labActionPerformed

    private void desActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desActionPerformed
        String id = Id;
        labDes.load(id, Types.Desseases, des.getSelectedItem().toString());
    }//GEN-LAST:event_desActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            CommonUi.clearAll(labReportsAndDesseasesFrame);
        } catch (Exception ex) {
            Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BMIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BMIActionPerformed

    private void weightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weightKeyReleased
        if (growth.checkBmi()) {
            growth.setBmi();
        }
    }//GEN-LAST:event_weightKeyReleased

    private void maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleActionPerformed
        female.setSelected(!male.isSelected());
    }//GEN-LAST:event_maleActionPerformed

    private void femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleActionPerformed
        male.setSelected(!female.isSelected());
    }//GEN-LAST:event_femaleActionPerformed

    private void weightUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightUnitActionPerformed

    }//GEN-LAST:event_weightUnitActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        for (String id : selectedIds) {
            new Immunition(id).set(jComboBox13.getSelectedItem().toString(), Integer.valueOf(jComboBox12.getSelectedItem().toString()), jDateChooser3.getDate());
        }
    }//GEN-LAST:event_jButton4ActionPerformed
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Health_frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 1100, 600);
//        JDesktopPane pane = new JDesktopPane();
//        pane.setBounds(10, 10, 1080, 580);
//        Health_frame hf = new Health_frame(null);
//        frame.add(hf);
//        hf.addNotify();
//        hf.setBounds(10, 10, 1050, 560);
//        hf.setVisible(true);

        frame.setVisible(true);
        frame.validate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BMI;
    private javax.swing.JTable Desseases;
    private javax.swing.JLabel HR_VH_Status7;
    private javax.swing.JInternalFrame Immunity;
    private javax.swing.JTable ImmunityTable;
    private javax.swing.JTextArea Info_Lab;
    private javax.swing.JTextArea Info_des;
    private javax.swing.JTable LabReports;
    private javax.swing.JDialog ManageLabAndDes;
    private javax.swing.JComboBox<String> VH_Select;
    private javax.swing.JTextField bmiStatus;
    private javax.swing.JComboBox<String> des;
    private javax.swing.JCheckBox desInfected;
    private javax.swing.JCheckBox desNotInfected;
    private javax.swing.JSpinner farBoth;
    private javax.swing.JCheckBox farBothCheck;
    private javax.swing.JSpinner farDenom;
    private javax.swing.JSpinner farLeft;
    private javax.swing.JCheckBox farLeftCheck;
    private javax.swing.JSpinner farRight;
    private javax.swing.JCheckBox farRightCheck;
    private javax.swing.JTextField farStatus;
    private javax.swing.JRadioButton female;
    private javax.swing.JInternalFrame growthFrame;
    private javax.swing.JTextField height;
    private javax.swing.JComboBox<String> heightUnit;
    private javax.swing.JDesktopPane initFrames;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton71;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JComboBox<String> lab;
    private javax.swing.JCheckBox labAbnormal;
    private javax.swing.JCheckBox labNormal;
    private javax.swing.JInternalFrame labReportsAndDesseasesFrame;
    private javax.swing.JRadioButton male;
    private javax.swing.JSpinner nearBoth;
    private javax.swing.JCheckBox nearBothCheck;
    private javax.swing.JSpinner nearDenom;
    private javax.swing.JSpinner nearLeft;
    private javax.swing.JCheckBox nearLeftCheck;
    private javax.swing.JSpinner nearRight;
    private javax.swing.JCheckBox nearRightCheck;
    private javax.swing.JTextField nearStatus;
    private javax.swing.JDialog newImmunitiom;
    private javax.swing.JTextField overrollStatus;
    private javax.swing.JInternalFrame vissionAndHearingFrame;
    private javax.swing.JTextField weight;
    private javax.swing.JLabel weightStatIcon;
    private javax.swing.JComboBox<String> weightUnit;
    // End of variables declaration//GEN-END:variables

}
