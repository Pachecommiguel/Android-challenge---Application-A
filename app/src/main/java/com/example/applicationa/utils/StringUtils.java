package com.example.applicationa.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

abstract public class StringUtils {

    public static String UTF8ToHex(String element) {
        byte[] bytes = element.getBytes(StandardCharsets.UTF_8);
        return String.format("%x", new BigInteger(1, bytes));
    }
}
