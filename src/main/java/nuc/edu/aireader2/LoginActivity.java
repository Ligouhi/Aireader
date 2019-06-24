package nuc.edu.aireader2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_PERMISSION_CODE = 1;

    private static String[] PERMISSIONS_INTERNET = {
            Manifest.permission.INTERNET};

    static Toast t;


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    HashMap map = JSONTOOL.analyze_once_json(msg.obj.toString());
//                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
////                        JSONObject jsonArray = new JSONObject(msg.obj.toString());
////                        for (int i=0; i < jsonArray.length(); i++)    {
////                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            String id = jsonObject.getString("ID");
                    Log.i("LoginActivity", (String) map.get("ID"));
//                        }

                    t = Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG);//信息框
                    t.show();//调用
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("uid",(String) map.get("ID"));
                    startActivity(intent);
                    LoginActivity.this.finish();
                    break;
                case 30:
                    t = Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_LONG);//信息框
                    t.show();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    Button login_btn;
    EditText uid;
    EditText psw;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);


        uid = (EditText)findViewById(R.id.uid);
        psw = (EditText)findViewById(R.id.psw);


        login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        switch (v.getId()){
            case R.id.login_btn:
                params.put("method", "_GET");
                params.put("ID",uid.getText().toString());
                params.put("Password", psw.getText().toString());
                client.post("http://10.0.2.2:8000/android_user/", params, new MyTextListener(this.handler, 3, 30));

        }
    }

}
