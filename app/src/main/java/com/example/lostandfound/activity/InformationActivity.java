package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDefine;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class InformationActivity extends MyAppCompatActivity {

    MyApplication myApplication;

    private TextView tv_info_info_username;
    private TextView tv_info_info_phone_number;
    private TextView tv_info_info_email_address;
    private TextView tv_info_info_contact_information;
    private TextView tv_info_info_introduction;
    private TextView tv_info_info_credit_score;

    private ImageView iv_info_info_photo;

    @SuppressLint("HandlerLeak")
    private Handler informationHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(InformationActivity.this,"系统出现故障",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(InformationActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(InformationActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_information_layout);
        initComponent();
        initData();
        initView();
    }

    private void initComponent(){
        tv_info_info_username = findViewById(R.id.tv_info_info_username);
        tv_info_info_phone_number = findViewById(R.id.tv_info_info_phone_number);
        tv_info_info_email_address = findViewById(R.id.tv_info_info_email_address);
        tv_info_info_contact_information = findViewById(R.id.tv_info_info_contact_information);
        tv_info_info_introduction = findViewById(R.id.tv_info_info_introduction);
        tv_info_info_credit_score = findViewById(R.id.tv_info_info_credit_score);
        iv_info_info_photo = findViewById(R.id.iv_info_info_photo);
        myApplication = (MyApplication)getApplication();
    }

    private void initData(){

        class MyThread extends Thread{

            private MyApplication myApplication;

            private MyThread(MyApplication myApplication){
                this.myApplication=myApplication;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=myApplication.getId();
                        String mysql_sql="call proc_select_userinfo(?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        ResultSet rs = preSt.executeQuery();
                        if (rs.next()) {
                            msg.what = MyDefine.REPLY_SUCCESS;
                            myApplication.setUsername(rs.getString("username"));
                            myApplication.setPhone_number(rs.getString("phone_number"));
                            myApplication.setEmail_address(rs.getString("email_address"));
                            myApplication.setContact_information(rs.getString("contact_information"));
                            myApplication.setIntroduction(rs.getString("introduction"));
                            myApplication.setCredit_score(rs.getInt("credit_score"));
                        } else{
                            msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                }
                informationHandler.sendMessage(msg);
            }
        }

        MyThread myThread=new MyThread(myApplication);
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initView(){
        tv_info_info_username.setText(myApplication.getUsername());
        tv_info_info_phone_number.setText(myApplication.getPhone_number());
        tv_info_info_email_address.setText(myApplication.getEmail_address());
        tv_info_info_contact_information.setText(myApplication.getContact_information());
        tv_info_info_introduction.setText(myApplication.getIntroduction());
        tv_info_info_credit_score.setText(""+myApplication.getCredit_score());
        LoadProfilePhoto();
    }

    private void LoadProfilePhoto(){
        String photo_path=((MyApplication)this.getApplication()).getPhoto_path();
        if(photo_path==null){
            return;
        }
        File file=new File(photo_path);
        Uri uri=getUriForFile(this,file);
        Glide.with(this).load(uri).into(iv_info_info_photo);
    }

    //获取文件uri(android7.0后必须使用FileProvider)
    private Uri getUriForFile(Context context,File file){
        Uri uri;
        if(Build.VERSION.SDK_INT>=24){
            uri=FileProvider.getUriForFile(context,"com.example.lostandfound.fileprovider",file);
        }
        else{
            uri=Uri.fromFile(file);
        }
        return uri;
    }
}
