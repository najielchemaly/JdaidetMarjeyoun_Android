package com.municipality.jdeidetmarjeyoun.utils;

/**
 * Created by Charbel on 9/13/2017.
 */

public final class StringUtils {

    private StringUtils(){}

    public static boolean isNull(Object obj){
        return obj==null || "null".equals(obj.toString().trim());
    }

    public static boolean isEmpty(String str){
        return str.trim().isEmpty();
    }

    public static boolean isValid(String str){
        return !isNull(str)&& !isEmpty(str);
    }

    public static boolean isValid(Object obj){
        return isValid(obj.toString());
    }

}
