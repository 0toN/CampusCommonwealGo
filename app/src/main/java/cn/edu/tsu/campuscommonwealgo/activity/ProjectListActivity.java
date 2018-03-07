package cn.edu.tsu.campuscommonwealgo.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.util.TransparentStatusBarUtil;

public class ProjectListActivity extends AppCompatActivity {
    @BindView(R.id.tv_tab_name)
    TextView mTxtTabName;
    @BindView(R.id.iv_left)
    ImageView mImgLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TransparentStatusBarUtil.setTransparent(this);
        }
        setContentView(R.layout.activity_project_list);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {
        mTxtTabName.setText("公益项目");
        mImgLeft.setImageResource(R.mipmap.ic_back);
    }

    @OnClick(R.id.iv_left)
    public void click(View v) {
        finish();
    }
}
