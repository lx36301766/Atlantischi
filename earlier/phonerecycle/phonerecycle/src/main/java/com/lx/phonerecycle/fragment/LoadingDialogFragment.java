package com.lx.phonerecycle.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lx.phonerecycle.R;

/**
 * Created with IntelliJ IDEA.
 * User: luo.xuan
 * Date: 13-6-2
 * Time: 下午6:16
 *
 * 加载框
 *
 */
@SuppressWarnings("unused")
public class LoadingDialogFragment extends DialogFragment {

    private static final String TAG = LoadingDialogFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.custom_dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loading_dialog, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    int showCount = 0;

    @Override
    public void show(FragmentManager manager, String tag) {
        if (showCount == 0) {
            try {
                super.show(manager, tag);
            } catch (Exception e) {
            }
        }
        showCount++;
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        throw new UnsupportedOperationException("please use show(FragmentManager manager, String tag)");
    }

    @Override
    public void dismiss() {
        showCount--;
        if (showCount == 0) {
            try {
                super.dismiss();
            } catch (Exception e) {
            }
        }
    }
}
