package com.example.vulcan.okhttp.mainMoudle;

import android.graphics.Bitmap;

import com.example.vulcan.okhttp.Base64Operator;
import com.example.vulcan.okhttp.image.ImageConvert;
import com.example.vulcan.okhttp.okhttp.HttpOperate;
import com.example.vulcan.okhttp.okhttp.Method;
import com.example.vulcan.okhttp.okhttp.RunnableForRequest;

import org.json.JSONObject;

public class ImageTransfer {

    public ImageTransfer(){}

    public Bitmap sendPostAndRecv(Bitmap bitmap,String styleID) {
        //byte[] girlBytes = ImageConvert.bitmapToByteArray(bitmap);
        byte[] imageBytes = ImageConvert.bitmapToByteArray(bitmap);
        String imageStr = Base64Operator.encode(imageBytes);

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("category", "jpg");
            jsonRequest.put("base64", imageStr);
            jsonRequest.put("styleID", styleID);

        } catch (Exception e) {
            System.out.println("错误:" + e);
        }
        HttpOperate operate = new HttpOperate("/test", Method.post, jsonRequest.toString());
        RunnableForRequest runForRequest = new RunnableForRequest(operate);
        Thread requestThread = new Thread(runForRequest);
        requestThread.start();
        //主线程等待线程pt执行结束
        try {
            requestThread.join();
            String responseStr = runForRequest.responseStr;
            return getImage(responseStr);
        } catch (Exception e) {
            return null;
        }
    }

    //从服务端返回的字符串中解析出图片并显示，由public void sendPostAndRecv()调用
    public Bitmap getImage(String responseStr) {
        Bitmap bitmap=null;
        if (!responseStr.equals("Error")) {
            try {
                JSONObject JsonRes = new JSONObject(responseStr);
                byte[] imgByte = Base64Operator.decode(JsonRes.getString("base64"));
                bitmap=ImageConvert.byteArrayToBitmap(imgByte);
            } catch (Exception e) {
                System.out.println("json错误:" + e);
            }
        }
        return bitmap;
    }



}

