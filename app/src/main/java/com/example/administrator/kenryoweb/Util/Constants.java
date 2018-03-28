package com.example.administrator.kenryoweb.Util;

/**
 * Created by Administrator on 2018/03/27.
 */

public final class Constants {
    //String
    public static final String hoge = "";
    public static final String MSG_STR = "工管番号をスキャンしてください。";
    public static final String MSG_CAN_STR = "缶タグをタッチしてください。";
    public static final String MSG_CAN_NEXT = "次の缶タグをタッチするか、\n登録してください。";
    public static final String MSG_CAN_FIN = "全てＯＫです。\n登録してください。";
    public static final String MSG_CAN_MAX = "最大数スキャン済みです。\n登録してください。";
    public static final String MSG_CAN_SCANNED = "はスキャン済みです。";
    public static final String MSG_TIMEOUT = "接続がタイムアウトしました。\n再試行してください。";

    public static final String URI_GET = "/WebAPI_Koyo_C/hikiate/get/";
    public static final String URI_CHECK = "/WebAPI_Koyo_C/hikiate/check/";
    public static final String URI_POST = "/WebAPI_Koyo_C/api/hikiate";

    public static final String STR_TIMEOUT = "TIMEOUT";

    //long
    public static final long VIB_READ[] = {0, 200};
    public static final long VIB_ERROR[] = {0, 500, 200, 500};

    //int
    public static final int TIMEOUT_MILLSEC = 5000;
    public static final int CNT_CAN_MAX = 6;
    public static final int SETTING = 8888;

    private Constants (){}
}
