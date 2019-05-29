package com.example.lostandfound.component;

import android.os.Bundle;

public class MyBundle {

    public static Bundle ApplyBundle(int user_id, int main_event_id){
        Bundle bundle=new Bundle();
        bundle.putInt("user_id",user_id);
        bundle.putInt("main_event_id",main_event_id);
        return bundle;
    }

    public static Bundle CommitEndBundle(int user_id,int main_event_id){
        Bundle bundle=new Bundle();
        bundle.putInt("user_id",user_id);
        bundle.putInt("main_event_id",main_event_id);
        return bundle;
    }

    public static Bundle InsertSecurityQuestionBundle(int id, String question, String answer){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        bundle.putString("question",question);
        bundle.putString("answer",answer);
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

    public static Bundle ReportBundle(int user_id, int main_event_id){
        Bundle bundle=new Bundle();
        bundle.putInt("user_id",user_id);
        bundle.putInt("main_event_id",main_event_id);
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

    public static Bundle UpdatephoneQuestionBundle(int id, String security_answer, String new_phone_mail){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        bundle.putString("security_answer",security_answer);
        bundle.putString("new_phone_mail",new_phone_mail);
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
        bundle.putString("user_name",myMessage.getUser_name());
        bundle.putString("location",myMessage.getLocation());
        bundle.putString("time",myMessage.getTime());
        bundle.putString("description",myMessage.getDescription());
        return bundle;
    }

    public static Bundle PictureBundle(int id){
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        return bundle;
    }
}
