package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDefine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrievePasswordActivity extends MyAppCompatActivity implements View.OnClickListener {

    private EditText et_retrieve_password_account;
    private Button btn_retrieve_password_next;

    @SuppressLint("HandlerLeak")
    private Handler RPHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    next(msg.getData());
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(RetrievePasswordActivity.this,"账号不存在",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(RetrievePasswordActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(RetrievePasswordActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_password_layout);
        initComponent();
        initView();
        initEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_retrieve_password_next:
                checkInput();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        et_retrieve_password_account = findViewById(R.id.et_retrieve_password_account);
        btn_retrieve_password_next = findViewById(R.id.btn_retrieve_password_next);
    }

    private void initView(){
        setStatusBarColor(this, ContextCompat.getColor(this,R.color.style));
    }

    private void initEvent(){
        btn_retrieve_password_next.setOnClickListener(this);
    }

    private void next(Bundle bundle){
        Intent intent=new Intent(this,PasswordQuestionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void checkInput(){
        String string=getEditAccount();

        if(string.equals("")){
            Toast.makeText(RetrievePasswordActivity.this,"输入不能为空",Toast.LENGTH_LONG).show();
        }
        else if(!string.contains("@")&&string.length()!=MyDefine.LENGTH_PHONENUMBER){
            Toast.makeText(RetrievePasswordActivity.this,"输入格式有误",Toast.LENGTH_LONG).show();
        }
        else{
            getId(MyBundle.AccountBundle(string));
        }

    }

    private String getEditAccount(){
        return et_retrieve_password_account.getText().toString();
    }

    private void getId(Bundle bundle){

        class MyThread extends Thread{

            private Bundle bundle;

            private MyThread(Bundle bundle){
                this.bundle=bundle;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        String phone_number_or_email_address = bundle.getString("phone_number_or_email_address", "");
                        String mysql_sql;
                        if(phone_number_or_email_address.contains("@")){
                            mysql_sql="call proc_select_id_by_email_address(?)";

                        }
                        else{
                            mysql_sql="call proc_select_id_by_phone_number(?)";
                        }
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setString(1, phone_number_or_email_address);
                        ResultSet rs = preSt.executeQuery();
                        if (rs.next()) {
                            msg.what = MyDefine.REPLY_SUCCESS;
                            Bundle bundle=new Bundle();
                            bundle.putInt("id",rs.getInt("id"));
                            bundle.putString("security_question",rs.getString("security_question"));
                            msg.setData(bundle);
                        } else{
                            msg.what = MyDefine.REPLY_FAILED;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                }
                RPHandler.sendMessage(msg);
            }
        }

        MyThread myThread=new MyThread(bundle);
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
