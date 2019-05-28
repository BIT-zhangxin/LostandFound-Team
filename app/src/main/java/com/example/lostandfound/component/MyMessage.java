package com.example.lostandfound.component;

import java.sql.Blob;
import java.util.Date;

public class MyMessage {

    private int main_event_id;//事件id

    private int main_event_type;//事件类型

    private int user_id;//用户id

    private String user_name;//用户名

    private int object_id;//物品id

    private String question;//问题

    private Date date;//事件发生时间

    private String name;//物品名称

    private String time;//物品描述事件

    private String location;//位置

    private String description;//描述

    private Blob picture;//图片

    private String format;//图片类型

    public int getMain_event_id() {
        return main_event_id;
    }

    public void setMain_event_id(int main_event_id) {
        this.main_event_id = main_event_id;
    }

    public int getMain_event_type() {
        return main_event_type;
    }

    public void setMain_event_type(int main_event_type) {
        this.main_event_type = main_event_type;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
