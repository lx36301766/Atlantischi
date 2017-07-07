package com.lx.testandroid.testviews;

import com.lx.testandroid.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PayCenterLeaveDialog extends Dialog {

    public PayCenterLeaveDialog(Context context) {
        super(context);
    }

    public PayCenterLeaveDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public PayCenterLeaveDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private static class Params {

        Context mContext;
        CharSequence mContent;
        OnClickListener mLeftListener;
        CharSequence mLeftText;
        OnClickListener mRightListener;
        CharSequence mRightText;

        Params(Context context) {
            mContext = context;
        }

        private void apply(final PayCenterLeaveDialog dialog) {
            View root = View.inflate(mContext, R.layout.dialog_paycenter_leave, null);
//            root = View.inflate(mContext, R.layout.dialog_buyflow_alert, null);
            dialog.setContentView(root);
            TextView contentTv = (TextView) dialog.findViewById(R.id.content);
            contentTv.setText(mContent);
            Button leftBtn = (Button) dialog.findViewById(R.id.left_btn);
            leftBtn.setText(mLeftText);
            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLeftListener != null) {
                        mLeftListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                }
            });
            Button rightBtn = (Button) dialog.findViewById(R.id.right_btn);
            rightBtn.setText(mRightText);
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRightListener != null) {
                        mRightListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                }
            });
        }
    }

    public static class Builder {

        Params mParams;

        public Builder (Context context) {
            mParams = new Params(context);
        }

        public Builder setContext(CharSequence content) {
            mParams.mContent = content;
            return this;
        }

        public Builder setLeftButton(CharSequence text, OnClickListener listener) {
            mParams.mLeftText = text;
            mParams.mLeftListener = listener;
            return this;
        }

        public Builder setRightButton(CharSequence text, OnClickListener listener) {
            mParams.mRightText = text;
            mParams.mRightListener = listener;
            return this;
        }

        public PayCenterLeaveDialog create() {
            PayCenterLeaveDialog dialog = new PayCenterLeaveDialog(mParams.mContext, R.style.invoice_jumei_dialog);
            mParams.apply(dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }

    }

}
