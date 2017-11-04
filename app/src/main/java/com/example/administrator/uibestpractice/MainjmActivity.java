package com.example.administrator.uibestpractice;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainjmActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainjm);
        LinearLayout layout=(LinearLayout)findViewById(R.id.laotie);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainjmActivity.this,jiqirenActivity.class);
                startActivity(intent);

            }
        });
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        LinearLayout layout1=(LinearLayout)findViewById(R.id.shuangji);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainjmActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
/*
        final Slidingmenu slidingmenu=(Slidingmenu)findViewById(R.id.slidingmenu) ;
        Button button=(Button)findViewById(R.id.dakaimenu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  slidingmenu.toggle();
            }
        });*/
    }
}
