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

public class Bishop extends Figure{
    public Bishop(Side side, Vector2 boardPos, Cell cell, GameScreen gameScreen, GameController controller) {
        super(side, boardPos, cell, gameScreen, controller);
        type = Figures.bishop;
        if(gameScreen == null) return;
        if(side == Side.white){
            sprite = new Texture(Gdx.files.internal("figures/white_bishop.png"));
        }else{
            sprite = new Texture(Gdx.files.internal("figures/black_bishop.png"));
        }
    }

    @Override
    public ArrayList<Cell> getValidMoves(Board board, boolean ignoreCheck) {
        int x = cell.x;
        int y = cell.y;
        ArrayList<Cell> validMoves = new ArrayList<>();
        if(gameScreen != null)
            if(gameScreen.controller.sideMove != side) return new ArrayList<>();
        for(int i = y + up, g = x + right; board.isIndexExists(i) && board.isIndexExists(g); i += up, g += right){
            Cell cur = board.board[i][g];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side){
                validMoves.add(cur);
                break;
            }
            else break;
        }
        for(int i = y + up, g = x + left; board.isIndexExists(i) && board.isIndexExists(g); i += up, g += left){
            Cell cur = board.board[i][g];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side){
                validMoves.add(cur);
                break;
            }
            else break;
        }
        for(int i = y + down, g = x + right; board.isIndexExists(i) && board.isIndexExists(g); i += down, g += right){
            Cell cur = board.board[i][g];
            if(cur.figure == null) validMoves.add(cur);
            else if(cur.figure.side != this.side){
                validMoves.add(cur);
                break;
            }
            else break;
        }
        for(int i = y + down, g = x + left; board.isIndexExists(i) && board.isIndexExists(g); i += down, g += left){
            Cell cur = board.board[i][g];
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
