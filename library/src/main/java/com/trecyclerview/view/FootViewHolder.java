package com.trecyclerview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.trecyclerview.R;
import com.trecyclerview.pojo.FootVo;

import static com.trecyclerview.view.LoadingMoreFooter.STATE_LOADING;
import static com.trecyclerview.view.LoadingMoreFooter.STATE_NOMORE;

/**
 * @author：tqzhang on 18/6/20 13:41
 */
public class FootViewHolder extends AbsFootView<FootVo, FootViewHolder.ViewHolder> {

    private int mProgressStyle;

    public FootViewHolder(Context context, int progressStyle) {
        super(context);
        mProgressStyle = progressStyle;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.listview_foot, parent, false));

    }

    @Override
    protected void onBindHolder(@NonNull ViewHolder holder, @NonNull FootVo mFootData) {

        RecyclerView.LayoutParams clp = (RecyclerView.LayoutParams) holder.mRootView.getLayoutParams();
        if (clp instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) clp).setFullSpan(true);
        }
        holder.loadingProgressBar.setProgressStyle(mProgressStyle);
        if (mFootData.state == STATE_NOMORE) {
            holder.loadingProgressBar.setState(STATE_NOMORE);
        } else if (mFootData.state == STATE_LOADING) {
            holder.loadingProgressBar.setState(STATE_LOADING);
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mRootView;
        LoadingMoreFooter loadingProgressBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRootView = itemView.findViewById(R.id.foot_view);
            loadingProgressBar = itemView.findViewById(R.id.loading_progress_bar);
        }
    }
}
