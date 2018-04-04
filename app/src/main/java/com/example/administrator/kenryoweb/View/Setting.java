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
import com.example.administrator.kenryoweb.Util.Constants;

public class Setting extends Activity implements View.OnClickListener {
    EditText IP_text, cw1_text, cw2_text, cw3_text, cw4_text, cw5_text;
    Button btn;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        prefs = getSharedPreferences("ConnectionData", Context.MODE_PRIVATE);

        IP_text = findViewById(R.id.IP_text);
        cw1_text = findViewById(R.id.cw1_text);
        cw2_text = findViewById(R.id.cw2_text);
        cw3_text = findViewById(R.id.cw3_text);
        cw4_text = findViewById(R.id.cw4_text);
        cw5_text = findViewById(R.id.cw5_text);

        //表示
        IP_text.setText(prefs.getString("ip", ""));
        cw1_text.setText(String.valueOf(prefs.getInt("cw1", Constants.WEIGHT_KBN_1)));
        cw2_text.setText(String.valueOf(prefs.getInt("cw2", Constants.WEIGHT_KBN_2)));
        cw3_text.setText(String.valueOf(prefs.getInt("cw3", Constants.WEIGHT_KBN_3)));
        cw4_text.setText(String.valueOf(prefs.getInt("cw4", Constants.WEIGHT_KBN_4)));
        cw5_text.setText(String.valueOf(prefs.getInt("cw5", Constants.WEIGHT_KBN_5)));

        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
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
        editor.putString("ip", IP_text.getText().toString());
        editor.putInt("cw1", Integer.parseInt(cw1_text.getText().toString()));
        editor.putInt("cw2", Integer.parseInt(cw2_text.getText().toString()));
        editor.putInt("cw3", Integer.parseInt(cw3_text.getText().toString()));
        editor.putInt("cw4", Integer.parseInt(cw4_text.getText().toString()));
        editor.putInt("cw5", Integer.parseInt(cw5_text.getText().toString()));

        //反映
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
