package com.rv.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rv.R;
import com.rv.pojo.ItemVo;
import com.trecyclerview.holder.AbsViewHolder;
import com.trecyclerview.holder.BaseHolder;

import java.util.Random;

/**
 * @author：tqzhang on 18/8/22 13:57
 */
public class StageredItemType extends AbsViewHolder<ItemVo, StageredItemType.ViewHolder> {
    public StageredItemType(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.type_1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ItemVo item) {
        int h = new Random().nextInt(180)+260;
        holder.rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h));
    }

    static class ViewHolder extends BaseHolder {

        RelativeLayout rootView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = getViewById(R.id.rl_root_view);
        }

    }

}
