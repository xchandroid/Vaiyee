package com.example.administrator.uibestpractice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lianxiren extends AppCompatActivity {
    lianxirenadapter lianxirenadapter;

    private Button bt_cancel, bt_delete;
   public TextView tv_sum;
    public LinearLayout linearLayout;
   public ListView listView;
    private boolean isduoxuan=false;
    public static boolean DUOXUAN=false;
    private CheckBox checkBox;
    private List<Lianxirenshuju>contactsList=new ArrayList<>();
    private List<String>deleteList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lianxiren);
       listView=(ListView)findViewById(R.id.lianxiren);
        lianxirenadapter=new lianxirenadapter(this,R.layout.lianxiren,contactsList);
        listView.setAdapter(lianxirenadapter);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                isduoxuan=true;
                DUOXUAN=true;
                linearLayout.setVisibility(View.VISIBLE);
                checkBox=(CheckBox)findViewById(R.id.cb_select);
                checkBox.setChecked(true);
                lianxirenadapter.notifyDataSetChanged();
                return true;
            }
        });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (isduoxuan) {
                        Lianxirenshuju lianxirenshuju=contactsList.get(i);
                        String number=lianxirenshuju.getHaoma();
                        if (checkBox.isChecked())
                        {
                            checkBox.setChecked(false);
                            deleteList.remove(number);
                        }
                        else {
                            checkBox.setChecked(true);
                            deleteList.add(number);
                        }
                        tv_sum.setText("共选择了"+deleteList.size()+"项");
                    }else {
                        Lianxirenshuju lianxirenshuju = contactsList.get(i);
                        String haoma = lianxirenshuju.getHaoma();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + haoma));
                        if (ContextCompat.checkSelfPermission(Lianxiren.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Lianxiren.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            startActivity(intent);
                        }
                    }
                }
            });

        if (ContextCompat.checkSelfPermission(Lianxiren.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Lianxiren.this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            readContactds();
        }
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null)
            actionBar.hide();

        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        tv_sum = (TextView) findViewById(R.id.tv_sum);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isduoxuan=false;
                DUOXUAN=false;
                deleteList.clear();
                linearLayout.setVisibility(View.GONE);
            }
        });
    }

    private void readContactds() {
        Cursor cursor=null;
        try {
                cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Lianxirenshuju lianxirenshuju=new Lianxirenshuju(name,number);
                        contactsList.add(lianxirenshuju);
                    }
                    lianxirenadapter.notifyDataSetChanged();
                }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readContactds();
                }else {
                    Toast.makeText(this,"您取消了联系人授权",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }
}
