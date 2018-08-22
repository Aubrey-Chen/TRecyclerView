package com.rv.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rv.R;
import com.rv.pojo.BannerVo;
import com.trecyclerview.holder.AbsViewHolder;
import com.trecyclerview.holder.BaseHolder;

/**
 * @author：tqzhang on 18/8/22 13:57
 */
public class banner extends AbsViewHolder<BannerVo, banner.ViewHolder> {
    public banner(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.banner;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BannerVo item) {

    }

    static class ViewHolder extends BaseHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

}
