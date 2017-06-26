package com.lx.testandroid.viewpager;

import java.util.ArrayList;
import java.util.List;

import com.lx.testandroid.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by xuanluo on 16/11/25.
 */

public class ViewPagerActivity extends FragmentActivity {

    SocialViewPager mViewPager;

    private List<View> viewList = new ArrayList<>();//view数组

    View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        view1 = View.inflate(this, R.layout.view1, null);
        view2 = View.inflate(this, R.layout.view2, null);
        view3 = View.inflate(this, R.layout.view3, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        setListener(view1);
        setListener(view2);
        setListener(view3);

        mViewPager = (SocialViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        });
    }

    private void setListener(View view) {
        Button button = (Button) view.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.switchScroll();
            }
        });
    }

}
