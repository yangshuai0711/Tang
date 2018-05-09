package edu.heuu.campusAssistant.login;

import java.io.File;

import edu.heuu.campusAssistant.activity.HomeActivity;
import edu.heuu.campusAssistant.activity.MainActivityGroup;
import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.TangClient;
import edu.heuu.campusAssistant.util.TangCrypt;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//登录
public class LoginActivity extends Activity
{
	File f=new File("sdcard/zhushou");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.login);
	    Thread thread = new Thread()
	    {
	        @Override
	        public void run()
	        {
		        try
		        {
		            sleep(13000);
		        }
		        catch (InterruptedException e)
		        {
		            e.printStackTrace();
		        }
		        finish();
		        changeFaces();
		    }
	    };

	    //thread.start();
		((TextView)findViewById(R.id.login_text_reg))
				.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegActivity.class);
				intent.setClass(LoginActivity.this, RegActivity.class);
				startActivity(intent);
				finish();
			}
		});

		((Button)findViewById(R.id.login_btn_login)).
				setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = getEditText(R.id.login_edittext_phone);
				String passwd = getEditText(R.id.login_edittext_passwd);

				String passwd_crypt = TangCrypt.md5(passwd);
				new LoginPhoneTask().execute(phone, passwd_crypt);
			}
		});
	}

	private String getEditText(int id){
		EditText edit = (EditText) findViewById(id);
		String str = edit.getText().toString();
		return str;
	}

	private class LoginPhoneTask extends AsyncTask<String, String, Integer> {

		protected Integer doInBackground(String... params) {

			String phone = params[0];
			String passwd = params[1];

			String ret = new TangClient(getApplicationContext()).login_phone(phone, passwd);
			if(ret.length() != 0){
				publishProgress("登陆失败："+ret);
				return -1;
			}
			return 1;
		}

		protected void onProgressUpdate(String... progress) {
			Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_SHORT).show();
		}


		protected void onPostExecute(Integer result) {
			if(result == 1){
				Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
				intent.setClass(LoginActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

    public void changeFaces()
	{
		Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
//		intent.setClass(LoginActivity.this, LoadingActivity.class);
		if(!f.exists())
		{
			//如果解压文件不存在
			intent.setClass(LoginActivity.this, LoadingActivity.class);
		}
		else
		{
			intent.setClass(LoginActivity.this, MainActivityGroup.class);
		}
		startActivity(intent);
		finish();
	}
}
