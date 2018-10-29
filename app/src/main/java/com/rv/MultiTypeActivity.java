package com.rv;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.rv.itemView.ItemType;
import com.rv.itemView.ItemType1;
import com.rv.itemView.ItemType2;
import com.rv.itemView.ItemType3;
import com.rv.itemView.ItemType4;
import com.rv.itemView.banner;
import com.rv.pojo.BannerVo;
import com.rv.pojo.Item1Vo;
import com.rv.pojo.Item2Vo;
import com.rv.pojo.Item3Vo;
import com.rv.pojo.ItemVo;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.adapter.ItemData;
import com.trecyclerview.adapter.OneToMany;
import com.trecyclerview.adapter.VHolder;
import com.trecyclerview.listener.OnItemClickListener;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.adapter.DelegateAdapter;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;
import com.trecyclerview.footview.FootViewHolder;
import com.trecyclerview.headview.HeaderViewHolder;


/**
 * @author：tqzhang on 18/8/22 13:48
 */
public class MultiTypeActivity extends AppCompatActivity implements OnItemClickListener {
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
                .bind(HeaderVo.class, new HeaderViewHolder(MultiTypeActivity.this, ProgressStyle.Pacman))
                .bind(BannerVo.class, new banner(MultiTypeActivity.this))
                .bind(ItemVo.class, new ItemType(MultiTypeActivity.this))
                .bind(Item1Vo.class, new ItemType1(MultiTypeActivity.this))
                .bind(Item2Vo.class, new ItemType2(MultiTypeActivity.this))
                .bind(FootVo.class, new FootViewHolder(MultiTypeActivity.this, ProgressStyle.Pacman))
                .bindArray(Item3Vo.class, new ItemType3(MultiTypeActivity.this), new ItemType4(MultiTypeActivity.this))
                .withClass(new OneToMany<Item3Vo>() {
                    @Override
                    public Class<? extends VHolder<Item3Vo, ?>> onItemView(int position, Item3Vo item3Vo) {
                        if (item3Vo.type.equals("3")) {
                            return ItemType3.class;
                        }
                        if (item3Vo.type.equals("4")) {
                            return ItemType4.class;
                        }
                        return ItemType4.class;
                    }
                })
                .setOnItemClickListener(this)
                .build();
        GridLayoutManager layoutManager = new GridLayoutManager(MultiTypeActivity.this, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (itemData.get(position) instanceof BannerVo
                        || itemData.get(position) instanceof HeaderVo
                        || itemData.get(position) instanceof Item1Vo
                        || itemData.get(position) instanceof FootVo) {
                    return 4;
                } else if (itemData.get(position) instanceof ItemVo|| itemData.get(position) instanceof Item3Vo) {
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
                final ItemData item = new ItemData();
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
        for (int i = 0; i < 3; i++) {
            itemData.add(new Item3Vo("3"));
        }for (int i = 0; i < 3; i++) {
            itemData.add(new Item3Vo("4"));
        }
        tRecyclerView.refreshComplete(itemData, false);
    }

    @Override
    public void onItemClick(View view, int position, Object o) {
        Toast.makeText(this, ((Item1Vo) o).type, Toast.LENGTH_SHORT).show();
    }
}
