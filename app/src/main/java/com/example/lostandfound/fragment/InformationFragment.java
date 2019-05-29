package com.example.lostandfound.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.lostandfound.R;
import com.example.lostandfound.activity.ApplyInfoActivity;
import com.example.lostandfound.activity.InformationActivity;
import com.example.lostandfound.activity.ModifyActivity;
import com.example.lostandfound.activity.SecurityChooseActivity;
import com.example.lostandfound.activity.UserPublishActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.File;
import java.util.Objects;

public class InformationFragment extends Fragment  implements View.OnClickListener  {

    private Context mContext;

    private CircleImageView circleImageView;

    private TextView tv_information_information;
    private TextView tv_information_modify;
    private TextView tv_information_security;
    private TextView tv_information_publish;
    private TextView tv_information_event;

    @SuppressLint("HandlerLeak")
    private Handler profilePhotoHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    LoadProfilePhoto();
                    break;
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
        MyDataProcesser.DownloadProfilePhoto(getActivity(),profilePhotoHandler);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.information_layout,container,false);
        initComponent(view);
        initEvent();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_information_information:
                StartInformation();
                break;
            case R.id.tv_information_modify:
                StartModify();
                break;
            case R.id.tv_information_security:
                StartSecurityChoose();
                break;
            case R.id.tv_information_publish:
                StartUserPublish();
                break;
            case R.id.tv_information_event:
                StartUserApply();
                break;
            default:
                break;
        }
    }

    private void initComponent(View view){
        tv_information_information = view.findViewById(R.id.tv_information_information);
        tv_information_modify = view.findViewById(R.id.tv_information_modify);
        tv_information_security = view.findViewById(R.id.tv_information_security);
        tv_information_publish = view.findViewById(R.id.tv_information_publish);
        tv_information_event = view.findViewById(R.id.tv_information_event);
        circleImageView=view.findViewById(R.id.img_profile_photo);
    }

    private void initEvent(){
        tv_information_information.setOnClickListener(this);
        tv_information_modify.setOnClickListener(this);
        tv_information_security.setOnClickListener(this);
        tv_information_publish.setOnClickListener(this);
        tv_information_event.setOnClickListener(this);
    }

    private void StartInformation(){
        Intent intent=new Intent(getActivity(),InformationActivity.class);
        startActivity(intent);
    }

    private void StartModify(){
        Intent intent=new Intent(getActivity(),ModifyActivity.class);
        startActivity(intent);
    }

    private void StartSecurityChoose(){
        Intent intent=new Intent(getActivity(),SecurityChooseActivity.class);
        startActivity(intent);
    }

    private void StartUserPublish(){
        Intent intent=new Intent(getActivity(),UserPublishActivity.class);
        startActivity(intent);
    }

    private void StartUserApply(){
        Intent intent=new Intent(getActivity(),ApplyInfoActivity.class);
        startActivity(intent);
    }

    private void LoadProfilePhoto(){
        String photo_path=((MyApplication)Objects.requireNonNull(getActivity()).getApplication()).getPhoto_path();
        if(photo_path==null){
            return;
        }
        File file=new File(photo_path);
        Uri uri=getUriForFile(getContext(),file);
        Glide.with(this).load(uri).into(circleImageView);
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
