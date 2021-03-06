package com.example.administrator.uibestpractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.gaokao.HCKHttpClient;
import com.example.administrator.gaokao.JsonHttpResponseHandler;
import com.example.administrator.gaokao.RequestParams;

public class jiqirenActivity extends BaseActivity {

    private ListView listView;  //显示聊天信息
    private LiaoTianAdpter adpter;
    private List<LiaoTianBean> liaoTianBeans;  //存放所有聊天数据的集合
    private EditText editText;
    private Button sendButton;
    Button buttonback;
    TextView textView;
    private static final String API = "http://www.tuling123.com/openapi/api";//api地址

    //应用唯一key，请到官网注册账号后，可以获取，注册地址：
    //http://www.tuling123.com/openapi/record.do?channel=43035
    private static final String KEY_STRING = "17657dd1fc353e824a7b122985b4870e";


    private String sendMessage;   //你自己发送的单条信息
    private String welcome[];  //放置欢迎信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiqiren);
        liaoTianBeans = new ArrayList<LiaoTianBean>();
        initViews(); //初始化view和adpter
        welcome = getResources().getStringArray(R.array.welcome); //获取我们内置的欢迎信息
        setListener(); //绑定监听事件
        initData(); // 进入界面，随机显示机器人的欢迎信息
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null)
            actionBar.hide();
    }

    private void initData() {

        int pos = (int) (Math.random() * welcome.length - 1);  //获取一个随机数
        showData(welcome[pos]); //用随机数获取我们内置的信息，用户进入界面，机器人的首次聊天信息

    }

    public void initViews() { //这个不解释了
        listView = (ListView) findViewById(R.id.list);
        adpter = new LiaoTianAdpter(liaoTianBeans, this);
        editText = (EditText) findViewById(R.id.et_sendmessage);
        sendButton = (Button) findViewById(R.id.btn_send);
        buttonback=(Button) findViewById(R.id.jiqirenback) ;
        textView=(TextView)findViewById(R.id.gengduo);
        listView.setAdapter(adpter);

    }

    public void setListener() {
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendData(); //点击发送按钮，触发

            }
        });
        buttonback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(jiqirenActivity.this,MainjmActivity.class);
                startActivity(intent);
                finish();
            }
        });
       textView.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(jiqirenActivity.this,"更多更能正在完善中",Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void sendData() {
        sendMessage = editText.getText().toString(); //获取你输入的信息
        if (sendMessage == null) { //判断是否为空
            Toast.makeText(this, "您还未输任何信息哦",Toast.LENGTH_LONG).show();
            return;
        }
        editText.setText("");
        sendMessage = sendMessage.replaceAll(" ", "").replaceAll("\n", "")
                .trim(); //替换空格和换行
        //LiaoTianBean是一个实体类，
        //封装我们发送的信息，然后加入集合，更新listview
        LiaoTianBean liaoTianBean = new LiaoTianBean();
        liaoTianBean.setMessage(sendMessage);
        liaoTianBean.setState(1); //1标示自己发送的信息
        liaoTianBeans.add(liaoTianBean); //把自己发送的信息，加入集合
        adpter.notifyDataSetChanged(); //通知listview更新

        getDataFromServer(); //从服务器获取返回到额数据，机器人的信息
    }

    public void getDataFromServer() {
        //获取机器人信息，这里使用的HCKHttpClient是一个开源框架，大家可以直接拿去用
        RequestParams params = new RequestParams();//放置我们需要传递的数据
        params.put("key", KEY_STRING);
        params.put("info", sendMessage);
        new HCKHttpClient().get(API, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(Throwable error, String content) { //获取数据失败调用
                super.onFailure(error, content);

                showData("主人，您的网络有问题哦");
            }

            @Override
            public void onSuccess(int statusCode, JSONObject response) {//获取数据成功调用
                super.onSuccess(statusCode, response);

                paresData(response);
            }

            @Override
            public void onFinish(String url) {//请求完成调用
                super.onFinish(url);

            }
        });
    }


    private void paresData(JSONObject jb) {  //解析返回的json数据
        try {
            String content = jb.getString("text"); //获取的机器人信息
            int code = jb.getInt("code");//服务器状态码
            updateView(code, content); //更新界面
        } catch (JSONException e) {
            e.printStackTrace();
            showData("主人，你的网络不好哦");

        }

    }

    private void showData(String message) {
        //和上面一样，创建一个LiaoTianBean对象，传入数据，在把它放进集合
        LiaoTianBean liaoTianBean = new LiaoTianBean();
        liaoTianBean.setMessage(message);
        liaoTianBean.setState(2); //这里2标示机器人的信息，用于listview的adpter，显示不同的界面
        liaoTianBeans.add(liaoTianBean);
        adpter.notifyDataSetChanged();

    }

    private void updateView(int code, String content) {
        //code有很多种状态，具体看官网
        //http://www.tuling123.com/openapi/record.do?channel=43035
        switch (code) {
            case 4004:
                showData("主人今天我累了，我要休息了，明天再来找我耍吧");
                break;
            case 40005:
                showData("主人，我听不懂你在说什么哦");
                break;
            case 40006:
                showData("主人，我今天闭关修炼哦，占不接客啦");
                break;
            case 40007:
                showData("主人，明天再和你耍啦，我吃坏肚子了，呜呜。。。");
                break;
            default:
                showData(content);
                Intent intent=new Intent(this,jiqirenActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
                NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification= new NotificationCompat.Builder(this).
                        setContentText(content)
                        .setContentTitle("收到一条新消息")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.notification)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notification))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true).
                               setDefaults(Notification.DEFAULT_ALL).
                                setPriority(NotificationCompat.PRIORITY_MAX).build();
                notificationManager.notify(1,notification);
                break;
        }
    }
}
