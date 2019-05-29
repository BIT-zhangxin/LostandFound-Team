package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrievePassword extends MyAppCompatActivity implements View.OnClickListener {
    private TextView tv_retrieve_password_title;
    private EditText et_retrieve_password_account;
    private Button btn_retrieve_password_next;

    @SuppressLint("HandlerLeak")
    private Handler RPHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    setUserId(msg.getData());
                    Intent intent=new Intent(RetrievePassword.this,PasswordQuestionActivity.class);
                    Bundle bundle_2=new Bundle();
                    bundle_2.putString("security_question",msg.getData().getString("security_question",""));
                    Message msg_2=new Message();
                    msg_2.setData(bundle_2);
                    intent.putExtras(msg_2.getData());
                    startActivity(intent);
                    break;
                case MyDefine.REPLY_FAILED:
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(RetrievePassword.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(RetrievePassword.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    private String getEditAccount(){
        return et_retrieve_password_account.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_retrieve_password_next:
                Bundle bundle= MyBundle.AccountBundle(getEditAccount());
                GetID(bundle);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_password);
        initComponent();
        initEvent();
    }

    private void initEvent(){
        btn_retrieve_password_next.setOnClickListener(this);
    }

    private void initComponent(){
        tv_retrieve_password_title = findViewById(R.id.tv_retrieve_password_title);
        et_retrieve_password_account = findViewById(R.id.et_retrieve_password_account);
        btn_retrieve_password_next = findViewById(R.id.btn_retrieve_password_next);
    }

    private void setUserId(Bundle bundle){
        MyApplication myApplication=(MyApplication)getApplication();
        myApplication.setId(bundle.getInt("id",0));
    }

    private void GetID(Bundle bundle){

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
                        String phone_number = bundle.getString("id_or_phone_number", "");
                        int type=0;
                        if(phone_number.contains("@"))
                            type=1;
                        else
                            type=0;
                        String mysql_sql=null;
                        if(type==0)
                            mysql_sql="call proc_phone_getID(?)";
                        else if(type==1)
                            mysql_sql="call proc_mail_getID(?)";
//                        String sql_server_sql = "exec proc_getID ?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setString(1, phone_number);
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
