package com.pcc.defsex;

import java.io.UnsupportedEncodingException;

public class DefineSex {

    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final int[] TONES_LETTERS = { 1091, 1077, 1110, 1072, 1086, 1108, 1103, 1102 };

    private String name;

    public void setName(final String name) throws UnsupportedEncodingException {
        this.name = new String(name.getBytes(), "cp1251");
    }

    public String getSex() {
        int charCodeOfLastLetter = name.charAt(name.length() - 1);
        for(int tone : TONES_LETTERS) {
            if(tone == charCodeOfLastLetter) {
                return FEMALE;
            }
        }
        return MALE;
    }

    public DefineSex(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void main(final String[] args) {
        DefineSex defineSex = new DefineSex("Сергій");
        System.out.println(defineSex.getSex());
    }
}
