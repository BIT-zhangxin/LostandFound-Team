package com.example.lostandfound.activity;

import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.lostandfound.R;
import com.example.lostandfound.component.MyAlertDialog;
import com.example.lostandfound.component.MyAppCompatActivity;
import com.example.lostandfound.fragment.InformationFragment;
import com.example.lostandfound.fragment.MessageFragment;
import com.example.lostandfound.fragment.PublishFragment;
import java.util.List;

public class MainActivity extends MyAppCompatActivity {

    private Fragment messageFragment;
    private Fragment publishFragment;
    private Fragment informationFragment;
    private Fragment mContent;//当前的fragment

    FragmentManager fragmentManager;

    private TextView tv_main_title;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            loadFragment(item.getItemId());
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initComponent();
        initView();
        initEvent();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        registerTip();
    }

    private void exit(){

        // 1\. 通过Context获取ActivityManager
            ActivityManager activityManager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);

        // 2\. 通过ActivityManager获取任务栈
        List<AppTask> appTaskList = activityManager.getAppTasks();

        // 3\. 逐个关闭Activity
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }
        // 4\. 结束进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    void registerTip(){
        MyAlertDialog myAlertDialog=new MyAlertDialog(MainActivity.this,0,
            "提示","你确认退出么？","确认","返回",false);
        myAlertDialog.setOnCertainButtonClickListener(new MyAlertDialog.onMyAlertDialogListener() {
            public void onCancelButtonClick() {

            }
            public void onCertainButtonClick() {

                exit();
            }
            public void onDismissListener() {

            }
        });
        myAlertDialog.show();
    }

    private void initComponent(){
        tv_main_title = findViewById(R.id.tv_main_title);
        navigation = findViewById(R.id.navigation);
        messageFragment=new MessageFragment();
        publishFragment=new PublishFragment();
        informationFragment=new InformationFragment();
        fragmentManager=getSupportFragmentManager();

        if(mContent==null){
            tv_main_title.setText(R.string.main_title_message);
            mContent=messageFragment;
            fragmentManager.beginTransaction()
                    .add(R.id.main_framelayout,mContent)
                    .commit();
        }
    }

    private void initView(){
        setStatusBarColor(this, ContextCompat.getColor(this,R.color.style));
    }

    private void initEvent(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //loadFragment(R.id.navigation_message);
    }

    private void loadFragment(int itemId){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        switch (itemId){
            case R.id.navigation_message:
                tv_main_title.setText(R.string.main_title_message);
                if (!messageFragment.isAdded()) {
                    transaction.hide(mContent).add(R.id.main_framelayout,messageFragment).commit();
                } else {
                    transaction.hide(mContent).show(messageFragment).commit();
                }
                mContent=messageFragment;
                break;
            case R.id.navigation_publish:
                tv_main_title.setText(R.string.main_title_publish);
                if (!publishFragment.isAdded()) {
                    transaction.hide(mContent).add(R.id.main_framelayout,publishFragment).commit();
                } else {
                    transaction.hide(mContent).show(publishFragment).commit();
                }
                mContent=publishFragment;
                break;
            case R.id.navigation_information:
                tv_main_title.setText(R.string.main_title_information);
                if (!informationFragment.isAdded()) {
                    transaction.hide(mContent).add(R.id.main_framelayout,informationFragment).commit();
                } else {
                    transaction.hide(mContent).show(informationFragment).commit();
                }
                mContent=informationFragment;
                break;
        }
    }
}
