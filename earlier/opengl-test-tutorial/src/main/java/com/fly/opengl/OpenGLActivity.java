package com.fly.opengl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.fly.opengl.gl1.VortexRenderer;
import com.fly.opengl.gl1.VortexView;
import com.fly.opengl.gl2.OpenGLRender;
import com.fly.opengl.gl2.mesh.SimplePlane;

public class OpenGLActivity extends Activity {

    private VortexView vortexView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        vortexView = new VortexView(this);
//        setContentView(vortexView);
        GLSurfaceView view = new GLSurfaceView(this);
        
        OpenGLRender render = new OpenGLRender(this);
        view.setRenderer(render);
        setContentView(view);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        vortexView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        vortexView.onResume();
    }

}