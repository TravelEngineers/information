package com.zgs.information.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author Shmily
 * @email shmily_zgs@163.com
 * @date 2017/9/6 15:11
 */
public class MD5Util {
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5=new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return md5.length()==32?md5.toUpperCase():("0"+md5).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
