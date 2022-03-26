package com.example.capstone_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.capstone_1.databinding.ActivityMainBinding;
import com.example.capstone_1.databinding.ActivityMainLoginBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class MainActivity_login extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainLoginBinding binding_login;
    private TextView tv_login_id, tv_login_name, tv_login_company, tv_login_phone;
    private Button btn_tell;
    String userID = "";
    String userPassword = "";
    String userName = "";
    String userCompany = "";
    HashMap <String, String> hashMap = new HashMap<String, String>(){{
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

        binding_login = ActivityMainLoginBinding.inflate(getLayoutInflater());
        setContentView(binding_login.getRoot());

        setSupportActionBar(binding_login.appBarLoginMain.toolbarLogin);
        binding_login.appBarLoginMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding_login.loginDrawerLayout;
        NavigationView navigationView = binding_login.navLoginView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main, R.id.nav_function1, R.id.nav_function2, R.id.nav_function3, R.id.nav_function4)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_login);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // nav_header 버튼 관리
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        tv_login_id = findViewById(R.id.tv_login_id);
        tv_login_name = findViewById(R.id.tv_login_name);
        tv_login_company = findViewById(R.id.tv_login_company);
        tv_login_phone = findViewById(R.id.tv_login_phone);
        btn_tell = findViewById(R.id.btn_tell);

        Intent intent = getIntent(); // 1
        userID = intent.getStringExtra("userID"); // 2
        userPassword = intent.getStringExtra("userPassword");
        userCompany = intent.getStringExtra("userCompany");
        userName = intent.getStringExtra("userName");

        tv_login_id.setText(userID);
        tv_login_name.setText(userName);
        tv_login_company.setText(userCompany);

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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_login);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}