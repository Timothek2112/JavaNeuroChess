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

public class King extends Figure{
    public King(Side side, Vector2 boardPos, Cell cell, GameScreen gameScreen, GameController controller) {
        super(side, boardPos, cell, gameScreen, controller);
        type = Figures.king;
        if(gameScreen == null) return;
        if(side == Side.white){
            sprite = new Texture(Gdx.files.internal("figures/white_king.png"));
        }else{
            sprite = new Texture(Gdx.files.internal("figures/black_king.png"));
        }
    }

    @Override
    public ArrayList<Cell> getValidMoves(Board board, boolean ignoreCheck) {
        int x = cell.x;
        int y = cell.y;
        ArrayList<Cell> validMoves = new ArrayList<>();
        if(controller.sideMove != side) return new ArrayList<>();

        if(board.isIndexExists(y + up)){
            Cell cur = board.board[y + up][x];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(x + right)){
            Cell cur = board.board[y][x + right];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(x + left)){
            Cell cur = board.board[y][x + left];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(x + right) && board.isIndexExists(y + up)){
            Cell cur = board.board[y + up][x + right];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(x + left) && board.isIndexExists(y + up)){
            Cell cur = board.board[y + up][x + left];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(x + right) && board.isIndexExists(y + down)){
            Cell cur = board.board[y + down][x + right];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side) validMoves.add(cur);
        }
        if(board.isIndexExists(x + left) && board.isIndexExists(y + down)){
            Cell cur = board.board[y + down][x + left];
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
