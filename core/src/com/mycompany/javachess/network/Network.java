package com.mycompany.javachess.network;

import com.mycompany.javachess.model.Figures;
import org.apache.commons.math3.util.Precision;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Network {
    public static Map<Figures, IntegersPair> integersForFigures = new HashMap<Figures, IntegersPair>(){{
        put(Figures.pawn, new IntegersPair(1d, 7d));
        put(Figures.knight, new IntegersPair(2d, 8d));
        put(Figures.bishop, new IntegersPair(3d, 9d));
        put(Figures.rook, new IntegersPair(4d, 10d));
        put(Figures.queen, new IntegersPair(5d, 11d));
        put(Figures.king, new IntegersPair(6d, 12d));
    }};
    public final int layers_count = 200;
    public final int layer_width = 64;
    public Layer[] layers = new Layer[layers_count];

    public Network()
    {
        Layer prevLayer = new Layer(0);
        for(int i = 0; i < layers.length; i++){
            if(i == 0){
                layers[i] = new Layer(64);
                for(int g = 0; g < layers[i].neurons.length; g++){
                    layers[i].neurons[g] = new Neuron(new Neuron[0], new Double[0]);
                }
            }else if(i == layers.length - 1){
                layers[i] = new Layer(1);
                for(int g = 0; g < layers[i].neurons.length; g++){
                    Double[] weights = new Double[prevLayer.neurons.length];
                    for(int j = 0; j < prevLayer.neurons.length; j++){
                        weights[j] = -0.1 + Math.random() * 0.2;
                    }
                    layers[i].neurons[g] = new Neuron(prevLayer.neurons, weights);
                }
            }else{
                layers[i] = new Layer(layer_width);
                for(int g = 0; g < layers[i].neurons.length; g++){
                    Double[] weights = new Double[prevLayer.neurons.length];
                    for(int j = 0; j < prevLayer.neurons.length; j++){
                        weights[j] = -0.1 + Math.random() * 0.2;
                    }
                    layers[i].neurons[g] = new Neuron(prevLayer.neurons, weights);
                }
            }
            prevLayer = layers[i];
        }
    }

    public Network(Double[] geneinput)
    {
        List<Double> gene = Arrays.stream(geneinput).collect(Collectors.toList());
        layers[0] = new Layer(64);
        for(int i = 0; i < layers[0].neurons.length; i++){
            layers[0].neurons[i] = new Neuron(new Neuron[0], new Double[0]);
        }
        for(int i = 1; i < layers.length; i++){
            layers[i] = new Layer(layer_width);
            for(int g = 0; g < layer_width; g++){
                Neuron[] connectedTo = layers[i-1].neurons;
                ArrayList<Double> weightsList = new ArrayList<>();
                if(i == 1){
                    gene.stream().skip((i - 1) * layer_width).limit(64).forEach(obj -> weightsList.add(obj.doubleValue()));

                }else{
                    gene.stream().skip((i - 1) * layer_width).limit(layer_width).forEach(obj -> weightsList.add(obj.doubleValue()));

                }
                Double[] weights = new Double[weightsList.size()];
                weightsList.toArray(weights);
                layers[i].neurons[g] = new Neuron(connectedTo, weights);
            }
        }
    }

    public Double createOutput(Double[] input){
        for(int i = 0; i < input.length; i++){
            layers[0].neurons[i].value = input[i];
        }
        for(int i = 1; i < layers.length; i++){
            for(int g = 0; g < layers[i].neurons.length; g++){
                layers[i].neurons[g].createValue();
            }
        }
        Double result = layers[layers.length - 1].neurons[0].value;
        //System.out.println(result);
        return result;
    }

    public Double[] getGene(){
        ArrayList<Double> gene = new ArrayList<>();
        for(Layer l : layers){
            for(Neuron n : l.neurons){
                for(Double weight : n.weights){
                    gene.add(weight);
                }
            }
        }
        Double[] geneArray = new Double[gene.size()];
        gene.toArray(geneArray);
        return geneArray;
    }
}

