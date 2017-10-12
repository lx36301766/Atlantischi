package com.starcor.launcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starcor.config.DeviceInfo;
import com.starcor.config.Factory;
import com.starcor.launcher.domain.AppInfo;
import com.starcor.launcher.domain.KeyAdapter;
import com.starcor.launcher.domain.Rotate3dAnimation;
import com.starcor.launcher.widget.ImgViewAndTextView;
import com.starcor.launcher.widget.ShortcutIcon;
import com.starcorcn.launcher.R;


public class AppsActivity extends Activity implements OnKeyListener {
    public static final String TAG = "AppsActivity";
    private GridView gv_page;// App列表
    private ImageView iv_left;//向左翻页按钮
    private ImageView iv_right;//向右翻页按钮
    private AppAdapter mAppAdapter;//App列表的适配器
    private Bitmap mSnapshot = null;//保存快照的Bitmap方便释放
    private ImageView iv_snapshot;//当前GridView的快照
    private TextView txt_pageCount;// 页面信息
    private ShortcutIcon selectedIcon = null;//当前选中Icon
    private ShortcutIcon lastView = null;//选中状态会不对
    private TextView tvTitle;

    private static final int DIRECTION_LEFT = 0;//向左移动选择框
    private static final int DIRECTION_RIGHT = 1;//向右移动选择框
    private static final int DIRECTION_DOWN = 2;//向下移动选择框
    private static final int DIRECTION_UP = 3;//向上移动选择框
    private static final int PAGE_SISE = 21;//每页显示App图标的数量
    private static final int COLUMNS_NUM = 7;//GridView的列数
    private int appNums = 0;//总的个数
    private int mPageCount;//当前列表页总数
    private int dstPage = -1;

    private boolean ready = false;
    private Handler mHandler = new Handler();
    private Object mLock = new Object();
    private int currentId = 0;
    private List<AppInfo> appInfos = new ArrayList<AppInfo>();
    private int lastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFilterList();
        setContentView(R.layout.activity_apps);
        loadApplications();
        if (DeviceInfo.getFactory() == Factory.VERSION_SC_1_0_0) {
            init();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();

        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");

        this.registerReceiver(appReceiver, filter);
        registerReceiver(intentReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    public void onDestroy() {
        if (appReceiver != null) {
            this.unregisterReceiver(appReceiver);
        }
        if (intentReceiver != null) {
            this.unregisterReceiver(intentReceiver);
        }
        super.onDestroy();
    }

    private void initFilterList() {
        filterList.add("com.starcor.hunan");
        filterList.add("com.android.development");
    }

    private BroadcastReceiver appReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG, "onReceive ACTION_SCREEN_ON ");
            if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
                appInfos.clear();
                loadApplications();
                if (DeviceInfo.getFactory() == Factory.VERSION_SC_1_0_0) {
                    init();
                }
            } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
                appInfos.clear();
                loadApplications();
                if (DeviceInfo.getFactory() == Factory.VERSION_SC_1_0_0) {
                    init();
                }
            } else if (Intent.ACTION_PACKAGE_CHANGED.equals(intent.getAction())) {

                appInfos.clear();
                loadApplications();
                if (DeviceInfo.getFactory() == Factory.VERSION_SC_1_0_0) {
                    init();
                }
            } else if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
                appInfos.clear();
                loadApplications();
                if (DeviceInfo.getFactory() == Factory.VERSION_SC_1_0_0) {
                    init();
                }
            } else if (Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction())) {
                appInfos.clear();
                loadApplications();
                if (DeviceInfo.getFactory() == Factory.VERSION_SC_1_0_0) {
                    init();
                }
            } else if (Intent.ACTION_PACKAGE_INSTALL.equals(intent.getAction())) {
                appInfos.clear();
                loadApplications();
                if (DeviceInfo.getFactory() == Factory.VERSION_SC_1_0_0) {
                    init();
                }
            }
        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "key " + event.getAction() + ":" + keyCode + ", event :" + event);
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyAdapter.KEY_RETURN:
                    this.finish();
                    break;
            }
            return false;
        }
        int newDstPage = -1;
        switch (keyCode) {
            case KeyAdapter.KEY_DOWN:
                changeSelectIcon(DIRECTION_DOWN);
                return true;
            case KeyAdapter.KEY_UP:
                changeSelectIcon(DIRECTION_UP);
                return true;
            case KeyAdapter.KEY_LEFT:
                changeSelectIcon(DIRECTION_LEFT);
                return true;
            case KeyAdapter.KEY_RIGHT:
                changeSelectIcon(DIRECTION_RIGHT);
                return true;
            case KeyAdapter.KEY_DPAD_ENTER:
            case KeyAdapter.KEY_ENTER:
                Log.i(TAG, "ENTER selectedIcon:" + selectedIcon);
                if (selectedIcon != null) {
                    selectedIcon.onClick(selectedIcon);
                } else {
                    changeSelectIcon(-1);
                }
                return true;
            case KeyAdapter.KEY_RETURN:
                return false;
            case KeyAdapter.KEY_1:
                newDstPage = 1;
                break;
            case KeyAdapter.KEY_2:
                newDstPage = 2;
                break;
            case KeyAdapter.KEY_3:
                newDstPage = 3;
                break;
            case KeyAdapter.KEY_4:
                newDstPage = 4;
                break;
            case KeyAdapter.KEY_5:
                newDstPage = 5;
                break;
            case KeyAdapter.KEY_6:
                newDstPage = 6;
                break;
            case KeyAdapter.KEY_7:
                newDstPage = 7;
                break;
            case KeyAdapter.KEY_8:
                newDstPage = 8;
                break;
            case KeyAdapter.KEY_9:
                newDstPage = 9;
                break;
        }
        if (ready) {
            // 已经准备翻页了，连续按
            // 如果会超过总页数的处理
            int num = Integer.valueOf((dstPage + "" + newDstPage));
            if (num <= mPageCount) {
                dstPage = num;
            }
        } else {
            synchronized (mLock) {
                dstPage = newDstPage;
            }
        }
        if (dstPage > -1) {
            if (!ready) {
                // 准备0.5秒后翻页
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lastPosition = changePage(lastPosition, dstPage);
                        ready = false;
                        synchronized (mLock) {
                            dstPage = -1;
                        }
                    }
                }, 500);
            }
        }
        return true;
    }

    private void changeSelectIcon(int direction) {
        int id = 0;
        currentId = 0;
        Log.i(TAG, ", currentId PPPLIKJUMNHYTGB:");
        if (selectedIcon == null && gv_page.getCount() > 0 && lastPosition != 0) {
            selectedIcon = ((ShortcutIcon) gv_page.getChildAt(lastPosition));
            selectedIcon.setSelect(true);
            Log.i(TAG, ", selectedIcon a==  " + selectedIcon);
            return;
        }
        currentId = selectedIcon.getId();
        Log.i(TAG, ", currentId:" + currentId);
        if (DIRECTION_DOWN == direction) {
            if (currentId > 13) {
                return;
            }
            if (mPageCount == mAppAdapter.getCurrentPage()) {
                int row = (appNums - (mPageCount - 1) * PAGE_SISE) / 7;
                if (row == 0) {
                    return;
                } else if (row == 1) {
                    if (currentId > 6) {
                        return;
                    }
                }
            }
            currentId += COLUMNS_NUM;
            if (mPageCount == mAppAdapter.getCurrentPage()) {
                if ((appNums - (mPageCount - 1) * PAGE_SISE) < currentId + 1) {
                    currentId = (appNums - (mPageCount - 1) * PAGE_SISE);
                }
            }
        } else if (DIRECTION_UP == direction) {
            if (currentId < 7) {
                return;
            }
            currentId -= COLUMNS_NUM;
        } else if (DIRECTION_LEFT == direction) {
            if ((currentId == 0 || currentId == 7 || currentId == 14) &&
                    mAppAdapter.getCurrentPage() > 1) {
                currentId = currentId + 6;
                id = -5;
            } else {
                currentId -= 1;
            }
            Log.i(TAG, "DIRECTION_LEFT currentId :" + currentId);
        } else if (DIRECTION_RIGHT == direction) {
            if ((currentId == 6 || currentId == 13 || currentId == 20) &&
                    mAppAdapter.getCurrentPage() < mPageCount) {
                currentId = currentId - 6;
                id = -5;
            } else {
                currentId += 1;
            }
        }
        /**
         * Id越界进行翻页
         */
        Log.i(TAG, "getcount :" + gv_page.getCount() + ", currentId:" + currentId + ", pagesize :" + mPageCount);
        if (id < 0) {
            if (DIRECTION_LEFT == direction || DIRECTION_UP == direction) {
                currentId = changePage(currentId, mAppAdapter.getCurrentPage() - 1);
            } else if (DIRECTION_DOWN == direction
                    || DIRECTION_RIGHT == direction) {
                currentId = changePage(currentId, mAppAdapter.getCurrentPage() + 1);
            }
        } else {
            if (currentId < 0) {
                currentId = 0;
            } else if (currentId > 20) {
                currentId = 20;
            } else if (mPageCount == mAppAdapter.getCurrentPage() && appNums <= (mPageCount - 1) * PAGE_SISE + currentId) {
                currentId -= 1;//设置最后一个图标的id
            }
            selectedIcon.setSelect(false);
            selectedIcon = (ShortcutIcon) gv_page.getChildAt(currentId);
            selectedIcon.setSelect(true);
        }
        lastPosition = currentId;
    }

    private final void init() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.OperationHeight(30));
        tvTitle.getPaint().setFakeBoldText(true);
        tvTitle.setTextColor(0xffffffff);
        ((LayoutParams) tvTitle.getLayoutParams()).leftMargin = MainActivity.OperationHeight(60);
        ((LayoutParams) tvTitle.getLayoutParams()).topMargin = MainActivity.OperationHeight(40);
        ((LayoutParams) tvTitle.getLayoutParams()).bottomMargin = MainActivity.OperationHeight(10);

        LayoutParams ll_lp;

        iv_snapshot = (ImageView) findViewById(R.id.iv_snapshot);
        gv_page = (GridView) findViewById(R.id.gv_page);
        gv_page.setOnKeyListener(this);
        gv_page.getLayoutParams().height = MainActivity.OperationHeight(550);
        gv_page.getLayoutParams().width = MainActivity.OperationWidth(1120);
        ((RelativeLayout.LayoutParams) gv_page.getLayoutParams()).topMargin = MainActivity.OperationHeight(40);
        gv_page.setNumColumns(COLUMNS_NUM);

        iv_left = (ImageView) findViewById(R.id.iv_left);
        ll_lp = (LayoutParams) iv_left.getLayoutParams();
        ll_lp.leftMargin = MainActivity.OperationWidth(25);

        iv_right = (ImageView) findViewById(R.id.iv_right);
        ll_lp = (LayoutParams) iv_right.getLayoutParams();
        ll_lp.rightMargin = MainActivity.OperationHeight(25);

        View view = findViewById(R.id.rl_bottom);
        ll_lp = (LayoutParams) view.getLayoutParams();
        ll_lp.bottomMargin = MainActivity.OperationHeight(35);

        txt_pageCount = (TextView) findViewById(R.id.txt_page_count);
        txt_pageCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.OperationHeight(20));

        Log.i(TAG, "Appinfos.size--=" + appInfos.size());
        appNums = appInfos.size();
        mPageCount = (int) Math.ceil(appNums * 1f / PAGE_SISE);
        txt_pageCount.setText("1/" + mPageCount + "    共" + appNums + "个应用");
        mAppAdapter = new AppAdapter();
        mAppAdapter.setAppInfos(appInfos);
        changePage(0, 1);

        gv_page.setAdapter(mAppAdapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(TAG, "hasFocus:" + hasFocus + ", selectedIcon:" + selectedIcon);
        if (hasFocus) {
            if (gv_page.getCount() == 0) {
                return;
            }
            if (selectedIcon == null && gv_page.getCount() > 0) {
                selectedIcon = ((ShortcutIcon) gv_page.getChildAt(0));
                Log.i(TAG, "hasFocus ttt selectedIcon :" + selectedIcon);
            }
            selectedIcon.setSelect(true);
        } else {
            lastView = selectedIcon;
        }
    }

    // 翻页
    public final int changePage(int selectedId, final int page) {
        Log.i(TAG, "selectedId :" + selectedId + ", page :" + page);
        if (page == mAppAdapter.getCurrentPage() || page > mPageCount
                || page < 1)
            return selectedId;
        txt_pageCount.setText(page + "/" + mPageCount + "    共" + appNums + "个应用");
        if (page < mPageCount) {
            iv_right.setVisibility(View.VISIBLE);
        } else {
            iv_right.setVisibility(View.INVISIBLE);
        }
        if (page > 1) {
            iv_left.setVisibility(View.VISIBLE);
        } else {
            iv_left.setVisibility(View.INVISIBLE);
        }
        if (mAppAdapter.getCurrentPage() != 0) {
            int flag = 1;
            if (page < mAppAdapter.getCurrentPage())
                flag = -1;
            gv_page.setDrawingCacheEnabled(true);
            mSnapshot = gv_page.getDrawingCache();

            mAppAdapter.setCurrentPage(page);
            iv_snapshot.setImageBitmap(mSnapshot);

            if (selectedIcon != null) {
                selectedIcon.setSelect(false);
                if (flag > 0) {
                    //右翻
                    int restNum = appNums - PAGE_SISE * (mAppAdapter.getCurrentPage() - 1);
                    Log.i(TAG, "right childcount num is :" + restNum + ", Id:" + selectedId + "page size :" + gv_page.getChildCount() + ",selectedIcon:" + selectedIcon);
                    if (restNum > 14) {
                        selectedIcon = (ShortcutIcon) gv_page.getChildAt(selectedId);
                    } else if (restNum < 8) {
                        selectedIcon = (ShortcutIcon) gv_page.getChildAt(0);
                    } else {
//					if (selectedId > 7 && selectedId+1 <= restNum) {
//						selectedId = 7;
//					}
                        if (selectedId + 1 > restNum) {
                            selectedId = 7;
                        }
                        selectedIcon = (ShortcutIcon) gv_page.getChildAt(selectedId);
                    }
                } else {
                    Log.i(TAG, " left childcount is :" + gv_page.getChildCount() + ", selectedId=" + selectedId + ", selectedIcon" + selectedIcon);
                    selectedIcon = (ShortcutIcon) gv_page.getChildAt(selectedId);
                }
            }
            applyRotation(selectedId, iv_snapshot, 0, -90 * flag);
            applyRotation(selectedId, gv_page, 90 * flag, 0);
        } else {
            mAppAdapter.setCurrentPage(page);
        }
        return selectedId;
    }

    /**
     * 实现旋转
     *
     * @param iv    旋转的对象
     * @param start 开始角度
     * @param end   结束角度
     */
    private void applyRotation(final int id, View iv, final float start, final float end) {
        final float centerX = mSnapshot.getWidth() / 2.0f;
        final float centerY = mSnapshot.getHeight() / 2.0f;
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
                centerX, centerY);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                if (lastView != null) {
                    lastView.setSelect(false);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_snapshot.setImageBitmap(null);
                mSnapshot.recycle();
                gv_page.setDrawingCacheEnabled(false);
                if (selectedIcon == null) {
                    selectedIcon = (ShortcutIcon) gv_page.getChildAt(id);
                }
                selectedIcon.setSelect(true);
            }
        });
        iv.startAnimation(rotation);
    }

    class AppAdapter extends BaseAdapter {
        private List<AppInfo> appInfos = new ArrayList<AppInfo>();
        private int currentPage = 0;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
            notifyDataSetChanged();
        }

        public List<AppInfo> getAppInfos() {
            return appInfos;
        }

        public void setAppInfos(List<AppInfo> appInfos) {
            this.appInfos = appInfos;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            int Difference = appInfos.size() - (currentPage - 1) * PAGE_SISE;
            return Math.min(Difference, PAGE_SISE);
        }

        @Override
        public Object getItem(int arg0) {
            return appInfos.get((currentPage - 1) * PAGE_SISE + arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View contView, ViewGroup arg2) {
            if (contView == null) {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(MainActivity.OperationWidth(149), MainActivity.OperationHeight(174));
                contView = new ImgViewAndTextView(AppsActivity.this,
                        (AppInfo) getItem(position), position);
                contView.setLayoutParams(lp);
            } else {
                ((ShortcutIcon) contView).reSetIcon((AppInfo) getItem(position), position
                        % PAGE_SISE);
                if (lastView == contView && lastView != null) {
                    lastView.setSelect(true);
                }
            }
            Log.e(TAG, "getView position:" + position);
            return contView;
        }
    }

    private List<String> filterList = new ArrayList<String>();

    private boolean isFilter(String packageName) {
        return filterList.contains(packageName);
    }

    private void loadApplications() {
        PackageManager manager = this.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> apps = manager.queryIntentActivities(
                mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
        if (apps != null) {
            final int count = apps.size();
            for (int i = 0; i < count; i++) {
                AppInfo application = new AppInfo();
                ResolveInfo info = apps.get(i);
                application.title = info.loadLabel(manager).toString();
                Log.i(TAG, "application.title =" + application.title);
                application.setActivity(new ComponentName(
                        info.activityInfo.applicationInfo.packageName,
                        info.activityInfo.name), Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                application.flags = info.activityInfo.applicationInfo.flags;
//				application.name = info.activityInfo.name;
                application.icon = info.activityInfo.loadIcon(manager);
                application.packageName = info.activityInfo.applicationInfo.packageName;
                if (info.filter != null && info.filter.hasCategory(Intent.CATEGORY_HOME)) {
                    continue;
                }
                if (isFilter(application.packageName)) {
                    continue;
                }
                if (application.packageName.equals("com.starcor.jiushi.fileexplorer") || application.packageName.equals("mstar.tvsetting.ui")
                        || application.packageName.equals("com.starcor.setting.service")) {
                    continue;
                }
                if (!application.packageName.equals(this.getPackageName()))
                    appInfos.add(application);
            }
            Log.i("", "Appinfos.size=" + appInfos.size() + ",count :" + count);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        return onKeyDown(keyCode, event);
    }


    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    Log.i(TAG, "Intent.ACTION_CLOSE_SYSTEM_DIALOGS action:" + action + ",reason:" + reason);
                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        // 短按home键
                        if (!isFinishing())
                            finish();
                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        // 长按home键
                        if (!isFinishing())
                            finish();
                    }
                }
            }
        }
    };

}
