package com.example.administrator.kenryoweb.Util;

/**
 * Created by Administrator on 2018/03/27.
 */

public final class Constants {
    //String
    public static final String hoge = "";
    public static final String MSG_STR = "工管番号をスキャンしてください。";
    public static final String MSG_CAN_STR = "缶タグをタッチしてください。";
    public static final String MSG_CAN_NEXT = "次の缶タグをタッチするか、\n検量した後、登録してください。";
    public static final String MSG_CAN_FIN = "全てＯＫです。\n登録してください。";
    public static final String MSG_CAN_MAX = "最大数スキャン済みです。\n検量した後、登録してください。";
    public static final String MSG_CAN_SCANNED = "はスキャン済みです。";
    public static final String MSG_TAG_EMPTY = "タグテキストが空です。";
    public static final String MSG_EMPTY = "レスポンスデータが空白です。";
    public static final String MSG_TIMEOUT = "接続がタイムアウトしました。\n再試行してください。";
    public static final String MSG_SERVER_ERR = "サーバーとの通信が行われていません。";

    public static final String URI_GET = "/WebAPI_Koyo_C/kenryo/get/";
    public static final String URI_CAN = "/WebAPI_Koyo_C/kenryo/can/";
    public static final String URI_MES = "/WebAPI_Koyo_C/kenryo/mes";
    public static final String URI_POST = "/WebAPI_Koyo_C/api/kenryo";

    public static final String STR_EMPTY = "TIMEOUT";
    public static final String STR_TIMEOUT = "TIMEOUT";
    public static final String STR_SERVER_ERR = "SERVER_ERR";

    //long
    public static final long VIB_READ[] = {0, 200};
    public static final long VIB_ERROR[] = {0, 500, 200, 500};
    public static final long INTERVAL_MES = 1000;

    //int
    public static final int TIMEOUT_MILLSEC = 10000;
    public static final int CNT_CAN_MAX = 6;
    public static final int SETTING = 8888;
    public static final int WEIGHT_KBN_1 = 100;
    public static final int WEIGHT_KBN_2 = 200;
    public static final int WEIGHT_KBN_3 = 300;
    public static final int WEIGHT_KBN_4 = 400;
    public static final int WEIGHT_KBN_5 = 500;

    private Constants (){}
}
