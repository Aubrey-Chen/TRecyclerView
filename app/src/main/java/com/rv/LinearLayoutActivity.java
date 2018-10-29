package com.rv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.rv.headerview.RefreshHeader;
import com.rv.itemView.ItemType;
import com.rv.itemView.banner;
import com.rv.pojo.BannerVo;
import com.rv.pojo.ItemVo;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.headview.HeaderViewHolder;


/**
 * @author：tqzhang on 18/8/22 13:48
 */
public class LinearLayoutActivity extends AppCompatActivity {
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
//                .save(HeaderVo.class, new HeaderViewHolder(LinearLayoutActivity.this, LayoutInflater.from(this).inflate(R.layout.custom_header_view,null)))
                .bind(HeaderVo.class, new HeaderViewHolder(LinearLayoutActivity.this, new RefreshHeader(this), new RefreshHeader(this).getOnTouchMoveListener()))
                .bind(BannerVo.class, new banner(LinearLayoutActivity.this))
                .bind(ItemVo.class, new ItemType(LinearLayoutActivity.this))
//                .save(FootVo.class, new FootViewHolder(LinearLayoutActivity.this, ProgressStyle.Pacman))
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(LinearLayoutActivity.this);
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

                        //数据返回是空的  没有更多  tRecyclerView.loadMoreComplete(null, false);
                        //
//                        tRecyclerView.setNoMore(l);
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
//        tRecyclerView.setNoMore(itemData);
    }
}
