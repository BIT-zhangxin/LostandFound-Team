package com.example.lostandfound.component;

import android.annotation.SuppressLint;
import android.os.Bundle;
import java.text.SimpleDateFormat;

public class MyBundle {

    public static Bundle ApplyInfoBundle(MyApplyInfo myApplyInfo){
        Bundle bundle=new Bundle();
        bundle.putInt("sub_event_id",myApplyInfo.getSub_event_id());
        bundle.putInt("main_event_id",myApplyInfo.getMain_event_id());
        bundle.putString("object_name",myApplyInfo.getObject_name());
        bundle.putInt("sub_event_type",myApplyInfo.getSub_event_type());
        bundle.putInt("origin_user_id",myApplyInfo.getOrigin_user_id());
        bundle.putString("origin_user_name",myApplyInfo.getOrigin_user_name());
        bundle.putInt("aim_user_id",myApplyInfo.getAim_user_id());
        bundle.putString("aim_user_name",myApplyInfo.getAim_user_name());
        bundle.putString("description",myApplyInfo.getDescription());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式可以自己根据需要修改
        String dateString = simpleDateFormat.format(myApplyInfo.getTime());

        bundle.putString("time",dateString);
        bundle.putString("contact_information",myApplyInfo.getContact_information());
        return bundle;
    }

    public static Bundle ApplyBundle(int origin_user_id,String answer,Bundle oldBundle){
        Bundle bundle=new Bundle();
        bundle.putInt("main_event_id",oldBundle.getInt("main_event_id"));
        if(oldBundle.getInt("main_event_type")==1){
            bundle.putInt("event_type",1);//失物事件
        }
        else {
            bundle.putInt("event_type",6);//拾物事件
        }
        bundle.putInt("origin_user_id",origin_user_id);
        bundle.putInt("aim_user_id",oldBundle.getInt("user_id"));
        bundle.putString("description",answer);
        return bundle;
    }

    public static Bundle LoginBundle(String account, String password){
        Bundle bundle=new Bundle();
        bundle.putString("phone_number_or_email_address",account);
        bundle.putString("password",password);
        return bundle;
    }

    public static Bundle PublishBundle(int user_id, int event_type, String object_name, String location, String time, String description,String question){
        Bundle bundle=new Bundle();
        bundle.putInt("user_id",user_id);
        bundle.putInt("event_type",event_type);
        bundle.putString("object_name",object_name);
        bundle.putString("location",location);
        bundle.putString("time",time);
        bundle.putString("description",description);
        bundle.putString("question",question);
        return bundle;
    }

    public static Bundle RegisterBundle(int phone_mail_type,String phone_number, String password,String question, String answer){
        Bundle bundle=new Bundle();
        bundle.putInt("phone_mail_type",phone_mail_type);
        bundle.putString("phone_number",phone_number);
        bundle.putString("password",password);
        bundle.putString("question",question);
        bundle.putString("answer",answer);
        return bundle;
    }

    public static Bundle UpdatePasswordPasswordBundle(int id, String old_password, String new_password){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        bundle.putString("old_password",old_password);
        bundle.putString("new_password",new_password);
        return bundle;
    }

    public static Bundle UpdatePasswordMailBundle(int id, String old_password, String new_mail){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        bundle.putString("old_password",old_password);
        bundle.putString("new_mail",new_mail);
        return bundle;
    }

    public static Bundle UpdatePasswordPhoneBundle(int id, String old_password, String new_phone){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        bundle.putString("old_password",old_password);
        bundle.putString("new_phone",new_phone);
        return bundle;
    }

    public static Bundle UpdatePasswordQuestionBundle(int id, String security_answer, String new_password){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        bundle.putString("security_answer",security_answer);
        bundle.putString("new_password",new_password);
        return bundle;
    }

    public static Bundle AccountBundle(String account){
        Bundle bundle=new Bundle();
        bundle.putString("phone_number_or_email_address",account);
        return bundle;
    }

    public static Bundle UpdateUserInformationBundle(int id, String username, String contact_information,String personal_profile){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        bundle.putString("username",username);
        bundle.putString("contact_information",contact_information);
        bundle.putString("personal_profile",personal_profile);
        return bundle;
    }

    public static Bundle ObjectBundle(MyMessage myMessage){
        Bundle bundle=new Bundle();

        bundle.putInt("main_event_id",myMessage.getMain_event_id());
        bundle.putInt("main_event_type",myMessage.getMain_event_type());
        bundle.putInt("object_id",myMessage.getObject_id());
        bundle.putString("name",myMessage.getName());
        bundle.putInt("user_id",myMessage.getUser_id());
        bundle.putString("user_name",myMessage.getUser_name());
        bundle.putString("location",myMessage.getLocation());
        bundle.putString("time",myMessage.getTime());
        bundle.putString("description",myMessage.getDescription());
        bundle.putString("question",myMessage.getQuestion());
        return bundle;
    }
}
