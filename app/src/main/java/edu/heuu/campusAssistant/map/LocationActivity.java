package edu.heuu.campusAssistant.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class LocationActivity extends Activity {

	MapView mMapView=null;//地图界面
	BaiduMap mBaiduMap;//百度地图
	BitmapDescriptor bitmap;//添加在地图上的气球

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_location);
		// 地图初始化		
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		 //普通地图  
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setTrafficEnabled(true);
        
        LatLng nodeLocation=new LatLng(39.625656,118.164013);//唐山市
        //移动节点至中心
         mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
         if(isGPSOpen())
 		{
 			//若GPS已经打开则进入主界面
 			initGPSListener();
 		}
 		else
 		{
 			//若GPS未打开则进入设置界面
 			gotoGPSSetting();
 		}
	}
	//初始化GPS
	private void initGPSListener() 
    {
		final LocationManager locationManager=(LocationManager)
    			this.getSystemService(Context.LOCATION_SERVICE);//获取位置管理器实例      
        LocationListener ll=new LocationListener()
        {//位置变化监听器
			@Override	//当位置变化时触发
			public void onLocationChanged(Location location)
			{
				System.out.println("*************location:"+location);
				if(location!=null)
				{
		    		try
		    		{
		    			double latitude=location.getLatitude();//获得经度
		    			double longitude=location.getLongitude();//获得 纬度
		    			
		    			LatLng nodeLocation=new LatLng(latitude,longitude);
		    			//构建Marker图标  
	    		        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ballon);  
	    		        //构建MarkerOption，用于在地图上添加Marker  
	    		        OverlayOptions option = new MarkerOptions()  
	    		            .position(nodeLocation)  
	    		            .icon(bitmap); 
	    		        mBaiduMap.clear();//清除图标
	    		        //在地图上添加Marker，并显示  
	    		        mBaiduMap.addOverlay(option);
	    		        //移动节点至中心
	    		        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
		    		}
		    		catch(Exception e)
		    		{
		    			e.printStackTrace();
		    		}
		    	}
			}
			@Override//Location Provider被禁用时更新
			public void onProviderDisabled(String provider){}
			@Override//Location Provider被启用时更新
			public void onProviderEnabled(String provider){}
			@Override//当Provider硬件状态变化时更新
			public void onStatusChanged(String provider, int status,Bundle extras){}       	
        };
     // 注册 位置改变的监听器 
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,ll); //添加位置变化监听器
    }
	//判断GPS是否打开 
    public boolean isGPSOpen()
    {
    	LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);  //获得位置管理对象 
        if (!alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) //如果GPS没开 
        {
        	return false;//返回false
        }
        else return true;//返回true
    }
    
    //跳到GPS设置界面
    public void gotoGPSSetting()
    {
   	 	 Intent intent = new Intent();  //创建Intent对象 
         intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//设置Intent的Action    
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //设置Intent的flags
         try
         {   
             startActivity(intent);//跳转到GPS设置界面方法   	                
         }catch(Exception e)
         {
        	 e.printStackTrace();
         }
    } 

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
//		mLocClient.stop();
		// 关闭定位图层
//		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

}
