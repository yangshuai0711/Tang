package edu.heuu.campusAssistant.activity.home;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.Global;
import edu.heuu.campusAssistant.util.TangClient;


/**
 * Created by ÎÄ¾²ÃÀÀöÐ¡TeTe on 2018/5/6.
 */

public class MyFragment extends Fragment {
    TextView tv_balance;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        init(view);
        return view;
    }

    public void init(View v){
        tv_balance = (TextView)v.findViewById(R.id.profile_tv_balance);
        tv_balance.setText(Global.decimal_str((float) Global.balance, 2) + ">");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            tv_balance.setText(Global.decimal_str((float) Global.balance, 2) + ">");
        }
    }
}
