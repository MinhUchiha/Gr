package com.kyuubi.gr.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 09/05/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://nqminh278.000webhostapp.com/gr/Login.php";
    private Map<String , String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
    }

    public Map<String, String> getParams(){
        return params;
    }

}
