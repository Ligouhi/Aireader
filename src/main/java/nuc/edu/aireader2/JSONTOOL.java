package nuc.edu.aireader2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public  class JSONTOOL {

    public static List<HashMap<String, String>> analyze_some_json (String data ){
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        JSONArray jsons = null;
        try {
            jsons = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while(i < jsons.length()){
            try {
                list.add(analyze_once_json(jsons.get(i).toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
    }
        return list;
}

    public static HashMap<String, String> analyze_once_json(String data){
        HashMap<String, String> map = new HashMap();
        JSONObject obj = null;
        try {
            obj = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator it = obj.keys();
        while(it.hasNext()){
            String key = (String)it.next();
            try {
                map.put(key, obj.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
        }
}
