package com.example.administrator.uibestpractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/9/5.
 */

public class Mydatabase extends SQLiteOpenHelper {
    private Context mContext;
    public static final String creat_book="create table Book(" +
            "id integer primary key autoincrement," +
            " author text," +
            " price real," +
            " pages integer," +
            " name text)";
    public static final String creat_category="create table Category(" +
            "id integer primary key autoincrement," +
            " category_name text," +
            " category_code text)";
    public Mydatabase(Context context,String name,SQLiteDatabase.CursorFactory factory,int verson){
        super(context,name,factory,verson);
    mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creat_book);
        sqLiteDatabase.execSQL(creat_category);
        Toast.makeText(mContext,"数据库创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       sqLiteDatabase.execSQL("drop table if exists Book");
        sqLiteDatabase.execSQL("drop table if exists Category");
        onCreate(sqLiteDatabase);
    }
}
