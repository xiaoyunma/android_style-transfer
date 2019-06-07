package com.example.vulcan.okhttp.mainMoudle;

import com.example.vulcan.okhttp.Base64Operator;
import com.example.vulcan.okhttp.image.ImageConvert;
import com.example.vulcan.okhttp.okhttp.HttpOperate;
import com.example.vulcan.okhttp.okhttp.Method;
import com.example.vulcan.okhttp.okhttp.RunnableForRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    public LoginService(){}

    public Map sendPostAndRecv(String UserPhone, String UserPassword) {


        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("UserPhone", UserPhone);
            jsonRequest.put("UserPassword", UserPassword);

        } catch (Exception e) {
            System.out.println("错误:" + e);
        }
        HttpOperate operate = new HttpOperate("/login", Method.post, jsonRequest.toString());
        RunnableForRequest runForRequest = new RunnableForRequest(operate);
        Thread requestThread = new Thread(runForRequest);
        requestThread.start();
        //主线程等待线程pt执行结束
        try {
            requestThread.join();
            String responseStr = runForRequest.responseStr;
            return getstate(responseStr);
        } catch (Exception e) {
            return null;
        }
    }

    //从服务端返回的字符串中解析出图片并显示，由public void sendPostAndRecv()调用
    public Map getstate(String responseStr) {
        int  failed=1;
        String IsOnline="1";
        Map map=new HashMap();
        if (!responseStr.equals("Error")) {
            try {
                JSONObject JsonRes = new JSONObject(responseStr);
                failed=JsonRes.getInt("failed");
                IsOnline=JsonRes.getString("IsOnline");
            } catch (Exception e) {
                System.out.println("json错误:" + e);
            }
        }
        map.put("failed",failed);
        map.put("IsOnline",IsOnline);
        return map;
    }



}

