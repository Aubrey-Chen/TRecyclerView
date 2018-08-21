package com.trecyclerview.view;

import android.content.Context;

/**
 * @author：tqzhang on 18/8/21 10:59
 */
public class HeaderViewHolder extends AbsHeaderView {
    private int mProgressStyle;

    public HeaderViewHolder(Context context, int progressStyle) {
        super(context);
        mProgressStyle = progressStyle;
    }

    @Override
    protected ArrowRefreshHeader createRefreshHeader() {
        ArrowRefreshHeader mRefreshHeader = new ArrowRefreshHeader(mContext);
        mRefreshHeader.setProgressStyle(mProgressStyle);
        return mRefreshHeader;
    }
}
