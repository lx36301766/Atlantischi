package com.lx.testandroid.hlistview;

import java.util.ArrayList;

import com.lx.testandroid.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    HorizontalListView listview1;
    HorizontalListView listview2;

    HorizontalListView.OnScrollStateChangedListener onScrollStateChangedListener1 = new HorizontalListView
            .OnScrollStateChangedListener() {

        @Override
        public void onScrollStateChanged(HorizontalListView parent, ScrollState scrollState) {
            if (scrollState == ScrollState.SCROLL_STATE_IDLE) {
                listview2.scrollToWithNotifyListener(parent.mNextX, false);
                Log.d(TAG, "onScrollStateChanged: listview1");
            }
        }
    };
    HorizontalListView.OnScrollStateChangedListener onScrollStateChangedListener2 = new HorizontalListView
            .OnScrollStateChangedListener() {

        @Override
        public void onScrollStateChanged(HorizontalListView parent, ScrollState scrollState) {
            if (scrollState == ScrollState.SCROLL_STATE_IDLE) {
                listview1.scrollToWithNotifyListener(parent.mNextX, false);
                Log.d(TAG, "onScrollStateChanged: listview2");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        GifImageView gifImageView = (GifImageView) findViewById(R.id.gif_view);
        //        gifImageView.setImageResource(R.mipmap.test2);

        listview1 = (HorizontalListView) findViewById(R.id.list1);
        listview2 = (HorizontalListView) findViewById(R.id.list2);
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        root.setOrientation(LinearLayout.VERTICAL);

        final ArrayList<String> data = getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, data);

        listview1.setAdapter(adapter);
        listview2.setAdapter(adapter);

        listview1.setOnScrollStateChangedListener(new HorizontalListView.OnScrollStateChangedListener() {
            @Override
            public void onScrollStateChanged(HorizontalListView parent,
                                             ScrollState scrollState) {
                if (scrollState == ScrollState.SCROLL_STATE_IDLE) {
                    listview2.scrollToWithNotifyListener(parent.mNextX, false);
                    Log.d(TAG, "onScrollStateChanged: listview1");
                }
            }
        });
        listview2.setOnScrollStateChangedListener(new HorizontalListView.OnScrollStateChangedListener() {
            @Override
            public void onScrollStateChanged(HorizontalListView parent,
                                             ScrollState scrollState) {
                if (scrollState == ScrollState.SCROLL_STATE_IDLE) {
                    listview1.scrollToWithNotifyListener(parent.mNextX, false);
                    Log.d(TAG, "onScrollStateChanged: listview2");
                }
            }
        });

//        listview1.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (view == clickSource) {
//                    listview2.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
//                }
//            }
//        });
//        listview2.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (view == clickSource) {
//                    listview1.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
//                }
//            }
//        });
//
//        listview1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (touchSource == null) {
//                    touchSource = v;
//                }
//                if (v == touchSource) {
//                    listview2.dispatchTouchEvent(event);
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        clickSource = v;
//                        touchSource = null;
//                    }
//                }
//                return false;
//            }
//        });
//        listview2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (touchSource == null) {
//                    touchSource = v;
//                }
//                if (v == touchSource) {
//                    listview1.dispatchTouchEvent(event);
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        clickSource = v;
//                        touchSource = null;
//                    }
//                }
//                return false;
//            }
//        });

    }

    View clickSource;
    View touchSource;

    int offset = 0;

    private ArrayList<String> getData() {
        ArrayList list = new ArrayList();
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        return list;
    }

}
