package com.example.vulcan.okhttp;

import android.util.Base64;

public class Base64Operator {

    public static String encode(byte []bytes){
        String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        return base64;
    }

    public static byte[] decode(String base64){
        byte[] bytes=Base64.decode(base64,Base64.DEFAULT);
        return bytes;
    }
}
