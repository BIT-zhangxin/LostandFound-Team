package com.example.lostandfound.component;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.example.lostandfound.fragment.PublishFragment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MyDataProcesser {
    //所有可以异步处理的网络通信操作

    //对主事件进行申请
    public static void Apply(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int main_event_id=bundle.getInt("main_event_id");
                        int event_type=bundle.getInt("event_type");
                        int origin_user_id=bundle.getInt("origin_user_id");
                        int aim_user_id=bundle.getInt("aim_user_id");
                        String description=bundle.getString("description");
                        String mysql_sql="call proc_apply(?,?,?,?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1, main_event_id);
                        preSt.setInt(2, event_type);
                        preSt.setInt(3, origin_user_id);
                        preSt.setInt(4, aim_user_id);
                        preSt.setString(5, description);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //修改主事件状态为结束
    public static void CommitEnd(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int user_id = bundle.getInt("user_id", 0);
                        int main_event_id = bundle.getInt("main_event_id",0);
                        String mysql_sql="call proc_commit_end(?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1, user_id);
                        preSt.setInt(2, main_event_id);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //登录查询
    public static void Login(Bundle bundle,Handler handler){

        class MyThread extends Thread{

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle,Handler handler){
                this.bundle=bundle;
                this.handler=handler;
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
                        String password = bundle.getString("password", "");
                        String mysql_sql;
                        if(phone_number_or_email_address.contains("@")){
                            mysql_sql="call proc_login_email_address(?,?)";
                        }
                        else{
                            mysql_sql="call proc_login_phone_number(?,?)";
                        }
                        //String sql_server_sql = "exec proc_login_phone_number ?,?";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setString(1,phone_number_or_email_address);
                        preSt.setString(2,password);
                        ResultSet rs = preSt.executeQuery();
                        if (rs.next()) {
                            msg.what = MyDefine.REPLY_SUCCESS;
                            Bundle bundle=new Bundle();
                            bundle.putInt("id",rs.getInt("id"));
                            msg.setData(bundle);
                        } else{
                            msg.what = MyDefine.REPLY_FAILED;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle,handler).start();
    }

    //发布信息
    public static void Publish(File file,Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private File file;
            private Bundle bundle;
            private Handler handler;

            private MyThread(File file,Bundle bundle, Handler handler) {
                this.file = file;
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int user_id=bundle.getInt("user_id",0);
                        int event_type=bundle.getInt("event_type",0);
                        String object_name=bundle.getString("object_name","");
                        String location=bundle.getString("location","");
                        String time=bundle.getString("time","");
                        String description=bundle.getString("description","");
                        String question=bundle.getString("question","");

                        String mysql_sql="call proc_publish(?,?,?,?,?,?,?,?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,user_id);
                        preSt.setInt(2,event_type);
                        preSt.setString(3, object_name);
                        preSt.setString(4, location);
                        preSt.setString(5,time);
                        preSt.setString(6,description);
                        preSt.setString(7,question);

                        if(file==null){
                            preSt.setBlob(8, (Blob) null);
                            preSt.setString(9, null);
                        }
                        else {
                            String[] result=file.getName().split("\\.");//切分文件格式
                            String format=result[1];
                            preSt.setBlob(8,new FileInputStream(file));
                            preSt.setString(9, format);
                        }

                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(file,bundle, handler).start();
    }

    //注册账号
    public static void Register(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int phone_mail_type=bundle.getInt("phone_mail_type",0);
                        String phone_number = bundle.getString("phone_number", "");
                        String password = bundle.getString("password", "");
                        String question =bundle.getString("question","");
                        String answer =bundle.getString("answer","");
                        String mysql_sql;
                        if(phone_mail_type==0)
                            mysql_sql= "call proc_register_phone(?,?,?,?)";
                        else
                            mysql_sql= "call proc_register_mail(?,?,?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setString(1, phone_number);
                        preSt.setString(2, password);
                        preSt.setString(3, question);
                        preSt.setString(4, answer);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //通过密码修改密码
    public static void UpdatePasswordPassword(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String old_password=bundle.getString("old_password", "");
                        String new_password=bundle.getString("new_password", "");
                        String mysql_sql="call proc_update_password_password(?,?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, old_password);
                        preSt.setString(3, new_password);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    public static void UpdatePasswordMail(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String old_password=bundle.getString("old_password", "");
                        String new_mail=bundle.getString("new_mail", "");
                        String mysql_sql="call proc_update_password_mail(?,?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, old_password);
                        preSt.setString(3, new_mail);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    public static void UpdatePasswordPhone(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String old_password=bundle.getString("old_password", "");
                        String new_phone=bundle.getString("new_phone", "");
                        String mysql_sql="call proc_update_password_phone(?,?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, old_password);
                        preSt.setString(3, new_phone);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //通过密保修改密码
    public static void UpdatePasswordQuestion(Bundle bundle,Handler handler) {

        class MyThread extends Thread {

            private Bundle bundle;
            private Handler handler;

            private MyThread(Bundle bundle, Handler handler) {
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String security_answer=bundle.getString("security_answer", "");
                        String new_password=bundle.getString("new_password", "");
                        String mysql_sql="call proc_update_password_question(?,?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, security_answer);
                        preSt.setString(3, new_password);
                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(bundle, handler).start();
    }

    //修改个人信息
    public static void UpdateUseInformation(File file,Bundle bundle, Handler handler) {

        class MyThread extends Thread {

            private File file;
            private Bundle bundle;
            private Handler handler;

            private MyThread(File file,Bundle bundle, Handler handler) {
                this.file = file;
                this.bundle = bundle;
                this.handler = handler;
            }

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int id=bundle.getInt("id",0);
                        String username=bundle.getString("username", "");
                        String contact_information=bundle.getString("contact_information", "");
                        String personal_profile=bundle.getString("personal_profile", "");
                        String mysql_sql="call proc_update_user_information(?,?,?,?,?,?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        preSt.setString(2, username);
                        preSt.setString(3, contact_information);
                        preSt.setString(4, personal_profile);

                        if(file==null){
                            preSt.setBlob(5, (Blob) null);
                            preSt.setString(6, null);
                        }
                        else {
                            String[] result=file.getName().split("\\.");//切分文件格式
                            String format=result[1];
                            preSt.setBlob(5,new FileInputStream(file));
                            preSt.setString(6, format);
                        }

                        preSt.executeUpdate();
                        msg.what = MyDefine.REPLY_SUCCESS;
                        Bundle bundle1=new Bundle();
                        bundle1.putString("absolutePath",file.getAbsolutePath());
                        msg.setData(bundle1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(file,bundle,handler).start();
    }

    //下载头像，形成一个文件存储于本地文件夹
    public static void DownloadProfilePhoto(Activity activity,Handler handler){

        class MyThread extends Thread{

            private Activity activity;
            private Handler handler;

            private MyThread(Activity activity,Handler handler){
                this.activity=activity;
                this.handler=handler;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        int userId=((MyApplication)activity.getApplication()).getId();
                        String mysql_sql="call proc_download_profile_photo(?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1, userId);
                        ResultSet rs = preSt.executeQuery();
                        if (rs.next()) {
                            msg.what = MyDefine.REPLY_SUCCESS;
                            String format=rs.getString("format");
                            Blob blob=rs.getBlob("profile_photo");
                            try {

                                InputStream in = blob.getBinaryStream();
                                String storagePath=Objects.requireNonNull(activity.getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath();
                                File storageDir=new File(storagePath);
                                //noinspection ResultOfMethodCallIgnored
                                storageDir.mkdirs();
                                File file=new File(storageDir,PublishFragment.getStringToday()+"."+format);
                                String absolutePath=file.getAbsolutePath();
                                OutputStream out = new FileOutputStream(file);
                                byte [] buff = new byte[1024];
                                int len;
                                while((len = in.read(buff)) > 0){
                                    out.write(buff, 0, len);
                                }
                                Bundle bundle=new Bundle();
                                bundle.putString("absolutePath",absolutePath);
                                msg.setData(bundle);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else{
                            msg.what = MyDefine.REPLY_FAILED;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(activity,handler).start();
    }

    //下载图片，形成一个文件存储于本地文件夹
    public static void DownloadObjectPicture(Activity activity,int objectId,Handler handler){

        class MyThread extends Thread{

            private Activity activity;
            private int objectId;
            private Handler handler;

            private MyThread(Activity activity,int objectId,Handler handler){
                this.activity=activity;
                this.objectId=objectId;
                this.handler=handler;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        String mysql_sql="call proc_download_object_picture(?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1, objectId);
                        ResultSet rs = preSt.executeQuery();
                        if (rs.next()) {

                            String format=rs.getString("picture_format");
                            Blob blob=rs.getBlob("picture");
                            String absolutePath;
                            try {

                                InputStream in = blob.getBinaryStream();
                                String storagePath=Objects.requireNonNull(activity.getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath();
                                File storageDir=new File(storagePath);
                                //noinspection ResultOfMethodCallIgnored
                                storageDir.mkdirs();
                                File file=new File(storageDir,PublishFragment.getStringToday()+"."+format);
                                absolutePath=file.getAbsolutePath();
                                OutputStream out = new FileOutputStream(file);
                                byte [] buff = new byte[1024];
                                int len;
                                while((len = in.read(buff)) > 0){
                                    out.write(buff, 0, len);
                                }
                                Bundle bundle=new Bundle();
                                bundle.putString("absolutePath",absolutePath);
                                msg.setData(bundle);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            msg.what = MyDefine.REPLY_SUCCESS;
                        } else{
                            msg.what = MyDefine.REPLY_FAILED;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_FAILED;
                }
                handler.sendMessage(msg);
            }
        }
        new MyThread(activity,objectId,handler).start();
    }

}
