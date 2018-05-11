package edu.heuu.campusAssistant.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Œƒæ≤√¿¿ˆ–°TeTe on 2018/5/6.
 */

public class TangJson {
    private JSONObject jv;
    public TangJson(String str){
        try {
            jv = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            jv = new JSONObject();
        }
    }

    public TangJson(){
        jv = new JSONObject();
    }

    public TangJson(JSONObject je){
        jv = je;
    }

    public String toString (){
        return jv.toString();
    }

    public JSONObject put(String k, String v){
        try {
            jv.put(k, v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jv;
    }

    public int getInt(String k){
        try {
            int r = jv.getInt(k);
            return r;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getDouble(String k){
        try {
            double r = jv.getDouble(k);
            return r;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getString(String k){
        try {
            String str = jv.getString(k);
            return str;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public TangJson getTangJsonbject(String k){
        try {
            JSONObject je = jv.getJSONObject(k);
            TangJson jt = new TangJson(je);
            return jt;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
