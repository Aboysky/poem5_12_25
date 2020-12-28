package com.example.poem5_12_25.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.poem5_12_25.GlobalObject;
import com.example.poem5_12_25.MainApp;
import com.example.poem5_12_25.R;
import com.example.poem5_12_25.database.Database1;
import com.example.poem5_12_25.databinding.ActivitySettingBinding;
import com.example.poem5_12_25.databinding.ActivityUserBinding;
import com.example.poem5_12_25.entity.Poem;
import com.example.poem5_12_25.entity.User;
import com.example.poem5_12_25.pojo.PoemPojo;
import com.example.poem5_12_25.utils.http.HttpRequestUtil;
import com.example.poem5_12_25.viewmodel.ConfigViewModel;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {

    @BindView(R.id.bt_user_foreiner)
    Button forinerButton;
    @BindView(R.id.bt_user_logout)
    Button registerButton;
    @BindView(R.id.bt_user_novel)
    Button novelButton;
    @BindView(R.id.bt_user_poem)
    Button poemButton;
    @BindView(R.id.bt_user_song)
    Button songButton;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUserBinding inflate = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());


    }

    @Override
    protected void onStart() {
        super.onStart();
        ConfigViewModel configViewModel = new ViewModelProvider(this).get(ConfigViewModel.class);
        // 监视 是否上传用户信息是否已经改变
        if (configViewModel.getCloud_update().getValue()!=null&&configViewModel.getCloud_update().getValue()) {
                Log.d("上传用户信息:",":");
                new UploadUserInfo().execute(UserActivity.this);
        }

        if (configViewModel.getNight_mode().getValue()!=null&&configViewModel.getNight_mode().getValue()) {
            Log.d("夜间模式开启","true");
            if (MainApp.getInstance().getNightMode()){

            }else {
                MainApp.getInstance().setNightMode(true);
                ((AppCompatActivity) context).recreate();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 退出清除所有用户数据
        ConfigViewModel configViewModel = new ViewModelProvider(this).get(ConfigViewModel.class);
        if (configViewModel.getClear_all().getValue()!=null&&configViewModel.getClear_all().getValue()){
            Log.d("退出清除所有数据","true");
            GlobalObject.submitTask(() -> {
                Database1.getInstance(UserActivity.this).UserDao().deleteAllUser();
                Database1.getInstance(UserActivity.this).FavorityPoemDao().deleteAllFavorityPoem();
                Database1.getInstance(UserActivity.this).PoemDao().deleteAllPoem();
            });
        }
    }

    @OnClick({
            R.id.bt_user_foreiner,
            R.id.bt_user_logout,
            R.id.bt_user_novel,
            R.id.bt_user_poem,
            R.id.bt_user_song,
            R.id.bt_system_setting
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_user_foreiner:
//                startActivity(new Intent(this, LoginActivity.class));
//                finish();
                break;
            case R.id.bt_user_logout:
                // 退出时删除所有用户数据
                GlobalObject.submitTask(() -> {
                    int i = Database1.getInstance(context).UserDao().deleteAllUser();
                    Log.d("删除所有用户数据----------",String.valueOf(i));
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                });

                // 到底删不删除喜欢的诗?????????


                break;
            case R.id.bt_user_novel:
//                startActivity(new Intent(this, RegisterActivity.class));
//                finish();
                break;
            case R.id.bt_user_poem:
                startActivity(new Intent(this, PoemActivity.class));
                finish();
                break;
            case R.id.bt_user_song:
//                startActivity(new Intent(this, RegisterActivity.class));
//                finish();
                break;
            case R.id.bt_system_setting:
                startActivity(new Intent(this, SettingActivity.class));
                finish();
                break;
        }
    }

    /**
     * 记住: 参数为: Context
     */
    class UploadUserInfo extends AsyncTask<AppCompatActivity, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(AppCompatActivity... users) {
            return HttpRequestUtil.UploadUserInfo(users[0]);
        }
//
        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            Log.d("是否上传成功:",bool.toString());
            if (bool) {
                Toast.makeText(UserActivity.this, "用户信息上传成功", Toast.LENGTH_SHORT).show();
            } else
                Snackbar.make(findViewById(R.id.user_layout), "网络连接失败！", Snackbar.LENGTH_SHORT).show();
        }
    }
}