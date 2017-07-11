package pl.atlantischi.ximagecompat.impl;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.widget.ImageView;
import pl.atlantischi.ximagecompat.interfaces.XImageCompat;

/**
 * Created by admin on 2017/7/9.
 */

public class UniversalImageLoaderCompat implements XImageCompat {

    @Override
    public void display(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView);
    }

}
