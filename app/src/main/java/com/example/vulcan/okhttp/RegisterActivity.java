package com.example.vulcan.okhttp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.vulcan.okhttp.R;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vulcan.okhttp.mainMoudle.LoginActivity;
import com.example.vulcan.okhttp.mainMoudle.RegisterService;

public class RegisterActivity extends AppCompatActivity {
    public Button btn_submit;
    public EditText username;
    public EditText userphone;
    public EditText userpassword1;
    public EditText userpassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_submit = (Button) findViewById(R.id.btn_register);
        username = (EditText) findViewById(R.id.rg_username);
        userphone = (EditText) findViewById(R.id.rg_userphone);
        userpassword1 = (EditText) findViewById(R.id.rg_userpassword1);
        userpassword2 = (EditText) findViewById(R.id.rg_userpassword2);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("")) {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("提示")//标题
                            .setMessage("用户名不能为空！")//内容
                            .setIcon(R.mipmap.ic_launcher)//图标
                            .create();
                    alertDialog1.show();
                } else if (userphone.getText().toString().equals("")) {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("提示")//标题
                            .setMessage("手机不能为空！")//内容
                            .setIcon(R.mipmap.ic_launcher)//图标
                            .create();
                    alertDialog1.show();

                } else if (userpassword1.getText().toString().equals("") || userpassword2.getText().toString().equals("")) {    //判断两次密码是否为空
                    AlertDialog alertDialog1 = new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("提示")//标题
                            .setMessage("两次输入密码不能为空！")//内容
                            .setIcon(R.mipmap.ic_launcher)//图标
                            .create();
                    alertDialog1.show();
                } else if (userpassword1.getText().toString().equals("") != userpassword2.getText().toString().equals("")) {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("提示")//标题
                            .setMessage("两次输入密码不一致！")//内容
                            .setIcon(R.mipmap.ic_launcher)//图标
                            .create();
                    alertDialog1.show();
                } else if (userpassword1.getText().toString().equals(userpassword2.getText().toString())) {
                    final RegisterService registerService = new RegisterService();
                    String state = registerService.sendPostAndRecv(username.getText().toString(), userphone.getText().toString(),
                            userpassword1.getText().toString());
                    if(state.equals("1")){
                        Toast.makeText(getApplication(), "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplication(), "注册失败", Toast.LENGTH_SHORT).show();
                    }



                }


            }


        });
    }
}
