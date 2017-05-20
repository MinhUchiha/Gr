package com.kyuubi.gr.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 09/05/2017.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://nqminh278.000webhostapp.com/gr/Register.php";
    private Map<String , String> params;

    public RegisterRequest(String name, String username, int role, String password,String birthday, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("password",password);
        params.put("role",role+"");
        params.put("birthday",birthday);
    }

    public Map<String, String> getParams(){
        return params;
    }
}
