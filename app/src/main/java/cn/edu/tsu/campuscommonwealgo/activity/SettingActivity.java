package cn.edu.tsu.campuscommonwealgo.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.dao.util.UserDaoUtil;
import cn.edu.tsu.campuscommonwealgo.entity.User;
import cn.edu.tsu.campuscommonwealgo.util.LoginUtil;
import cn.edu.tsu.campuscommonwealgo.util.TransparentStatusBarUtil;
import cn.edu.tsu.campuscommonwealgo.util.ViewUtil;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.tv_tab_name)
    TextView mTxtTabName;
    @BindView(R.id.iv_left)
    ImageView mImgLeft;
    @BindView(R.id.iv_right)
    ImageView mImgRight;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TransparentStatusBarUtil.setTransparent(this);
        }
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        username = getIntent().getStringExtra("username");

        mImgLeft.setImageResource(R.mipmap.ic_back);
        ViewUtil.hide(mImgRight);
        mTxtTabName.setText("设置");
    }

    @OnClick({R.id.iv_left, R.id.tv_log_out})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_log_out:
                logout();
                break;
            default:
                break;
        }
    }

    private void logout() {
        LoginUtil.clear(getApplicationContext());
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
