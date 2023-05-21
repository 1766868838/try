package com.example.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordToKey{
    static byte[] salt = new byte[16];
    private static final int KEY_LENGTH = 16; // 密钥长度
    private static final String HASH_ALGORITHM = "SHA-256"; // 哈希算法

    public static byte[] main(String p,byte[] salt) {
        String password = p; // 用户输入的口令
        byte[] hashedKey = hashKey(password, salt); // 哈希口令和盐得到密钥
        byte[] finalKey = new byte[KEY_LENGTH];
        System.arraycopy(hashedKey, 0, finalKey, 0, KEY_LENGTH); // 截取前16字节作为最终密钥
        System.out.println("Final key: " + bytesToHexString(finalKey));
        return finalKey;
    }

    /**
     * 哈希函数
     * @param password
     * @param salt
     * @return 密钥
     */
    private static byte[] hashKey(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.reset();
            digest.update(salt);
            byte[] hash = digest.digest(password.getBytes());
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param bytes[]
     * @return 十六进制字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

    public static byte[] hexStringToBytes(String str){
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
}
