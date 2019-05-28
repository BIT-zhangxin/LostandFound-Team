package com.example.lostandfound.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.component.MyApplication;
import com.example.lostandfound.component.MyBundle;
import com.example.lostandfound.component.MyDataProcesser;
import com.example.lostandfound.component.UriUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class TestPhotoActivity extends MyAppCompatActivity implements View.OnClickListener {

    public static final int PHOTO_REQUEST_CODE=1;//拍照
    public static final int PICTURE_SELECT_REQUEST_CODE =2;//选择图片
    public static final int CROP_REQUEST_CODE=3;//裁剪图片
    private static final int PERMISSION_REQUEST_CODE=4;//获取权限

    public static final String JPG_FORMAT=".jpg";//jpg图片格式
    public static final String JPEG_FORMAT=".jpeg";//jpeg图片格式

    public static final String PROFILE_PHOTO_TYPE="profile_photo";
    public static final String OBJECT_PICTURE_TYPE="object_picture";

    private boolean hasPermission=false;//是否已经获取权限

    private MyApplication myApplication;

    private Uri photoUri;//保存拍照时返回的uri
    private Uri pictureUri;//保存选择图片时返回的uri
    private Uri cropUri;//保存裁剪时返回的uri

    private File photoFile;//保存拍照文件
    private File pictureFile;//保存选择图片文件
    private File cropFile;//保存裁剪文件



    private Button button1;
    private Button button2;

    private ImageView smallImg;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_photo_layout);
        initView();
        checkPermissions();
    }

    private void initView(){
        smallImg=findViewById(R.id.small_img);
        button1=findViewById(R.id.btn_test_photo_photo);
        button2=findViewById(R.id.btn_test_photo_select);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        myApplication=(MyApplication)getApplication();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_test_photo_photo:
                photoUri=goCamera();
                break;
            case R.id.btn_test_photo_select:
                goPictureAlbum();
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
                Toast.makeText(this,"权限授予失败！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //如果图片为空
        if(resultCode!=RESULT_OK) return;
        //拍照
        if(requestCode==PHOTO_REQUEST_CODE){
            cropUri=goCrop(photoUri);
        }
        //选择相册图片
        else if(requestCode==PICTURE_SELECT_REQUEST_CODE){
            pictureUri=data.getData();
            pictureFile=new File(UriUtils.getRealPathFromUri(this,pictureUri));
            cropUri=goCrop(pictureUri);
        }
        //裁剪图片
        else if(requestCode==CROP_REQUEST_CODE){
            Glide.with(this).load(cropUri).into(smallImg);
        }
    }

    //调用相机
    private Uri goCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //创建拍照保存的图片文件
        photoFile=createFile(JPG_FORMAT);
        Uri uri=getUriForFile(this, photoFile);
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

    //调用图片裁剪
    private Uri goCrop(Uri inputUri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(inputUri,"image/*");
        intent.putExtra("scale",true);
        //aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",250);
        intent.putExtra("outputY",250);
        //取消人脸识别
        intent.putExtra("noFaceDetection",true);
        //创建裁剪图片的文件
        cropFile=createFile(JPEG_FORMAT);
        Uri outputUri=Uri.fromFile(cropFile);
        //设置不要返回图片数据，而是存储在uri位置
        intent.putExtra("return-data",false);
        intent.putExtra("output",outputUri);
        //设置图片格式，只支持png，jpeg和webp
        intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent,CROP_REQUEST_CODE);
        return outputUri;
    }

    //动态检查权限
    private void checkPermissions(){
        if(hasPermission) return;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //检查是否有存储和拍照权限
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
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
        String storagePath=Objects.requireNonNull(getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath();
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

//    //上传图片
//    private void upload(File file,String type){
//        Bundle bundle=MyBundle.PictureBundle(myApplication.getId());
//        switch (type) {
//            case PROFILE_PHOTO_TYPE:
//                MyDataProcesser.UploadProfilePhoto(file,bundle,null);
//                break;
//            case OBJECT_PICTURE_TYPE:
//                MyDataProcesser.UploadObjectPicture(file,bundle,null);
//                break;
//            default:
//                break;
//        }
//    }
//
//    //下载图片
//    private void download(Activity activity,String type){
//        Bundle bundle=MyBundle.PictureBundle(myApplication.getId());
//        switch (type) {
//            case PROFILE_PHOTO_TYPE:
//                MyDataProcesser.DownloadProfilePhoto(activity,bundle,null);
//                break;
//            case OBJECT_PICTURE_TYPE:
//                MyDataProcesser.DownloadObjectPicture(activity,bundle,null);
//                break;
//            default:
//                break;
//        }
//    }

}
