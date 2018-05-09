package edu.heuu.campusAssistant.login;

import edu.heuu.campusAssistant.activity.MainActivityGroup;
import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.Constant;
import edu.heuu.campusAssistant.util.WaitAnmiSurfaceView;
import edu.heuu.campusAssistant.util.ZipUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
 
public class LoadingActivity extends Activity 
{    
    Dialog waitDialog;
    WaitAnmiSurfaceView wasv;

	Handler hd=new Handler()
    {
    	@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg)//重写方法
    	{
    		switch(msg.what)
    		{
    			case Constant.WAIT_DIALOG_REPAINT://等待对话框刷新		
    				wasv.repaint();
    			break;
    		}	
    	}
    };
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        
        //设置为全屏横
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
		//设置为竖屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);        
        setContentView(R.layout.login);
        extracted();
    }
	@SuppressWarnings("deprecation")
	private void extracted() 
	{
		showDialog(Constant.WAIT_DIALOG);
	}
	
	@Override
    public Dialog onCreateDialog(int id)
    {    	
    	Dialog result=null;
    	switch(id)
    	{
			case Constant.WAIT_DIALOG://历史记录对话框的初始化
				AlertDialog.Builder b=new AlertDialog.Builder(this); 
				b.setItems(null, null);
				b.setCancelable(false);
				waitDialog=b.create();
				result=waitDialog;	    		
			break;  
    	}
    	return result;
    }
	@Override
    public void onPrepareDialog(int id, final Dialog dialog)//每次弹出对话框时被回调以动态更新对话框内容的方法
    {
    	//若不是历史对话框则返回
    	if(id!=Constant.WAIT_DIALOG)return;
        dialog.setContentView(R.layout.loading);
        wasv=(WaitAnmiSurfaceView)dialog.findViewById(R.id.wasv);
        new Thread()
        {
			public void run()
        	{	        		
        		for(int i=0;i<200;i++)
        		{
        			wasv.angle=wasv.angle+5; 
        			hd.sendEmptyMessage(Constant.WAIT_DIALOG_REPAINT);
        			try
        			{
        				Thread.sleep(50);
        			}
        			catch(Exception e)
        			{
        				e.printStackTrace();
        			}
        		}
        		dialog.cancel();
        		unzipAndChange();
        	}	        	
        }.start();
    }
	public void unzipAndChange()
	{
		try
		{
			//解压
			ZipUtil.unZip(LoadingActivity.this, "zhushou.zip", "/sdcard/");				
		}
		catch(Exception e)
		{
			System.out.println("解压出错！");
		}
		Intent intent=new Intent();
		intent.setClass(LoadingActivity.this, MainActivityGroup.class);
		startActivity(intent);
		finish();
	}
}