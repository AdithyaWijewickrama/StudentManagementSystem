package com.ManageDetails;

import com.ui.CommonUi;
import com.Database.Sql;
import javax.swing.ImageIcon;
import com.Database.DBconnect;
import javax.swing.JLabel;

public class Images extends Thread {

    public static final ImageIcon LOADING = new ImageIcon("src\\Images\\loader.gif");
    public static final int ORIGINAL = 0;
    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;
    public static final int PREFERRED = 3;
    public int option = 0;
    public String id = null;
    public ImageIcon img = null;
    public JLabel label = null;
    public boolean needload = false;

    public Images(String id, JLabel label, int option, boolean needLoad) {
        this.id = id;
        this.label = label;
        this.option = option;
    }

    public ImageIcon getImage(String id) {
        ImageIcon image = null;
        image = Sql.getImage("SELECT Image FROM Images WHERE `Adimssion No.`='" + id + "'", DBconnect.CONN);
        return image;
    }

    @Override
    public void run() {
        label.setIcon(LOADING);
        img = getImage(id);
        label.setIcon(img);
        label.setIcon(img.getIconHeight() > img.getIconWidth() ? CommonUi.getVertFitImage(img, label.getHeight()) : CommonUi.getHorzFitImage(img, label.getWidth()));
        switch (option) {
            case 1:
                label.setIcon(CommonUi.getVertFitImage(img, label.getHeight()));
                break;
            case 2:
                label.setIcon(CommonUi.getHorzFitImage(img, label.getWidth()));
                break;
            case 3:
                label.setIcon(img.getIconHeight() > img.getIconWidth() ? CommonUi.getVertFitImage(img, label.getWidth()) : CommonUi.getHorzFitImage(img, label.getWidth()));
                break;
            case 0:
            default:
                label.setIcon(img);
                break;
        }
    }

}
