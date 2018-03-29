package com.example.administrator.kenryoweb.Display;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.administrator.kenryoweb.Model.Data.Data;
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
        DataMeasure data = (DataMeasure) d;

        if (!TextUtils.isEmpty(data.ErrMsg)) {
            msg_text.setText(data.ErrMsg);
            return false;
        }
        //todo 風袋云々、差分を計算して表示
        mes_text.setText(data.MES);
        return true;
    }

    @Override
    public void showTimeoutMessage() {
        msg_text.setText(Constants.MSG_TIMEOUT);
    }
}
