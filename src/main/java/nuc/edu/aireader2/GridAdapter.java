package nuc.edu.aireader2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<BookData> appList;
    private ImageView imageView;
    private TextView textView;
    private Handler handler;
    private int index;
    BookData app;

    public GridAdapter(Context context,List<BookData> appList){
        this.context = context;
        this.appList = appList;
    }
    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        app = appList.get(position);
        convertView = View.inflate(context, R.layout.bk_item,null);
        imageView = (ImageView) convertView.findViewById(R.id.itm_img);
        textView = (TextView) convertView.findViewById(R.id.itm_name);

        //获取数据
        imageView.setImageResource(app.getAppImg1());
        textView.setText(app.getAppName());

        imageView.setOnClickListener(new MyListener(position,app.getAppName()));



        return convertView;
    }

    private Intent intent;

    private class MyListener implements View.OnClickListener {

        int position;
        String name;
        public MyListener(int position,String name){
            this.position = position;
            this.name = name;
        }
        @Override
        public void onClick(View v) {


            switch (position){
                case 0:
                     intent = new Intent(context,BookActivity.class);
                    //需要穿的参数
                    intent.putExtra("album_name",name.split(" ")[0]);

                    v.getContext().startActivity(intent);
                    break;
                case 1:
                     intent = new Intent(context,BookActivity.class);
                    //需要穿的参数
                    intent.putExtra("album_name",name.split(" ")[0]);

                    v.getContext().startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(context,BookActivity.class);
                    //需要穿的参数
                    intent.putExtra("album_name",name.split(" ")[0]);

                    v.getContext().startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(context,BookActivity.class);
                    //需要穿的参数
                    intent.putExtra("album_name",name.split(" ")[0]);

                    v.getContext().startActivity(intent);
                    break;
                    case 4:
                    intent = new Intent(context,BookActivity.class);
                    //需要穿的参数
                    intent.putExtra("album_name",name.split(" ")[0]);

                    v.getContext().startActivity(intent);
                    break;
                    case 5:
                    intent = new Intent(context,BookActivity.class);
                    //需要穿的参数
                    intent.putExtra("album_name",name.split(" ")[0]);

                    v.getContext().startActivity(intent);
                    break;
                case 6:
                    intent = new Intent(context,BookActivity.class);
                    //需要穿的参数
                    intent.putExtra("album_name",name.split(" ")[0]);

                    v.getContext().startActivity(intent);
                    break;


            }
        }
    }
}
