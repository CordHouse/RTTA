package com.example.capstone_1.ui.function2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone_1.R;
import com.example.capstone_1.databinding.FragmentFunction2Binding;

public class function2Fragment extends Fragment {

    private function2ViewModel slideshowViewModel;
    private FragmentFunction2Binding binding;

    private Button btn_youTube, btn_youTube1, btn_youTube2, btn_youTube3, btn_youTube4, btn_youTube5, btn_youTube6, btn_youTube7;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(function2ViewModel.class);

        binding = FragmentFunction2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();


        btn_youTube = root.findViewById(R.id.btn_youTube);
        btn_youTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "ZgyXP_SPPpo");
                startActivity(intent);
            }
        });

        btn_youTube1 = root.findViewById(R.id.btn_youTube1);
        btn_youTube1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "Daj3U08h4rc");
                startActivity(intent);
            }
        });

        btn_youTube2 = root.findViewById(R.id.btn_youTube2);
        btn_youTube2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "NfzxJ7TlhQ0");
                startActivity(intent);
            }
        });

        btn_youTube3 = root.findViewById(R.id.btn_youTube3);
        btn_youTube3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "waXpMQgubTI");
                startActivity(intent);
            }
        });

        btn_youTube4 = root.findViewById(R.id.btn_youTube4);
        btn_youTube4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "Jqj48mhziJM");
                startActivity(intent);
            }
        });

        btn_youTube5 = root.findViewById(R.id.btn_youTube5);
        btn_youTube5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "ViZtrjdwY9I");
                startActivity(intent);
            }
        });

        btn_youTube6 = root.findViewById(R.id.btn_youTube6);
        btn_youTube6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "VoTP-c5Sme8");
                startActivity(intent);
            }
        });

        btn_youTube7 = root.findViewById(R.id.btn_youTube7);
        btn_youTube7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.capstone_1.youTube_Activity.class);
                intent.putExtra("videoId", "mu9g3CCBKhQ");
                startActivity(intent);
            }
        });

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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