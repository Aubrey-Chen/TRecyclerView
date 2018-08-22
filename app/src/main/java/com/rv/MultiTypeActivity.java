package com.rv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.rv.itemView.ItemType1;
import com.rv.itemView.banner;
import com.rv.pojo.BannerVo;
import com.rv.pojo.ItemVo;
import com.trecyclerview.TRecyclerView;
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
public class MultiTypeActivity extends AppCompatActivity {
    private TRecyclerView tRecyclerView;
    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        tRecyclerView = findViewById(R.id.recycler_view);
        items = new Items();
        adapter = new MultiTypeAdapter();
        adapter.register(HeaderVo.class, new HeaderViewHolder(MultiTypeActivity.this, ProgressStyle.Pacman));
        adapter.register(BannerVo.class, new banner(MultiTypeActivity.this));
        adapter.register(ItemVo.class, new ItemType1(MultiTypeActivity.this));
        adapter.register(FootVo.class, new FootViewHolder(MultiTypeActivity.this, ProgressStyle.Pacman));
        GridLayoutManager layoutManager = new GridLayoutManager(MultiTypeActivity.this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (items.get(position) instanceof BannerVo
                        || items.get(position) instanceof HeaderVo
                        || items.get(position) instanceof FootVo) ? 2 : 1;
            }
        });

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
                        items.clear();
                        items.add(new BannerVo());
                        for (int i = 0; i < 20; i++) {
                            items.add(new ItemVo());
                        }
                        tRecyclerView.refreshComplete();
                    }

                }, 2000);

            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 20; i++) {
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
        for (int i = 0; i < 20; i++) {
            items.add(new ItemVo());
        }
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }
}
