package nuc.edu.aireader2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GET_RECODE_AUDIO = 1;
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static String[] PERMISSION_AUDIO = {
            Manifest.permission.RECORD_AUDIO
    };
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    File soundFile= null;
    MediaRecorder mr = null;
   private TextView audio_tv = null;
   boolean isRecording = false;
    MediaPlayer mPlayer = null;
    private  TextView play_tv = null;
    boolean isplay = false;

    public static void verifyAudioPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_AUDIO,
                    GET_RECODE_AUDIO);
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readpage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.rp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); // 显示按钮
        getSupportActionBar().setHomeButtonEnabled(true); // 可点击
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        audio_tv = (TextView)findViewById(R.id.audio_tv);
        audio_tv.setOnClickListener(this);
        play_tv = (TextView)findViewById(R.id.play_tv);
        play_tv.setOnClickListener(this);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
        verifyAudioPermissions(this);
    }




    //开始录制
    private void startRecord(){

        if(mr == null){
            File dir = new File(Environment.getExternalStorageDirectory(),"sounds");
            if(!dir.exists()){
                dir.mkdirs();
            }
             soundFile = new File(dir,System.currentTimeMillis()+".mpeg4");
            if(!soundFile.exists()){
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);  //音频输入源
            mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);   //设置输出格式
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);   //设置编码格式

            //设置音频文件的存储方式
            if(Build.VERSION.SDK_INT < 26){
                //若api低于26，调用setOutputFile(String path)
                mr.setOutputFile(soundFile.getAbsolutePath());
            }else {
                //若API高于26 使用setOutputFile(File path)
                mr.setOutputFile(soundFile);
            }
            try {
                mr.prepare();
                mr.start();  //开始录制
                Toast.makeText(ReadActivity.this,"开始录音",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(ReadActivity.this,"未录音",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

        isRecording = true;
    }

    //停止录制，资源释放
    private void stopRecord(){
        try {
            mr.stop();
            Toast.makeText(this,"录音结束",Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            // TODO 如果当前java状态和jni里面的状态不一致，
            //e.printStackTrace();
            mr = null;
            Toast.makeText(this,"无录音",Toast.LENGTH_LONG).show();
//            mr = new MediaRecorder();
        }
        mr.release();
        mr = null;


        isRecording = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.audio_tv:
                setRecord();
                break;
            case R.id.play_tv:
                onPlay(isplay);
                isplay = !isplay;
                break;
        }
    }


    private void setRecord() {
        if(isRecording)
        {
            stopRecord();
            audio_tv.setTextColor(getResources()
                    .getColor(R.color.black));
            audio_tv.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.audio,0,0);
        }
        else
        {
            startRecord();
            audio_tv.setTextColor(getResources()
                    .getColor(R.color.bule));
            audio_tv.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.audio_press,0,0);
        }

    }


    private void onPlay(boolean start) {
        if (!start) {
            startPlaying();
            play_tv.setTextColor(getResources()
                    .getColor(R.color.bule));
            play_tv.setText("暂停");
            play_tv.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.stop,0,0);
        } else {
            stopPlaying();
            play_tv.setTextColor(getResources()
                    .getColor(R.color.black));
            play_tv.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.play,0,0);
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
//　　  //设置要播放的文件
            mPlayer.setDataSource(soundFile.getPath());
            mPlayer.prepare();
//　　  //播放之
            mPlayer.start();
        } catch (IOException e) {
            Log.e("0", "prepare() failed");
        }
    }

    //停止播放
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

}

