package com.example.vulcan.okhttp.okhttp;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.nio.channels.ReadPendingException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/*此类用于构造http请求*/
public class HttpOperate {
    public String IpAndPort="http://192.168.43.233:5000";//服务端IP及端口号
    public String route;//服务端路由
    public Method method;//客户端向服务端发送请求的方式post或者get
    public String json;//客户端准备发送给服务端的数据
    public OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    public HttpOperate(String route, Method method,String json){
        this.route=route;
        this.method=method;
        this.json=json;
    }
    //向服务端发送post请求的详细流程
    String postRequest() throws IOException {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String res;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(IpAndPort+route)
                .addHeader("Connection", "close")
                .addHeader("Content-Length",Integer.toString(json.length()))
                .addHeader("Accept","application/json")
                .post(body)

                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("返回头部：",response.headers().toString());
            res = response.body().string();
        } catch (ProtocolException e) {
//            if(e instanceof SocketTimeoutException){
//                System.out.println("套接字超时:"+e);
//            }
//            if(e instanceof ConnectException){
//                System.out.println("连接超时:"+e);
//            }
           Log.d("返回错误",e.toString());
            res="Error";
        }
        return res;
    }
    //向服务端发送get请求的详细流程
    String getRequest() throws IOException {
        return "";
    }
    //根据条件调用postRequest()或者getRequest()，并返回服务端返回给客户端的字符串信息
    String request(){
        String responseStr=null;
        switch(method){
            case post:
                try{
                    responseStr=postRequest();
                }
                catch (Exception e){
                    System.out.println("Post请求错误:\n"+e);
                }
                break;
            case get:
                try{
                    responseStr=getRequest();
                }
                catch (Exception e){
                    System.out.println("Get请求错误:\n"+e);
                }
                break;
            default:
                break;
        }
        return responseStr;
    }
}