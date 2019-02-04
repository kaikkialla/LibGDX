package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.Player.Direction.LEFT;
import static com.mygdx.game.Player.Direction.NONE;
import static com.mygdx.game.Player.Direction.RIGHT;

public class Player extends TigetObject{
    World world;

    float x;
    float y;
    float radius;

    Sprite sprite;
    Texture texture;

    BodyDef bodyDef;
    static Body body;

    float PLAYER_IMPULSE = 10;

    public Direction direction = NONE;



    Player(World world, float x, float y, float radius) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.radius = radius;

        texture = new Texture("chungus.png");
        sprite = new Sprite(texture);
        sprite.flip(false, true);


        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        body = world.createBody(bodyDef);
        body.createFixture(shape, 2f);
    }

    public static Body getBody() {
        return body;
    }

    public void render(Batch batch) {
        update();

        sprite.setPosition(body.getPosition().x - radius, body.getPosition().y - radius);
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOriginCenter();
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.draw(batch);
    }

    private void update() {
        if(direction == LEFT) {
            body.applyLinearImpulse(new Vector2(-PLAYER_IMPULSE, 0), body.getWorldCenter(), true);
        } else if(direction == RIGHT) {
            body.applyLinearImpulse(new Vector2(-PLAYER_IMPULSE, 0), body.getWorldCenter(), true);
        }
    }



    public void jump() {

    }

    public void move(Direction direction) {
        this.direction = direction;
    }

    public void stop() {
        move(NONE);
    }

    enum Direction{
        LEFT, RIGHT, NONE
    }
}
