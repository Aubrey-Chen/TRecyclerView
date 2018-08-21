package com.trecyclerview.view;

import android.content.Context;

/**
 * @author：tqzhang on 18/8/21 10:59
 */
public class HeaderViewHolder extends AbsHeaderView {

    public HeaderViewHolder(Context context, int progressStyle) {
        super(context,progressStyle);
    }

    @Override
    protected ArrowRefreshHeader createRefreshHeader() {
        ArrowRefreshHeader mRefreshHeader = new ArrowRefreshHeader(mContext);
        mRefreshHeader.setProgressStyle(mProgressStyle);
        return mRefreshHeader;
    }
}
