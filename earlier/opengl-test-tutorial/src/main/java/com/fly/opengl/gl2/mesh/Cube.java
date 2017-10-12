package com.fly.opengl.gl2.mesh;

import android.util.Log;

public class Cube extends Mesh {
    
    public Cube(float width, float height, float depth) {

        width /= 2;
        height /= 2;
        depth /= 2;

        float vertices[] = {
                -width, -height, -depth, // 0
                width, -height, -depth, // 1
                width, height, -depth, // 2
                -width, height, -depth, // 3
                -width, -height, depth, // 4
                width, -height, depth, // 5
                width, height, depth, // 6
                -width, height, depth, // 7
        };

        short indices[] = {
                0, 4, 5,
                0, 5, 1,
                1, 5, 6,
                1, 6, 2,
                2, 6, 7,
                2, 7, 3,
                3, 7, 4,
                3, 4, 0,
                4, 7, 6,
                4, 6, 5,
                3, 0, 1,
                3, 1, 2, };

        float[] colors = {
                1f, 0f, 0f, 1f, // point 0 red
                0f, 1f, 0f, 1f, // point 1 green
                0f, 0f, 1f, 1f, // point 2 blue
                1f, 1f, 1f, 1f, // point 3 white
                
                0f, 0f, 1f, 1f, // point 2 blue
                1f, 1f, 1f, 1f, // point 3 white
                1f, 0f, 0f, 1f, // point 0 red
                0f, 1f, 0f, 1f, // point 1 green
        };

        setIndices(indices);
        setVertices(vertices);
        setColors(colors);

        Log.i("lucien", "in Cube constructor");
    }
}