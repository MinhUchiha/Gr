package com.kyuubi.gr.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 19/05/2017.
 */

public class AddHomeworkRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://nqminh278.000webhostapp.com/gr/CreateHomework.php";
    private Map<String , String> params;

    public AddHomeworkRequest(String classid, String content, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("classid",classid);
        params.put("content",content);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
