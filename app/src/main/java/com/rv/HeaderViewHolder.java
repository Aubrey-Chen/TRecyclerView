package com.rv;

import android.content.Context;

import com.trecyclerview.ArrowRefreshHeader;
import com.trecyclerview.ProgressStyle;
import com.trecyclerview.view.HeaderView;

/**
 * @author：tqzhang on 18/8/20 17:18
 */
public class HeaderViewHolder extends HeaderView {

    public HeaderViewHolder(Context context) {
        super(context);
    }

    @Override
    protected ArrowRefreshHeader createRefreshHeader() {
        ArrowRefreshHeader mRefreshHeader = new ArrowRefreshHeader(mContext);
        mRefreshHeader.setProgressStyle(ProgressStyle.Pacman);
        return mRefreshHeader;
    }
}
