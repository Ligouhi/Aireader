package nuc.edu.aireader2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

@SuppressLint("ValidFragment")
class CaptialFragment extends Fragment {
    public CaptialFragment() {
        // Required empty public constructor
    }
    private TextView albname;
    private SharedPreferences sharedPreferences;
    private Button readbtn;
    private ImageView cpimg;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_captial, container, false);
        albname = (TextView)view.findViewById(R.id.albname);
        readbtn = (Button)view.findViewById(R.id.readbtn);

        cpimg = (ImageView)view.findViewById(R.id.cpimg);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        final HashMap<String,String> map = JSONTOOL.analyze_once_json(msg.obj.toString());
                        albname.setText(map.get("Plan")+"专辑");
                        readbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(),BookActivity.class);
                                //需要穿的参数
                                intent.putExtra("currentPlan",map.get("Plan"));
                                startActivity(intent);
                                }
                        });
                        Handler handler = new Handler() {
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case 3:
                                        final HashMap<String,String> map1 = JSONTOOL.analyze_once_json(msg.obj.toString());
                                        Log.i("aaaaa",map1.get("Img"));
                                        cpimg.setImageResource(Integer.parseInt(map1.get("Img"),16));
                                        break;
                                    case 30:
                                        Log.i("ssss", "fail");
                                        break;
                                }
                                super.handleMessage(msg);
                            }
                        };
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        params.put("method", "_GET");
                        params.put("Name", map.get("Plan"));
                        params.put("db", "Album");
                        client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));
                        //client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));

                        break;
                    case 30:
                        Log.i("ssss", "fail");
                        break;
                }
                super.handleMessage(msg);
            }
        };
        sharedPreferences = getContext().getSharedPreferences("Login", 0);
        String name = sharedPreferences.getString("Account", "");
        String psw = sharedPreferences.getString("Password", "");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", "_GET");
        params.put("ID", name);
        params.put("Password", psw);
        client.post("http://10.0.2.2:8000/android_user/", params, new MyTextListener(handler, 3, 30));

    }
}
