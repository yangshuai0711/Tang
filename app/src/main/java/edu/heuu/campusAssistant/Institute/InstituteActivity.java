package edu.heuu.campusAssistant.Institute;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.BitmapIOUtil;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.app.Activity;
import android.content.Intent;
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

public class InstituteActivity extends Activity{
	private final PubMethod pub=new PubMethod(this);//加载文件
	private String[] imgPath=new String[20];//图片路径
 	private String[] title1=new String[20];//listView列表行的小标题
 	private String[] title2=new String[20];//小标题下的简介
 	private String[] infor=new String[20];//文件内容
 	private String[] textPath=new String[20];//按下列表后传递的文件路径

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xueyuan);
		//用自定义的字体方法
		FontManager.changeFonts(FontManager.getContentView(this),this);
		initListView();
	    //初始化ImageView
	    ImageView iv=(ImageView)InstituteActivity.this.findViewById(R.id.ImageView03);
	    Drawable d=Drawable.createFromPath("/sdcard/zhushou/img/tu.jpg");
	    iv.setBackground(d);
	}

	//初始化ListView列表
	public void initListView()
	{
		final LayoutInflater inflater=LayoutInflater.from(InstituteActivity.this);
		String information=pub.loadFromFile("xueyuan/xueyuanlist.txt");//文本内容
		infor=information.split("\\|");
		final int count=infor.length/4;//列表行数
		//加载图片路径，小标题，小标题下的简介以及传递信息
		for(int i=0;i<count;i++)
		{
			imgPath[i]="img/"+infor[i*4+2];
			title1[i]=infor[i*4].trim();
			title2[i]=infor[i*4+1].trim();
			textPath[i]=infor[i*4+3].trim();
		}
		//为ListView准备内容适配器
		BaseAdapter ba=new BaseAdapter()
		{
			@Override
			public int getCount() 
			{
				return count;//总共选项
			}

			@Override
			public Object getItem(int arg0) { return arg0; }

			@Override
			public long getItemId(int arg0) { return 0; }

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{
				LinearLayout ll=(LinearLayout)arg1;
				if(ll==null)
				{
					ll=(LinearLayout)(inflater.inflate(R.layout.row2, null).findViewById(R.id.listLinearLayout01));
				}	
				//设置图片
				ImageView ii=(ImageView)ll.getChildAt(0);					
				ii.setImageBitmap(BitmapIOUtil.getSBitmap(imgPath[arg0]));
				ii.setScaleType(ImageView.ScaleType.FIT_XY);
					
				LinearLayout ll2=(LinearLayout)ll.getChildAt(1);
				TextView tv1=(TextView) ll2.getChildAt(0);
				tv1.setText(title1[arg0]);
				tv1.setTextSize(18);//设置字体大小
				tv1.setTextColor(InstituteActivity.this.getResources().getColor(R.color.ziti2));//设置字体颜色
			    tv1.setGravity(Gravity.LEFT);
			    //使用自定义字体(方正卡通)
				tv1.setTypeface(FontManager.tf);
					  
				TextView tv2=(TextView)ll2.getChildAt(1);
				tv2.setText(title2[arg0]);
				tv2.setTextSize(12);//设置字体大小
			    tv2.setTextColor(InstituteActivity.this.getResources().getColor(R.color.ziti3));//设置字体颜色
				tv2.setPadding(10, 0, 1, 0);
				//使用自定义字体(方正卡通)
			    tv2.setTypeface(FontManager.tf);
	
				return  ll;
			}
		};
		//初始化ListView列表
		ListView lv=(ListView)InstituteActivity.this.findViewById(R.id.listView01);
	    lv.setAdapter(ba);//添加适配器
	    lv.setBackgroundColor(InstituteActivity.this.getResources().getColor(R.color.text));
	    lv.setOnItemClickListener(
				new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
					{
						// TODO Auto-generated method stub
						//跳转界面
						Intent intent=new Intent();
						Bundle bundle=new Bundle();
						bundle.putString("name",textPath[arg2].toString());
						intent.putExtras(bundle);
						intent.setClass(InstituteActivity.this, InstituteDetailActivity.class);
						startActivity(intent);
					}
				});
	}
}
