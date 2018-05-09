package edu.heuu.campusAssistant.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Œƒæ≤√¿¿ˆ–°TeTe on 2018/5/6.
 */

public class TangCrypt {
    private static String tang_key = "1111";
    private static String tang_sign = "1111";
    public static String crypt(String cont){
        return cont;
    }

    public static String sign(String cont){
        return cont;
    }

    public static String md5(String cont){
        if (cont.length() == 0) {
            return "";
        }

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(cont.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }
}
