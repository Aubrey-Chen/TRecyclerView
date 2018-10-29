package com.rv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.rv.itemView.ItemType;
import com.rv.itemView.ItemType1;
import com.rv.itemView.ItemType2;
import com.rv.itemView.banner;
import com.rv.pojo.BannerVo;
import com.rv.pojo.Item1Vo;
import com.rv.pojo.Item2Vo;
import com.rv.pojo.ItemVo;
import com.trecyclerview.SwipeRecyclerView;
import com.trecyclerview.listener.OnLoadMoreListener;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;
import com.trecyclerview.footview.FootViewHolder;
import com.trecyclerview.headview.HeaderViewHolder;


/**
 * @author：tqzhang on 18/8/22 13:48
 */
public class SwipeMultiTypeActivity extends AppCompatActivity {
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
                .bind(HeaderVo.class, new HeaderViewHolder(SwipeMultiTypeActivity.this, ProgressStyle.Pacman))
                .bind(BannerVo.class, new banner(SwipeMultiTypeActivity.this))
                .bind(ItemVo.class, new ItemType(SwipeMultiTypeActivity.this))
                .bind(Item1Vo.class, new ItemType1(SwipeMultiTypeActivity.this))
                .bind(Item2Vo.class, new ItemType2(SwipeMultiTypeActivity.this))
                .bind(FootVo.class, new FootViewHolder(SwipeMultiTypeActivity.this, ProgressStyle.Pacman))
                .build();
        GridLayoutManager layoutManager = new GridLayoutManager(SwipeMultiTypeActivity.this, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (itemData.get(position) instanceof BannerVo
                        || itemData.get(position) instanceof HeaderVo
                        || itemData.get(position) instanceof Item1Vo
                        || itemData.get(position) instanceof FootVo) {
                    return 4;
                } else if (itemData.get(position) instanceof ItemVo) {
                    return 2;
                } else if (itemData.get(position) instanceof Item2Vo) {
                    return 1;
                }
                return 4;
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
                        initData();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final ItemData item = new ItemData();
                        item.add(new Item1Vo("Python"));
                        for (int i = 0; i < 6; i++) {
                            item.add(new ItemVo());
                        }
                        item.add(new Item1Vo("Go"));
                        for (int i = 0; i < 12; i++) {
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
        for (int i = 0; i < 8; i++) {
            itemData.add(new Item2Vo());
        }
        itemData.add(new Item1Vo("java"));
        for (int i = 0; i < 6; i++) {
            itemData.add(new ItemVo());
        }
        itemData.add(new Item1Vo("android"));
        for (int i = 0; i < 6; i++) {
            itemData.add(new ItemVo());
        }
        tRecyclerView.refreshComplete(itemData, false);
    }
}
