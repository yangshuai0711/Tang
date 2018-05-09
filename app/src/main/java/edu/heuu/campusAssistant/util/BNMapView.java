package edu.heuu.campusAssistant.util;

import edu.heuu.campusAssistant.map.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BNMapView extends View
{
	//��Ļ�Ĵ�С
	static float viewWidth;
	static float viewHeight;
	//�Ŵ�ͼƬ�Ĵ�С
	static float bmFDWidth;
	static float bmFDHeight;
	//��СͼƬ�Ĵ�С
	static float bmSXWidth;
	static float bmSXHeight;
	//����ͼ��Ĵ�С
	static float bmTBWidth;
	static float bmTBHeight;
	//ƽ��ͼ�Ĵ�С
	static float mapWidth;
    static float mapHeight;	
    
	static boolean isLoaded=false;
    static Bitmap bmMap,bmFD,bmSX,bmBallon,bmTB;
    //��������
    Paint paint=new Paint();    
    Paint paint1=new Paint();
    //����������
    float pyx=0;
    float pyy=0;
    float scale=1.0f; //���ű���

    //ѡ�н������id
    int selectedId=-1;
    //������ı߿�
    Path bpath=new Path();
    
    MapSQData msd=new MapSQData();
    
    //ʰȡ�����꣬��ԭͼ���ؼ���
    float sqx;
    float sqy;
 
    public BNMapView(Context context)
    {
    	super(context);
    }
    
    public BNMapView(Context context,AttributeSet art)
    {
    	super(context,art);
    	this.setFocusable(true); //���õ�ǰViewӵ�п��ƽ��� 
        this.setFocusableInTouchMode(true);//���õ�ǰViewӵ�д����¼�       
        paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		paint1= new Paint();//��������
		paint1.setAntiAlias(true);//�򿪿����
		initBitmap();//��ʼ��ͼƬ��Ϣ
    }    
    
    public void initBitmap()//��ȡͼƬ
    {
    	if(isLoaded)return;
    	isLoaded=true;    	
    	bmMap=BitmapIOUtil.getSBitmap("img/st.png");//���ѧУƽ��ͼ
    	//���ѧУƽ��ͼ��С
    	mapWidth=bmMap.getWidth();
    	mapHeight=bmMap.getHeight(); 
    	bmFD=BitmapFactory.decodeResource(getResources(), R.drawable.aj);//��÷Ŵ�ťͼ
    	//��÷Ŵ�ťͼ�Ĵ�С
    	bmFDWidth=bmFD.getWidth();
    	bmFDHeight=bmFD.getHeight();
    	bmSX=BitmapFactory.decodeResource(getResources(), R.drawable.ajj);//�����С��ťͼ
    	//�����С��ťͼ�Ĵ�С
    	bmSXWidth=bmSX.getWidth();
    	bmSXHeight=bmSX.getHeight();
    	bmBallon=BitmapFactory.decodeResource(getResources(), R.drawable.ballon);//�������ͼ
    	bmTB=BitmapFactory.decodeResource(getResources(), R.drawable.tb);//��÷���ͼ��
    	//��÷���ͼ��Ĵ�С
    	bmTBWidth=bmTB.getWidth();
    	bmTBHeight=bmTB.getHeight();
    }
    
    //��Ļ���������ָ��id�Ľ�����
    public void gotoBuilding(int id)
    {
    	selectedId=id;
    	int[] bwz=MapSQData.buildingBallon[id];
    	//����������
    	pyx=viewWidth/2-bwz[0]*scale;
    	pyy=viewHeight/2-bwz[1]*scale;
		if(selectedId!=-1)
		{
			@SuppressWarnings("static-access")
			int[] pdata=msd.buildingBorder[selectedId];
			bpath=new Path();//������ı߿�
			bpath.moveTo(pdata[0], pdata[1]);
			for(int i=1;i<pdata.length/2;i++)
			{
				bpath.lineTo(pdata[i*2], pdata[i*2+1]);
			}
			bpath.lineTo(pdata[0], pdata[1]);
		}
    }
    
    @SuppressWarnings("static-access")
	@Override
    public void onDraw(Canvas canvas)
    {    	
    	//ƽ���С
    	viewWidth=this.getWidth();
    	viewHeight=this.getHeight();
    	
    	paint1.setColor(Color.BLACK);//���û��ʵ���ɫ
    	paint1.setStyle(Style.STROKE);//���û��ʵ���ʽ
    	paint1.setStrokeWidth(4);//���û��ʵĴ�ϸ��
    	
    	canvas.drawARGB(0, 0, 0, 0);//�Զ�����ɫ
    	
    	canvas.save();//����
    	canvas.translate(pyx,pyy);
    	canvas.scale(scale, scale);
        canvas.drawBitmap(bmMap, 0,0, paint1);//����ͼƬ
        if(selectedId!=-1)
        {
        	//����ѡ�еĽ�����߿�
        	paint.setColor(new Color().argb(75, 9, 36, 196));
        	paint.setStyle(Style.STROKE);
        	paint.setStrokeWidth(6);
            canvas.drawPath(bpath, paint);  
        }
        
        canvas.restore();  
        
        if(selectedId!=-1)
        {            
            //����ѡ������
            float bx=MapSQData.buildingBallon[selectedId][0];
            float by=MapSQData.buildingBallon[selectedId][1];
            bx=bx*scale+pyx;
            by=by*scale+pyy;
            canvas.drawBitmap(bmBallon,bx-18,by-66, paint1); 
            
        }   
        //�Ŵ���С��ťλ��
        canvas.drawBitmap(bmFD,viewWidth-bmFDWidth-20,viewHeight-bmFDWidth-140, paint1);
        canvas.drawBitmap(bmSX,viewWidth-bmSXWidth-20,viewHeight-bmSXWidth-40, paint1); 
        
        canvas.drawBitmap(bmTB,15,10, paint1);//���ư�ť
        //�������·ָ���
        canvas.drawLine(0, 0, viewWidth, 0, paint1);
        canvas.drawLine(0, viewHeight, viewWidth, viewHeight, paint1);
    }   

    //����������
    float preX;
    float preY;
    boolean isMove=false;//�ж��Ƿ�ɷŴ����С��־
    @Override
    public boolean onTouchEvent(MotionEvent event)   
    { 
    	float tx=(int)event.getX();
    	float ty=(int)event.getY();
    	
    	switch (event.getAction()) 
        {            
            case MotionEvent.ACTION_DOWN:
            	//��ǰ��ť������
            	preX=tx;
            	preY=ty;
            	isMove=false;
            break; 
            case MotionEvent.ACTION_MOVE: 
            	if(Math.abs(tx-preX)>40||Math.abs(ty-preY)>40)
            	{
            		isMove=true;
            	}            	
            	if(isMove)
            	{
            		pyx+=tx-preX;
            		pyy+=ty-preY;
            		preX=tx;
            		preY=ty;
            	}
            break; 
            case MotionEvent.ACTION_UP:
            	if(!isMove)
            	{
            		//�ж��Ƿ��зŴ�ť
            		if(tx>=(viewWidth-bmFDWidth-20)&&tx<=(viewWidth-20)
            				&&ty>=(viewHeight-bmFDWidth-140)&&ty<=(viewHeight-140))
            		{//�Ŵ�ť
            			scale=scale+0.1f;
            			if(scale>3)
            			{
            				scale=3;
            			}
            		}
            		//�ж��Ƿ�����С��ť
            		else if(tx>=(viewWidth-bmSXWidth-20)&&tx<=(viewWidth-20)
            				&&ty>=(viewHeight-bmSXWidth-40)&&ty<=(viewHeight-40))
            		{//��С��ť
            			scale=scale-0.1f;
            			if(scale<1)
            			{
            				scale=1;
            			}
            		}
            		else
            		{//���ʰȡ
            			//������ڵ�ǰ������൱�ڵ����ԭͼ����
            			sqx=(tx-pyx)/scale;
            			sqy=(ty-pyy)/scale;
            			
            			//�������sqx��sqy���жϽ����ﱻѡ��    		
            			selectedId=MapSQUtil.getSelectBuildingID(sqx, sqy);
            			if(selectedId!=-1)
            			{
            				@SuppressWarnings("static-access")
							int[] pdata=msd.buildingBorder[selectedId];
            				bpath=new Path();
            				bpath.moveTo(pdata[0], pdata[1]);
            				for(int i=1;i<pdata.length/2;i++)
            				{
            					bpath.lineTo(pdata[i*2], pdata[i*2+1]);
            				}
            				bpath.lineTo(pdata[0], pdata[1]);
            			}
            		}
            	}
            break;
        }
    	
    	verifyPY();  
    	this.postInvalidate();
        return true; 
    }
    //������귶Χ
    //ͼƬ�����ƶ�����Χ����˼��������������귶Χ
    public void verifyPY()
    {
    	if(pyx>0)
		{
			pyx=0;
		}
		else if(pyx<viewWidth-mapWidth*scale)
		{
			pyx=viewWidth-mapWidth*scale;
		}
		
		if(pyy>0)
		{
			pyy=0;
		}
		else if(pyy<viewHeight-mapHeight*scale)
		{
			pyy=viewHeight-mapHeight*scale;
		}
    }
}  

