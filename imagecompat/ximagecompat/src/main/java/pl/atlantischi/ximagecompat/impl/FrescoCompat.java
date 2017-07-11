package pl.atlantischi.ximagecompat.impl;

import com.facebook.drawee.backends.pipeline.Fresco;

import android.widget.ImageView;
import pl.atlantischi.ximagecompat.interfaces.XImageCompat;

/**
 * Created by admin on 2017/7/7.
 */

public class FrescoCompat implements XImageCompat {

    @Override
    public void display(String url, ImageView imageView) {
        Fresco.initialize(imageView.getContext());


    }

}
