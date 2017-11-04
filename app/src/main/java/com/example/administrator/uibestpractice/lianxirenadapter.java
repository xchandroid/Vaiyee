package com.example.administrator.uibestpractice;

import android.app.Application;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Created by Administrator on 2017/9/14.
 */

public class lianxirenadapter extends ArrayAdapter<Lianxirenshuju> {
    private int resourbuju;


    public lianxirenadapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        resourbuju=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       Lianxirenshuju lianxirenshuju=getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView==null){
       view= LayoutInflater.from(getContext()).inflate(resourbuju,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.xingming=view.findViewById(R.id.xingming);
            viewHolder.haoma=view.findViewById(R.id.haoma);
            viewHolder.checkBox=view.findViewById(R.id.cb_select);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.xingming.setText(lianxirenshuju.getXingming());
        viewHolder.haoma.setText(lianxirenshuju.getHaoma());
        if (Lianxiren.DUOXUAN)
        {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
        }else {
            viewHolder.checkBox.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewHolder{
       public TextView xingming;
        public TextView haoma;
        private CheckBox checkBox;
    }
}
