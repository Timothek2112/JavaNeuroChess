package com.mycompany.javachess.network;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.mycompany.javachess.controllers.GameController;
import com.mycompany.javachess.model.Board;
import com.mycompany.javachess.model.Cell;
import com.mycompany.javachess.model.Side;
import com.mycompany.javachess.objects.Figure;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.Arrays;

public class NeyroParty {
    public Network white;
    public Network black;
    public Board board;
    GameController controller = new GameController();
    public Network winner;
    public Network looser;
    public int movesBeforeWin = 0;
    private Cell[] move;

    public NeyroParty(){
        white = new Network();
        black = new Network();
        board = new Board(controller);
        board.createDefaultPosition();
    }

    public NeyroParty(Double[] gene1, Double[] gene2){
        white = new Network(gene1);
        black = new Network(gene2);
        board = new Board(controller);
        board.createDefaultPosition();
    }

    public Network playGame(){
        while(winner == null){
            makeMove();
        }
        return winner;
    }



    public void makeMove(){
        Side moving = controller.sideMove;
        Double rate = 0d;
        ArrayList<Cell[]> allMoves = board.getAllValidMoves(controller.sideMove);
        ArrayList<Double> rates = new ArrayList<>();
        for (int i = 0; i < allMoves.size(); i++) {
            Cell[] move = allMoves.get(i);
            Figure figure = move[0].figure;
            figure.move(new Vector2(move[1].x, move[1].y), board, controller);
            if (moving == Side.white) {
                rate = white.createOutput(board.getNumericBoard(Side.white));
            } else {
                rate = black.createOutput(board.getNumericBoard(Side.black));
            }
            rates.add(rate);
            if(rate < 0.5){
                figure.undoMove();
            }else{
                break;
            }
        }
        if(rate < 0.5){
            int randomMove =  (int)(0 + Math.random() * allMoves.size());
            allMoves.get(randomMove)[0].figure.move(new Vector2(allMoves.get(randomMove)[1].x, allMoves.get(randomMove)[1].y), board, controller);
        }
        movesBeforeWin++;
        if(controller.blackCheck){
            win(white);
        }else if(controller.whiteCheck){
            win(black);
        }
//        System.out.println(moving);
//        board.printBoard();
//        System.out.println();
//        System.out.println();
    }

    public void win(Network net){
        System.out.println(movesBeforeWin);
        System.out.println("WIN");
        if(white == net){
            winner = white;
            looser = black;
        }else{
            winner = black;
            looser = white;
        }
    }
}
