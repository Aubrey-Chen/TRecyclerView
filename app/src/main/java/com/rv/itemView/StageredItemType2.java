package com.rv.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rv.R;
import com.rv.pojo.ItemVo;
import com.trecyclerview.holder.AbsItemHolder;
import com.trecyclerview.holder.AbsHolder;

import java.util.Random;

/**
 * @author：tqzhang on 18/8/22 13:57
 */
public class StageredItemType2 extends AbsItemHolder<ItemVo, StageredItemType2.ViewHolder> {
    public StageredItemType2(Context context) {
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
        int h = new Random().nextInt(180) + 260;
        holder.rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h));
        holder.tv_type.setText("StageredItemType2" + item.type);
    }

    static class ViewHolder extends AbsHolder {

        RelativeLayout rootView;
        TextView tv_type;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            rootView = getViewById(R.id.rl_root_view);
            tv_type = getViewById(R.id.tv_type);

        }

    }

}
