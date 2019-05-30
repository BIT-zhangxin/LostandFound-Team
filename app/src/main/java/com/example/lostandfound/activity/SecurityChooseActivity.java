package com.example.lostandfound.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;

public class SecurityChooseActivity extends MyAppCompatActivity implements View.OnClickListener {

    private Button btn_security_choose_phone;
    private Button btn_security_choose_mail;
    private Button btn_security_choose_password;

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


