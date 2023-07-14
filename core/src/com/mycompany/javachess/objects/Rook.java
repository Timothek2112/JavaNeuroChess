package com.mycompany.javachess.objects;

import com.badlogic.gdx.Game;
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

public class Rook extends Figure{
    public Rook(Side side, Vector2 boardPos, Cell cell, GameScreen gameScreen, GameController controller) {
        super(side, boardPos, cell, gameScreen, controller);
        type = Figures.rook;
        if(gameScreen == null) return;
        if(side == Side.white){
            sprite = new Texture(Gdx.files.internal("figures/white_rook.png"));
        }else{
            sprite = new Texture(Gdx.files.internal("figures/black_rook.png"));
        }
    }

    @Override
    public ArrayList<Cell> getValidMoves(Board board, boolean ignoreCheck) {
        int x = cell.x;
        int y = cell.y;
        ArrayList<Cell> validMoves = new ArrayList<>();
        if(gameScreen != null)
            if(gameScreen.controller.sideMove != side) return new ArrayList<>();

        for(int i = y + up; board.isIndexExists(i); i += up){
            Cell cur = board.board[i][x];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side){
                validMoves.add(cur);
                break;
            }
            else break;
        }
        for(int i = y + down; board.isIndexExists(i); i += down){
            Cell cur = board.board[i][x];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side){
                validMoves.add(cur);
                break;
            }
            else break;
        }
        for(int i = x + left; board.isIndexExists(i); i += left){
            Cell cur = board.board[y][i];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side){
                validMoves.add(cur);
                break;
            }
            else break;
        }
        for(int i = x + right; board.isIndexExists(i); i += right){
            Cell cur = board.board[y][i];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side){
                validMoves.add(cur);
                break;
            }
            else break;
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
