package com.ui.components;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class TextField {

    JTextField f;
    String min = "0";
    boolean focusedNow = false;
    boolean dotAlowed = true;
    Runnable func = null;

    public Runnable getFunc() {
        return func;
    }

    public void setFunc(Runnable func) {
        this.func = func;
    }

    public boolean isDotAlowed() {
        return dotAlowed;
    }

    public void setMinDigit(char digit) {
        min = String.valueOf(digit);
    }

    public void setDotAlowed(boolean dotAlowed) {
        this.dotAlowed = dotAlowed;
    }
    boolean clearWhenFocused = false;

    public TextField(JTextField f) {
        this.f = f;
    }

    public void focusedNow() {
        focusedNow = true;
    }

    public void focuseLost() {
        focusedNow = false;
    }

    public void clearWhenFocused(boolean b) {
        clearWhenFocused = b;
    }

    public boolean isFocusedNow() {
        return focusedNow;
    }

    public void setAsStandardNumberField() {
        f.setEditable(false);
        f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (f.getText().length() == 1) {
                    if (key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
                        f.setText(min);
                    } else if (Character.isDigit(key) || (key == '.' && dotAlowed)) {
                        if (f.getText().equals(min)) {
                            f.setText(key + "");
                        } else {
                            f.setText(f.getText() + "" + key);
                        }
                    }
                    focuseLost();
                } else if (key == KeyEvent.VK_DELETE) {
                    f.setText(min);
                    focuseLost();
                } else if (key == KeyEvent.VK_BACK_SPACE) {
                    if (focusedNow) {
                        f.setText(min);
                        focuseLost();
                    } else {
                        f.setText(f.getText().substring(0, f.getText().length() - 1));
                    }
                } else if (Character.isDigit(key) || key == '.') {
                    if (focusedNow) {
                        f.setText("" + key);
                        focuseLost();
                    } else {
                        f.setText(f.getText() + "" + key);
                    }
                } else if (func != null) {
                    func.run();
                }
                e.consume();
            }
        });
        f.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                Font fn = f.getFont();
                f.setFont(new Font(fn.getName(), Font.BOLD, fn.getSize()));
                if (clearWhenFocused) {
                    focusedNow();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                Font fn = f.getFont();
                f.setFont(new Font(fn.getName(), Font.PLAIN, fn.getSize()));
                if (clearWhenFocused) {
                    focuseLost();
                }
            }
        });
    }

    public JTextField getF() {
        return f;
    }

    public void setF(JTextField f) {
        this.f = f;
    }

    public boolean isClearWhenFocused() {
        return clearWhenFocused;
    }

    public void setClearWhenFocused(boolean clearWhenFocused) {
        this.clearWhenFocused = clearWhenFocused;
    }

}
