package com.example.administrator.kenryoweb.AsyncTask;

import com.example.administrator.kenryoweb.Model.Data.Data;
import com.example.administrator.kenryoweb.Model.Data.DataCantag;
import com.example.administrator.kenryoweb.Model.Data.DataKenryo;
import com.example.administrator.kenryoweb.Util.Init;
import com.example.administrator.kenryoweb.Util.Util;
import com.example.administrator.kenryoweb.View.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Administrator on 2018/03/27.
 */

public class UpdateProcessTask extends AbstractAsyncTask {
    private MainActivity activity;
    private Util util;

    public UpdateProcessTask(MainActivity activity, String urlStr, String requestMethod, Util util) {
        super(activity, urlStr, requestMethod, true);
        this.activity = activity;
        this.util = util;
    }

    @Override
    public void applyDataToScreen(String result) {
        try {
            //缶タグデータをパース
            ObjectMapper mapper = new ObjectMapper();
            DataKenryo d = mapper.readValue(result, DataKenryo.class);
            //エラーチェック
            if (util.isErrorOccurred(d)) {
                //計量値定期取得はボタン押下時に停止してあるので、再開させる
                util.startGetMeasuringValueRegularly();
                return;
            }

            Init.initPage(activity);
        }
        catch (Exception ex) {

        }
    }

    @Override
    public void afterTimeoutProcess() {

    }
}
