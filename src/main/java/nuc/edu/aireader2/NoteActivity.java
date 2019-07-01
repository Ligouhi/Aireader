package nuc.edu.aireader2;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    List<NoteData> applist = new ArrayList<>();;
   GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nt_toolbar);
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



        gridView = (GridView)findViewById(R.id.nt_gv);
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        List<HashMap<String,String>> list = JSONTOOL.analyze_some_json(msg.obj.toString());
                        for(HashMap<String,String>map:list){
                            applist.add(new NoteData(map.get("Passage"),map.get("Note")));
                        }
                        gridView.setAdapter(new GridAdapter3(NoteActivity.this,applist));
                        Toast.makeText(NoteActivity.this, "计划通", Toast.LENGTH_SHORT).show();
                        break;
                    case 30:
                        Log.i("ssss", "fail");
                        break;
                }
                super.handleMessage(msg);
            }
        };
        SharedPreferences sharedPreferences = getSharedPreferences("Login", 0);
        String name = sharedPreferences.getString("Account", "");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("method", "_GET");
        params.put("db", "Note");
        params.put("ID", name);
        client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));

    }
}
