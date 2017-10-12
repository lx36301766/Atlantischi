package com.starcor.launcher.widget;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starcor.launcher.MainActivity;
import com.starcor.launcher.domain.AppInfo;
import com.starcorcn.launcher.R;

public class ImgViewAndTextView extends ShortcutIcon {
    public ImgViewAndTextView(Context context, AppInfo info, int id) {
        super(context, info);
        setId(id);
    }

    @Override
    public void setSelect(boolean value) {
        mAppName.setSelected(value);
        if (value) {
            setBackgroundResource(R.drawable.image_focus);
            mAppName.setTextColor(0xffffffff);
        } else {
            mAppName.setTextColor(0xffaaaaaa);
            setBackgroundResource(0);
        }
    }

    @Override
    protected void init() {
        super.init();
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams lp = new LayoutParams(MainActivity.OperationWidth(80), MainActivity.OperationHeight(80));
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        lp.bottomMargin = MainActivity.OperationHeight(15);
        mIcon = new ImageView(mContext);
        mIcon.setImageDrawable(info.icon);

        this.addView(mIcon, lp);
        mAppName = new TextView(mContext);
        mAppName.setTextSize(TypedValue.COMPLEX_UNIT_PX, MainActivity.OperationHeight(20));
        mAppName.setTextColor(0xffaaaaaa);
        mAppName.setGravity(Gravity.CENTER);
        mAppName.setSingleLine(true);
        mAppName.setEllipsize(TruncateAt.MARQUEE);
        mAppName.setText(info.title);
        mAppName.setVisibility(View.VISIBLE);
        lp = new LayoutParams(MainActivity.OperationWidth(120), LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        this.addView(mAppName, lp);
        setGravity(Gravity.CENTER);
    }

    @Override
    public void reSetIcon(AppInfo info, int id) {
        super.reSetIcon(info, id);
        mIcon.setImageDrawable(info.icon);
        mAppName.setText(info.title);
    }
}
