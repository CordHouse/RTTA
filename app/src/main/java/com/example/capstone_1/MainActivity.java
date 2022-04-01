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
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone_1.databinding.ActivityMainBinding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button btn_login, btn_register;
    private TextView tv_water, tv_waterPersent, tv_tmp, tv_sky, tv_vec, tv_wsd, tv_humidity, tv_date, tv_time, tv_address;
    private ImageView img_weather;

    //주소 + 키 + 수정 등등 해야함
    private String url_weather = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=";
    private String url_service_Key = "vYddcBhVO08I9DSvmVD6Mhs12hpr7JzPOK8VQcn80lKi07kH4clX2seo0VFMebG45VJTEUBC91nYSvUsXQzDpg%3D%3D";
    private int url_x, url_y;
    private String url_m;
    private String html;
    private String[] Array;

    //주소
    private double cur_lat;
    private double cur_lon;
    public static int TO_GRID = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main, R.id.nav_function1, R.id.nav_function2, R.id.nav_function3, R.id.nav_function4)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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
        // 나오긴 하는데, 각자 어떤 역할인지 구분해서 나눠줘야함..
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        html = getData();
                        Array = html.split("\n");
                        runOnUiThread(new Runnable() {
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
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
            }
        }).start();
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

    //네비
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // nav_header 버튼 관리
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() { // 로그인
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() { // 회원가입
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    // 설정
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @SuppressLint("SetTextI18n")
        public void onLocationChanged(Location location) {
            cur_lon = location.getLongitude(); //경도
            cur_lat = location.getLatitude();   //위도
            LatXLngY tmp = convertGRID_GPS(TO_GRID, cur_lat, cur_lon);
            String address = getCurrentAddress(cur_lat, cur_lon);
            tv_address = findViewById(R.id.tv_address);
            tv_address.setText(address); // 주소 표시

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
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    // 격자좌표로 변경 (https://gist.github.com/fronteer-kr/14d7f779d52a21ac2f16 참고자료)
    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y )
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
        LatXLngY rs = new LatXLngY();

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

}
