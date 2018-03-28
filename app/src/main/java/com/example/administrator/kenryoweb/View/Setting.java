package com.example.administrator.kenryoweb.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.kenryoweb.R;

public class Setting extends Activity implements View.OnClickListener {
    EditText txtIP;
    Button btn;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        txtIP = findViewById(R.id.txtIP);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);

        //接続先を取得
        prefs = getSharedPreferences("ConnectionData", Context.MODE_PRIVATE);
        String ip = prefs.getString("ip", "");

        txtIP.setText(ip);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    //クリック処理の実装
    public void onClick(View v) {

        // 返すデータ(Intent&Bundle)の作成
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("key.StringData", "DATA");
        //bundle.putInt("key.intData", 123456789);
        data.putExtras(bundle);

        //Setting
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ip", txtIP.getText().toString());
        editor.apply();

        // setResult() で bundle を載せた
        // 送るIntent dataをセットする

        // 第一引数は…Activity.RESULT_OK,
        // Activity.RESULT_CANCELED など
        setResult(RESULT_OK, data);

        // finish() で終わらせて
        // Intent data を送る
        finish();
    }

}
