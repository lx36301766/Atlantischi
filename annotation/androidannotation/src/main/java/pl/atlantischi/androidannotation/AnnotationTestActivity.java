package pl.atlantischi.androidannotation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import pl.atlantischi.androidannotation.runtime.XInjectManager;
import pl.atlantischi.androidannotation.runtime.inject.RContentView;
import pl.atlantischi.androidannotation.runtime.inject.RInjectView;
import pl.atlantischi.androidannotation.runtime.inject.ROnClickV2;

/**
 * Created on 21/03/2017.
 *
 * @author lx
 */

@RContentView(R.layout.activity_annotation)
public class AnnotationTestActivity extends Activity {

    @RInjectView(R.id.annotation_text)
    TextView annotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XInjectManager.init(this);
        annotext.setText("SSFESSS");
    }

    @ROnClickV2({R.id.annotation_text})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.annotation_text:
                annotext.setText("XXXSSEREE");
                break;
        }
    }

}
