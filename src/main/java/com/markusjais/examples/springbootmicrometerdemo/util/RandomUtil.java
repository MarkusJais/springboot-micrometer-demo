package com.markusjais.examples.springbootmicrometerdemo.util;

import java.util.Random;

public enum RandomUtil {

    INSTANCE;
    Random random = new Random();

    public int getRandomIntBetween(int lowerBound, int upperBound) {
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }

}