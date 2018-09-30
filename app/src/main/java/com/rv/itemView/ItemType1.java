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
import com.trecyclerview.listener.OnItemClickListener;

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
        return new ViewHolder(view,mOnItemClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Item1Vo item) {
        holder.tvType.setText(item.type);
    }

    static class ViewHolder extends BaseHolder {

        TextView tvType;

        ViewHolder(@NonNull final View itemView, final OnItemClickListener mOnItemClickListener) {
            super(itemView);
            tvType = getViewById(R.id.tv_type);
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
