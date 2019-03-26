package com.shouzhan.design.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * @author danbin
 * @version MD5Utils.java, v 0.1 2019-03-25 下午1:52 danbin
 */
public class MD5Utils {

    private final static String DIGEST_ALGORITHM_MD5 = "MD5";

    /**
     * 用来将字节转换成 16 进制表示的字符
     */
    private final static String[] HEX_DIGITS = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
            "f"};

    public static String getFileMD5(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        FileChannel fch = null;
        try {
            fis = new FileInputStream(file);
            fch = fis.getChannel();
            MappedByteBuffer byteBuffer = fch.map(
                    FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest messageDigest = MessageDigest
                    .getInstance(DIGEST_ALGORITHM_MD5);
            messageDigest.update(byteBuffer);
            return byteArrayToHexString(messageDigest.digest());
        } catch (Exception e) {
            return null;
        } finally {
            Utils.silentClose(fch);
            Utils.silentClose(fis);
        }
    }

    /**
     * MD5加密算法的具体实现
     *
     * @param src 需要加密的字符串
     * @return 加密后的结果字符串, 32位, 小写
     */
    public static String getStringMD5(String src) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        String result = null;
        try {
            result = new String(src);
            result = byteArrayToHexString(MessageDigest.getInstance(
                    DIGEST_ALGORITHM_MD5).digest(result.getBytes()));
        } catch (Exception err) {
            err.printStackTrace();
        }
        return result.toLowerCase();
    }

    public static String byteArrayToHexString(byte[] digest) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            result.append(byteToHexString(digest[i]));
        }
        return result.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }


}
