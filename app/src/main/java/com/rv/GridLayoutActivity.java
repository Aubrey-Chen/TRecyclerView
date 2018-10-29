package com.rv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.rv.itemView.ItemType;
import com.rv.itemView.banner;
import com.rv.pojo.BannerVo;
import com.rv.pojo.ItemVo;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnItemClickListener;
import com.trecyclerview.listener.OnNetWorkListener;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;
import com.trecyclerview.footview.FootViewHolder;
import com.trecyclerview.headview.HeaderViewHolder;

import static com.trecyclerview.footview.LoadingMoreFooter.STATE_LOADING;
import static com.trecyclerview.footview.LoadingMoreFooter.STATE_NO_NET_WORK;


/**
 * @author：tqzhang on 18/8/22 13:48
 */
public class GridLayoutActivity extends AppCompatActivity implements OnItemClickListener {
    private TRecyclerView tRecyclerView;
    private ItemData itemData;
    private DelegateAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        tRecyclerView = findViewById(R.id.recycler_view);
        itemData = new ItemData();
        adapter = new DelegateAdapter.Builder()
                .bind(HeaderVo.class, new HeaderViewHolder(GridLayoutActivity.this, ProgressStyle.Pacman))
                .bind(BannerVo.class, new banner(GridLayoutActivity.this))
                .bind(ItemVo.class, new ItemType(GridLayoutActivity.this))
                .bind(FootVo.class, new FootViewHolder(GridLayoutActivity.this, ProgressStyle.SysProgress))
                .setOnItemClickListener(this)
                .build();
        GridLayoutManager layoutManager = new GridLayoutManager(GridLayoutActivity.this, 2);
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
    }

    private void setListener() {
        tRecyclerView.setOnNetWorkListener(new OnNetWorkListener() {
            @Override
            public boolean onNetWork() {

                return NetWorkUtil.isNetworkAvailable(GridLayoutActivity.this);
            }
        });
        tRecyclerView.addOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemData.clear();
                        itemData.add(new BannerVo());
                        for (int i = 0; i < 10; i++) {
                            itemData.add(new ItemVo());
                        }
                        tRecyclerView.refreshComplete(itemData, false);
                    }

                }, 5000);

            }

            @Override
            public void onLoadMore() {
                final ItemData l = new ItemData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            l.add(new ItemVo());
                        }
                        itemData.addAll(l);
                        tRecyclerView.loadMoreComplete(l, false);
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
        tRecyclerView.refreshComplete(itemData, false);
    }

    @Override
    public void onItemClick(View view, int position, Object o) {
        if (o instanceof FootVo) {
            tRecyclerView.refreshFootView(STATE_LOADING);
            final ItemData l = new ItemData();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    for (int i = 0; i < 10; i++) {
//                        l.add(new ItemVo());
//                    }
//                    itemData.addAll(l);
//                    tRecyclerView.loadMoreComplete(l, false);
                    tRecyclerView.refreshFootView(STATE_NO_NET_WORK);
                }

            }, 2000);
        }

    }
}
