package cn.edu.tsu.campuscommonwealgo.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.activity.DonationDetailsActivity;
import cn.edu.tsu.campuscommonwealgo.activity.ProjectListActivity;
import cn.edu.tsu.campuscommonwealgo.adapter.CommonwealProjectAdapter;
import cn.edu.tsu.campuscommonwealgo.model.CommonwealProject;
import cn.edu.tsu.campuscommonwealgo.util.ToastUtil;

/**
 * Created by XWM on 2018/1/12.
 */

public class LoveSupportFragment extends Fragment {
    @BindView(R.id.iv_support_type)
    ImageView mImgDonatinType;
    @BindView(R.id.more_layout)
    LinearLayout mLayoutMore;
    @BindView(R.id.project_recyclerview)
    RecyclerView mRecyclerView;
    private List<CommonwealProject> projects;

    private CommonwealProjectAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_love_support, container, false);
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
        adapter = new CommonwealProjectAdapter(getActivity(), projects);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        //设置每个Item的间隔距离
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(projects.size(), 0, 0, 0, 15));
    }

    private void setData() {
        projects = new ArrayList<>();
        CommonwealProject project1 = new CommonwealProject();
        project1.setTitle("公益校园Go多渠道筹资捐助山西省贫困大学生");
        project1.setContent("帮助贫困大学生是一件全社会的大事，公益校园Go多种渠道捐助贫困大学生");
        project1.setPicResid(R.mipmap.commonwel_project_01);

        CommonwealProject project2 = new CommonwealProject();
        project2.setTitle("公益校园Go在各大高校筹建校园基金会");
        project2.setContent("成立校园基金会作为每个高校的根据地，具有很强的现实意义");
        project2.setPicResid(R.mipmap.commonwel_project_02);

        CommonwealProject project3 = new CommonwealProject();
        project3.setTitle("公益校园Go多渠道筹资捐助山东省贫困大学生");
        project3.setContent("帮助贫困大学生是一件全社会的大事，公益校园Go多种渠道捐助贫困大学生。");
        project3.setPicResid(R.mipmap.commonwel_project_03);

        CommonwealProject project4 = new CommonwealProject();
        project4.setTitle("公益校园Go多渠道筹资捐助贵州省贫困大学生");
        project4.setContent("帮助贫困大学生是一件全社会的大事，公益校园Go多种渠道捐助贫困大学生");
        project4.setPicResid(R.mipmap.commonwel_project_04);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
    }

    @OnClick({R.id.iv_support_type, R.id.more_layout})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_support_type:
//                ToastUtil.showShort(getActivity(), "资助类型");
                startActivity(new Intent(getActivity(), DonationDetailsActivity.class));
                break;
            case R.id.more_layout:
//                ToastUtil.showShort(getActivity(), "更多公益项目");
                startActivity(new Intent(getActivity(), ProjectListActivity.class));
                break;
            default:
                break;
        }
    }
}
