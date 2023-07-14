package com.mycompany.javachess.model;

import com.mycompany.javachess.objects.Figure;

public class Cell {
    public Figure figure;
    public int x;
    public int y;

    public Cell(Figure figure, int x, int y){
        this.x = x;
        this.y = y;
    }

    public Boolean isEmpty(){
        return figure == null;
    }
}
