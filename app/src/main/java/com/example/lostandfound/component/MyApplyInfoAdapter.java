package com.example.lostandfound.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lostandfound.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyApplyInfoAdapter extends ArrayAdapter<MyApplyInfo> {
    private int resourceId;

    public MyApplyInfoAdapter(Context context, int textViewResourceId, List<MyApplyInfo> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MyApplyInfo myApplyInfo=getItem(position);
        @SuppressLint("ViewHolder") View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        TextView tv_apply_info_object_name=view.findViewById(R.id.tv_apply_info_object_name);
        TextView tv_apply_info_event_type=view.findViewById(R.id.tv_apply_info_event_type);
        TextView tv_apply_info_time=view.findViewById(R.id.tv_apply_info_time);

        assert myApplyInfo != null;
        tv_apply_info_object_name.setText(myApplyInfo.getObject_name());
        int sub_event_type=myApplyInfo.getSub_event_type();
        tv_apply_info_event_type.setText(MyEventChange.SubEventToString(sub_event_type));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式可以自己根据需要修改
        String dateString = simpleDateFormat.format(myApplyInfo.getTime());

        tv_apply_info_time.setText(dateString);
        return view;
    }
}