package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import java.util.LinkedList;



public class MyGdxGame extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;

	World world;
	LinkedList <TigetObject> renderQueue;

	float SCREEN_WIDTH;
	float SCREEN_HEIGHT;
	float DELTA_TIME;



	float cameraZoom = 0.2f;

	Stage UI;

	Player mPlayer;
	Platform platform;

	Button mLeftButton;
	Button mRightButton;
	Button mUpButton;


	@Override
	public void create () {
		UI = new Stage();
		world = new World(new Vector2(0, 9.8f), true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		DELTA_TIME = Gdx.graphics.getDeltaTime();
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();


		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = cameraZoom;
		try{
			camera.position.set(Player.getBody().getPosition(), camera.position.z);
		} catch (Exception e) {}

		camera.update();

		renderQueue = new LinkedList<TigetObject>();

		mPlayer = new Player(world, 300 * cameraZoom, 200 * cameraZoom, 75 * cameraZoom);
		renderQueue.push(mPlayer);
		renderQueue.push(new Platform(world, 300 * cameraZoom, 600 * cameraZoom, 200, 100 * cameraZoom));








		mLeftButton = new Button(new Texture("left.png"), 0* cameraZoom,  0 * cameraZoom, 200 * cameraZoom, 200 * cameraZoom);
		mRightButton = new Button(new Texture("right.png"), 1000 * cameraZoom, 0 * cameraZoom, 64 * cameraZoom, 64 * cameraZoom);
		mUpButton = new Button(new Texture("up.png"), 2000 * cameraZoom, 0 * cameraZoom, 64 * cameraZoom, 64 * cameraZoom);

		//leftButton.setBounds(200, 200, 32,32);
		UI.addActor(mLeftButton);
		UI.addActor(mRightButton);
		UI.addActor(mUpButton);



		mLeftButton.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				super.touchDown(event, x, y, pointer, button);
				mPlayer.move(Player.Direction.LEFT);
				//
			}
		});

		mRightButton.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				super.touchDown(event, x, y, pointer, button);
				mPlayer.move(Player.Direction.RIGHT);
				//
			}
		});

		mUpButton.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				super.touchDown(event, x, y, pointer, button);
				//
			}
		});

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(1/4f, 6, 2);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (TigetObject gameObject : renderQueue)
			gameObject.render(batch);

		batch.end();

		UI.draw();
		//renderer.render(world, camera.combined);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(true, width, height);
	}
}