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

	float BUTTON_WIDTH = 150;
	float BUTTON_HEIGHT = 150;

	@Override
	public void create () {

		DELTA_TIME = Gdx.graphics.getDeltaTime();
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();


		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());





		UI = new Stage();
		mLeftButton = new Button(new Texture("left.png"), 200, 200, BUTTON_WIDTH, BUTTON_HEIGHT);
		mRightButton = new Button(new Texture("right.png"), 600, 200, BUTTON_WIDTH, BUTTON_HEIGHT);
		mUpButton = new Button(new Texture("up.png"), Gdx.graphics.getWidth() - 500, 200, BUTTON_WIDTH, BUTTON_HEIGHT);

		UI.addActor(mLeftButton);
		UI.addActor(mRightButton);
		UI.addActor(mUpButton);



		mLeftButton.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				mPlayer.move(Player.Direction.LEFT);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				mPlayer.stop();
			}
		});

		mRightButton.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				mPlayer.move(Player.Direction.RIGHT);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				mPlayer.stop();
			}
		});

		Gdx.input.setInputProcessor(UI);



		world = new World(new Vector2(0, 100), true);

		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = cameraZoom;

		camera.update();

		renderQueue = new LinkedList<TigetObject>();

		mPlayer = new Player(world,
				300 * cameraZoom,
				0 * cameraZoom,
				75 * cameraZoom);
		renderQueue.push(mPlayer);


		Platform platform1 = new Platform(world,
				600 * cameraZoom,
				600 * cameraZoom,
				1200 * cameraZoom,
				100 * cameraZoom);

		Platform platform2 = new Platform(world,
				(1500 - 69) * cameraZoom,
				(600 + 142) * cameraZoom,
				600 * cameraZoom,
				100 * cameraZoom);
		platform2.body.setTransform(platform2.x, platform2.y, 0.5235987756f);



		renderQueue.push(platform1);
		renderQueue.push(platform2);


	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.set(Player.getBody().getPosition(), camera.position.z);
		camera.update();
		world.step(Gdx.graphics.getDeltaTime(), 6, 100);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//batch.draw(new Texture("bg.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


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