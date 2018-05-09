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
	private final PubMethod pub=new PubMethod(this);//�����ļ�
	private String[] imgPath=new String[20];//ͼƬ·��
 	private String[] title1=new String[20];//listView�б��е�С����
 	private String[] title2=new String[20];//С�����µļ��
 	private String[] infor=new String[20];//�ļ�����
 	private String[] textPath=new String[20];//�����б�󴫵ݵ��ļ�·��

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xueyuan);
		//���Զ�������巽��
		FontManager.changeFonts(FontManager.getContentView(this),this);
		initListView();
	    //��ʼ��ImageView
	    ImageView iv=(ImageView)InstituteActivity.this.findViewById(R.id.ImageView03);
	    Drawable d=Drawable.createFromPath("/sdcard/zhushou/img/tu.jpg");
	    iv.setBackground(d);
	}

	//��ʼ��ListView�б�
	public void initListView()
	{
		final LayoutInflater inflater=LayoutInflater.from(InstituteActivity.this);
		String information=pub.loadFromFile("xueyuan/xueyuanlist.txt");//�ı�����
		infor=information.split("\\|");
		final int count=infor.length/4;//�б�����
		//����ͼƬ·����С���⣬С�����µļ���Լ�������Ϣ
		for(int i=0;i<count;i++)
		{
			imgPath[i]="img/"+infor[i*4+2];
			title1[i]=infor[i*4].trim();
			title2[i]=infor[i*4+1].trim();
			textPath[i]=infor[i*4+3].trim();
		}
		//ΪListView׼������������
		BaseAdapter ba=new BaseAdapter()
		{
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
				if(ll==null)
				{
					ll=(LinearLayout)(inflater.inflate(R.layout.row2, null).findViewById(R.id.listLinearLayout01));
				}	
				//����ͼƬ
				ImageView ii=(ImageView)ll.getChildAt(0);					
				ii.setImageBitmap(BitmapIOUtil.getSBitmap(imgPath[arg0]));
				ii.setScaleType(ImageView.ScaleType.FIT_XY);
					
				LinearLayout ll2=(LinearLayout)ll.getChildAt(1);
				TextView tv1=(TextView) ll2.getChildAt(0);
				tv1.setText(title1[arg0]);
				tv1.setTextSize(18);//���������С
				tv1.setTextColor(InstituteActivity.this.getResources().getColor(R.color.ziti2));//����������ɫ
			    tv1.setGravity(Gravity.LEFT);
			    //ʹ���Զ�������(������ͨ)
				tv1.setTypeface(FontManager.tf);
					  
				TextView tv2=(TextView)ll2.getChildAt(1);
				tv2.setText(title2[arg0]);
				tv2.setTextSize(12);//���������С
			    tv2.setTextColor(InstituteActivity.this.getResources().getColor(R.color.ziti3));//����������ɫ
				tv2.setPadding(10, 0, 1, 0);
				//ʹ���Զ�������(������ͨ)
			    tv2.setTypeface(FontManager.tf);
	
				return  ll;
			}
		};
		//��ʼ��ListView�б�
		ListView lv=(ListView)InstituteActivity.this.findViewById(R.id.listView01);
	    lv.setAdapter(ba);//���������
	    lv.setBackgroundColor(InstituteActivity.this.getResources().getColor(R.color.text));
	    lv.setOnItemClickListener(
				new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
					{
						// TODO Auto-generated method stub
						//��ת����
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
