package edu.heuu.campusAssistant.activity;

import edu.heuu.campusAssistant.util.FontManager;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;

/**
 * �Լ�ʵ�ֵ�һ��ͨ��ActivityGroup��
 * ����ͨ���򵥵���д���������е�����ť���õ�����ť���ƶ�̬����Activity��ActivityGroup��
 * ��������Ҫ��ʵ������ʵ������������
 *     1.ָ����̬����Activity�������Ķ���getContainer()������
 *     2.��ʼ�����еĵ�����ť��initRadioBtns()������������Ҫ�������еĵ�����ť��ִ��initRadioBtn(int id)������
 *     3.ʵ�ֵ�����ť�����������ľ��巽����onCheckedChanged(...)���������������ʵ��ĳ��������ť��Ҫ������Ӧ��Activity�Ĺ�����ϵ�����Ե���setContainerView(...)������
 * @author zet
 *
 */
@SuppressWarnings("deprecation")
public abstract class MZActivityGroup extends ActivityGroup implements
CompoundButton.OnCheckedChangeListener{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//���Զ�������巽��
 	    FontManager.changeFonts(FontManager.getContentView(this),this);
        initRadioBtns();
    }
    
    //����Activity��View����������Ӧ����ViewGroup������
    private ViewGroup container;
    private LocalActivityManager localActivityManager;
    /**
     * ����Activity��View������id�����ǹ̶��ģ����������򽻸�������
     * �����߿����ڲ����ļ����Զ�����id��ͨ����д�������������View�����Ķ���
     * @return
     */
    abstract protected ViewGroup getContainer();
    /**
     * ��ʵ������ã����ݵ�����ťid��ʼ����ť
     * @param id
     */
    protected void initRadioBtn(int id)
    {
    	RadioButton btn = (RadioButton) findViewById(id);
        btn.setOnCheckedChangeListener(this);
    }
    
    /**
     * �����߱�����д�������������������ʼ�����еĵ�����ť
     */
    abstract protected void initRadioBtns();
    /**
     * Ϊ����Activity��ʼ��Intent��Ϣ
     * @param cls
     * @return
     */
    private Intent initIntent(Class<?> cls)
    {
        return new Intent(this,cls).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
    /**
     * ����������ʵ�����е��ã��ܽ�Activity�����ڵ�Activity�Ƴ����ٽ�ָ����ĳ��Activity����
     * @param activityName ���ص�Activity��localActivityManager�е�����
     * @param activityClassTye    Ҫ����Activity������
     */
    protected void setContainerView(String activityName, Class<?> activityClassTye)
    {
        if(null == localActivityManager)
        {
            localActivityManager = getLocalActivityManager();
        }
        if(null == container)
        {
            container = getContainer();
        }
        //�Ƴ����ݲ���ȫ����View
        container.removeAllViews();
		Activity contentActivity = localActivityManager.getActivity(activityName);
        if((activityName.equals("TangShanMapActivity"))||(activityName.equals("SchoolMapActivity")))
        {
        	localActivityManager.startActivity(activityName, initIntent(activityClassTye));
        }else if (null == contentActivity) 
        {
            localActivityManager.startActivity(activityName, initIntent(activityClassTye));
        }
        //����Activity
        container.addView(
                localActivityManager.getActivity(activityName).getWindow().getDecorView(),
                new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
    }
}