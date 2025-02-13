package com.Communication;

import com.Codes.AppConfig;
import com.Codes.Commons;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

public class TrayMessage {

    static final String DEF = "";
    public String mainMsg;
    public Image Image;
    public String subMsg;
    public String toolTip;
    public MessageType MsgType;
    public String path = null;
    public Container parent;
    
    public TrayMessage(String mainMsg) {
        this(mainMsg, null, DEF, DEF);
    }

    public TrayMessage(String mainMsg, Image Image) {
        this(mainMsg, Image, DEF, DEF);
    }

    public TrayMessage(String mainMsg, Image Image, String subMsg) {
        this(mainMsg, Image, subMsg, DEF);
    }

    public TrayMessage(String mainMsg, String subMsg, MessageType type) {
        this(mainMsg, null, subMsg, null, type);
    }

    public TrayMessage(String mainMsg, String subMsg, Image Image, MessageType type) {
        this(mainMsg, Image, subMsg, null, type);
    }

    public TrayMessage(String mainMsg, Image Image, String subMsg, String toolTip) {
        this(mainMsg, Image, subMsg, toolTip, MessageType.NONE);
    }

    public TrayMessage(String mainMsg, Image Image, String subMsg, String toolTip, MessageType messageType) {
        this.mainMsg = mainMsg;
        this.Image = Image;
        this.subMsg = subMsg;
        this.toolTip = toolTip;
        this.MsgType = messageType;
        if(Image==null){
            this.Image=AppConfig.SECUREICON.getImage();
        }
    }

    public MessageType getMsgType() {
        return MsgType;
    }

    public void setMsgType(MessageType MsgType) {
        this.MsgType = MsgType;
    }

    public String getMainMsg() {
        return mainMsg;
    }

    public void setMainMsg(String mainMsg) {
        this.mainMsg = mainMsg;
    }

    public Image getImage() {
        return Image;
    }

    public void setImage(Image Image) {
        this.Image = Image;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public void display() {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(Image);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(toolTip);
        if (path != null) {
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (new File(path).exists()) {
                            Commons.openFile(path);
                        }
                        trayIcon.removeActionListener(this);
                        tray.remove(trayIcon);
                    } catch (IOException ex) {
                    }
                }
            });
        }
        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
            java.util.logging.Logger.getLogger(TrayMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        trayIcon.displayMessage(mainMsg, subMsg, MsgType);
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tray.remove(trayIcon);
                cancel();
            }
        };
        t.schedule(task, 5000);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}