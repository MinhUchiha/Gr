package com.kyuubi.gr.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 22/05/2017.
 */

public class GetUserListRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://nqminh278.000webhostapp.com/gr/GetChatList.php";
    private Map<String , String> params;

    public GetUserListRequest(String username, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("username", username);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
