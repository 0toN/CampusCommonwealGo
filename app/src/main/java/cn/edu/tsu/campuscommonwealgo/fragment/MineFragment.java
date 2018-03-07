package cn.edu.tsu.campuscommonwealgo.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.activity.MainActivity;
import cn.edu.tsu.campuscommonwealgo.activity.SettingActivity;
import cn.edu.tsu.campuscommonwealgo.activity.UpdatePersonalInfoActivity;
import cn.edu.tsu.campuscommonwealgo.dao.util.UserDaoUtil;
import cn.edu.tsu.campuscommonwealgo.entity.User;
import cn.edu.tsu.campuscommonwealgo.util.ToastUtil;

/**
 * Created by XWM on 2018/1/9.
 */

public class MineFragment extends Fragment {
    @BindView(R.id.tv_tab_name)
    TextView mTxtTabName;
    @BindView(R.id.iv_left)
    ImageView mImgLeft;
    @BindView(R.id.iv_right)
    ImageView mImgRight;
    @BindView(R.id.iv_avatar)
    ImageView mImgAvatar;
    @BindView(R.id.tv_nickname)
    TextView mTxtNickname;
    @BindView(R.id.tv_school)
    TextView mTxtSchool;
    @BindView(R.id.layout_my_info)
    ConstraintLayout mLayoutInfo;
    @BindView(R.id.layout_my_donation)
    ConstraintLayout mLayoutMyDonation;
    @BindView(R.id.layout_my_support)
    ConstraintLayout mLayoutMySupport;
    private User user;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    private void initViews() {
        Intent intent = ((MainActivity) getActivity()).getIntent();
        username = intent.getStringExtra("username");
        updateData();

        mImgLeft.setVisibility(View.GONE);
        mImgRight.setImageResource(R.mipmap.ic_setting);
        mTxtTabName.setText("我");
    }

    @OnClick({R.id.iv_right, R.id.layout_my_info, R.id.layout_my_support, R.id.layout_my_donation})
    public void click(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_right:
                intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
                break;
            case R.id.layout_my_info:
                intent = new Intent(getActivity(), UpdatePersonalInfoActivity.class);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
                break;
            case R.id.layout_my_donation:
                ToastUtil.showShort(getActivity(), "捐助详情");
                break;
            case R.id.layout_my_support:
                ToastUtil.showShort(getActivity(), "资助详情");
                break;
            default:
                break;
        }
    }

    private void updateData() {
        List<User> users = UserDaoUtil.queryUserByUsername(username);
        if (users.size() == 1) {
            user = users.get(0);
            mTxtNickname.setText(user.getNickname());
            mTxtSchool.setText(user.getSchool());
            String avatarUrl = user.getAvatarUrl();
            if (avatarUrl != null && !avatarUrl.trim().equals("")) {
                Glide.with(this).load(avatarUrl).into(mImgAvatar);
            }
        }
    }
}
