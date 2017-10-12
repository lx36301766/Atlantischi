package com.fly.libgdx.g_seven;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class SevenGame implements ApplicationListener {

    Stage stage1;
    Stage stage2;

    int width;
    int height;

    boolean moveRight;
    boolean moveUp;
    float speed = 11;

    float prevPosX;
    float prevPosY;

    @Override
    public void create() {

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        System.out.println("width=" + width);
        System.out.println("height=" + height);

        stage1 = new Stage(width, height, true);
        stage2 = new Stage(width, height, true, stage1.getSpriteBatch());

        Image image1 = new Image(new TextureRegion(new Texture("xk.jpg"), 150, 150, width, height), Scaling.stretch,
                Align.CENTER, "bg");
        stage1.addActor(image1);

        Image image2 = new Image(new TextureRegion(new Texture("star.png")), Scaling.stretch, Align.CENTER, "star");
        image2.x = width / width + 50;
        image2.y = height / 5;
        stage2.addActor(image2);

        Image image3 = createImage("narrow.png", OBSTACLE1);
        image3.x = (width - image3.width) / 2 + 180;
        image3.y = (height - image3.height) / 2 + 280;
        
        Image image4 = createImage("narrow.png", OBSTACLE2);
        image4.x = (width - image4.width) / 2 - 180;
        image4.y = (height - image4.height) / 2 - 280;
        
        Image image5 = createImage("cup.png", OBSTACLE3);
        image5.x = (width - image5.width) / 2;
        image5.y = (height - image5.height) / 2;
        
        Image image6 = createImage("narrow.png", OBSTACLE4);
        image6.x = (width - image6.width) / 2 - 180;
        image6.y = (height - image6.height) / 2 + 280;
        
        Image image7 = createImage("narrow.png", OBSTACLE5);
        image7.x = (width - image7.width) / 2 + 180;
        image7.y = (height - image7.height) / 2 - 280;
    }

    private Image createImage (String resName, String name) {
        Image image = new Image(new TextureRegion(new Texture(resName)), Scaling.stretch, Align.CENTER, name); 
        stage1.addActor(image);
        return image;
    }
    
    private final static String OBSTACLE1 = "narrow1";
    private final static String OBSTACLE2 = "narrow2";
    private final static String OBSTACLE3 = "cup";
    private final static String OBSTACLE4 = "narrow4";
    private final static String OBSTACLE5 = "narrow5";
    
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage1.act(Gdx.graphics.getDeltaTime());
        stage2.act(Gdx.graphics.getDeltaTime());
        stage1.draw();
        stage2.draw();
        moveStar();
    }

    private void moveStar() {
        Actor[] obstacles = {
                stage1.findActor(OBSTACLE1),
                stage1.findActor(OBSTACLE2),
                stage1.findActor(OBSTACLE3),
                stage1.findActor(OBSTACLE4),
                stage1.findActor(OBSTACLE5),};
        
        Actor star = stage2.findActor("star");
        
        if (checkRightCollision(star, obstacles)) {
            moveRight = false;
        } else if (checkLeftCollision(star, obstacles)) {
            moveRight = true;
        }
        if (checkUpCollision(star, obstacles)) {
            moveUp = false;
        } else if (checkDownCollision(star, obstacles)) {
            moveUp = true;
        }

        prevPosX = star.x;
        prevPosY = star.y;
        star.x = moveRight ? star.x + speed+2 : star.x - speed+2;
        star.y = moveUp    ? star.y + speed : star.y - speed;
    }

    private boolean checkRightCollision(Actor star, Actor... others) {
        boolean isCollidedBounds = star.x > width - star.width;
        boolean isCollidedActors = false;
        for (Actor other : others) {
            if (!(star.y + star.height < other.y || star.y > other.y + other.height)
             && (star.x + star.width >= other.x) && prevPosX + star.width < other.x) {
                isCollidedActors = true;
            }
        }
        return isCollidedBounds || isCollidedActors;
    }

    private boolean checkLeftCollision(Actor star, Actor... others) {
        boolean isCollidedBounds = star.x < 1;
        boolean isCollidedActors = false;
        for (Actor other : others) {
            if (!(star.y + star.height < other.y || star.y > other.y + other.height)
             && (star.x <= other.x + other.width) && prevPosX > other.x + other.width) {
                isCollidedActors = true;
            }
        }
        return isCollidedBounds || isCollidedActors;
    }
    
    private boolean checkUpCollision(Actor star, Actor... others) {
        boolean isCollidedBounds = star.y > height - star.height;
        boolean isCollidedActors = false;
        for (Actor other : others) {
            if (!(star.x + star.width < other.x || star.x > other.x + other.width)
             && star.y + star.height >= other.y && prevPosY + star.height < other.y) {
                isCollidedActors = true;
            }
        }
        return isCollidedBounds || isCollidedActors;
    }

    private boolean checkDownCollision(Actor star, Actor... others) {
        boolean isCollidedBounds = star.y < 1;
        boolean isCollidedActors = false;
        for (Actor other : others) {
            if (!(star.x + star.width < other.x || star.x > other.x + other.width)
             && star.y <= other.y + other.height && prevPosY > other.y + other.height) {
                isCollidedActors = true;
            }
        }
        return isCollidedBounds || isCollidedActors;
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
