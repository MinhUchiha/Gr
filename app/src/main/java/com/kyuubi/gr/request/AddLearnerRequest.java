package com.kyuubi.gr.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 15/05/2017.
 */

public class AddLearnerRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://nqminh278.000webhostapp.com/gr/AddLearner.php";
    private Map<String , String> params;

    public AddLearnerRequest(String classid, String username, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("classid",classid);
        params.put("username",username);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
