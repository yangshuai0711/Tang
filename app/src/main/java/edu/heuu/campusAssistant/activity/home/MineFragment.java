package edu.heuu.campusAssistant.activity.home;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.heuu.campusAssistant.map.R;
import edu.heuu.campusAssistant.util.Global;
import edu.heuu.campusAssistant.util.TangClient;
import edu.heuu.campusAssistant.util.TangJson;

/**
 * Created by 文静美丽小TeTe on 2018/5/6.
 */

public class MineFragment extends Fragment {
    final int STATUS_MINING = 1;
    final int STATUS_MINE_RIPE = 2;
    final int STATUS_MINE_LOST = 3;
    TangClient cli;
    int t_left, status, background_timing;
    double balance, power, mine_val;

    TextView tv_balance, tv_power, tv_time;
    TextView tv_mine;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        init(view);
        return view;
    }
    public void init(View v){
        background_timing = 0;
        cli = new TangClient(v.getContext());
        tv_balance = (TextView)v.findViewById(R.id.mine_tv_balance);
        tv_power = (TextView)v.findViewById(R.id.mine_tv_power);
        tv_time = (TextView)v.findViewById(R.id.mine_tv_time);
        tv_mine = (TextView)v.findViewById(R.id.mine_tv_mine);
        context = v.getContext();
        tv_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CollectMineTask().execute();
            }
        });
    }
    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
        new GetMineInfoTask().execute();
    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
    }
    */

    private class GetMineInfoTask extends AsyncTask<String, String, Integer> {
        protected Integer doInBackground(String... params) {
            String account = Global.phone;
            TangJson tangJson = new TangJson();
            tangJson.put("phone", account);
            String req = tangJson.toString();
            String ret = cli.post("mine/info", req);

            TangJson tangJson_ret = new TangJson(ret);
            int ret_code = tangJson_ret.getInt("ret");
            if(ret_code == 1) {
                TangJson data = tangJson_ret.getTangJsonbject("data");
                TangJson produce = data.getTangJsonbject("produce");
                if (produce == null) {
                    // trigger refresh mine
                    t_left = 0;
                    status = STATUS_MINE_RIPE;
                } else {
                    t_left = produce.getInt("t_left");
                    status = produce.getInt("status");
                    mine_val = produce.getDouble("val");
                }

                balance = data.getDouble("balance");
                power = data.getDouble("power");

                return 1;
            }
            return 0;
        }

        protected void onPostExecute(Integer result) {
            if(result == 1){
                update_balance(balance);
                update_power(power);
                update_mine_status(status, t_left);
            }

            if(t_left > 0){
                if(background_timing == 0){
                    background_timing = 1;
                    new BackgroundMineTimingTask().execute();
                }
            }
        }
    }


    private class CollectMineTask extends AsyncTask<String, String, Integer> {
        protected Integer doInBackground(String... params) {
            String account = Global.phone;
            TangJson tangJson = new TangJson();
            tangJson.put("phone", account);
            String req = tangJson.toString();
            // trigger collect
            String ret = cli.post("mine/collect", req);
            TangJson data = new TangJson(ret).getTangJsonbject("data");
            if(data != null){
                String msg = data.getString("msg");
                publishProgress(msg);
            }
            return 1;
        }

        protected void onProgressUpdate(String... progress) {
            Toast.makeText(context, progress[0], Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(Integer result) {
            new GetMineInfoTask().execute();
        }
    }

    private class BackgroundMineTimingTask extends AsyncTask<String, String, Integer> {
        protected Integer doInBackground(String... params) {
            while (true) {
                try {
                    if(t_left < 0){
                        return 1;
                    }

                    Thread.sleep(1000);
                    t_left--;
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

        }
        protected void onProgressUpdate(String... progress) {
            if (t_left < 0) {
                update_mine_status(STATUS_MINE_RIPE, t_left);
            }else{
                update_mine_status(STATUS_MINING, t_left);
            }
        }

        protected void onPostExecute(Integer result) {

            if(result == 1){
                if (t_left < 0) {
                    update_mine_status(STATUS_MINE_RIPE, t_left);
                }else{
                    update_mine_status(STATUS_MINING, t_left);
                }
            }
            background_timing = 0;
        }
    }


    public void update_balance(double balance){
        tv_balance.setText("总资产：" + String.valueOf(balance));
    }
    public void update_power(double power){
        tv_power.setText("算力值：" + String.valueOf(power));
    }

    public void update_mine_status(int status, int ts_left){
        if(ts_left < 0){
            ts_left = 0;
        }

        if(status == STATUS_MINING){
            tv_mine.setText("挖矿中["+ ts_to_time(ts_left) + "]");
        }else if(status == STATUS_MINE_RIPE){
            tv_mine.setText("成功挖矿:" + String.valueOf(mine_val));
        }else if(status == STATUS_MINE_LOST){
            tv_mine.setText("矿已流失");
        }
        tv_time.setText("倒计时：" + ts_to_time(ts_left));
    }

    public String ts_to_time(int ts){
        int hour = ts / 3600;
        ts = ts - hour * 3600;
        int min = ts / 60;
        ts = ts - min * 60;
        int sec =  ts;
        return String.valueOf(hour) + "小时" + String.valueOf(min) + "分钟" + String.valueOf(sec) + "秒";
    }
}
