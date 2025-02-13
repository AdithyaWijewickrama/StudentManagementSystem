/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui.components;

import com.Codes.Commons;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class RoundLabel extends JLabel {
    Color borderColor;
    public RoundLabel(String label, ImageIcon icon) {
        super(label);
        setIcon(icon);
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);
        setIgnoreRepaint(false);
    }
    public RoundLabel(String label,int width,int height,Color borderColor) {
        super(label);
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setIgnoreRepaint(false);
        this.borderColor=borderColor;
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(new Color(0,0,0));
        g.setColor(borderColor);
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintBorder(g);
    }

    Shape shape;

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        JLabel l = new RoundLabel("",100,100,Color.BLUE);
        JFrame frame = new JFrame();
        l.setSize(100, 100);
        frame.add(l);
        frame.getContentPane().setBackground(Color.yellow);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setSize(150, 150);
        UIManager.setLookAndFeel(new FlatLightLaf());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
