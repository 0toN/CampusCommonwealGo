package cn.edu.tsu.campuscommonwealgo.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.activity.view.CustomVideoView;
import cn.edu.tsu.campuscommonwealgo.dao.util.UserDaoUtil;
import cn.edu.tsu.campuscommonwealgo.entity.User;
import cn.edu.tsu.campuscommonwealgo.util.AnimationUtil;
import cn.edu.tsu.campuscommonwealgo.util.Config;
import cn.edu.tsu.campuscommonwealgo.util.LogUtil;
import cn.edu.tsu.campuscommonwealgo.util.LoginUtil;
import cn.edu.tsu.campuscommonwealgo.util.MD5Encoder;
import cn.edu.tsu.campuscommonwealgo.util.ToastUtil;
import cn.edu.tsu.campuscommonwealgo.util.TransparentStatusBarUtil;
import cn.edu.tsu.campuscommonwealgo.util.ViewUtil;

public class LoginRegisterActivity extends AppCompatActivity {
    @BindView(R.id.videoview)
    CustomVideoView videoView;
    @BindView(R.id.et_login_username)
    EditText mEdtLoginUsername;
    @BindView(R.id.et_login_passwd)
    EditText mEdtLoginPasswd;
    @BindView(R.id.tv_login)
    TextView mBtnLogin;
    @BindView(R.id.login_layout)
    ConstraintLayout mLayoutLogin;
    @BindView(R.id.register_layout)
    ConstraintLayout mLayoutRegister;
    @BindView(R.id.et_register_username)
    EditText mEdtRegisterUsername;
    @BindView(R.id.et_register_passwd)
    EditText mEdtRegisterPasswd;
    @BindView(R.id.tv_register)
    TextView mBtnRegister;
    @BindView(R.id.cb_register_show_password)
    CheckBox mCbShowRegisterPw;
    @BindView(R.id.cb_login_show_password)
    CheckBox mCbShowLoginPw;
    @BindView(R.id.cb_reset_show_password)
    CheckBox mCbShowResetPw;
    @BindView(R.id.tv_register_to_login)
    TextView mBtnRegisterToLogin;
    @BindView(R.id.tv_login_to_register)
    TextView mBtnLoginToRegister;
    @BindView(R.id.reset_layout)
    ConstraintLayout mLayoutReset;
    @BindView(R.id.tv_login_to_reset)
    TextView mBtnLoginToReset;
    @BindView(R.id.tv_register_to_reset)
    TextView mBtnRegisterToReset;
    @BindView(R.id.tv_reset_to_login)
    TextView mBtnResetToLogin;
    @BindView(R.id.tv_reset_to_register)
    TextView mBtnResetToRegister;
    @BindView(R.id.et_reset_username)
    EditText mEdtResetUsername;
    @BindView(R.id.et_original_passwd)
    EditText mEdtOriginalPasswd;
    @BindView(R.id.et_new_passwd)
    EditText mEdtNewPasswd;
    @BindView(R.id.tv_reset)
    TextView mBtnReset;
    @BindView(R.id.iv_register_qq)
    ImageView mBtnQQ;
    @BindView(R.id.iv_register_wechat)
    ImageView mBtnWechat;
    @BindView(R.id.iv_register_weibo)
    ImageView mBtnWeibo;
    @BindView(R.id.layout_progressbar)
    ConstraintLayout mLayoutProgressbar;
    private TextWatcher textWatcher;

    private int msec;

    private User user;
    private String localToken;

    private static Tencent mTencent;
    private static boolean isServerSideLogin = false;
    private String openId;
    private String accessToken;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TransparentStatusBarUtil.setTransparent(this);
        }
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        initViews();
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Config.APP_ID, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        msec = videoView.getCurrentPosition();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        videoView.start();
        videoView.seekTo(msec);
    }

    private void initViews() {
        String viewSource = "android.resource://" + getPackageName() + "/" + R.raw.intro;
        videoView.setVideoURI(Uri.parse(viewSource));
        videoView.start();
        //循环播放
        videoView.setOnCompletionListener((mediaPlayer) -> {
            videoView.start();
        });

        mEdtLoginPasswd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEdtRegisterPasswd.setInputType(InputType.TYPE_CLASS_TEXT);
        createTextWatcher();
        mEdtLoginUsername.addTextChangedListener(textWatcher);
        mEdtLoginPasswd.addTextChangedListener(textWatcher);
        mEdtRegisterUsername.addTextChangedListener(textWatcher);
        mEdtRegisterPasswd.addTextChangedListener(textWatcher);
        mEdtResetUsername.addTextChangedListener(textWatcher);
        mEdtOriginalPasswd.addTextChangedListener(textWatcher);
        mEdtNewPasswd.addTextChangedListener(textWatcher);
        mCbShowLoginPw.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mEdtLoginPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mEdtLoginPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
        ));

        mCbShowRegisterPw.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mEdtRegisterPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mEdtRegisterPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }));

        mCbShowResetPw.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                mEdtNewPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mEdtNewPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }));
    }

    private void createTextWatcher() {
        textWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String loginUsername = mEdtLoginUsername.getText().toString();
                String loginPasswd = mEdtLoginPasswd.getText().toString().trim();
                if (Pattern.matches("[0-9]{11}", loginUsername) && !loginPasswd.equals("")) {
                    mBtnLogin.setBackgroundResource(R.drawable.login_register_reset_btn_enable_bg_shape);
                    mBtnLogin.setEnabled(true);
                } else {
                    mBtnLogin.setBackgroundResource(R.drawable.login_register_reset_btn_unable_bg_shape);
                    mBtnLogin.setEnabled(false);
                }

                String registerUsername = mEdtRegisterUsername.getText().toString();
                String registerPasswd = mEdtRegisterPasswd.getText().toString().trim();
                if (Pattern.matches("[0-9]{11}", registerUsername) && !registerPasswd.equals("")) {
                    mBtnRegister.setBackgroundResource(R.drawable.login_register_reset_btn_enable_bg_shape);
                    mBtnRegister.setEnabled(true);
                } else {
                    mBtnRegister.setBackgroundResource(R.drawable.login_register_reset_btn_unable_bg_shape);
                    mBtnRegister.setEnabled(false);
                }

                String resetUsername = mEdtResetUsername.getText().toString();
                String originalPasswd = mEdtOriginalPasswd.getText().toString().trim();
                String newPasswd = mEdtNewPasswd.getText().toString().trim();
                if (Pattern.matches("[0-9]{11}", resetUsername) && !originalPasswd.equals("") && !newPasswd.equals("")) {
                    mBtnReset.setBackgroundResource(R.drawable.login_register_reset_btn_enable_bg_shape);
                    mBtnReset.setEnabled(true);
                } else {
                    mBtnReset.setBackgroundResource(R.drawable.login_register_reset_btn_unable_bg_shape);
                    mBtnReset.setEnabled(false);
                }
            }
        };
    }

    @OnClick({R.id.tv_register_to_login, R.id.tv_login_to_register, R.id.iv_register_qq,
            R.id.iv_register_weibo, R.id.iv_register_wechat, R.id.tv_login, R.id.tv_register,
            R.id.tv_login_to_reset, R.id.tv_register_to_reset, R.id.tv_reset_to_login,
            R.id.tv_reset_to_register, R.id.tv_reset})
    void click(View v) {
        switch (v.getId()) {
            case R.id.tv_register_to_login:
                mLayoutLogin.setAnimation(AnimationUtil.leftMoveIn());
                mLayoutRegister.setAnimation(AnimationUtil.rightMoveOut());
                mLayoutLogin.setVisibility(View.VISIBLE);
                mLayoutRegister.setVisibility(View.GONE);
                break;
            case R.id.tv_login_to_register:
                mLayoutRegister.setAnimation(AnimationUtil.rightMoveIn());
                mLayoutLogin.setAnimation(AnimationUtil.leftMoveOut());
                mLayoutLogin.setVisibility(View.GONE);
                mLayoutRegister.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_login_to_reset:
                mLayoutLogin.setAnimation(AnimationUtil.rightMoveOut());
                mLayoutReset.setAnimation(AnimationUtil.leftMoveIn());
                mLayoutReset.setVisibility(View.VISIBLE);
                mLayoutLogin.setVisibility(View.GONE);
                break;
            case R.id.tv_reset_to_login:
                mLayoutLogin.setAnimation(AnimationUtil.rightMoveIn());
                mLayoutReset.setAnimation(AnimationUtil.leftMoveOut());
                mLayoutLogin.setVisibility(View.VISIBLE);
                mLayoutReset.setVisibility(View.GONE);
                break;
            case R.id.tv_register_to_reset:
                mLayoutReset.setAnimation(AnimationUtil.leftMoveIn());
                mLayoutRegister.setAnimation(AnimationUtil.rightMoveOut());
                mLayoutReset.setVisibility(View.VISIBLE);
                mLayoutRegister.setVisibility(View.GONE);
                break;
            case R.id.tv_reset_to_register:
                mLayoutRegister.setAnimation(AnimationUtil.leftMoveIn());
                mLayoutReset.setAnimation(AnimationUtil.rightMoveOut());
                mLayoutRegister.setVisibility(View.VISIBLE);
                mLayoutReset.setVisibility(View.GONE);
                break;
            case R.id.tv_register:
                register();
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_reset:
                reset();
                break;
            case R.id.iv_register_qq:
                loginByQQ();
                break;
            case R.id.iv_register_wechat:
                ToastUtil.showShort(this, "功能暂未开放");
                break;
            case R.id.iv_register_weibo:
                ToastUtil.showShort(this, "功能暂未开放");
                break;
            default:
                break;
        }
    }

    private void reset() {
        closeKeyboard();
        ViewUtil.show(mLayoutProgressbar);

        new Handler().postDelayed(() -> {
            String username = mEdtResetUsername.getText().toString();
            List<User> users = UserDaoUtil.queryUserByUsername(username);
            if (users.size() == 1) {
                User user = users.get(0);
                String originalPasswd = mEdtOriginalPasswd.getText().toString();
                if (originalPasswd.equals(user.getPassword())) {
                    String newPasswd = mEdtNewPasswd.getText().toString();
                    user.setPassword(newPasswd);
                    UserDaoUtil.updateUser(user);
                    mLayoutLogin.setAnimation(AnimationUtil.rightMoveIn());
                    mLayoutReset.setAnimation(AnimationUtil.leftMoveOut());
                    mLayoutLogin.setVisibility(View.VISIBLE);
                    mLayoutReset.setVisibility(View.GONE);
                    ToastUtil.showShort(this, "重置密码完成");
                } else {
                    ToastUtil.showShort(this, "原密码不正确");
                }
            } else {
                ToastUtil.showShort(this, "该手机号未注册");
            }
            ViewUtil.hide(mLayoutProgressbar);
        }, 1200);
    }

    private void login() {
        closeKeyboard();
        ViewUtil.show(mLayoutProgressbar);

        String username = mEdtLoginUsername.getText().toString();
        String password = mEdtLoginPasswd.getText().toString();
        new Handler().postDelayed(() -> {
            List<User> users = UserDaoUtil.queryUserByUsername(username);
            if (users.size() == 0) {
                ToastUtil.showShort(this, "该手机号未注册");
                ViewUtil.hide(mLayoutProgressbar);
            } else {
                User user = users.get(0);
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    localToken = user.getToken();
                    loginSuccess(username);
                } else {
                    ToastUtil.showShort(this, "密码不正确");
                    ViewUtil.hide(mLayoutProgressbar);
                }
            }
        }, 1200);
    }

    private void loginByQQCall(String nickname, String avatarUrl) {
        localToken = MD5Encoder.encode(openId + accessToken);
        user = new User();
        user.setUsername(openId);
        user.setPassword(accessToken);
        user.setNickname(nickname);
        user.setAvatarUrl(avatarUrl);
        user.setSchool("泰山学院");
        user.setThirdPartyUser(true);
        user.setToken(localToken);

        List<User> users = UserDaoUtil.queryUserByUsername(openId);
        if (users.size() == 0) {
            UserDaoUtil.insertUser(user);
        }
        loginSuccess(openId);
    }

    private void register() {
        closeKeyboard();
        ViewUtil.show(mLayoutProgressbar);

        String username = mEdtRegisterUsername.getText().toString();
        String password = mEdtRegisterPasswd.getText().toString();
        new Handler().postDelayed(() -> {
            List<User> users = UserDaoUtil.queryUserByUsername(username);
            if (users.size() != 0) {
                ToastUtil.showShort(this, "该手机号已被注册");
                ViewUtil.hide(mLayoutProgressbar);
            } else {
                user = new User();
                localToken = MD5Encoder.encode(username + password);
                user.setUsername(username);
                user.setPassword(password);
                user.setNickname(username);
                user.setSchool("泰山学院");
                user.setThirdPartyUser(false);
                user.setToken(localToken);
                UserDaoUtil.insertUser(user);
                loginSuccess(username);
            }
        }, 1200);
    }

    private void loginSuccess(String username) {
        LoginUtil.saveLoginInfo(getApplicationContext(), localToken);
        Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    IUiListener loginListener = new IUiListener() {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                return;
            }
            JSONObject resultJson = (JSONObject) response;
            if (resultJson.length() == 0) {
                return;
            }
            initOpenidAndToken(resultJson);
            updateUserInfo();
            ToastUtil.showShort(LoginRegisterActivity.this, "授权成功");
        }

        @Override
        public void onError(UiError e) {
            ViewUtil.hide(mLayoutProgressbar);
            ToastUtil.showShort(LoginRegisterActivity.this, "授权出错");
            LogUtil.d("XXXX onError: ", "errorMsg:" + e.errorMessage
                    + "errorDetail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            ViewUtil.hide(mLayoutProgressbar);
            ToastUtil.showShort(LoginRegisterActivity.this, "授权取消");
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    };

    private void loginByQQ() {
        ViewUtil.show(mLayoutProgressbar);
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                return;
            }
            mTencent.logout(this);
            mTencent.login(this, "all", loginListener);
        }
    }

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            accessToken = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(accessToken, expires);
                mTencent.setOpenId(openId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onComplete(final Object response) {
                    JSONObject userInfoJson = (JSONObject) response;
                    String nickname = null;
                    try {
                        nickname = userInfoJson.getString("nickname");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String avatarUrl = null;
                    if (userInfoJson.has("figureurl")) {
                        try {
                            avatarUrl = (userInfoJson).getString("figureurl_qq_2");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loginByQQCall(nickname, avatarUrl);
                }

                @Override
                public void onError(UiError e) {
                    ToastUtil.showShort(LoginRegisterActivity.this, "获取qq用户信息错误");
                }

                @Override
                public void onCancel() {
                    ToastUtil.showShort(LoginRegisterActivity.this, "取消获取qq用户信息");
                }
            };
            userInfo = new UserInfo(this, mTencent.getQQToken());
            userInfo.getUserInfo(listener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
