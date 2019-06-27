package nuc.edu.aireader2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    List<BookData> appList = new ArrayList<>();
    private GridView gridView;
    private SharedPreferences sharedPreferences;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

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
        if(getIntent()!=null){

            initData();
        }
    }
    private void initData(){
        //加载适配器
        gridView = (GridView)findViewById(R.id.collcet_gv);
        // Inflate the layout for this fragment
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        List<HashMap<String,String>>list = JSONTOOL.analyze_some_json(msg.obj.toString());
                       appList =  new ArrayList<>();

                        Handler handler = new Handler() {
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case 1:
                                        HashMap<String,String> map = JSONTOOL.analyze_once_json(msg.obj.toString());
                                        appList.add(new BookData(R.mipmap.cover1,map.get("Name")+"专辑"));
                                        i--;
                                        break;
                                    case 10:
                                        Log.i("ssss","fail10");
                                        break;}
                                if(i==0)
                                {
                                    Log.i("ssss","applist:"+appList.size());
                                    gridView.setAdapter(new GridAdapter(CollectActivity.this,appList));
                                }

                            }

                        };
                                        AsyncHttpClient client = new AsyncHttpClient();
                                        RequestParams params = new RequestParams();

                                        for( HashMap<String,String> map: list){
                                            i++;
                                            params = new RequestParams();
                                            params.put("method", "_GET");
                                            params.put("db", "Album");
                                            params.put("Name",map.get("Pname"));
                                            client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 1, 10));
                                        }

                        break;
                    case 30:
                        Log.i("ssss","fail");
                        break;
                }
                super.handleMessage(msg);
            }
        };
        sharedPreferences = getSharedPreferences("Login",0);
        String id = sharedPreferences.getString("Account","");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", "_GET");
        params.put("db", "Collect");
        params.put("ID",id);
        client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));


    }
}
