package com.example.vulcan.okhttp.mainMoudle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vulcan.okhttp.R;
import com.example.vulcan.okhttp.RegisterActivity;

import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    //声明变量
    private EditText mEtFeedback;
    private Button mBtnFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        //引用变量
        mEtFeedback = (EditText) findViewById(R.id.et_feedback);
        mBtnFeedback = (Button)findViewById(R.id.btn_feedback);
       // 单击提交按钮提交内容事件
        mBtnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取内容
                String feedbackcontent = mEtFeedback.getText().toString();
                if (feedbackcontent.toString().equals("")){
                    Toast.makeText(FeedbackActivity.this,"反馈内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    final FeedbackService feedbackService = new FeedbackService();
                    String state  = feedbackService.sendPostAndRecv(mEtFeedback.getText().toString());
                    if(state.equals("1")){
                        Toast.makeText(getApplication(), "提交成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplication(), "提交失败", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
