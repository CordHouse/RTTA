package com.example.capstone_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.capstone_1.databinding.ActivityMainFunction3Binding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class function3_Activity extends AppCompatActivity {

    private ActivityMainFunction3Binding binding;
    private AppBarConfiguration mAppBarConfiguration;

    private Button btn_login, btn_register;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainFunction3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarFunction3.toolbarFunction3);
        DrawerLayout drawer = binding.function3DrawerLayout;
        NavigationView navigationView = binding.navFunction3View;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main, R.id.nav_function1, R.id.nav_function2, R.id.nav_function3, R.id.nav_function4)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_function3);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_main:
                        Intent intent_main = new Intent(function3_Activity.this, MainActivity.class);
                        startActivity(intent_main);
                        Toast.makeText(getApplicationContext(), "메인", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function1:
                        Intent intent_function1 = new Intent(function3_Activity.this, function1_Activity.class);
                        startActivity(intent_function1);
                        Toast.makeText(getApplicationContext(), "사고 대처 방법", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function2:
                        Intent intent_function2 = new Intent(function3_Activity.this, function2_Activity.class);
                        startActivity(intent_function2);
                        Toast.makeText(getApplicationContext(), "응급처치 영상", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function3:
                        Toast.makeText(getApplicationContext(), "자동차 법률사이트", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function4:
                        Intent intent_function4 = new Intent(function3_Activity.this, function4_Activity.class);
                        startActivity(intent_function4);
                        Toast.makeText(getApplicationContext(), "길 찾기", Toast.LENGTH_SHORT).show();
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.function3_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    //네비
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // nav_header 버튼 관리
        // Inflate the menu; this adds items to the action bar if it is present.
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        getMenuInflater().inflate(R.menu.main, menu);
        btn_login.setOnClickListener(new View.OnClickListener() { // 로그인
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(function3_Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() { // 회원가입
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(function3_Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    // 설정
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_function3);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
