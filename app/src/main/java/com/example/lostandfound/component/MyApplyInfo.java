package com.example.lostandfound.component;

import java.sql.Timestamp;

public class MyApplyInfo {

    private int sub_event_id;//子事件id

    private int main_event_id;//主事件id

    private int object_name;//物品名称

    private int sub_event_type;//子事件类型

    private int origin_user_id;//起始用户id

    private String origin_user_name;//起始用户名称

    private int aim_user_id;//目标用户id

    private String aim_user_name;//目标用户名称

    private String description;//描述

    private Timestamp time;//子事件发生时间

    private String contact_information;//拾物事件失主联系方式

    public int getSub_event_id() {
        return sub_event_id;
    }

    public void setSub_event_id(int sub_event_id) {
        this.sub_event_id = sub_event_id;
    }

    public int getMain_event_id() {
        return main_event_id;
    }

    public void setMain_event_id(int main_event_id) {
        this.main_event_id = main_event_id;
    }

    public int getObject_name() {
        return object_name;
    }

    public void setObject_name(int object_name) {
        this.object_name = object_name;
    }

    public int getSub_event_type() {
        return sub_event_type;
    }

    public void setSub_event_type(int sub_event_type) {
        this.sub_event_type = sub_event_type;
    }

    public int getOrigin_user_id() {
        return origin_user_id;
    }

    public void setOrigin_user_id(int origin_user_id) {
        this.origin_user_id = origin_user_id;
    }

    public String getOrigin_user_name() {
        return origin_user_name;
    }

    public void setOrigin_user_name(String origin_user_name) {
        this.origin_user_name = origin_user_name;
    }

    public int getAim_user_id() {
        return aim_user_id;
    }

    public void setAim_user_id(int aim_user_id) {
        this.aim_user_id = aim_user_id;
    }

    public String getAim_user_name() {
        return aim_user_name;
    }

    public void setAim_user_name(String aim_user_name) {
        this.aim_user_name = aim_user_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContact_information() {
        return contact_information;
    }

    public void setContact_information(String contact_information) {
        this.contact_information = contact_information;
    }
}
