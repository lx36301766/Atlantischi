package com.fly.libgdx.a_first;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FirstGame implements ApplicationListener {

    private SpriteBatch batch;

    private Texture texture;

    private TextureRegion region;

    private Sprite sprite;

    BitmapFont bitmapFont;

    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture("sun.png");
        // region = new TextureRegion(texture, 400, 100, 300, 300);
        sprite = new Sprite(texture, 200, 100, 500, 500);
        sprite.setPosition(110, 400);
        sprite.setRotation(45);
        sprite.setColor(0.2f, 0.7f, 0.4f, 0.8f);
        bitmapFont = new BitmapFont(Gdx.files.internal("libgdx_font.fnt"), Gdx.files.internal("libgdx_font.png"), false);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // batch.draw(region, 50, 250);
        sprite.draw(batch);

        bitmapFont.draw(batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), 5, Gdx.graphics.getHeight() - 20);
        bitmapFont.draw(batch, "libgdx游戏开发框架", 0, Gdx.graphics.getHeight() / 20);

        batch.end();
    }

    @Override
    public void resize(int arg0, int arg1) {
    }

    @Override
    public void resume() {
    }

}
