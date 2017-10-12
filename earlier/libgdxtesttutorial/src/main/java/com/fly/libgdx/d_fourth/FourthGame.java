package com.fly.libgdx.d_fourth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.FadeIn;
import com.badlogic.gdx.scenes.scene2d.actions.FadeOut;
import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;
import com.badlogic.gdx.scenes.scene2d.actions.MoveTo;
import com.badlogic.gdx.scenes.scene2d.actions.Parallel;
import com.badlogic.gdx.scenes.scene2d.actions.Repeat;
import com.badlogic.gdx.scenes.scene2d.actions.RotateTo;
import com.badlogic.gdx.scenes.scene2d.actions.Sequence;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class FourthGame implements ApplicationListener {

    private Stage stage;
    private Texture texture;

    @Override
    public void create() {
        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        texture = new Texture("star.png");
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        float duration = 10f;
        int maxWidth = Gdx.graphics.getWidth() - texture.getWidth();
        int maxHeight = Gdx.graphics.getHeight() - texture.getHeight();

        for (int i = 0; i < 20; i++) {
            Image image = new Image(texture, Scaling.none, Align.CENTER, "star" + i);
            image.x = MathUtils.random(0, maxWidth);
            image.y = MathUtils.random(0, maxHeight);
            // 移动方向和地点随机
            Action moveAction = Sequence.$(
                    MoveTo.$(MathUtils.random(0, maxWidth), MathUtils.random(0, maxHeight), duration / 2),
                    MoveBy.$(MathUtils.random(0, maxWidth), MathUtils.random(0, maxHeight), duration / 2));
            // 旋转
            Action rotateAction = RotateTo.$(360, duration);
            // 闪烁，重复10次
            Action fadeAction = Repeat.$(Sequence.$(FadeOut.$(duration / 20), FadeIn.$(duration / 20)), (int) duration);
            image.action(Parallel.$(moveAction, rotateAction, fadeAction)); // 所有action并行

            stage.addActor(image);
        }

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();
    }

}
