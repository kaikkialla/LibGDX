package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends TigetObject{
    World world;
    float x;
    float y;
    float width;
    float height;

    Sprite sprite;
    Texture texture;

    BodyDef bodyDef;
    Body platform;



    Platform(World world, float x, float y, float width, float height) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if(texture != null) {
            texture = new Texture("platform.png");
            sprite = new Sprite(texture);
            sprite.flip(true, false);
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        platform = world.createBody(bodyDef);
        platform.createFixture(shape, 2f);
    }




    
    private void render(Batch batch) {
        sprite.setPosition(platform.getPosition().x, platform.getPosition().y);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        sprite.setRotation((float) Math.toDegrees(platform.getAngle()));
        sprite.draw(batch);
    }
}
