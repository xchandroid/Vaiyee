package com.example.administrator.uibestpractice;

/**
 * Created by Administrator on 2017/10/29.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
