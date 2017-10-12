package com.fly.libgdx.b_second;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FirstActor extends Actor {

    Texture texture1;
    Texture texture2;
    Animation animation;
    TextureRegion[] walkFrame = new TextureRegion[2];
    float stateTime;

    public FirstActor(String name) {
        super(name);
        texture1 = new Texture("actor1.gif");
        texture2 = new Texture("actor2.gif");
        super.height = texture1.getHeight();
        super.width = texture1.getWidth();
        TextureRegion region1 = new TextureRegion(texture1);
        TextureRegion region2 = new TextureRegion(texture2);
        TextureRegion[][] textureRegions = region1.split(14, 14);
//        walkFrame = new TextureRegion[4];
//        walkFrame[0] = textureRegions[0][0];
//        walkFrame[1] = textureRegions[0][1];
//        walkFrame[2] = textureRegions[1][0];
//        walkFrame[3] = textureRegions[1][1];
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                walkFrame[i] = region1;
            } else {
                walkFrame[i] = region2;
            }
        }
        animation = new Animation(0.25f, walkFrame);
        super.x = 200;
        super.y = 100;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        System.out.println("stateTime="+stateTime);
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, super.x, super.y);
    }

    @Override
    public Actor hit(float x, float y) {
        Gdx.app.log("lx", "x="+x+" width="+width);
        return x > 0 && y > 0 && super.height > y && super.width > x ? this : null;
    }

}
