package edu.heuu.campusAssistant.activity;

import edu.heuu.campusAssistant.map.TangShanMapActivity;
import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.map.SchoolMapActivity;
import edu.heuu.campusAssistant.util.FontManager;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class MainActivityGroup extends MZActivityGroup {
    //加载的Activity的名字，LocalActivityManager就是通过这些名字来查找对应的Activity的。
    private static final String CONTENT_ACTIVITY_NAME_0 = "LianHeActivity";
    private static final String CONTENT_ACTIVITY_NAME_1 = "ReProActivity";
    private static final String CONTENT_ACTIVITY_NAME_2 = "TangShanMapActivity";
    private static final String CONTENT_ACTIVITY_NAME_3 = "SchoolMapActivity";
    private static final String CONTENT_ACTIVITY_NAME_4 = "MoreActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
		//用自定义的字体方法
        FontManager.init(this);
 	    FontManager.changeFonts(FontManager.getContentView(this),this);
        
        initRadioBtns();
        ((RadioButton)findViewById(R.id.radio_button0)).setChecked(true);
    }
   
    /**
     * 找到自定义id的加载Activity的View
     */
    protected ViewGroup getContainer() 
    {
        return (ViewGroup) findViewById(R.id.container);
    }
    
    /**
     * 初始化按钮
     */
    protected void initRadioBtns() 
    {
        initRadioBtn(R.id.radio_button0);
        initRadioBtn(R.id.radio_button1);
        initRadioBtn(R.id.radio_button2);
        initRadioBtn(R.id.radio_button3);
        initRadioBtn(R.id.radio_button4);
    }
    /**
     * 导航按钮被点击时，具体发生的变化
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
    {
        if (isChecked) 
        {
            switch (buttonView.getId()) 
            {
            	case R.id.radio_button0:
            		setContainerView(CONTENT_ACTIVITY_NAME_0, LianHeActivity.class);
            	break;
            	case R.id.radio_button1:
            		setContainerView(CONTENT_ACTIVITY_NAME_1, ReProActivity.class);
            	break;
            	case R.id.radio_button2:
            		setContainerView(CONTENT_ACTIVITY_NAME_2, TangShanMapActivity.class);
                break;
            	case R.id.radio_button3:
            		setContainerView(CONTENT_ACTIVITY_NAME_3, SchoolMapActivity.class);
                break;
            	case R.id.radio_button4:
            		setContainerView(CONTENT_ACTIVITY_NAME_4, MoreActivity.class);
                break;
            	default:
                break;
            }
        }
    }
} 