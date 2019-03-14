package com.lx.testandroid.recyclerview;

import java.util.ArrayList;
import java.util.List;

import com.lx.testandroid.R;
import com.lx.testandroid.recyclerview.observablescrollview.CacheFragmentStatePagerAdapter;
import com.lx.testandroid.recyclerview.observablescrollview.ObservableRecyclerView;
import com.lx.testandroid.recyclerview.layoutmanager.SocialRecyclerLayoutManager;
import com.lx.testandroid.recyclerview.refreshrecyclerview.RecyclerRefreshLayout;
import com.lx.testandroid.recyclerview.scrollhelper.SocialScrollerHelper;
import com.lx.testandroid.superswiperefresh.SuperSwipeRefreshLayout;
import com.lx.testandroid.testviews.SocialViewPageTitleLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.IntDef;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class FragmentTestActivity extends FragmentActivity {

    public static final int AAA = 1;
    public static final int BBB = 2;
    public static final int CCC = 3;
    private static final String TAG = FragmentTestActivity.class.getSimpleName();
    ViewPager mViewPager;
    SocialViewPageTitleLayout mSocialViewPageTitleLayout;
    View toolBar;
    SocialScrollerHelper mSocialScrollerHelper;
    private FragmentManager mFragmentManager;
    private CacheFragmentStatePagerAdapter mFragmentStatePagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        new Thread() {
        //            @Override
        //            public void run() {
        //                test(AAA);
        //            }
        //        }.start();
        //
        //        List aa = new ArrayList();
        //        test2(aa);

        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mFragmentManager = getSupportFragmentManager();
        mFragmentStatePagerAdapter = new CacheFragmentStatePagerAdapter(mFragmentManager) {

            Fragment[] fragments = {
                    Fragment.instantiate(getApplicationContext(), SocialIndexLiveFragment.class.getName()),
                    Fragment.instantiate(getApplicationContext(), SocialIndexAttentionFragment.class.getName()),
                    Fragment.instantiate(getApplicationContext(), SocialIndexChoiceFragment.class.getName())
            };

            @Override
            protected Fragment createItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

        };
        mViewPager.setAdapter(mFragmentStatePagerAdapter);

//        mSocialViewPageTitleLayout = (SocialViewPageTitleLayout) findViewById(R.id.page_title);
        mSocialViewPageTitleLayout.setViewPager(mViewPager);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSocialViewPageTitleLayout.setTitles("关注", "热门直播", "精选");
            }
        }, 100);

        toolBar = mSocialViewPageTitleLayout;

//        final int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
//        findViewById(R.id.pager_wrapper).setPadding(0, getActionBarSize() + tabHeight, 0, 0);

        mSocialScrollerHelper = new SocialScrollerHelper(this);
    }

    @MainThread
    public String test(@Tes int avc) {
        return null;
    }

    @MainThread
    public String test2(@Size(multiple = 2) List avc) {
        return null;
    }

    @IntDef({AAA, BBB, CCC})
    public @interface Tes {

    }

    public static abstract class SocialIndexBaseFragment extends Fragment {

        private ArrayList<String> data = new ArrayList<>();
        private HomeAdapter mAdapter;

        public int dp2px(float dpValue) {
            final float scale = getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            final SocialRecyclerLayoutManager recyclerLayoutManager = new SocialRecyclerLayoutManager(getActivity());
            View root = inflater.inflate(R.layout.fragment, container, false);
            final ObservableRecyclerView mRecyclerView = (ObservableRecyclerView) root.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
            for (int i = 0; i < 50; i++) {
                data.add(i + "");
            }
//            superSwipeRefreshLayout(root);
            recyclerRefreshLayout(root);

            FragmentTestActivity parentActivity = (FragmentTestActivity) getActivity();
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setTouchInterceptionViewGroup((ViewGroup) parentActivity.findViewById(R.id.container));
            mRecyclerView.setScrollViewCallbacks(parentActivity.mSocialScrollerHelper);
            return root;
        }

        private void recyclerRefreshLayout(View root) {
            final RecyclerRefreshLayout recyclerRefreshLayout =
                    (RecyclerRefreshLayout) root.findViewById(R.id.super_swipe_refresh_layout);
            recyclerRefreshLayout.setNestedScrollingEnabled(true);
            recyclerRefreshLayout.setNestedScrollingEnabled(true);
            recyclerRefreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    recyclerRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerRefreshLayout.setRefreshing(false);
                        }
                    }, 3000);
                }
            });

        }

        private void superSwipeRefreshLayout(View root) {
            final SuperSwipeRefreshLayout refreshLayout =
                    (SuperSwipeRefreshLayout) root.findViewById(R.id.super_swipe_refresh_layout);
            TextView tv = new TextView(getActivity());
            tv.setGravity(Gravity.CENTER);
            tv.setText("header");
            tv.setTextColor(Color.RED);
            tv.setTextSize(25);
            tv.setBackgroundColor(0x99009999);
            refreshLayout.setHeaderView(tv);
            TextView tv2 = new TextView(getActivity());
            tv2.setGravity(Gravity.CENTER);
            tv2.setText("footer");
            tv2.setTextColor(Color.RED);
            tv2.setTextSize(25);
            tv2.setBackgroundColor(0x99009999);
            refreshLayout.setFooterView(tv2);
            refreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
                @Override
                public void onRefresh() {
                    Log.d(TAG, "Pull, onRefresh");
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }

                @Override
                public void onPullDistance(int distance) {
                    Log.d(TAG, "Pull, onPullDistance, distance=" + distance);
                }

                @Override
                public void onPullEnable(boolean enable) {
                    Log.d(TAG, "Pull, onPullEnable, enable=" + enable);
                }
            });
            refreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    Log.d(TAG, "LoadMore, onRefresh");
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setLoadMore(false);
                        }
                    }, 1000);
                }

                @Override
                public void onPushDistance(int distance) {
                    Log.d(TAG, "LoadMore, onPullDistance, distance=" + distance);
                }

                @Override
                public void onPushEnable(boolean enable) {
                    Log.d(TAG, "LoadMore, onPullEnable, enable=" + enable);
                }
            });
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            data.clear();
        }

        class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                return new MyViewHolder(View.inflate(getActivity(), R.layout.list_item, null));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                contentHolder((MyViewHolder) viewHolder, position);
            }

            private void contentHolder(MyViewHolder myViewHolder, int position) {
                if (position % 2 == 0) {
                    myViewHolder.tv.setBackgroundColor(0x7f9900ff);
                } else {
                    myViewHolder.tv.setBackgroundColor(0x7f99000f);
                }
                if (position < getItemCount()) {
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                            .MATCH_PARENT, dp2px(200));
                    myViewHolder.tv.setLayoutParams(lp);
                    myViewHolder.tv.setText(data.get(position));
                }
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

    }

    /**
     * 社区首页直播
     */
    public static class SocialIndexLiveFragment extends SocialIndexBaseFragment {

    }

    /**
     * 社区首页关注
     */
    public static class SocialIndexAttentionFragment extends SocialIndexBaseFragment {

    }

    /**
     * 社区首页精选
     */
    public static class SocialIndexChoiceFragment extends SocialIndexAttentionFragment {

    }

}
