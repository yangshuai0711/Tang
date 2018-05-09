package edu.heuu.campusAssistant.activity;

import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by �ľ�����СTeTe on 2018/5/6.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mfragmentManager;
    private List<Fragment> mlist;

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mlist = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mlist.get(arg0);//��ʾ�ڼ���ҳ��
    }

    @Override
    public int getCount() {
        return mlist.size();//�м���ҳ��
    }
}
