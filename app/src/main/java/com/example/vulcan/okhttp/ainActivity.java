package com.example.vulcan.okhttp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.vulcan.okhttp.image.ImageConvert;
import com.example.vulcan.okhttp.okhttp.HttpOperate;
import com.example.vulcan.okhttp.okhttp.Method;
import com.example.vulcan.okhttp.okhttp.RunnableForRequest;

import org.json.JSONObject;

public class ainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }
    public Context context=ainActivity.this;
    //主界面视图控件的声明
    public Button Bt;
    public TextView TextShow;
    public ImageView ImageShow;
    //public ProgressBar ProgBar;
    public EditText Et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ain);

        //主界面视图控件的初始化
        Bt = (Button) findViewById(R.id.Button1);
        TextShow = (TextView) findViewById(R.id.show1);
        ImageShow=(ImageView) findViewById(R.id.image1);
        //ProgBar=(ProgressBar) findViewById(R.id.probar);

        //按钮bt监听
        Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建一个线程执行post请求
                //ProgBar.setVisibility(View.VISIBLE);
                //ImageShow.setImageResource(R.drawable.girl);
                sendPostAndRecv();
                //ProgBar.setVisibility(View.GONE);
            }
        });//bt按钮监听
    }
    //向服务端发送图片并接收返回的图片，由按钮监听方法调用
    public void sendPostAndRecv(){
        Resources resources = context.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.test3);
        byte[] girlBytes=ImageConvert.drawableTobyteArray(drawable);
        String girlStr=Base64Operator.encode(girlBytes);

        JSONObject jsonRequest=new JSONObject();
        try{
            jsonRequest.put("category","jpg");
            jsonRequest.put("base64",girlStr);
            jsonRequest.put("UserID","string");
            jsonRequest.put("UserName","string");
            jsonRequest.put("UserPhone","string");
            jsonRequest.put("UserPassword","string");
        }
        catch(Exception e){
            System.out.println("错误:"+e);
        }
        HttpOperate operate=new HttpOperate("/test", Method.post,jsonRequest.toString());
        RunnableForRequest runForRequest=new RunnableForRequest(operate);
        Thread requestThread=new Thread( runForRequest);
        requestThread.start();
        //主线程等待线程pt执行结束
        try{
            requestThread.join();
            String responseStr=runForRequest.responseStr;
            getImage(responseStr);
        }
        catch(Exception e){}
    }

    //从服务端返回的字符串中解析出图片并显示，由public void sendPostAndRecv()调用
    public void getImage(String responseStr){
        if(!responseStr.equals("Error")){
            try{
                JSONObject JsonRes=new JSONObject(responseStr);
                TextShow.setText(JsonRes.getString("category"));
                byte[] imgByte=Base64Operator.decode(JsonRes.getString("base64"));
                ImageShow.setImageBitmap(ImageConvert.byteArrayToBitmap(imgByte));
            }
            catch(Exception e){
                System.out.println("json错误:"+e);
            }
        }
    }
}
