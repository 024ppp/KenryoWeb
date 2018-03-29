package com.example.administrator.kenryoweb.Model.Data;

import com.example.administrator.kenryoweb.Util.Constants;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/03/20.
 */

public class DataKenryo extends Data {
    //工管番号で取得する分
    public String KOKBAN = "";
    public String MD01_ZAINMK = "";
    public String MD03_SYJRY = "";
    public String MES = "";
    public String DIF = "";

    //缶タグ情報（缶数分）
    public ArrayList<String> PC01_CANNO = new ArrayList<String>();
    public ArrayList<String> PC01_CANKBN = new ArrayList<String>();

    //缶タグスキャン済みチェック
    public boolean isThisCanTagScanned(String canno) {
        for (String c : PC01_CANNO) {
            if (c.equals(canno)) {
                return true;
            }
        }
        return false;
    }

    //缶タグが最大数スキャンされたかどうかチェック
    public boolean isCanTagMaxCount() {
        if (PC01_CANNO.size() >= Constants.CNT_CAN_MAX) {
            return true;
        }
        return false;
    }

    //缶タグが一つかどうかチェック
    public boolean isCanTagOnlyOne() {
        if (PC01_CANNO.size() == 1) {
            return true;
        }
        return false;
    }
}
