package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAlertDialog;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDefine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityChooseActivity extends MyAppCompatActivity implements View.OnClickListener {

    private Button btn_security_choose_phone;
    private Button btn_security_choose_mail;
    private Button btn_security_choose_password;

//    @SuppressLint("HandlerLeak")
//    private Handler securityQuestionHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case MyDefine.REPLY_SUCCESS:
//                    warningTip("你已设置密保，无需再次设置");
//                    break;
//                case MyDefine.REPLY_FAILED:
//                    Intent intent=new Intent(SecurityChooseActivity.this,SecurityQuestionSetActivity.class);
//                    startActivity(intent);
//                    break;
//                case MyDefine.REPLY_UNKNOWN_ERROR:
//                    Toast.makeText(SecurityChooseActivity.this,"未知错误",Toast.LENGTH_LONG).show();
//                    break;
//                case MyDefine.REPLY_NO_RESPONSE:
//                    Toast.makeText(SecurityChooseActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @SuppressLint("HandlerLeak")
    private Handler setByQuestionHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Intent intent=new Intent(SecurityChooseActivity.this,PasswordQuestionActivity.class);
                    intent.putExtras(msg.getData());
                    startActivity(intent);
                    break;
//                case MyDefine.REPLY_FAILED:
//                    warningTip("你还未设置密保");
//                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(SecurityChooseActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(SecurityChooseActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler setByQuestionHandler_2 =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Intent intent=new Intent(SecurityChooseActivity.this,phone_mail_QuestionActivity.class);
                    intent.putExtras(msg.getData());
                    startActivity(intent);
                    break;
//                case MyDefine.REPLY_FAILED:
//                    warningTip("你还未设置密保");
//                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(SecurityChooseActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(SecurityChooseActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_choose_layout);
        initComponent();
        initEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_security_choose_password:
                StartPassword();
                break;
            case R.id.btn_security_choose_mail:
                Start_mail();
                break;
            case R.id.btn_security_choose_phone:
                Start_phone();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        btn_security_choose_phone = findViewById(R.id.btn_security_choose_phone);
        btn_security_choose_mail=findViewById(R.id.btn_security_choose_mail);
        btn_security_choose_password=findViewById(R.id.btn_security_choose_password);
    }

    private void initEvent(){
        btn_security_choose_mail.setOnClickListener(this);
        btn_security_choose_phone.setOnClickListener(this);
        btn_security_choose_password.setOnClickListener(this);
    }

//    private void StartSetQuestion(){
//
//        class MyThread extends Thread{
//
//            private int id;
//
//            private MyThread(int id){
//                this.id=id;
//            }
//
//            @Override
//            public void run() {
//                Message msg=new Message();
//                try {
//                    Connection connection = MyConnectionHelper.getConnection();
//                    if (connection == null) {
//                        msg.what = MyDefine.REPLY_NO_RESPONSE;
//                    } else {
//                        String mysql_sql="call proc_select_security_question(?)";
//                        String sql_server_sql = "exec proc_select_security_question ?";
//                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
//                        preSt.setInt(1,id);
//                        ResultSet rs = preSt.executeQuery();
//                        if (rs.next()) {
//                            msg.what = MyDefine.REPLY_SUCCESS;
//                            Bundle bundle=new Bundle();
//                            bundle.putString("security_question",rs.getString("security_question"));
//                            msg.setData(bundle);
//                        } else{
//                            msg.what = MyDefine.REPLY_FAILED;
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
//                }
//                securityQuestionHandler.sendMessage(msg);
//            }
//        }
//
//        MyThread myThread=new MyThread(((MyApplication)getApplication()).getId());
//        myThread.start();
//        try {
//            myThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private void StartPassword(){
        Intent intent=new Intent(SecurityChooseActivity.this,PasswordPasswordActivity.class);
        startActivity(intent);
    }

    private void Start_phone(){
        Intent intent=new Intent(SecurityChooseActivity.this,PasswordPhoneActivity.class);
        startActivity(intent);
    }

    private void Start_mail(){
        Intent intent=new Intent(SecurityChooseActivity.this,PasswordMailActivity.class);
        startActivity(intent);
    }

//    private void StartQuestion(){
//
//        class MyThread extends Thread{
//
//            private int id;
//
//            private MyThread(int id){
//                this.id=id;
//            }
//
//            @Override
//            public void run() {
//                Message msg=new Message();
//                try {
//                    Connection connection = MyConnectionHelper.getConnection();
//                    if (connection == null) {
//                        msg.what = MyDefine.REPLY_NO_RESPONSE;
//                    } else {
//                        String mysql_sql="call proc_select_security_question(?)";
//                        String sql_server_sql = "exec proc_select_security_question ?";
//                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
//                        preSt.setInt(1,id);
//                        ResultSet rs = preSt.executeQuery();
//                        if (rs.next()) {
//                            msg.what = MyDefine.REPLY_SUCCESS;
//                            Bundle bundle=new Bundle();
//                            bundle.putString("security_question",rs.getString("security_question"));
//                            msg.setData(bundle);
//                        } else{
//                            msg.what = MyDefine.REPLY_FAILED;
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
//                }
//                setByQuestionHandler.sendMessage(msg);
//            }
//        }
//
//        MyThread myThread=new MyThread(((MyApplication)getApplication()).getId());
//        myThread.start();
//        try {
//            myThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void StartQuestion_2() {
//        class MyThread extends Thread{
//
//            private int id;
//
//            private MyThread(int id){
//                this.id=id;
//            }
//            @Override
//            public void run() {
//                Message msg = new Message();
//                try {
//                    Connection connection = MyConnectionHelper.getConnection();
//                    if (connection == null) {
//                        msg.what = MyDefine.REPLY_NO_RESPONSE;
//                    } else {
//                        String mysql_sql = "call proc_select_security_question(?)";
//                        String sql_server_sql = "exec proc_select_security_question ?";
//                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
//                        preSt.setInt(1, id);
//                        ResultSet rs = preSt.executeQuery();
//                        if (rs.next()) {
//                            msg.what = MyDefine.REPLY_SUCCESS;
//                            Bundle bundle=new Bundle();
//                            bundle.putString("security_question",rs.getString("security_question"));
//                            msg.setData(bundle);
//                        } else{
//                            msg.what = MyDefine.REPLY_FAILED;
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
//                }
//                setByQuestionHandler_2.sendMessage(msg);
//            }
//        }
//
//        MyThread myThread=new MyThread(((MyApplication)getApplication()).getId());
//        myThread.start();
//        try {
//            myThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}


