package com.test.testmvp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.testmvp.listener.OnDownloadListener;
import com.test.testmvp.model.DownloadFileModel;
import com.test.testmvp.presenter.DownloadPresenterImpl;
import com.test.testmvp.view.IDownloadView;

public class MainActivity extends AppCompatActivity implements IDownloadView {

    //文件下载地址，可能已失效
    private static final String DOWNLOAD_FILE_URL = "http://do.xiazaiba.com/route.php?ct=stat&ac=stat&g=aHR0cDovL2FwcHMud2FuZG91amlhLmNvbS9yZWRpcmVjdD9zaWduYXR1cmU9YzkxNGQ2OSZ1cmw9aHR0cCUzQSUyRiUyRmRvd25sb2FkLmVvZW1hcmtldC5jb20lMkZhcHAlM0ZjaGFubmVsX2lkJTNEMTAwJTI2Y2xpZW50X2lkJTI2aWQlM0QyNDQwMDMmcG49Y29tLnVzb2Z0LmFwcC51aSZtZDU9OTI4MWIxZjU1YmM1YzExNmYyMThmZjYwY2FhZWEwMmImYXBraWQ9MTQyODY1ODgmdmM9MTEmc2l6ZT0zNDE0MTEw";
    //下载的文件名
    private static final String DOWNLOAD_FILE_NAME = "test.apk";
    //下载的文件存放路径
    private static final String DOWNLOAD_FILE_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    //进度条控件
    private ProgressBar mProgressBar;

    //处理下载的Presenter
    private DownloadPresenterImpl downloadPresenter;
    //下载的文件Model
    private DownloadFileModel downloadFileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        downloadFileModel = new DownloadFileModel(DOWNLOAD_FILE_NAME, DOWNLOAD_FILE_SAVE_PATH);
        downloadPresenter = new DownloadPresenterImpl(this);
    }

    @Override
    public void initViews() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    //处理下载按钮点击事件
    public void handleDownloadBtnClick(View view) {
        downloadPresenter.startDownload(DOWNLOAD_FILE_URL, downloadFileModel.getFile(), new OnDownloadListener() {
            @Override
            public void onStartDownload() {
                showProgressBar();
            }

            @Override
            public void onProgressUpdate(int progress) {
                updateProgressBar(progress);
            }

            @Override
            public void onDownloadSuccess() {
                hideProgressBar();
                showToast("download success, path: " + downloadFileModel.getFilePath());
            }

            @Override
            public void onDownloadError(Exception e) {
                hideProgressBar();
                showToast(e.getMessage());
            }
        });
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateProgressBar(int progress) {
        mProgressBar.setProgress(progress);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
