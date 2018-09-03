package com.rv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
        adapter.bind(HeaderVo.class, new HeaderViewHolder(MultiTypeActivity.this, ProgressStyle.Pacman));
        adapter.bind(BannerVo.class, new banner(MultiTypeActivity.this));
        adapter.bind(ItemVo.class, new ItemType(MultiTypeActivity.this));
        adapter.bind(Item1Vo.class, new ItemType1(MultiTypeActivity.this));
        adapter.bind(Item2Vo.class, new ItemType2(MultiTypeActivity.this));
        adapter.bind(FootVo.class, new FootViewHolder(MultiTypeActivity.this, ProgressStyle.Pacman));
        GridLayoutManager layoutManager = new GridLayoutManager(MultiTypeActivity.this, 4);
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
    }

    private void setListener() {
        tRecyclerView.addOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }

                }, 5000);

            }

            @Override
            public void onLoadMore() {
                final Items item = new Items();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        item.add(new Item1Vo("Python"));
                        for (int i = 0; i < 6; i++) {
                            item.add(new ItemVo());
                        }
                        item.add(new Item1Vo("Go"));
                        for (int i = 0; i < 12; i++) {
                            item.add(new ItemVo());
                        }
                        items.addAll(item);
                        tRecyclerView.loadMoreComplete(item,false);
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
