package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MD5;
import com.example.lostandfound.component.MyAlertDialog;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;

public class PasswordPhoneActivity extends MyAppCompatActivity implements View.OnClickListener {

    private EditText et_information_security_password_phone_old_hint;
    private EditText et_information_security_password_phone_new_hint;
    private Button btn_information_security_password_phone_commit;

    @SuppressLint("HandlerLeak")
    private Handler updatePPHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(PasswordPhoneActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(PasswordPhoneActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(PasswordPhoneActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_phone_layout);
        initComponent();
        initEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_information_security_password_phone_commit:
                UpdatePasswordPhone();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        et_information_security_password_phone_old_hint=findViewById(R.id.et_information_security_password_phone_old_hint);
        et_information_security_password_phone_new_hint=findViewById(R.id.et_information_security_password_phone_new_hint);
        btn_information_security_password_phone_commit=findViewById(R.id.btn_information_security_password_phone_commit);
    }

    private void initEvent(){
        btn_information_security_password_phone_commit.setOnClickListener(this);
    }

    private void UpdatePasswordPhone(){
        String old_password=MD5.md5(et_information_security_password_phone_old_hint.getText().toString());
        String new_phone=et_information_security_password_phone_new_hint.getText().toString();
        int id=((MyApplication)getApplication()).getId();
        Bundle bundle=MyBundle.UpdatePasswordPhoneBundle(id,old_password,new_phone);
        MyDataProcesser.UpdatePasswordPhone(bundle,updatePPHandler);
    }

//    void warningTip(){
//        MyAlertDialog myAlertDialog=new MyAlertDialog(PasswordPhoneActivity.this,0,
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
