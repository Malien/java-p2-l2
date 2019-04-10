package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegExChecker {
    public static boolean checkName(String toCheck){
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Яа-яА-Я_'`]+\\s*\\d*");
        Matcher matcher = pattern.matcher(toCheck);
        return matcher.matches();
    }
    public static boolean checkDouble(String toCheck){
        Pattern pattern = Pattern.compile("[\\d]+[.]?[\\d]+");
        Matcher matcher = pattern.matcher(toCheck);
        return matcher.matches();
    }
}
