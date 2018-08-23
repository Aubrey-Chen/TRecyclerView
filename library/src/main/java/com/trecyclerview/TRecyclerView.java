package com.trecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.trecyclerview.listener.OnScrollStateListener;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.listener.OnTScrollListener;
import com.trecyclerview.multitype.MultiTypeAdapter;
import com.trecyclerview.multitype.TypePool;
import com.trecyclerview.view.AbsFootView;
import com.trecyclerview.view.AbsHeaderView;
import com.trecyclerview.view.ArrowRefreshHeader;

import java.util.Collection;
import java.util.List;

/**
 * @author：tqzhang on 18/6/22 16:03
 */
public class TRecyclerView extends RecyclerView {
    private MultiTypeAdapter mMultiTypeAdapter;

    private boolean loadingMoreEnabled = false;

    private boolean pullRefreshEnabled = false;


    protected boolean isLoadMore = true;

    protected boolean isLoading = true;

    //是否正在下拉刷新
    private boolean mRefreshing = false;

    /**
     * true 没有更多
     */
    private boolean isNoMore = false;


    private float mLastY = -1;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;


    private OnRefreshListener mOnRefreshListener;

    private OnTScrollListener mOnScrollListener;

    private OnScrollStateListener mOnScrollStateListener;

    private static final float DRAG_RATE = 2.0f;

    private ArrowRefreshHeader mRefreshHeader = null;

    public TRecyclerView(Context context) {
        this(context, null);
    }

    public TRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void refreshComplete(List<Object> list, boolean noMore) {
        if (mRefreshHeader != null) {
            mRefreshHeader.refreshComplete();
        }
        mRefreshing = false;
        mMultiTypeAdapter.setItems(list);
        mMultiTypeAdapter.notifyDataSetChanged();
        isNoMore = noMore;
    }

    public void loadMoreComplete(int size) {
        if (mRefreshing) {
            mRefreshing = false;
        }
        isLoading = true;
        isLoadMore = false;
        mMultiTypeAdapter.getItems().remove(mMultiTypeAdapter.getItems().size() - 1 - size);
        mMultiTypeAdapter.notifyMoreDataChanged(mMultiTypeAdapter.getItems().size() - size - 1, mMultiTypeAdapter.getItems().size());
    }

    public void setNoMore(int size) {
        isNoMore = true;
        loadMoreComplete(size);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.mMultiTypeAdapter = (MultiTypeAdapter) adapter;
        super.setAdapter(adapter);
        TypePool mTypePool = mMultiTypeAdapter.getTypePool();
        for (int i = 0; i < mTypePool.size(); i++) {
            if (mTypePool.getItemViewBinder(i) instanceof AbsFootView) {
                setLoadingMoreEnabled(true);
            } else if (mTypePool.getItemViewBinder(i) instanceof AbsHeaderView) {
                AbsHeaderView mHeaderItemView = (AbsHeaderView) mTypePool.getItemViewBinder(i);
                mRefreshHeader = mHeaderItemView.getRefreshHeaderView();
                pullRefreshEnabled = true;
            }
        }
    }

    public void setLoadingMoreEnabled(boolean enabled) {
        loadingMoreEnabled = enabled;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = (ev.getRawY() - mLastY) / DRAG_RATE;
                mLastY = ev.getRawY();
                if (isOnTop() && pullRefreshEnabled && !mRefreshing) {
                    mRefreshHeader.onMove(deltaY);
                    if (mRefreshHeader.getVisibleHeight() > 0) {
                        return false;
                    }
                }
                break;
            default:
                // reset
                mLastY = -1;
                if (isOnTop() && pullRefreshEnabled && !mRefreshing) {
                    if (mRefreshHeader.releaseAction()) {
                        if (mOnRefreshListener != null) {
                            mRefreshing = true;
                            mOnRefreshListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isOnTop() {
        if (mRefreshHeader != null && mRefreshHeader.getParent() != null) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrolled(dx, dy);
        }
        int mAdapterCount = mMultiTypeAdapter.getItemCount();
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }
        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() + 1;
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into) + 1;
                break;
            default:
                break;
        }

        boolean isBottom = mAdapterCount == lastVisibleItemPosition;
        if (mOnRefreshListener != null && loadingMoreEnabled && !mRefreshing && isBottom) {
            mRefreshing = false;
            isLoadMore = true;
            if (isLoading) {
                isLoading = false;
                mMultiTypeAdapter.notifyFootViewChanged(isNoMore);
                if (!isNoMore) {
                    mOnRefreshListener.onLoadMore();
                }
            }
        }

    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (mOnScrollStateListener != null) {
            mOnScrollStateListener.onScrollStateChanged(state);
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(state);
        }
    }

//    public void defaultRefresh() {
//        if (mRefreshing) {
//            return;
//        }
//        if (mRefreshHeader.getVisibleHeight() > 0 || mRefreshing) {// if RefreshHeader is Refreshing, return
//            return;
//        }
//        if (pullRefreshEnabled && mOnRefreshListener != null) {
//            mRefreshHeader.setState(2);
//            int offSet = mRefreshHeader.getMeasuredHeight();
//            mRefreshHeader.onMove(offSet);
//            mRefreshing = true;
//            mOnRefreshListener.onRefresh();
//        }
//    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    public enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }

    public void addOnScrollStateListener(OnScrollStateListener listener) {
        mOnScrollStateListener = listener;
    }

    public void addOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    public void addOnTScrollListener(OnTScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

}
