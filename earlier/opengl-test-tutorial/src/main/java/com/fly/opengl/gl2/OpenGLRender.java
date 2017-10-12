package com.fly.opengl.gl2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import com.fly.opengl.R;
import com.fly.opengl.gl2.mesh.Cube;
import com.fly.opengl.gl2.mesh.Group;
import com.fly.opengl.gl2.mesh.Mesh;
import com.fly.opengl.gl2.mesh.SimplePlane;
import com.fly.opengl.gl2.square.SmoothColoredSquare;
import com.fly.opengl.gl2.square.Square;

/**
 * http://disanji.net/2011/06/01/android-opengl-es-dev-tutorial-1/
 * 
 * @author sMedio
 * 
 */
public class OpenGLRender implements Renderer {

    Context context;

    Square square;

    Group root;

    public OpenGLRender(Context context) {
        this.context = context;
        Group group = new Group();
        root = group;
        addCube();
        addPlane();
    }

    private void addCube() {
        Cube cube = new Cube(1, 1, 1);
        cube.rotate(0, 45, 0);
        cube.translate(1, 1, 0);
        addMesh(cube);
    }

    private void addPlane() {
        SimplePlane plane = new SimplePlane();
        plane.rotate(45, 0, 0);
        plane.translate(0, 0, 3f);
        plane.loadBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.star));
        addMesh(plane);
    }

    public void addMesh(Mesh mesh) {
        root.add(mesh);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background color to black ( rgba ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // OpenGL docs.
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);// OpenGL docs.
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                GL10.GL_NICEST);
        square = new SmoothColoredSquare();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);// OpenGL docs.
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
        // Reset the projection matrix
        gl.glLoadIdentity();// OpenGL docs.
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
        // Reset the modelview matrix
        gl.glLoadIdentity();// OpenGL docs.
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        drawMesh(gl);
        drawSquare(gl);
    }

    float a = 1f;

    private void drawSquare(GL10 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, -10f);

        gl.glPushMatrix();
        gl.glTranslatef(2, 2, 0);
        gl.glRotatef(a++, 1, 0, 0);
        gl.glScalef(1f, 2f, 1f);
        square.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(-2, -2, 0);
        gl.glRotatef(a++, 0, 1, 0);
        gl.glScalef(1f, 1f, 1f);
        square.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0, 0, -1);
        gl.glRotatef(a++, 0, 0, 1);
        gl.glScalef(1f, 2f, 1f);
        square.draw(gl);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    private void drawMesh(GL10 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, -5.5f);
        root.draw(gl);
        gl.glPopMatrix();

//        gl.glPushMatrix();
//        gl.glTranslatef(-1, -3, -7f);
//        root.draw(gl);
//        gl.glPopMatrix();
    }

}
