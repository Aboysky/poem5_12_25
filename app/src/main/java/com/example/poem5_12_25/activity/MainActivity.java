package com.example.poem5_12_25.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.poem5_12_25.GlobalObject;
import com.example.poem5_12_25.R;
import com.example.poem5_12_25.database.Database1;
import com.example.poem5_12_25.entity.User;
import com.example.poem5_12_25.viewmodel.ConfigViewModel;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bt_main_login)
    Button logingButton;

    @BindView(R.id.bt_main_register)
    Button registerButton;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        // 如果本地数据库中有用户数据,则重新重定向到用户界面
//        GlobalObject.submitTask(() -> {
//            int i = Database1.getInstance(context).UserDao().deleteAllUser();
//            Log.d("删除所有用户数据----------",String.valueOf(i));
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        });
        GlobalObject.submitTask(() -> {
            List<User> users = Database1.getInstance(context).UserDao().selectAllUser();
            if (!Database1.getInstance(context).UserDao().selectAllUser().isEmpty()) {
                Intent intent = new Intent(context,UserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GlobalObject.submitTask(() -> {
            List<User> users = Database1.getInstance(context).UserDao().selectAllUser();
            if (!Database1.getInstance(context).UserDao().selectAllUser().isEmpty()) {
                Intent intent = new Intent(context,UserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    @OnClick({
            R.id.bt_main_login,
            R.id.bt_main_register
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_main_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.bt_main_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }
}