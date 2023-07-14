package com.mycompany.javachess.controllers;

import com.mycompany.javachess.model.Board;
import com.mycompany.javachess.model.Side;

public class GameController {
    public boolean whiteCheck;
    public boolean blackCheck;
    public boolean whiteCheckMate;
    public boolean blackCheckMate;
    public Side sideMove = Side.white;

    public void onMove(){
        sideMove = Side.white == sideMove ? Side.black : Side.white;
    }

    public void updateIsCheck(Board board){
        whiteCheck = board.isCheck(Side.white);
        blackCheck = board.isCheck(Side.black);
    }
}
