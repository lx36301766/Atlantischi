package com.fly.opengl.gl2.square;

import javax.microedition.khronos.opengles.GL10;

public class FlatColoredSquare extends Square {

    public void draw(GL10 gl) {
        super.draw(gl);
        gl.glColor4f(0f, 0.5f, 0, 0.0f);
    }
    
}
