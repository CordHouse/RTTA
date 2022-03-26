package com.example.capstone_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity{
    private EditText et_new_id, et_new_password, et_name, et_phone, et_company;
    private Button btn_new_register, btn_new_check;
    private Spinner spinner_tell;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_new_id = findViewById(R.id.et_new_id);
        et_new_password = findViewById(R.id.et_new_password);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_company = findViewById(R.id.et_company);
        btn_new_check = findViewById(R.id.btn_new_check);
        spinner_tell = findViewById(R.id.spinner_tell);

        //아이디 중복 체크
        btn_new_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_new_id.getText().toString();
                if (validate) {
                    return; //검증 완료
                }

                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            if (success) {
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                et_new_id.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                            }
                            else {
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        spinner_tell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                et_company.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 회원가입 버튼 클릭 시 수행
        btn_new_register = findViewById(R.id.btn_new_register);
        btn_new_register.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){

              // 텍스트 칸에 입력된 값을 가져온다.
              String userID = et_new_id.getText().toString();
              String userPassword = et_new_password.getText().toString();
              String userName = et_name.getText().toString();
              String userPhone = et_phone.getText().toString();
              String userCompany = et_company.getText().toString();

              //아이디 중복체크 했는지 확인
              if (!validate) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                  dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                  dialog.show();
                  return;
              }

              Response.Listener<String> responseListener = new Response.Listener<String>(){
                  @Override
                  public void onResponse(String response) {
                      try {
                          JSONObject jsonObject = new JSONObject(response);
                          boolean success = jsonObject.getBoolean("success");
                          if(success){ // 회원가입이 성공한 경우
                              Toast.makeText(getApplicationContext(), "회원가입이 성공하였습니다.", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                              startActivity(intent);
                          }
                          else{ // 회원가입이 실패한 경우
                              Toast.makeText(getApplicationContext(), "회원가입이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                  }
              };

              // 서버로 Volley를 이용해서 요청 함
              RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userPhone, userCompany, responseListener);
              RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
              queue.add(registerRequest);
          }
        });

    }
}
