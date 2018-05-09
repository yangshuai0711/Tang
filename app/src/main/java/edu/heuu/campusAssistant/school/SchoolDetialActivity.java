package edu.heuu.campusAssistant.school;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.BitmapIOUtil;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SchoolDetialActivity extends Activity 
{
	PubMethod pub=new PubMethod(this);
	FrameLayout fl_desc;//布局对象
	TextView tvTxt_short;//短内容文本
	TextView tvTxt_long;//长内容文本
    TextView ck;//查看全文
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schooldetail);
		//用自定义的字体方法
		FontManager.changeFonts(FontManager.getContentView(this),this);
		initSchoolDetail();
	}
	
	//初始化界面
	public void initSchoolDetail()
	{
		//表示查看全文的控制
		ck=(TextView)SchoolDetialActivity.this.findViewById(R.id.ckwy);
		ck.setTextSize(15);
		//获得当前的Intent 
		Intent intent = this.getIntent();    
		//获得全部数据 
		Bundle bundle = intent.getExtras();   
		//获得名为txt的路径值 
		String value = bundle.getString("txt");  
		//具体文本内容		
		String txtInf=pub.loadFromFile(value);
		//加载图片
		String imgValue=bundle.getString("img");
		//获取ImageView对象
	    ImageView iv=(ImageView)SchoolDetialActivity.this.findViewById(R.id.ImageView003);
		iv.setImageBitmap(BitmapIOUtil.getSBitmap(imgValue));
		//设置标题
		String title=bundle.getString("title");
		//获取TextView对象
		TextView tvTitle=(TextView)SchoolDetialActivity.this.findViewById(R.id.TextViewSchoolDetail02);
		//设置文本内容
		tvTitle.setText(title);
		//文本属性设置
		tvTitle.setTextSize(29);
		tvTitle.setTypeface(FontManager.tf);
		tvTitle.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		//加载文本信息
		initTextView(txtInf.trim());
		//查看全文设置
		ck.setGravity(Gravity.CENTER_HORIZONTAL);
		ck.setOnClickListener(
				new View.OnClickListener() 
				{					
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						if(ck.getText().equals("查看全文"))
						{
							tvTxt_long.setVisibility(View.VISIBLE);
							tvTxt_short.setVisibility(View.GONE);
							ck.setText("收起");
						}
						else if(ck.getText().equals("收起"))
						{
							tvTxt_long.setVisibility(View.GONE);
							tvTxt_short.setVisibility(View.VISIBLE);
							ck.setText("查看全文");
						}
						ck.setTextSize(15);
					}
				});
	}	
	//初始化TextView文本信息
	public void initTextView(String txtInf)
	{
		tvTxt_short=(TextView)SchoolDetialActivity.this.findViewById(R.id.TextView_short);
		tvTxt_long=(TextView)SchoolDetialActivity.this.findViewById(R.id.TextView_long);
		tvTxt_long.setText(txtInf.trim());	
		tvTxt_short.setText(txtInf.trim());
		tvTxt_long.setVisibility(View.GONE);
		tvTxt_short.setVisibility(View.VISIBLE);
	}
}
