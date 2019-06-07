package com.example.vulcan.okhttp.mainMoudle;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vulcan.okhttp.R;
import com.example.vulcan.okhttp.RegisterActivity;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtUserPhone;
    private EditText mEtUserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnLogin =(Button) findViewById(R.id.btn_login);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mEtUserPassword = (EditText) findViewById(R.id.et_userpassword);
        mEtUserPhone = (EditText) findViewById(R.id.et_userphone);
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

        mEtUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("edittext",s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
@Override
    public void onClick(View v){
        String UserPhone = mEtUserPhone.getText().toString();
        String UserPassword = mEtUserPassword.getText().toString();
        switch (v.getId())
        {
            case R.id.btn_login:
                if(UserPhone.equals("") || UserPassword.equals(""))
            {
                AlertDialog alertDialog1 = new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("提示")//标题
                        .setMessage("账号密码不能为空！")//内容
                        .setIcon(R.mipmap.ic_launcher)//图标
                        .create();
                        alertDialog1.show();

            }else {
                    final LoginService loginService = new LoginService();
                    Map returnValue = loginService.sendPostAndRecv(mEtUserPhone.getText().toString(), mEtUserPassword.getText().toString());
                    if(returnValue.get("failed").equals(1)){
                        Toast.makeText(getApplication(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if ((returnValue.get("IsOnline").equals("0")))
                            Toast.makeText(getApplication(), "登陆成功", Toast.LENGTH_SHORT).show();
                        else{
                            Toast.makeText(getApplication(), "此账号已经登录", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

            case R.id.btn_register: //跳转到注册页面
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;

        }


}

}
