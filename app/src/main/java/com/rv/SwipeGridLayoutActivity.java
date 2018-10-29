package com.rv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.rv.itemView.ItemType;
import com.rv.itemView.banner;
import com.rv.pojo.BannerVo;
import com.rv.pojo.ItemVo;
import com.trecyclerview.SwipeRecyclerView;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnLoadMoreListener;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;
import com.trecyclerview.footview.FootViewHolder;


/**
 * @author：tqzhang on 18/8/22 13:48
 */
public class SwipeGridLayoutActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRecyclerView tRecyclerView;
    private ItemData itemData;
    private DelegateAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type2);
        tRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        itemData = new ItemData();
        adapter = new DelegateAdapter.Builder()
                .bind(BannerVo.class, new banner(SwipeGridLayoutActivity.this))
                .bind(ItemVo.class, new ItemType(SwipeGridLayoutActivity.this))
                .bind(FootVo.class, new FootViewHolder(SwipeGridLayoutActivity.this, ProgressStyle.SysProgress,"努力加载","没有更多啦。。"))
                .build();
        GridLayoutManager layoutManager = new GridLayoutManager(SwipeGridLayoutActivity.this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (itemData.get(position) instanceof BannerVo
                        || itemData.get(position) instanceof HeaderVo
                        || itemData.get(position) instanceof FootVo) ? 2 : 1;
            }
        });

        tRecyclerView.setAdapter(adapter);
        tRecyclerView.setLayoutManager(layoutManager);
        setListener();
        initData();

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, 60);
            mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemData.clear();
                        itemData.add(new BannerVo());
                        for (int i = 0; i < 10; i++) {
                            itemData.add(new ItemVo());
                        }
                        tRecyclerView.refreshComplete(itemData, false);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                }, 5000);
            }
        });

    }

    private void setListener() {
        tRecyclerView.addOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final ItemData item = new ItemData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            item.add(new ItemVo());
                        }
                        itemData.addAll(item);
                        tRecyclerView.loadMoreComplete(item, false);
//                        tRecyclerView.setNoMore(20);
                    }

                }, 2000);
            }
        });
    }

    private void initData() {
        itemData.clear();
        itemData.add(new BannerVo());
        for (int i = 0; i < 10; i++) {
            itemData.add(new ItemVo());
        }
        tRecyclerView.refreshComplete(itemData, true);
    }
}
