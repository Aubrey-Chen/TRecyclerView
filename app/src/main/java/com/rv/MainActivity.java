package com.rv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.trecyclerview.TRecyclerView;
import com.trecyclerview.entity.HeaderInfo;
import com.trecyclerview.multitype.Items;
import com.trecyclerview.multitype.MultiTypeAdapter;
import com.trecyclerview.view.HeaderViewHolder;

public class MainActivity extends AppCompatActivity {

    TRecyclerView tRecyclerView;
    Items oldItems;

    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tRecyclerView = findViewById(R.id.t_Recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        oldItems = new Items();
        initData();
        adapter = new MultiTypeAdapter();
        adapter.register(HeaderInfo.class, new HeaderViewHolder(this));
        adapter.register(Data.class, new TestItem(this));
//        adapter.register(FootInfo.class, new FootItemViewBinder());
        tRecyclerView.setAdapter(adapter);
        tRecyclerView.setLayoutManager(layoutManager);
        adapter.setItems(oldItems);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        oldItems.add(new HeaderInfo());
        for (int i = 0; i < 20; i++) {
            oldItems.add(new Data(i));
        }

    }
}
