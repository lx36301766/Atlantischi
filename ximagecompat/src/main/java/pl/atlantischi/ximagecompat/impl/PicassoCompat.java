package pl.atlantischi.ximagecompat.impl;

import com.squareup.picasso.Picasso;

import android.widget.ImageView;
import pl.atlantischi.ximagecompat.interfaces.XImageCompat;

/**
 * Created by admin on 2017/7/7.
 */

public class PicassoCompat implements XImageCompat {

    @Override
    public void display(String url, ImageView imageView) {
        Picasso.with(imageView.getContext()).load(url);
    }

}
