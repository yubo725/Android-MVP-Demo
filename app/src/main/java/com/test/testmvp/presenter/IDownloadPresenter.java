package com.test.testmvp.presenter;

import com.test.testmvp.listener.OnDownloadListener;

import java.io.File;

/**
 * Created by yubo on 2017/3/26.
 */

public interface IDownloadPresenter {

    //下载文件的逻辑，由实现类去实现该方法，listener用于监听下载过程
    void startDownload(String urlStr, File saveFile, OnDownloadListener listener);

}
