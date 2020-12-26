package com.example.poem5_12_25.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poem5_12_25.MainApp;
import com.example.poem5_12_25.R;
import com.example.poem5_12_25.cache.LastPoemCache;
import com.example.poem5_12_25.dao.UserDao;
import com.example.poem5_12_25.database.Database1;
import com.example.poem5_12_25.entity.Poem;
import com.example.poem5_12_25.entity.User;
import com.example.poem5_12_25.pojo.PoemPojo;
import com.example.poem5_12_25.utils.http.HttpRequestUtil;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_loginactivity_register)
    TextView mTvLoginactivityRegister;
    @BindView(R.id.rl_loginactivity_top)
    RelativeLayout mRlLoginactivityTop;
    @BindView(R.id.et_loginactivity_username)
    EditText mEtLoginactivityUsername;
    @BindView(R.id.et_loginactivity_password)
    EditText mEtLoginactivityPassword;
    @BindView(R.id.ll_loginactivity_two)
    LinearLayout mLlLoginactivityTwo;

    private Context context = this;

    /**
     * 保存当前用户名和密码,以用来存入数据库
     */
    private String currentUsername;
    private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.tv_loginactivity_register,
            R.id.bt_loginactivity_login
    })
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    new GetLogin().execute(mEtLoginactivityUsername.getText().toString(), mEtLoginactivityPassword.getText().toString());
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /**
     * 发起登录请求
     */
    class GetLogin extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context,"正在登录中",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String username = "",password = "";
            username = strings[0];
            currentUsername = username;
            password = strings[1];
            currentPassword = password;
            return HttpRequestUtil.login(username,password);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                SharedPreferences user = MainApp.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =user.edit();
                editor.putString("currentusername",currentUsername);
                Intent intent = new Intent(context, PoemActivity.class);
                UserDao userDao = Database1.getInstance(context).UserDao();
                userDao.deleteAllUser();
                userDao.insertUser(new User(1,currentUsername,currentPassword));
                startActivity(intent);
                finish();//销毁这个Activity
            }else {
                Toast.makeText(context, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
            }
        }
    }
}