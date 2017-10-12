package com.fly.libgdx.e_fifth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Rectangle;

public class FifthGame implements ApplicationListener {

    private Camera cam;
    private Texture texture;
    private Mesh mesh;
    private Rectangle glViewport;

    float width;
    float height;

    float basePos = 128.0f;
    float ratio = 8;
    
    @Override
    public void create() {
        // 创建一个静态的，由两个三角形构成的4个顶点的矩形
        // 矩形分为3*2块。网格有3个位置参数(x,y,z)
        // 网格的位置属性有两个值
        mesh = new Mesh(true, 4, 6, new VertexAttribute(VertexAttributes.Usage.Position, 3, "attr_Position"),
                new VertexAttribute(Usage.TextureCoordinates, 2, "attr_texCoords"));
        texture = new Texture("cup.png");
        // 设置对应的边点
        // 以第一个为例-2048f，-2048f,0是位置参数，因为是二维的，所以Z值为0
        // 0,1对应顶点坐标
        float pos = basePos * ratio;
        mesh.setVertices(new float[] {
                -pos, -pos, 0, 0, 1,
                pos, -pos, 0, 1, 1,
                pos, pos, 0, 1, 0, 
                -pos, pos, 0, 0, 0 });
        // 设置索引，这个有点纠结，看文章详解
        mesh.setIndices(new short[] {
                0, 1, 2,
                2, 3, 0 });

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        int scale = 5;
        cam = new OrthographicCamera(width * scale, height * scale);
        float x = width / 2.0f;
        float y = height / 2.0f;
        cam.position.set(0, 0, 0);

        glViewport = new Rectangle(0, 0, width , height);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        GL10 gl = Gdx.graphics.getGL10();
        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);

//        cam.viewportWidth -= width / 10;
//        cam.viewportHeight -= height / 10;
//        cam.position.x -= width / 100;
//        cam.position.y -= height / 100;
        cam.update();
        cam.apply(gl);
        // 贴图
        gl.glActiveTexture(GL10.GL_TEXTURE0);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        texture.bind();

        mesh.render(GL10.GL_TRIANGLES);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
