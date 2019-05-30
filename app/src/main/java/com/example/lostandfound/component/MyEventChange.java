package com.example.lostandfound.component;

public class MyEventChange {

    public static String MainEventToString(int main_event_type){
        switch (main_event_type){
            case 1:
                return "失物事件";
            case 2:
                return "拾物事件";
            case 11:
                return "结束的失物事件";
            case 12:
                return "结束的拾物事件";
            default:
                return "";
        }
    }

    public static String SubEventToString(int sub_event_type){
        switch (sub_event_type){
            case 1:
                return "失物事件申请";
            case 2:
                return "失物事件申请拒绝";
            case 3:
                return "失物事件申请同意";
            case 6:
                return "拾物事件申请";
            case 7:
                return "拾物事件申请拒绝";
            case 8:
                return "拾物事件申请同意";
            default:
                return "";
        }
    }
}
