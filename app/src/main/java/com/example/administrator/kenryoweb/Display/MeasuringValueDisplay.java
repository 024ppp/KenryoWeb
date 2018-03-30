package com.example.administrator.kenryoweb.Display;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.administrator.kenryoweb.Model.Data.Data;
import com.example.administrator.kenryoweb.Model.Data.DataKenryo;
import com.example.administrator.kenryoweb.Model.Data.DataMeasure;
import com.example.administrator.kenryoweb.R;
import com.example.administrator.kenryoweb.Util.Constants;

public class MeasuringValueDisplay extends Display {
    private TextView mes_text, dif_text, msg_text;

    public MeasuringValueDisplay(Activity activity) {
        this.mes_text = activity.findViewById(R.id.mes_text);
        this.dif_text = activity.findViewById(R.id.dif_text);
        //msg
        this.msg_text = activity.findViewById(R.id.msg_text);
    }

    @Override
    public boolean showData(Data d) {
        DataKenryo data = (DataKenryo) d;

        if (!TextUtils.isEmpty(data.ErrMsg)) {
            msg_text.setText(data.ErrMsg);
            return false;
        }

        //計量値と差分を表示
        mes_text.setText(data.MES);
        dif_text.setText(data.DIF);
        return true;
    }

    @Override
    public void showTimeoutMessage() {
        msg_text.setText(Constants.MSG_TIMEOUT);
    }

}
