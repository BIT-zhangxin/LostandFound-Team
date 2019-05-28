package com.example.lostandfound.fragment;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.lostandfound.R;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.MyDefine;
import com.example.lostandfound.component.UriUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PublishFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    private Spinner spinner_event_type;
    private EditText et_publish_object_name;
    private EditText et_publish_location;
    private EditText et_publish_time;
    private EditText et_publish_description;
    private EditText et_publish_question;
    private Button btn_publish_photo;
    private Button btn_publish_select_image;
    private Button btn_publish_publish;
    private ImageView img_publish_image;


    public static final int PHOTO_REQUEST_CODE=1;//拍照
    public static final int PICTURE_SELECT_REQUEST_CODE =2;//选择图片
    private static final int PERMISSION_REQUEST_CODE=4;//获取权限

    public static final String JPG_FORMAT=".jpg";//jpg图片格式

    private boolean hasPermission=false;//是否已经获取权限

    private Uri photoUri;//保存拍照时返回的uri
    private Uri pictureUri;//保存选择图片时返回的uri

    private File uploadFile;//保存图片文件

    @SuppressLint("HandlerLeak")
    private Handler publishHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyDefine.REPLY_SUCCESS:
                    Toast.makeText(mContext,"发布成功",Toast.LENGTH_LONG).show();
                    Clear();
                    break;
                case MyDefine.REPLY_FAILED:
                    Toast.makeText(mContext,"发布失败",Toast.LENGTH_LONG).show();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_layout,container,false);
        initComponent(view);
        initEvent();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_publish_photo:
                checkPermissions();
                photoUri=goCamera();
                break;
            case R.id.btn_publish_select_image:
                goPictureAlbum();
                break;
            case R.id.btn_publish_publish:
                Publish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==PERMISSION_REQUEST_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                hasPermission=true;
            }
            else{
                hasPermission=false;
                Toast.makeText(getActivity(),"权限授予失败！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //如果图片为空
        if(resultCode!=RESULT_OK) return;
        //拍照
        if(requestCode==PHOTO_REQUEST_CODE){
            //cropUri=goCrop(photoUri);
            Glide.with(this).load(photoUri).into(img_publish_image);
        }
        //选择相册图片
        else if(requestCode==PICTURE_SELECT_REQUEST_CODE){
            pictureUri=data.getData();
            uploadFile=new File(UriUtils.getRealPathFromUri(getActivity(),pictureUri));
            //cropUri=goCrop(pictureUri);
            Glide.with(this).load(pictureUri).into(img_publish_image);
        }
    }

    private void initComponent(View view){
        spinner_event_type=view.findViewById(R.id.spinner_event_type);
        et_publish_object_name=view.findViewById(R.id.et_publish_object_name);
        et_publish_location=view.findViewById(R.id.et_publish_location);
        et_publish_time=view.findViewById(R.id.et_publish_time);
        et_publish_description=view.findViewById(R.id.et_publish_description);
        et_publish_question=view.findViewById(R.id.et_publish_question);
        btn_publish_photo=view.findViewById(R.id.btn_publish_photo);
        btn_publish_select_image=view.findViewById(R.id.btn_publish_select_image);
        btn_publish_publish=view.findViewById(R.id.btn_publish_publish);
        img_publish_image=view.findViewById(R.id.img_publish_image);
    }

    private void initEvent(){
        btn_publish_photo.setOnClickListener(this);
        btn_publish_select_image.setOnClickListener(this);
        btn_publish_publish.setOnClickListener(this);
    }

    void Publish(){
        String object_name=et_publish_object_name.getText().toString();
        if(object_name.equals("")){
            Message message=new Message();
            message.what=MyDefine.REPLY_FAILED;
            publishHandler.sendMessage(message);
            return;
        }
        int user_id=((MyApplication)Objects.requireNonNull(getActivity()).getApplication()).getId();
        int event_type=(int)spinner_event_type.getSelectedItemId()+1;
        String location=et_publish_location.getText().toString();
        String time=et_publish_time.getText().toString();
        String description=et_publish_description.getText().toString();
        String question=et_publish_question.getText().toString();
        Bundle bundle= MyBundle.PublishBundle(user_id,event_type,object_name,location,time,description,question);
        MyDataProcesser.Publish(uploadFile,bundle,publishHandler);
    }

    private void Clear(){
        et_publish_object_name.setText("");
        et_publish_location.setText("");
        et_publish_time.setText("");
        et_publish_description.setText("");
    }


    //调用相机
    private Uri goCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //创建拍照保存的图片文件
        uploadFile=createFile(JPG_FORMAT);
        Uri uri=getUriForFile(getActivity(), uploadFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,PHOTO_REQUEST_CODE);
        return uri;
    }

    //调用系统的相册
    private void goPictureAlbum(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICTURE_SELECT_REQUEST_CODE);
    }

    //动态检查权限
    private void checkPermissions(){
        if(hasPermission) return;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //检查是否有存储和拍照权限
            if(checkSelfPermission(Objects.requireNonNull(getActivity()),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(getActivity(),Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                hasPermission=true;
            }
            else{
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);
            }
        }
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

    //创建文件
    private File createFile(String format){
        String storagePath=Objects.requireNonNull(getActivity().getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath();
        File storageDir=new File(storagePath);
        //noinspection ResultOfMethodCallIgnored
        storageDir.mkdirs();
        return new File(storageDir,getStringToday()+format);
    }

    //获取当前时间字符串
    @SuppressLint("SimpleDateFormat")
    public static String getStringToday(){
        Date currentTime=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(currentTime);
    }
}
