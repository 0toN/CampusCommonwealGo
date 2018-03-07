package cn.edu.tsu.campuscommonwealgo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.dao.util.UserDaoUtil;
import cn.edu.tsu.campuscommonwealgo.entity.User;
import cn.edu.tsu.campuscommonwealgo.util.LoginUtil;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, 2000);
    }

    private void checkLoginStatus() {
        if (LoginUtil.isLogin(getApplicationContext())) {
            autoLogin();
        } else {
            toLogin();
        }
    }

    private void autoLogin() {
        String token = LoginUtil.getLoginInfo(this);
        List<User> users = UserDaoUtil.queryUserByToken(token);
        if (users.size() == 1) {
            User user = users.get(0);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("username", user.getUsername());
            startActivity(intent);
            finish();
        } else {
            toLogin();
        }
    }

    private void toLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
