package cn.edu.tsu.campuscommonwealgo.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.dao.util.UserDaoUtil;
import cn.edu.tsu.campuscommonwealgo.entity.User;
import cn.edu.tsu.campuscommonwealgo.util.TransparentStatusBarUtil;

public class UpdatePersonalInfoActivity extends AppCompatActivity {
    @BindView(R.id.tv_tab_name)
    TextView mTxtTabName;
    @BindView(R.id.iv_left)
    ImageView mImgLeft;
    @BindView(R.id.iv_right)
    ImageView mImgRight;
    @BindView(R.id.et_nickname)
    EditText mEdtNickname;
    @BindView(R.id.et_school)
    EditText mEdtSchool;
    @BindView(R.id.nicknameWrapper)
    TextInputLayout mNicknameWrapper;
    @BindView(R.id.schoolWrapper)
    TextInputLayout mSchoolWrapper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TransparentStatusBarUtil.setTransparent(this);
        }
        setContentView(R.layout.activity_update_personal_info);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        List<User> users = UserDaoUtil.queryUserByUsername(intent.getStringExtra("username"));
        user = users.get(0);

        mImgLeft.setImageResource(R.mipmap.ic_back);
        mImgRight.setImageResource(R.mipmap.ic_save);
        mTxtTabName.setText("个人信息");
        mEdtNickname.setText(user.getNickname());
        mEdtSchool.setText(user.getSchool());
    }

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right:
                String nickname = mEdtNickname.getText().toString();
                String school = mEdtSchool.getText().toString();
                if (!validateNickname(nickname)) {
                    mNicknameWrapper.setError("昵称不能为空");
                } else if (!validateSchool(school)) {
                    mSchoolWrapper.setError("学校不能为空");
                }else{
                    mNicknameWrapper.setErrorEnabled(false);
                    mSchoolWrapper.setErrorEnabled(false);
                    saveInfo(nickname,school);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void saveInfo(String nickname, String school) {
        user.setNickname(nickname);
        user.setSchool(school);
        UserDaoUtil.updateUser(user);
    }

    private boolean validateNickname(String nickname) {
        if (nickname.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSchool(String school) {
        if (school.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
