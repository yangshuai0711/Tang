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
	private PubMethod pub=new PubMethod(this);//�����ı�
	private String[] infor=new String[20];//�ļ�����
	private String[] imgPath=new String[20];//ͼƬ��ַ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tangshaninfor);
		//���Զ�������巽��
		FontManager.changeFonts(FontManager.getContentView(this),this);
		initList();
	}
	//��ʼ����ɽ��Ϣ1����
	public void initList()
	{
		Intent intent = this.getIntent();    //��õ�ǰ��Intent  
		Bundle bundle = intent.getExtras();  //���ȫ������  
		String value = bundle.getString("name");  //�����Ϊname��
		String information=pub.loadFromFile("tangshan/"+value);
		infor=information.split("\\|");
		final int count=infor.length/2;
		//ͼƬ
		for(int i=0;i<count;i++)
		{
			imgPath[i]="img/"+infor[i*2+1];
		}
		//ΪListView׼������������
		BaseAdapter ba=new BaseAdapter()
		{
			LayoutInflater inflater=LayoutInflater.from(TangShanInforActivity.this);
			@Override
			public int getCount() 
			{
			    return count;//�ܹ�ѡ��
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
				//��ʼ��ImageView
		        ImageView  ii=(ImageView)ll.getChildAt(0);
				ii.setImageBitmap(BitmapIOUtil.getSBitmap(imgPath[arg0]));//����ͼƬ
				ii.setScaleType(ImageView.ScaleType.FIT_XY);
				ii.setPadding(100, 2, 80, 2);
							
				//��ʼ��TextView 
				TextView tv=(TextView)ll.getChildAt(1);
				tv.setText(infor[arg0*2]);
				tv.setTextSize(20);//���������С
				tv.setTextColor(TangShanInforActivity.this.getResources().getColor(R.color.ziti3));//����������ɫ
				tv.setPadding(45,2,15,0);//������������
				tv.setGravity(Gravity.LEFT);
				//ʹ���Զ�������(������ͨ)
				tv.setTypeface(FontManager.tf);
						
				return ll;
		    }
		};
		ListView lv=(ListView)TangShanInforActivity.this.findViewById(R.id.ListView01);
	    lv.setAdapter(ba);
	    lv.setBackgroundColor(TangShanInforActivity.this.getResources().getColor(R.color.text));
	}
}
