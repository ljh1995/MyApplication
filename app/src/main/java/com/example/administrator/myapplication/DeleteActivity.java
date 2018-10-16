package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.view.SwipeBean;
import com.mcxtzhang.commonadapter.rv.*;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianjh on 2018\8\17 0017.
 * Current page
 */

public class DeleteActivity extends AppCompatActivity{
    private static final String TAG = "zxt";
    private RecyclerView mLv;
    private List<SwipeBean> mDatas;
    private CommonAdapter<SwipeBean> mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        mLv = (RecyclerView) findViewById(R.id.rv);

        initDatas();
        mAdapter = new CommonAdapter<SwipeBean>(this, mDatas, R.layout./*item_swipe_menu*/item_cst_swipe) {
            @Override
            public void convert(final ViewHolder holder, SwipeBean swipeBean) {
//((SwipeMenuLayout)holder.getConvertView()).setIos(false);//这句话关掉IOS阻塞式交互效果
                holder.setText(R.id.content, swipeBean.name);
                //验证长按
                holder.setOnLongClickListener(R.id.content, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(mContext, "longclig", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
                        return false;
                    }
                });

                holder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        if (pos >= 0 && pos < mDatas.size()) {
                            Toast.makeText(DeleteActivity.this, "删除:" + pos, Toast.LENGTH_SHORT).show();
                            mDatas.remove(pos);
                            mAdapter.notifyItemRemoved(pos);//推荐用这个
                            //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                            //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                            //mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
                (holder).setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).name, Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onClick() called with: v = [" + v + "]");
                    }
                });
                //置顶：
                holder.setOnClickListener(R.id.btnTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();

                        if (pos > 0 && pos < mDatas.size()) {
                            SwipeBean swipeBean = mDatas.get(pos);
                            mDatas.remove(swipeBean);
                            mAdapter.notifyItemInserted(0);
                            mDatas.add(0, swipeBean);
                            mAdapter.notifyItemRemoved(pos + 1);
                            if (mLayoutManager.findFirstVisibleItemPosition() == 0) {
                                mLv.scrollToPosition(0);
                            }
                            //notifyItemRangeChanged(0,holder.getAdapterPosition()+1);
                        }

                    }
                });

            }
        };
        mLv.setAdapter(mAdapter);
        //mRecyclerView.setLayoutManager(mLayoutManager = new GridLayoutManager(getContext(), 2));
        mLv.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));

        //6 2016 10 21 add , 增加viewChache 的 get()方法，
        // 可以用在：当点击外部空白处时，关闭正在展开的侧滑菜单。我个人觉得意义不大，
        mLv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                    if (null != viewCache) {
                        viewCache.smoothClose();
                    }
                }
                return false;
            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add(new SwipeBean("" + i));
        }
    }
}
