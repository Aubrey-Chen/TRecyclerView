package com.rv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.rv.itemView.StageredItemType;
import com.rv.itemView.StageredItemType2;
import com.rv.itemView.banner;
import com.rv.pojo.BannerVo;
import com.rv.pojo.ItemVo;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.adapter.VHolder;
import com.trecyclerview.adapter.OneToMany;
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
public class StaggeredGridLayoutActivity extends AppCompatActivity {
    private TRecyclerView tRecyclerView;
    private ItemData itemData;
    private DelegateAdapter adapter;
    private int indexPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        tRecyclerView = findViewById(R.id.recycler_view);
        itemData = new ItemData();
        adapter = new DelegateAdapter.Builder()
                .bind(HeaderVo.class, new HeaderViewHolder(StaggeredGridLayoutActivity.this, ProgressStyle.Pacman))
                .bind(BannerVo.class, new banner(StaggeredGridLayoutActivity.this))
                .bindArray(ItemVo.class, new StageredItemType(StaggeredGridLayoutActivity.this), new StageredItemType2(StaggeredGridLayoutActivity.this))
                .withClass(new OneToMany<ItemVo>() {
                    @NonNull
                    @Override
                    public Class<? extends VHolder<ItemVo, ?>> onItemView(int var1, @NonNull ItemVo var2) {
                        if (Integer.parseInt(var2.type) == 1) {
                            return StageredItemType.class;
                        } else {
                            return StageredItemType2.class;
                        }
                    }
                })
                .bind(FootVo.class, new FootViewHolder(StaggeredGridLayoutActivity.this, ProgressStyle.Pacman))
                .build();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        tRecyclerView.setAdapter(adapter);
        tRecyclerView.setLayoutManager(layoutManager);
        setListener();
        initData();
    }

    private void setListener() {
        tRecyclerView.addOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        itemData.clear();
                        itemData.add(new BannerVo());
                        for (int i = 0; i < 20; i++) {
                            if (i % 2 == 0) {
                                itemData.add(new ItemVo("" + 1));
                            } else {
                                itemData.add(new ItemVo("" + 2));
                            }

                        }
                        tRecyclerView.refreshComplete(itemData, false);

                    }

                }, 5000);

            }

            @Override
            public void onLoadMore() {
                final ItemData item = new ItemData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        indexPage += 1;
                        for (int i = 0; i < 20; i++) {
                            item.add(new ItemVo(i + ""));
                        }
                        itemData.addAll(item);
                        //模拟加载多页没有更多
                        if (indexPage == 4) {
                            tRecyclerView.loadMoreComplete(item, true);
                        } else {
                            tRecyclerView.loadMoreComplete(item, false);
                        }

                    }

                }, 2000);
            }
        });
    }

    private void initData() {
        itemData.clear();
        itemData.add(new BannerVo());
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                itemData.add(new ItemVo("" + 1));
            } else {
                itemData.add(new ItemVo("" + 2));
            }
        }
        tRecyclerView.refreshComplete(itemData, true);
    }
}
