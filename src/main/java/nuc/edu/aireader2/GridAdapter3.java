package nuc.edu.aireader2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GridAdapter3 extends BaseAdapter {
    private Context context;
    private List<NoteData> appList;

    private TextView pas_name;
    private TextView nt_content;
    private Handler handler;
    private int index;
    private TextView pname;
    NoteData app;

    public GridAdapter3(Context context,List<NoteData> appList){
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
        convertView = View.inflate(context, R.layout.nt_item,null);

        pas_name = (TextView) convertView.findViewById(R.id.pas_name);
        nt_content = (TextView)convertView.findViewById(R.id.nt_content);


        //获取数据

        pas_name.setText(app.getPassage());
        nt_content.setText(app.getNote());
//        pas_name.setOnClickListener(new GridAdapter3.MyListener(position,app.getPassage()));





        return convertView;
    }

    private Intent intent;

//    private class MyListener implements View.OnClickListener {
//
//        int position;
//        String name;
//
//        public MyListener(int position,String name){
//            this.position = position;
//            this.name = name;
//
//        }
//
//        @Override
//        public void onClick(View v) {
//            intent = new Intent(context,ReadActivity.class);
//            //需要穿的参数
//
//            intent.putExtra("pas_name",name);
////                    intent.putExtra("album_name");
//            v.getContext().startActivity(intent);
//        }
//
//    }
}
