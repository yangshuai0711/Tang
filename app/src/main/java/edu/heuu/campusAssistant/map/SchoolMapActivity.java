package edu.heuu.campusAssistant.map;

import edu.heuu.campusAssistant.util.BNMapView;
import edu.heuu.campusAssistant.util.Constant;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SchoolMapActivity extends Activity
{
	PubMethod pub=new PubMethod(this);//加载文件
	BNMapView view;//学校地图
	String textPath;//子列表路径
	ListView lv,lv1,lv2;//列表
	ImageView iv;//下拉图片

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schoolmap);
		//用自定义的字体方法
		FontManager.changeFonts(FontManager.getContentView(this),this);
		
		lv=(ListView)SchoolMapActivity.this.findViewById(R.id.ListView1);
		lv1=(ListView)SchoolMapActivity.this.findViewById(R.id.ListView2);
		lv2=(ListView)SchoolMapActivity.this.findViewById(R.id.ListView3);
		
		initListView();//加载ListView信息
		Toast.makeText(this, "目前本导航只支持联合大学本部。", Toast.LENGTH_LONG).show();
		//初始化ImageView
		iv=(ImageView)SchoolMapActivity.this.findViewById(R.id.ImageView1);
		iv.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						System.out.println(textPath);
						lv1.setVisibility(View.GONE);
						lv2.setVisibility(View.VISIBLE);
						iv.setVisibility(View.GONE);
						initDetialList2(textPath);
					}
				});
	}
	public void initListView()
	{
	    Constant.List=pub.loadFromFile("map/mapList.txt");
		final String[] title=Constant.List.split("\\|");//ListView的目录
		//为ListView准备内容适配器
		BaseAdapter ba=new BaseAdapter()
		{
			@Override
			public int getCount()
			{
				return title.length/2;
			}
			@Override
			public Object getItem(int arg0) { return arg0; }

			@Override
			public long getItemId(int arg0) { return 0; }
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2)
			{ 
				//LinearLayout布局
				LinearLayout ll=new LinearLayout(SchoolMapActivity.this);
				TextView tv=new TextView(SchoolMapActivity.this);
				tv.setText(title[arg0*2].trim());
				tv.setTextSize(22);
				tv.setPadding(4, 0, 4, 0);
				tv.setGravity(Gravity.LEFT);
				tv.setTypeface(FontManager.tf);
				ll.addView(tv);
				return ll;
			}
		};
		//初始化ListView
		lv.setAdapter(ba);
		//设置选项被单击的监听器
		lv.setOnItemClickListener(
				new OnItemClickListener()
				{		
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
					{
						// TODO Auto-generated method stub
						lv2.setVisibility(View.GONE);
						lv1.setVisibility(View.VISIBLE);
						iv.setVisibility(View.VISIBLE);
						initDetialList(title[arg2*2+1]);
					}
				});
		lv2.setVisibility(View.GONE);
		lv1.setVisibility(View.VISIBLE);
		initDetialList(title[1]);//默认子ListView列表
		view=(BNMapView)SchoolMapActivity.this.findViewById(R.id.View01);
		view.gotoBuilding(27);//标记相应建筑
		view.postInvalidate();
	}
	//子ListView列表
	public void initDetialList(String path)
	{
		textPath=path;
		Constant.List=pub.loadFromFile(path);
		final String[] name=Constant.List.split("\\|");//子ListView的目录
		//为子ListView准备内容适配器
		BaseAdapter ba=new BaseAdapter()
		{
			@Override
			public int getCount()
			{
				return 4;
			}
			@Override
			public Object getItem(int arg0) { return arg0; }

			@Override
			public long getItemId(int arg0) { return 0; }
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2)
			{
				//LinearLayout布局
				LinearLayout ll=new LinearLayout(SchoolMapActivity.this);
				TextView tv=new TextView(SchoolMapActivity.this);
				tv.setText(name[arg0*2].trim());
				tv.setTextSize(22);
				tv.setPadding(4, 4, 4, 0);
				tv.setGravity(Gravity.LEFT);
				tv.setTypeface(FontManager.tf);
				ll.addView(tv);
				return ll;
			}
		};
		//初始化子ListView
		
		lv1.setAdapter(ba);
		//设置子ListView选项被单击的监听器
		lv1.setOnItemClickListener(
				new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
					{
						// TODO Auto-generated method stub 
						lv2.setVisibility(View.GONE);
						lv1.setVisibility(View.VISIBLE);
						iv.setVisibility(View.VISIBLE);
						int id=Integer.parseInt(name[arg2*2+1].trim());//子ListView各个选项的id
						view.gotoBuilding(id);//标记相应建筑
						view.postInvalidate();
						
					}
				});
	}
	//子ListView2列表
	public void initDetialList2(String path)
	{
		Constant.List=pub.loadFromFile(path);
		final String[] name=Constant.List.split("\\|");//子ListView的目录
		//为子ListView准备内容适配器
		BaseAdapter ba=new BaseAdapter()
		{
			@Override
			public int getCount()
			{
				return name.length/2;
			}
			@Override
			public Object getItem(int arg0) { return arg0; }

			@Override
			public long getItemId(int arg0) { return 0; }
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2)
			{
				//LinearLayout布局
				LinearLayout ll=new LinearLayout(SchoolMapActivity.this);
				TextView tv=new TextView(SchoolMapActivity.this);
				tv.setText(name[arg0*2].trim());
				tv.setTextSize(22);
				tv.setPadding(4, 4, 4, 0);
				tv.setGravity(Gravity.LEFT);
				tv.setTypeface(FontManager.tf);
				ll.addView(tv);
				return ll;
			}
		};
		//初始化子ListView
		
		lv2.setAdapter(ba);
		//设置子ListView选项被单击的监听器
		lv2.setOnItemClickListener(
				new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
					{
						// TODO Auto-generated method stub 
						lv2.setVisibility(View.GONE);
						lv1.setVisibility(View.VISIBLE);
						iv.setVisibility(View.VISIBLE);
						int id=Integer.parseInt(name[arg2*2+1].trim());//子ListView各个选项的id
						view.gotoBuilding(id);//标记相应建筑
						view.postInvalidate();
					}
				});
	}
}
