package com.example.administrator.kenryoweb.Util;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.kenryoweb.R;
import com.example.administrator.kenryoweb.View.MainActivity;

/**
 * Created by Administrator on 2018/03/27.
 */

public final class Init {
    public static void initPage(MainActivity activity) {
        //EditText
        //focus
        EditText ido_text = activity.findViewById(R.id.ido_text);
        ido_text.setFocusableInTouchMode(true);
        ido_text.setFocusable(true);
        ido_text.requestFocus();
        ido_text.setText("");

        //TextView
        TextView[] arr_textview = new TextView[] {activity.findViewById(R.id.zainmk_text)
                                                , activity.findViewById(R.id.set_text)
                                                , activity.findViewById(R.id.mes_text)
                                                , activity.findViewById(R.id.dif_text)
                                                , activity.findViewById(R.id.c1)
                                                , activity.findViewById(R.id.c2)
                                                , activity.findViewById(R.id.c3)
                                                , activity.findViewById(R.id.c4)
                                                , activity.findViewById(R.id.c5)
                                                , activity.findViewById(R.id.c6)};
        for (TextView t : arr_textview) { t.setText(""); }

        //msg
        TextView msg_text = activity.findViewById(R.id.msg_text);
        msg_text.setText(Constants.MSG_STR);

        //Button
        Button upd_btn = activity.findViewById(R.id.upd_btn);
        upd_btn.setEnabled(false);

        //DataKenryo
        activity.setDataKenryo(null);
    }
}
