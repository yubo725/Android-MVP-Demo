package com.test.testmvp.view;

/**
 * Created by yubo on 2017/3/26.
 * view层的抽象接口，需要Activity去实现该接口（所以Activity当作view看待）
 */

public interface IDownloadView {

    void initViews();
    void showProgressBar();
    void hideProgressBar();
    void updateProgressBar(int progress);
    void showToast(String msg);

}
