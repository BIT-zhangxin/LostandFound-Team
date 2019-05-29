package com.example.lostandfound.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;
import com.example.lostandfound.component.MyEventChange;
import java.io.File;

public class ObjectActivity extends MyAppCompatActivity implements View.OnClickListener {

    private ImageView iv_object_picture;

    private TextView tv_object_name;
    private TextView tv_object_user_name;
    private TextView tv_object_main_event_type;
    private TextView tv_object_location;
    private TextView tv_object_id;
    private TextView tv_object_time;
    private TextView tv_object_description;
    private Button btn_object_apply;

    private int objectId;

    @SuppressLint("HandlerLeak")
    private Handler ObjectPictureHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Bundle bundle=msg.getData();
                    String absolutePath=bundle.getString("absolutePath");

                    LoadProfilePhoto(absolutePath);
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(ObjectActivity.this,"申请失败,重复申请",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(ObjectActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler ApplyHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(ObjectActivity.this,"申请成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(ObjectActivity.this,"不能申请自己发布的事件",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_UNKNOWN_ERROR:
                    Toast.makeText(ObjectActivity.this,"申请失败,重复申请",Toast.LENGTH_LONG).show();
                    break;
                case MyDefine.REPLY_NO_RESPONSE:
                    Toast.makeText(ObjectActivity.this,"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_layout);
        initComponent();
        initEvent();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_object_apply:
                Apply();
                break;
            default:
                break;
        }
    }

    private void initComponent(){
        iv_object_picture=findViewById(R.id.iv_object_picture);
        tv_object_id=findViewById(R.id.tv_object_id);
        tv_object_user_name=findViewById(R.id.tv_object_user_name);
        tv_object_name = findViewById(R.id.tv_object_name);
        tv_object_main_event_type = findViewById(R.id.tv_object_main_event_type);
        tv_object_location = findViewById(R.id.tv_object_location);
        tv_object_time = findViewById(R.id.tv_object_time);
        tv_object_description = findViewById(R.id.tv_object_description);
        btn_object_apply = findViewById(R.id.btn_object_apply);
    }

    private void initEvent(){
        btn_object_apply.setOnClickListener(this);
    }

    private void initData(){
        Bundle messageBundle = getIntent().getExtras();
        assert messageBundle != null;
        objectId=messageBundle.getInt("object_id",0);
        tv_object_id.setText(String.valueOf(objectId));
        tv_object_user_name.setText(messageBundle.getString("user_name",""));
        tv_object_name.setText(messageBundle.getString("name",""));
        tv_object_main_event_type.setText(MyEventChange.MainEventToString(messageBundle.getInt("main_event_type",0)));
        tv_object_location.setText(messageBundle.getString("location",""));
        tv_object_time.setText(messageBundle.getString("time",""));
        tv_object_description.setText(messageBundle.getString("description",""));

        //下载图片
        MyDataProcesser.DownloadObjectPicture(this,messageBundle.getInt("object_id",0),ObjectPictureHandler);

    }

    private void Apply(){
        int user_id=((MyApplication)getApplication()).getId();
        int main_event_id=objectId;
        Bundle bundle=MyBundle.ApplyBundle(user_id,main_event_id);
        MyDataProcesser.Apply(bundle,ApplyHandler);
    }

    private void LoadProfilePhoto(String absolutePath){
        File file=new File(absolutePath);
        Uri uri=getUriForFile(this,file);
        Glide.with(this).load(uri).into(iv_object_picture);
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
