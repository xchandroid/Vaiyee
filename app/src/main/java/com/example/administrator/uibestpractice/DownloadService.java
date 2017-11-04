package com.example.administrator.uibestpractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private String downloadUrl;
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("正在下载...",progress));
        }

        @Override
        public void onSuccess() {
            downloadTask=null;
            stopForeground(true);
     getNotificationManager().notify(1,getNotification("下载成功！",-1));
            Toast.makeText(DownloadService.this,"下载成功1",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
           downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载失败！",-1));
            Toast.makeText(DownloadService.this,"下载失败" ,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this,"下载暂停" ,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"下载取消" ,Toast.LENGTH_LONG).show();
        }
    };
    public DownloadService() {
    }
    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title,int progress){
        Intent intent = new Intent(this,Double_menu1.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.xiazai));
        builder.setSmallIcon(R.drawable.xiazai1);
        builder.setContentTitle(title);
        builder.setContentIntent(pendingIntent);
        if (progress>=0)
        {
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
    DownloadBinder mBinder =new DownloadBinder();
    class DownloadBinder extends Binder{
      public void startDownoad(String url){
          if (downloadTask==null)
          {
              downloadUrl = url;
              downloadTask = new DownloadTask(downloadListener);
              downloadTask.execute(url);
              startForeground(1,getNotification("正在下载",0));
              Toast.makeText(DownloadService.this,"下载中..." ,Toast.LENGTH_LONG).show();
          }
      }
      public void  pauseDownload()
      {
          if (downloadTask!=null)
          {
              downloadTask.pauseDownload();
          }
      }
      public void cancelDownload()
      {
          if (downloadTask!=null)
          {
              downloadTask.cancelDownload();
          }else {
              if (downloadUrl!=null)
              {
                  String filename = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                  String diretory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                  File file = new File(diretory+filename);
                  if (file.exists())
                  {
                      file.delete();
                  }
                  getNotificationManager().cancel(1);
                  Toast.makeText(DownloadService.this,"下载取消" ,Toast.LENGTH_LONG).show();
              }
          }
      }
          }
    @Override
    public IBinder onBind(Intent intent) {
    return mBinder;
    }
}
