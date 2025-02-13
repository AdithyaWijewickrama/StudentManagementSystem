package com.ui.frames;

import com.ui.internalFrames.Health_frame;
import com.ui.CommonUi;
import com.ManageDetails.student.CurrentStudent;
import com.ui.internalFrames.Marks_frame;
import com.Database.DBconnect;
import com.Codes.Commons;
import static com.Codes.Commons.setDefault;
import static com.Codes.Commons.getImage;
import java.awt.Color;
import java.io.IOException;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import com.Database.Database;
import com.ManageDetails.student.Student;
import com.Codes.AppConfig;
import com.Main.Defaults;
import com.Main.Selection;
import com.ui.dialogs.security.ChangePassword;
import com.ui.dialogs.security.Recovery;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.TrayIcon.MessageType;
import com.ui.internalFrames.Manage_frame;
import com.MarksReport.Marks;
import java.awt.event.MouseEvent;
import com.ui.internalFrames.Print_frame;
import java.util.Arrays;
import java.util.List;
import static com.Codes.Commons.openFile;
import com.ui.dialogs.security.PasswordOptions;
import static com.ui.frames.UserLoginFrame.school;
import static com.ui.frames.UserLoginFrame.security;
import com.formdev.flatlaf.FlatDarkLaf;
import com.ui.CommonUi;
import com.ui.dialogs.SchoolDetails;
import javax.swing.JDialog;

public final class UserFrame extends javax.swing.JFrame {

    boolean navExpand = false;
    public boolean b = true;
    public static Connection MarksConn = DBconnect.CONN;
    public static Connection printerConn = DBconnect.connect(Database.PRINTER);
    public static final int IMAGEWIDTH = 117;
    public static final int IMAGEHEIGHT = 150;
    public static int FRAMEWIDTH = 1050;
    public static int FRAMEHEIGHT = 590;
    public static final String DEFAULTOTHER = "Value";
    public static Color BACKGROUND = Color.WHITE;
    public static final String ID_NO = "Admission No.";
    public static final String NAME = "Name";
    public static final String GRADE = "Grade";
    public static final String SUBGRADE = "Sub grade";
    public static final String AGE = "Age";
    public static final String MEDIUM = "Medium";
    public static final String GENDER = "Gender";
    public static final String PHONE = "Telephone";
    public static final String BIRTH = "Date of birth";
    public static final String GUARDIAN = "Guardian";
    public static final String ADDRESS = "Home address";
    public static final String EMAIL = "Email";
    public CurrentStudent currentStudent;
    public Manage_frame Manage_frame;
    public Marks_frame Marks_frame;
    public Print_frame Print_frame;
    public Health_frame Health_frame;
    public Selection selection;
    public JInternalFrame currentFrame;
    public JTable detailsTable;
    private JDialog schoolDetails;

    public UserFrame(LoadFrame fl) {
        initComponents();
        if (fl != null) {
            fl.startApplication();
        }
        selection = new Selection();
        detailsTable = selection.Table;
        schoolDetails = new SchoolDetails(security.getUsername(), this, true);
        currentStudent = new CurrentStudent();
        Marks_frame = new Marks_frame(selection, currentStudent);
        Health_frame = new Health_frame(detailsTable);
        Manage_frame = new Manage_frame(selection);
        Print_frame = new Print_frame(currentStudent, selection);
        setIconImage(AppConfig.APPICON_50.getImage());
        detailsTable.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                String[] ids = selection.getSelectedIds();
                load(currentFrame, ids);
                currentStudent.load(ids[0]);
            }
        });
        this.currentFrame = selection;
        for (JInternalFrame f : getInFrames()) {
            f.setIconifiable(false);
            f.setResizable(true);
        }
        jButton35.setEnabled(false);
        for (Component c : jPanel8.getComponents()) {
            if (((JButton) c).equals(navigator)) {
                continue;
            }
            ((JButton) c).setIcon(getImage((ImageIcon) ((JButton) c).getIcon(), 52, 52));
        }
        if (fl != null) {
            fl.close();
        }
        MainDesktop.add(currentStudent);
        currentStudent.hide();
        selection.tableLoad();
        for (Component c : jPanel8.getComponents()) {
            JButton b = (JButton) c;
            if (!b.equals(navigator)) {
                b.addActionListener((ActionEvent e) -> {
                    for (Component c2 : jPanel8.getComponents()) {
                        JButton b2 = (JButton) c2;
                        if (b.equals(b2)) {
                            currentFrameLabel.setText(b.getToolTipText());
                        }
                    }
                });
            }
        }
        jButton11.doClick(0);
        loadNavigation();
    }

    public void loadNavigation() {
        user.setIcon(Commons.getImage(school.getRoundImage(), 40, 40));
        jLabel1.setText(school.getName());
    }

    public void load(JInternalFrame frame, String[] ids) {
        if (ids == null) {
            return;
        }
        if (ids.length == 0) {
            return;
        }
        if (frame instanceof CurrentStudent) {
            currentStudent.load(ids[0]);
        } else if (frame instanceof Manage_frame) {
            Manage_frame.updateFrameLoad(ids);
        } else if (frame instanceof Marks_frame) {
            String id = ids[0];
            String term = "";
            int gr = Student.getGrade(id);
            List<Object> e = Marks.getWrittenExams(id, gr);
            for (int i = Marks.TERMS.length - 1; i > -1; i++) {
                term = Marks.TERMS[i];
                if (e.contains(Marks.TERMS[i])) {
                    break;
                }
            }
            Marks_frame.load(ids[0], term, gr);
            Marks_frame.setTerm(term);
        } else if (frame instanceof Health_frame) {
            Health_frame.load(ids[0]);
        } else if (frame instanceof Print_frame) {
            Print_frame.selectedIds = ids;
            Print_frame.load(ids);
        }
    }

    ImageIcon getIcon(String filename) {
        return new ImageIcon(filename);
    }

    public boolean isNavExpanded() {
        return navExpand;
    }

    public static String getBorW() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? "white" : "black";
    }

    public void selectFrame(JInternalFrame frame) {
        MainDesktop.remove(currentFrame);
        if (!frame.equals(selection) && !frame.equals(Manage_frame)) {
            selection.setSize(250, boxPanel.getHeight() - 4);
            selection.setPreferredSize(new Dimension(250, boxPanel.getHeight() - 4));
            boxPanel.add(selection);
        } else if (Arrays.asList(MainDesktop.getComponents()).contains(selection)) {
            boxPanel.remove(selection);
        }
        if (!Arrays.asList(MainDesktop.getComponents()).contains(frame)) {
            MainDesktop.add(frame);
        }
        frame.setBounds(4, 4, MainDesktop.getWidth() - 4, MainDesktop.getHeight() - 4);
        MainDesktop.setPreferredSize(frame.getMinimumSize());
        frame.setVisible(true);
        currentFrame = frame;
    }

    public static String[] getColumnNames() {
        return new String[]{ID_NO, NAME, AGE, GRADE, MEDIUM, BIRTH, GENDER, PHONE, ADDRESS, GUARDIAN, EMAIL};
    }

    public void SideBarCollapse(int Width) {
        navigator.setToolTipText("Open Navigation");
        jPanel8.setPreferredSize(new Dimension(Width, jPanel8.getHeight()));
        jPanel8.setMaximumSize(new Dimension(Width, jPanel8.getHeight()));
        jScrollPane13.setPreferredSize(new Dimension(Width + 10, jScrollPane13.getHeight()));
        jScrollPane13.setMaximumSize(new Dimension(Width + 10, jScrollPane13.getHeight()));
        for (Component c : jPanel8.getComponents()) {
            JButton b = (JButton) c;
            b.setHorizontalAlignment(SwingConstants.CENTER);
            b.setHorizontalTextPosition(SwingConstants.CENTER);
            if (b.equals(navigator)) {
                continue;
            }
            b.setText("");
        }
        validate();
        repaint();
        doLayout();
        navExpand = false;
    }

    public void SideBarExpand(int Width) {
        navigator.setToolTipText("Close Navigation");
        jPanel8.setPreferredSize(new Dimension(Width, jPanel8.getHeight()));
        jPanel8.setMaximumSize(new Dimension(Width, jPanel8.getHeight()));
        jScrollPane13.setPreferredSize(new Dimension(Width + 10, jScrollPane13.getHeight()));
        jScrollPane13.setMaximumSize(new Dimension(Width + 10, jScrollPane13.getHeight()));
        for (Component c : jPanel8.getComponents()) {
            JButton b = (JButton) c;
            if (b.equals(navigator)) {
                continue;
            }
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setHorizontalTextPosition(SwingConstants.RIGHT);
            if (b.equals(navigator)) {
                continue;
            }
            b.setText(b.getToolTipText());
        }
        validate();
        repaint();
        doLayout();
        navExpand = true;
    }

    public JInternalFrame[] getInFrames() {
        return new JInternalFrame[]{Marks_frame, Print_frame, selection, Health_frame, Manage_frame};
    }

    void setNextPrev() {
        if (detailsTable.getRowCount() == 0) {
            jButton35.setEnabled(false);
            jButton34.setEnabled(false);
        } else if (detailsTable.getEditingRow() == detailsTable.getRowCount() - 1) {
            jButton34.setEnabled(false);
            jButton35.setEnabled(true);
        } else if (detailsTable.getEditingRow() <= 0) {
            jButton35.setEnabled(false);
            jButton34.setEnabled(true);
        } else {
            jButton35.setEnabled(true);
            jButton34.setEnabled(true);
        }
    }

    public void setMessage(String m, MessageType type) {
        com.Communication.Messages.setMessage(m, Message, jLabel4, type, 0);
    }

    public void setMessage(String m, MessageType type, int time) {
        com.Communication.Messages.setMessage(m, Message, jLabel4, type, time);
    }

    void setCurrentStudent() {
        if (!Arrays.asList(MainDesktop.getComponents()).contains(currentStudent)) {
            MainDesktop.add(currentStudent);
        }
        currentStudent.setSize(420, 250);
        currentStudent.setLocation(4, 4);
        currentStudent.validate();
        currentStudent.repaint();
    }

    void showCurrentStudent(int time) {
        setCurrentStudent();
        currentStudent.show();
        
        if (time != 0) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    currentStudent.hide();
                    timer.cancel();
                }
            };
            timer.schedule(task, time, 1);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CS_Copy = new javax.swing.JPopupMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        Settings = new javax.swing.JPopupMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane13 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        fullbtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        fullbtn1 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        navigator = new javax.swing.JButton();
        NavigationBar = new javax.swing.JPanel();
        jButton35 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        currentFrameLabel = new javax.swing.JLabel();
        user = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        boxPanel = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        MainDesktop = new JDesktopPane(){
            @Override
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon("src\\Images\\Logo-2.pn").getImage(), 0, 0, MainDesktop.getWidth()==0?500:MainDesktop.getWidth(), MainDesktop.getHeight()==0?500:MainDesktop.getHeight(), this);
            }
        };
        jPanel10 = new javax.swing.JPanel();
        Message = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();

        jMenuItem11.setText("Copy Text");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        CS_Copy.add(jMenuItem11);

        Settings.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        Settings.setMinimumSize(new java.awt.Dimension(200, 400));
        Settings.setPreferredSize(new java.awt.Dimension(200, 250));

        jMenu1.setText("Security");

        jMenuItem1.setText("Change the password");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem4.setText("Security recovery");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        Settings.add(jMenu1);

        jMenu6.setText("App prefference");
        jMenu6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenu6.setMargin(new java.awt.Insets(5, 5, 5, 5));

        jMenuItem2.setText("LIGHT MODE");
        jMenuItem2.setToolTipText("Needs Restart");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem2);

        jMenuItem3.setText("DARK MODE");
        jMenuItem3.setToolTipText("Needs Restart");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem3);

        Settings.add(jMenu6);

        jMenuItem6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem6.setText("Go to login");
        jMenuItem6.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        Settings.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem7.setText("Exit");
        jMenuItem7.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        Settings.add(jMenuItem7);

        jMenuItem8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jMenuItem8.setText("About");
        jMenuItem8.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        Settings.add(jMenuItem8);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(AppConfig.APPNAME);
        setLocation(new java.awt.Point(100, 100));
        setMinimumSize(new java.awt.Dimension(800, 431));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel8.setPreferredSize(new java.awt.Dimension(55, 615));

        jButton11.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jButton11.setIcon(getIcon("src\\Images\\Toolbar\\Home-"+getBorW()+".png"));
        jButton11.setToolTipText("Home");
        buttonGroup1.add(jButton11);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton11.setMaximumSize(new java.awt.Dimension(260, 65));
        jButton11.setMinimumSize(new java.awt.Dimension(55, 55));
        jButton11.setPreferredSize(new java.awt.Dimension(55, 55));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jButton1.setIcon(getIcon("src\\Images\\Toolbar\\ManageStudents-"+getBorW()+".png"));
        jButton1.setToolTipText("Manage details");
        buttonGroup1.add(jButton1);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton1.setMaximumSize(new java.awt.Dimension(260, 65));
        jButton1.setMinimumSize(new java.awt.Dimension(55, 55));
        jButton1.setPreferredSize(new java.awt.Dimension(55, 55));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        fullbtn.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        fullbtn.setIcon(getIcon("src\\Images\\Toolbar\\Single-"+getBorW()+".png"));
        fullbtn.setToolTipText("Single student mode");
        buttonGroup1.add(fullbtn);
        fullbtn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        fullbtn.setMaximumSize(new java.awt.Dimension(260, 65));
        fullbtn.setMinimumSize(new java.awt.Dimension(55, 55));
        fullbtn.setPreferredSize(new java.awt.Dimension(55, 55));
        fullbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullbtnActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jButton2.setIcon(getIcon("src\\Images\\Toolbar\\Analyze-"+getBorW()+".png"));
        jButton2.setToolTipText("Data analyzer");
        buttonGroup1.add(jButton2);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton2.setMaximumSize(new java.awt.Dimension(260, 65));
        jButton2.setMinimumSize(new java.awt.Dimension(55, 55));
        jButton2.setPreferredSize(new java.awt.Dimension(55, 55));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jButton14.setIcon(getIcon("src\\Images\\Toolbar\\MarksReport-"+getBorW()+".png"));
        jButton14.setToolTipText("Marks report");
        buttonGroup1.add(jButton14);
        jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton14.setMaximumSize(new java.awt.Dimension(260, 65));
        jButton14.setMinimumSize(new java.awt.Dimension(55, 55));
        jButton14.setPreferredSize(new java.awt.Dimension(55, 55));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jButton12.setIcon(getIcon("src\\Images\\Toolbar\\Health-"+getBorW()+".png"));
        jButton12.setToolTipText("Health report");
        buttonGroup1.add(jButton12);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton12.setMaximumSize(new java.awt.Dimension(260, 65));
        jButton12.setMinimumSize(new java.awt.Dimension(55, 55));
        jButton12.setPreferredSize(new java.awt.Dimension(55, 55));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        fullbtn1.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        fullbtn1.setIcon(getIcon("src\\Images\\Toolbar\\Print-"+getBorW()+".png"));
        fullbtn1.setToolTipText("Report printer");
        buttonGroup1.add(fullbtn1);
        fullbtn1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        fullbtn1.setMaximumSize(new java.awt.Dimension(260, 65));
        fullbtn1.setMinimumSize(new java.awt.Dimension(55, 55));
        fullbtn1.setPreferredSize(new java.awt.Dimension(55, 55));
        fullbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullbtn1ActionPerformed(evt);
            }
        });

        jButton41.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jButton41.setIcon(getIcon("src\\Images\\Toolbar\\Settings-"+getBorW()+".png"));
        jButton41.setToolTipText("Settings");
        jButton41.setFocusable(false);
        jButton41.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton41.setMaximumSize(new java.awt.Dimension(260, 65));
        jButton41.setMinimumSize(new java.awt.Dimension(40, 40));
        jButton41.setPreferredSize(new java.awt.Dimension(77, 90));
        jButton41.setVerifyInputWhenFocusTarget(false);
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        navigator.setFont(new java.awt.Font("Tahoma", 0, 40)); // NOI18N
        navigator.setText("≡");
        navigator.setToolTipText("Open Navigation");
        navigator.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        navigator.setMargin(new java.awt.Insets(0, 0, 0, 0));
        navigator.setMaximumSize(new java.awt.Dimension(260, 65));
        navigator.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        navigator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navigatorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fullbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fullbtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(navigator, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(navigator, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(fullbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(fullbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane13.setViewportView(jPanel8);

        NavigationBar.setForeground(new java.awt.Color(204, 204, 204));

        jButton35.setIcon(getImage("src\\Images\\Toolbar\\DropLeft-"+getBorW()+".png",30,30));
        jButton35.setBorder(null);
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jButton34.setIcon(getImage("src\\Images\\Toolbar\\DropRight-"+getBorW()+".png",30,30));
        jButton34.setBorder(null);
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jToggleButton1.setIcon(getImage("src\\Images\\Toolbar\\ShowStudent-"+getBorW()+".png",30,30));
        jToggleButton1.setBorder(null);
        jToggleButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseExited(evt);
            }
        });
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        currentFrameLabel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        currentFrameLabel.setText("Home");

        user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        user.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        user.setPreferredSize(new java.awt.Dimension(45, 45));
        user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        javax.swing.GroupLayout NavigationBarLayout = new javax.swing.GroupLayout(NavigationBar);
        NavigationBar.setLayout(NavigationBarLayout);
        NavigationBarLayout.setHorizontalGroup(
            NavigationBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationBarLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentFrameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        NavigationBarLayout.setVerticalGroup(
            NavigationBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NavigationBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentFrameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jButton35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        boxPanel.setMaximumSize(new java.awt.Dimension(45456546, 45456546));
        boxPanel.setMinimumSize(new java.awt.Dimension(585, 200));
        boxPanel.setPreferredSize(new java.awt.Dimension(1162, 622));
        boxPanel.setLayout(new javax.swing.BoxLayout(boxPanel, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane12.setMinimumSize(new java.awt.Dimension(500, 500));
        jScrollPane12.setPreferredSize(new java.awt.Dimension(1072, 622));

        MainDesktop.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                MainDesktopComponentResized(evt);
            }
        });

        javax.swing.GroupLayout MainDesktopLayout = new javax.swing.GroupLayout(MainDesktop);
        MainDesktop.setLayout(MainDesktopLayout);
        MainDesktopLayout.setHorizontalGroup(
            MainDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 929, Short.MAX_VALUE)
        );
        MainDesktopLayout.setVerticalGroup(
            MainDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        jScrollPane12.setViewportView(MainDesktop);

        boxPanel.add(jScrollPane12);

        jPanel10.setPreferredSize(new java.awt.Dimension(0, 28));

        Message.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        Message.setToolTipText("Message blog");
        Message.setBorder(null);
        Message.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Message.setEnabled(false);
        Message.setFocusable(false);
        Message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MessageActionPerformed(evt);
            }
        });

        jLabel4.setPreferredSize(new java.awt.Dimension(16, 16));

        jButton8.setText("Logger");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Message, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addGap(325, 325, 325)
                .addComponent(jButton8)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Message, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(NavigationBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 931, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(NavigationBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boxPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1012, 709));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int t = JOptionPane.showConfirmDialog(null, "Are you sure want to change the Theme!\nThis will restart the Application");
        if (t == 0) {
            setDefault(Defaults.LookAndFeel, "light", DBconnect.CONN);
            this.dispose();
            new com.ui.frames.UserLoginFrame("light").setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        int t = JOptionPane.showConfirmDialog(null, "Are you sure want to change the Theme!\nThis will restart the Application");
        if (t == 0) {
            setDefault(Defaults.LookAndFeel, "dark", DBconnect.CONN);
            this.dispose();
            new com.ui.frames.UserLoginFrame("dark").setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void fullbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullbtn1ActionPerformed
        selectFrame(Print_frame);
    }//GEN-LAST:event_fullbtn1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        selectFrame(Analyze_frame);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        selectFrame(Manage_frame);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void fullbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullbtnActionPerformed
        selectFrame(currentStudent);
    }//GEN-LAST:event_fullbtnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        int t = JOptionPane.showConfirmDialog(null, "Are you sure want to Exit!");
        if (t == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        try {
            openFile("Student Management System-My Soft Documentation.pdf");
        } catch (IOException ex) {
            Commons.showMsg(ex);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed
    String[] selectedIds;

    public static Object[] getDecrypted(Object[] data, String id) {
        Object[] data1 = new Object[data.length];
        for (int i = 0; i < data1.length; i++) {
            data1[i] = getDecrypted(data[i], id);
        }
        return data1;
    }

    public static String[] getDecrypted(String[] data, String id) {
        String[] data1 = new String[data.length];
        for (int i = 0; i < data1.length; i++) {
            data1[i] = getDecrypted(data[i], id);
        }
        return data1;
    }

    public static Object[][] getDecrypted(Object[][] data, String id) {
        Object[][] data1 = new Object[data.length][data[0].length];
        for (int i = 0; i < data1.length; i++) {
            data1[i] = getDecrypted(data[i], id);
        }
        return data1;
    }

    public static String[][] getDecrypted(String[][] data, String id) {
        String[][] data1 = new String[data.length][data[0].length];
        for (int i = 0; i < data1.length; i++) {
            data1[i] = getDecrypted(data[i], id);
        }
        return data1;
    }

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        selectFrame(selection);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        selectFrame(Health_frame);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        selectFrame(Marks_frame);
    }//GEN-LAST:event_jButton14ActionPerformed
    String ID;
    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        int row = detailsTable.getSelectedRow() + 1;
        detailsTable.setRowSelectionInterval(detailsTable.getSelectedRow() + 1, detailsTable.getSelectedRow() + 1);
        if (row > detailsTable.getRowCount() - 1) {
            jButton34.setEnabled(false);
            if (detailsTable.getRowCount() == 0) {
                jButton35.setEnabled(false);
            }
        } else {
            detailsTable.setEditingRow(row);
            ID = detailsTable.getValueAt(row, 0).toString();
        }
        showCurrentStudent(2000);
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        detailsTable.setRowSelectionInterval(detailsTable.getSelectedRow() - 1, detailsTable.getSelectedRow() - 1);
        int row = detailsTable.getSelectedRow();
        if (row == -1) {
            jButton35.setEnabled(false);
            jButton34.setEnabled(true);
            if (detailsTable.getRowCount() == 0) {
                jButton35.setEnabled(false);
            }
        } else {
            detailsTable.setEditingRow(row);
            ID = detailsTable.getValueAt(row, 0).toString();
        }
        showCurrentStudent(2000);
    }//GEN-LAST:event_jButton35ActionPerformed


    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
//        if (GlobalSearch.getWidth() == GlobalSearch.getMaximumSize().width) {
//            jScrollPane12.setMaximumSize(new Dimension(32767, 32767));
//        } else {
//            jScrollPane12.setMaximumSize(new Dimension(1072, 32767));
//        }
        try {
            jScrollPane12.validate();
            jScrollPane12.repaint();
            if (jScrollPane12.getHeight() / 2 - FRAMEHEIGHT / 2 > 130) {
                jScrollPane12.add(currentStudent);
                currentStudent.setBounds(5, 5, 130, 320);
            } else {
                jScrollPane12.remove(currentStudent);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formComponentResized

    private void jToggleButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1MouseClicked

    private void jToggleButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseEntered
        if (!jToggleButton1.isSelected()) {
            showCurrentStudent(0);
        } else {
            currentStudent.moveToFront();
        }
    }//GEN-LAST:event_jToggleButton1MouseEntered

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (!jToggleButton1.isSelected()) {
            currentStudent.hide();
        } else {
            showCurrentStudent(0);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseExited
        if (!jToggleButton1.isSelected()) {
            currentStudent.hide();
        }
    }//GEN-LAST:event_jToggleButton1MouseExited

    public static Object getDecrypted(Object str, String id) {
        String data = getDecrypted(str.toString(), id);
        return data;
    }
    public static String[] dc = {"{id}", "{name}", "{grade}", "{subgrade}", "{mediaum}", "{dob}", "{gender}", "{tp}", "{address}", "{guard}", "{email}"};

    public static String getDecrypted(String str, String id) {
        String[] data = new Student(id).getAllS();
        for (int i = 0; i < dc.length; i++) {
            str = str.replace(dc[i], data[i]);
        }
        return str;
    }

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        Settings.show(jPanel8, jButton41.getX() + jButton41.getWidth(), jButton41.getY() + jButton41.getHeight() - Settings.getHeight());
    }//GEN-LAST:event_jButton41ActionPerformed

    private void navigatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navigatorActionPerformed
        if (isNavExpanded()) {
            SideBarCollapse(55);
        } else {
            SideBarExpand(240);
        }
    }//GEN-LAST:event_navigatorActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        ChangePassword changePassword = new ChangePassword(security);
        changePassword.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        PasswordOptions p = new PasswordOptions(security.getUsername(), this, true) {
            @Override
            public void actionTaken(int action) {
                new Recovery(security.getUsername()).setVisible(true);
            }
        };
        CommonUi.genarateCenter(p);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void MessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MessageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MessageActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed

    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void MainDesktopComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_MainDesktopComponentResized
        if (currentFrame == null) {
            return;
        }
        if (currentFrame.isDisplayable() && currentFrame.isVisible()) {
            currentFrame.setBounds(4, 4, MainDesktop.getWidth() - 4, MainDesktop.getHeight() - 4);
            currentFrame.validate();
        }
    }//GEN-LAST:event_MainDesktopComponentResized

    private void userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userMouseClicked
        CommonUi.genarateCenter(schoolDetails, 706, 570);
        schoolDetails.setVisible(true);
        loadNavigation();
    }//GEN-LAST:event_userMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
//        if (com.Codes.System.out.b) {
//            com.Codes.System.out.update();
//        } else {
//            com.Codes.System.out.show();
//        }
    }//GEN-LAST:event_jButton8ActionPerformed

//    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, e);
        }
//new UserFrame(null).setVisible(true);
        java.awt.EventQueue.invokeLater(() -> {
            new UserFrame(null).setVisible(true);
        });
    }

//    public boolean drop = true;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu CS_Copy;
    private javax.swing.JDesktopPane MainDesktop;
    private javax.swing.JTextField Message;
    private javax.swing.JPanel NavigationBar;
    private javax.swing.JPopupMenu Settings;
    private javax.swing.JPanel boxPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel currentFrameLabel;
    private javax.swing.JButton fullbtn;
    private javax.swing.JButton fullbtn1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JButton navigator;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables

}
