package com.woc.common.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class EncryptUtil {

    /**
     * PKCS5Padding -- Pkcs7 两种padding方法都可以
     * @param content 3c2b1416d82883dfeaa6a9aa5ecb8245  16进制
     * @param key
     * @return
     */
    public static String decryptAES(String content, String key) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // "算法/模式/补码方式"
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return new String(cipher.doFinal(parseHexStr2Byte(content)),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码
     * @param content
     * @param key
     * @return
     */
    public static String encryptAES(String content,String key){
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // "算法/模式/补码方式"

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return parseByte2HexStr(cipher.doFinal(content.getBytes("utf-8"))).toLowerCase() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    public static void main(String[] args) throws Throwable {

        String data = "c12d8ce6bdc3199d0d0c2d4c83099662";
        String key = "1567872445341000";

        String content = "962494";

        String decrypt = decryptAES(data,key);

        String s = encryptAES(content, key);

        System.out.println("解密后数据" + decrypt);
        System.out.println("加密后数据" + s );


    }

}