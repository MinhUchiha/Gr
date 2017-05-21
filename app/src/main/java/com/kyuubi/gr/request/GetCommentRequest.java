package com.kyuubi.gr.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 21/05/2017.
 */

public class GetCommentRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://nqminh278.000webhostapp.com/gr/GetCommment.php";
    private Map<String , String> params;

    public GetCommentRequest(int postid, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("postid",postid+"");
    }

    public Map<String, String> getParams(){
        return params;
    }
}
