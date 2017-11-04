package com.example.administrator.uibestpractice;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private List<Msg>MsgList;
      static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout left;
        LinearLayout right;
        TextView msg_left;
        TextView msg_right;
        public ViewHolder(View view){
            super(view);
            left=(LinearLayout)view.findViewById(R.id.left);
            right=(LinearLayout)view.findViewById(R.id.right);
            msg_left=(TextView)view.findViewById(R.id.text_left);
            msg_right=(TextView)view.findViewById(R.id.text_right);
        }
    }
    public MsgAdapter(List<Msg>msgList){
        MsgList=msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
      ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg=MsgList.get(position);
        if (msg.getType()==Msg.receivde){
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
            holder.msg_left.setText(msg.getContex());
        }else if (msg.getType()==Msg.send){
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            holder.msg_right.setText(msg.getContex());
        }
    }

    @Override
    public int getItemCount() {
        return MsgList.size();
    }
}
