package com.example.capstone_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone_1.databinding.AppBarLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_password;
    private Button btn_login_join; //btn_go_register;
    private ImageButton imageButton_login_back;
    private AppBarLoginBinding binding;
    private TextView tv_idPw, tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = AppBarLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarNewLogin);

        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        btn_login_join = findViewById(R.id.btn_login_join);
//        btn_go_register = findViewById(R.id.btn_go_register);
        imageButton_login_back = findViewById(R.id.image_login_back);

        tv_idPw = findViewById(R.id.tv_idPw);
        tv_register = findViewById(R.id.tv_register);

        //아이디 비밀번호 찾기
        tv_idPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //회원가입
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //메인으로
        imageButton_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //회원가입 버튼 클릭 시 수행
//        btn_go_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });

        //로그인
        btn_login_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                String userPassword = et_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인이 성공한 경우
                                String userID = jsonObject.getString("userID");
                                String userPassword = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                String userCompany = jsonObject.getString("userCompany");
                                Toast.makeText(getApplicationContext(), "로그인이 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity_login.class);
                                intent.putExtra("userID", userID); // intent.putExtra 때문에 그다음 장소에서도 가능한듯? 응용해보자 ###
                                intent.putExtra("userPassword", userPassword);
                                intent.putExtra("userName",userName);
                                intent.putExtra("userCompany",userCompany);
                                startActivity(intent);
                            } else { // 로그인이 실패한 경우
                                Toast.makeText(getApplicationContext(), "로그인이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // nav_header 버튼 관리
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
