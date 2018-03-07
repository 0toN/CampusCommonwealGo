package cn.edu.tsu.campuscommonwealgo.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.dao.util.UserDaoUtil;
import cn.edu.tsu.campuscommonwealgo.entity.User;
import cn.edu.tsu.campuscommonwealgo.fragment.CommonwealFragment;
import cn.edu.tsu.campuscommonwealgo.fragment.HomeFragment;
import cn.edu.tsu.campuscommonwealgo.fragment.MineFragment;
import cn.edu.tsu.campuscommonwealgo.util.LogUtil;
import cn.edu.tsu.campuscommonwealgo.util.ToastUtil;
import cn.edu.tsu.campuscommonwealgo.util.TransparentStatusBarUtil;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.content)
    FrameLayout mFrameLayout;
    @BindView(R.id.rbHome)
    RadioButton mRbHome;
    @BindView(R.id.rbCommonweal)
    RadioButton mRbCommonWeal;
    @BindView(R.id.rbMine)
    RadioButton mRbMine;

    private FragmentManager mFragmentManager;
    private Fragment[] mFragments;
    private int mIndex;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TransparentStatusBarUtil.setTransparent(this);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        mIndex = 0;

        HomeFragment homeFragment = new HomeFragment();
        CommonwealFragment commonWealFragment = new CommonwealFragment();
        MineFragment mineFragment = new MineFragment();

        //添加到数组
        mFragments = new Fragment[]{homeFragment, commonWealFragment, mineFragment};

        mFragmentManager = getFragmentManager();
        //开启事务
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        //默认显示首页
        Fragment fragment = mFragments[mIndex];
        ft.add(R.id.content, fragment)
                .show(fragment)
                .commit();
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        //隐藏当前fragment
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        Fragment fragment = mFragments[index];
        if (!fragment.isAdded()) {
            ft.add(R.id.content, fragment).show(fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();
        mIndex = index;
    }

    @OnClick({R.id.rbHome, R.id.rbCommonweal, R.id.rbMine})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbHome:
                setIndexSelected(0);
                break;
            case R.id.rbCommonweal:
                setIndexSelected(1);
                break;
            case R.id.rbMine:
                setIndexSelected(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.showShort(this, "再按一次退出应用");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
