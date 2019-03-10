package com.lazycece.admin.common.utils;

/**
 * @author lazycece
 */
public class StringUtils {

    /**
     * validate a string is blank or not.
     * return <code>true</code>, if string is null，"" or  "'   '";
     * otherwise return <code>false</code>
     *
     * @param str string
     * @return true/false
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * validate a string isn't blank or be.
     * return <code>false</code>, if string is null，"" or  "'   '";
     * otherwise return <code>true</code>
     *
     * @param str string
     * @return true/false
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}