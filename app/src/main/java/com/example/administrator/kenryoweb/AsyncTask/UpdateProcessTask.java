package com.example.administrator.kenryoweb.AsyncTask;

import com.example.administrator.kenryoweb.Util.Init;
import com.example.administrator.kenryoweb.View.MainActivity;

/**
 * Created by Administrator on 2018/03/27.
 */

public class UpdateProcessTask extends AbstractAsyncTask {
    private MainActivity activity;

    public UpdateProcessTask(MainActivity activity, String urlStr, String requestMethod) {
        super(activity, urlStr, requestMethod);
        this.activity = activity;
    }

    @Override
    public void applyDataToScreen(String result) {
        Init.initPage(activity);
    }

    @Override
    public void afterTimeoutProcess() {

    }
}
