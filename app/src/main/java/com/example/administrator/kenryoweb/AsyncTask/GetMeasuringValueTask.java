package com.example.administrator.kenryoweb.AsyncTask;

import android.text.TextUtils;

import com.example.administrator.kenryoweb.Display.Display;
import com.example.administrator.kenryoweb.Display.MeasuringValueDisplay;
import com.example.administrator.kenryoweb.Model.Data.DataKenryo;
import com.example.administrator.kenryoweb.Model.Data.DataMeasure;
import com.example.administrator.kenryoweb.View.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetMeasuringValueTask extends AbstractAsyncTask {
    private MainActivity activity;
    private Display display;

    public GetMeasuringValueTask(MainActivity activity, String urlStr, String requestMethod) {
        super(activity, urlStr, requestMethod, false);
        this.activity = activity;
        this.display = new MeasuringValueDisplay(activity);
    }

    @Override
    public void applyDataToScreen(String result) {
        //受信したJsonデータを加工して、画面に反映させる
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataMeasure dataMeasure = mapper.readValue(result, DataMeasure.class);

            //todo スッキリさせる
            //計量値が取得できていれば、DataKenryoに追加する
            if (TextUtils.isEmpty(dataMeasure.MES)) {
                return;
            }
            DataKenryo d = activity.getDataKenryo();
            d.MES = dataMeasure.MES;

            //画面に反映する
            if (display.showData(d)) {
                //データを保持
                activity.setDataKenryo(d);
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
