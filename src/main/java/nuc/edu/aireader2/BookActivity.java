package nuc.edu.aireader2;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    List<BookData> appList = new ArrayList<>();
    private TextView baname_tv;
    private TextView bainfo_tv;
    private GridView gridView;
    private TextView star_btn;
    private SharedPreferences sharedPreferences;
    private boolean isStar;
    private TextView plan_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        baname_tv = (TextView)findViewById(R.id.ba_nametv);
        bainfo_tv = (TextView)findViewById(R.id.ba_infotv);
        star_btn = (TextView) findViewById(R.id.star_tv);
        plan_tv = (TextView)findViewById(R.id.plan_tv);
        plan_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 3:
                                Toast.makeText(BookActivity.this, "计划通", Toast.LENGTH_SHORT).show();
                                break;
                            case 30:
                                Log.i("ssss", "fail");
                                break;
                        }
                        super.handleMessage(msg);
                    }
                };
                sharedPreferences = getSharedPreferences("Login", 0);
                String name = sharedPreferences.getString("Account", "");
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("method", "_PUT");
                params.put("ID", name);
                params.put("Plan", baname_tv.getText().toString());
                client.post("http://10.0.2.2:8000/android_user/", params, new MyTextListener(handler, 3, 30));

            }
        });
        star_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStar){

                    Handler handler = new Handler() {
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case 3:
                                    Toast.makeText(BookActivity.this, "取消收藏", Toast.LENGTH_LONG).show();
                                    star_btn.setTextColor(getResources()
                                            .getColor(R.color.btnclr));

                                    star_btn.setCompoundDrawablesWithIntrinsicBounds(0,
                                            R.mipmap.star, 0, 0);
                                    break;
                                case 30:
                                    Log.i("ssss", "fail");
                                    break;
                            }
                            super.handleMessage(msg);
                        }
                    };
                    sharedPreferences = getSharedPreferences("Login", 0);
                    String name = sharedPreferences.getString("Account", "");
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("method", "_DELETE");
                    params.put("db", "Collect");
                    params.put("ID", name);
                    params.put("Pname", baname_tv.getText().toString());
                    client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));

                }
                else {
                    Handler handler = new Handler() {
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case 3:
                                    Toast.makeText(BookActivity.this, "收藏成功！", Toast.LENGTH_LONG).show();
                                    star_btn.setTextColor(getResources()
                                            .getColor(R.color.bule));

                                    star_btn.setCompoundDrawablesWithIntrinsicBounds(0,
                                            R.mipmap.star_press, 0, 0);
                                    break;
                                case 30:
                                    Log.i("ssss", "fail");
                                    break;
                            }
                            super.handleMessage(msg);
                        }
                    };
                    sharedPreferences = getSharedPreferences("Login", 0);
                    String name = sharedPreferences.getString("Account", "");
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("method", "_POST");
                    params.put("db", "Collect");
                    params.put("ID", name);
                    params.put("Pname", baname_tv.getText().toString());
                    client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.bp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); // 显示按钮
        getSupportActionBar().setHomeButtonEnabled(true); // 可点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getStringExtra("currentPlan")!=null)  {
            String album = getIntent().getStringExtra("currentPlan");
            initData(album);

        }
        else if(getIntent()!=null){
            String name = getIntent().getStringExtra("album_name");
            initData(name);
        }
    }
    private void initData(String name){
    //加载适配器
        gridView = (GridView)findViewById(R.id.ba_gv);
        // Inflate the layout for this fragment
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        List<HashMap<String,String>> list = JSONTOOL.analyze_some_json(msg.obj.toString());
                        for(HashMap<String,String> map : list)
                        {
                            appList.add(new BookData(map.get("Name")));
                        }
                        Log.i("ssss","目录加载成功");
                        gridView.setAdapter(new GridAdapter2(BookActivity.this,appList));
                        break;
                    case 10:
                        Log.i("ssss","目录加载失败");
                        break;
                    case 3:
                        HashMap<String,String> map = JSONTOOL.analyze_once_json(msg.obj.toString());
                        Log.i("ssss","name"+map.get("Econtent"));
                        String Name = map.get("Name");
                        baname_tv.setText(Name);
                        bainfo_tv.setText("英文"+Name+"专辑");
                        break;
                    case 30:
                        Log.i("ssss","fail");
                        break;
                }
                super.handleMessage(msg);
            }
        };
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", "_GET");
        params.put("db", "Album");
        params.put("Name",name);
        client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));
        //client.post("http://192.168.43.24:8000/android/", params, new MyTextListener(handler, 3, 30));
        params = new RequestParams();
        params.put("method", "_GET");
        params.put("db", "Passage");
        params.put("From",name);
        client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 1, 10));
        //client.post("http://192.168.43.24:8000/android/", params, new MyTextListener(handler, 1, 10));
    }
}
