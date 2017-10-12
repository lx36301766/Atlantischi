package com.fly.opengl.gl2.square;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {

    public Square() {
        init1();
    }

    FloatBuffer vertexBuffer;
    FloatBuffer vertexBuffer2;
    ShortBuffer indexBuffer;

    short[] indices = { 0, 1, 2, 0, 2, 3 };

    private void init1() {
        float vertices[] = {
                -1.0f, 1.0f, 0.0f, //
                -1.0f, -1.0f, 0.0f, //
                1.0f, -1.0f, 0.0f, //
                1.0f, 1.0f, 0.0f, //
        };
        float[] vertices1 = new float[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices1[i] = vertices[i] - 0;
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices1.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices1);
        vertexBuffer.position(0);
        
        float[] vertices2 = new float[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices2[i] = vertices[i] + 1;
        }
        ByteBuffer vbb2 = ByteBuffer.allocateDirect(vertices2.length * 4);
        vbb2.order(ByteOrder.nativeOrder());
        vertexBuffer2 = vbb2.asFloatBuffer();
        vertexBuffer2.put(vertices2);
        vertexBuffer2.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); // OpenGL docs.
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
        
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer2); // OpenGL docs.
//        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
        
        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
    }

}
