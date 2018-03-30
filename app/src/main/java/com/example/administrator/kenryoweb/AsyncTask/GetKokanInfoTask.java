package com.example.administrator.kenryoweb.AsyncTask;


import com.example.administrator.kenryoweb.Display.Display;
import com.example.administrator.kenryoweb.Display.KokanInfoDisplay;
import com.example.administrator.kenryoweb.Model.Data.DataKenryo;
import com.example.administrator.kenryoweb.View.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Administrator on 2018/03/16.
 */

public class GetKokanInfoTask extends AbstractAsyncTask {
    private MainActivity activity;
    private Display display;

    public GetKokanInfoTask(MainActivity activity, String urlStr, String requestMethod) {
        super(activity, urlStr, requestMethod, true);
        this.activity = activity;
        this.display = new KokanInfoDisplay(activity);
    }

    @Override
    public void applyDataToScreen(String result) {
        //受信したJsonデータを加工して、画面に反映させる
        try {
            //なぜかサーバー側からレスポンスがずっと返ってくる...
            //が、↓のように条件分岐を入れたら解決した。謎である
            DataKenryo dataKenryo = activity.getDataKenryo();
            //レスポンスを受信済みであったら処理しない
            if (dataKenryo != null) {
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            DataKenryo d = mapper.readValue(result, DataKenryo.class);
            //画面に反映する
            if (display.showData(d)) {
                //データを保持
                activity.setDataKenryo(d);
            }
        }
        catch (IOException ex) {

        }

    }

    @Override
    public void afterTimeoutProcess() {
        display.showTimeoutMessage();
    }
}
