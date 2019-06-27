package nuc.edu.aireader2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
private TextView user_name;
private EditText user_nameinput;
private Button sigout_btn;
private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ur_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); // 显示按钮
        getSupportActionBar().setHomeButtonEnabled(true); // 可点击
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      user_name = (TextView)findViewById(R.id.user_name);
      user_nameinput = (EditText)findViewById(R.id.user_name_input);
      sigout_btn = (Button)findViewById(R.id.sigout_btn);
      sigout_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              sharedPreferences = getSharedPreferences("Login",0);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              editor.clear();
              editor.commit();
              Intent intent = new Intent(UserActivity.this,MainActivity.class);
              startActivity(intent);
              finish();
          }
      });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent()!=null){
            String name = getIntent().getStringExtra("uid");
            user_nameinput.setText(name);
            user_name.setText(name);
        }
    }
}
