package com.mycompany.javachess.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mycompany.javachess.controllers.GameController;
import com.mycompany.javachess.model.Board;
import com.mycompany.javachess.model.Cell;
import com.mycompany.javachess.model.Figures;
import com.mycompany.javachess.model.Side;
import com.mycompany.javachess.screen.GameScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public abstract class Figure extends Rectangle
{
    public Figures type;
    int up = 1;
    int right = 1;
    int left = -1;
    int down = -1;
    public Cell cell;
    public Vector2 boardPos;
    public Texture sprite;
    public final Side side;
    public Stack<Cell> history = new Stack<>();
    public Stack<Figure> figuresHistory = new Stack<>();
    GameScreen gameScreen;
    GameController controller;
    public ArrayList<MoveMark> moveMarks = new ArrayList<>();

    protected Figure(Side side, Vector2 boardPos, Cell cell, GameScreen gameScreen, GameController controller) {
        this.side = side;
        this.boardPos = boardPos;
        this.cell = cell;
        if(side == Side.black){
            up = -1;
            down = 1;
        }
        width = 50;
        height = 50;
        this.gameScreen = gameScreen;
        this.controller = controller;
        cell.figure = this;
    }
    public abstract ArrayList<Cell> getValidMoves(Board board, boolean ignoreCheck);

    public void move(Vector2 to, Board board, GameController controller){
        Cell toCell = board.board[(int)to.y][(int)to.x];
        Figure saveFigure = toCell.figure;
        figuresHistory.add(saveFigure);
        cell.figure = null;
        history.add(cell);
        cell = toCell;
        toCell.figure = this;
        controller.onMove();
        controller.updateIsCheck(board);
    }

    public void undoMove(){
        if(history.empty())
            return;
        Cell prevCell = history.pop();
        cell.figure = figuresHistory.pop();
        cell = prevCell;
        prevCell.figure = this;
        controller.onMove();
    }

    public void draw(SpriteBatch batch){
        batch.draw(sprite, x, y, width, height);
        for(int i = 0; i < moveMarks.size(); i++){
            moveMarks.get(i).draw(batch);
        }
    }
    public void update(float deltaTime){
        x = MathUtils.round(boardPos.x  + 61.2f * cell.x) + 10;
        y = MathUtils.round(boardPos.y + 61.2f * cell.y) + 10;
        for(int i = 0; i < moveMarks.size(); i++){
            moveMarks.get(i).update(deltaTime);
        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            gameScreen.camera.unproject(touchPos);
            if(getBounds().contains(touchPos.x, touchPos.y)){
                ArrayList<Cell> validMoves = this.getValidMoves(gameScreen.board, false);
                for(Cell cell : validMoves){
                    MoveMark metka = new MoveMark(new Texture(Gdx.files.internal("bg.png")), this, gameScreen, cell.x, cell.y);
                    metka.x = MathUtils.round(boardPos.x  + 61.2f * cell.x) + 10;
                    metka.y = MathUtils.round(boardPos.y + 61.2f * cell.y) + 10;
                    metka.width = 50;
                    metka.height = 50;
                    metka.sprite.setAlpha(0.1f);
                    moveMarks.add(metka);
                }
            }else{
                moveMarks.clear();
            }
        }
    }

}
