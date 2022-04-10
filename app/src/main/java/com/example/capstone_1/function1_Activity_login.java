package com.example.capstone_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.capstone_1.databinding.ActivityMainFunction1Binding;
import com.example.capstone_1.databinding.ActivityMainFunction1LoginBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class function1_Activity_login extends AppCompatActivity {

    private ActivityMainFunction1LoginBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView tv_login_id, tv_login_name, tv_login_company, tv_login_phone, tv_address_login;
    private Button btn_tell;
    private String address;

    String userID = "";
    String userName = "";
    String userCompany = "";
    HashMap<String, String> hashMap = new HashMap<String, String>(){{
        put("메리츠화재","1566-7711"); put("교보AXA","1566-1566"); put("우리아비바생명보험","1588-4770");
        put("한화손해보험","1566-8000"); put("AIG손해보험","1544-0911"); put("미래에셋생명보험","1588-0220");
        put("롯데손해보험","1588-3344"); put("ERGO다음","1544-2580"); put("kdb생명","1588-4040");
        put("그린손해보험","1588-5959"); put("THE-K손해보험","1566-3000"); put("동부생명","1588-3131");
        put("흥국화재","1688-1688"); put("하이카다이렉트","1577-1001"); put("동양생명","1577-1004");
        put("제일화재","1566-8282"); put("한화생명(구.대한생명)","1588-6363"); put("푸르덴셜생명보험","2144-2600");
        put("삼성화재","1588-5114"); put("알리안츠생명보험","1588-6500"); put("신한생명보험","1588-5580");
        put("현대해상","1588-5656"); put("삼성생명보험","1588-3114"); put("PCA생명","1588-4300");
        put("LIG손해보험","1544-0114"); put("흥국생명","1588-2288"); put("뉴욕라이프","080-779-7575");
        put("동부화재","1588-0100"); put("교보생명","1588-1001"); put("ING생명보험","1588-5005");

        put("하나HSBC생명보험","080-3488-7000");
        put("KB생명보험","1588-9922");
        put("카디프생명보험","1688-2004");
        put("녹십자생명보험","1577-3311");
        put("라이나생명보험","1588-0058");
        put("AIA생명보험","080-500-4949");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainFunction1LoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarFunction1Login.toolbarFunction1Login);
        DrawerLayout drawer = binding.function1DrawerLayoutLogin;
        NavigationView navigationView = binding.navFunction1ViewLogin;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main_login, R.id.nav_function1_login, R.id.nav_function2_login, R.id.nav_function3_login, R.id.nav_function4_login)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_function1_login);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_main_login:
                        Intent intent_main = new Intent(function1_Activity_login.this, MainActivity_login.class);
                        intent_main.putExtra("userID",userID);
                        intent_main.putExtra("userName", userName);
                        intent_main.putExtra("userCompany",userCompany);
                        intent_main.putExtra("address", address);
                        startActivity(intent_main);
                        Toast.makeText(getApplicationContext(), "메인1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function1_login:
                        Toast.makeText(getApplicationContext(), "사고 대처 방법1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function2_login:
                        Intent intent_function2 = new Intent(function1_Activity_login.this, function2_Activity_login.class);
                        intent_function2.putExtra("userID",userID);
                        intent_function2.putExtra("userName", userName);
                        intent_function2.putExtra("userCompany",userCompany);
                        intent_function2.putExtra("address", address);
                        startActivity(intent_function2);
                        Toast.makeText(getApplicationContext(), "응급처치 영상1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function3_login:
                        Intent intent_function3 = new Intent(function1_Activity_login.this, function3_Activity_login.class);
                        intent_function3.putExtra("userID",userID);
                        intent_function3.putExtra("userName", userName);
                        intent_function3.putExtra("userCompany",userCompany);
                        intent_function3.putExtra("address", address);
                        startActivity(intent_function3);
                        Toast.makeText(getApplicationContext(), "자동차 법률사이트1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function4_login:
                        Intent intent_function4 = new Intent(function1_Activity_login.this, function4_Activity_login.class);
                        intent_function4.putExtra("userID",userID);
                        intent_function4.putExtra("userName", userName);
                        intent_function4.putExtra("userCompany",userCompany);
                        intent_function4.putExtra("address", address);
                        startActivity(intent_function4);
                        Toast.makeText(getApplicationContext(), "길 찾기1", Toast.LENGTH_SHORT).show();
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.function1_drawer_layout_login);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    //네비
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // nav_header 버튼 관리
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        tv_login_id = findViewById(R.id.tv_login_id);
        tv_login_name = findViewById(R.id.tv_login_name);
        tv_login_company = findViewById(R.id.tv_login_company);
        tv_login_phone = findViewById(R.id.tv_login_phone);
        tv_address_login = findViewById(R.id.tv_address_login);
        btn_tell = findViewById(R.id.btn_tell);

        Intent intent = getIntent(); // 1
        userID = intent.getStringExtra("userID"); // 2
        userCompany = intent.getStringExtra("userCompany");
        userName = intent.getStringExtra("userName");
        address = intent.getStringExtra("address");

        tv_login_id.setText(userID);
        tv_login_name.setText(userName);
        tv_login_company.setText(userCompany);
        tv_address_login.setText(address);
        tv_address_login.setSingleLine(true);    // 한줄로 표시하기
        tv_address_login.setEllipsize(TextUtils.TruncateAt.MARQUEE); // 흐르게 만들기
        tv_address_login.setSelected(true);
        if(hashMap.containsKey(userCompany)){
            tv_login_phone.setText(hashMap.get(userCompany));
        }

        btn_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("tel:/"+hashMap.get(userCompany)));
                startActivity(mIntent);
            }
        });
        return true;
    }

    // 설정
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_function1_login);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
