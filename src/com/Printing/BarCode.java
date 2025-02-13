/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Printing;

import com.Codes.Commons;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
public class BarCode {

    public static BufferedImage getBuffer(String data, BarcodeFormat formate, int w, int h) throws WriterException, IOException {
        return MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(new String(data.getBytes("UTF-8"), "UTF-8"), formate, w, h));
    }
    public static BufferedImage getBuffer(byte[] data, BarcodeFormat formate, int w, int h) throws WriterException, IOException {
        return MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(new String(data, "UTF-8"), formate, w, h));
    }

    public static void create(String data, String path, BarcodeFormat formate, int w, int h) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes("UTF-8"), "UTF-8"), formate, w, h);
        MatrixToImageConfig f = new MatrixToImageConfig();
        File file=File.createTempFile("SMS Temp file", path.substring(path.lastIndexOf('.') + 1));
        file.deleteOnExit();
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), file, f);
    }

    public static String read(String path) throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
        Result rslt = new MultiFormatReader().decode(binaryBitmap);
        return rslt.getText();
    }

}

class t {

    public static void main(String[] args) throws IOException, WriterException {
        String s = "";
        for (int i = 0; i < 1; i++) {
            s = s.concat("a");
        }
        try {
            String img = "DemoImages\\1.png";
            BarCode.create(s, "QR.png", BarcodeFormat.PDF417, 50, 50);
            Commons.openFile("QR.png");
            FileInputStream im = new FileInputStream(new File(img));
            byte[] imgByte = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (int i; (i = im.read(imgByte)) != -1;) {
                bos.write(imgByte, 0, i);
            }
            imgByte = bos.toByteArray();
            ImageIO.write(BarCode.getBuffer(imgByte,BarcodeFormat.QR_CODE,500,500), "png", new File("QR.png"));
            System.out.println(BarCode.read("C:\\Users\\Dell\\Downloads\\Screenshot 2021-11-11 at 10-31-52 1-Chathurika Maduwanthi pdf.png"));
        } catch (IOException ex) {
            Logger.getLogger(t.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(t.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
