package edu.heuu.campusAssistant.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.TangClient;
import edu.heuu.campusAssistant.util.TangCrypt;
import edu.heuu.campusAssistant.util.TangJson;

/**
 * Created by 文静美丽小TeTe on 2018/5/5.
 */

public class RegActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);

        ((TextView)findViewById(R.id.reg_back))
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegActivity.this, LoginActivity.class);
                intent.setClass(RegActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ((TextView) findViewById(R.id.reg_text_verifycode))
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = getEditText(R.id.reg_edittext_phone);
                new VerifyCodeTask().execute(phone);
            }
        });


        ((Button) findViewById(R.id.reg_btn_reg))
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = getEditText(R.id.reg_edittext_phone);
                String verify_code = getEditText(R.id.reg_edittext_verifycode);
                String passwd = getEditText(R.id.reg_edittext_passwd);
                String re_passwd = getEditText(R.id.reg_edittext_repasswd);
                if(passwd.equals(re_passwd) == false ){
                    Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwd.length() < 6){
                    Toast.makeText(getApplicationContext(), "请设置六位以上的密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                String passwd_crypt = TangCrypt.crypt(passwd);
                String verify_code_md5 = TangCrypt.md5(verify_code);
                Log.d("XXXMD5", verify_code + ";" + verify_code_md5);
                new RegTask().execute(phone, verify_code_md5, passwd_crypt);
            }
        });
    }

    private String getEditText(int id){
        EditText edit = (EditText) findViewById(id);
        String str = edit.getText().toString();
        return str;
    }

    private class VerifyCodeTask extends AsyncTask<String, String, Integer> {
        protected Integer doInBackground(String... params) {
            String phone = params[0];

            TangClient cli = new TangClient(getApplicationContext());
            TangJson jv = new TangJson();
            jv.put("phone", phone);

            String ret = cli.post("account/reg_verify", jv.toString());
            jv = new TangJson(ret);
            int ret_code = jv.getInt("ret");

            if(ret_code == 1){
                return 1;
            }else{
                String msg = jv.getTangJsonbject("data".toString()).getString("msg".toString());
                publishProgress("获取验证码失败：" + msg);
            }

            return -1;
        }

        protected void onProgressUpdate(String... progress) {
            Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_SHORT).show();
        }


        protected void onPostExecute(Integer result) {
            if(result == 1)
                Toast.makeText(getApplicationContext(), "验证码：123433", Toast.LENGTH_SHORT).show();

        }
    }

    private class RegTask extends AsyncTask<String, String, Integer> {
        protected Integer doInBackground(String... params) {
            String phone = params[0];
            String verify_code = params[1];
            String passwd = params[2];

            TangClient cli = new TangClient(getApplicationContext());
            TangJson jv = new TangJson();
            jv.put("phone", phone);
            jv.put("verify_code", verify_code);
            jv.put("passwd", passwd);

            String ret = cli.post("account/reg", jv.toString());
            jv = new TangJson(ret);
            int ret_code = jv.getInt("ret");

            if(ret_code == 1){
                return 1;
            }else{
                String msg = jv.getTangJsonbject("data".toString()).getString("msg".toString());
                publishProgress("注册失败：" + msg);
            }

            return -1;
        }

        protected void onProgressUpdate(String... progress) {
            Toast.makeText(getApplicationContext(), progress[0] , Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(Integer result) {
            if (result == 1){
                Toast.makeText(getApplicationContext(), "注册成功，请登陆", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegActivity.this, LoginActivity.class);
                intent.setClass(RegActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
