package edu.heuu.campusAssistant.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.CommonParams.NL_Net_Mode;
import com.baidu.navisdk.CommonParams.Const.ModelName;
import com.baidu.navisdk.comapi.mapcontrol.MapParams.Const.LayerMode;
import com.baidu.navisdk.comapi.routeguide.RouteGuideParams.RGLocationMode;
import com.baidu.navisdk.comapi.routeplan.BNRoutePlaner;
import com.baidu.navisdk.comapi.routeplan.IRouteResultObserver;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.baidu.navisdk.comapi.setting.SettingParams;
import com.baidu.navisdk.model.NaviDataEngine;
import com.baidu.navisdk.model.RoutePlanModel;
import com.baidu.navisdk.model.datastruct.RoutePlanNode;
import com.baidu.navisdk.ui.routeguide.BNavConfig;
import com.baidu.navisdk.ui.routeguide.BNavigator;
import com.baidu.navisdk.ui.widget.RoutePlanObserver;
import com.baidu.navisdk.util.common.PreferenceHelper;
import com.baidu.navisdk.util.common.ScreenUtil;
import com.baidu.nplatform.comapi.basestruct.GeoPoint;
import com.baidu.nplatform.comapi.map.MapGLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.baidu.navisdk.comapi.mapcontrol.BNMapController;

@SuppressLint("NewApi")
public class TangShanMapActivity extends Activity 
{	
	Map<String,GeoPoint> myMap=null;//用于存放位置名称和位置经纬度的map	
	private RoutePlanModel mRoutePlanModel = null;//用于路线规划的对象
	private MapGLSurfaceView mMGLMapView = null;//用于绘制
	GeoPoint start;//起点，构造器参数为经度，纬度
	GeoPoint end;//终点
	String strFrom=null;//起点字符串
	String strTo=null;//终点字符串	
	 int eX; //经度
     int eY;  //纬度	
	protected void onCreate(Bundle savedInstanceState) 
	{//继承Activity必须重写的方法
		super.onCreate(savedInstanceState);		//调用父类方法        
		setContentView(R.layout.ditu);		//切换到主界面
		eX=this.getIntent().getIntExtra("longN", (int)(118.164013f*1E5));//经度
		eY=this.getIntent().getIntExtra("latN",(int)(39.625656f*1E5));//纬度		
		final Button bStart=(Button)TangShanMapActivity.this.findViewById(R.id.b01);
		final LinearLayout ll1=(LinearLayout)TangShanMapActivity.this.findViewById(R.id.l1);
		ll1.setVisibility(View.GONE);//设为不可见
		final LinearLayout ll2=(LinearLayout)TangShanMapActivity.this.findViewById(R.id.l2);
		ll2.setVisibility(View.GONE);//设为不可见
		final LinearLayout ll3=(LinearLayout)TangShanMapActivity.this.findViewById(R.id.l3);
		ll3.setVisibility(View.GONE);//设为不可见
		bStart.setOnClickListener(
				new View.OnClickListener() 
				{
					@Override
					public void onClick(View arg0) 
					{
						initSpinner(bStart,ll1,ll2,ll3);
					}
				});	
		
		findViewById(R.id.online_calc_btn).setOnClickListener(new OnClickListener() {
			//在线规划按钮
			@Override
			public void onClick(View arg0) {
				startCalcRoute(NL_Net_Mode.NL_Net_Mode_OnLine);
			}
		});

		findViewById(R.id.simulate_btn).setOnClickListener(
				//模拟导航按钮
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						startNavi(false);
					}
				});

		findViewById(R.id.real_btn).setOnClickListener(new OnClickListener() {
			//真实导航按钮
			@Override
			public void onClick(View arg0) {
				PreferenceHelper.getInstance(getApplicationContext())
						.putBoolean(SettingParams.Key.SP_TRACK_LOCATE_GUIDE,
								false);
				startNavi(true);
			}
		});
		findViewById(R.id.gps_dw).setOnClickListener(new OnClickListener() {
			//GPS定位
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(TangShanMapActivity.this,LocationActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
		BNRoutePlaner.getInstance().setRouteResultObserver(null);
		((ViewGroup) (findViewById(R.id.mapview_layout))).removeAllViews();
		BNMapController.getInstance().onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		initMapView();
		((ViewGroup) (findViewById(R.id.mapview_layout))).addView(mMGLMapView);
		BNMapController.getInstance().onResume();
	}
	//初始化下拉列表
	public void initSpinner(final Button bStart,final LinearLayout ll1,final LinearLayout ll2,final LinearLayout ll3)
	{
		bStart.setVisibility(View.GONE);//设置不可见
		setView(bStart,ll1,ll2,ll3);
		String[] stations={"唐山站","唐山北","唐山西站汽车站","唐山东站汽车站"};
		String[] location={"河北联合大学本部","河北联合大学建设路校区","河北联合大学轻工学院","河北联合大学冀唐学院","河北联合大学北校区"};
		initHashMap();
		final Spinner spinner1=(Spinner)this.findViewById(R.id.spinner01);//初始化Spinner对象
		final Spinner spinner2=(Spinner)this.findViewById(R.id.spinner02);//初始化Spinner对象
		 //第一个参数是上下文
        //第二个参数是android sdk中自己内置的一个布局，就是将每一条数据都显示在这个view上面
        //第三个参数就是我们要显示的数据
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(TangShanMapActivity.this,android.R.layout.simple_spinner_item,stations);
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(TangShanMapActivity.this,android.R.layout.simple_spinner_item,location);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);  //设置下拉列表的风格  
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);  //设置下拉列表的风格  
        spinner1.setAdapter(adapter);//为spinner1添加适配器
        spinner2.setAdapter(adapter2);//为spinner2添加适配器
        spinner1.setVisibility(View.VISIBLE);//设置可视  
        spinner2.setVisibility(View.VISIBLE);//设置可视
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() 
        {              
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				strFrom=spinner1.getSelectedItem().toString();//获得路线起点字符串
				start=myMap.get(strFrom);//获得起点的经纬度
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}                            
        });          
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() 
        {              
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				strTo=spinner2.getSelectedItem().toString();//获得路线终点字符串
				end=myMap.get(strTo);//获得终点的经纬度
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}                            
        }); 
        Button bend=(Button)TangShanMapActivity.this.findViewById(R.id.gps_btn);
        bend.setOnClickListener(
        		new View.OnClickListener() 
        		{
					@Override
					public void onClick(View arg0) 
					{
						bStart.setVisibility(View.VISIBLE);
						setView(bStart,ll1,ll2,ll3);
					}
				});
	}
	
	//初始化搜索按钮、ll1、ll2以及搜索路线按钮
	public void setView(Button v1,LinearLayout v2,LinearLayout v3,LinearLayout v4)
	{
		if(v1.getVisibility()==0)
		{//v1可见
			v2.setVisibility(View.GONE);
			v3.setVisibility(View.GONE);
			v4.setVisibility(View.GONE);
		}
		else if(v1.getVisibility()==8)
		{//v1不可见
			v2.setVisibility(View.VISIBLE);
			v3.setVisibility(View.VISIBLE);
			v4.setVisibility(View.VISIBLE);
		}
	}

	
	public  void initHashMap()
	{
		if(myMap==null)
		{
			myMap=new HashMap<String,GeoPoint>();
		}		 
		myMap.put("唐山站", new GeoPoint((int)(39.62574f* 1E5),(int)(118.11847f* 1E5)));
		myMap.put("唐山北", new GeoPoint((int)(39.816742f*1E5),(int)(118.120078f*1E5)));
		myMap.put("唐山西站汽车站", new GeoPoint((int)(39.584502f*1E5),(int)(118.212741f*1E5)));
		myMap.put("唐山东站汽车站", new GeoPoint((int)(39.625484f*1E5),(int)(118.228568f*1E5)));
		myMap.put("河北联合大学本部", new GeoPoint((int)(39.625656f*1E5),(int)(118.164013f*1E5)));
		myMap.put("河北联合大学北校区", new GeoPoint((int)(39.67669f*1E5),(int)(118.161297f*1E5)));		
		myMap.put("河北联合大学建设路校区", new GeoPoint((int)(39.636468f*1E5),(int)(118.180319f*1E5)));
		myMap.put("河北联合大学冀唐学院", new GeoPoint((int)(39.583109f*1E5),(int)(118.142250f*1E5)));
		myMap.put("河北联合大学轻工学院", new GeoPoint((int)(39.636496f*1E5),(int)(118.064850f*1E5)));	
	}

	//初始化mMGLMapView
	 private void initMapView() {		 
        if (Build.VERSION.SDK_INT < 14) {// 版本号小于14
            BaiduNaviManager.getInstance().destroyNMapView();//释放导航视图（地图）
        }
        mMGLMapView = BaiduNaviManager.getInstance().createNMapView(this);//创建导航视图（地图）
        BNMapController.getInstance().setLevel(14);//设置地图放大比例尺
        BNMapController.getInstance().setLayerMode(LayerMode.MAP_LAYER_MODE_BROWSE_MAP);
        updateCompassPosition();//更新指南针
        BNMapController.getInstance().locateWithAnimation(eX, eY);//设置地图的中心位置
	}
		
		/**
		 * 更新指南针位置
		 */
	private void updateCompassPosition(){
		int screenW = this.getResources().getDisplayMetrics().widthPixels;//获得屏幕宽度
		BNMapController.getInstance().resetCompassPosition(//设置指南针的位置
				screenW - ScreenUtil.dip2px(this, 30),ScreenUtil.dip2px(this, 126), -1);
	}

	private void startCalcRoute(int netmode) {
		//获取输入的起终点
		int sX = 0, sY = 0, eX = 0, eY = 0;
		try {
			sX = start.getLongitudeE6();//获得经度
			sY =start.getLatitudeE6();//获得维度
			eX =end.getLongitudeE6();//获得经度
			eY =end.getLatitudeE6();//获得维度
		} catch (Exception e) {
			e.printStackTrace();
		}
		//起点
		RoutePlanNode startNode = new RoutePlanNode(sX, sY,
				RoutePlanNode.FROM_MAP_POINT, strFrom, strFrom);
		//终点
		RoutePlanNode endNode = new RoutePlanNode(eX, eY,
				RoutePlanNode.FROM_MAP_POINT, strTo, strTo);
		//将起终点添加到nodeList
		ArrayList<RoutePlanNode> nodeList = new ArrayList<RoutePlanNode>(2);
		nodeList.add(startNode);//添加起点
		nodeList.add(endNode);//添加终点
		//设置观察者
		BNRoutePlaner.getInstance().setObserver(new RoutePlanObserver(this, null));
		//设置算路方式
		BNRoutePlaner.getInstance().setCalcMode(NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME);
		// 设置算路结果回调
		BNRoutePlaner.getInstance().setRouteResultObserver(mRouteResultObserver);
		// 设置起终点并算路
		boolean ret = BNRoutePlaner.getInstance().setPointsToCalcRoute(
				nodeList,NL_Net_Mode.NL_Net_Mode_OnLine);
		if(!ret){
			Toast.makeText(this, "规划失败", Toast.LENGTH_SHORT).show();
		}
	}

	private void startNavi(boolean isReal) {
		if (mRoutePlanModel == null) {
			Toast.makeText(this, "请先算路！", Toast.LENGTH_LONG).show();
			return;
		}
		// 获取路线规划结果起点
		RoutePlanNode startNode = mRoutePlanModel.getStartNode();
		// 获取路线规划结果终点
		RoutePlanNode endNode = mRoutePlanModel.getEndNode();
		if (null == startNode || null == endNode) {
			return;
		}
		// 获取路线规划算路模式
		int calcMode = BNRoutePlaner.getInstance().getCalcMode();
		Bundle bundle = new Bundle();//创建Bundle对象
		bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_VIEW_MODE,
				BNavigator.CONFIG_VIEW_MODE_INFLATE_MAP);
		bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_CALCROUTE_DONE,
				BNavigator.CONFIG_CLACROUTE_DONE);
		bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_START_X,
				startNode.getLongitudeE6());
		bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_START_Y,
				startNode.getLatitudeE6());
		bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_END_X, endNode.getLongitudeE6());
		bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_END_Y, endNode.getLatitudeE6());
		bundle.putString(BNavConfig.KEY_ROUTEGUIDE_START_NAME,
				mRoutePlanModel.getStartName(this, false));
		bundle.putString(BNavConfig.KEY_ROUTEGUIDE_END_NAME,
				mRoutePlanModel.getEndName(this, false));
		bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_CALCROUTE_MODE, calcMode);
		if (!isReal) {
			// 模拟导航
			bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_LOCATE_MODE,
					RGLocationMode.NE_Locate_Mode_RouteDemoGPS);
		} else {
			// GPS 导航
			bundle.putInt(BNavConfig.KEY_ROUTEGUIDE_LOCATE_MODE,
					RGLocationMode.NE_Locate_Mode_GPS);
		}
		
		Intent intent = new Intent(TangShanMapActivity.this, BNavigatorActivity.class);//创建Intent对象
		intent.putExtras(bundle);//添加Bundle对象
		startActivity(intent);//切换Activity
	}
	//算路结果监听器IRouteResultObserver
	private IRouteResultObserver mRouteResultObserver = new IRouteResultObserver() {

		@Override
		public void onRoutePlanYawingSuccess() {
		}

		@Override
		public void onRoutePlanYawingFail() {
		}

		@Override
		public void onRoutePlanSuccess() {
			BNMapController.getInstance().setLayerMode(//设置地图层模式
					LayerMode.MAP_LAYER_MODE_ROUTE_DETAIL);
			mRoutePlanModel = (RoutePlanModel) NaviDataEngine.getInstance()//设置路线模型
					.getModel(ModelName.ROUTE_PLAN);
		}

		@Override
		public void onRoutePlanFail() {
		}

		@Override
		public void onRoutePlanCanceled() {
		}

		@Override
		public void onRoutePlanStart() {
		}
	};
}
