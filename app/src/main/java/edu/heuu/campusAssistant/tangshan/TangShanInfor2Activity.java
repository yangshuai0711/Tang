package edu.heuu.campusAssistant.tangshan;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class TangShanInfor2Activity extends Activity{
	private PubMethod pub=new PubMethod(this);		//�����ı�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
		//���Զ�������巽��
		FontManager.changeFonts(FontManager.getContentView(this),this);
		initListView();
	}
	//��ʼ����ɽ��Ϣ2����
	public void initListView()
	{
		Intent intent = this.getIntent();    //��õ�ǰ��Intent  
		Bundle bundle=intent.getExtras();  //���ȫ������  
		String information= bundle.getString("name");  //�����Ϊname��
		
		String infor=pub.loadFromFile("tangshan/"+information);
		final String[] content=infor.split("\\|");
		
		//������
        BaseAdapter ba=new BaseAdapter()
        {
			@Override
			public int getCount() 
			{
				return content.length/2;
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
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{
				LinearLayout ll=(LinearLayout)arg1;//new LinearLayout(TangShanInfor2Activity.this);
				if(ll==null)
				{
					ll=new LinearLayout(TangShanInfor2Activity.this);
					ll.setOrientation(LinearLayout.VERTICAL);		//���ó���	
					ll.setPadding(0,1,0,1);//������������
				}
				Drawable d=Drawable.createFromPath("/sdcard/zhushou/img/"+content[arg0*2+1]);
				ll.setBackgroundDrawable(d);
				ll.setPadding(0, 2, 0, 2);
				ll.setLayoutParams(new Gallery.LayoutParams(720,540));
				
				TextView tv=new TextView(TangShanInfor2Activity.this);
				tv.setText(content[arg0*2]);
				tv.setTextSize(20);
				tv.setTextColor(TangShanInfor2Activity.this.getResources().getColor(R.color.blue));
				tv.setGravity(Gravity.BOTTOM);
				tv.setPadding(4, 440, 4, 0);
				//ʹ���Զ�������(������ͨ)
			    tv.setTypeface(FontManager.tf);
				ll.addView(tv);
				
				return ll;
			}        	
        };
        Gallery gl=(Gallery)this.findViewById(R.id.Gallery01);
        gl.setAdapter(ba);
        gl.setBackgroundColor(TangShanInfor2Activity.this.getResources().getColor(R.color.back));
	}
}
