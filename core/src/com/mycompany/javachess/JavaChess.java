package com.mycompany.javachess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mycompany.javachess.screen.MainMenu;

public class JavaChess extends Game {

	public SpriteBatch batch;
	public BitmapFont bitmapFont;

	@Override
	public void create () {
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bitmapFont.dispose();
	}
}
