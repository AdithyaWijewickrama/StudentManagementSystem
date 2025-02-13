package com.SystemSecurity;

import java.util.ArrayList;
import java.util.Random;

public class Encryption {
    ArrayList<Character> decrypt = new ArrayList<>();
    ArrayList<Character> encrypt = new ArrayList<>();

    public Encryption() {
        for (int i = 32; i < 128; i++) {
            decrypt.add((char) i);
        }
    }

    private void suffle(char key) {
        for (int i = (int) key; i < 128; i++) {
            encrypt.add((char) i);
        }
        for (int i = 32; i < (int) key; i++) {
            encrypt.add((char) i);
        }
    }

    public String encrypt(String original) {
        char key = (char) (new Random().nextInt(127 - 33) + 33);
        suffle(key);
        char[] d = original.toCharArray();
        String e = "";
        for (int i = 0; i < d.length; i++) {
            e = e.concat(decrypt.indexOf(d[i]) == -1 ? Character.toString(d[i]) : encrypt.get(decrypt.indexOf(d[i])).toString());
        }
        e = e.concat(Character.toString(key));
        return e;
    }

    public String decrypt(String encryptedStr) {
        char[] d = encryptedStr.toCharArray();
        suffle(d[d.length - 1]);
        String e = "";
        for (int i = 0; i < d.length - 1; i++) {
            e = e.concat(encrypt.indexOf(d[i]) == -1 ? Character.toString(d[i]) : decrypt.get(encrypt.indexOf(d[i])).toString());
        }
        return e;
    }

    public static void main(String[] args) {
        System.out.println(new Encryption().decrypt("x)\"5A*4A:063A'\"703*5&At6#+&$5`A"));
        System.out.println(new Encryption().encrypt("My name is fuck"));
        System.out.println(new Encryption().encrypt("My name is fuck"));
        System.out.println(new Encryption().encrypt("My name is fuck"));
    }
}
