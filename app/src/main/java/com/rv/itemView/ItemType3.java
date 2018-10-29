package com.rv.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.rv.R;
import com.rv.pojo.Item1Vo;
import com.rv.pojo.Item3Vo;
import com.trecyclerview.holder.AbsHolder;
import com.trecyclerview.holder.AbsItemHolder;

/**
 * @author：tqzhang on 18/8/22 13:57
 */
public class ItemType3 extends AbsItemHolder<Item3Vo, ItemType3.ViewHolder> {
    public ItemType3(Context context) {
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
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Item3Vo item) {
        holder.tvType.setText(item.type);
    }

    static class ViewHolder extends AbsHolder {

        TextView tvType;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvType = getViewById(R.id.tv_type);
        }

    }

}
