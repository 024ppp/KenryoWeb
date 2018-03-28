package com.example.administrator.kenryoweb.Display;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.kenryoweb.Model.Data.Data;
import com.example.administrator.kenryoweb.Model.Data.DataKenryo;
import com.example.administrator.kenryoweb.R;
import com.example.administrator.kenryoweb.Util.Constants;

/**
 * Created by Administrator on 2018/03/13.
 */

public class KokanInfoDisplay extends Display{
    private EditText ido_text;
    private TextView zainmk_text, set_text, msg_text;

    public KokanInfoDisplay(Activity activity) {
        this.ido_text = activity.findViewById(R.id.ido_text);
        this.zainmk_text = activity.findViewById(R.id.zainmk_text);
        this.set_text = activity.findViewById(R.id.set_text);
        //msg
        this.msg_text = activity.findViewById(R.id.msg_text);
    }

    @Override
    public boolean showData(Data d) {
        DataKenryo data = (DataKenryo) d;

        if (!TextUtils.isEmpty(data.ErrMsg)) {
            ido_text.setText("");
            msg_text.setText(data.ErrMsg);
            return false;
        }

        ido_text.setText(data.KOKBAN);
        zainmk_text.setText(data.ZAINMK);
        set_text.setText(data.SET);
        msg_text.setText(Constants.MSG_CAN_STR);

        ido_text.setFocusable(false);
        return true;
    }

    @Override
    public void showTimeoutMessage() {
        ido_text.setText("");
        msg_text.setText(Constants.MSG_TIMEOUT);
    }
}
