package com.mycompany.javachess.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mycompany.javachess.JavaChess;
import com.mycompany.javachess.config.GameConfig;

public class MainMenu implements Screen {
    final JavaChess game;
    private OrthographicCamera camera;

    public MainMenu(JavaChess game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 900, 600);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.bitmapFont.draw(game.batch, "Welcome to JavaChess!", GameConfig.width / 2 - 100, GameConfig.height - 100);
        game.bitmapFont.draw(game.batch, "Press any key to continue...", GameConfig.width / 2 - 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
