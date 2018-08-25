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
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.listener.OnLoadMoreListener;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.multitype.Items;
import com.trecyclerview.multitype.MultiTypeAdapter;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;
import com.trecyclerview.view.FootViewHolder;
import com.trecyclerview.view.HeaderViewHolder;


/**
 * @author：tqzhang on 18/8/22 13:48
 */
public class SwipeMultiTypeActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRecyclerView tRecyclerView;
    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type2);
        tRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        items = new Items();
        adapter = new MultiTypeAdapter();
        adapter.bind(HeaderVo.class, new HeaderViewHolder(SwipeMultiTypeActivity.this, ProgressStyle.Pacman));
        adapter.bind(BannerVo.class, new banner(SwipeMultiTypeActivity.this));
        adapter.bind(ItemVo.class, new ItemType(SwipeMultiTypeActivity.this));
        adapter.bind(Item1Vo.class, new ItemType1(SwipeMultiTypeActivity.this));
        adapter.bind(Item2Vo.class, new ItemType2(SwipeMultiTypeActivity.this));
        adapter.bind(FootVo.class, new FootViewHolder(SwipeMultiTypeActivity.this, ProgressStyle.Pacman));
        GridLayoutManager layoutManager = new GridLayoutManager(SwipeMultiTypeActivity.this, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (items.get(position) instanceof BannerVo
                        || items.get(position) instanceof HeaderVo
                        || items.get(position) instanceof Item1Vo
                        || items.get(position) instanceof FootVo) {
                    return 4;
                } else if (items.get(position) instanceof ItemVo) {
                    return 2;
                } else if (items.get(position) instanceof Item2Vo) {
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
                        items.add(new Item1Vo("Python"));
                        for (int i = 0; i < 6; i++) {
                            items.add(new ItemVo());
                        }
                        items.add(new Item1Vo("Go"));
                        for (int i = 0; i < 12; i++) {
                            items.add(new ItemVo());
                        }
                        tRecyclerView.loadMoreComplete(20);
//                        tRecyclerView.setNoMore(20);
                    }

                }, 2000);
            }
        });
    }

    private void initData() {
        items.clear();
        items.add(new BannerVo());
        for (int i = 0; i < 8; i++) {
            items.add(new Item2Vo());
        }
        items.add(new Item1Vo("java"));
        for (int i = 0; i < 6; i++) {
            items.add(new ItemVo());
        }
        items.add(new Item1Vo("android"));
        for (int i = 0; i < 6; i++) {
            items.add(new ItemVo());
        }
        tRecyclerView.refreshComplete(items,false);
    }
}
