package com.example.main;





import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MarkerRequest extends StringRequest {
    final static private String URL="http://10.0.2.2/teamProject/addMarker.php";
    private Map<String,String> parameters;
    public MarkerRequest(Response.Listener<String> listener){
        super(Method.GET,URL,listener,null);
        parameters=new HashMap<>();
        parameters.put("coupleID","c1");
        parameters.put("date","2019-09-22");
    }
    protected Map<String,String> getParams()throws AuthFailureError {
        return parameters;
    }
}
