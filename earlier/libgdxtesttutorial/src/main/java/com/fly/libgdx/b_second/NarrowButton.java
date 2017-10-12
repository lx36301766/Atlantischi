package com.fly.libgdx.b_second;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NarrowButton extends Actor {

    Texture texture;

    public NarrowButton(String name) {
        super(name);
        texture = new Texture("narrow.png");
        this.height = texture.getHeight();
        this.width = texture.getWidth();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw(texture, super.x, super.y);
    }

    @Override
    public Actor hit(float x, float y) {
        return x > 0 && y > 0 && super.height > y && super.width > x ? this : null;
    }

    public boolean touchDown(float x, float y, int pointer) {
        Actor actor = super.parent.findActor("renwu");
        actor.x += 10;
        return false;
    }
}
