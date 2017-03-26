package com.test.testmvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.test.testmvp.listener.OnDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yubo on 2017/3/26.
 */

public class DownloadPresenterImpl implements IDownloadPresenter {

    private Context mContext;

    public DownloadPresenterImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void startDownload(final String urlStr, final File saveFile, final OnDownloadListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("OnDownloadListener should not be null!");
        }
        listener.onStartDownload();
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    URL url = new URL(urlStr);
                    URLConnection conn = url.openConnection();
                    inputStream = conn.getInputStream();
                    fileOutputStream = new FileOutputStream(saveFile);
                    int totalSize = inputStream.available();
                    int hasDownload = 0;
                    int hasRead = 0;
                    byte[] buf = new byte[512];
                    while ((hasRead = inputStream.read(buf)) > 0) {
                        fileOutputStream.write(buf, 0, hasRead);
                        hasDownload += hasRead;
                        final int progress = (int) (hasDownload * 100.0f / totalSize);
                        listener.onProgressUpdate(progress);
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onProgressUpdate(progress);
                            }
                        });
                    }
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadSuccess();
                        }
                    });
                } catch (final MalformedURLException e) {
                    e.printStackTrace();
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadError(e);
                        }
                    });
                } catch (final IOException e) {
                    e.printStackTrace();
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadError(e);
                        }
                    });
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

}
