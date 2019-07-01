package nuc.edu.aireader2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

public class SocialFragment extends Fragment {
  private TextView day_cn;
  private TextView day_en;
  private CheckBox ck_trans;
  private Button change_btn;


    public SocialFragment() {
        // Required empty public constructor
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    HashMap map = JSONTOOL.analyze_once_json(msg.obj.toString());
                    day_en.setText(map.get("ensentence").toString());
                    day_cn.setText(map.get("cnsentence").toString());

//                    Toast t = Toast.makeText(getContext(), "成功", Toast.LENGTH_LONG);//信息框
//                    t.show();//调用
                    break;
                case 30:
                     Toast.makeText(getContext(), "失败", Toast.LENGTH_LONG).show();//信息框

                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        day_en = (TextView)view.findViewById(R.id.day_en);
        day_cn = (TextView)view.findViewById(R.id.day_cn);
        ck_trans = (CheckBox)view.findViewById(R.id.ck_trans);
        change_btn = (Button)view.findViewById(R.id.change_btn);

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("db", "Day_sentence");
                client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));
            }
        });
        ck_trans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    day_cn.setVisibility(View.VISIBLE);

                }
                else
                    day_cn.setVisibility(View.GONE);
            }

        });

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("db", "Day_sentence");
        client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(this.handler, 3, 30));

        return view;
    }


}
