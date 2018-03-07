package cn.edu.tsu.campuscommonwealgo.fragment;

/**
 * Created by XWM on 2018/1/12.
 */

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置RecyclerView每个item四周的间隔距离的实现类
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int leftSpace;
    int rightSpace;
    int topSpace;
    int bottomSpace;
    int itemCount;

    public SpaceItemDecoration(int itemCount, int leftSpace, int rightSpace, int topSpace, int bottomSpace) {
        this.itemCount = itemCount;
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = leftSpace;
        outRect.right = rightSpace;
        outRect.bottom = bottomSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = topSpace;
        }
    }
}