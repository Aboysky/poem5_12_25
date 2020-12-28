package com.example.poem5_12_25.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.poem5_12_25.GlobalConfig;
import com.example.poem5_12_25.MainApp;
import com.example.poem5_12_25.R;
import com.example.poem5_12_25.databinding.ActivityMainBinding;
import com.example.poem5_12_25.databinding.ActivitySettingBinding;
import com.example.poem5_12_25.entity.Poem;
import com.example.poem5_12_25.utils.http.HttpRequestUtil;
import com.example.poem5_12_25.viewmodel.ConfigViewModel;

import butterknife.BindView;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // livedata 绑定数据
        ActivitySettingBinding inflate = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        // 初始化保存的数据  为什么不用 viewmodel对象来保存呢?? 因为我需要我下次进来的时候,我仍然能够看到一模一样的,viewmodel对象在程序关闭后也会背杀死
        SharedPreferences preferences1 = MainApp.getInstance().getSharedPreferences("usersetting", Context.MODE_PRIVATE);
        inflate.settingRefreshRegisterPage.setChecked(preferences1.getBoolean("register_refresh",Boolean.FALSE));
        inflate.settingRefreshUserPage.setChecked(preferences1.getBoolean("home_refresh",Boolean.FALSE));
        inflate.settingRefreshPoemPage.setChecked(preferences1.getBoolean("poem_refresh",Boolean.FALSE));
        inflate.settingClearDataLogout.setChecked(preferences1.getBoolean("clear_all",Boolean.FALSE));
        inflate.settingNightCode.setChecked(preferences1.getBoolean("night_mode",Boolean.FALSE));
        inflate.settingTextSize.setChecked(preferences1.getBoolean("max_text",Boolean.FALSE));
        inflate.settingRefreshLoginPage.setChecked(preferences1.getBoolean("login_refresh",Boolean.FALSE));
        inflate.settingCloudUpload.setChecked(preferences1.getBoolean("cloud_upload",Boolean.FALSE));

        ConfigViewModel configViewModel = new ViewModelProvider(this).get(ConfigViewModel.class);



        inflate.settingCloudUpload.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            configViewModel.setCloud_update(isChecked);
        });

        inflate.settingClearDataLogout.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            configViewModel.setClear_all(isChecked);
        });

        inflate.settingNightCode.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            configViewModel.setNight_mode(isChecked);
        });

        inflate.settingRefreshLoginPage.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            configViewModel.setLogin_refresh(isChecked);
        });

        inflate.settingRefreshPoemPage.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            configViewModel.setPoem_refresh(isChecked);
        });

        inflate.settingRefreshRegisterPage.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            configViewModel.setRegister_refresh(isChecked);
        });

        inflate.settingRefreshUserPage.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            configViewModel.setHome_refresh(isChecked);
        });

        inflate.settingTextSize.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            configViewModel.setMax_text(isChecked);
        });
//        // 监视 是否上传用户信息是否已经改变
//        configViewModel.getCloud_update().observe(this, aBoolean -> {
//            Log.d("上传用户信息",aBoolean.toString());
//            if (aBoolean) {
//                new UploadUserInfo().execute(this);
//            }
//
//        });
        inflate.settingSave.setOnClickListener(v -> {
            ConfigViewModel configViewModel1 = new ViewModelProvider(SettingActivity.this).get(ConfigViewModel.class);
            SharedPreferences preferences = MainApp.getInstance().getSharedPreferences("usersetting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("register_refresh", configViewModel1.getRegister_refresh().getValue());
            editor.putBoolean("home_refresh", configViewModel1.getHome_refresh().getValue());
            editor.putBoolean("poem_refresh", configViewModel1.getPoem_refresh().getValue());
            editor.putBoolean("clear_all", configViewModel1.getClear_all().getValue());
            editor.putBoolean("night_mode", configViewModel1.getNight_mode().getValue());
            editor.putBoolean("max_text", configViewModel1.getMax_text().getValue());
            editor.putBoolean("login_refresh", configViewModel1.getLogin_refresh().getValue());
            editor.putBoolean("cloud_upload", configViewModel1.getCloud_update().getValue());
            editor.apply();
            startActivity(new Intent(this,UserActivity.class));
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        ConfigViewModel configViewModel = new ViewModelProvider(this).get(ConfigViewModel.class);
        SharedPreferences preferences = MainApp.getInstance().getSharedPreferences("usersetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("register_refresh", configViewModel.getRegister_refresh().getValue());
        editor.putBoolean("home_refresh", configViewModel.getHome_refresh().getValue());
        editor.putBoolean("poem_refresh", configViewModel.getPoem_refresh().getValue());
        editor.putBoolean("clear_all", configViewModel.getClear_all().getValue());
        editor.putBoolean("night_mode", configViewModel.getNight_mode().getValue());
        editor.putBoolean("max_text", configViewModel.getMax_text().getValue());
        editor.putBoolean("login_refresh", configViewModel.getLogin_refresh().getValue());
        editor.putBoolean("F", configViewModel.getCloud_update().getValue());
        editor.apply();
    }
}