package com.example.administrator.uibestpractice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginActivity extends BaseActivity {
     private EditText zhanghao;
    private  EditText mima;
    private Button login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox jizhumiam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        zhanghao=(EditText) findViewById(R.id.editText2);
        mima=(EditText) findViewById(R.id.editText);
        preferences=PreferenceManager.getDefaultSharedPreferences(this);
        jizhumiam=(CheckBox)findViewById(R.id.checkBox);
        final boolean isjizhu=preferences.getBoolean("jizhumiam",false);
        if(isjizhu){
            String p=preferences.getString("p","");
            mima.setText(p);
           jizhumiam.setChecked(true);
        }
        String inpuTtext=load();
        if (!TextUtils.isEmpty(inpuTtext))
        {
            zhanghao.setText(inpuTtext);
            zhanghao.setSelection(inpuTtext.length());
        }
        login=(Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accout=zhanghao.getText().toString();
                String password=mima.getText().toString();
                if(accout.equals("123")&&password.equals("123"))
                {
                    editor=preferences.edit();
                    if (jizhumiam.isChecked()){
                        editor.putBoolean("jizhumima",true);
                        editor.putString("p",password);
                    }
                    else {
                        editor.clear();
                    }
                    editor.apply();
                    AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("登录成功！");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(LoginActivity.this,Double_menu1.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }else {
                    Toast.makeText(LoginActivity.this,"账号或密码错误，请重新输入！",Toast.LENGTH_LONG).show();
                }
            }
        });
        final Button button5=(Button)findViewById(R.id.button);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showmenu(button5);
                Toast.makeText(LoginActivity.this,"点击了菜单键",Toast.LENGTH_LONG).show();
            }
        });

       ActionBar actionBar=getSupportActionBar();
    if (actionBar !=null)
        actionBar.hide();
    }
    private void showmenu(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                     Intent intent=new Intent(LoginActivity.this,MainjmActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.item2:
                        Toast.makeText(LoginActivity.this, "敬请期待", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.item3:
                    ActivityCollector.FinishALL();
                    default:
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(LoginActivity.this,"菜单已关闭",Toast.LENGTH_LONG).show();
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String string=zhanghao.getText().toString();
        baocun(string);
    }

    public void baocun(String shuju){
        FileOutputStream outputStream=null;
        BufferedWriter bufferedWriter=null;
        try {
            outputStream=openFileOutput("account", Context.MODE_PRIVATE);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(shuju);
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (bufferedWriter !=null) {
                    bufferedWriter.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public String load(){
        FileInputStream fileInputStream=null;
        BufferedReader bufferedReader=null;
        StringBuilder content=new StringBuilder();
        try {
            fileInputStream=openFileInput("account");
            bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
           String line="";
           while((line=bufferedReader.readLine())!=null){
                content.append(line);
           }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException a) {
                    a.printStackTrace();
                }
            }
        }
        return content.toString();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(LoginActivity.this, "功能正在完善中", Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                Toast.makeText(LoginActivity.this, "敬请期待", Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;

    }*/
}
