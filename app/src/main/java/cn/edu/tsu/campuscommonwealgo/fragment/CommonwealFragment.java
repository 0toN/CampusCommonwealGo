package cn.edu.tsu.campuscommonwealgo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.adapter.CommonwealFragmentPagerAdapter;
import cn.edu.tsu.campuscommonwealgo.util.ToastUtil;

/**
 * Created by XWM on 2018/1/9.
 */

public class CommonwealFragment extends Fragment {
    @BindView(R.id.iv_right)
    ImageView mImgRight;
    @BindView(R.id.tv_tab_name)
    TextView mTxtTabName;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private CommonwealFragmentPagerAdapter adapter;
    private List<String> titles;
    private List<Fragment> fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commonweal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
        render();
    }

    private void render() {
        initFragment();
        initViews();
    }

    private void initViews() {
        mTxtTabName.setText("公益");
        mImgRight.setImageResource(R.mipmap.ic_search);

        adapter = new CommonwealFragmentPagerAdapter(getActivity().getFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setIndicatorWidth(120);
    }

    public void initFragment() {
        fragments = new ArrayList<>();

        LoveDonationFragment donationFragment = new LoveDonationFragment();
        LoveSupportFragment supportFragment = new LoveSupportFragment();
        fragments.add(donationFragment);
        fragments.add(supportFragment);
    }

    private void setData() {
        titles = new ArrayList<>();
        titles.add("爱捐助");
        titles.add("爱资助");
    }

    /**
     * 设置TabLayout指示器的宽度
     */
    private void setIndicatorWidth(int marginDip) {
        try {
            Field mTabStripField = mTabLayout.getClass().getDeclaredField("mTabStrip");
            mTabStripField.setAccessible(true);

            LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(mTabLayout);

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);

                tabView.setPadding(0, 0, 0, 0);

                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = marginDip;
                params.rightMargin = marginDip;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_right})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_right:
                ToastUtil.showShort(getActivity(), "搜索");
                break;
        }
    }
}
