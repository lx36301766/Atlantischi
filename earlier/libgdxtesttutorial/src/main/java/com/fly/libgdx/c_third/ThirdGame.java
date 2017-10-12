package com.fly.libgdx.c_third;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Delay;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class ThirdGame implements ApplicationListener {

    private Stage stage;
    private Label label;
    private Texture texture1;
    private Texture texture2;
    private Button button;
    private CheckBox checkBox;

    @Override
    public void create() {
        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("libgdx_font.fnt"),
                Gdx.files.internal("libgdx_font.png"), false);
        LabelStyle style = new LabelStyle(bitmapFont, null);
        label = new Label("fpsLabel", style, "label1");
        label.x = 5;
        label.y = Gdx.graphics.getHeight() - label.height - 5;
        stage.addActor(label);

        texture1 = new Texture("button1.png");
        NinePatch n1 = new NinePatch(texture1, 7, 7, 9, 9);
        ButtonStyle style2 = new ButtonStyle(n1, n1, n1, 0f, 0f, 0f, 0f);
        button = new Button(style2, "button");
        button.x = 30;
        button.y = 30;
        button.width = 100f;
        button.height = 32f;
        stage.addActor(button);

        texture2 = new Texture("button2.png");
        CheckBoxStyle style3 = new CheckBoxStyle(new TextureRegion(texture1), new TextureRegion(texture2), bitmapFont,
                new Color(1, 1, 1, 0.5f));
        checkBox = new CheckBox("checkbox", style3, "checkbox");
        checkBox.x = 200;
        checkBox.y = 400;
        checkBox.width = 300f;
        checkBox.height = 64f;
        checkBox.setText("Yes");
        checkBox.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                if (checkBox.isChecked()) {
                    checkBox.setText("Yes");
                } else {
                    checkBox.setText("NO");
                }
            }
        });
        stage.addActor(checkBox);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        label.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
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
        stage.dispose();
    }

}
