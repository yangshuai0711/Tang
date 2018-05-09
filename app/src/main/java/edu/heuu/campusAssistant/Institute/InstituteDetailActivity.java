package edu.heuu.campusAssistant.Institute;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.BitmapIOUtil;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class InstituteDetailActivity extends Activity {
    private final PubMethod pub=new PubMethod(this);		//加载文本
    private String[] infor=new String[20];//文件内容
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.institutemain);
		//用自定义的字体方法
		FontManager.changeFonts(FontManager.getContentView(this),this);
	    init();
	}
	//初始化界面信息
	public void init()
	{
		//获取Intent
		Intent intent=this.getIntent();
		//获取Bundle对象的值对象
		Bundle bundle=intent.getExtras();
		String textPath=bundle.getString("name");
		//获取信息路径
		String information=pub.loadFromFile("xueyuan/"+textPath);
		infor=information.split("\\|");
		TextView tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView01);
		//设置字体颜色
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));
		tv.setText(infor[0].trim());
		//设置字体大小
		tv.setTextSize(22);
		//设置留白
		tv.setPadding(0, 2, 2, 1);
		//创建ImageView对象
		ImageView iv=(ImageView)InstituteDetailActivity.this.findViewById(R.id.ImageView01);
		//加载图片
		iv.setImageBitmap(BitmapIOUtil.getSBitmap("img/"+infor[1]));
		//把图片不按比例扩大到View的大小显示
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
					
		//创建TextView对象
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView1);
		//设置字体颜色
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));
		tv.setText(infor[2].trim());
		//设置字体大小
		tv.setTextSize(18);
		//设置留白
		tv.setPadding(0, 1, 0, 1);
		//创建TextView对象
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView2);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));//设置字体颜色
		tv.setText(infor[3].trim());
		tv.setTextSize(16);
		tv.setPadding(0, 1, 0,10);
		//创建TextView对象
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView3);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.red));//设置字体颜色
		tv.setBackgroundColor(InstituteDetailActivity.this.getResources().getColor(R.color.blue));
		tv.setText("相关信息:");
		tv.setPadding(2, 1, 0, 0);
		tv.setTextSize(20);
					
		//创建TextView对象
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView4);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));
		tv.setText(infor[4].trim());
		tv.setPadding(16, 1, 0, 4);
		tv.setTextSize(18);
		tv.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						changeText(5);
					}
				}
		);
		//创建TextView对象
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView5);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));//设置字体颜色
		tv.setText(infor[6].trim());
		tv.setPadding(16, 1, 0, 4);
		tv.setTextSize(18);
		tv.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						changeText(7);
					}
				}
		);
		//创建TextView对象
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView6);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));//设置字体颜色
		tv.setText(infor[8].trim());
		tv.setPadding(16, 1, 0, 4);
		tv.setTextSize(18);
		tv.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						changeText(9);
					}
				}
		);
	}
	//界面更新
	public void changeText(int id)
	{
		//创建Intent对象
		Intent intent=new Intent();
		Bundle bundle=new Bundle();
		//添加键值对象
		bundle.putString("name",infor[id].toString());
		intent.putExtras(bundle);
		intent.setClass(InstituteDetailActivity.this, InstituteDetailActivity.class);
		//启动intent
		startActivity(intent);  
		finish();
	}
}
