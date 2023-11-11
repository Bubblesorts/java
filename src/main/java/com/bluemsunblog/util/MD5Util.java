package com.bluemsunblog.util;

import java.security.MessageDigest;

public class MD5Util {

    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 将字节转化称十六进制字符串
     * @param b
     * @return
     */
    private static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 将字节数组转化称十六进制字符串
     * @param bArr
     * @return
     */
    private static String byteArrayToHexString(byte[] bArr){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < bArr.length; i++){
            stringBuffer.append(byteToHexString(bArr[i]));
        }
        return stringBuffer.toString();
    }


    public static String MD5Encode(String origin, String charsetname){
        String res = null;
        try{
            res = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(charsetname == null || "".equals(charsetname)){
                res = byteArrayToHexString(md.digest(res.getBytes()));
            }else{
                res = byteArrayToHexString(md.digest(res.getBytes(charsetname)));
            }
        }catch (Exception exception){

        }
        return res;
    }
}


