package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyApplyInfo;
import com.example.lostandfound.component.MyApplyInfoAdapter;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDefine;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplyRecordActivity extends MyAppCompatActivity{

    private List<MyApplyInfo> myApplyInfoList=new ArrayList<>();
    private ListView list_view_apply_info;
    RefreshLayout refreshLayout_user_apply_info;

    @SuppressLint("HandlerLeak")
    private Handler informationHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(ApplyRecordActivity.this,"系统出现故障",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(ApplyRecordActivity.this,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(ApplyRecordActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_record_layout);
        initComponent();
        initEvent();
        initData();
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            refreshLayout_user_apply_info.autoRefresh();
        }
    }

    void initComponent(){
        list_view_apply_info=findViewById(R.id.list_view_apply_info);
        refreshLayout_user_apply_info=findViewById(R.id.refreshLayout_user_apply_info);
    }

    private void initData(){
        myApplyInfoList.clear();
        int id=((MyApplication)getApplication()).getId();

        class MyThread extends Thread{

            private int id;

            private MyThread(int id){
                this.id=id;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        String mysql_sql="call proc_select_apply_info(?)";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setInt(1,id);
                        ResultSet rs = preSt.executeQuery();
                        while(rs.next()){
                            MyApplyInfo myApplyInfo=new MyApplyInfo();
                            myApplyInfo.setSub_event_id(rs.getInt("sub_event_id"));
                            myApplyInfo.setMain_event_id(rs.getInt("main_event_id"));
                            myApplyInfo.setObject_name(rs.getString("object_name"));
                            myApplyInfo.setSub_event_type(rs.getInt("sub_event_type"));
                            myApplyInfo.setOrigin_user_id(rs.getInt("origin_user_id"));
                            myApplyInfo.setOrigin_user_name(rs.getString("origin_user_name"));
                            myApplyInfo.setAim_user_id(rs.getInt("aim_user_id"));
                            myApplyInfo.setAim_user_name(rs.getString("aim_user_name"));
                            myApplyInfo.setDescription(rs.getString("description"));
                            myApplyInfo.setTime(rs.getTimestamp("time"));
                            myApplyInfo.setContact_information(rs.getString("contact_information"));
                            myApplyInfoList.add(myApplyInfo);
                        }
                        msg.what=MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                }
                informationHandler.sendMessage(msg);
            }
        }

        MyThread myThread=new MyThread(id);
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void initView(){
        MyApplyInfoAdapter myApplyInfoAdapter=new MyApplyInfoAdapter(ApplyRecordActivity.this,R.layout.apply_info_item,myApplyInfoList);
        list_view_apply_info.setAdapter(myApplyInfoAdapter);
    }

    void initEvent(){

        list_view_apply_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ApplyRecordActivity.this,ApplyInfoActivity.class);
                intent.putExtras(MyBundle.ApplyInfoBundle(myApplyInfoList.get(position)));
                startActivityForResult(intent,1);
            }
        });

        refreshLayout_user_apply_info.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
                initView();
                refreshlayout.finishRefresh(200/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout_user_apply_info.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                initData();
                initView();
                refreshlayout.finishLoadMore(200/*,false*/);//传入false表示加载失败
            }
        });

    }
}
