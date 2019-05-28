package com.example.lostandfound.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.activity.ObjectActivity;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyConnectionHelper;
import com.example.lostandfound.component.MyDefine;
import com.example.lostandfound.component.MyMessage;
import com.example.lostandfound.component.MyMessageAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private Context mContext;

    private List<MyMessage> myMessageList=new ArrayList<>();
    private ListView listView;

    RefreshLayout refreshLayout;

    @SuppressLint("HandlerLeak")
    private Handler messageHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(mContext,"未知错误",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(mContext,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=getActivity();
        //TODO:@彭青峰，条件接口
        initData("");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.message_layout,container,false);
        initComponent(view);
        initView();
        initEvent();
        return view;
    }

    void initComponent(View view){
        listView=view.findViewById(R.id.list_view_message);
        refreshLayout=view.findViewById(R.id.refreshLayout);
    }

    void initData(String condition){


        class MyThread extends Thread{

            private String condition;

            private MyThread(String condition){
                this.condition=condition;
            }

            @Override
            public void run() {
                Message msg=new Message();
                try {
                    Connection connection = MyConnectionHelper.getConnection();
                    if (connection == null) {
                        msg.what = MyDefine.REPLY_NO_RESPONSE;
                    } else {
                        String condition_sql="%"+condition+"%";
                        String mysql_sql="call proc_select_message(?)";
                        //String sql_server_sql = "exec proc_select_message";
                        PreparedStatement preSt = connection.prepareStatement(mysql_sql);
                        preSt.setString(1,condition_sql);
                        ResultSet rs = preSt.executeQuery();
                        while(rs.next()){
                            MyMessage myMessage=new MyMessage();
                            myMessage.setMain_event_id(rs.getInt("main_event_id"));
                            myMessage.setMain_event_type(rs.getInt("main_event_type"));
                            myMessage.setUser_id(rs.getInt("user_id"));
                            myMessage.setUser_name(rs.getString("user_name"));
                            myMessage.setObject_id(rs.getInt("object_id"));
                            myMessage.setQuestion(rs.getString("question"));
                            myMessage.setDate(rs.getDate("date"));
                            myMessage.setName(rs.getString("name"));
                            myMessage.setTime(rs.getString("time"));
                            myMessage.setLocation(rs.getString("location"));
                            myMessage.setDescription(rs.getString("description"));
                            myMessage.setPicture(rs.getBlob("picture"));
                            myMessage.setFormat(rs.getString("format"));
                            myMessageList.add(myMessage);
                        }
                        msg.what=MyDefine.REPLY_SUCCESS;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    msg.what = MyDefine.REPLY_UNKNOWN_ERROR;
                }
                messageHandler.sendMessage(msg);
            }
        }

        MyThread myThread=new MyThread(condition);
        myMessageList.clear();
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void initView(){
        MyMessageAdapter myMessageAdapter=new MyMessageAdapter(getActivity(),R.layout.message_item,myMessageList);
        listView.setAdapter(myMessageAdapter);
    }

    void initEvent(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),ObjectActivity.class);
                intent.putExtras(MyBundle.ObjectBundle(myMessageList.get(position)));
                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData("");
                refreshlayout.finishRefresh(200/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                initData("");
                refreshlayout.finishLoadMore(200/*,false*/);//传入false表示加载失败
            }
        });

    }
}
