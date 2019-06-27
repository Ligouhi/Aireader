package nuc.edu.aireader2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridAdapter2 extends BaseAdapter {
    private Context context;
    private List<BookData> appList;

    private TextView textView;
    private Handler handler;
    private int index;
    private TextView pname;
    BookData app;

    public GridAdapter2(Context context,List<BookData> appList){
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
        convertView = View.inflate(context, R.layout.bkname_item,null);

        textView = (TextView) convertView.findViewById(R.id.bknametv);

        //获取数据

        textView.setText(app.getAppName());
        textView.setOnClickListener(new MyListener(position,app.getAppName()));



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
                    intent = new Intent(context,ReadActivity.class);
                    //需要穿的参数

                    intent.putExtra("pas_name",name);
//                    intent.putExtra("album_name");
                    v.getContext().startActivity(intent);
            }

    }
}
