package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MD5;
import com.example.lostandfound.component.MyAlertDialog;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;

public class phone_mail_QuestionActivity extends MyAppCompatActivity implements View.OnClickListener {

    private TextView tv_phone_mail_security_question;
    private EditText et_phone_mail_security_question_hint;
    private EditText et_phone_mail_security_question_new_hint;
    private Button btn_phone_mail_security_question_commit;

    @SuppressLint("HandlerLeak")
    private Handler updatePQHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(phone_mail_QuestionActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(phone_mail_QuestionActivity.this,"密保答案错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(phone_mail_QuestionActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonemail_question_layout);
        initComponent();
        initEvent();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_phone_mail_security_question_commit:
                UpdatephoneQuestion();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        tv_phone_mail_security_question=findViewById(R.id.tv_phone_mail_security_question);
        et_phone_mail_security_question_hint=findViewById(R.id.et_phone_mail_security_question_hint);
        et_phone_mail_security_question_new_hint=findViewById(R.id.et_phone_mail_security_question_new_hint);
        btn_phone_mail_security_question_commit=findViewById(R.id.btn_phone_mail_security_question_commit);
    }

    private void initEvent(){
        btn_phone_mail_security_question_commit.setOnClickListener(this);
    }

    private void initData(){
        Bundle bundle=getIntent().getExtras();
        assert bundle != null;
        tv_phone_mail_security_question.setText(bundle.getString("security_question",""));
    }

    private void UpdatephoneQuestion(){
        String security_answer=et_phone_mail_security_question_hint.getText().toString();
        String phone_mail=et_phone_mail_security_question_new_hint.getText().toString();
//        if(!new_password.equals(new_password_repeat)){
//            warningTip();
//            return;
//        }
        int id=((MyApplication)getApplication()).getId();
        Bundle bundle=MyBundle.UpdatephoneQuestionBundle(id,security_answer,phone_mail);
        MyDataProcesser.UpdatephoneQuestion(bundle,updatePQHandler);
    }

//    void warningTip(){
//        MyAlertDialog myAlertDialog=new MyAlertDialog(phone_mail_QuestionActivity.this,0,
//                "提示","两次密码输入不一致！","知道了","",true);
//        myAlertDialog.setOnCertainButtonClickListener(new MyAlertDialog.onMyAlertDialogListener() {
//            public void onCancelButtonClick() {
//
//            }
//            public void onCertainButtonClick() {
//
//            }
//            public void onDismissListener() {
//
//            }
//        });
//        myAlertDialog.show();
//    }
}
