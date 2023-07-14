package com.mycompany.javachess.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mycompany.javachess.config.GameConfig;
import com.mycompany.javachess.controllers.GameController;
import com.mycompany.javachess.network.IntegersPair;
import com.mycompany.javachess.network.Network;
import com.mycompany.javachess.objects.*;
import com.mycompany.javachess.screen.GameScreen;

import java.util.ArrayList;
import java.lang.*;

public class Board extends SimpleObject {
    public Cell[][] board = new Cell[8][8];
    GameScreen gameScreen;
    GameController controller;

    public Board(GameController controller){
        super(null);
        setDefaultSettings();
        this.controller = controller;
    }

    public Board(GameScreen gameScreen){
        super(new Texture(Gdx.files.internal("board.png")));
        this.gameScreen = gameScreen;
        this.controller = gameScreen.controller;
        setDefaultSettings();
    }

    private void setDefaultSettings(){
        width = 500;
        height = 500;
        x = GameConfig.width / 2 - width / 2;
        y = GameConfig.height / 2 - height / 2;
        for(int i = 0; i < 8; i++){
            for(int g = 0; g < 8; g++){
                board[i][g] = new Cell(null, g, i);
            }
        }
    }

    public void createDefaultPosition(){
        clearBoard();

        new Rook(Side.black, new Vector2(x, y), board[7][0], gameScreen, controller);
        new Rook(Side.black, new Vector2(x, y), board[7][7], gameScreen, controller);
        new Knight(Side.black, new Vector2(x, y), board[7][1], gameScreen, controller);
        new Knight(Side.black, new Vector2(x, y), board[7][6], gameScreen, controller);
        new Bishop(Side.black, new Vector2(x, y), board[7][2], gameScreen, controller);
        new Bishop(Side.black, new Vector2(x, y), board[7][5], gameScreen, controller);
        new Queen(Side.black, new Vector2(x, y), board[7][3], gameScreen, controller);
        new King(Side.black, new Vector2(x, y), board[7][4], gameScreen, controller);

        for(int i = 0; i < 8; i++){
            new Pawn(Side.black, new Vector2(x, y), board[6][i], gameScreen, controller);
        }

        new Rook(Side.white, new Vector2(x, y), board[0][0], gameScreen, controller);
        new Rook(Side.white, new Vector2(x, y), board[0][7], gameScreen, controller);
        new Knight(Side.white, new Vector2(x, y), board[0][1], gameScreen, controller);
        new Knight(Side.white, new Vector2(x, y), board[0][6], gameScreen, controller);
        new Bishop(Side.white, new Vector2(x, y), board[0][2], gameScreen, controller);
        new Bishop(Side.white, new Vector2(x, y), board[0][5], gameScreen, controller);
        new Queen(Side.white, new Vector2(x, y), board[0][3], gameScreen, controller);
        new King(Side.white, new Vector2(x, y), board[0][4], gameScreen, controller);
        for(int i = 0; i < 8; i++){
            new Pawn(Side.white, new Vector2(x, y), board[1][i], gameScreen, controller);
        }
    }

    public Boolean isEnemyForMe(Vector2 pos, Side side){
        if(board[(int)pos.y][(int)pos.x] == null)
            return false;
        if(board[(int)pos.y][(int)pos.x].figure.side != side){
            return true;
        }
        return false;
    }

    public void clearBoard(){
        for(int i = 0; i < 8; i++){
            for(int g = 0; g < 8; g++){
                board[i][g] = new Cell(null, g, i);
            }
        }
    }

    public Boolean isIndexExists(int index){
        return index >= 0 && index < 8;
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        for(Cell[] line : board){
            for(Cell cell : line){
                if(!cell.isEmpty()) cell.figure.draw(batch);
            }
        }
    }

    @Override
    public void update(float delta){
        super.update(delta);
        for(Cell[] line : board){
            for(Cell cell : line){
                if(!cell.isEmpty()) cell.figure.update(delta);
            }
        }
    }

    public Cell getKingCell(Side side){
        for(Cell[] line : board){
            for(Cell cell : line){
                if(!cell.isEmpty()){
                    if(cell.figure instanceof King && cell.figure.side == side){
                        return cell;
                    }
                }
            }
        }
        return null;
    }

    public boolean isCheck(Side to){
        for(Cell[] line : board){
            for(Cell cell : line){
                if(cell.isEmpty()) continue;
                if(cell.figure.side == to) continue;
                ArrayList<Cell> validMoves = cell.figure.getValidMoves(this, true);
                if(validMoves.contains(getKingCell(to))){
                    return true;
                }
            }
        }
        return false;
    }

    public void importNumericBoard(java.lang.Double[] numericBoard, Side lessNums){
        clearBoard();
        Side maxNums;
        if(lessNums == Side.white) maxNums = Side.black;
        else maxNums = Side.white;
        for(int i = 0; i < numericBoard.length; i++){
            java.lang.Double cur = numericBoard[i];
            for(Figures figure : Network.integersForFigures.keySet()){
                IntegersPair current = Network.integersForFigures.get(figure);
                if(current.contains(cur)){
                    int place = current.getPlace(cur);
                    switch(figure){
                        case bishop:
                            board[i / 8][i % 8].figure = new Bishop(place == 1 ? lessNums : maxNums, new Vector2(i / 8, i % 8), board[i / 8][i % 8], null, controller);
                            break;
                        case pawn:
                            board[i / 8][i % 8].figure = new Pawn(place == 1 ? lessNums : maxNums, new Vector2(i / 8, i % 8), board[i / 8][i % 8], null, controller);
                            break;
                        case queen:
                            board[i / 8][i % 8].figure = new Queen(place == 1 ? lessNums : maxNums, new Vector2(i / 8, i % 8), board[i / 8][i % 8], null, controller);
                            break;
                        case king:
                            board[i / 8][i % 8].figure = new King(place == 1 ? lessNums : maxNums, new Vector2(i / 8, i % 8), board[i / 8][i % 8], null, controller);
                            break;
                        case knight:
                            board[i / 8][i % 8].figure = new Knight(place == 1 ? lessNums : maxNums, new Vector2(i / 8, i % 8), board[i / 8][i % 8], null, controller);
                            break;
                        case rook:
                            board[i / 8][i % 8].figure = new Rook(place == 1 ? lessNums : maxNums, new Vector2(i / 8, i % 8), board[i / 8][i % 8], null, controller);
                            break;
                    }

                }

            }
        }

    }

    public java.lang.Double[] getNumericBoard(Side relativeTo){
        java.lang.Double[] networkBoard = new java.lang.Double[64];
        for(int i = 0; i < board.length; i++){
            for(int g = 0; g < board[0].length; g++){
                Cell cell = board[i][g];
                if(cell.isEmpty()){
                    networkBoard[i * 8 + g] = 0d;
                    continue;
                }
                IntegersPair integerForFigure = Network.integersForFigures.get(cell.figure.type);
                if(cell.figure.side == relativeTo){
                    networkBoard[i * 8 + g] = integerForFigure.a;
                }else{
                    networkBoard[i * 8 + g] = integerForFigure.b;
                }
            }
        }
        return networkBoard;
    }

    public ArrayList<Cell[]> getAllValidMoves(Side side){
        ArrayList<Cell[]> allMoves = new ArrayList<>();
        for(Cell[] line : board){
            for(Cell cell : line){
                if(cell.isEmpty()) continue;
                if(cell.figure.side != side) continue;
                ArrayList<Cell> validMoves = cell.figure.getValidMoves(this, false);
                for(Cell move : validMoves){
                    Cell[] movePair = new Cell[2];
                    movePair[0] = cell;
                    movePair[1] = move;
                    allMoves.add(movePair);
                }
            }
        }
        return allMoves;
    }

    public void printBoard(){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_WHITE = "\u001B[37m";

        for(Cell[] line : board){
            for(Cell cell : line){
                if(cell.isEmpty()){
                    System.out.print("_");
                    continue;
                }
                if(cell.figure.side == Side.white) System.out.print(ANSI_WHITE);
                else System.out.print(ANSI_BLACK);
                if(cell.figure.type == Figures.knight)
                    System.out.print("n");
                else
                    System.out.print(cell.figure.type.toString().charAt(0));
            }
            System.out.print("\n" + ANSI_RESET);
        }
    }
}
