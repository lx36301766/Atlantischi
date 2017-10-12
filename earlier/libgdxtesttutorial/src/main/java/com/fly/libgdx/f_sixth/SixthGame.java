package com.fly.libgdx.f_sixth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.*;

public class SixthGame implements ApplicationListener {

    protected OrthographicCamera camera;
    protected Box2DDebugRenderer renderer; // 测试用绘制器
    protected World world;

    float width;
    float height;

    @Override
    public void create() {

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        int sclae = 5;
        camera = new OrthographicCamera(32 * sclae, 32 * sclae);
        camera.position.set(0, 0, 0);
        renderer = new Box2DDebugRenderer();

        Shape c = new CircleShape();// 创建一个形状（圆）
        c.setRadius(3f); // 设置半径

        world = new World(new Vector2(0, -9.8f), true); // 一般标准重力场
        
        BodyDef bd = new BodyDef(); // 声明物体定义
        bd.position.set(2f, 10f);
        bd.type = BodyType.StaticBody;
        Body b = world.createBody(bd); // 通过world创建一个物体
        b.setUserData(null);
        b.createFixture(c, 1f); // 将形状和密度赋给物体
        
        BodyDef bd2 = new BodyDef();
        bd2.position.set(5f, 10f);
        bd2.type = BodyType.DynamicBody;
        Body b2 = world.createBody(bd2); // 通过world创建一个物体
        b2.setUserData(null);
        b2.createFixture(c, 1f); // 将形状和密度赋给物体
        
        PolygonShape polygonShape = null;
        polygonShape.setAsBox(2f, 3f);
        
        JointDef jd = new FrictionJointDef();
        jd.bodyA = b;
        jd.bodyB = b2;
        Joint joint = world.createJoint(jd);
        
        world.QueryAABB(new QueryCallback() {
            
            @Override
            public boolean reportFixture(Fixture fixture) {
                System.out.println(fixture);
                return false;
            }
        }, 1, 1, 5, 5);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        GL10 gl = Gdx.app.getGraphics().getGL10();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();
        camera.apply(gl);
//        camera.viewportWidth -= width / 100;
//        camera.viewportHeight -= height / 100;
//        camera.position.x -= width / 100;
//        camera.position.y -= height / 100;
        renderer.render(world, camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();

        renderer = null;
        world = null;

    }

}
