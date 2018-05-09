package edu.heuu.campusAssistant.activity;

import java.util.ArrayList;
import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import edu.heuu.campusAssistant.Institute.InstituteActivity;
import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.school.SchoolActivity;
import edu.heuu.campusAssistant.tangshan.TangShanActivity;
import edu.heuu.campusAssistant.util.FontManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressWarnings("deprecation")
public class LianHeActivity extends Activity {
	private ViewPager m_vp;//分页对象
	private LocalActivityManager manager=null;
	
	private ArrayList<View> list;//存放activity的列表
	
	@SuppressWarnings("unused")
	private boolean mIsEngineInitSuccess = false;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
       
		//用自定义的字体方法
 	    FontManager.changeFonts(FontManager.getContentView(this),this);
 	   
        manager=new LocalActivityManager(this,true);
        manager.dispatchCreate(savedInstanceState);
        
        m_vp=(ViewPager)LianHeActivity.this.findViewById(R.id.viewpager);
        //获取Button对象
        final Button bn0= (Button) findViewById(R.id.Button01);
        final Button bn1 = (Button) findViewById(R.id.Button02);
        final Button bn2 = (Button) findViewById(R.id.Button03);
        //设置监听
        bn0.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						changeText(bn0,bn1,bn2,0);
					}
				});
        bn1.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						changeText(bn0,bn1,bn2,1);
					}
				});
        bn2.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						changeText(bn0,bn1,bn2,2);
					}
				});
        
        //创建List对象
        list=new ArrayList<View>();
        Intent intent1=new Intent(this,SchoolActivity.class);
        list.add(getView("SchoolActivity",intent1));
        Intent intent2=new Intent(this,InstituteActivity.class);
        list.add(getView("InstituteActivity",intent2));
        Intent intent3=new Intent(this,TangShanActivity.class);
		intent3.putExtra("longN", (int)(118.164013f*1E5));//经度
		intent3.putExtra("latN", (int)(39.625656f*1E5));//纬度
        list.add(getView("TangShanActivity",intent3));
        
        //准备PagerAdapter适配器
        PagerAdapter fa=new PagerAdapter()
        {
        	 @Override
             public void destroyItem(ViewGroup container, int position,Object object) 
        	 {
                 ViewPager pViewPager = ((ViewPager) container);
                 pViewPager.removeView(list.get(position));
             }
			@Override
			public int getCount() 
			{
				// TODO Auto-generated method stub
				return list.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) 
			{
				// TODO Auto-generated method stub
				return arg0==arg1;
			}
			
			 @Override
		     public Object instantiateItem(View arg0, int arg1) 
			 {
		         ViewPager pViewPager = ((ViewPager) arg0);
		         pViewPager.addView(list.get(arg1));
		         return list.get(arg1);
		     }
        };
 
        m_vp.setAdapter(fa);
        m_vp.setCurrentItem(0);
        //添加监听
        m_vp.setOnPageChangeListener(
        		new  OnPageChangeListener()
        		{
        			@Override  
            	    public void onPageScrollStateChanged(int arg0) 
        			{  
            	        // TODO Auto-generated method stub  
            	    }  

            	    @Override  
            	    public void onPageScrolled(int arg0, float arg1, int arg2) 
            	    {  
            	        // TODO Auto-generated method stub  
            	    }  

            	    @Override  
            	    public void onPageSelected(int arg0) 
            	    {  
            	    	changeText(bn0,bn1,bn2,arg0);
            	    }
        		});
   
        //-----------此处为判断key校验是否成功--------------
		BaiduNaviManager.getInstance().initEngine(this, getSdcardDir(),
                mNaviEngineInitListener, new LBSAuthManagerListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        String str = null;
                        if (0 == status) {
                            str = "key校验成功!";
                        } else {
                            str = "key校验失败, " + msg;
                        }
                        System.out.println(str);
                    }
                });
		
		  //--------------------------------------------------
        
    }
	private View getView(String string, Intent intent) 
	{
		// TODO Auto-generated method stub
		return manager.startActivity(string, intent).getDecorView();
	}

	//页面翻转方法
	public void changeText(Button bn1,Button bn2,Button bn3,int count)
	{
		switch(count)
		{
			case 0:
			{
				bn1.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.gray));
				bn2.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.title));
				bn3.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.title));
				bn1.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti));
				bn2.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti1));
				bn3.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti1));
			}break;
			case 1:
			{
				bn2.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.gray));
				bn1.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.title));	
				bn3.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.title));
				bn2.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti));
				bn1.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti1));
				bn3.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti1));
			}break;
			case 2:
			{
				bn3.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.gray));
				bn1.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.title));	
				bn2.setBackgroundColor(LianHeActivity.this.getResources().getColor(R.color.title));
				bn3.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti));
				bn2.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti1));
				bn1.setTextColor(LianHeActivity.this.getResources().getColor(R.color.ziti1));
			}break;
		}
		m_vp.setCurrentItem(count);
	}
	
	
	//----------------校验需要的方法---------------
	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}    
    private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
		public void engineInitSuccess() {
			mIsEngineInitSuccess = true;
			System.out.println("初始化成功");
		}

		public void engineInitStart() {
			System.out.println("开始初始化");
		}

		public void engineInitFail() {
			System.out.println("初始化失败");
		}
	};
	
	//----------------校验需要的方法---------------
}