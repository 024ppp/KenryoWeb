package com.example.administrator.kenryoweb.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.kenryoweb.Util.Constants;
import com.example.administrator.kenryoweb.View.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Administrator on 2018/03/16.
 */

public abstract class AbstractAsyncTask extends AsyncTask<String, String, String> {
    private MainActivity activity;
    private String urlStr;
    private String requestMethod;
    ProgressDialog dialog;

    public AbstractAsyncTask(MainActivity activity, String urlStr, String requestMethod) {
        this.activity = activity;
        this.urlStr = urlStr;
        this.requestMethod = requestMethod;
    }

    public abstract void applyDataToScreen(String result);
    public abstract void afterTimeoutProcess();

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("サーバー問い合わせ中...");
        dialog.show();
    }

    @Override
    public String doInBackground(String... params) {
        HttpURLConnection con = null;
        InputStream is = null;
        String result = "";

        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);
            con.setConnectTimeout(Constants.TIMEOUT_MILLSEC);

            //HTTPリクエストメソッドで分岐
            switch (requestMethod) {
                case "GET":
                    con.connect();
                    break;
                case "POST":
                    OutputStream out = null;
                    String param = params[0];
                    try {
                        //↓このへんなぞ
                        con.setInstanceFollowRedirects(false);
                        con.setRequestProperty("Accept-Language", "jp");
                        con.setDoOutput(true);
                        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                        con.connect();

                        out = con.getOutputStream();
                        out.write( param.getBytes("UTF-8") );
                        out.flush();
                        Log.d("debug","flush");
                    } catch (IOException e) {
                        // POST送信エラー
                        e.printStackTrace();
                        result = "POST送信エラー";
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                    break;
            }

            is = con.getInputStream();
            result = convertResponseToString(is);
        }
        catch (SocketTimeoutException st_ex) {
            result = Constants.STR_TIMEOUT;
            Log.d("test", st_ex.getMessage());
        }
        catch (Exception ex) {
        }
        finally {
            if (con != null) {
                con.disconnect();
            }
            if(is != null) {
                try {
                    is.close();
                }
                catch(IOException ex) {
                }
            }
        }
        return result;
    }

    @Override
    public void onPostExecute(String result) {
        try {
            //空文字の返答
            if (TextUtils.isEmpty(result)) {
                return;
            }
            //結果がTIMEOUT文字列だった場合
            if (result.equals(Constants.STR_TIMEOUT)) {
                afterTimeoutProcess();
                return;
            }
            //画面に反映
            applyDataToScreen(result);
        }
        finally {
            dialog.dismiss();
        }
    }

    // レスポンスデータをStringデータに変換する
    private String convertResponseToString(InputStream is) throws IOException, UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        while((st = br.readLine()) != null) {
            sb.append(st);
        }

        try {
            is.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
