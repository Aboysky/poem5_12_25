package com.example.poem5_12_25.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.poem5_12_25.R;
import com.example.poem5_12_25.adapter.PoemAdapter;
import com.example.poem5_12_25.database.Database1;
import com.example.poem5_12_25.entity.Poem;
import com.example.poem5_12_25.listener.RecyclerItemClickListener;
import com.example.poem5_12_25.service.FavorityPoemService;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private ArrayList<Poem> poem_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 状态栏沉浸
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_favorite);


        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlRefresh.setRefreshing(false);
            }
        });

        // 卡片视图设置
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        rvContent.setItemAnimator(new DefaultItemAnimator());        // 设置 RecyclerView 默认动画效果
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);                          // 设置视图逆序显示。
        layoutManager.setReverseLayout(true);
        rvContent.setLayoutManager(layoutManager);
        rvContent.addOnItemTouchListener(new RecyclerItemClickListener(rvContent, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < poem_list.size()) {
                    Poem poem = poem_list.get(position);
                    Intent intent = new Intent(FavoriteActivity.this,PoemActivity.class);
                    intent.putExtra("local", true);         // 是否本地页面的标志
                    intent.putExtra("position", position);
                    intent.putExtra("id", poem.getId());
                    intent.putExtra("title", poem.getName());
                    intent.putExtra("author", poem.getAuthor());
                    intent.putExtra("content", poem.getContent());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        srlRefresh.setOnRefreshListener(this::loadFavorite);

        loadFavorite();
    }

    /**
     * 从数据库里加载收藏列表
     */
    private void loadFavorite() {
        FavorityPoemService favorityPoemService = new FavorityPoemService(this);
        poem_list = new ArrayList<>();
        poem_list.addAll(favorityPoemService.selectUserAllFavorityPoem());
        rvContent.setAdapter(new PoemAdapter(poem_list, this, rvContent));
        Snackbar.make(rvContent, "刷新收藏夹完成！", Snackbar.LENGTH_SHORT).show();
        srlRefresh.setRefreshing(false);
    }
}
