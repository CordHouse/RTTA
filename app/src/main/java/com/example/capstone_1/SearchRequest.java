package com.example.capstone_1;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SearchRequest extends StringRequest {
    final static private String URL = "http://qkqktl5310.ivyro.net/search.php";
    final private Map<String, String> map;

    public SearchRequest(String userID, String userName, String userPhone, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userName", userName);
        map.put("userPhone", userPhone);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
