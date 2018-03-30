package com.example.administrator.kenryoweb.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.kenryoweb.AsyncTask.GetCantagInfoTask;
import com.example.administrator.kenryoweb.AsyncTask.GetKokanInfoTask;
import com.example.administrator.kenryoweb.AsyncTask.GetMeasuringValueTask;
import com.example.administrator.kenryoweb.AsyncTask.UpdateProcessTask;
import com.example.administrator.kenryoweb.Model.Data.Data;
import com.example.administrator.kenryoweb.Model.Data.DataKenryo;
import com.example.administrator.kenryoweb.R;
import com.example.administrator.kenryoweb.View.MainActivity;

/**
 * Created by Administrator on 2018/03/27.
 */

public class Util {
    private MainActivity activity;
    private TextView msg_text;
    private String ip;

    //計量値定期取得タスクにて使用
    private Handler handler;
    private Runnable runnable;

    public Util(MainActivity activity) {
        this.activity = activity;
        this.msg_text = activity.findViewById(R.id.msg_text);

        //接続先サーバのIPアドレス(URI)を取得
        SharedPreferences prefs = activity.getSharedPreferences("ConnectionData", Context.MODE_PRIVATE);
        ip = "http://" + prefs.getString("ip", "");
    }

    //サーバーでエラーが起こったかどうかをチェック
    public boolean isErrorOccurred(Data d) {
        if (TextUtils.isEmpty(d.ErrMsg)) {
            return false;
        }

        if (this.msg_text == null) {
            return false;
        }

        msg_text.setText(d.ErrMsg);
        return true;
    }

    //メッセージ表示
    public void showMessage(String msg) {
        msg_text.setText(msg);
    }

    //リクエスト先URI作成
    private String createURI(String act) {
        String uri = ip;

        switch (act) {
            case "GET":
                uri += Constants.URI_GET;
                break;
            case "CAN":
                uri += Constants.URI_CAN;
                break;
            case "MES":
                uri += Constants.URI_MES;
                break;
            case "POST":
                uri += Constants.URI_POST;
                break;
            default:
                uri = "";
                break;
        }
        return uri;
    }

    //Listener追加
    public void addListener() {
        //---EditText---
        //バーコードリーダー対応
        final EditText e = activity.findViewById(R.id.ido_text);
        e.addTextChangedListener(new TextWatcher(){
            EditText editText = e;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().length() >= 6) {
                    //工程管理Noが6文字以上になったら、工程管理番号問い合わせをサーバーに送信する
                    String urlStr = createURI("GET") + editText.getText().toString();
                    GetKokanInfoTask task = new GetKokanInfoTask(activity, urlStr, "GET");
                    task.execute();
                    //キーボードをしまう
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        //---Button---
        //Clear
        activity.findViewById(R.id.clear_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog(OK,Cancel Ver.)
                new android.app.AlertDialog.Builder(activity)
                        .setTitle("確認")
                        .setMessage("クリアしてよろしいですか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // OK button pressed
                                Init.initPage(activity);
                                //計量値自動取得を中止
                                stopGetMeasuringValue();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        //Upd
        activity.findViewById(R.id.upd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog(OK,Cancel Ver.)
                new android.app.AlertDialog.Builder(activity)
                        .setTitle("確認")
                        .setMessage("登録しますか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // OK button pressed
                                sendUpdateRequest();
                                //計量値自動取得を中止
                                //todo 更新が失敗した時に再開させるべきか...不要な気がするので未着手
                                stopGetMeasuringValue();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    //更新リクエスト本処理
    private void sendUpdateRequest() {
        DataKenryo dataKenryo = activity.getDataKenryo();
        try {
            String urlStr = createURI("POST");
            UpdateProcessTask task = new UpdateProcessTask(activity, urlStr, "POST");
            //送信データ作成
            String json = JsonConverter.toString(dataKenryo);
            task.execute(json);
        }
        catch (Exception ex) {
        }
    }

    //缶タグ追加処理
    public void addCantag(DataKenryo dataKenryo, String tag) {
        //缶タグが追加可能かチェック
        if (!canAddCantag(dataKenryo, tag)) {
            return;
        }
        //スキャンした缶タグの缶区分を取得
        sendCanTagRequest(tag);
    }

    //缶タグ追加可能かチェック
    private boolean canAddCantag(DataKenryo dataKenryo, String tag) {
        //工管番号リード以降でないと処理できない
        if (dataKenryo == null) {
            return false;
        }

        //空チェック
        if (TextUtils.isEmpty(tag)) {
            showMessage(Constants.MSG_TAG_EMPTY);
            return false;
        }

        //最大数スキャン済みかどうかチェック
        if (dataKenryo.isCanTagMaxCount()) {
            showMessage(Constants.MSG_CAN_MAX);
            return false;
        }

        //缶タグスキャン済みチェック
        if (dataKenryo.isThisCanTagScanned(tag)) {
            //スキャン済み
            showMessage(tag + Constants.MSG_CAN_SCANNED);
            return false;
        }
        return true;
    }

    //缶区分取得リクエスト送受信タスクを作成
    private void sendCanTagRequest(String tag) {
        try {
            String q = "?can=" + tag;
            String urlStr = createURI("CAN") + q;
            GetCantagInfoTask task = new GetCantagInfoTask(activity, urlStr, "GET", this);
            task.execute();
        }
        catch (Exception ex) {
        }
    }

    //計量値定期取得を開始
    public void startGetMeasuringValueRegularly() {
        this.handler = new Handler();
        this.runnable = new Runnable() {
            @Override
            public void run() {
                //計量値取得タスク開始
                String urlStr = createURI("MES");
                GetMeasuringValueTask task = new GetMeasuringValueTask(activity, urlStr, "GET");
                task.execute();
                //定期実行の間隔を指定
                handler.postDelayed(this, Constants.INTERVAL_MES);
            }
        };
        handler.post(runnable);
    }

    //定期取得停止（クリア、登録ボタン押下時に実行）
    private void stopGetMeasuringValue() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
    }

    //取得した計量値を表示、設定値での判定を行う
    public void checkMeasuringValue(String info) {
        DataKenryo dataKenryo = activity.getDataKenryo();
        double min = Double.parseDouble(dataKenryo.MD03_SYJRY) * 0.9;
        double max = Double.parseDouble(dataKenryo.MD03_SYJRY) * 1.1;
        double value = Double.valueOf(info);

        //計量値を判定。よければ登録ボタン有効化
//        if (min < value && value < max) {
//            txtKeiryo.setBackground(ContextCompat.getDrawable(this, R.drawable.ok));
//        }
//        else if (min > value) {
//            txtKeiryo.setBackground(ContextCompat.getDrawable(this, R.drawable.low));
//            btnUpd.setEnabled(false);
//        }
//        else if (value > max) {
//            txtKeiryo.setBackground(ContextCompat.getDrawable(this, R.drawable.high));
//            btnUpd.setEnabled(false);
//        }
//        txtKeiryo.setText(info);
    }
}
