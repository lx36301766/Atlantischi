package com.lx.testandroid.recyclerview;

import java.util.ArrayList;
import java.util.Collections;

import com.lx.testandroid.R;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewActivity extends Activity {


    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
        Collections.addAll(data, "1", "2", "3", "5", "1", "2", "3", "5", "1", "2", "3", "5", "1", "2", "3", "5",
                "1", "2", "3", "5", "1", "2", "3", "5", "1", "2", "3", "5", "1", "2", "3", "5");
    }

    class HomeAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new MyViewHolder(View.inflate(RecyclerViewActivity.this, R.layout.list_item, null));
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            viewHolder.tv.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.text);
        }
    }

    class DividerItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

}
