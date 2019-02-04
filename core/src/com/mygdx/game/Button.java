package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button extends Actor{

    Sprite buttonSprite;

    Button(Texture texture, float x, float y, float width, float heigth) {
        buttonSprite = new Sprite(texture);
        setBounds(x, y, width, heigth);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        buttonSprite.draw(batch);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        buttonSprite.setPosition(getX(), getY());
    }


}
