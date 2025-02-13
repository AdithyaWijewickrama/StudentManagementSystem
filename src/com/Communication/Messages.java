package com.Communication;

import com.Codes.Commons;
import java.awt.TrayIcon.MessageType;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Messages {

    public static final int TIME = 2000;
    public static final ImageIcon INFO = new ImageIcon("src\\Images\\Message\\Info.png");
    public static final ImageIcon ERROR = new ImageIcon("src\\Images\\Message\\Error.png");
    public static final ImageIcon WARNING = new ImageIcon("src\\Images\\Message\\Warning.png");
    public static final ImageIcon GOOD = new ImageIcon("src\\Images\\Message\\Good.png");

    public static void setMessage(String msg, JTextField msgField, int time) {
        Messages.setMessage(msg, msgField, null, MessageType.NONE, 0);
    }

    public static void setMessage(String msg, JTextField msgField, JLabel icoLabel, MessageType msgType, int time) {
        if (msg != null) {
            msgField.setText(msg);
        }
        if (icoLabel != null) {
            ImageIcon icon = null;
            switch (msgType) {
                case ERROR:
                    icon = ERROR;
                    break;
                case INFO:
                    icon = INFO;
                    break;
                case WARNING:
                    icon = WARNING;
                    break;
            }
            icoLabel.setIcon(Commons.getImage(icon, 16, 16));
        }
        if (time != 0) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    msgField.setText("");
                    icoLabel.setIcon(null);
                    cancel();
                }
            };
            timer.schedule(task, time, 1);
        }
    }

    
}
