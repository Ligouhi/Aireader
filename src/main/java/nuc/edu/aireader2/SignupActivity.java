package nuc.edu.aireader2;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private EditText uid_sup;
    private EditText psw_sup;
    private EditText psw_1sup;
    private Button sup_btn;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Toast t;
            switch (msg.what) {
                case 3:
                    t = Toast.makeText(SignupActivity.this, "注册成功", Toast.LENGTH_LONG);//信息框
                    t.show();//调用
                    SignupActivity.this.finish();
                    break;
                case 30:
                    t = Toast.makeText(SignupActivity.this, "注册失败", Toast.LENGTH_LONG);//信息框
                    t.show();
                    break;
            }
            super.handleMessage(msg);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        uid_sup = (EditText)findViewById(R.id.uid_sup);
        psw_sup = (EditText)findViewById(R.id.psw_sup);
        psw_1sup = (EditText)findViewById(R.id.psw_1sup);
        sup_btn = (Button)findViewById(R.id.sup_btn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.sig_toolbar);
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

        sup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(psw_sup.getText().toString().equals(psw_1sup.getText().toString())&&!psw_sup.getText().toString().isEmpty())
                {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("method", "_POST");
                    params.put("ID",uid_sup.getText().toString());
                    params.put("Password", psw_1sup.getText().toString());
                    client.post("http://10.0.2.2:8000/android_user/", params, new MyTextListener(handler, 3, 30));
                 }
                 else{
                    Toast t = new Toast(SignupActivity.this);
                    t.setText("输入格式不正确！");
                    t.show();
                }

            }
        });
    }
}
