package com.example.capstone_1.ui.function1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone_1.R;
import com.example.capstone_1.databinding.FragmentFunction1Binding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class function1Fragment extends Fragment {

    private function1ViewModel galleryViewModel;
    private FragmentFunction1Binding binding;

    private Button btn_youTube, btn_youTube1;
    private Button btn_level1, btn_level2, btn_level3, btn_level4, btn_level5, btn_reset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(function1ViewModel.class);

        binding = FragmentFunction1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_youTube = root.findViewById(R.id.btn_youTube);
        btn_youTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "U33y0eTKB4w");
                startActivity(intent);
            }
        });

        btn_youTube1 = root.findViewById(R.id.btn_youTube1);
        btn_youTube1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "U350etE-iWA");
                startActivity(intent);
            }
        });

        btn_level1 = root.findViewById(R.id.btn_level1);
        btn_level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_level1.setBackgroundColor(Color.YELLOW);
                btn_level1.setClickable(false);
            }
        });
        btn_level2 = root.findViewById(R.id.btn_level2);
        btn_level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_level2.setBackgroundColor(Color.YELLOW);
                btn_level2.setClickable(false);
            }
        });
        btn_level3 = root.findViewById(R.id.btn_level3);
        btn_level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_level3.setBackgroundColor(Color.YELLOW);
                btn_level3.setClickable(false);
            }
        });
        btn_level4 = root.findViewById(R.id.btn_level4);
        btn_level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_level4.setBackgroundColor(Color.YELLOW);
                btn_level4.setClickable(false);
            }
        });
        btn_level5 = root.findViewById(R.id.btn_level5);
        btn_level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_level5.setBackgroundColor(Color.YELLOW);
                btn_level5.setClickable(false);
            }
        });
        btn_reset = root.findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_level1.setBackgroundColor(Color.GRAY);
                btn_level1.setClickable(true);
                btn_level2.setBackgroundColor(Color.GRAY);
                btn_level2.setClickable(true);
                btn_level3.setBackgroundColor(Color.GRAY);
                btn_level3.setClickable(true);
                btn_level4.setBackgroundColor(Color.GRAY);
                btn_level4.setClickable(true);
                btn_level5.setBackgroundColor(Color.GRAY);
                btn_level5.setClickable(true);
            }
        });

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}