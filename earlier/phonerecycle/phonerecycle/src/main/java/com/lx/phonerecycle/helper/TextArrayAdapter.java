package com.lx.phonerecycle.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.lx.phonerecycle.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luo.xuan
 * Date: 13-7-3
 * Time: 上午11:37
 *
 * 单行文本的adapter
 *
 */

@SuppressWarnings("unused")
public class TextArrayAdapter<T> extends ArrayAdapter<T> {

    public TextArrayAdapter(Context context) {
        super(context, 0);
    }

    public TextArrayAdapter(Context context, T[] objects) {
        super(context, 0, objects);
    }

    public TextArrayAdapter(Context context, List<T> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = convertView == null ? new TextView(getContext()) : (TextView) convertView;
        textView.setTextColor(getContext().getResources().getColor(R.color.white));
        textView.setTextSize(25);
        textView.setPadding(0, 5, 0, 5);
        T item = getItem(position);
        if (item instanceof Integer) {
            textView.setText((Integer) getItem(position));
        } else if (item instanceof String) {
            textView.setText((String) getItem(position));
        } else {
            throw new IllegalArgumentException("Error Item : " + item.getClass().getName());
        }
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

}
