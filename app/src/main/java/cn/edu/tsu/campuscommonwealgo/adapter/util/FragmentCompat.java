package cn.edu.tsu.campuscommonwealgo.adapter.util;

import android.app.Fragment;

/**
 * ClassName: : ${type_name}
 * Description: ${todo}(这里用一句话描述这个类的作用)
 * Copyright  : Copyright (c) 2016
 * Author     : 小小码农
 * Date       : 2016/12/20 11:40
 */

public class FragmentCompat {
    interface FragmentCompatImpl {
        void setMenuVisibility(Fragment f, boolean visible);

        void setUserVisibleHint(Fragment f, boolean deferStart);
    }

    static class BaseFragmentCompatImpl implements FragmentCompatImpl {

        @Override
        public void setMenuVisibility(Fragment f, boolean visible) {

        }

        @Override
        public void setUserVisibleHint(Fragment f, boolean deferStart) {

        }
    }

    static class ICSFragmentCompatImpl extends BaseFragmentCompatImpl {
        @Override
        public void setMenuVisibility(Fragment f, boolean visible) {
            FragmentCompatICS.setMenuVisibility(f, visible);
        }
    }

    static class ICSMR1FragmentCompatImpl extends ICSFragmentCompatImpl {
        @Override
        public void setUserVisibleHint(Fragment f, boolean deferStart) {
            FragmentCompatICSMR1.setUserVisibleHint(f, deferStart);
        }
    }

    static final FragmentCompatImpl IMPL;

    static {
        if (android.os.Build.VERSION.SDK_INT >= 15) {
            IMPL = new ICSMR1FragmentCompatImpl();
        } else if (android.os.Build.VERSION.SDK_INT >= 14) {
            IMPL = new ICSFragmentCompatImpl();
        } else {
            IMPL = new BaseFragmentCompatImpl();
        }
    }

    /**
     * Call {@link Fragment#setMenuVisibility(boolean) Fragment.setMenuVisibility(boolean)}
     * if running on an appropriate version of the platform.
     */
    public static void setMenuVisibility(Fragment f, boolean visible) {
        IMPL.setMenuVisibility(f, visible);
    }

    /**
     * Call {@link Fragment#setUserVisibleHint(boolean) setUserVisibleHint(boolean)}
     * if running on an appropriate version of the platform.
     */
    public static void setUserVisibleHint(Fragment f, boolean deferStart) {
        IMPL.setUserVisibleHint(f, deferStart);
    }
}