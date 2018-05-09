package edu.heuu.campusAssistant.activity.home;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.heuu.campusAssistant.activity.HomeActivity;
import edu.heuu.campusAssistant.login.LoginActivity;
import edu.heuu.campusAssistant.login.RegActivity;
import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.TangClient;

/**
 * Created by 文静美丽小TeTe on 2018/5/6.
 */

public class MineFragment extends Fragment {
    TangClient cli;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
    }
    */

    public void init(View v){
        cli = new TangClient(v.getContext());
        ((TextView)v.findViewById(R.id.mine_tv_mine))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class GetMineInfoTask extends AsyncTask<String, String, Integer> {
        protected Integer doInBackground(String... params) {
            String account = params[0];
            String passwd = params[1];

            publishProgress("登陆中..." + account + ";" + passwd);
            String ret = cli.post("hello", "{}");
            publishProgress("登陆返回"+ret);
            return 1;
        }

        protected void onProgressUpdate(String... progress) {

        }


        protected void onPostExecute(Integer result) {
            if(result == 1){
            }
        }
    }
}
