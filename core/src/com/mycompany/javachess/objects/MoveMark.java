package com.mycompany.javachess.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mycompany.javachess.screen.GameScreen;

public class MoveMark extends SimpleObject{
    Figure owner;
    GameScreen gameScreen;
    int boardX;
    int boardY;
    public MoveMark(Texture tex, Figure owner, GameScreen gameScreen, int boardX, int boardY) {
        super(tex);
        this.owner = owner;
        this.gameScreen = gameScreen;
        this.boardX = boardX;
        this.boardY = boardY;
    }

    @Override
    public void update(float delta){
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameScreen.camera.unproject(touchPos);
            if(getBounds().contains(touchPos.x, touchPos.y)){
                owner.move(new Vector2(boardX, boardY), gameScreen.board, gameScreen.controller);
            }
        }
    }
}
