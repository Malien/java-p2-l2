package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameChecker {
    //TODO: check if ukr and rus ascii chars differ
    //To my knowledge they do not, though alphabetic differences such as э ъ є ї may conflict
    //FIXME: this method and class is redundant
    public static boolean check(String toCheck){
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Яа-яА-Я_'`]+\\s*\\d*");
        Matcher matcher = pattern.matcher(toCheck);
        return matcher.matches();
    }
}
