package com.example.vulcan.okhttp.okhttp;

import org.json.JSONObject;

/*此类用于构造进行http请求的线程*/
public class RunnableForRequest implements Runnable{
    public HttpOperate operate;
    public String responseStr="";
    public String category="";
    public String base64="";
    public String styleID="";
    //登录注册功能
    public String UserID="";
    public String UserName="";
    public String UserPhone="";
    public String UserPassword="";


    public RunnableForRequest(HttpOperate operate){
        this.operate=operate;

    }
    public void run(){
        responseStr=operate.request();
    }
}
