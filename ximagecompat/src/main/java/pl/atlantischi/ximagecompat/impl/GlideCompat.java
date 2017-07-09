package pl.atlantischi.ximagecompat.impl;

import com.bumptech.glide.Glide;

import android.widget.ImageView;
import pl.atlantischi.ximagecompat.interfaces.XImageCompat;

/**
 * Created by admin on 2017/7/7.
 */

public class GlideCompat implements XImageCompat {

    @Override
    public void display(String url, ImageView imageView) {
        Glide.with(imageView).load(url).into(imageView);
    }

}
