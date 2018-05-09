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
	//屏幕的大小
	static float viewWidth;
	static float viewHeight;
	//放大图片的大小
	static float bmFDWidth;
	static float bmFDHeight;
	//缩小图片的大小
	static float bmSXWidth;
	static float bmSXHeight;
	//方向图标的大小
	static float bmTBWidth;
	static float bmTBHeight;
	//平面图的大小
	static float mapWidth;
    static float mapHeight;	
    
	static boolean isLoaded=false;
    static Bitmap bmMap,bmFD,bmSX,bmBallon,bmTB;
    //创建画笔
    Paint paint=new Paint();    
    Paint paint1=new Paint();
    //换算后的坐标
    float pyx=0;
    float pyy=0;
    float scale=1.0f; //缩放比例

    //选中建筑物的id
    int selectedId=-1;
    //建筑物的边框
    Path bpath=new Path();
    
    MapSQData msd=new MapSQData();
    
    //拾取的坐标，以原图像素计算
    float sqx;
    float sqy;
 
    public BNMapView(Context context)
    {
    	super(context);
    }
    
    public BNMapView(Context context,AttributeSet art)
    {
    	super(context,art);
    	this.setFocusable(true); //设置当前View拥有控制焦点 
        this.setFocusableInTouchMode(true);//设置当前View拥有触摸事件       
        paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		paint1= new Paint();//创建画笔
		paint1.setAntiAlias(true);//打开抗锯齿
		initBitmap();//初始分图片信息
    }    
    
    public void initBitmap()//获取图片
    {
    	if(isLoaded)return;
    	isLoaded=true;    	
    	bmMap=BitmapIOUtil.getSBitmap("img/st.png");//获得学校平面图
    	//获得学校平面图大小
    	mapWidth=bmMap.getWidth();
    	mapHeight=bmMap.getHeight(); 
    	bmFD=BitmapFactory.decodeResource(getResources(), R.drawable.aj);//获得放大按钮图
    	//获得放大按钮图的大小
    	bmFDWidth=bmFD.getWidth();
    	bmFDHeight=bmFD.getHeight();
    	bmSX=BitmapFactory.decodeResource(getResources(), R.drawable.ajj);//获得缩小按钮图
    	//获得缩小按钮图的大小
    	bmSXWidth=bmSX.getWidth();
    	bmSXHeight=bmSX.getHeight();
    	bmBallon=BitmapFactory.decodeResource(getResources(), R.drawable.ballon);//获得气球图
    	bmTB=BitmapFactory.decodeResource(getResources(), R.drawable.tb);//获得方向图标
    	//获得方向图标的大小
    	bmTBWidth=bmTB.getWidth();
    	bmTBHeight=bmTB.getHeight();
    }
    
    //屏幕中央滚动到指定id的建筑物
    public void gotoBuilding(int id)
    {
    	selectedId=id;
    	int[] bwz=MapSQData.buildingBallon[id];
    	//换算后的坐标
    	pyx=viewWidth/2-bwz[0]*scale;
    	pyy=viewHeight/2-bwz[1]*scale;
		if(selectedId!=-1)
		{
			@SuppressWarnings("static-access")
			int[] pdata=msd.buildingBorder[selectedId];
			bpath=new Path();//建筑物的边框
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
    	//平面大小
    	viewWidth=this.getWidth();
    	viewHeight=this.getHeight();
    	
    	paint1.setColor(Color.BLACK);//设置画笔的颜色
    	paint1.setStyle(Style.STROKE);//设置画笔的样式
    	paint1.setStrokeWidth(4);//设置画笔的粗细度
    	
    	canvas.drawARGB(0, 0, 0, 0);//自定义颜色
    	
    	canvas.save();//保存
    	canvas.translate(pyx,pyy);
    	canvas.scale(scale, scale);
        canvas.drawBitmap(bmMap, 0,0, paint1);//绘制图片
        if(selectedId!=-1)
        {
        	//绘制选中的建筑物边框
        	paint.setColor(new Color().argb(75, 9, 36, 196));
        	paint.setStyle(Style.STROKE);
        	paint.setStrokeWidth(6);
            canvas.drawPath(bpath, paint);  
        }
        
        canvas.restore();  
        
        if(selectedId!=-1)
        {            
            //绘制选中气球
            float bx=MapSQData.buildingBallon[selectedId][0];
            float by=MapSQData.buildingBallon[selectedId][1];
            bx=bx*scale+pyx;
            by=by*scale+pyy;
            canvas.drawBitmap(bmBallon,bx-18,by-66, paint1); 
            
        }   
        //放大缩小按钮位置
        canvas.drawBitmap(bmFD,viewWidth-bmFDWidth-20,viewHeight-bmFDWidth-140, paint1);
        canvas.drawBitmap(bmSX,viewWidth-bmSXWidth-20,viewHeight-bmSXWidth-40, paint1); 
        
        canvas.drawBitmap(bmTB,15,10, paint1);//绘制按钮
        //绘制上下分割线
        canvas.drawLine(0, 0, viewWidth, 0, paint1);
        canvas.drawLine(0, viewHeight, viewWidth, viewHeight, paint1);
    }   

    //建筑物坐标
    float preX;
    float preY;
    boolean isMove=false;//判断是否可放大或缩小标志
    @Override
    public boolean onTouchEvent(MotionEvent event)   
    { 
    	float tx=(int)event.getX();
    	float ty=(int)event.getY();
    	
    	switch (event.getAction()) 
        {            
            case MotionEvent.ACTION_DOWN:
            	//当前按钮的坐标
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
            		//判断是否按中放大按钮
            		if(tx>=(viewWidth-bmFDWidth-20)&&tx<=(viewWidth-20)
            				&&ty>=(viewHeight-bmFDWidth-140)&&ty<=(viewHeight-140))
            		{//放大按钮
            			scale=scale+0.1f;
            			if(scale>3)
            			{
            				scale=3;
            			}
            		}
            		//判断是否按中缩小按钮
            		else if(tx>=(viewWidth-bmSXWidth-20)&&tx<=(viewWidth-20)
            				&&ty>=(viewHeight-bmSXWidth-40)&&ty<=(viewHeight-40))
            		{//缩小按钮
            			scale=scale-0.1f;
            			if(scale<1)
            			{
            				scale=1;
            			}
            		}
            		else
            		{//点击拾取
            			//计算出在当前情况下相当于点击的原图哪里
            			sqx=(tx-pyx)/scale;
            			sqy=(ty-pyy)/scale;
            			
            			//如果根据sqx、sqy，判断建筑物被选中    		
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
    //检查坐标范围
    //图片不能移动出范围，因此计算弯腰限制坐标范围
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

