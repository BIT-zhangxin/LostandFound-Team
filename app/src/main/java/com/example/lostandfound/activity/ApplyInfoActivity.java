package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;
import com.example.lostandfound.component.MyEventChange;

public class ApplyInfoActivity extends MyAppCompatActivity implements View.OnClickListener {

    private TextView tv_apply_info_name;
    private TextView tv_apply_info_event_id;
    private TextView tv_apply_info_event_type;
    private TextView tv_apply_info_user_name;
    private TextView tv_apply_info_time;
    private TextView tv_apply_info_description;
    private TextView tv_apply_info_contact_information;
    private Button btn_apply_info_reject;
    private Button btn_apply_info_apply;

    private LinearLayout ll_apply_info_contact_information;

    private Bundle bundle;//传入的数据

    @SuppressLint("HandlerLeak")
    private Handler applyInfoHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(ApplyInfoActivity.this,"操作成功",Toast.LENGTH_LONG).show();
                    setResult(1);
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(ApplyInfoActivity.this,"系统出现故障",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(ApplyInfoActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(ApplyInfoActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_info_layout);
        bundle=getIntent().getExtras();
        initComponent();
        initView();
        initEvent();
        initData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_apply_info_reject:
                reject();
                break;
            case R.id.btn_apply_info_apply:
                pass();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        tv_apply_info_name=findViewById(R.id.tv_apply_info_name);
        tv_apply_info_event_id=findViewById(R.id.tv_apply_info_event_id);
        tv_apply_info_event_type=findViewById(R.id.tv_apply_info_event_type);
        tv_apply_info_user_name = findViewById(R.id.tv_apply_info_user_name);
        tv_apply_info_time = findViewById(R.id.tv_apply_info_time);
        tv_apply_info_description = findViewById(R.id.tv_apply_info_description);
        tv_apply_info_contact_information = findViewById(R.id.tv_apply_info_contact_information);
        btn_apply_info_reject = findViewById(R.id.btn_apply_info_reject);
        btn_apply_info_apply = findViewById(R.id.btn_apply_info_apply);
        ll_apply_info_contact_information = findViewById(R.id.ll_apply_info_contact_information);
    }

    private void initView(){
        int sub_event_type=bundle.getInt("sub_event_type");
        if(isOriginUser()||(sub_event_type==2||sub_event_type==7||sub_event_type==3)){
            ll_apply_info_contact_information.setVisibility(View.INVISIBLE);
            btn_apply_info_reject.setVisibility(View.INVISIBLE);
            btn_apply_info_apply.setVisibility(View.INVISIBLE);
        }
        else if(sub_event_type==1||sub_event_type==6){
            ll_apply_info_contact_information.setVisibility(View.INVISIBLE);
        }
        else if(sub_event_type==8){
            btn_apply_info_reject.setVisibility(View.INVISIBLE);
            btn_apply_info_apply.setVisibility(View.INVISIBLE);
        }
        setStatusBarColor(this, ContextCompat.getColor(this,R.color.style));
    }

    private void initEvent(){
        btn_apply_info_reject.setOnClickListener(this);
        btn_apply_info_apply.setOnClickListener(this);
    }

    private void initData(){
        bundle = getIntent().getExtras();
        assert bundle != null;
        tv_apply_info_name.setText(bundle.getString("object_name"));
        tv_apply_info_event_id.setText(String.valueOf(bundle.getInt("main_event_id")));
        tv_apply_info_event_type.setText(MyEventChange.SubEventToString(bundle.getInt("sub_event_type")));

        if(isOriginUser()){
            tv_apply_info_user_name.setText(bundle.getString("aim_user_name"));
        }
        else {
            tv_apply_info_user_name.setText(bundle.getString("origin_user_name"));
        }
        tv_apply_info_time.setText(bundle.getString("time"));
        tv_apply_info_description.setText(bundle.getString("description"));
        tv_apply_info_contact_information.setText(bundle.getString("contact_information"));
    }

    private boolean isOriginUser(){
        int id=((MyApplication)getApplication()).getId();
        return id == bundle.getInt("origin_user_id");
    }

    private void reject(){
        int sub_event_id=bundle.getInt("sub_event_id");
        int event_type=bundle.getInt("sub_event_type");
        if(event_type==1){
            event_type=2;
        }
        else {
            event_type=7;
        }
        Bundle bundle= MyBundle.ReplyBundle(sub_event_id,event_type);
        MyDataProcesser.Reply(bundle,applyInfoHandler);
    }

    private void pass(){
        int sub_event_id=bundle.getInt("sub_event_id");
        int event_type=bundle.getInt("sub_event_type");
        if(event_type==1){
            event_type=3;
        }
        else {
            event_type=8;
        }
        Bundle bundle= MyBundle.ReplyBundle(sub_event_id,event_type);
        MyDataProcesser.Reply(bundle,applyInfoHandler);
    }
}
