package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	Camera camera;

    World world;
    //Box2DDebugRenderer mDebugRender = new Box2DDebugRenderer();

    LinkedList<TigetObject> RenderQueue = new LinkedList<TigetObject>();//Очередь объектов на рендер


	@Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();

        world= new World(new Vector2(0,9.8f),true) ;
		//Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        RenderQueue.push(new Platform(world, 0,0, 100, 100));

	}



	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//Отдаем команды на отрисовку, но не отрисовывает их
        camera.position.x =+ 10;
        batch.setProjectionMatrix(camera.combined);

        //batch.draw(new Texture("chungus.png"), 0,0);
		batch.end();

        world.step(1/30f, 6, 2);//Обновляем мир

        for(TigetObject object : RenderQueue) {
            object.render();
        }
        //mDebugRender.render(world, camera.combined);
	}





	@Override
	public void dispose () {
		batch.dispose();
	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        //Вызывать камеру
    }



}
