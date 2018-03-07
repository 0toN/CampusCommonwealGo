package cn.edu.tsu.campuscommonwealgo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.tsu.campuscommonwealgo.R;
import cn.edu.tsu.campuscommonwealgo.activity.ProjectDetailsActivity;
import cn.edu.tsu.campuscommonwealgo.model.CommonwealProject;

/**
 * Created by XWM on 2018/1/11.
 */

public class CommonwealProjectAdapter extends RecyclerView.Adapter<CommonwealProjectAdapter.ViewHolder> {

    private Context mContext;
    private List<CommonwealProject> projects;

    public CommonwealProjectAdapter(Context context, List<CommonwealProject> projects) {
        this.mContext = context;
        this.projects = projects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.project_item_layout, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_project)
        ConstraintLayout mLayoutProject;
        @BindView(R.id.iv_project_picture)
        ImageView mImgPic;
        @BindView(R.id.tv_project_title)
        TextView mTxtTitle;
        @BindView(R.id.tv_project_content)
        TextView mTxtContent;

        private CommonwealProject project;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(int position) {
            project = projects.get(position);
            mImgPic.setImageResource(project.getPicResid());
            mTxtTitle.setText(project.getTitle());
            mTxtContent.setText(project.getContent());
        }

        @OnClick(R.id.layout_project)
        public void click(){
            //ToastUtil.showShort(mContext, "项目详情");
            mContext.startActivity(new Intent(mContext,ProjectDetailsActivity.class));
        }
    }
}
