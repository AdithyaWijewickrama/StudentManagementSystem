package com.ui.internalFrames;

import Standard.array;
import com.ui.components.TextField;
import com.ui.CommonUi;
import static com.ui.CommonUi.gsi;
import com.Codes.Commons;
import static com.Codes.Commons.getImage;
import com.ManageDetails.student.Student;
import com.Communication.Messages;
import com.Communication.SysMsg;
import com.ManageDetails.student.CurrentStudent;
import static com.ui.frames.UserFrame.getBorW;
import static com.MarksReport.Marks.getGrades;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.TrayIcon.MessageType;
import com.Main.Selection;
import com.MarksReport.ALevel;
import com.MarksReport.Fields;
import com.MarksReport.Marks;
import com.MarksReport.Subjects;
import com.formdev.flatlaf.FlatLightLaf;
import com.ui.CommonUi;
import com.ui.components.TextField;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;

public class Marks_frame extends javax.swing.JInternalFrame {

    public String Id;
    public String selectedTerm;
    int currentWidth;
    int grade = 1;
    public JPanel MR_CurrentPanel;
    public JInternalFrame currentFrame;
    public String saveDir;
    public Selection selection;
    public CurrentStudent CurrentStudent;
    public JTable table;
    public HashMap<Container, ArrayList<JTextField>> fields;
    public ArrayList<JTextField> alFields;
    public ArrayList<JTextField> olFields;
    public ArrayList<JTextField> priFields;
    public ArrayList<JTextField> junFields;

    public Marks_frame(Selection slc, CurrentStudent CurrentStudent) {
        initComponents();
        validate();
        repaint();
        this.currentFrame = JIN1;

        this.selection = slc;
        this.table = selection.Table;
        grade = Integer.parseInt(gsi(gradeSelection));
        for (Component t : selectTerm.getComponents()) {
            if (t instanceof JButton) {
                JButton btn = (JButton) t;
                btn.addActionListener((ActionEvent e) -> {
                    for (Component t1 : selectTerm.getComponents()) {
                        if (t1 instanceof JButton) {
                            ((JButton) t1).setSelected(t1.equals(t));
                        }
                    }
                    load(table.getValueAt(table.getSelectedRow(), 0).toString(), getTerm(), getGrade());
                });
            }
        }
        find.setIcon(getImage("src\\Images\\Toolbar\\Search-" + getBorW() + ".png", 25, 25));
        setFields();
        ALFields.setModel(new DefaultComboBoxModel(ALevel.getStreams().toArray()));
        currentWidth = fields.get(JIN1).size();
        TextField t = new TextField(findText);
        t.setMinDigit('1');
        t.setAsStandardNumberField();
        t.setDotAlowed(false);
    }

    void setFields() {
        fields = new Fields(JIN1, JIN6, JIN10, JIN12).fields;
        olFields = fields.get(JIN10);
        alFields = fields.get(JIN12);
        priFields = fields.get(JIN1);
        junFields = fields.get(JIN6);
        for (Map.Entry<Container, ArrayList<JTextField>> e : fields.entrySet()) {
            ArrayList<JTextField> value = e.getValue();
            value.stream().map((tf) -> new TextField(tf)).map((tfs) -> {
                tfs.setAsStandardNumberField();
                return tfs;
            }).forEachOrdered((tfs) -> {
                tfs.clearWhenFocused(true);
            });
        }
    }

    public String getFrameName() {
        String GS = sel_Subgrade.getSelectedItem().toString();
        return String.format("Marks of Grade %s%s Students", grade, GS);
    }

    public JLabel[] getLabels() {
        JLabel Labels[] = new JLabel[currentWidth];
        Fields.getLabels(currentFrame, currentWidth).toArray(Labels);
        return Labels;
    }

    public String[] getSubjects(int g) {
        String[] Subjects = new Subjects(g).getSubs();
        return Subjects;
    }

    public String getTerm() {
        for (Component c : selectTerm.getComponents()) {
            if (c instanceof JButton) {
                if (((JButton) c).isSelected()) {
                    return ((JButton) c).getText();
                }
            }
        }
        return "";
    }

    public void setTerm(String term) {
        for (Component c : selectTerm.getComponents()) {
            if (c instanceof JButton) {
                if (((JButton) c).getText().equals(term)) {
                    if (!((JButton) c).isSelected()) {
                        ((JButton) c).doClick();
                    }
                }
            }
        }
    }

    private void setALStreams() {
        ALFields.setModel(new DefaultComboBoxModel<>(array.getStr(ALevel.getStreams())));
    }

    private void setALFields(String streamName) {
        List<Object> fd = ALevel.getSubs(streamName);
        fd = fd == null || streamName == null ? ALevel.getSubs("Default") : fd;
        Sbj1.setText(fd.get(0) + "");
        Sbj2.setText(fd.get(1) + "");
        Sbj3.setText(fd.get(2) + "");
        SBJ1.setText(fd.get(0) + "");
        SBJ2.setText(fd.get(1) + "");
        SBJ3.setText(fd.get(2) + "");
    }

    private void setStFeild(String id) {
        setALFields(ALevel.getStream(id, getGrade(), getTerm()));
    }

    public void setStudentPossition(String id, String term) {
        pos.setText("Calculating...");
        String subGrade = getSubGrade();
        int grade = Integer.parseInt(gradeSelection.getSelectedItem().toString());
        List<Object> poses;
        try {
            poses = subGrade.equals("*All")
                    ? new com.MarksReport.Marks(id, term, grade).getPossitions()
                    : com.MarksReport.Marks.getPossitions(id, term, grade, subGrade);
            if (poses == null) {
                pos.setText("Possiotion");
            } else if (poses.size() > 1) {
                setMessage("There are " + poses.size() + " Students that have " + total.getText() + " total marks", MessageType.WARNING, 0);
                pos.setText(poses.get(0) + "");
            } else if (poses.size() == 1) {
                pos.setText(poses.get(0) + "");
            }
        } catch (Exception ex) {
            Commons.showMsg(ex);
        }
    }

    public void getFocus(char c, JTextField txt) {
        Container con = currentFrame;
        int i = fields.get(con).indexOf(txt);
        if ((int) c == (int) 'w') {
            if (i != -1 && i - 1 > 0) {
                fields.get(con).get(i - 1).requestFocus();
            }
        } else if ((int) c == (int) 's') {
            if (i != -1 && i + 1 < currentWidth) {
                fields.get(con).get(i + 1).requestFocus();
            } else {
                update_btn.requestFocus();
            }
        }
    }

    public void setExamBtns(int g) {
        List<Component> btns = Arrays.asList(selectTerm.getComponents());
        if (!btns.contains(exams_label)) {
            selectTerm.add(exams_label);
        }
        if (!btns.contains(ol_btn)) {
            selectTerm.add(ol_btn);
        }
//        if(!btns.contains(al_btn)){
//            selectTerm.add(al_btn);
//        }
        if (!btns.contains(sc_05)) {
            selectTerm.add(sc_05);
        }
        if (g < 5) {
            selectTerm.remove(sc_05);
        } else if (g < 12) {
            selectTerm.remove(ol_btn);
        }
    }

    public final void changeGrade(int grade) {
        if (currentFrame != null) {
            MR_InitFrames.remove(currentFrame);
            currentFrame.setVisible(false);
        }
        if (grade < 6) {
            currentFrame = JIN1;
        } else if (grade < 10) {
            currentFrame = JIN6;
        } else if (grade < 12) {
            currentFrame = JIN10;
        } else if (grade < 14) {
            currentFrame = JIN12;
        }
        try {
            MR_InitFrames.add(currentFrame);
            MR_InitFrames.validate();
            MR_InitFrames.repaint();
        } catch (Exception e) {
            Commons.showMsg(e);
        }
        currentFrame.setBounds((MR_InitFrames.getWidth() - 370) / 2, (MR_InitFrames.getHeight() - 440) / 2, 370, 440);
        currentFrame.setVisible(true);
        currentWidth = fields.get(currentFrame).size();
    }

    public void load(String id, String term, int gr) {
        Student.setData(id, admission, name);
        ArrayList<JTextField> tf = fields.get(currentFrame);
        Marks mrk = new com.MarksReport.Marks(id, term, gr);
        if (mrk.hasData()) {
            ArrayList<Double> m2 = mrk.getMarks();
            Double tot = mrk.getTotal();
            Double avr = mrk.getAverage();
            for (int u = 0; u < currentWidth; u++) {
                tf.get(u).setText(Marks.getps(m2.get(u)));
            }
            total.setText(Marks.getps(tot));
            average.setText(Marks.getps(avr));
            progress.setValue(avr.intValue());
            setMarksGrades(id, term, gr);
            setStudentPossition(id, term);
            if (gr < 14 && gr > 11) {
                String strm = ALevel.getStream(id, getGrade(), term);
                setALFields(strm);
                ALFields.setSelectedItem(strm);
            }
        } else {
            setMessage(id + " has no data", MessageType.INFO, Messages.TIME);
        }
    }

    public void Consume(KeyEvent evt, JTextField txt) {
        Container con = currentFrame;
        int i = fields.get(con).indexOf(txt);
        char Key = evt.getKeyChar();
        if (Key == KeyEvent.VK_ENTER) {
            if (i != -1 && i + 1 < fields.get(con).size()) {
                fields.get(con).get(i + 1).requestFocus();
            } else {
                update_btn.requestFocus();
            }
        }
    }

    public void setMarksGrades(ArrayList<JTextField> marksFields, ArrayList<JLabel> gradesLabels, int w) {
        char Grades[] = getGrades(getVals(marksFields));
        for (int i = 0; i < w; i++) {
            gradesLabels.get(i).setText(Character.toString(Grades[i]));
        }
    }

    public void setMarksGrades(String id, String term, int grade) {
        char Grades[] = getGrades(Marks.getMarks(id, term, grade));
        ArrayList<JLabel> Labels = Fields.getLabels(currentFrame, currentWidth);
        for (int i = 0; i < currentWidth; i++) {
            Labels.get(i).setText(Character.toString(Grades[i]));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        marks = new javax.swing.JPanel();
        JIN6 = new javax.swing.JInternalFrame();
        j_religion = new javax.swing.JTextField();
        p_pLng = new javax.swing.JTextField();
        p_eng = new javax.swing.JTextField();
        d6 = new javax.swing.JTextField();
        p_science = new javax.swing.JTextField();
        p_history = new javax.swing.JTextField();
        g6 = new javax.swing.JTextField();
        p_civics = new javax.swing.JTextField();
        i6 = new javax.swing.JTextField();
        p_estc = new javax.swing.JTextField();
        k6 = new javax.swing.JTextField();
        l6 = new javax.swing.JTextField();
        n6 = new javax.swing.JTextField();
        jLabel195 = new javax.swing.JLabel();
        jLabel207 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jLabel192 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jLabel205 = new javax.swing.JLabel();
        jLabel203 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        jLabel562 = new javax.swing.JLabel();
        jLabel563 = new javax.swing.JLabel();
        jLabel564 = new javax.swing.JLabel();
        jLabel565 = new javax.swing.JLabel();
        jLabel567 = new javax.swing.JLabel();
        jLabel568 = new javax.swing.JLabel();
        jLabel785 = new javax.swing.JLabel();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jLabel188 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        jLabel569 = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        jLabel570 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        jLabel571 = new javax.swing.JLabel();
        jLabel572 = new javax.swing.JLabel();
        jLabel199 = new javax.swing.JLabel();
        jLabel200 = new javax.swing.JLabel();
        jLabel573 = new javax.swing.JLabel();
        jLabel206 = new javax.swing.JLabel();
        jLabel574 = new javax.swing.JLabel();
        JIN12 = new javax.swing.JInternalFrame();
        a11 = new javax.swing.JTextField();
        b11 = new javax.swing.JTextField();
        c11 = new javax.swing.JTextField();
        d11 = new javax.swing.JTextField();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel590 = new javax.swing.JLabel();
        SBJ1 = new javax.swing.JLabel();
        SBJ3 = new javax.swing.JLabel();
        SBJ2 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel597 = new javax.swing.JLabel();
        jLabel598 = new javax.swing.JLabel();
        jLabel790 = new javax.swing.JLabel();
        Sbj1 = new javax.swing.JTextField();
        Sbj3 = new javax.swing.JTextField();
        Sbj2 = new javax.swing.JTextField();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        MR_Delete = new javax.swing.JButton();
        Add = new javax.swing.JButton();
        jLabel599 = new javax.swing.JLabel();
        Field = new javax.swing.JTextField();
        jLabel163 = new javax.swing.JLabel();
        ALFields = new javax.swing.JComboBox<>();
        d12 = new javax.swing.JTextField();
        jLabel591 = new javax.swing.JLabel();
        jLabel210 = new javax.swing.JLabel();
        JIN10 = new javax.swing.JInternalFrame();
        a10 = new javax.swing.JTextField();
        b10 = new javax.swing.JTextField();
        c10 = new javax.swing.JTextField();
        d10 = new javax.swing.JTextField();
        e10 = new javax.swing.JTextField();
        f10 = new javax.swing.JTextField();
        g10 = new javax.swing.JTextField();
        h10 = new javax.swing.JTextField();
        i10 = new javax.swing.JTextField();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jLabel582 = new javax.swing.JLabel();
        jLabel583 = new javax.swing.JLabel();
        jLabel584 = new javax.swing.JLabel();
        jLabel585 = new javax.swing.JLabel();
        jLabel586 = new javax.swing.JLabel();
        jLabel587 = new javax.swing.JLabel();
        jLabel787 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel588 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jLabel589 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        al_btn = new javax.swing.JButton();
        ol_btn = new javax.swing.JButton();
        sc_05 = new javax.swing.JButton();
        exams_label = new javax.swing.JLabel();
        jDialog1 = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        buttonGroup1 = new javax.swing.ButtonGroup();
        MR_jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        selectTerm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton46 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton49 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        MR_InitFrames = new javax.swing.JDesktopPane();
        JIN1 = new javax.swing.JInternalFrame();
        p_religion = new javax.swing.JTextField();
        p_prLng = new javax.swing.JTextField();
        p_english = new javax.swing.JTextField();
        p_maths = new javax.swing.JTextField();
        p_env = new javax.swing.JTextField();
        p_pts = new javax.swing.JTextField();
        p_secLng = new javax.swing.JTextField();
        p_read = new javax.swing.JTextField();
        p_write = new javax.swing.JTextField();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel179 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jLabel566 = new javax.swing.JLabel();
        jLabel575 = new javax.swing.JLabel();
        jLabel576 = new javax.swing.JLabel();
        jLabel577 = new javax.swing.JLabel();
        jLabel578 = new javax.swing.JLabel();
        jLabel579 = new javax.swing.JLabel();
        jLabel786 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel580 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jLabel581 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jToolBar4 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        gradeSelection = new javax.swing.JComboBox<>();
        sel_Subgrade = new javax.swing.JComboBox<>();
        MR_Panel1 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel678 = new javax.swing.JLabel();
        pos = new javax.swing.JTextField();
        jLabel673 = new javax.swing.JLabel();
        jLabel675 = new javax.swing.JLabel();
        progress = new javax.swing.JProgressBar();
        update_btn = new javax.swing.JButton();
        jLabel679 = new javax.swing.JLabel();
        jLabel680 = new javax.swing.JLabel();
        admission = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        average = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel208 = new javax.swing.JLabel();
        jLabel676 = new javax.swing.JLabel();
        jLabel674 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        Progressf = new javax.swing.JProgressBar();
        jLabel677 = new javax.swing.JLabel();
        idBoxf = new javax.swing.JTextField();
        nameBoxf = new javax.swing.JTextField();
        totalF = new javax.swing.JTextField();
        avrf = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        findText = new javax.swing.JTextField();
        find = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        JIN6.setFrameIcon(null);
        JIN6.setVisible(false);
        JIN6.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        j_religion.setToolTipText("Enter the marks and press Enter");
        j_religion.setName("Junior1"); // NOI18N
        j_religion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(j_religion, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 80, -1));

        p_pLng.setName("Junior2"); // NOI18N
        p_pLng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(p_pLng, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 80, -1));

        p_eng.setToolTipText("Enter the marks and press Enter");
        p_eng.setName("Junior3"); // NOI18N
        p_eng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(p_eng, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 80, -1));

        d6.setToolTipText("Enter the marks and press Enter");
        d6.setName("Junior4"); // NOI18N
        d6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(d6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 80, -1));

        p_science.setToolTipText("Enter the marks and press Enter");
        p_science.setName("Junior"); // NOI18N
        p_science.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_scienceActionPerformed(evt);
            }
        });
        p_science.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(p_science, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 80, -1));

        p_history.setToolTipText("Enter the marks and press Enter");
        p_history.setName("Junior"); // NOI18N
        p_history.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(p_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 80, -1));

        g6.setToolTipText("Enter the marks and press Enter");
        g6.setName("Junior"); // NOI18N
        g6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g6ActionPerformed(evt);
            }
        });
        g6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(g6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 80, -1));

        p_civics.setToolTipText("Enter the marks and press Enter");
        p_civics.setName("Junior"); // NOI18N
        p_civics.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(p_civics, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 80, -1));

        i6.setToolTipText("Enter the marks and press Enter");
        i6.setName("Junior"); // NOI18N
        i6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i6ActionPerformed(evt);
            }
        });
        i6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(i6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 80, -1));

        p_estc.setToolTipText("Enter the marks and press Enter");
        p_estc.setName("Junior"); // NOI18N
        p_estc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(p_estc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 80, -1));

        k6.setToolTipText("Enter the marks and press Enter");
        k6.setName("Junior"); // NOI18N
        k6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(k6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, 80, -1));

        l6.setToolTipText("Enter the marks and press Enter");
        l6.setName("Junior"); // NOI18N
        l6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(l6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 80, -1));

        n6.setToolTipText("Enter the marks and press Enter");
        n6.setName("Junior"); // NOI18N
        n6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G6KeyTyped(evt);
            }
        });
        JIN6.getContentPane().add(n6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 80, -1));

        jLabel195.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel195.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel195.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel195, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 20, 20));

        jLabel207.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel207.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel207.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel207, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 20, 20));

        jLabel201.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel201.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel201.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel201, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 20, 20));

        jLabel192.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel192.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel192.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel192, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 20, 20));

        jLabel191.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel191.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel191.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel191, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 20, 20));

        jLabel204.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel204.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel204.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel204, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 20, 20));

        jLabel190.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel190.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel190.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel190, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 20, 20));

        jLabel193.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel193.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel193.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel193, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, 20, 20));

        jLabel194.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel194.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel194.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel194, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 20, 20));

        jLabel205.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel205.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel205.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel205, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 20, 20));

        jLabel203.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel203.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel203.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel203, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 20, 20));

        jLabel202.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel202.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel202.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel202, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 20, 20));

        jLabel189.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel189.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel189.setName("Junior"); // NOI18N
        JIN6.getContentPane().add(jLabel189, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 370, 20, 20));

        jLabel562.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel562.setText("SECONDORY LANGUAGE");
        JIN6.getContentPane().add(jLabel562, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, 20));

        jLabel563.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel563.setText("ENGLISH                           ");
        JIN6.getContentPane().add(jLabel563, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 20));

        jLabel564.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel564.setText("RELIGON");
        JIN6.getContentPane().add(jLabel564, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        jLabel565.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel565.setText("MATHAMATICS                  ");
        JIN6.getContentPane().add(jLabel565, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        jLabel567.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel567.setText("SCIENCE");
        JIN6.getContentPane().add(jLabel567, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 20));

        jLabel568.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel568.setText("CIVICS");
        JIN6.getContentPane().add(jLabel568, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 20));

        jLabel785.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel785.setText("PRIMARY LANGUGE");
        JIN6.getContentPane().add(jLabel785, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        jLabel182.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel182.setText(":");
        jLabel182.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 10, 20));

        jLabel183.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel183.setText(":");
        jLabel183.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel183, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 10, 20));

        jLabel184.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel184.setText(":");
        jLabel184.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel184, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 10, 20));

        jLabel185.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel185.setText(":");
        jLabel185.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel185, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 10, 20));

        jLabel186.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel186.setText(":");
        jLabel186.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel186, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 10, 20));

        jLabel187.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel187.setText(":");
        jLabel187.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel187, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 10, 20));

        jLabel188.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel188.setText(":");
        jLabel188.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel188, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 10, 20));

        jLabel196.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel196.setText(":");
        jLabel196.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel196, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 10, 20));

        jLabel569.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel569.setText("PRACTICAL SKILLS         ");
        JIN6.getContentPane().add(jLabel569, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, 20));

        jLabel197.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel197.setText(":");
        jLabel197.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel197, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 10, 20));

        jLabel570.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel570.setText("GEOGRAPHY");
        JIN6.getContentPane().add(jLabel570, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 20));

        jLabel198.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel198.setText(":");
        jLabel198.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel198, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 10, 20));

        jLabel571.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel571.setText("HISTORY");
        JIN6.getContentPane().add(jLabel571, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jLabel572.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel572.setText("INFORMATION TECHNOLOGY");
        JIN6.getContentPane().add(jLabel572, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, 20));

        jLabel199.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel199.setText(":");
        jLabel199.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel199, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 10, 20));

        jLabel200.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel200.setText(":");
        jLabel200.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel200, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 10, 20));

        jLabel573.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel573.setText("HEALTH");
        JIN6.getContentPane().add(jLabel573, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, 20));

        jLabel206.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel206.setText(":");
        jLabel206.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN6.getContentPane().add(jLabel206, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 10, 20));

        jLabel574.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel574.setText("EASTHETIC");
        JIN6.getContentPane().add(jLabel574, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, 20));

        JIN12.setFrameIcon(null);
        JIN12.setVisible(false);
        JIN12.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        a11.setToolTipText("Enter the marks and press Enter");
        a11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G12KeyTyped(evt);
            }
        });
        JIN12.getContentPane().add(a11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 80, -1));

        b11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G12KeyTyped(evt);
            }
        });
        JIN12.getContentPane().add(b11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 80, -1));

        c11.setToolTipText("Enter the marks and press Enter");
        c11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G12KeyTyped(evt);
            }
        });
        JIN12.getContentPane().add(c11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 80, -1));

        d11.setToolTipText("Enter the marks and press Enter");
        d11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G12KeyTyped(evt);
            }
        });
        JIN12.getContentPane().add(d11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 80, -1));

        jLabel156.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel156.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel156.setName("Al"); // NOI18N
        JIN12.getContentPane().add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 20, 20));

        jLabel157.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel157.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel157.setName("Al"); // NOI18N
        JIN12.getContentPane().add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, 20, 20));

        jLabel158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel158.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel158.setName("Al"); // NOI18N
        JIN12.getContentPane().add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 20, 20));

        jLabel159.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel159.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel159.setName("Al"); // NOI18N
        JIN12.getContentPane().add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 20, 20));

        jLabel590.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel590.setText("ENGLISH                           ");
        JIN12.getContentPane().add(jLabel590, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 20));

        SBJ1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SBJ1.setText("SUBJECT 01");
        JIN12.getContentPane().add(SBJ1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 150, 20));

        SBJ3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SBJ3.setText("SUBJECT 03");
        JIN12.getContentPane().add(SBJ3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 150, 20));

        SBJ2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SBJ2.setText("SUBJECT 02");
        JIN12.getContentPane().add(SBJ2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 150, 20));

        jLabel152.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel152.setText(":");
        jLabel152.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN12.getContentPane().add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 10, 20));

        jLabel153.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel153.setText(":");
        jLabel153.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN12.getContentPane().add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 10, 20));

        jLabel154.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel154.setText(":");
        jLabel154.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN12.getContentPane().add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 10, 20));

        jLabel155.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel155.setText(":");
        jLabel155.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN12.getContentPane().add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 10, 20));

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel597.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel597.setText("Subject 01");
        jPanel11.add(jLabel597, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 20));

        jLabel598.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel598.setText("Subject 03");
        jPanel11.add(jLabel598, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 20));

        jLabel790.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel790.setText("Subject 02");
        jPanel11.add(jLabel790, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        Sbj1.setToolTipText("Enter the marks and press Enter");
        Sbj1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sbj1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Sbj1KeyTyped(evt);
            }
        });
        jPanel11.add(Sbj1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 165, -1));

        Sbj3.setToolTipText("Enter the marks and press Enter");
        Sbj3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sbj3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Sbj3KeyTyped(evt);
            }
        });
        jPanel11.add(Sbj3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 165, -1));

        Sbj2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sbj2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Sbj2KeyTyped(evt);
            }
        });
        jPanel11.add(Sbj2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 165, -1));

        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText(":");
        jLabel160.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel11.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 10, 20));

        jLabel161.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel161.setText(":");
        jLabel161.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel11.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 10, 20));

        jLabel162.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel162.setText(":");
        jLabel162.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel11.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 10, 20));

        MR_Delete.setText("Delete");
        MR_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MR_DeleteActionPerformed(evt);
            }
        });
        jPanel11.add(MR_Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, -1, -1));

        Add.setText("Add");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        jPanel11.add(Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel599.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel599.setText("New Fieled");
        jPanel11.add(jLabel599, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        Field.setToolTipText("Enter the marks and press Enter");
        Field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FieldKeyTyped(evt);
            }
        });
        jPanel11.add(Field, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 165, -1));

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText(":");
        jLabel163.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel11.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 10, 20));

        JIN12.getContentPane().add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 300, 200));

        ALFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ALFieldsActionPerformed(evt);
            }
        });
        JIN12.getContentPane().add(ALFields, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        d12.setToolTipText("Enter the marks and press Enter");
        d12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G12KeyTyped(evt);
            }
        });
        JIN12.getContentPane().add(d12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 80, -1));

        jLabel591.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel591.setText("Common");
        JIN12.getContentPane().add(jLabel591, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 20));

        jLabel210.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel210.setText(":");
        jLabel210.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN12.getContentPane().add(jLabel210, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 10, 20));

        JIN10.setFrameIcon(null);
        JIN10.setVisible(false);
        JIN10.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        a10.setToolTipText("Enter the marks and press Enter");
        a10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(a10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 80, -1));

        b10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(b10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 80, -1));

        c10.setToolTipText("Enter the marks and press Enter");
        c10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(c10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 80, -1));

        d10.setToolTipText("Enter the marks and press Enter");
        d10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(d10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 80, -1));

        e10.setToolTipText("Enter the marks and press Enter");
        e10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(e10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 80, -1));

        f10.setToolTipText("Enter the marks and press Enter");
        f10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(f10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 80, -1));

        g10.setToolTipText("Enter the marks and press Enter");
        g10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(g10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 80, -1));

        h10.setToolTipText("Enter the marks and press Enter");
        h10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(h10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 80, -1));

        i10.setToolTipText("Enter the marks and press Enter");
        i10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G10KeyTyped(evt);
            }
        });
        JIN10.getContentPane().add(i10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 80, -1));

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel141.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 20, 20));

        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel142.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel142.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 20, 20));

        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel143.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel143.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 20, 20));

        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel144.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel144.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 20, 20));

        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel145.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel145.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 20, 20));

        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel146.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel146.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, 20, 20));

        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel147.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel147.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 20, 20));

        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel149.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel149.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 20, 20));

        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel151.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel151.setName("OL"); // NOI18N
        JIN10.getContentPane().add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 20, 20));

        jLabel582.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel582.setText("BASKET 01");
        JIN10.getContentPane().add(jLabel582, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, 20));

        jLabel583.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel583.setText("ENGLISH                           ");
        JIN10.getContentPane().add(jLabel583, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        jLabel584.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel584.setText("RELIGON");
        JIN10.getContentPane().add(jLabel584, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        jLabel585.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel585.setText("MATHAMATICS                  ");
        JIN10.getContentPane().add(jLabel585, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel586.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel586.setText("SCIENCE");
        JIN10.getContentPane().add(jLabel586, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, 20));

        jLabel587.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel587.setText("HISTORY");
        JIN10.getContentPane().add(jLabel587, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 20));

        jLabel787.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel787.setText("PRIMARY LANGUAGE");
        JIN10.getContentPane().add(jLabel787, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 20));

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText(":");
        jLabel134.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 10, 20));

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setText(":");
        jLabel135.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 10, 20));

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText(":");
        jLabel136.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 10, 20));

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel137.setText(":");
        jLabel137.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 10, 20));

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText(":");
        jLabel138.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 10, 20));

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText(":");
        jLabel139.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 10, 20));

        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText(":");
        jLabel140.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 10, 20));

        jLabel588.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel588.setText("BASKET 02");
        JIN10.getContentPane().add(jLabel588, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, 20));

        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel148.setText(":");
        jLabel148.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 10, 20));

        jLabel589.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel589.setText("EASTHETIC");
        JIN10.getContentPane().add(jLabel589, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, 20));

        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText(":");
        jLabel150.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN10.getContentPane().add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 10, 20));

        javax.swing.GroupLayout marksLayout = new javax.swing.GroupLayout(marks);
        marks.setLayout(marksLayout);
        marksLayout.setHorizontalGroup(
            marksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(marksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(marksLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JIN6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(marksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(marksLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JIN12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(marksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(marksLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JIN10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        marksLayout.setVerticalGroup(
            marksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(marksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(marksLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JIN6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(marksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(marksLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JIN12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(marksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(marksLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JIN10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        al_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        al_btn.setText("Advanced Level");
        al_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        al_btn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        al_btn.setMaximumSize(new java.awt.Dimension(1000, 65));
        al_btn.setMinimumSize(new java.awt.Dimension(36, 36));
        al_btn.setPreferredSize(new java.awt.Dimension(170, 40));
        al_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        al_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                al_btnActionPerformed(evt);
            }
        });

        ol_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ol_btn.setText("Ordanary level");
        ol_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ol_btn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ol_btn.setMaximumSize(new java.awt.Dimension(1000, 65));
        ol_btn.setMinimumSize(new java.awt.Dimension(36, 36));
        ol_btn.setPreferredSize(new java.awt.Dimension(170, 40));
        ol_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        sc_05.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sc_05.setText("Scholorship(05)");
        sc_05.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sc_05.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        sc_05.setMaximumSize(new java.awt.Dimension(1000, 65));
        sc_05.setMinimumSize(new java.awt.Dimension(36, 36));
        sc_05.setPreferredSize(new java.awt.Dimension(170, 40));
        sc_05.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        exams_label.setText("Exams");
        exams_label.setPreferredSize(new java.awt.Dimension(170, 16));

        jDialog1.getContentPane().setLayout(new javax.swing.BoxLayout(jDialog1.getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jDialog1.getContentPane().add(jScrollPane2);

        setResizable(true);
        setTitle("Marks");
        setFrameIcon(getImage(new ImageIcon("src\\Images\\Toolbar\\Single-"+getBorW()+".png"),28,28));
        setMinimumSize(new java.awt.Dimension(780, 548));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(getImage("src\\Images\\Toolbar\\MarksReport-"+getBorW()+".png",0,0));

        jLabel125.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("MARKS REPORT");

        selectTerm.setMaximumSize(new java.awt.Dimension(180, 545));
        selectTerm.setMinimumSize(new java.awt.Dimension(10, 100));
        selectTerm.setPreferredSize(new java.awt.Dimension(180, 545));
        selectTerm.setLayout(new javax.swing.BoxLayout(selectTerm, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel1.setText("Term tests");
        jLabel1.setPreferredSize(new java.awt.Dimension(170, 16));
        selectTerm.add(jLabel1);

        jButton46.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton46.setText("First Term");
        buttonGroup1.add(jButton46);
        jButton46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton46.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton46.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton46.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton46.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton46.setRolloverEnabled(false);
        jButton46.setSelected(true);
        jButton46.setVerifyInputWhenFocusTarget(false);
        jButton46.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });
        selectTerm.add(jButton46);

        jButton48.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton48.setText("Third Term");
        buttonGroup1.add(jButton48);
        jButton48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton48.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton48.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton48.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton48.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton48.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectTerm.add(jButton48);

        jButton47.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton47.setText("Second Term");
        buttonGroup1.add(jButton47);
        jButton47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton47.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton47.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton47.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton47.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton47.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectTerm.add(jButton47);

        jLabel2.setText("Unit tests");
        jLabel2.setPreferredSize(new java.awt.Dimension(170, 16));
        selectTerm.add(jLabel2);

        jButton49.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton49.setText("Unit Test 01");
        buttonGroup1.add(jButton49);
        jButton49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton49.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton49.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton49.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton49.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton49.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });
        selectTerm.add(jButton49);

        jButton50.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton50.setText("Unit Test 02");
        buttonGroup1.add(jButton50);
        jButton50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton50.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton50.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton50.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton50.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton50.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectTerm.add(jButton50);

        jButton51.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton51.setText("Unit Test 03");
        buttonGroup1.add(jButton51);
        jButton51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton51.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton51.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton51.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton51.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton51.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });
        selectTerm.add(jButton51);

        jButton52.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton52.setText("Unit Test 04");
        buttonGroup1.add(jButton52);
        jButton52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton52.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton52.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton52.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton52.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton52.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectTerm.add(jButton52);

        jButton53.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton53.setText("Unit Test 05");
        buttonGroup1.add(jButton53);
        jButton53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton53.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton53.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton53.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton53.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton53.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectTerm.add(jButton53);

        jButton54.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton54.setText("Unit Test 06");
        buttonGroup1.add(jButton54);
        jButton54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton54.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton54.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton54.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton54.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton54.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectTerm.add(jButton54);

        jButton55.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton55.setText("Unit Test 07");
        buttonGroup1.add(jButton55);
        jButton55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton55.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton55.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton55.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton55.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton55.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectTerm.add(jButton55);

        jButton56.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton56.setText("Unit Test 08");
        buttonGroup1.add(jButton56);
        jButton56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton56.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton56.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton56.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton56.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton56.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });
        selectTerm.add(jButton56);

        jButton57.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton57.setText("Unit Test 09");
        buttonGroup1.add(jButton57);
        jButton57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton57.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton57.setMaximumSize(new java.awt.Dimension(1000, 65));
        jButton57.setMinimumSize(new java.awt.Dimension(36, 36));
        jButton57.setPreferredSize(new java.awt.Dimension(170, 40));
        jButton57.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectTerm.add(jButton57);

        jScrollPane1.setViewportView(selectTerm);

        javax.swing.GroupLayout MR_jPanel14Layout = new javax.swing.GroupLayout(MR_jPanel14);
        MR_jPanel14.setLayout(MR_jPanel14Layout);
        MR_jPanel14Layout.setHorizontalGroup(
            MR_jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel125, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MR_jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        MR_jPanel14Layout.setVerticalGroup(
            MR_jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MR_jPanel14Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        MR_InitFrames.setMinimumSize(new java.awt.Dimension(380, 470));

        JIN1.setFrameIcon(null);
        JIN1.setPreferredSize(new java.awt.Dimension(370, 440));
        JIN1.setVisible(true);
        JIN1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                JIN1FocusLost(evt);
            }
        });
        JIN1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_religion.setToolTipText("Enter the marks and press Enter");
        p_religion.setDoubleBuffered(true);
        p_religion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_religion, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 80, -1));

        p_prLng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_prLng, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 80, -1));

        p_english.setToolTipText("Enter the marks and press Enter");
        p_english.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_english, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 80, -1));

        p_maths.setToolTipText("Enter the marks and press Enter");
        p_maths.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_maths, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 80, -1));

        p_env.setToolTipText("Enter the marks and press Enter");
        p_env.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_envActionPerformed(evt);
            }
        });
        p_env.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_env, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 80, -1));

        p_pts.setToolTipText("Enter the marks and press Enter");
        p_pts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_pts, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 80, -1));

        p_secLng.setToolTipText("Enter the marks and press Enter");
        p_secLng.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_secLng, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 80, -1));

        p_read.setToolTipText("Enter the marks and press Enter");
        p_read.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_read, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 80, -1));

        p_write.setToolTipText("Enter the marks and press Enter");
        p_write.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                G1KeyTyped(evt);
            }
        });
        JIN1.getContentPane().add(p_write, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 80, -1));

        jLabel171.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel171.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel171.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 20, 20));

        jLabel172.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel172.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel172.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 20, 20));

        jLabel173.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel173.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel173.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 20, 20));

        jLabel174.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel174.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel174.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 20, 20));

        jLabel175.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel175.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel175.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 20, 20));

        jLabel176.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel176.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel176.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 20, 20));

        jLabel177.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel177.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel177.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 260, 20, 20));

        jLabel179.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel179.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel179.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel179, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, 20, 20));

        jLabel181.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel181.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel181.setName("primary"); // NOI18N
        JIN1.getContentPane().add(jLabel181, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340, 20, 20));

        jLabel566.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel566.setText("SECONDORY LANGUAGE");
        JIN1.getContentPane().add(jLabel566, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, 20));

        jLabel575.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel575.setText("ENGLISH                           ");
        JIN1.getContentPane().add(jLabel575, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        jLabel576.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel576.setText("RELIGON");
        JIN1.getContentPane().add(jLabel576, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        jLabel577.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel577.setText("MATHAMATICS                  ");
        JIN1.getContentPane().add(jLabel577, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 20));

        jLabel578.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel578.setText("ENVIROMENTAL STUDIES ");
        JIN1.getContentPane().add(jLabel578, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, 20));

        jLabel579.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel579.setText("PRACTICAL STUDIES          ");
        JIN1.getContentPane().add(jLabel579, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 20));

        jLabel786.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel786.setText("PRIMARY LANGUAGE");
        JIN1.getContentPane().add(jLabel786, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 20));

        jLabel164.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel164.setText(":");
        jLabel164.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 10, 20));

        jLabel165.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel165.setText(":");
        jLabel165.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 10, 20));

        jLabel166.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel166.setText(":");
        jLabel166.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 10, 20));

        jLabel167.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel167.setText(":");
        jLabel167.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 10, 20));

        jLabel168.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel168.setText(":");
        jLabel168.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 10, 20));

        jLabel169.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel169.setText(":");
        jLabel169.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 10, 20));

        jLabel170.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel170.setText(":");
        jLabel170.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 10, 20));

        jLabel580.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel580.setText("READING");
        JIN1.getContentPane().add(jLabel580, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, 20));

        jLabel178.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel178.setText(":");
        jLabel178.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 10, 20));

        jLabel581.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel581.setText("WRITING");
        JIN1.getContentPane().add(jLabel581, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, 20));

        jLabel180.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel180.setText(":");
        jLabel180.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        JIN1.getContentPane().add(jLabel180, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 10, 20));

        MR_InitFrames.setLayer(JIN1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout MR_InitFramesLayout = new javax.swing.GroupLayout(MR_InitFrames);
        MR_InitFrames.setLayout(MR_InitFramesLayout);
        MR_InitFramesLayout.setHorizontalGroup(
            MR_InitFramesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MR_InitFramesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JIN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MR_InitFramesLayout.setVerticalGroup(
            MR_InitFramesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MR_InitFramesLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(JIN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jToolBar4.setRollover(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        jToolBar4.add(jPanel1);

        gradeSelection.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        gradeSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13" }));
        gradeSelection.setMaximumSize(new java.awt.Dimension(100, 35));
        gradeSelection.setMinimumSize(new java.awt.Dimension(100, 35));
        gradeSelection.setPreferredSize(new java.awt.Dimension(100, 35));
        gradeSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeSelectionActionPerformed(evt);
            }
        });
        jToolBar4.add(gradeSelection);

        sel_Subgrade.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        sel_Subgrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "*All", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K" }));
        sel_Subgrade.setMaximumSize(new java.awt.Dimension(100, 35));
        sel_Subgrade.setMinimumSize(new java.awt.Dimension(100, 35));
        sel_Subgrade.setPreferredSize(new java.awt.Dimension(100, 35));
        sel_Subgrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sel_SubgradeActionPerformed(evt);
            }
        });
        jToolBar4.add(sel_Subgrade);

        MR_Panel1.setLayout(new javax.swing.BoxLayout(MR_Panel1, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel678.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel678.setText("Current Student");

        pos.setEditable(false);
        pos.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        pos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pos.setText("0");
        pos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Students progress in class"));
        pos.setPreferredSize(new java.awt.Dimension(200, 38));
        pos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                posActionPerformed(evt);
            }
        });

        jLabel673.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel673.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel673.setText("Total");

        jLabel675.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel675.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel675.setText("Average");

        progress.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        progress.setPreferredSize(new java.awt.Dimension(200, 19));
        progress.setStringPainted(true);

        update_btn.setText("UPDATE");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        jLabel679.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel679.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel679.setText("Name");

        jLabel680.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel680.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel680.setText("Admission No.");

        admission.setEditable(false);
        admission.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        admission.setMaximumSize(new java.awt.Dimension(140, 24));
        admission.setMinimumSize(new java.awt.Dimension(140, 24));
        admission.setPreferredSize(new java.awt.Dimension(140, 24));

        name.setEditable(false);
        name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        name.setMaximumSize(new java.awt.Dimension(140, 24));
        name.setMinimumSize(new java.awt.Dimension(140, 24));
        name.setPreferredSize(new java.awt.Dimension(140, 24));

        total.setEditable(false);
        total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        total.setMaximumSize(new java.awt.Dimension(140, 24));
        total.setMinimumSize(new java.awt.Dimension(140, 24));
        total.setPreferredSize(new java.awt.Dimension(140, 24));

        average.setEditable(false);
        average.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        average.setMaximumSize(new java.awt.Dimension(140, 24));
        average.setMinimumSize(new java.awt.Dimension(140, 24));
        average.setPreferredSize(new java.awt.Dimension(140, 24));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(update_btn)
                .addContainerGap())
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(admission, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(jLabel678)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                        .addGap(6, 6, 6))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel679)
                            .addComponent(jLabel680))
                        .addGap(106, 149, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel673))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(jLabel675)
                                .addGap(0, 42, Short.MAX_VALUE))
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(average, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel678)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel680))
                    .addComponent(pos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(admission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel679)
                .addGap(5, 5, 5)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel673)
                    .addComponent(jLabel675))
                .addGap(5, 5, 5)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(average, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(update_btn)
                .addContainerGap())
        );

        MR_Panel1.add(jPanel20);

        jPanel14.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel14.setPreferredSize(new java.awt.Dimension(450, 295));

        jLabel208.setText("Admission No");

        jLabel676.setText("Average");

        jLabel674.setText("Total");

        jLabel209.setText("Name");

        Progressf.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        Progressf.setStringPainted(true);

        jLabel677.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel677.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel677.setText("Compare");
        jLabel677.setPreferredSize(new java.awt.Dimension(215, 15));

        idBoxf.setEditable(false);
        idBoxf.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        nameBoxf.setEditable(false);
        nameBoxf.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        totalF.setEditable(false);
        totalF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        avrf.setEditable(false);
        avrf.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jToolBar1.setRollover(true);

        findText.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        findText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        findText.setText("1");
        findText.setToolTipText("");
        findText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findTextActionPerformed(evt);
            }
        });
        findText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                findTextKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                findTextKeyTyped(evt);
            }
        });
        jToolBar1.add(findText);

        find.setBorder(null);
        find.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        find.setMaximumSize(new java.awt.Dimension(25, 25));
        find.setMinimumSize(new java.awt.Dimension(25, 25));
        find.setPreferredSize(new java.awt.Dimension(25, 25));
        find.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findActionPerformed(evt);
            }
        });
        jToolBar1.add(find);

        jButton1.setText("▲");
        jButton1.setToolTipText("Load marks");
        jButton1.setBorder(null);
        jButton1.setMaximumSize(new java.awt.Dimension(25, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Progressf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel677, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idBoxf)
                            .addComponent(jLabel208, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalF, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel674, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel209, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameBoxf)
                            .addComponent(avrf)
                            .addComponent(jLabel676, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel677, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel209)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameBoxf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel676)
                        .addGap(6, 6, 6)
                        .addComponent(avrf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel208)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idBoxf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel674)
                        .addGap(6, 6, 6)
                        .addComponent(totalF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(Progressf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        MR_Panel1.add(jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(MR_jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addComponent(MR_InitFrames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(MR_Panel1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MR_jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(MR_InitFrames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(MR_Panel1, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void p_scienceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_scienceActionPerformed
    }//GEN-LAST:event_p_scienceActionPerformed

    private void g6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g6ActionPerformed
    }//GEN-LAST:event_g6ActionPerformed

    private void i6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i6ActionPerformed
    }//GEN-LAST:event_i6ActionPerformed

    private void Sbj1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sbj1KeyPressed
    }//GEN-LAST:event_Sbj1KeyPressed

    private void Sbj1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sbj1KeyTyped
    }//GEN-LAST:event_Sbj1KeyTyped

    private void Sbj3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sbj3KeyPressed
    }//GEN-LAST:event_Sbj3KeyPressed

    private void Sbj3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sbj3KeyTyped
    }//GEN-LAST:event_Sbj3KeyTyped

    private void Sbj2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sbj2KeyPressed
    }//GEN-LAST:event_Sbj2KeyPressed

    private void Sbj2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sbj2KeyTyped
    }//GEN-LAST:event_Sbj2KeyTyped

    private void MR_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MR_DeleteActionPerformed
        String s = ALFields.getSelectedItem().toString();
        if (!s.equals("Default")) {
            try {
                ALevel.deleteStream(s);
            } catch (SQLException ex) {
                Commons.showMsg(ex);
            }
        } else {
            setMessage("Cannot delete the default stream", MessageType.ERROR, Messages.TIME);
        }
        setStFeild(Id);
    }//GEN-LAST:event_MR_DeleteActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        try {
            ALevel.addStream(Field.getText(), Sbj1.getText(), Sbj2.getText(), Sbj3.getText());
        } catch (SQLException ex) {
            setMessage(ex.toString().contains("unique") ? "Stream in this name already exists" : ex.toString(), MessageType.ERROR, Messages.TIME);
            Commons.showMsg(ex);
        }
        setStFeild(CurrentStudent.getAdmissionNo());
    }//GEN-LAST:event_AddActionPerformed

    private void FieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldKeyPressed

    }//GEN-LAST:event_FieldKeyPressed

    private void FieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldKeyReleased

    }//GEN-LAST:event_FieldKeyReleased

    private void FieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FieldKeyTyped

    }//GEN-LAST:event_FieldKeyTyped

    private void ALFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ALFieldsActionPerformed
        if (ALFields.getSelectedIndex() != -1) {
            setALFields(ALFields.getSelectedItem().toString());
        }
    }//GEN-LAST:event_ALFieldsActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed

    }//GEN-LAST:event_jButton46ActionPerformed

    private void p_envActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_envActionPerformed

    }//GEN-LAST:event_p_envActionPerformed

    private void JIN1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JIN1FocusLost
        JIN1.requestFocus();
        getToolkit().beep();
    }//GEN-LAST:event_JIN1FocusLost

    private void gradeSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeSelectionActionPerformed
        grade = getGrade();
        changeGrade(grade);
        setExamBtns(grade);
//        MarksReport_frame.setTitle(getFrameName());
    }//GEN-LAST:event_gradeSelectionActionPerformed

    private void findTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findTextActionPerformed

    }//GEN-LAST:event_findTextActionPerformed

    private void findTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findTextKeyPressed

    }//GEN-LAST:event_findTextKeyPressed

    public String getSubGrade() {
        return sel_Subgrade.getSelectedItem().toString();
    }

    private void findTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findTextKeyTyped
        int key = Integer.parseInt(findText.getText());
        System.out.println(findText.getText());
        String[] ids = getSubGrade().equals("*All")
                ? Marks.getStudentId(key, grade, getTerm())
                : Marks.getStudentId(key, grade, getSubGrade(), getTerm());
        int length = ids == null ? 0 : ids.length;
        if (Character.isDigit(key)) {
            if (length == Integer.parseInt(findText.getText() + key)) {
                evt.consume();
            }
        }
        if (key == KeyEvent.VK_ENTER) {
            find.doClick(100);
        }
    }//GEN-LAST:event_findTextKeyTyped

    private void findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findActionPerformed
        String[] ids = Marks.getStudentId(Integer.parseInt(findText.getText()), grade, getTerm());
        String id = ids.length == 1 ? ids[0] : JOptionPane.showInputDialog(null, String.format("There are %d students are in %s possition\nSelect a Admission No. to load marks", ids.length, findText.getText()), "Select a student", JOptionPane.WARNING_MESSAGE, Commons.getImage(Messages.WARNING, 20, 20), ids, null).toString();
        Student.setData(id, idBoxf, nameBoxf, null);
        Marks mp = new Marks(id, getTerm(), grade);
        ArrayList<Double> mark = mp.getMarks();
        avrf.setText(Marks.getps(mp.getAverage()) + "");
        totalF.setText(Marks.getps(mp.getTotal()) + "");
        Progressf.setValue((int) Double.parseDouble(avrf.getText()));
    }//GEN-LAST:event_findActionPerformed

    private void posActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_posActionPerformed

    }//GEN-LAST:event_posActionPerformed
    void setMessage(SysMsg m) {
        setMessage(m.getMessage(), m.getType(), m.getTime());
    }

    void setMessage(String msg, MessageType t, int tm) {

    }

    int getGrade() {
        return Integer.parseInt(gradeSelection.getSelectedItem().toString());
    }

    ArrayList<Double> getVals(ArrayList<JTextField> m) {
        ArrayList<Double> s = new ArrayList<>();
        for (JTextField t : m) {
            s.add(new Double(t.getText()));
        }
        return s;
    }

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
        String[] ids = selection.getSelectedIds();
        Container con = currentFrame;
        ArrayList<JTextField> tf = fields.get(con);
        ArrayList<Double> mrk = getVals(tf);
        String term = getTerm();
        String id = admission.getText();
        com.MarksReport.Marks mark = new com.MarksReport.Marks(id, term, getGrade());
        try {
            if (getGrade() < 14 && getGrade() > 11) {
                ALevel.setStream(ids, getGrade(), term, CommonUi.gsi(ALFields));
            }
            mark.setMarks(mrk);
            mark.updateMarks();
            setMessage("Changes saved", MessageType.INFO, Messages.TIME);
        } catch (Exception ex) {
            getToolkit().beep();
            setMessage("Could not save the changes", MessageType.ERROR, Messages.TIME);
            Commons.showMsg(ex);
        }
    }//GEN-LAST:event_update_btnActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed

    }//GEN-LAST:event_jButton49ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed

    }//GEN-LAST:event_jButton51ActionPerformed

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed

    }//GEN-LAST:event_jButton56ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String id = idBoxf.getText();
        load(id, getTerm(), getGrade());
    }//GEN-LAST:event_jButton1ActionPerformed

    void actionFields(KeyEvent evt, JTextField tf) {
        char c = evt.getKeyChar();
        int cd = (int) c;
        if (cd == KeyEvent.VK_ENTER) {
            Consume(evt, tf);
        } else {
            getFocus(c, tf);
            setMarksGrades(fields.get(currentFrame), Fields.getLabels(currentFrame, currentWidth), currentWidth);
        }
    }

    private void G10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G10KeyTyped
        JTextField tf = (JTextField) evt.getSource();
        actionFields(evt, tf);
    }//GEN-LAST:event_G10KeyTyped

    private void G6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G6KeyTyped
        JTextField tf = (JTextField) evt.getSource();
        actionFields(evt, tf);
    }//GEN-LAST:event_G6KeyTyped

    private void G1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G1KeyTyped
        JTextField tf = (JTextField) evt.getSource();
        actionFields(evt, tf);
    }//GEN-LAST:event_G1KeyTyped

    private void G12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G12KeyTyped
        JTextField tf = (JTextField) evt.getSource();
        actionFields(evt, tf);
    }//GEN-LAST:event_G12KeyTyped

    private void al_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_al_btnActionPerformed

    }//GEN-LAST:event_al_btnActionPerformed

    private void sel_SubgradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sel_SubgradeActionPerformed

    }//GEN-LAST:event_sel_SubgradeActionPerformed
    class SetPossitions extends Thread {

        private List<Object> poses;
        private String id;
        private String term;
        private String subGrade;
        private int grade;

        @Override
        public void run() {
            try {
                setPriority(Thread.MAX_PRIORITY);
                poses = subGrade.equals("*All")
                        ? new com.MarksReport.Marks(id, term, grade).getPossitions()
                        : com.MarksReport.Marks.getPossitions(id, term, grade, subGrade);
                if (poses == null) {

                } else if (poses.size() > 1) {
                    setMessage("There are " + poses.size() + " Students that have " + total.getText() + " total marks", MessageType.WARNING, 0);
                    pos.setText(poses.get(0) + "");
                } else if (poses.size() == 1) {
                    pos.setText(poses.get(0) + "");
                }
            } catch (Exception ex) {
                Commons.showMsg(ex);
            }
        }

        public void setStudentPossition(String Id, String Term, int Grade, String SubGrade) {
            id = Id;
            term = Term;
            grade = Grade;
            subGrade = SubGrade;
            start();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Commons.showMsg(ex);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 1460, 680);
        JDesktopPane pane = new JDesktopPane();
        pane.setBounds(5, 5, 1450, 670);
        Selection s = new Selection();
        CurrentStudent cs = new CurrentStudent();
        s.setResizable(true);

        Marks_frame hf = new Marks_frame(s, cs);
        hf.setBounds(0, 0, 1050, 590);
        s.Table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                hf.load(s.Table.getValueAt(s.Table.getSelectedRow(), 0).toString(), hf.getTerm(), hf.getGrade());
            }
        });

        s.setBounds(1050, 0, 260, 590);
        hf.setVisible(true);
        s.setVisible(true);
        s.validate();
        s.doLayout();
        pane.add(hf);
        pane.add(s);
        frame.getContentPane().add(pane);
        frame.setVisible(true);

    }

    static class rt extends Thread {

        final Container c;

        public rt(Container c) {
            this.c = c;
        }

        @Override
        public void run() {
            createRepaintTimer(c);
        }

        private void createRepaintTimer(final Container frame) {
            final Timer timer = new Timer(1, null);
            timer.addActionListener(e -> {
                if (!frame.isVisible()) {
                    timer.stop();
                } else {
                    frame.validate();
                    frame.repaint();
                    frame.doLayout();
                }
            });
            timer.start();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ALFields;
    private javax.swing.JButton Add;
    private javax.swing.JTextField Field;
    private javax.swing.JInternalFrame JIN1;
    private javax.swing.JInternalFrame JIN10;
    private javax.swing.JInternalFrame JIN12;
    private javax.swing.JInternalFrame JIN6;
    private javax.swing.JButton MR_Delete;
    private javax.swing.JDesktopPane MR_InitFrames;
    private javax.swing.JPanel MR_Panel1;
    private javax.swing.JPanel MR_jPanel14;
    private javax.swing.JProgressBar Progressf;
    private javax.swing.JLabel SBJ1;
    private javax.swing.JLabel SBJ2;
    private javax.swing.JLabel SBJ3;
    private javax.swing.JTextField Sbj1;
    private javax.swing.JTextField Sbj2;
    private javax.swing.JTextField Sbj3;
    private javax.swing.JTextField a10;
    private javax.swing.JTextField a11;
    private javax.swing.JTextField admission;
    private javax.swing.JButton al_btn;
    private javax.swing.JTextField average;
    private javax.swing.JTextField avrf;
    private javax.swing.JTextField b10;
    private javax.swing.JTextField b11;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField c10;
    private javax.swing.JTextField c11;
    private javax.swing.JTextField d10;
    private javax.swing.JTextField d11;
    private javax.swing.JTextField d12;
    private javax.swing.JTextField d6;
    private javax.swing.JTextField e10;
    private javax.swing.JLabel exams_label;
    private javax.swing.JTextField f10;
    private javax.swing.JButton find;
    private javax.swing.JTextField findText;
    private javax.swing.JTextField g10;
    private javax.swing.JTextField g6;
    private javax.swing.JComboBox<String> gradeSelection;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField i10;
    private javax.swing.JTextField i6;
    private javax.swing.JTextField idBoxf;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel562;
    private javax.swing.JLabel jLabel563;
    private javax.swing.JLabel jLabel564;
    private javax.swing.JLabel jLabel565;
    private javax.swing.JLabel jLabel566;
    private javax.swing.JLabel jLabel567;
    private javax.swing.JLabel jLabel568;
    private javax.swing.JLabel jLabel569;
    private javax.swing.JLabel jLabel570;
    private javax.swing.JLabel jLabel571;
    private javax.swing.JLabel jLabel572;
    private javax.swing.JLabel jLabel573;
    private javax.swing.JLabel jLabel574;
    private javax.swing.JLabel jLabel575;
    private javax.swing.JLabel jLabel576;
    private javax.swing.JLabel jLabel577;
    private javax.swing.JLabel jLabel578;
    private javax.swing.JLabel jLabel579;
    private javax.swing.JLabel jLabel580;
    private javax.swing.JLabel jLabel581;
    private javax.swing.JLabel jLabel582;
    private javax.swing.JLabel jLabel583;
    private javax.swing.JLabel jLabel584;
    private javax.swing.JLabel jLabel585;
    private javax.swing.JLabel jLabel586;
    private javax.swing.JLabel jLabel587;
    private javax.swing.JLabel jLabel588;
    private javax.swing.JLabel jLabel589;
    private javax.swing.JLabel jLabel590;
    private javax.swing.JLabel jLabel591;
    private javax.swing.JLabel jLabel597;
    private javax.swing.JLabel jLabel598;
    private javax.swing.JLabel jLabel599;
    private javax.swing.JLabel jLabel673;
    private javax.swing.JLabel jLabel674;
    private javax.swing.JLabel jLabel675;
    private javax.swing.JLabel jLabel676;
    private javax.swing.JLabel jLabel677;
    private javax.swing.JLabel jLabel678;
    private javax.swing.JLabel jLabel679;
    private javax.swing.JLabel jLabel680;
    private javax.swing.JLabel jLabel785;
    private javax.swing.JLabel jLabel786;
    private javax.swing.JLabel jLabel787;
    private javax.swing.JLabel jLabel790;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JTextField j_religion;
    private javax.swing.JTextField k6;
    private javax.swing.JTextField l6;
    private javax.swing.JPanel marks;
    private javax.swing.JTextField n6;
    private javax.swing.JTextField name;
    private javax.swing.JTextField nameBoxf;
    private javax.swing.JButton ol_btn;
    private javax.swing.JTextField p_civics;
    private javax.swing.JTextField p_eng;
    private javax.swing.JTextField p_english;
    private javax.swing.JTextField p_env;
    private javax.swing.JTextField p_estc;
    private javax.swing.JTextField p_history;
    private javax.swing.JTextField p_maths;
    private javax.swing.JTextField p_pLng;
    private javax.swing.JTextField p_prLng;
    private javax.swing.JTextField p_pts;
    private javax.swing.JTextField p_read;
    private javax.swing.JTextField p_religion;
    private javax.swing.JTextField p_science;
    private javax.swing.JTextField p_secLng;
    private javax.swing.JTextField p_write;
    private javax.swing.JTextField pos;
    private javax.swing.JProgressBar progress;
    private javax.swing.JButton sc_05;
    private javax.swing.JComboBox<String> sel_Subgrade;
    private javax.swing.JPanel selectTerm;
    private javax.swing.JTextField total;
    private javax.swing.JTextField totalF;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
}
