package com.example.poem5_12_25.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.widget.Switch;

import com.example.poem5_12_25.R;
import com.example.poem5_12_25.databinding.ActivityMainBinding;
import com.example.poem5_12_25.databinding.ActivitySettingBinding;

import butterknife.BindView;

public class SettingActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // livedata 绑定数据
        ActivitySettingBinding inflate = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        setContentView(R.layout.activity_setting);



    }

}