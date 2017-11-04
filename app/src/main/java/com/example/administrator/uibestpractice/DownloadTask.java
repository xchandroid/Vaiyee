package com.example.administrator.uibestpractice;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/29.
 */

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    public static final int SUCCCESS=0;
    public static final int FAILED=1;
    public static final int PAUSED=2;
    public static final int CANCELED=3;
    private DownloadListener downloadListener;
    private boolean isCanceled= false;
    private boolean isPaused = false;
    private int lastProgress;
        public DownloadTask(DownloadListener downloadListener){
            this.downloadListener= downloadListener;
        };

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try{
            long downloadedLeng = 0 ;//记录已下载的文件长度
            String downloadUrl = strings[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String diretory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(diretory+fileName);
            if (file.exists())
            {
                downloadedLeng = file.length();
            }
            long contentLeng = getContentLength(downloadUrl);
            if (contentLeng==0)
            {
                return FAILED;
            }else if (contentLeng==downloadedLeng)
            {
                return SUCCCESS;
            }
            OkHttpClient okclient = new OkHttpClient();
            Request request = new Request.Builder()
            .addHeader("RANGE","bytes="+downloadedLeng+"-")
                    .url(downloadUrl)
            .build();
            Response reponse = okclient.newCall(request).execute();
            if (reponse != null)
            {
                is = reponse.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLeng);//跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len ;
                while ((len = is.read(b))!=-1)
                {
                    if (isCanceled)
                    {
                        return CANCELED;
                    }
                    else if (isPaused)
                    {
                        return PAUSED;
                    }else
                    {
                        total+= len;
                        savedFile.write(b,0,len);
                        int progress = (int)((total+downloadedLeng)*100/contentLeng);
                        publishProgress(progress);
                    }
                }
                reponse.body().close();
                return SUCCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (is!=null)
                {
                    is.close();
                }
                if (savedFile!= null)
                {
                    savedFile.close();
                }
                if(isCanceled&&file!=null)
                {
                    file.delete();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress>lastProgress)
        {
            downloadListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case SUCCCESS:
                downloadListener.onSuccess();break;
            case FAILED:
                downloadListener.onFailed();;break;
            case PAUSED:
                downloadListener.onPaused();break;
            case CANCELED:
                downloadListener.onCanceled();break;
            default: break;
        }
    }
    public void pauseDownload(){
    isPaused = true;
    }
    public void cancelDownload(){
        isCanceled = true;
    }
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient ok = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = ok.newCall(request).execute();
        if (response!=null&& response.isSuccessful())
        {
            long contentLength = response.body().contentLength();
            return contentLength;
        }
       return 0;
    }

}
