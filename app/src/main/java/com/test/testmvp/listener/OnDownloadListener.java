package com.test.testmvp.listener;

/**
 * Created by yubo on 2017/3/26.
 * 下载的监听器，处理下载的不同阶段
 */

public interface OnDownloadListener {

    void onStartDownload();
    void onProgressUpdate(int progress);
    void onDownloadSuccess();
    void onDownloadError(Exception e);

}
