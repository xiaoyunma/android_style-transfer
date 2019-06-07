package com.example.vulcan.okhttp.mainMoudle;

import com.example.vulcan.okhttp.Base64Operator;
import com.example.vulcan.okhttp.image.ImageConvert;
import com.example.vulcan.okhttp.okhttp.HttpOperate;
import com.example.vulcan.okhttp.okhttp.Method;
import com.example.vulcan.okhttp.okhttp.RunnableForRequest;

import org.json.JSONObject;

public class RegisterService {

    public RegisterService(){}

    public String sendPostAndRecv(String UserName, String UserPhone, String UserPassword) {


        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("UserName", UserName);
            jsonRequest.put("UserPhone", UserPhone);
            jsonRequest.put("UserPassword", UserPassword);

        } catch (Exception e) {
            System.out.println("错误:" + e);
        }
        HttpOperate operate = new HttpOperate("/register", Method.post, jsonRequest.toString());
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
    public String getstate(String responseStr) {
        String  state="0";
        if (!responseStr.equals("Error")) {
            try {
                JSONObject JsonRes = new JSONObject(responseStr);
                state=JsonRes.getString("state");
            } catch (Exception e) {
                System.out.println("json错误:" + e);
            }
        }
        return state;
    }



}

