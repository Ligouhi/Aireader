package nuc.edu.aireader2;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("ValidFragment")
class BookFragment extends Fragment implements GridView.OnItemClickListener{
    List<BookData> appList = new ArrayList<>();
    GridView gridView;
    View view;
    public BookFragment() {
        // Required empty public constructor
    }

    private void initData(){

        gridView = (GridView) view.findViewById(R.id.book_gv);

        //加载适配器
        // Inflate the layout for this fragment
          Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        List<HashMap<String,String>> list = JSONTOOL.analyze_some_json(msg.obj.toString());

                        String dot = " 专辑";
                        for(HashMap<String,String> map:list)
                        {Log.i("sssss","name"+map.get("Img")+"--"+map.get("Name"));
                            appList.add(new BookData(Integer.parseInt(map.get("Img"),16),map.get("Name")+dot));
                        }


                        gridView.setAdapter(new GridAdapter(getContext(),appList));
                        break;
                    case 30:
                        Log.i("ssss","fail");
                        break;
                }
                super.handleMessage(msg);
            }
        };
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", "_GETALL");
        params.put("db", "Album");
        client.post("http://10.0.2.2:8000/android/", params, new MyTextListener(handler, 3, 30));
        //client.post("http://192.168.43.24:8000/android/", params, new MyTextListener(handler, 3, 30));
        Log.i("ssss","sucess--"+appList.isEmpty());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_book, container, false);
        initData();
        gridView.getOnItemClickListener();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){

        }

    }
}
