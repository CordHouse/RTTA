package com.example.capstone_1.ui.function3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone_1.R;
import com.example.capstone_1.databinding.FragmentFunction2Binding;
import com.example.capstone_1.databinding.FragmentFunction3Binding;
import com.example.capstone_1.function3_Activity;

import java.util.HashMap;

public class function3Fragment extends Fragment {

    private function3ViewModel slideshowViewModel;
    private FragmentFunction3Binding binding;

    private Spinner spinner_law;
    private String url_m, news_Item;
    private Button btn_search_law;
    private HashMap<String, String> hashMap = new HashMap<>();
    private WebView wb_box;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(function3ViewModel.class);

        binding = FragmentFunction3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        hashMap.put("주차 및 정차", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=2&cciNo=2&cnpClsNo=1&search_put=");
        hashMap.put("고속도로 주의사항", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=2&cciNo=3&cnpClsNo=1&search_put=");
        hashMap.put("저속전기자동차 운행제한", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=2&cciNo=3&cnpClsNo=2&search_put=");
        hashMap.put("벌칙", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=3&cciNo=1&cnpClsNo=1&search_put=");
        hashMap.put("범칙금", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=3&cciNo=1&cnpClsNo=2&search_put=");
        hashMap.put("과태료", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=3&cciNo=1&cnpClsNo=3&search_put=");
        hashMap.put("교통사고 발생시 조치사항", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=4&cciNo=1&cnpClsNo=1&search_put=");
        hashMap.put("교통사고 신고 및 조사", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=4&cciNo=1&cnpClsNo=2&search_put=");
        hashMap.put("교통사고 발생시 형사처벌", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=4&cciNo=2&cnpClsNo=2&search_put=");
        hashMap.put("교통사고 발생시 손해배상", "https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=684&ccfNo=4&cciNo=2&cnpClsNo=3&search_put=");

        spinner_law = root.findViewById(R.id.spinner_law);
        btn_search_law = root.findViewById(R.id.btn_search_law);
        wb_box = root.findViewById(R.id.wb_box);

        spinner_law.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                url_m = "";
                news_Item = adapterView.getItemAtPosition(i).toString();
                if(news_Item.equals("주차 및 정차")){
                    url_m = "주차 및 정차";
                }
                else if(news_Item.equals("고속도로 주의사항")){
                    url_m = "고속도로 주의사항";
                }
                else if(news_Item.equals("저속전기자동차 운행제한")){
                    url_m = "저속전기자동차 운행제한";
                }
                else if(news_Item.equals("벌칙")){
                    url_m = "벌칙";
                }
                else if(news_Item.equals("범칙금")){
                    url_m = "범칙금";
                }
                else if(news_Item.equals("과태료")){
                    url_m = "과태료";
                }
                else if(news_Item.equals("교통사고 발생시 조치사항")){
                    url_m = "교통사고 발생시 조치사항";
                }
                else if(news_Item.equals("교통사고 신고 및 조사")){
                    url_m = "교통사고 신고 및 조사";
                }
                else if(news_Item.equals("교통사고 발생시 형사처벌")){
                    url_m = "교통사고 발생시 형사처벌";
                }
                else if(news_Item.equals("교통사고 발생시 손해배상")){
                    url_m = "교통사고 발생시 손해배상";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_search_law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i<hashMap.size()-1; i++){
                    if(hashMap.containsKey(url_m)){
                        wb_box.loadUrl(hashMap.get(url_m));
                        break;
                    }
                }
            }
        });

        wb_box.getSettings().setJavaScriptEnabled(true);
        wb_box.setWebChromeClient(new WebChromeClient());
        wb_box.setWebViewClient(new myWebViewClient());
        WebSettings webSet = wb_box.getSettings();
        webSet.setBuiltInZoomControls(true);


        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}