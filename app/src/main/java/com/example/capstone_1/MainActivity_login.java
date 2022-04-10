package com.example.capstone_1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.capstone_1.databinding.ActivityMainBinding;
import com.example.capstone_1.databinding.ActivityMainLoginBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity_login extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainLoginBinding binding_login;
    private TextView tv_login_id, tv_login_name, tv_login_company, tv_login_phone;
    private Button btn_tell;

    private TextView tv_water, tv_waterPersent, tv_tmp, tv_sky, tv_vec, tv_wsd, tv_humidity, tv_date, tv_time, tv_address, tv_address_login;
    private String address; // 로그인시 위치 표현
    private ImageView img_weather;
    private TextView tv_slash1, tv_slash2, tv_slash3, tv_slash4, tv_slash5, tv_slash6;
    private View v_center_line;
    private LinearLayout box3, box4;

    //주소 + 키 + 수정 등등 해야함
    private String url_weather = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=";
    private String url_service_Key = "vYddcBhVO08I9DSvmVD6Mhs12hpr7JzPOK8VQcn80lKi07kH4clX2seo0VFMebG45VJTEUBC91nYSvUsXQzDpg%3D%3D";
    private int url_x, url_y, url_xx, url_yy;
    private String url_m;
    private String html;
    private String[] Array;

    //주소
    private double cur_lat;
    private double cur_lon;
    public static int TO_GRID = 0;

    //미세먼지
    private TextView tv_pm25Value, tv_coValue, tv_o3Value, tv_o3Grade, tv_pm10Value, tv_coGrade;
    private String url_dust = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName=";
    private String url_dust_service_Key = "vYddcBhVO08I9DSvmVD6Mhs12hpr7JzPOK8VQcn80lKi07kH4clX2seo0VFMebG45VJTEUBC91nYSvUsXQzDpg%3D%3D";
    private String address_dust_sido, html_dust, url_d, address_dust_station;
    private String[] Array_dust, dust_list;
    private int set_i = 0;

    //검색
    private TextView tv_water_search, tv_waterPersent_search, tv_tmp_search, tv_sky_search, tv_vec_search, tv_wsd_search, tv_humidity_search, tv_slash1_search, tv_slash2_search,
            tv_slash3_search, tv_address_search, tv_pm25Value_search, tv_coValue_search, tv_o3Value_search, tv_o3Grade_search, tv_pm10Value_search, tv_coGrade_search
            ,tv_slash4_search, tv_slash5_search, tv_slash6_search;
    private ImageView img_weather_search;
    private String html_search, url_m_search, address_search="", html_dust_search, url_d_search, Address_dust_sido_search="";
    private String[] Array_search, dust_list_search;
    private double search_lat, search_lon;

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
        DrawerLayout drawer = binding_login.loginDrawerLayout;
        NavigationView navigationView = binding_login.navLoginView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main_login, R.id.nav_function1_login, R.id.nav_function2_login, R.id.nav_function3_login, R.id.nav_function4_login)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_login);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_main_login:
                        Toast.makeText(getApplicationContext(), "메인1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function1_login:
                        Intent intent_function1 = new Intent(MainActivity_login.this, function1_Activity_login.class);
                        intent_function1.putExtra("userID",userID);
                        intent_function1.putExtra("userName", userName);
                        intent_function1.putExtra("userCompany",userCompany);
                        intent_function1.putExtra("address", address);
                        startActivity(intent_function1);
                        Toast.makeText(getApplicationContext(), "사고 대처 방법1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function2_login:
                        Intent intent_function2 = new Intent(MainActivity_login.this, function2_Activity_login.class);
                        intent_function2.putExtra("userID",userID);
                        intent_function2.putExtra("userName", userName);
                        intent_function2.putExtra("userCompany",userCompany);
                        intent_function2.putExtra("address", address);
                        startActivity(intent_function2);
                        Toast.makeText(getApplicationContext(), "응급처치 영상1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function3_login:
                        Intent intent_function3 = new Intent(MainActivity_login.this, function3_Activity_login.class);
                        intent_function3.putExtra("userID",userID);
                        intent_function3.putExtra("userName", userName);
                        intent_function3.putExtra("userCompany",userCompany);
                        intent_function3.putExtra("address", address);
                        startActivity(intent_function3);
                        Toast.makeText(getApplicationContext(), "자동차 법률사이트1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function4_login:
                        Intent intent_function4 = new Intent(MainActivity_login.this, function4_Activity_login.class);
                        intent_function4.putExtra("userID",userID);
                        intent_function4.putExtra("userName", userName);
                        intent_function4.putExtra("userCompany",userCompany);
                        intent_function4.putExtra("address", address);
                        startActivity(intent_function4);
                        Toast.makeText(getApplicationContext(), "길 찾기1", Toast.LENGTH_SHORT).show();
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.login_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkDangerousPermissions();
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        tv_tmp = findViewById(R.id.tv_tmp);
        tv_sky = findViewById(R.id.tv_sky);
        tv_water = findViewById(R.id.tv_water);
        tv_waterPersent = findViewById(R.id.tv_waterPersent);
        tv_vec = findViewById(R.id.tv_vec);
        tv_wsd = findViewById(R.id.tv_wsd);
        tv_humidity = findViewById(R.id.tv_humidity);
        img_weather = findViewById(R.id.img_weather);

        tv_pm25Value = findViewById(R.id.tv_pm25Value);
        tv_coValue = findViewById(R.id.tv_coValue);
        tv_o3Value = findViewById(R.id.tv_o3Value);
        tv_o3Grade = findViewById(R.id.tv_o3Grade);
        tv_pm10Value = findViewById(R.id.tv_pm10Value);
        tv_coGrade = findViewById(R.id.tv_coGrade);

        tv_slash1 = findViewById(R.id.tv_slash1);
        tv_slash2 = findViewById(R.id.tv_slash2);
        tv_slash3 = findViewById(R.id.tv_slash3);
        tv_slash4 = findViewById(R.id.tv_slash4);
        tv_slash5 = findViewById(R.id.tv_slash5);
        tv_slash6 = findViewById(R.id.tv_slash6);

        tv_tmp_search = findViewById(R.id.tv_tmp_search);
        tv_sky_search = findViewById(R.id.tv_sky_search);
        tv_water_search = findViewById(R.id.tv_water_search);
        tv_waterPersent_search = findViewById(R.id.tv_waterPersent_search);
        tv_vec_search = findViewById(R.id.tv_vec_search);
        tv_wsd_search = findViewById(R.id.tv_wsd_search);
        tv_humidity_search = findViewById(R.id.tv_humidity_search);
        img_weather_search = findViewById(R.id.img_weather_search);

        tv_pm25Value_search = findViewById(R.id.tv_pm25Value_search);
        tv_coValue_search = findViewById(R.id.tv_coValue_search);
        tv_o3Value_search = findViewById(R.id.tv_o3Value_search);
        tv_o3Grade_search = findViewById(R.id.tv_o3Grade_search);
        tv_pm10Value_search = findViewById(R.id.tv_pm10Value_search);
        tv_coGrade_search = findViewById(R.id.tv_coGrade_search);

        tv_slash1_search = findViewById(R.id.tv_slash1_search);
        tv_slash2_search = findViewById(R.id.tv_slash2_search);
        tv_slash3_search = findViewById(R.id.tv_slash3_search);
        tv_slash4_search = findViewById(R.id.tv_slash4_search);
        tv_slash5_search = findViewById(R.id.tv_slash5_search);
        tv_slash6_search = findViewById(R.id.tv_slash6_search);
        tv_address_search = findViewById(R.id.tv_address_search);

        v_center_line = findViewById(R.id.v_center_line);
        box3 = findViewById(R.id.box3);
        box4 = findViewById(R.id.box4);
        // 나오긴 하는데, 각자 어떤 역할인지 구분해서 나눠줘야함..
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        html = getData();
                        Array = html.split("\n");
                        html_dust = getFineDust();
                        dust_list = html_dust.split("\n");
                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                long now = System.currentTimeMillis();
                                Date date = new Date(now);
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
                                String get_time = dateFormat.format(date);
                                tv_time.setText(get_time.substring(0, 2) + " : " + get_time.substring(2, 4));
                                for (int i = 0; i < Array.length - 1; i++) {
                                    if (Array[i].contains("날짜 :"))
                                        tv_date.setText(Array[i].substring(5, 9) + "." + Array[i].substring(9, 11) + "." + Array[i].substring(11, 13));
                                    else if (Array[i].contains("종류 :"))// 강수확률
                                        switch (Array[i].substring(4, 7)) {
                                            case "POP":
                                                tv_waterPersent.setText("강수확률 : " + Array[i + 3].substring(3) + "%");
                                                break;
                                            case "PTY":
                                                String num_PTY = Array[i + 3].substring(3);
                                                if (num_PTY.equals("0"))
                                                    tv_water.setText("강수형태 : 없음");
                                                else if (num_PTY.equals("1")) {
                                                    tv_water.setText("강수형태 : 비");
                                                    img_weather.setImageResource(R.drawable.rain);
                                                } else if (num_PTY.equals("2")) {
                                                    tv_water.setText("강수형태 : 비/눈");
                                                    img_weather.setImageResource(R.drawable.rain_snow);
                                                } else if (num_PTY.equals("3")) {
                                                    tv_water.setText("강수형태 : 눈");
                                                    img_weather.setImageResource(R.drawable.snow);
                                                } else if (num_PTY.equals("4")) {
                                                    tv_water.setText("강수형태 : 소나기");
                                                    img_weather.setImageResource(R.drawable.rain);
                                                }
                                                break;
                                            case "REH":
                                                tv_humidity.setText("습도 : " + Array[i + 3].substring(3) + "%");
                                                break;
                                            case "SKY":
                                                String num_SKY = Array[i + 3].substring(3);
                                                if (num_SKY.equals("1")) {
                                                    tv_sky.setText("날씨 : 맑음");
                                                    img_weather.setImageResource(R.drawable.sun);
                                                } else if (num_SKY.equals("3")) {
                                                    tv_sky.setText("날씨 : 구름많음");
                                                    img_weather.setImageResource(R.drawable.cloud_m);
                                                } else if (num_SKY.equals("4")) {
                                                    tv_sky.setText("날씨 : 흐림");
                                                    img_weather.setImageResource(R.drawable.cloud);
                                                }
                                                break;
                                            case "TMP":
                                                tv_tmp.setText(Array[i + 3].substring(3) + "°C");
                                                break;
                                            case "VEC":
                                                tv_vec.setText("풍향 : " + Array[i + 3].substring(3) + "m/s");
                                                break;
                                            case "WSD":
                                                tv_wsd.setText("풍속 : " + Array[i + 3].substring(3) + "m/s");
                                                break;
                                        }
                                }
                                for (int i = 0; i < dust_list.length - 1; i++) {
                                    if (dust_list[i].equals("지역 :" + address_dust_station)) {
                                        set_i = i;
                                        if (dust_list[set_i-2].contains("미세먼지25 :")) {
                                            tv_pm25Value.setText("미세먼지(25pm) : " + dust_list[set_i-2].substring(8) + "㎍/㎥");
                                        }if (dust_list[set_i-5].contains("일산화탄소 :")) {
                                            tv_coValue.setText("일산화탄소 : " + dust_list[set_i-5].substring(7) + "ppm");
                                        }if (dust_list[set_i-3].contains("미세먼지10 :")) {
                                            tv_pm10Value.setText("미세먼지(10pm) : " + dust_list[set_i-3].substring(8) + "㎍/㎥");
                                        }if (dust_list[set_i-4].contains("오존지수 :")) {
                                            if(dust_list[set_i-4].substring(6).equals("1")){
                                                tv_o3Grade.setText("오존지수 : 좋음");
                                            }
                                            else if(dust_list[set_i-4].substring(6).equals("2")){
                                                tv_o3Grade.setText("오존지수 : 보통");
                                            }
                                            else if(dust_list[set_i-4].substring(6).equals("3")){
                                                tv_o3Grade.setText("오존지수 : 나쁨");
                                            }
                                            else if(dust_list[set_i-4].substring(6).equals("4")){
                                                tv_o3Grade.setText("오존지수 : 매우나쁨");
                                            }
                                        }if (dust_list[set_i-1].contains("일산화탄소지수 :")) {
                                            if(dust_list[set_i-1].substring(9).equals("1")){
                                                tv_coGrade.setText("일산화탄소지수 : 좋음");
                                            }
                                            else if(dust_list[set_i-1].substring(9).equals("2")){
                                                tv_coGrade.setText("일산화탄소지수 : 보통");
                                            }
                                            else if(dust_list[set_i-1].substring(9).equals("3")){
                                                tv_coGrade.setText("일산화탄소지수 : 나쁨");
                                            }
                                            else if(dust_list[set_i-1].substring(9).equals("4")){
                                                tv_coGrade.setText("일산화탄소지수 : 매우나쁨");
                                            }
                                        }if (dust_list[set_i+1].contains("오존 :")) {
                                            tv_o3Value.setText("오존 : "+ dust_list[set_i+1].substring(4) + "ppm");
                                        }
                                    }
                                }
                                tv_tmp.setVisibility(View.VISIBLE);
                                tv_sky.setVisibility(View.VISIBLE);
                                tv_water.setVisibility(View.VISIBLE);
                                tv_waterPersent.setVisibility(View.VISIBLE);
                                tv_vec.setVisibility(View.VISIBLE);
                                tv_wsd.setVisibility(View.VISIBLE);
                                tv_humidity.setVisibility(View.VISIBLE);
                                img_weather.setVisibility(View.VISIBLE);

                                tv_pm25Value.setVisibility(View.VISIBLE);
                                tv_coValue.setVisibility(View.VISIBLE);
                                tv_o3Value.setVisibility(View.VISIBLE);
                                tv_o3Grade.setVisibility(View.VISIBLE);
                                tv_pm10Value.setVisibility(View.VISIBLE);
                                tv_coGrade.setVisibility(View.VISIBLE);

                                tv_slash1.setVisibility(View.VISIBLE);
                                tv_slash2.setVisibility(View.VISIBLE);
                                tv_slash3.setVisibility(View.VISIBLE);
                                tv_slash4.setVisibility(View.VISIBLE);
                                tv_slash5.setVisibility(View.VISIBLE);
                                tv_slash6.setVisibility(View.VISIBLE);
                            }
                        });
                        Thread.sleep(1000);
                    }catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    catch (Exception e) {
                        e.printStackTrace( );
                    }
            }
        }).start();

        new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                while (!Thread.interrupted())
                    if(!address_search.equals("") && !Address_dust_sido_search.equals("")) {
                        try {
                            html_search = get_search_Data(url_xx, url_yy);
                            Array_search = html_search.split("\n");
                            html_dust_search = getFineDust_search();
                            dust_list_search = html_dust_search.split("\n");
                            tv_address_search.setText(Address_dust_sido_search +" " + address_search + " 지역의 현재 날씨");
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    for (int i = 0; i < Array_search.length - 1; i++) {
                                        if (Array_search[i].contains("종류 :"))// 강수확률
                                            switch (Array_search[i].substring(4, 7)) {
                                                case "POP":
                                                    tv_waterPersent_search.setText("강수확률 : " + Array_search[i + 3].substring(3) + "%");
                                                    break;
                                                case "PTY":
                                                    String num_PTY = Array_search[i + 3].substring(3);
                                                    if (num_PTY.equals("0"))
                                                        tv_water_search.setText("강수형태 : 없음");
                                                    else if (num_PTY.equals("1")) {
                                                        tv_water_search.setText("강수형태 : 비");
                                                        img_weather_search.setImageResource(R.drawable.rain);
                                                    } else if (num_PTY.equals("2")) {
                                                        tv_water_search.setText("강수형태 : 비/눈");
                                                        img_weather_search.setImageResource(R.drawable.rain_snow);
                                                    } else if (num_PTY.equals("3")) {
                                                        tv_water_search.setText("강수형태 : 눈");
                                                        img_weather_search.setImageResource(R.drawable.snow);
                                                    } else if (num_PTY.equals("4")) {
                                                        tv_water_search.setText("강수형태 : 소나기");
                                                        img_weather_search.setImageResource(R.drawable.rain);
                                                    }
                                                    break;
                                                case "REH":
                                                    tv_humidity_search.setText("습도 : " + Array_search[i + 3].substring(3) + "%");
                                                    break;
                                                case "SKY":
                                                    String num_SKY = Array_search[i + 3].substring(3);
                                                    if (num_SKY.equals("1")) {
                                                        tv_sky_search.setText("날씨 : 맑음");
                                                        img_weather_search.setImageResource(R.drawable.sun);
                                                    } else if (num_SKY.equals("3")) {
                                                        tv_sky_search.setText("날씨 : 구름많음");
                                                        img_weather_search.setImageResource(R.drawable.cloud_m);
                                                    } else if (num_SKY.equals("4")) {
                                                        tv_sky_search.setText("날씨 : 흐림");
                                                        img_weather_search.setImageResource(R.drawable.cloud);
                                                    }
                                                    break;
                                                case "TMP":
                                                    tv_tmp_search.setText(Array_search[i + 3].substring(3) + "°C");
                                                    break;
                                                case "VEC":
                                                    tv_vec_search.setText("풍향 : " + Array_search[i + 3].substring(3) + "m/s");
                                                    break;
                                                case "WSD":
                                                    tv_wsd_search.setText("풍속 : " + Array_search[i + 3].substring(3) + "m/s");
                                                    break;
                                            }
                                    }
                                    for (int i = 0; i < dust_list_search.length - 1; i++) {
                                        if (dust_list_search[i].equals("지역 :" + address_search)) {
                                            set_i = i;
                                            if (dust_list_search[set_i-2].contains("미세먼지25 :")) {
                                                tv_pm25Value_search.setText("미세먼지(25pm) : " + dust_list_search[set_i-2].substring(8) + "㎍/㎥");
                                            }if (dust_list_search[set_i-5].contains("일산화탄소 :")) {
                                                tv_coValue_search.setText("일산화탄소 : " + dust_list_search[set_i-5].substring(7) + "ppm");
                                            }if (dust_list_search[set_i-3].contains("미세먼지10 :")) {
                                                tv_pm10Value_search.setText("미세먼지(10pm) : " + dust_list_search[set_i-3].substring(8) + "㎍/㎥");
                                            }if (dust_list_search[set_i-4].contains("오존지수 :")) {
                                                if(dust_list_search[set_i-4].substring(6).equals("1")){
                                                    tv_o3Grade_search.setText("오존지수 : 좋음");
                                                }
                                                else if(dust_list_search[set_i-4].substring(6).equals("2")){
                                                    tv_o3Grade_search.setText("오존지수 : 보통");
                                                }
                                                else if(dust_list_search[set_i-4].substring(6).equals("3")){
                                                    tv_o3Grade_search.setText("오존지수 : 나쁨");
                                                }
                                                else if(dust_list_search[set_i-4].substring(6).equals("4")){
                                                    tv_o3Grade_search.setText("오존지수 : 매우나쁨");
                                                }
                                            }if (dust_list_search[set_i-1].contains("일산화탄소지수 :")) {
                                                if(dust_list_search[set_i-1].substring(9).equals("1")){
                                                    tv_coGrade_search.setText("일산화탄소지수 : 좋음");
                                                }
                                                else if(dust_list_search[set_i-1].substring(9).equals("2")){
                                                    tv_coGrade_search.setText("일산화탄소지수 : 보통");
                                                }
                                                else if(dust_list_search[set_i-1].substring(9).equals("3")){
                                                    tv_coGrade_search.setText("일산화탄소지수 : 나쁨");
                                                }
                                                else if(dust_list_search[set_i-1].substring(9).equals("4")){
                                                    tv_coGrade_search.setText("일산화탄소지수 : 매우나쁨");
                                                }
                                            }if (dust_list_search[set_i+1].contains("오존 :")) {
                                                tv_o3Value_search.setText("오존 : "+ dust_list_search[set_i+1].substring(4) + "ppm");
                                            }
                                        }
                                    }
                                    tv_tmp_search.setVisibility(View.VISIBLE);
                                    tv_sky_search.setVisibility(View.VISIBLE);
                                    tv_water_search.setVisibility(View.VISIBLE);
                                    tv_waterPersent_search.setVisibility(View.VISIBLE);
                                    tv_vec_search.setVisibility(View.VISIBLE);
                                    tv_wsd_search.setVisibility(View.VISIBLE);
                                    tv_humidity_search.setVisibility(View.VISIBLE);
                                    img_weather_search.setVisibility(View.VISIBLE);

                                    tv_pm25Value_search.setVisibility(View.VISIBLE);
                                    tv_coValue_search.setVisibility(View.VISIBLE);
                                    tv_o3Value_search.setVisibility(View.VISIBLE);
                                    tv_o3Grade_search.setVisibility(View.VISIBLE);
                                    tv_pm10Value_search.setVisibility(View.VISIBLE);
                                    tv_coGrade_search.setVisibility(View.VISIBLE);

                                    tv_slash1_search.setVisibility(View.VISIBLE);
                                    tv_slash2_search.setVisibility(View.VISIBLE);
                                    tv_slash3_search.setVisibility(View.VISIBLE);
                                    tv_slash4_search.setVisibility(View.VISIBLE);
                                    tv_slash5_search.setVisibility(View.VISIBLE);
                                    tv_slash6_search.setVisibility(View.VISIBLE);
                                    tv_address_search.setVisibility(View.VISIBLE);

                                    v_center_line.setVisibility(View.VISIBLE);
                                    box3.setVisibility(View.VISIBLE);
                                    box4.setVisibility(View.VISIBLE);

                                }
                            });
                            Thread.sleep(1000);
                        }catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        catch (Exception e) {
                            e.printStackTrace( );
                        }
                    }
            }
        }).start();

        html = getData();
        Array = html.split("\n");
        html_dust = getFineDust();
        dust_list = html_dust.split("\n");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        String get_time = dateFormat.format(date);
        tv_time.setText(get_time.substring(0, 2) + " : " + get_time.substring(2, 4));
        for (int i = 0; i < Array.length - 1; i++) {
            if (Array[i].contains("날짜 :"))
                tv_date.setText(Array[i].substring(5, 9) + "." + Array[i].substring(9, 11) + "." + Array[i].substring(11, 13));
            else if (Array[i].contains("종류 :"))// 강수확률
                switch (Array[i].substring(4, 7)) {
                    case "POP":
                        tv_waterPersent.setText("강수확률 : " + Array[i + 3].substring(3) + "%");
                        break;
                    case "PTY":
                        String num_PTY = Array[i + 3].substring(3);
                        if (num_PTY.equals("0"))
                            tv_water.setText("강수형태 : 없음");
                        else if (num_PTY.equals("1")) {
                            tv_water.setText("강수형태 : 비");
                            img_weather.setImageResource(R.drawable.rain);
                        } else if (num_PTY.equals("2")) {
                            tv_water.setText("강수형태 : 비/눈");
                            img_weather.setImageResource(R.drawable.rain_snow);
                        } else if (num_PTY.equals("3")) {
                            tv_water.setText("강수형태 : 눈");
                            img_weather.setImageResource(R.drawable.snow);
                        } else if (num_PTY.equals("4")) {
                            tv_water.setText("강수형태 : 소나기");
                            img_weather.setImageResource(R.drawable.rain);
                        }
                        break;
                    case "REH":
                        tv_humidity.setText("습도 : " + Array[i + 3].substring(3) + "%");
                        break;
                    case "SKY":
                        String num_SKY = Array[i + 3].substring(3);
                        if (num_SKY.equals("1")) {
                            tv_sky.setText("날씨 : 맑음");
                            img_weather.setImageResource(R.drawable.sun);
                        } else if (num_SKY.equals("3")) {
                            tv_sky.setText("날씨 : 구름많음");
                            img_weather.setImageResource(R.drawable.cloud_m);
                        } else if (num_SKY.equals("4")) {
                            tv_sky.setText("날씨 : 흐림");
                            img_weather.setImageResource(R.drawable.cloud);
                        }
                        break;
                    case "TMP":
                        tv_tmp.setText(Array[i + 3].substring(3) + "°C");
                        break;
                    case "VEC":
                        tv_vec.setText("풍향 : " + Array[i + 3].substring(3) + "m/s");
                        break;
                    case "WSD":
                        tv_wsd.setText("풍속 : " + Array[i + 3].substring(3) + "m/s");
                        break;
                }
        }
        for (int i = 0; i < dust_list.length - 1; i++) {
            if (dust_list[i].equals("지역 :" + address_dust_station)) {
                set_i = i;
                if (dust_list[set_i-2].contains("미세먼지25 :")) {
                    tv_pm25Value.setText("미세먼지(25pm) : " + dust_list[set_i-2].substring(8) + "㎍/㎥");
                }if (dust_list[set_i-5].contains("일산화탄소 :")) {
                    tv_coValue.setText("일산화탄소 : " + dust_list[set_i-5].substring(7) + "ppm");
                }if (dust_list[set_i-3].contains("미세먼지10 :")) {
                    tv_pm10Value.setText("미세먼지(10pm) : " + dust_list[set_i-3].substring(8) + "㎍/㎥");
                }if (dust_list[set_i-4].contains("오존지수 :")) {
                    if(dust_list[set_i-4].substring(6).equals("1")){
                        tv_o3Grade.setText("오존지수 : 좋음");
                    }
                    else if(dust_list[set_i-4].substring(6).equals("2")){
                        tv_o3Grade.setText("오존지수 : 보통");
                    }
                    else if(dust_list[set_i-4].substring(6).equals("3")){
                        tv_o3Grade.setText("오존지수 : 나쁨");
                    }
                    else if(dust_list[set_i-4].substring(6).equals("4")){
                        tv_o3Grade.setText("오존지수 : 매우나쁨");
                    }
                }if (dust_list[set_i-1].contains("일산화탄소지수 :")) {
                    if(dust_list[set_i-1].substring(9).equals("1")){
                        tv_coGrade.setText("일산화탄소지수 : 좋음");
                    }
                    else if(dust_list[set_i-1].substring(9).equals("2")){
                        tv_coGrade.setText("일산화탄소지수 : 보통");
                    }
                    else if(dust_list[set_i-1].substring(9).equals("3")){
                        tv_coGrade.setText("일산화탄소지수 : 나쁨");
                    }
                    else if(dust_list[set_i-1].substring(9).equals("4")){
                        tv_coGrade.setText("일산화탄소지수 : 매우나쁨");
                    }
                }if (dust_list[set_i+1].contains("오존 :")) {
                    tv_o3Value.setText("오존 : "+ dust_list[set_i+1].substring(4) + "ppm");
                }
            }
        }
        tv_tmp.setVisibility(View.VISIBLE);
        tv_sky.setVisibility(View.VISIBLE);
        tv_water.setVisibility(View.VISIBLE);
        tv_waterPersent.setVisibility(View.VISIBLE);
        tv_vec.setVisibility(View.VISIBLE);
        tv_wsd.setVisibility(View.VISIBLE);
        tv_humidity.setVisibility(View.VISIBLE);
        img_weather.setVisibility(View.VISIBLE);

        tv_pm25Value.setVisibility(View.VISIBLE);
        tv_coValue.setVisibility(View.VISIBLE);
        tv_o3Value.setVisibility(View.VISIBLE);
        tv_o3Grade.setVisibility(View.VISIBLE);
        tv_pm10Value.setVisibility(View.VISIBLE);
        tv_coGrade.setVisibility(View.VISIBLE);

        tv_slash1.setVisibility(View.VISIBLE);
        tv_slash2.setVisibility(View.VISIBLE);
        tv_slash3.setVisibility(View.VISIBLE);
        tv_slash4.setVisibility(View.VISIBLE);
        tv_slash5.setVisibility(View.VISIBLE);
        tv_slash6.setVisibility(View.VISIBLE);
    }

    //날씨 https://textbox.tistory.com/13 참고자료
    private String getDay() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String get_day = dateFormat.format(date);
        return get_day;
    }
    //날씨
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        String get_time = dateFormat.format(date);
        int time_tmp = Integer.parseInt(get_time);
        if (time_tmp >= 210 && time_tmp < 510) {
            return "0200";
        } else if (time_tmp >= 510 && time_tmp < 810) {
            return "0500";
        } else if (time_tmp >= 810 && time_tmp < 1110) {
            return "0800";
        } else if (time_tmp >= 1110 && time_tmp < 1410) {
            return "1100";
        } else if (time_tmp >= 1410 && time_tmp < 1710) {
            return "1400";
        } else if (time_tmp >= 1710 && time_tmp < 2010) {
            return "1700";
        } else if (time_tmp >= 2010 && time_tmp < 2310) {
            return "2000";
        } else {
            return "2300";
        }
    }

    // 날씨
    String getData() {
        StringBuffer buffer = new StringBuffer();
        url_m = url_weather + url_service_Key + "&numOfRows=10&pageNo=2&base_date=" + getDay() + "&base_time=" + getTime() + "&nx="+url_x+"&ny="+url_y;

        try {
            URL url = new URL(url_m); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); // url 위치로 인풋스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기

                        if (tag.equals("item")) ;
                        else if (tag.equals("fcstDate")) {
                            buffer.append("날짜 : ");
                            xpp.next();
                            // addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(xpp.getText());
                            buffer.append("\n"); // 줄바꿈 문자 추가
                        } else if (tag.equals("fcstTime")) {
                            buffer.append("시간 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("category")) {
                            buffer.append("종류 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("fcstValue")) {
                            buffer.append("값 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    }

    @SuppressLint("SetTextI18n")
    String get_search_Data(int xx, int yy) {
        StringBuffer buffer = new StringBuffer();
        url_m_search = url_weather + url_service_Key + "&numOfRows=10&pageNo=2&base_date=" + getDay() + "&base_time=" + getTime() + "&nx="+xx+"&ny="+yy;

        try {
            URL url = new URL(url_m_search); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); // url 위치로 인풋스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기

                        if (tag.equals("item")) ;
                        else if (tag.equals("fcstDate")) {
                            buffer.append("날짜 : ");
                            xpp.next();
                            // addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(xpp.getText());
                            buffer.append("\n"); // 줄바꿈 문자 추가
                        } else if (tag.equals("fcstTime")) {
                            buffer.append("시간 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("category")) {
                            buffer.append("종류 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("fcstValue")) {
                            buffer.append("값 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @SuppressLint("SetTextI18n")
        public void onLocationChanged(Location location) {
            cur_lon = location.getLongitude(); //경도
            cur_lat = location.getLatitude();   //위도
            MainActivity_login.LatXLngY tmp = convertGRID_GPS(TO_GRID, cur_lat, cur_lon);
            address = getCurrentAddress(cur_lat, cur_lon);

            tv_address_login = findViewById(R.id.tv_address_login);
            tv_address_login.setText(address);
            tv_address_login.setSingleLine(true);    // 한줄로 표시하기
            tv_address_login.setEllipsize(TextUtils.TruncateAt.MARQUEE); // 흐르게 만들기
            tv_address_login.setSelected(true);

            tv_address = findViewById(R.id.tv_address);
            tv_address.setText(address); // 주소 표시

            Array_dust = address.split(" ");
            address_dust_sido = Array_dust[1].substring(0,2);
            address_dust_station = Array_dust[4];

            url_x = (int) Math.round(tmp.x);
            url_y = (int) Math.round(tmp.y);
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
        }
    };

    //관리자 권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }
    //관리자 권한
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    // 격자좌표로 변경 (https://gist.github.com/fronteer-kr/14d7f779d52a21ac2f16 참고자료)
    private MainActivity_login.LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y )
    {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        MainActivity_login.LatXLngY rs = new MainActivity_login.LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;
    }
    // 격자좌표로 변경 (https://gist.github.com/fronteer-kr/14d7f779d52a21ac2f16 참고자료)
    class LatXLngY
    {
        public double lat;
        public double lng;

        public double x; // 반환은 x -> 격자좌표
        public double y; // 반환은 y -> 격자좌표

    }
    // 주소 가져오기 https://webnautes.tistory.com/1315 참고자료
    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    10);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // nav_header 버튼 관리
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_position).getActionView();
        searchView.setQueryHint("ex) 서울 종로구");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                address_search = s.substring(3);
                Address_dust_sido_search = s.substring(0,2);
                Location location = getLocationFromAddress(getApplicationContext(), s);
                search_lat = location.getLatitude();
                search_lon = location.getLongitude();
                LatXLngY search_tmp = convertGRID_GPS(TO_GRID, search_lat, search_lon);
                url_xx = (int) Math.round(search_tmp.x);
                url_yy = (int) Math.round(search_tmp.y);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        tv_login_id = findViewById(R.id.tv_login_id);
        tv_login_name = findViewById(R.id.tv_login_name);
        tv_login_company = findViewById(R.id.tv_login_company);
        tv_login_phone = findViewById(R.id.tv_login_phone);
        tv_address_login = findViewById(R.id.tv_address_login);
        btn_tell = findViewById(R.id.btn_tell);

        Intent intent = getIntent(); // 1
        userID = intent.getStringExtra("userID"); // 2
        userPassword = intent.getStringExtra("userPassword");
        userCompany = intent.getStringExtra("userCompany");
        userName = intent.getStringExtra("userName");

        tv_login_id.setText(userID);
        tv_login_name.setText(userName);
        tv_login_company.setText(userCompany);
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_login);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    String getFineDust() {
        StringBuffer buffer_dust = new StringBuffer();
        url_d = url_dust + address_dust_sido + "&pageNo=1&numOfRows=100&returnType=xml&serviceKey=" +  url_dust_service_Key + "&ver=1.0";

        try {
            URL url = new URL(url_d); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); // url 위치로 인풋스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기

                        if (tag.equals("item")) ;
                        else if (tag.equals("pm25Value")) {
                            buffer_dust.append("미세먼지25 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n"); // 줄바꿈 문자 추가
                        } else if (tag.equals("coValue")) {
                            buffer_dust.append("일산화탄소 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        } else if (tag.equals("pm10Value")) {
                            buffer_dust.append("미세먼지10 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("o3Grade")) {
                            buffer_dust.append("오존지수 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("coGrade")) {
                            buffer_dust.append("일산화탄소지수 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("o3Value")) {
                            buffer_dust.append("오존 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("stationName")) {
                            buffer_dust.append("지역 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) buffer_dust.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return buffer_dust.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    }

    String getFineDust_search() {
        StringBuffer buffer_dust = new StringBuffer();
        url_d_search = url_dust + Address_dust_sido_search + "&pageNo=1&numOfRows=100&returnType=xml&serviceKey=" +  url_dust_service_Key + "&ver=1.0";

        try {
            URL url = new URL(url_d_search); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); // url 위치로 인풋스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기

                        if (tag.equals("item")) ;
                        else if (tag.equals("pm25Value")) {
                            buffer_dust.append("미세먼지25 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n"); // 줄바꿈 문자 추가
                        } else if (tag.equals("coValue")) {
                            buffer_dust.append("일산화탄소 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        } else if (tag.equals("pm10Value")) {
                            buffer_dust.append("미세먼지10 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("o3Grade")) {
                            buffer_dust.append("오존지수 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("coGrade")) {
                            buffer_dust.append("일산화탄소지수 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("o3Value")) {
                            buffer_dust.append("오존 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("stationName")) {
                            buffer_dust.append("지역 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) buffer_dust.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return buffer_dust.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    }

    // 위치 검색
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address, 10);
            Address addressLoc = addresses.get(0);

            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLocation;
    }
}