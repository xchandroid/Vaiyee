package com.example.administrator.uibestpractice;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.nineoldandroids.view.ViewHelper;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Double_menu1 extends FragmentActivity implements View.OnClickListener,ViewPager.OnPageChangeListener
{

    private DrawerLayout mDrawerLayout;
    public static final int PAIZHAO=1;
    public static final int XIANGCE=2;
    private ImageView zhaopian,zhaopian2;
    private Uri imageuri;
    private EditText wangzhi;
    private Button dakaiwangzhi,start_download,pause_download,cancel_download;
    private WebView webView;
    private TextView fanhuixinxi,weizhixinxi;

    // 底部菜单4个Linearlayout
    private LinearLayout ll_home;
    private LinearLayout ll_address;
    private LinearLayout ll_friend;
    private LinearLayout ll_setting;

    // 底部菜单4个ImageView
    private ImageView iv_home;
    private ImageView iv_address;
    private ImageView iv_friend;
    private ImageView iv_setting;

    // 底部菜单4个菜单标题
    private TextView tv_home;
    private TextView tv_address;
    private TextView tv_friend;
    private TextView tv_setting;

    // 中间内容区域
    private ViewPager viewPager;

    // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;
    private List<View> views;
    //下载服务
    private DownloadService.DownloadBinder downloadBinder;
    //获取位置
    LocationClient locationClient;
    //防止多次移动到当前位置
    private boolean isFirstLocation=true;
    private baiduMap a;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
         downloadBinder = (DownloadService.DownloadBinder)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_double_menu1);
        a= new baiduMap();
        initView();
        initEvents();
        wangzhi=(EditText)findViewById(R.id.wangzhi);
        List<String> pemissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(Double_menu1.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            pemissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(Double_menu1.this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED)
        {
            pemissions.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!pemissions.isEmpty())
        {
            String[] pemission = pemissions.toArray(new String[pemissions.size()]);
            ActivityCompat.requestPermissions(this,pemission,6);
        }

        dakaiwangzhi=(Button)findViewById(R.id.openRbutton);
        /*
        dakaiwangzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wz ="http://"+wangzhi.getText().toString();

                sendRequstwithOKhttp();

                /*
                String wz ="http://"+wangzhi.getText().toString();
             if (wz!=null)
             {
                 webView.getSettings().setJavaScriptEnabled(true);
                 webView.setWebViewClient(new WebViewClient());
                 webView.loadUrl(wz);
             }else
             {
                 Toast.makeText(Double_menu1.this,"请输入正确的网址",Toast.LENGTH_SHORT).show();
             }
            }
        });*/
        RelativeLayout bofangmusic=(RelativeLayout)findViewById(R.id.playmusic);
        bofangmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Double_menu1.this,MyMediaPlayer.class);
                startActivity(intent);
            }
        });
        LinearLayout xiangce=(LinearLayout)findViewById(R.id.xiangce);
        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Double_menu1.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Double_menu1.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }else {
                    openAlum();
                }
            }


        });
        zhaopian=(ImageView)findViewById(R.id.showphoto);
        zhaopian2=(ImageView)findViewById(R.id.showphoto2);
        RelativeLayout recyclerView=(RelativeLayout)findViewById(R.id.baoma);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Double_menu1.this,jiqirenActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final LinearLayout paizhao=(LinearLayout)findViewById(R.id.paizhao);
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File zhaopian=new File(getExternalCacheDir(),"zhaopiao.jpg");
                try {
                   if (zhaopian.exists()) {
                       zhaopian.delete();
                   }
                       zhaopian.createNewFile();
                }catch (IOException e){
                     e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                  imageuri= FileProvider.getUriForFile(Double_menu1.this,"ABCD",zhaopian);
                }else {
                    imageuri=Uri.fromFile(zhaopian);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                startActivityForResult(intent,PAIZHAO);
            }
        });
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.falali);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Double_menu1.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        LinearLayout layout=(LinearLayout)findViewById(R.id.duqulainxiren);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Double_menu1.this,Lianxiren.class);
                startActivity(intent);
            }
        });
        ImageView button=(ImageView)findViewById(R.id.dadianhua);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Double_menu1.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Double_menu1.this,new String[]{Manifest.permission.CALL_PHONE},1);
                }else
                {
                    call();
                }
            }

        });

        // 初始化控件
        initView1();
        // 初始化底部按钮事件
        initEvent1();
    }

    private void requestLocation() {
        initLocation();
        locationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        /*指定定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        */
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        locationClient.setLocOption(option);
    }

    public  void setDingwei(View v)
    {
        requestLocation();
    }
    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentLocation = new StringBuilder();
            currentLocation.append("纬度:").append(bdLocation.getLatitude()).append("\n");
            currentLocation.append("经线:").append(bdLocation.getLongitude()).append("\n").append("国家:").append(bdLocation.getCountry())
            .append("\n").append("省/区:").append(bdLocation.getProvince()).append("\n")
            .append("城市:").append(bdLocation.getCity()).append("\n")
            .append("区/县:").append(bdLocation.getDistrict()).append("\n")
            .append("街道:").append(bdLocation.getLocationDescribe()).append("\n")
                   .append("定位方式:");
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation)
            {
                currentLocation.append("GPS");
            }else if (bdLocation.getLocType()==BDLocation.TypeNetWorkLocation)
            {
                currentLocation.append("网络");
            }
            View mview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.page_01,null);
            Button bt = (Button)mview.findViewById(R.id.dingwei);
            bt.setClickable(false);
            weizhixinxi= mview.findViewById(R.id.weizhiTV);
           weizhixinxi.setText(currentLocation);
           views.add(mview);adapter.notifyDataSetChanged();
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()==BDLocation.TypeNetWorkLocation)
            {
                navigateTO(bdLocation);
            }
        }
    }

    private void navigateTO(BDLocation bdLocation) {
        if (isFirstLocation)
        {
            LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
           a.baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            a.baiduMap.animateMapStatus(update);
            isFirstLocation = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(bdLocation.getLatitude());
        locationBuilder.longitude(bdLocation.getLongitude());
        MyLocationData locationData =locationBuilder.build();
        a.baiduMap.setMyLocationData(locationData);

    }

    private void initEvent1() {
        // 设置按钮监听
        ll_home.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_friend.setOnClickListener(this);
        ll_setting.setOnClickListener(this);

        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);


        Intent serviceIntent = new Intent(Double_menu1.this,DownloadService.class);
        startService(serviceIntent);//启动服务
        bindService(serviceIntent,serviceConnection,BIND_AUTO_CREATE);//绑定服务
    }

    private void initView1() {

        // 底部菜单4个Linearlayout
        this.ll_home = (LinearLayout) findViewById(R.id.ll_home);
        this.ll_address = (LinearLayout) findViewById(R.id.ll_address);
        this.ll_friend = (LinearLayout) findViewById(R.id.ll_friend);
        this.ll_setting = (LinearLayout) findViewById(R.id.ll_setting);

        // 底部菜单4个ImageView
        this.iv_home = (ImageView) findViewById(R.id.iv_home);
        this.iv_address = (ImageView) findViewById(R.id.iv_address);
        this.iv_friend = (ImageView) findViewById(R.id.iv_friend);
        this.iv_setting = (ImageView) findViewById(R.id.iv_setting);

        // 底部菜单4个菜单标题
        this.tv_home = (TextView) findViewById(R.id.tv_home);
        this.tv_address = (TextView) findViewById(R.id.tv_address);
        this.tv_friend = (TextView) findViewById(R.id.tv_friend);
        this.tv_setting = (TextView) findViewById(R.id.tv_setting);

        // 中间内容区域ViewPager
        this.viewPager = (ViewPager) findViewById(R.id.vp_content);

        // 适配器
        View page_01 = View.inflate(Double_menu1.this, R.layout.page_01, null);
        View page_02 = View.inflate(Double_menu1.this, R.layout.page_02, null);
        View page_03 = View.inflate(Double_menu1.this, R.layout.page_03, null);
        View page_04 = View.inflate(Double_menu1.this, R.layout.page_04, null);

        views = new ArrayList<View>();
        views.add(page_01);
        views.add(page_02);
        views.add(page_03);
        views.add(page_04);

        this.adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);
        //下载按钮

         start_download = (Button) findViewById(R.id.start_download);
         pause_download = findViewById(R.id.pause_download);
         cancel_download = findViewById(R.id.cancel_download);

    }


    @Override
   public void onPageScrollStateChanged(int arg0) {

     }

           @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

    @Override
    public void onClick(View view) {

        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // ImageView和TetxView置为绿色，页面随之跳转
        switch (view.getId()) {
            case R.id.ll_home:
                iv_home.setImageResource(R.drawable.xiaoxi1);
                tv_home.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_address:
                iv_address.setImageResource(R.drawable.lianxiren);
                tv_address.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_friend:
                iv_friend.setImageResource(R.drawable.dongtai);
                tv_friend.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_setting:
                iv_setting.setImageResource(R.drawable.gengduo);
                tv_setting.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(3);
                break;
            case R.id.start_download:
                String url = "https://raw.githubusercontent/com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                downloadBinder.startDownoad(url);
                break;
            case R.id.pause_download :
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downloadBinder.cancelDownload();break;
            default:
                break;
        }
    }

    private void restartBotton() {
        // ImageView置为灰色
        iv_home.setImageResource(R.drawable.tab_weixin_normal);
        iv_address.setImageResource(R.drawable.tab_address_normal);
        iv_friend.setImageResource(R.drawable.tab_find_frd_normal);
        iv_setting.setImageResource(R.drawable.tab_settings_normal);
        // TextView置为白色
        tv_home.setTextColor(0xffffffff);
        tv_address.setTextColor(0xffffffff);
        tv_friend.setTextColor(0xffffffff);
        tv_setting.setTextColor(0xffffffff);
    }

    @Override
    public void onPageSelected(int position) {

        restartBotton();
        //当前view被选择的时候,改变底部菜单图片，文字颜色
        switch (position) {
            case 0:
                iv_home.setImageResource(R.drawable.xiaoxi1);
                tv_home.setTextColor(0xff1B940A);
                break;
            case 1:
                iv_address.setImageResource(R.drawable.lianxiren);
                tv_address.setTextColor(0xff1B940A);
                break;
            case 2:
                iv_friend.setImageResource(R.drawable.dongtai);
                tv_friend.setTextColor(0xff1B940A);
                break;
            case 3:
                iv_setting.setImageResource(R.drawable.gengduo);
                tv_setting.setTextColor(0xff1B940A);
                break;

            default:
                break;
        }
    }

    private void sendRequstwithOKhttp() {
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient okHttpClient =new OkHttpClient();
                  Request request = new Request.Builder().
                          url("http://10.0.2.2/get_data.xml").build();
                    Response response =okHttpClient.newCall(request).execute();
                    String fanhuishuju= response.body().string();
                   jiexiXMl(fanhuishuju);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jiexiXMl(String fanhuishuju) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullPaser = factory.newPullParser();
            xmlPullPaser.setInput(new StringReader(fanhuishuju));
            int evenType = xmlPullPaser.getEventType();
            String id="";
            String name="";
            String version = "";
            while (evenType!=XmlPullParser.END_DOCUMENT)
            {
                String nodeName = xmlPullPaser.getName();
                switch (evenType)
                {
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)){
                            id = xmlPullPaser.nextText();
                        }else if ("name".equals(nodeName))
                        {
                            name = xmlPullPaser.nextText();
                        }else if ("version".equals(nodeName))
                        {
                            version = xmlPullPaser.nextText();
                        }   break;
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName))
                        {
                            Log.d("Double_menu1",id);
                            Log.d("Double_menu1",name);
                            Log.d("Double_menu1",version);
                            String XMl=id+name+version;
                            /*
                            showXMl(XMl);
                            */
                        }break;
                    default:
                }
                evenType = xmlPullPaser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    private void showXMl(String xMl) {
        TextView xml = (TextView)findViewById(R.id.xmlText);
        xml.setText(xMl);
    }

    /*
        private void showResponse(String fanhuishuju) {
            fanhuixinxi =(TextView)findViewById(R.id.fanhuixinxi);
            fanhuixinxi.setText(fanhuishuju);
        }
    */
    private void openAlum() {
        Intent intent= new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,XIANGCE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PAIZHAO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
                        zhaopian.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case XIANGCE:
                if (requestCode == RESULT_OK) {
                    handleimageonkitkat(data);
                    break;
                }
        }
    }

    private void handleimagebeforekitkat(Intent data) {
          Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleimageonkitkat(Intent data) {
          String imagePath=null;
          Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri))
        {
            String DocId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority()))
            {
                String id=DocId.split(":")[1];
                String seletion=MediaStore.Images.Media._ID+ "=" +id;
                imagePath= getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,seletion);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri conctentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocId));
                imagePath = getImagePath(conctentUri, null);
               }
            }else if ("content".equalsIgnoreCase(uri.getScheme()))
            {
                imagePath=getImagePath(uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme()))
            {
                imagePath=uri.getPath();
            }
          displayImage(imagePath);
    }

    private void displayImage(String imagePath) {

       if (imagePath!=null){
           Bitmap bitmap1=BitmapFactory.decodeFile(imagePath);
           zhaopian2.setImageBitmap(bitmap1);
           Toast.makeText(this,"获取图片地址成功",Toast.LENGTH_SHORT).show();
       }else {
           Toast.makeText(this,"获取图片地址失败",Toast.LENGTH_SHORT).show();
       }
    }

    private String getImagePath(Uri externalContentUri, String seletion) {
          String path=null;
        Cursor cursor=getContentResolver().query(externalContentUri,null,seletion,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }cursor.close();
        }
        return path;
    }

    private void call() {
        try {
            Intent intent=new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);

        }catch ( SecurityException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    call();
                }else {
                    Toast.makeText(this,"你拒绝了打电话",Toast.LENGTH_SHORT).show();
                }break;
            case 2:
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    openAlum();
                }else {
                    Toast.makeText(this,"你拒绝了内存访问",Toast.LENGTH_SHORT).show();
                }break;
            case 6:
                if (grantResults.length>0)
                {
                    for (int result : grantResults)
                    {
                        if (result!=PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(Double_menu1.this,"您必须同意所有权限才能 正常使用本程序",Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else
                {
                    Toast.makeText(Double_menu1.this,"发生未知错误！",Toast.LENGTH_LONG).show();
                }

            default:
        }
    }

    //开始下载
    public void star_download(View view)
    {
        String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
        downloadBinder.startDownoad(url);
    }
    //暂停下载
    public void pause_download(View view)
    {
        downloadBinder.pauseDownload();
    }
    //取消下载
    public void cancel_download(View view)
    {
        downloadBinder.cancelDownload();
    }
    //打开右侧菜单
    public void OpenRightMenu(View view)
    {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.RIGHT);
    }

    private void initEvents()
    {
        mDrawerLayout.setDrawerListener(new DrawerListener()
        {
            @Override
            public void onDrawerStateChanged(int newState)
            {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT"))
                {

                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else if(drawerView.getTag().equals("RIGHT"))
                {
                    ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);
            }
        });
    }

    private void initView()
    {
        mDrawerLayout = findViewById(R.id.drawerlayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.RIGHT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        locationClient.stop();
        a.baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("温馨提示！");
        builder.setMessage("确实退出应用吗？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
              finish();
            }
        });
        builder.setNegativeButton("否",null);
        builder.create().show();
    }
    public void openMap(View view)
    {
        Intent intent = new Intent(Double_menu1.this,baiduMap.class);
        startActivity(intent);
    }
}