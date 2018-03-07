package cn.edu.tsu.campuscommonwealgo.adapter;

import android.app.Fragment;
import android.app.FragmentManager;

import java.util.List;

import cn.edu.tsu.campuscommonwealgo.adapter.util.FragmentPagerAdapter;

/**
 * Created by XWM on 2018/1/11.
 */

public class CommonwealFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public CommonwealFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
