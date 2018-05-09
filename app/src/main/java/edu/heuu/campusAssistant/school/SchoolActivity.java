package edu.heuu.campusAssistant.school;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.BitmapIOUtil;
import edu.heuu.campusAssistant.util.Constant;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SchoolActivity extends Activity{
	//存放图片路径的字符串数组
	String[] imgSubPath=new String[7];
	PubMethod pub=new PubMethod(this);
	TextView tv;
	ImageView iv;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school);
	
	    //用自定义的字体方法
	    FontManager.changeFonts(FontManager.getContentView(this),this);
	    //初始化ImageView
	    ImageView iv=(ImageView)SchoolActivity.this.findViewById(R.id.ImageView01);
	    Drawable d=Drawable.createFromPath("/sdcard/zhushou/img/xiaomen.jpg");
	    iv.setBackground(d);
	    
	    initSchoolList();
	}
	
	//初始化学校简介菜单
	public void initSchoolList()
	{
		final LayoutInflater inflater=LayoutInflater.from(SchoolActivity.this);
		//根据路径读取文本中信息
		Constant.List=pub.loadFromFile("school/school.txt");
		Constant.ListArray=Constant.List.split("\\|");
		//获取数组长度
		final int count=Constant.ListArray.length/4;
		//获得所有图片的路径
		for(int i=0;i<6;i++)
		{
			imgSubPath[i]="img/"+Constant.ListArray[i*4+2];
		}
		//为ListView准备适配器
		BaseAdapter ba=new BaseAdapter()
		{
			@Override
			public int getCount()
			{
				return count;
			}
			@Override
			public Object getItem(int arg0) { return arg0; }

			@Override
			public long getItemId(int arg0) { return 0; }
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2)
			{
				//LinearLayout布局
				LinearLayout ll=(LinearLayout)arg1;
				if(ll==null)
				{
					ll=(LinearLayout)(inflater.inflate(R.layout.row, null).findViewById(R.id.listLinearLayout01));
				}
				ImageView iv=(ImageView)ll.getChildAt(0);
				iv.setImageBitmap(BitmapIOUtil.getSBitmap(imgSubPath[arg0]));
				
				LinearLayout ll2=(LinearLayout)ll.getChildAt(1);
				TextView tv1=(TextView)ll2.getChildAt(0);
				tv1.setText(Constant.ListArray[arg0*4].trim());
				tv1.setTextSize(18);
				tv1.setPadding(0, 5, 5, 0);
				tv1.setGravity(Gravity.LEFT);
				tv1.setTypeface(FontManager.tf);
				//列表各项的小标题
				TextView tv2=(TextView)ll2.getChildAt(1);
				tv2.setText(Constant.ListArray[arg0*4+1].trim());
			    tv2.setTextSize(12);//设置字体大小
				tv2.setPadding(4,0,1,1);//设置四周留白
			    tv2.setGravity(Gravity.LEFT);
			    tv2.setTextColor(Color.GRAY);
			    //使用自定义字体(方正卡通)
				tv2.setTypeface(FontManager.tf);			
				return ll;
			}
		};
		ListView lv=(ListView)SchoolActivity.this.findViewById(R.id.listView01);
		lv.setAdapter(ba);
		//设置选项被单击的监听器
		lv.setOnItemClickListener(
				new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
					{
						// TODO Auto-generated method stub
						Intent intent=new Intent();
						Bundle b=new Bundle();
						//学校中各项内容的子路径
						String textPath="school/"+Constant.ListArray[arg2*4+3].toString();
						b.putString("txt", textPath);
						intent.putExtras(b);
						//将标题传递过去le
						String title=Constant.ListArray[arg2*4].toString();
						b.putString("title", title);
						intent.putExtras(b);
						//将图片路径传递过去
						String imgPath="img/"+Constant.ListArray[arg2*4+2].toString();
						b.putString("img", imgPath);
						intent.putExtras(b);
						
						intent.setClass(SchoolActivity.this, SchoolDetialActivity.class);
						startActivity(intent);
					}
				});
	}
}