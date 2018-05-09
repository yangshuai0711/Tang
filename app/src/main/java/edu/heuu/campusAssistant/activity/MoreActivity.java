package edu.heuu.campusAssistant.activity;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

//更多
@SuppressWarnings("deprecation")
public class MoreActivity  extends Activity
{
	final PubMethod pub=new PubMethod(this);		//加载文本
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		
		//用自定义的字体方法
	    FontManager.changeFonts(FontManager.getContentView(this),this);
		final Button bHelp=(Button)MoreActivity.this.findViewById(R.id.Button1);
		final Button bAbout=(Button)MoreActivity.this.findViewById(R.id.Button2);
		
		final String[] text=new String[2];
		text[0]=pub.loadFromFile("txt/help.txt");
		text[1]=pub.loadFromFile("txt/about.txt");
		//适配器
        BaseAdapter ba=new BaseAdapter()
        {
			@Override
			public int getCount() 
			{
				return 2;
			} 
			@Override
			public Object getItem(int arg0) 
			{			    
				return arg0;
			}

			@Override
			public long getItemId(int arg0) 
			{
				// TODO Auto-generated method stub
				return arg0;
			}
			
			@SuppressLint("SdCardPath")
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{				
				TextView tv=new TextView(MoreActivity.this);
				tv.setText(text[arg0]);
				tv.setTextSize(26);
				tv.setTextColor(MoreActivity.this.getResources().getColor(R.color.ziti2));
				tv.setGravity(Gravity.LEFT);
				//使用自定义字体(方正卡通)
				tv.setTypeface(FontManager.tf);
				tv.setPadding(6, 6, 6, 6);
				
				return tv;
			}        	
        };
        final Gallery gl=(Gallery)this.findViewById(R.id.Gallery01);
        gl.setAdapter(ba);
        gl.setSelection(0);
        bHelp.setOnClickListener(
				new View.OnClickListener()
				{
					//帮助按钮
					@Override
					public void onClick(View v) 
					{
						changeButton(bHelp,bAbout,gl,0);
					}
				});
		bAbout.setOnClickListener(
				new View.OnClickListener()
				{
					//关于按钮
					@Override
					public void onClick(View v) 
					{
						changeButton(bHelp,bAbout,gl,1);
					}
				});
        gl.setOnItemSelectedListener(
        		new OnItemSelectedListener()
        		{
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
					{
						// TODO Auto-generated method stub
						changeButton(bHelp,bAbout,gl,arg2);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) 
					{
						// TODO Auto-generated method stub
					}
        			
        		});
	}
	//按钮交换方法
	public void changeButton(Button bn1,Button bn2,Gallery gl,int id)
	{
		if(id==0)
		{
			bn1.setBackgroundColor(MoreActivity.this.getResources().getColor(R.color.gray));
			bn2.setBackgroundColor(MoreActivity.this.getResources().getColor(R.color.title));
			bn1.setTextColor(MoreActivity.this.getResources().getColor(R.color.ziti));
			bn2.setTextColor(MoreActivity.this.getResources().getColor(R.color.ziti1));
		}else
		{
			bn1.setBackgroundColor(MoreActivity.this.getResources().getColor(R.color.title));
			bn2.setBackgroundColor(MoreActivity.this.getResources().getColor(R.color.gray));
			bn1.setTextColor(MoreActivity.this.getResources().getColor(R.color.ziti1));
			bn2.setTextColor(MoreActivity.this.getResources().getColor(R.color.ziti));
		}
		gl.setSelection(id);
	}
}
