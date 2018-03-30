package com.example.administrator.kenryoweb.AsyncTask;

import android.text.TextUtils;

import com.example.administrator.kenryoweb.Display.Display;
import com.example.administrator.kenryoweb.Display.MeasuringValueDisplay;
import com.example.administrator.kenryoweb.Model.Data.DataKenryo;
import com.example.administrator.kenryoweb.Model.Data.DataMeasure;
import com.example.administrator.kenryoweb.Util.Constants;
import com.example.administrator.kenryoweb.Util.Util;
import com.example.administrator.kenryoweb.View.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetMeasuringValueTask extends AbstractAsyncTask {
    private MainActivity activity;
    private Display display;
    private Util util;

    public GetMeasuringValueTask(MainActivity activity, String urlStr, String requestMethod, Util util) {
        super(activity, urlStr, requestMethod, false);
        this.activity = activity;
        this.util = util;
        this.display = new MeasuringValueDisplay(activity);
    }

    @Override
    public void applyDataToScreen(String result) {
        //受信したJsonデータを加工して、画面に反映させる
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataMeasure dataMeasure = mapper.readValue(result, DataMeasure.class);
            //エラーチェック
            if (util.isErrorOccurred(dataMeasure)) {
                return;
            }

            //レスポンスを反映して、データ作成
            DataKenryo d = createDataKenryo(dataMeasure.MES);

            //画面に反映する
            if (display.showData(d)) {
                //データを保持
                activity.setDataKenryo(d);
            }
        }
        catch (Exception ex) {

        }

    }

    //主なタスクは計量値と差分の算出である
    private DataKenryo createDataKenryo(String value) {
        DataKenryo dataKenryo = activity.getDataKenryo();
        //小数点以下は切り捨てを想定
        int set = (int)Double.parseDouble(dataKenryo.MD03_SYJRY);   //設定値
        int mes = (int)Double.parseDouble(value);                   //計量値
        int dif;                                                    //差分
        int containerWeight = 0;                                    //風袋重量

        //風袋重量を算出
        for (String kbn : dataKenryo.PC01_CANKBN) {
            containerWeight += getContainerWeight(kbn);
        }

        //風袋重量を引いた値を算出
        mes = mes - containerWeight;
        //差分を算出
        dif = mes - set;

        //データに反映
        dataKenryo.MES = String.valueOf(mes);
        dataKenryo.DIF = String.valueOf(dif);
        return dataKenryo;
    }

    //缶区分に応じた風袋重量を返す
    private int getContainerWeight(String kbn) {
        switch (kbn) {
            case "1":
                return Constants.WEIGHT_KBN_1;
            case "2":
                return Constants.WEIGHT_KBN_2;
            case "3":
                return Constants.WEIGHT_KBN_3;
            case "4":
                return Constants.WEIGHT_KBN_4;
            case "5":
                return Constants.WEIGHT_KBN_5;
            default:
                return 0;
        }
    }

    @Override
    public void afterTimeoutProcess() {
        display.showTimeoutMessage();
    }
}
