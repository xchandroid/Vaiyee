package com.example.administrator.uibestpractice;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<Msg>msgList=new ArrayList<>();
    private RecyclerView recyclerView;
    private Button button,button2;
    private EditText editText;
    private MsgAdapter adapter;
    private Mydatabase dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null) {
            actionBar.hide();
        }
        initMsgs();
        dbhelper=new Mydatabase(this,"BookStore.db",null,2);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        button=(Button)findViewById(R.id.buttonSend);
        editText=(EditText)findViewById(R.id.edit_text);
         adapter=new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String context=editText.getText().toString();
                String n="小伙子不要得意忘形";
                String b="你以为你跟厉害吗？";
                String z="你就是个垃圾，还不是抄别人的代码";
                String r="小伙子你的android之路还很长";
                int i=0;
                if (!"".equals(context)){
                    Msg msg=new Msg(context,Msg.send);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    recyclerView.scrollToPosition(msgList.size()-1);
                    editText.setText("");
                    Msg a=new Msg(n,Msg.receivde);
                    msgList.add(a);
                    adapter.notifyItemInserted(msgList.size()-1);
                    recyclerView.scrollToPosition(msgList.size()-1);
                    switch (i){
                        case 0:
                            Msg q=new Msg(b,Msg.receivde);
                            msgList.add(q);
                            adapter.notifyItemInserted(msgList.size()-1);
                            recyclerView.scrollToPosition(msgList.size()-1);
                            i+=1;break;
                        case 1:
                            Msg w=new Msg(z,Msg.receivde);
                            msgList.add(w);
                            adapter.notifyItemInserted(msgList.size()-1);
                            recyclerView.scrollToPosition(msgList.size()-1);
                            i+=1;break;
                        case 2:
                            Msg e=new Msg(r,Msg.receivde);
                            msgList.add(e);
                            adapter.notifyItemInserted(msgList.size()-1);
                            recyclerView.scrollToPosition(msgList.size()-1);
                            i+=1;break;
                        default:
                    }
                }
            }
        });
        Button back=(Button)findViewById(R.id.back) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainjmActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button button1=(Button)findViewById(R.id.buttonxiaxian);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("com.example.administrator.uibestpractice.XIAXIAN");
                sendBroadcast(intent);
            }
        });
        button2=(Button)findViewById(R.id.createDB);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showmymenu(button2);
            }


        });
    }

    private void showmymenu(final View v) {
        PopupMenu popupmenu=new PopupMenu(this,v);
        popupmenu.getMenuInflater().inflate(R.menu.menu_item2,popupmenu.getMenu());
        popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.shujuku:
                        /*
                        dbhelper.getWritableDatabase();break;  //旧版数据库操作方式
                        */
                        Connector.getDatabase();
                        Toast.makeText(MainActivity.this,"数据库创建成功",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.adddate:
                        /*
                        SQLiteDatabase db=dbhelper.getWritableDatabase();
                        ContentValues values=new ContentValues();
                        values.put("name","计算机网络");
                        values.put("author","谢希仁");
                        values.put("pages",560);
                        values.put("price",50);
                        db.insert("Book",null,values);
                        values.clear();
                        values.put("name","java程序设计");
                        values.put("author","谢昌宏");
                        values.put("pages",456);
                        values.put("price",49.9);
                        db.insert("Book",null,values);
                        Toast.makeText(MainActivity.this,"数据添加成功",Toast.LENGTH_SHORT).show();
                        */
                        Book book=new Book();
                        book.setName("安卓程序开发");
                        book.setAuthor("谢昌宏");
                        book.setPages(580);
                        book.setPress("清华大学出版社");
                        book.setPrice(49);
                        book.save();

                        Book book1=new Book();
                        book1.setName("JAVA程序开发");
                        book1.setAuthor("谢明轩");
                        book1.setPages(480);
                        book1.setPress("清华大学出版社");
                        book1.setPrice(49);
                        book1.save();

                        Book book2=new Book();
                        book2.setName("Windows程序开发");
                        book2.setAuthor("王力宏");
                        book2.setPages(580);
                        book2.setPress("清华大学出版社");
                        book2.setPrice(29);
                        book2.save();
                        Toast.makeText(MainActivity.this,"数据添加成功",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.update:
                        /*
                        SQLiteDatabase db1=dbhelper.getWritableDatabase();
                        ContentValues values1=new ContentValues();
                        values1.put("price",25);
                        db1.update("Book",values1,"name=?",new String[]{"计算机网络"});
                        Toast.makeText(MainActivity.this,"更新数据成功",Toast.LENGTH_SHORT).show();
                        */
                        Book book3=new Book();
                        book3.setPrice(99);
                        book3.updateAll("press=?","清华大学出版社");
                        Toast.makeText(MainActivity.this,"更新数据成功",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.delete:
                        /*
                        SQLiteDatabase db2=dbhelper.getWritableDatabase();
                        db2.delete("Book","pages>?",new String[]{"500"});
                        Toast.makeText(MainActivity.this,"删除数据成功",Toast.LENGTH_SHORT).show();
                        */
                        DataSupport.deleteAll(Book.class,"price<?","30");
                        Toast.makeText(MainActivity.this,"小于30块的书删除数据成功",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:
                        /*
                        SQLiteDatabase db3=dbhelper.getWritableDatabase();
                        Cursor cursor=db3.query("Book",null,null,null,null,null,null);
                        if (cursor.moveToFirst()){
                            do {
                                String name=cursor.getString(cursor.getColumnIndex("name"));
                                String author=cursor.getString(cursor.getColumnIndex("author"));
                                int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                                int price=cursor.getInt(cursor.getColumnIndex("price"));
                                editText.setText("书名是："+name+"\n"+"作者是："+author+"\n"+"页数："+pages+"\n"+"价格是："+price);
                            }while (cursor.moveToNext());
                        }
                        cursor.close();*/
                        String xinxi0="";
                        List<Book>books=DataSupport.findAll(Book.class);
                        for (Book book4:books){
                            String name=book4.getName();
                            String author=book4.getAuthor();
                            int pages=book4.getPages();
                            Double price=book4.getPrice();
                            String press=book4.getPress();
                            xinxi0+=name+" "+author+" "+press+" "+pages+" "+price;
                        }
                        editText.setText(xinxi0);
                        break;
                    case R.id.firstdata:
                        Book firstBook=DataSupport.findFirst(Book.class);
                        String name=firstBook.getName();
                        String author=firstBook.getAuthor();
                        int pages=firstBook.getPages();
                        Double price=firstBook.getPrice();
                        String press=firstBook.getPress();
                        String xinxi=name+author+press+pages+price;
                        editText.setText(xinxi);break;
                    case R.id.seconddata:
                        List<Book> secondBook=DataSupport.where("name=?","JAVA程序开发").find(Book.class);
                        for (Book book5:secondBook)
                        {
                            String name1=book5.getName();
                            String author1=book5.getAuthor();
                            int pages1=book5.getPages();
                            Double price1=book5.getPrice();
                            String press1=book5.getPress();
                            String xinxi1=name1+author1+press1+pages1+price1;
                        editText.setText(xinxi1);
                        }
                        break;
                    default:
                }
                return true;
            }
        });
        popupmenu.show();
    }


    private void initMsgs(){
        Msg msg1=new Msg("我写的第一个聊天界面",Msg.receivde);
        msgList.add(msg1);
        Msg msg2=new Msg("有一点小小的成就感",Msg.send);
        msgList.add(msg2);
        Msg msg3=new Msg("一定要坚持下去",Msg.receivde);
        msgList.add(msg3);

    }
}
