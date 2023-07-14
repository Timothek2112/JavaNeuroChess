package com.mycompany.javachess.network;

import com.badlogic.gdx.graphics.g3d.particles.influencers.ParticleControllerInfluencer;
import org.apache.commons.math3.util.Precision;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Neuron {
    public int number = (int)(Math.random() * 10000);
    public Neuron[] connectedNeurons;
    public Double[] weights;
    public Double value;
    public Neuron(Neuron[] neurons, Double[] weights){
        connectedNeurons = neurons;
        this.weights = weights;
    }

    public void createValue(){
        this.value = Double.valueOf(0);
        for(int i = 0; i < connectedNeurons.length; i++){
            this.value += connectedNeurons[i].value * weights[i];
        }
        activation(this.value);
    }

    public Double activation(Double value){
        Double result = 1 / (1 + Math.exp(value));
        this.value = result;
        return this.value;
    }

    public boolean equals(Object guess){
        if(!(guess instanceof Neuron)) return false;
        if(guess == this) return true;
        if(number == ((Neuron) guess).number) return true;
        return false;
    }

    public int hashCode(){
        return number;
    }
}
