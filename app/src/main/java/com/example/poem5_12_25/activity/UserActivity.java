package com.example.poem5_12_25.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.poem5_12_25.GlobalObject;
import com.example.poem5_12_25.R;
import com.example.poem5_12_25.database.Database1;

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
        setContentView(R.layout.activity_user);

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
}