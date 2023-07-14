package com.mycompany.javachess.network;

import java.awt.geom.Rectangle2D;

public class IntegersPair {
    public Double a;
    public Double b;
    public IntegersPair(Double a, Double b){
        this.a = a;
        this.b = b;
    }
    public boolean contains(Double guess){
        if(a == guess || b == guess) return true;
        return false;
    }
    public int getPlace(Double guess){
        if(a == guess) return 1;
        if(b == guess) return 2;
        return -1;
    }
}
