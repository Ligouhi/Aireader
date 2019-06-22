package nuc.edu.aireader2;

import android.os.Handler;
import android.os.Message;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MyTextListener extends TextHttpResponseHandler {
    Handler handler;
    int s_state, f_state;

    public MyTextListener(Handler handler, int s_state, int f_state) {
        this.handler = handler;
        this.s_state = s_state;
        this.f_state = f_state;
    }

    Message msg = Message.obtain();
    @Override
    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
        msg.what = f_state;


        handler.sendMessage(msg);
    }

    @Override
    public void onSuccess(int i, Header[] headers, String s) {
    msg.what = s_state;
        msg.obj = headers;
        handler.sendMessage(msg);
    }
}
