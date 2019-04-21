package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegExChecker {
    public static boolean checkDouble(String toCheck){
        Pattern pattern = Pattern.compile("[\\d]+[.]?[\\d]+");
        Matcher matcher = pattern.matcher(toCheck);
        return matcher.matches();
    }
    public static boolean checkInteger(String toCheck){
        Pattern pattern = Pattern.compile("([123456789]+[\\d]*)|0");
        Matcher matcher = pattern.matcher(toCheck);
        return matcher.matches();
    }
    public static boolean checkIntegerWithoutZero(String toCheck){
        Pattern pattern = Pattern.compile("[\\d]+");
        Matcher matcher = pattern.matcher(toCheck);
        return matcher.matches();
    }
}
