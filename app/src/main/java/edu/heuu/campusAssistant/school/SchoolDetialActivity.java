package edu.heuu.campusAssistant.school;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.BitmapIOUtil;
import edu.heuu.campusAssistant.util.FontManager;
import edu.heuu.campusAssistant.util.PubMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SchoolDetialActivity extends Activity 
{
	PubMethod pub=new PubMethod(this);
	FrameLayout fl_desc;//���ֶ���
	TextView tvTxt_short;//�������ı�
	TextView tvTxt_long;//�������ı�
    TextView ck;//�鿴ȫ��
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schooldetail);
		//���Զ�������巽��
		FontManager.changeFonts(FontManager.getContentView(this),this);
		initSchoolDetail();
	}
	
	//��ʼ������
	public void initSchoolDetail()
	{
		//��ʾ�鿴ȫ�ĵĿ���
		ck=(TextView)SchoolDetialActivity.this.findViewById(R.id.ckwy);
		ck.setTextSize(15);
		//��õ�ǰ��Intent 
		Intent intent = this.getIntent();    
		//���ȫ������ 
		Bundle bundle = intent.getExtras();   
		//�����Ϊtxt��·��ֵ 
		String value = bundle.getString("txt");  
		//�����ı�����		
		String txtInf=pub.loadFromFile(value);
		//����ͼƬ
		String imgValue=bundle.getString("img");
		//��ȡImageView����
	    ImageView iv=(ImageView)SchoolDetialActivity.this.findViewById(R.id.ImageView003);
		iv.setImageBitmap(BitmapIOUtil.getSBitmap(imgValue));
		//���ñ���
		String title=bundle.getString("title");
		//��ȡTextView����
		TextView tvTitle=(TextView)SchoolDetialActivity.this.findViewById(R.id.TextViewSchoolDetail02);
		//�����ı�����
		tvTitle.setText(title);
		//�ı���������
		tvTitle.setTextSize(29);
		tvTitle.setTypeface(FontManager.tf);
		tvTitle.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		//�����ı���Ϣ
		initTextView(txtInf.trim());
		//�鿴ȫ������
		ck.setGravity(Gravity.CENTER_HORIZONTAL);
		ck.setOnClickListener(
				new View.OnClickListener() 
				{					
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						if(ck.getText().equals("�鿴ȫ��"))
						{
							tvTxt_long.setVisibility(View.VISIBLE);
							tvTxt_short.setVisibility(View.GONE);
							ck.setText("����");
						}
						else if(ck.getText().equals("����"))
						{
							tvTxt_long.setVisibility(View.GONE);
							tvTxt_short.setVisibility(View.VISIBLE);
							ck.setText("�鿴ȫ��");
						}
						ck.setTextSize(15);
					}
				});
	}	
	//��ʼ��TextView�ı���Ϣ
	public void initTextView(String txtInf)
	{
		tvTxt_short=(TextView)SchoolDetialActivity.this.findViewById(R.id.TextView_short);
		tvTxt_long=(TextView)SchoolDetialActivity.this.findViewById(R.id.TextView_long);
		tvTxt_long.setText(txtInf.trim());	
		tvTxt_short.setText(txtInf.trim());
		tvTxt_long.setVisibility(View.GONE);
		tvTxt_short.setVisibility(View.VISIBLE);
	}
}
