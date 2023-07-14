package com.mycompany.javachess.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mycompany.javachess.controllers.GameController;
import com.mycompany.javachess.model.Board;
import com.mycompany.javachess.model.Cell;
import com.mycompany.javachess.model.Figures;
import com.mycompany.javachess.model.Side;
import com.mycompany.javachess.screen.GameScreen;

import java.util.ArrayList;

public class Knight extends Figure{
    public Knight(Side side, Vector2 boardPos, Cell cell, GameScreen gameScreen, GameController controller) {
        super(side, boardPos, cell, gameScreen, controller);
        type = Figures.knight;
        if(gameScreen == null) return;
        if(side == Side.white){
            sprite = new Texture(Gdx.files.internal("figures/white_knight.png"));
        }else{
            sprite = new Texture(Gdx.files.internal("figures/black_knight.png"));
        }
    }

    @Override
    public ArrayList<Cell> getValidMoves(Board board, boolean ignoreCheck) {
        int x = cell.x;
        int y = cell.y;
        ArrayList<Cell> validMoves = new ArrayList<>();
        if(gameScreen != null)
            if(gameScreen.controller.sideMove != side) return new ArrayList<>();

        if(board.isIndexExists(y + 2 * up) && board.isIndexExists(x + right)){
            Cell cur = board.board[y + 2 * up][x + right];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(y + 2 * up) && board.isIndexExists(x + left)){
            Cell cur = board.board[y + 2 * up][x + left];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(y + 2 * down) && board.isIndexExists(x + right)){
            Cell cur = board.board[y + 2 * down][x + right];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(y + 2 * down) && board.isIndexExists(x + left)){
            Cell cur = board.board[y + 2 * down][x + left];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }

        if(board.isIndexExists(y +  up) && board.isIndexExists(x + 2 * right)){
            Cell cur = board.board[y + up][x + 2 * right];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(y + down) && board.isIndexExists(x + 2 * right)){
            Cell cur = board.board[y + down][x + 2 * right];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(y + up) && board.isIndexExists(x + 2 * left)){
            Cell cur = board.board[y + up][x + 2 * left];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(y + down) && board.isIndexExists(x + 2 * left)){
            Cell cur = board.board[y + down][x + 2 * left];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(!ignoreCheck) {
            for (int i = 0; i < validMoves.size(); i++) {
                Cell validMove = validMoves.get(i);
                move(new Vector2(validMove.x, validMove.y), board, controller);
                controller.updateIsCheck(board);
                if (controller.whiteCheck && side == Side.white || controller.blackCheck && side == Side.black) {
                    undoMove();
                    validMoves.remove(i);
                    i--;
                }else{
                    undoMove();
                }
            }
        }

        return validMoves;
    }
}
