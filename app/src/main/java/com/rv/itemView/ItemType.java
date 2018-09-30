package com.rv.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.rv.R;
import com.rv.pojo.ItemVo;
import com.trecyclerview.holder.AbsViewHolder;
import com.trecyclerview.holder.BaseHolder;
import com.trecyclerview.listener.OnItemClickListener;

/**
 * @author：tqzhang on 18/8/22 13:57
 */
public class ItemType extends AbsViewHolder<ItemVo, ItemType.ViewHolder> {
    public ItemType(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.type;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view,mOnItemClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ItemVo item) {

    }

    static class ViewHolder extends BaseHolder {

        ViewHolder(@NonNull final View itemView, final OnItemClickListener mOnItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=mOnItemClickListener){
                        mOnItemClickListener.onItemClick(v,getAdapterPosition(),itemView.getTag());
                    }

                }
            });
        }

    }

}
