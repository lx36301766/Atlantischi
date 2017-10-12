package com.fly.opengl.gl1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public class VortexRenderer2 implements Renderer {

    private static final String LOG_TAG = VortexRenderer2.class.getSimpleName();

    // a raw buffer to hold indices allowing a reuse of points.
    private ShortBuffer _indexBuffer;

    // a raw buffer to hold the vertices
    private FloatBuffer _vertexBuffer;

    // a raw buffer to hold the colors
    private FloatBuffer _colorBuffer;

    private int _nrOfVertices = 0;

    private float _xAngle;
    private float _yAngle;

    private float x;
    private float y;

    private float _width = 320f;
    private float _height = 480f;

    private short[] _indicesArray = { 0, 1, 2 };

    private VortexView vortexView;

    public VortexRenderer2(VortexView vortexView) {
        this.vortexView = vortexView;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        gl.glMatrixMode(GL10.GL_PROJECTION);
        float ratio = _width / _height;
        float size = .01f * (float) Math.tan(Math.toRadians(45.0) / 2);
        //gl.glFrustumf(-size, size, -size / ratio, size / ratio, 0.01f, 100.0f);

        gl.glOrthof(-1, 1, -1 / ratio, 1 / ratio, 0.01f, 100.0f);
        gl.glViewport(0, 0, (int) _width, (int) _height);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        // preparation
        // define the color we want to be displayed as the "clipping wall"
        gl.glClearColor(0f, 0f, 0f, 1.0f);
        // enable the differentiation of which side may be visible
        gl.glEnable(GL10.GL_CULL_FACE);
        // which is the front? the one which is drawn counter clockwise
        gl.glFrontFace(GL10.GL_CCW);
        // which one should NOT be drawnC
        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        initTriangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        _width = w;
        _height = h;
        gl.glViewport(0, 0, w, h);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // clear the color buffer and the depth buffer
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);

        for (int i = 1; i <= 10; i++) {
            gl.glLoadIdentity();
            gl.glTranslatef(0.0f, -1f, -1.0f + -1.5f * i);
            // set rotation
            gl.glRotatef(_xAngle, 1f, 0f, 0f);
            gl.glRotatef(_yAngle, 0f, 1f, 0f);
            gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
        }

//        // define the color we want to be displayed as the "clipping wall"
//        // gl.glClearColor(0f, 0f, 0f, 1.0f);
//
//        // reset the matrix - good to fix the rotation to a static angle
//        gl.glLoadIdentity();
//
//        // clear the color buffer to show the ClearColor we called above...
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//
//        // set rotation
//        gl.glRotatef(_xAngle, 1f, 0f, 0f);
//        gl.glRotatef(_yAngle, 0f, 1f, 0f);
//
//        // gl.glColor4f(0.5f, 0f, 0f, 0.5f);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
//        gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
//        gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
    }

    private void initTriangle() {
        float[] coords = {
                -0.5f, -0.5f, 0.5f, // 0
                0.5f, -0.5f, 0.5f, // 1
                0f, -0.5f, -0.5f, // 2
                0f, 0.5f, 0f, // 3
        };
        _nrOfVertices = coords.length;

        float[] colors = {
                1f, 0f, 0f, 1f, // point 0 red
                0f, 1f, 0f, 1f, // point 1 green
                0f, 0f, 1f, 1f, // point 2 blue
                1f, 1f, 1f, 1f, // point 3 white
        };

        short[] indices = new short[] {
                0, 1, 3, // rwg
                0, 2, 1, // rbg
                0, 3, 2, // rbw
                1, 2, 3, // bwg
        };

        // float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(_nrOfVertices * 3 * 4);
        vbb.order(ByteOrder.nativeOrder());
        _vertexBuffer = vbb.asFloatBuffer();

        // short has 2 bytes
        ByteBuffer ibb = ByteBuffer.allocateDirect(_nrOfVertices * 2);
        ibb.order(ByteOrder.nativeOrder());
        _indexBuffer = ibb.asShortBuffer();

        // float has 4 bytes, 4 colors (RGBA) * number of vertices * 4 bytes
        ByteBuffer cbb = ByteBuffer.allocateDirect(4 * _nrOfVertices * 4);
        cbb.order(ByteOrder.nativeOrder());
        _colorBuffer = cbb.asFloatBuffer();

        _vertexBuffer.put(coords);
        _indexBuffer.put(indices);
        _colorBuffer.put(colors);

        _vertexBuffer.position(0);
        _indexBuffer.position(0);
        _colorBuffer.position(0);
    }

    public void setXAngle(float angle) {
        _xAngle = angle;
    }

    public float getXAngle() {
        return _xAngle;
    }

    public void setYAngle(float angle) {
        _yAngle = angle;
    }

    public float getYAngle() {
        return _yAngle;
    }

    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            x = event.getX();
            y = event.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            final float x_delta = x - event.getX();
            final float y_delta = y - event.getY();
            vortexView.queueEvent(new Runnable() {
                public void run() {
                    setXAngle(getXAngle() + x_delta);
                    setYAngle(getYAngle() + y_delta);
                }
            });
            x = event.getX();
            y = event.getY();
            break;
        case MotionEvent.ACTION_UP:

            break;
        default:
            break;
        }
        return true;
    }

}
