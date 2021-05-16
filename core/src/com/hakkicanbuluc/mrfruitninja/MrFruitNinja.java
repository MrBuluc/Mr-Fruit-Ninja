package com.hakkicanbuluc.mrfruitninja;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

public class MrFruitNinja extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;

	Texture background;
	Texture bomb, health, strawberry, watermelon;

	BitmapFont font, playFont;
	FreeTypeFontGenerator fontGenerator;

	int lives = 3, score = 0;

	private double currentTime, gameOverTime = -1f;

	Random random = new Random();

	Array<Fruit> fruitArray = new Array<Fruit>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("ninjabackground.png");
		bomb = new Texture("bomb.png");
		health = new Texture("health.png");
		strawberry = new Texture("strawberry.png");
		watermelon = new Texture("watermelon.png");

		Gdx.input.setInputProcessor(this);

		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("caveat.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.color = Color.RED;
		parameter.size = 80;
		font = fontGenerator.generateFont(parameter);
		parameter.size = 100;
		parameter.color = Color.FIREBRICK;
		playFont = fontGenerator.generateFont(parameter);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		Fruit.radius = Math.max(height, width) / 20f;
	}

	@Override
	public void render () {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		batch.begin();
		batch.draw(background, 0, 0, width, height);

		double newTime = TimeUtils.millis() / 1000f;
		double frameTime = Math.min(newTime - currentTime, 0.3);

		float deltaTime = (float) frameTime;

		currentTime = newTime;

		addItem();

		if (lives <= 0 && gameOverTime == 0f) {
			gameOverTime = currentTime;
		}

		if (lives > 0) {
			for (int i = 0; i < lives; i++) {
				batch.draw(health, i * 60f + 20f, height - 60f, 50f, 50f);
			}

			for (Fruit fruit : fruitArray) {
				fruit.update(deltaTime);

				switch (fruit.type) {
					case REGULAR:
						batch.draw(strawberry, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case EXTRA:
						batch.draw(watermelon, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case ENEMY:
						batch.draw(bomb, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case LIFE:
						batch.draw(health, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
				}
			}
		}
		font.draw(batch, "Score: 0", 30, 80);
		playFont.draw(batch, "Cut to Play", width * 0.5f, height * 0.5f);
		batch.end();
	}

	private void addItem() {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		float pos = random.nextFloat() * Math.max(width, height);
		Fruit item = new Fruit(new Vector2(pos, -Fruit.radius), new Vector2(pos, pos));

		float type = random.nextFloat();
		if (type > 0.98) {
			item.type = Fruit.Type.LIFE;
		} else if (type > 0.88) {
			item.type = Fruit.Type.EXTRA;
		} else if (type > 0.78) {
			item.type = Fruit.Type.ENEMY;
		}

		fruitArray.add(item);
	}
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		fontGenerator.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
