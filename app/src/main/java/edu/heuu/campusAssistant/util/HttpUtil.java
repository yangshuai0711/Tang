package edu.heuu.campusAssistant.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by 文静美丽小TeTe on 2018/5/6.
 */
// curl -d '{}' http://118.24.23.203:9000/api/hello

public class HttpUtil {
    public String post(String url, String str_data){
        // 测试服务器所在的项目URL
        HttpPost postRequest = new HttpPost(url);

        // 构造请求串
        StringEntity entity = null;
        try {
            entity = new StringEntity(str_data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        HttpClient client = new DefaultHttpClient();
        postRequest.setEntity(entity);
        HttpResponse response;
        try {
            response = client.execute(postRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String entityString;
        try {
            entityString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return entityString;
    }
}
