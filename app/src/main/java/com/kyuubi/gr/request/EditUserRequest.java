package com.kyuubi.gr.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 19/05/2017.
 */

public class EditUserRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://nqminh278.000webhostapp.com/gr/EditUser.php";
    private Map<String , String> params;

    public EditUserRequest(String username, String name, Integer role, String birthday, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("name",name);
        params.put("role",role+"");
        params.put("birthday",birthday);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
