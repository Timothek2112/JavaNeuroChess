package com.mycompany.javachess.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mycompany.javachess.JavaChess;
import com.mycompany.javachess.config.GameConfig;
import com.mycompany.javachess.controllers.GameController;
import com.mycompany.javachess.model.Board;
import com.mycompany.javachess.model.Cell;
import com.mycompany.javachess.model.Side;
import com.mycompany.javachess.objects.*;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GameScreen implements Screen {
    final JavaChess game;
    public ArrayList<Figure> figures = new ArrayList<>();
    public OrthographicCamera camera;
    public GameController controller = new GameController();
    public Board board;
    public ArrayList<SimpleObject> objects = new ArrayList<>();

    public GameScreen(JavaChess game) {
        this.game = game;
        board = new Board(this);
        objects.add(board);
        board.createDefaultPosition();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConfig.width, GameConfig.height);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f,0,0,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        Iterator<SimpleObject> iterator = objects.iterator();
        for(int i = 0; i < objects.size(); i++){
            SimpleObject cur = objects.get(i);
            cur.update(delta);
            cur.draw(game.batch);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            board.createDefaultPosition();
        }
        game.batch.end();
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
