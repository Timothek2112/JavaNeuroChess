package com.mycompany.javachess.network;

public class Layer {
    public Neuron[] neurons;
    public Layer(int size){
        neurons = new Neuron[size];
    }
}
