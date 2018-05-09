package edu.heuu.campusAssistant.activity;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.app.Activity;

import edu.heuu.campusAssistant.activity.home.AppFragment;
import edu.heuu.campusAssistant.activity.home.MineFragment;
import edu.heuu.campusAssistant.activity.home.MyFragment;
import edu.heuu.campusAssistant.login.LoginActivity;
import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.TangCrypt;

/**
 * Created by 文静美丽小TeTe on 2018/5/6.
 */

public class HomeActivity extends FragmentActivity {
    private TextView tv_item_one;
    private TextView tv_item_two;
    private TextView tv_item_three;
    private ViewPager myViewPager;
    private List<Fragment> list;
    private TabFragmentPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        InitView();

        //把Fragment添加到List集合里面
        list = new ArrayList<Fragment>();
        list.add(new AppFragment());
        list.add(new MineFragment());
        list.add(new MyFragment());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面
        tv_item_one.setBackgroundColor(Color.GREEN);//被选中就为红色
        tv_item_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_item_one.setBackgroundColor(Color.GREEN);//被选中就为红色
                tv_item_two.setBackgroundColor(Color.WHITE);//被选中就为红色
                tv_item_three.setBackgroundColor(Color.WHITE);//被选中就为红色
                myViewPager.setCurrentItem(0);  //初始化显示第一个页面
            }
        });

        tv_item_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_item_one.setBackgroundColor(Color.WHITE);//被选中就为红色
                tv_item_two.setBackgroundColor(Color.GREEN);//被选中就为红色
                tv_item_three.setBackgroundColor(Color.WHITE);//被选中就为红色
                myViewPager.setCurrentItem(1);  //初始化显示第一个页面
            }
        });

        tv_item_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_item_one.setBackgroundColor(Color.WHITE);//被选中就为红色
                tv_item_two.setBackgroundColor(Color.WHITE);//被选中就为红色
                tv_item_three.setBackgroundColor(Color.GREEN);//被选中就为红色
                myViewPager.setCurrentItem(2);  //初始化显示第一个页面
            }
        });
    }

    /**
     * 初始化控件
     */
    private void InitView() {
        tv_item_one = (TextView) findViewById(R.id.tv_item_one);
        tv_item_two = (TextView) findViewById(R.id.tv_item_two);
        tv_item_three = (TextView) findViewById(R.id.tv_item_three);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
    }

}
