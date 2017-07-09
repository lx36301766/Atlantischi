package pl.atlantischi.ximagecompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import pl.atlantischi.ximagecompat.impl.PicassoCompat;
import pl.atlantischi.ximagecompat.interfaces.XImageCompat;

public class XImageCompatActivity extends AppCompatActivity {

    String url = "http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=75aaa91fa444ad342eea8f83e59220c2"
                    + "/0bd162d9f2d3572cf556972e8f13632763d0c388.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ximage_compat);

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        XImageCompat xImageCompat = new PicassoCompat();
        xImageCompat.display(url, iv);

    }
}
