package edu.heuu.campusAssistant.tangshan;

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

public class TangShanActivity extends Activity{
 	private PubMethod pub=new PubMethod(this);//�����ļ�
 	private String[] title=new String[50];//С����
 	String[] infor=new String[50];//�ļ�����
 	String[] textPath=new String[50];//�����б�󴫵ݵ���Ϣ
 	String[] content=new String[20];//С�����µļ��
	private String[] imgPath=new String[20];//ͼƬ��ַ
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tangshan);
		//���Զ�������巽��
		FontManager.changeFonts(FontManager.getContentView(this), this);
		initListView();
	    //��ʼ��ImageView
	    ImageView iv=(ImageView)TangShanActivity.this.findViewById(R.id.ImageView02);
	    Drawable d=Drawable.createFromPath("/sdcard/zhushou/img/jz.jpg");
	    iv.setBackground(d);
	}
	//��ʼ��ListView�б�
	public void initListView()
	{
		String information=pub.loadFromFile("tangshan/tangshanlist.txt");
		infor=information.split("\\|");
		final int count=infor.length/4;
		
		//����ͼƬ·��,С�����Լ����ʹ�����Ϣ
		for(int i=0;i<count;i++)
		{
			imgPath[i]="img/"+infor[i*4+2];//ͼƬ·��
			title[i]=infor[i*4].trim();
			content[i]=infor[4*i+1];
			textPath[i]=infor[i*4+3];
		}
		//ΪListView׼������������
		BaseAdapter ba=new BaseAdapter()
		{
			LayoutInflater inflater=LayoutInflater.from(TangShanActivity.this);
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
					ll=(LinearLayout)(inflater.inflate(R.layout.row1, null).findViewById(R.id.listLinearLayout01));
				}
				ImageView iv=(ImageView)ll.getChildAt(0);
				iv.setImageBitmap(BitmapIOUtil.getSBitmap(imgPath[arg0]));
				iv.setScaleType(ImageView.ScaleType.FIT_XY);
					
				LinearLayout ll2=(LinearLayout)ll.getChildAt(1);
				TextView tv1=(TextView)ll2.getChildAt(0);
				tv1.setText(title[arg0]);
				tv1.setTextSize(18);//���������С
				tv1.setTextColor(TangShanActivity.this.getResources().getColor(R.color.ziti2));//����������ɫ
				tv1.setPadding(2,0,1,2);//������������
			    tv1.setGravity(Gravity.LEFT);
			    tv1.setTypeface(FontManager.tf);
			        
			    TextView tv2=(TextView)ll2.getChildAt(1);
			    tv2.setText(content[arg0]);
				tv2.setTextSize(12);//���������С
				tv2.setTextColor(TangShanActivity.this.getResources().getColor(R.color.ziti3));//����������ɫ
				tv2.setPadding(10,0,5,1);//������������
			    tv2.setGravity(Gravity.LEFT);
			    tv2.setTypeface(FontManager.tf);
				return ll;
			}
		};
        //��ʼ��ListView
        ListView lv=(ListView)TangShanActivity.this.findViewById(R.id.listView01);
	    lv.setAdapter(ba);
	    lv.setBackgroundColor(TangShanActivity.this.getResources().getColor(R.color.text));
	    lv.setOnItemClickListener(
		        new OnItemClickListener()
			    {	
				    @Override
			        public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
				    {
					// TODO Auto-generated method stub
					    change(arg2);
					}
			    } 
		);
	}
	//�б��µĴ�����
	public void change(int id)
	{
		Intent intent=new Intent();
		Bundle bundle=new Bundle();
		bundle.putString("name",textPath[id].toString());
		intent.putExtras(bundle);
		if((id+1)%3!=0)
		{
			intent.setClass(TangShanActivity.this,TangShanInforActivity.class);
		}else
		{
			intent.setClass(TangShanActivity.this,TangShanInfor2Activity.class);
		}
		startActivity(intent);
	}
}
