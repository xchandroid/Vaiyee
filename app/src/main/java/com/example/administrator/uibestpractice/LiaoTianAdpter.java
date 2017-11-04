package com.example.administrator.uibestpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class LiaoTianAdpter extends BaseAdapter {
   private List<LiaoTianBean>liaoTianBeens;
    private Context context;
    private int tid;
    private LayoutInflater layoutInflater;
    public LiaoTianAdpter(List<LiaoTianBean>liaoTianBeens,Context context){
        this.liaoTianBeens=liaoTianBeens;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return liaoTianBeens.size();
    }

    @Override
    public Object getItem(int i) {
        return liaoTianBeens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (liaoTianBeens.get(i).getState()==LiaoTianBean.JIESHOU){
            view=layoutInflater.inflate(R.layout.chatting_item_msg_text_left,null);
        }else if(liaoTianBeens.get(i).getState()==LiaoTianBean.SEND) {
            view=layoutInflater.inflate(R.layout.chatting_item_msg_text_right,null);
        }
         Holder holder=new Holder();
        holder.textView=(TextView)view.findViewById(R.id.tv_chatcontent);
        holder.imageView=(ImageView)view.findViewById(R.id.iv_userhead);
        holder.textView.setText(liaoTianBeens.get(i).getMessage());
        return view;
    }
    static class Holder{
        public TextView textView;
        public ImageView imageView;
    }
}
