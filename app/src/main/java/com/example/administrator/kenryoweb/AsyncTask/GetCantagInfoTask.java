package com.example.administrator.kenryoweb.AsyncTask;

import com.example.administrator.kenryoweb.Display.CantagDisplay;
import com.example.administrator.kenryoweb.Display.Display;
import com.example.administrator.kenryoweb.Model.Data.DataCantag;
import com.example.administrator.kenryoweb.Model.Data.DataKenryo;
import com.example.administrator.kenryoweb.Util.Util;
import com.example.administrator.kenryoweb.View.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Administrator on 2018/03/20.
 */

public class GetCantagInfoTask extends AbstractAsyncTask {
    private MainActivity activity;
    private Display display;
    private Util util;

    public GetCantagInfoTask(MainActivity activity, String urlStr, String requestMethod, Util util) {
        super(activity, urlStr, requestMethod, true);
        this.activity = activity;
        this.util = util;
        this.display = new CantagDisplay(activity);
    }

    @Override
    public void applyDataToScreen(String result) {
        //受信したJsonデータを加工して、画面に反映させる
        try {
            //缶タグデータをパース
            ObjectMapper mapper = new ObjectMapper();
            DataCantag dataCantag = mapper.readValue(result, DataCantag.class);
            //エラーチェック
            if (util.isErrorOccurred(dataCantag)) {
                return;
            }

            //Dataを引っ張ってきて、缶タグデータをプラス
            DataKenryo d = activity.getDataKenryo();
            d.PC01_CANNO.add(dataCantag.PC01_CANNO);
            d.PC01_CANKBN.add(dataCantag.PC01_CANKBN);

            //画面に反映する
            if (!display.showData(d)) {
                return;
            }

            //データを保持
            activity.setDataKenryo(d);

            //1つ目の缶タグ取得後に、計量値定期取得を開始する
            if (d.isCanTagOnlyOne()) {
                util.startGetMeasuringValueRegularly();
            }
        }
        catch (Exception ex) {

        }
    }

    @Override
    public void afterTimeoutProcess() {
        display.showTimeoutMessage();
    }
}
