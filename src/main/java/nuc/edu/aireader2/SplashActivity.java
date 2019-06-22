package nuc.edu.aireader2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    private static  final  long SPLASH_DELAY_MILLTS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goHome();
            }

         
        },SPLASH_DELAY_MILLTS);
    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }
}
