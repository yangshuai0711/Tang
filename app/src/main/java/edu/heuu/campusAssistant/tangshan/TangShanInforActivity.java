package edu.heuu.campusAssistant.tangshan;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.BitmapIOUtil;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TangShanInforActivity extends Activity {
	private PubMethod pub=new PubMethod(this);//加载文本
	private String[] infor=new String[20];//文件内容
	private String[] imgPath=new String[20];//图片地址

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tangshaninfor);
		//用自定义的字体方法
		FontManager.changeFonts(FontManager.getContentView(this),this);
		initList();
	}
	//初始化唐山信息1界面
	public void initList()
	{
		Intent intent = this.getIntent();    //获得当前的Intent  
		Bundle bundle = intent.getExtras();  //获得全部数据  
		String value = bundle.getString("name");  //获得名为name的
		String information=pub.loadFromFile("tangshan/"+value);
		infor=information.split("\\|");
		final int count=infor.length/2;
		//图片
		for(int i=0;i<count;i++)
		{
			imgPath[i]="img/"+infor[i*2+1];
		}
		//为ListView准备内容适配器
		BaseAdapter ba=new BaseAdapter()
		{
			LayoutInflater inflater=LayoutInflater.from(TangShanInforActivity.this);
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
		        if (ll == null)
		        { 
		            ll = (LinearLayout)(inflater.inflate(R.layout.tangshan1, null).findViewById(R.id.linearLayout1));
		        } 
				//初始化ImageView
		        ImageView  ii=(ImageView)ll.getChildAt(0);
				ii.setImageBitmap(BitmapIOUtil.getSBitmap(imgPath[arg0]));//设置图片
				ii.setScaleType(ImageView.ScaleType.FIT_XY);
				ii.setPadding(100, 2, 80, 2);
							
				//初始化TextView 
				TextView tv=(TextView)ll.getChildAt(1);
				tv.setText(infor[arg0*2]);
				tv.setTextSize(20);//设置字体大小
				tv.setTextColor(TangShanInforActivity.this.getResources().getColor(R.color.ziti3));//设置字体颜色
				tv.setPadding(45,2,15,0);//设置四周留白
				tv.setGravity(Gravity.LEFT);
				//使用自定义字体(方正卡通)
				tv.setTypeface(FontManager.tf);
						
				return ll;
		    }
		};
		ListView lv=(ListView)TangShanInforActivity.this.findViewById(R.id.ListView01);
	    lv.setAdapter(ba);
	    lv.setBackgroundColor(TangShanInforActivity.this.getResources().getColor(R.color.text));
	}
}
