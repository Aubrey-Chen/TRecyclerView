package com.rv.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.rv.R;
import com.rv.pojo.BannerVo;
import com.rv.pojo.Item1Vo;
import com.rv.pojo.ItemVo;
import com.trecyclerview.holder.AbsViewHolder;
import com.trecyclerview.holder.BaseHolder;

/**
 * @author：tqzhang on 18/8/22 13:57
 */
public class ItemType1 extends AbsViewHolder<Item1Vo, ItemType1.ViewHolder> {
    public ItemType1(Context context) {
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
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Item1Vo item) {
        holder.tvType.setText(item.type);
    }

    static class ViewHolder extends BaseHolder {

        TextView tvType;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = getViewById(R.id.tv_type);
        }

    }

}
