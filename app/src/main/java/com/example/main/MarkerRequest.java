package com.example.main;





import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MarkerRequest extends StringRequest {
    final static private String URL="http://mjckjs.gabia.io/whispering/php/addMarker.php";
    private Map<String,String> parameters;
    public MarkerRequest(Response.Listener<String> listener){
        super(Method.GET,URL,listener,null);
        parameters=new HashMap<>();
        String a = "c1";
        parameters.put("coupleID",a );
        String b  ="2019-09-22";
        parameters.put("date",b);
    }
    protected Map<String,String> getParams()throws AuthFailureError {
        return parameters;
    }
}
