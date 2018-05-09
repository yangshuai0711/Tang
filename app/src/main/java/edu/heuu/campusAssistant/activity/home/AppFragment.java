package edu.heuu.campusAssistant.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.heuu.campusAssistant.map.R;

/**
 * Created by ÎÄ¾²ÃÀÀöĞ¡TeTe on 2018/5/6.
 */

public class AppFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app, null);
        return view;
    }
}
