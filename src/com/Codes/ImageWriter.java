package com.Codes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageWriter {

    public static BufferedImage getImage(Image img) {
        BufferedImage buffer = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = (Graphics2D) buffer.getGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return buffer;
    }

    public static BufferedImage getImage(ImageIcon image) {
        return getImage(image.getImage());
    }

    public static File write(BufferedImage img, String ext, File f) throws IOException {
        ImageIO.write(img, ext, f);
        return f;
    }
}
