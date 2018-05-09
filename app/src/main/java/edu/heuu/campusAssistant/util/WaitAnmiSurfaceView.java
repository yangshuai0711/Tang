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
	Paint paint;//画笔
//	float distance=0;//主辅点距离
	float viewWidth=80;
	float viewHeight=80; 
//	float scaleSpeedSpan=200;//缩放步进比例============
	Bitmap bitmapTmp;//要绘制的图片
	float picWidth;//图片宽度
	float picHeight;//图片高度
	public float angle=90;//初始旋转角度

	public WaitAnmiSurfaceView(Context activity,AttributeSet as) 
	{
		super(activity,as);
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		//加载图片
		bitmapTmp=BitmapFactory.decodeResource(activity.getResources(), R.drawable.star);
		picWidth=bitmapTmp.getWidth();
		picHeight=bitmapTmp.getHeight();
	} 

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas)
	{		
		//绘制黑色填充矩形
		paint.setColor(Color.WHITE);//设置画笔颜色
		//计算左上侧点的坐标
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
	
	//自己为了方便开发的repaint方法
    public void repaint()
    {
		this.invalidate();
	}
}