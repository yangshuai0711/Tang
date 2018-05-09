package edu.heuu.campusAssistant.Institute;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.BitmapIOUtil;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class InstituteDetailActivity extends Activity {
    private final PubMethod pub=new PubMethod(this);		//�����ı�
    private String[] infor=new String[20];//�ļ�����
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.institutemain);
		//���Զ�������巽��
		FontManager.changeFonts(FontManager.getContentView(this),this);
	    init();
	}
	//��ʼ��������Ϣ
	public void init()
	{
		//��ȡIntent
		Intent intent=this.getIntent();
		//��ȡBundle�����ֵ����
		Bundle bundle=intent.getExtras();
		String textPath=bundle.getString("name");
		//��ȡ��Ϣ·��
		String information=pub.loadFromFile("xueyuan/"+textPath);
		infor=information.split("\\|");
		TextView tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView01);
		//����������ɫ
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));
		tv.setText(infor[0].trim());
		//���������С
		tv.setTextSize(22);
		//��������
		tv.setPadding(0, 2, 2, 1);
		//����ImageView����
		ImageView iv=(ImageView)InstituteDetailActivity.this.findViewById(R.id.ImageView01);
		//����ͼƬ
		iv.setImageBitmap(BitmapIOUtil.getSBitmap("img/"+infor[1]));
		//��ͼƬ������������View�Ĵ�С��ʾ
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
					
		//����TextView����
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView1);
		//����������ɫ
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));
		tv.setText(infor[2].trim());
		//���������С
		tv.setTextSize(18);
		//��������
		tv.setPadding(0, 1, 0, 1);
		//����TextView����
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView2);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));//����������ɫ
		tv.setText(infor[3].trim());
		tv.setTextSize(16);
		tv.setPadding(0, 1, 0,10);
		//����TextView����
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView3);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.red));//����������ɫ
		tv.setBackgroundColor(InstituteDetailActivity.this.getResources().getColor(R.color.blue));
		tv.setText("�����Ϣ:");
		tv.setPadding(2, 1, 0, 0);
		tv.setTextSize(20);
					
		//����TextView����
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView4);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));
		tv.setText(infor[4].trim());
		tv.setPadding(16, 1, 0, 4);
		tv.setTextSize(18);
		tv.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						changeText(5);
					}
				}
		);
		//����TextView����
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView5);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));//����������ɫ
		tv.setText(infor[6].trim());
		tv.setPadding(16, 1, 0, 4);
		tv.setTextSize(18);
		tv.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						changeText(7);
					}
				}
		);
		//����TextView����
		tv=(TextView)InstituteDetailActivity.this.findViewById(R.id.TextView6);
		tv.setTextColor(InstituteDetailActivity.this.getResources().getColor(R.color.ziti2));//����������ɫ
		tv.setText(infor[8].trim());
		tv.setPadding(16, 1, 0, 4);
		tv.setTextSize(18);
		tv.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						changeText(9);
					}
				}
		);
	}
	//�������
	public void changeText(int id)
	{
		//����Intent����
		Intent intent=new Intent();
		Bundle bundle=new Bundle();
		//��Ӽ�ֵ����
		bundle.putString("name",infor[id].toString());
		intent.putExtras(bundle);
		intent.setClass(InstituteDetailActivity.this, InstituteDetailActivity.class);
		//����intent
		startActivity(intent);  
		finish();
	}
}
