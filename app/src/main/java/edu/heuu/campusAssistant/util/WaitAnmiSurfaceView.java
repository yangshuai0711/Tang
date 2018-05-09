package edu.heuu.campusAssistant.util;

import edu.heuu.campusAssistant.map.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class WaitAnmiSurfaceView extends View 
{
	Paint paint;//����
//	float distance=0;//���������
	float viewWidth=80;
	float viewHeight=80; 
//	float scaleSpeedSpan=200;//���Ų�������============
	Bitmap bitmapTmp;//Ҫ���Ƶ�ͼƬ
	float picWidth;//ͼƬ���
	float picHeight;//ͼƬ�߶�
	public float angle=90;//��ʼ��ת�Ƕ�

	public WaitAnmiSurfaceView(Context activity,AttributeSet as) 
	{
		super(activity,as);
		paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		//����ͼƬ
		bitmapTmp=BitmapFactory.decodeResource(activity.getResources(), R.drawable.star);
		picWidth=bitmapTmp.getWidth();
		picHeight=bitmapTmp.getHeight();
	} 

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas)
	{		
		//���ƺ�ɫ������
		paint.setColor(Color.WHITE);//���û�����ɫ
		//�������ϲ�������
		float left=(viewWidth-picWidth)/2+80;
		float top=(viewHeight-picHeight)/2+80;      
		Matrix m1=new Matrix();
		m1.setTranslate(left,top);
		Matrix m3=new Matrix();
		m3.setRotate(angle, viewWidth/2+80, viewHeight/2+80);
		Matrix mzz=new Matrix();
		mzz.setConcat(m3, m1);
		canvas.drawBitmap(bitmapTmp, mzz, paint);
	}
	
	//�Լ�Ϊ�˷��㿪����repaint����
    public void repaint()
    {
		this.invalidate();
	}
}