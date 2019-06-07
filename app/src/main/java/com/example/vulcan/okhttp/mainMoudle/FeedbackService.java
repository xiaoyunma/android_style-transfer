package com.example.vulcan.okhttp.mainMoudle;

import com.example.vulcan.okhttp.okhttp.HttpOperate;
import com.example.vulcan.okhttp.okhttp.Method;
import com.example.vulcan.okhttp.okhttp.RunnableForRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xiaoyun Ma on 2019/5/13.
 */

public class FeedbackService {
    public FeedbackService(){}

    public String sendPostAndRecv(String FeedbackContent) {


        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("FeedbackContent", FeedbackContent);

        } catch (Exception e) {
            System.out.println("错误:" + e);
        }
        HttpOperate operate = new HttpOperate("/feedback", Method.post, jsonRequest.toString());
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
