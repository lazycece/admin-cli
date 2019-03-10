package com.lazycece.admin.common.utils;

import java.math.BigInteger;
import java.util.UUID;

/**
 * @author lazycece
 */
public class UUIDUtils {

    /**
     * init a string which length is 32
     *
     * @return string
     */

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * get a array of uuid string ,the array's length is number
     *
     * @param number unsigned int
     * @return array
     */
    public static String[] uuidArray(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = uuid();
        }
        return ss;
    }

    public static String sn(int num) {
        String uuid = uuid();
        String longSn = new BigInteger(uuid, 16).toString();
        return longSn.substring(0, num);
    }
}
