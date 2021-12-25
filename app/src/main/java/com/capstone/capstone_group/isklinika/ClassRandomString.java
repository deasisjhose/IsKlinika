package com.capstone.capstone_group.isklinika;

import java.util.Random;

public class ClassRandomString {

    private final String DATA = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Random RANDOM ;
    private int length ;
    private StringBuilder sb ;

    public ClassRandomString() {
        this.RANDOM = new Random() ;
    }

    public String createRandom(int length){
        this.length = length;
        sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }
        return sb.toString() ;
    }
}
