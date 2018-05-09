package edu.heuu.campusAssistant.activity;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ReProActivity extends Activity{
	PubMethod pub=new PubMethod(this);//加载文件
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baodao);
		//用自定义的字体方法
		FontManager.changeFonts(FontManager.getContentView(this),this);
		initListView();
	}
	//初始化列表
	public void initListView()
	{
		//获取liucheng.txt中的信息
		String information=pub.loadFromFile("txt/liucheng.txt");
		String[] title=information.split("\\|");
		//获取TextView对象
		TextView tv=(TextView)ReProActivity.this.findViewById(R.id.textView01);
		//对TextView对象的基本设置
		tv.setText(title[0]);
		tv.setTextSize(24);
		tv.setPadding(2, 2, 2, 0);
		
		tv=(TextView)ReProActivity.this.findViewById(R.id.textView02);
		tv.setText(title[1]);
		tv.setTextSize(20);
		tv.setPadding(4, 2, 4, 6);
	}
}
