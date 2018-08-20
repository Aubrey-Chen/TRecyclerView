package com.rv;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.trecyclerview.holder.AbsViewHolder;
import com.trecyclerview.holder.BaseHolder;

/**
 * @author：tqzhang on 18/8/20 17:36
 */
public class TestItem extends AbsViewHolder<Data, TestItem.ViewHolder> {
    public TestItem(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.test_layout;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Data item) {
        holder.tv.setText(item.i + "");
    }


    static class ViewHolder extends BaseHolder {

        TextView tv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = getViewById(R.id.text_view);
        }
    }
}
