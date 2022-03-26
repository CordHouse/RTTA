package com.example.capstone_1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final private static String URL = "http://qkqktl5310.ivyro.net/smartRegister.php";
    private Map<String, String> map;

    public RegisterRequest(String userID, String userPassword, String userName, String userPhone, String userCompany, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("userPhone", userPhone);
        map.put("userCompany", userCompany);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
