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
	PubMethod pub=new PubMethod(this);//�����ļ�
	BNMapView view;//ѧУ��ͼ
	String textPath;//���б�·��
	ListView lv,lv1,lv2;//�б�
	ImageView iv;//����ͼƬ

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schoolmap);
		//���Զ�������巽��
		FontManager.changeFonts(FontManager.getContentView(this),this);
		
		lv=(ListView)SchoolMapActivity.this.findViewById(R.id.ListView1);
		lv1=(ListView)SchoolMapActivity.this.findViewById(R.id.ListView2);
		lv2=(ListView)SchoolMapActivity.this.findViewById(R.id.ListView3);
		
		initListView();//����ListView��Ϣ
		Toast.makeText(this, "Ŀǰ������ֻ֧�����ϴ�ѧ������", Toast.LENGTH_LONG).show();
		//��ʼ��ImageView
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
		final String[] title=Constant.List.split("\\|");//ListView��Ŀ¼
		//ΪListView׼������������
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
				//LinearLayout����
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
		//��ʼ��ListView
		lv.setAdapter(ba);
		//����ѡ������ļ�����
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
		initDetialList(title[1]);//Ĭ����ListView�б�
		view=(BNMapView)SchoolMapActivity.this.findViewById(R.id.View01);
		view.gotoBuilding(27);//�����Ӧ����
		view.postInvalidate();
	}
	//��ListView�б�
	public void initDetialList(String path)
	{
		textPath=path;
		Constant.List=pub.loadFromFile(path);
		final String[] name=Constant.List.split("\\|");//��ListView��Ŀ¼
		//Ϊ��ListView׼������������
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
				//LinearLayout����
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
		//��ʼ����ListView
		
		lv1.setAdapter(ba);
		//������ListViewѡ������ļ�����
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
						int id=Integer.parseInt(name[arg2*2+1].trim());//��ListView����ѡ���id
						view.gotoBuilding(id);//�����Ӧ����
						view.postInvalidate();
						
					}
				});
	}
	//��ListView2�б�
	public void initDetialList2(String path)
	{
		Constant.List=pub.loadFromFile(path);
		final String[] name=Constant.List.split("\\|");//��ListView��Ŀ¼
		//Ϊ��ListView׼������������
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
				//LinearLayout����
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
		//��ʼ����ListView
		
		lv2.setAdapter(ba);
		//������ListViewѡ������ļ�����
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
						int id=Integer.parseInt(name[arg2*2+1].trim());//��ListView����ѡ���id
						view.gotoBuilding(id);//�����Ӧ����
						view.postInvalidate();
					}
				});
	}
}
