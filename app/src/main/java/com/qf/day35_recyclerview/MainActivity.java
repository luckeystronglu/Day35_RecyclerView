package com.qf.day35_recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private List<String> datas;
    private MyAdapter myAdapter;

    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datas = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            if(i % 3 == 0){
                datas.add("内容：" + i + "内容：" + i + "内容：" + i + "内容：" + i);
            } else {
                datas.add("内容：" + i);
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        //        recyclerView.setItemAnimator(new DefaultItemAnimator());//设置动画
//        recyclerView.addItemDecoration(new MyItemDecotration(this));//设置分割线

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, true));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
        myAdapter = new MyAdapter(this);
        myAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(myAdapter);

        myAdapter.setDatas(datas);

        /**
         * 类似ViewPager
         */
//        new LinearSnapHelper().attachToRecyclerView(recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.RED);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("print", "------>开始刷新，加载数据");

                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //数据加载完成
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //关闭刷新
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }.start();
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        //添加
//        datas.add(position, "这是一个新的数据");
//        datas.add(position, "这是一个新的数据2");
//        datas.add(position, "这是一个新的数据3");
//        datas.add(position, "这是一个新的数据4");
//        datas.add(position, "这是一个新的数据5");
////        myAdapter.notifyDataSetChanged();
////        myAdapter.notifyItemInserted(position);
//        myAdapter.notifyItemRangeInserted(position, 5);

        //改变位置
        String str = datas.remove(position);
        datas.add(0, str);
        myAdapter.notifyItemMoved(position, 0);
        recyclerView.scrollToPosition(0);

    }

    @Override
    public void onLongClick(View view, int postion) {
//        Log.d("print", "---------->长按了：" + postion);
        datas.remove(postion);
        myAdapter.notifyItemRemoved(postion);
    }
}
