package edu.heuu.campusAssistant.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Œƒæ≤√¿¿ˆ–°TeTe on 2018/5/6.
 */

public class TangClient{
    private String url_base;
    private HttpUtil cli;
    Context mc;
    public TangClient(Context c){
        url_base = "http://118.24.23.203:9000/api/";
        cli = new HttpUtil();
        mc = c;
    }

    public String post(String path, String req){
        String resp = cli.post(url_base + path, req);
        return resp;
    }

    public String login_phone(String phone, String passwd){
        TangJson jv = new TangJson();
        jv.put("phone", phone);
        jv.put("passwd", passwd);
        jv.put("ts", "123");
        jv.put("sign", "123");
        String req = jv.toString();
        String ret = post("account/login_phone", req);

        jv = new TangJson(ret);
        int ret_code = jv.getInt("ret");
        if (ret_code != 1){
            String err = jv.getTangJsonbject("data").getString("msg");
            return err;
        }

        String nonce = jv.getTangJsonbject("data").getString("nonce");
        setLocalToken(nonce);

        return "";
    }

    public String getLocalToken(){
        SharedPreferences settings = mc.getSharedPreferences("tang", mc.MODE_PRIVATE);
        String token = settings.getString("token", "");
        return token;
    }

    private void setLocalToken(String token){
        SharedPreferences settings = mc.getSharedPreferences("tang", mc.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", token);
        editor.commit();
    }
}
