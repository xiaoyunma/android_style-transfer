package com.example.vulcan.okhttp.okhttp;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class OkHttpOperate {
    public static OkHttpClient client=new OkHttpClient();
    public Request request;
    public Call call;
    public String responseStr;
    public boolean flag;
    String IpAndPort="http://192.168.1.26:5000";
    public OkHttpOperate(){
        flag=false;
    }
    public void createPostRequest(String route,String postStr){
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,postStr);
        request = new Request.Builder()
                .url(IpAndPort+route)
                .post(body)
                .build();
    }
//    public void createGetRequest(){
//        request = new Request.Builder()
//                .url("http://123.57.31.11/androidnet/getJoke?id=7")
//                .get()
//                .build();
//    }
    public void run(){
        call = client.newCall(request);

       call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               flag=false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try{
                    responseStr = response.body().string();
                    flag=true;
                }
                catch(Exception e){
                    System.out.println("错误："+e);
                }

            }
        });
    }
}
