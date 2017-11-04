package com.example.administrator.uibestpractice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class MyMediaPlayer extends AppCompatActivity implements View.OnClickListener{
    private Button play,pause,stop,videoplay,videopause,videoreset;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private VideoView videoView;
    private TextView geming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        play= (Button) findViewById(R.id.play);
        pause=(Button) findViewById(R.id.pause);
        stop =( Button) findViewById(R.id.stop);
        videoView=(VideoView)findViewById(R.id.videoView);
        videopause=(Button) findViewById(R.id.vidiopause);
        videoplay=(Button) findViewById(R.id.vidioplay);
        videoreset=(Button) findViewById(R.id .videoreset);
        videoplay.setOnClickListener(this);
        videopause.setOnClickListener(this);
        videoreset.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else
        {
            initmediaplayer();
            initvideoplayer();
        }
    }

    private void initvideoplayer() {
        try{
            File file= new File(Environment.getExternalStorageDirectory(),"跑步.mp4");
            videoView.setVideoPath(file.getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initmediaplayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "认真的雪.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
            geming=(TextView)findViewById(R.id.geming);
            geming.setText(file.getName());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initmediaplayer();
                    initmediaplayer();
                }else {
                    Toast.makeText(this,"取消了授权无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.play:
               if (!mediaPlayer.isPlaying())
               {
                   mediaPlayer.start();
                   play.setBackgroundResource(R.drawable.play);
                   String music=geming.getText().toString();
                   String a[] = music.split("\\.");
                   String ming=a[0];
                   Toast.makeText(this,"正在播放"+ming,Toast.LENGTH_SHORT).show();
               }else {
                   mediaPlayer.pause();
                   play.setBackgroundResource(R.drawable.pause);
               }break;
           case R.id.pause:
               if (mediaPlayer.isPlaying())
               {
                   mediaPlayer.pause();
               }break;
           case R.id.stop:
               if (mediaPlayer.isPlaying()){
                   mediaPlayer.reset();
                   initmediaplayer();
               }break;
           case R.id.vidioplay:
               if (!videoView.isPlaying())
               {
                   videoView.start();
               }break;
           case R.id.vidiopause:
               if (videoView.isPlaying())
               {
                   videoView.pause();
               }break;
           case R.id.videoreset:
               if (videoView.isPlaying())
               {
                   videoView.resume();
               }break;
           default:
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (videoView!=null)
        {
            videoView.suspend();
        }
    }
}
