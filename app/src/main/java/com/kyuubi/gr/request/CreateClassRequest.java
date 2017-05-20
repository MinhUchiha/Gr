package com.kyuubi.gr.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 12/05/2017.
 */

public class CreateClassRequest extends StringRequest {
        private static final String REGISTER_REQUEST_URL = "https://nqminh278.000webhostapp.com/gr/CreateClass.php";
        private Map<String , String> params;

        public CreateClassRequest(String classid, String classname, String setember, String time, String teacher, Response.Listener<String> listener){
            super(Request.Method.POST, REGISTER_REQUEST_URL, listener,null);
            params = new HashMap<>();
            params.put("classid",classid);
            params.put("classname",classname);
            params.put("setember",setember);
            params.put("time",time);
            params.put("teacher", teacher);
        }

        public Map<String, String> getParams(){
            return params;
        }
}
