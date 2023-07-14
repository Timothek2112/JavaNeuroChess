package com.mycompany.javachess.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mycompany.javachess.config.GameConfig;
import com.mycompany.javachess.controllers.GameController;
import com.mycompany.javachess.model.Board;
import com.mycompany.javachess.model.Cell;
import com.mycompany.javachess.model.Figures;
import com.mycompany.javachess.model.Side;
import com.mycompany.javachess.screen.GameScreen;

import java.util.ArrayList;

public class Pawn extends Figure
{
    Boolean isFirstMove = true;
    public Pawn(Side side, Vector2 boardPos, Cell cell, GameScreen gameScreen, GameController controller){
        super(side, boardPos, cell, gameScreen, controller);
        type = Figures.pawn;
        if(gameScreen == null) return;
        if(side == Side.white){
            sprite = new Texture(Gdx.files.internal("figures/white_pawn.png"));
        }else{
            sprite = new Texture(Gdx.files.internal("figures/black_pawn.png"));
        }
    }

    @Override
    public void move(Vector2 to, Board board, GameController gameController){
        super.move(to, board, gameController);
        isFirstMove = false;
    }

    @Override
    public ArrayList<Cell> getValidMoves(Board board, boolean ignoreCheck) {
        int x = cell.x;
        int y = cell.y;
        ArrayList<Cell> validMoves = new ArrayList<>();
        if(gameScreen != null)
            if(gameScreen.controller.sideMove != side) return new ArrayList<>();

        if(board.isIndexExists(y + up)){
            if(board.board[y + up][x].figure == null){
                validMoves.add(board.board[y + up][x]);
            }
        }
        if(isFirstMove){
            if(board.isIndexExists(y + 2 * up)){
                if(board.board[y + 2 * up][x].figure == null){
                    validMoves.add(board.board[y + 2 * up][x]);
                }
            }
        }
        if(board.isIndexExists(y + up) && board.isIndexExists(x + left)){
            if(board.board[y + up][x + left].figure != null){
                if(board.board[y + up][x + left].figure.side != this.side)
                    validMoves.add(board.board[y + up][x + left]);
            }
        }
        if(board.isIndexExists(y + up) && board.isIndexExists(x + right)){
            if(board.board[y + up][x + right].figure != null){
                if(board.board[y + up][x + right].figure.side != this.side)
                    validMoves.add(board.board[y + up][x + right]);
            }
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
