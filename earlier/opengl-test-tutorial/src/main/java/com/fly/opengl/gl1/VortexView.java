package com.fly.opengl.gl1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public class VortexView extends GLSurfaceView implements Renderer {

    private float _red = 0.4f;
    private float _green = 0.8f;
    private float _blue = 0.6f;
    private float _alpha = 0.1f;
    
    private VortexRenderer renderer;

    public VortexView(Context context) {
        super(context);
        renderer = new VortexRenderer(this);
        setRenderer(renderer);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置OpenGL使用vertex数组来画。
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        initTriangle2();
        //initStaticTriangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(_red, _green, _blue, _alpha);
        // reset the matrix - good to fix the rotation to a static angle
        //gl.glLoadIdentity();
        // clear the color buffer to show the ClearColor we called above...
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // set rotation
        gl.glRotatef(_angle, 0.0f, 1f, 0f);
        // 设置三角形为暗红色
//        gl.glColor4f(0.5f, 0f, 0.0f, 0.5f);
        
        // 初始化Vertex Pointer.
        // 第一个参数是大小，也是顶点的维数。我们使用的是x,y,z三维坐标。
        // 第二个参数，GL_FLOAT定义buffer中使用的数据类型。
        // 第三个变量是0，是因为我们的坐标是在数组中紧凑的排列的，没有使用offset。
        // 第四个参数顶点缓冲。
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
        
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
        
        // 第一个参数定义了什么样的图元将被画出来。
        // 第二个参数定义有多少个元素，
        // 第三个是indices使用的数据类型。
        // 最后一个是绘制顶点使用的索引缓冲。
        gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
        // */

        /*
        // draw the static triangle
        // gl.glRotatef(_angle, 1f, 1f, 0f);
        gl.glColor4f(0f, 0.5f, 0f, 0.5f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBufferStatic);
        gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices, GL10.GL_UNSIGNED_SHORT, _indexBufferStatic);

        // set rotation for the non-static triangle
        gl.glRotatef(_angle, 0f, 1f, 0f);

        gl.glColor4f(0.5f, 0f, 0f, 0.5f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
//         */

    }

    private void initTriangle() {
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

        float[] coords = {
                -0.5f, -0.5f, 0f, // (x1, y1, z1)
                0.5f, -0.5f, 0f, // (x2, y2, z2)
                0.5f, 0.5f, 0f // (x3, y3, z3)
        };
        
        float[] colors = {
                0f, 0f, 1f, 1f, // point 1
                0f, 1f, 0f, 1f, // point 2
                1f, 0f, 0f, 1f, // point 3
        };

        _vertexBuffer.put(coords);
        _indexBuffer.put(_indicesArray);
        _colorBuffer.put(colors);

        _vertexBuffer.position(0);
        _indexBuffer.position(0);
        _colorBuffer.position(0);
    }
    private void initTriangle2() {
        float[] coords = {
                // coodinates
        };
        _nrOfVertices = coords.length;

        float[] colors = {
                // colors
        };

        short[] indices = new short[] {
                // indices
        };

        // float has 4 bytes, coordinate * 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(coords.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        _vertexBuffer = vbb.asFloatBuffer();

        // short has 2 bytes, indices * 2 bytes
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        _indexBuffer = ibb.asShortBuffer();

        // float has 4 bytes, colors (RGBA) * 4 bytes
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        _colorBuffer = cbb.asFloatBuffer();

        _vertexBuffer.put(coords);
        _indexBuffer.put(indices);
        _colorBuffer.put(colors);

        _vertexBuffer.position(0);
        _indexBuffer.position(0);
        _colorBuffer.position(0);

    }
    
    private void initStaticTriangle() {
        // float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(_nrOfVertices * 3 * 4);
        vbb.order(ByteOrder.nativeOrder());
        _vertexBufferStatic = vbb.asFloatBuffer();

        // short has 2 bytes
        ByteBuffer ibb = ByteBuffer.allocateDirect(_nrOfVertices * 2);
        ibb.order(ByteOrder.nativeOrder());
        _indexBufferStatic = ibb.asShortBuffer();

        float[] coords = { -0.4f, -0.4f, 0f, // (x1, y1, z1)
                0.4f, -0.4f, 0f, // (x2, y2, z2)
                0f, 0.4f, 0f // (x3, y3, z3)
        };

        _vertexBufferStatic.put(coords);
        _indexBufferStatic.put(_indicesArray);

        _vertexBufferStatic.position(0);
        _indexBufferStatic.position(0);
    }

    public void setColor(float r, float g, float b) {
        _red = r;
        _green = g;
        _blue = b;
    }

    public void setAngle(float _angle) {
        this._angle = _angle;
    }

    public boolean onTouchEvent(final MotionEvent event) {
//        queueEvent(new Runnable() {
//            public void run() {
//                setColor(event.getX() / getWidth(), event.getY() / getHeight(), (event.getX() + event.getY())
//                        / (getWidth() + getHeight()));
//                setAngle(event.getX() / 10);
//            }
//        });
        return renderer.onTouchEvent(event);
    }

    private float _angle;

    private FloatBuffer _colorBuffer;
    
    // a raw buffer to hold indices
    private ShortBuffer _indexBuffer;
    private ShortBuffer _indexBufferStatic;

    // a raw buffer to hold the vertices
    private FloatBuffer _vertexBuffer;
    private FloatBuffer _vertexBufferStatic;

    private short[] _indicesArray = { 0, 1, 2 };
    private int _nrOfVertices = 3;

}
