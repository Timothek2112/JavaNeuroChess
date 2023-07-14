package com.mycompany.javachess.network;

import com.badlogic.gdx.utils.Json;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Learn {
    public NeyroParty[] parties;
    public int generationSize = 10;
    public int maxGenerations = 10;

    public static void main(String[] args){
        new Learn().learn();
    }

    public Learn(){
        parties = new NeyroParty[generationSize];
        ArrayList<Double> savedGene = new ArrayList<>();
        File file = new File("./the-file-name.txt");
        try {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null)
            savedGene.add(Double.valueOf(st));
        Double[] gene = new Double[savedGene.size()];
        savedGene.toArray(gene);
            for(int i = 0; i < generationSize; i++){
                parties[i] = new NeyroParty(mutateGene(gene), mutateGene(gene));
            }
        } catch (Exception ex) {System.out.println(ex.getMessage());}
    }

    public void learn() {

        playAllParties();
        int generations = 0;
        while(generations < maxGenerations){
            ArrayList<Double[]> genes = new ArrayList<>();
            for(NeyroParty party : parties){
                genes.add(party.winner.getGene());
            }
            for(NeyroParty party : parties){
                genes.add(party.looser.getGene());
            }
            for(int i = 0; i < genes.size(); i+=2){
                ArrayList<Double[]> crossed = cross(genes.get(i), genes.get(i+1));
                genes.set(i, crossed.get(0));
                genes.set(i + 1, crossed.get(1));
            }
            for(int i = 0; i < genes.size(); i++){
                genes.set(i, mutateGene(genes.get(i)));
            }
            int genesIndex = 0;
            for(int i = 0; i < parties.length; i++) {
                parties[i] = new NeyroParty(genes.get(genesIndex), genes.get(genesIndex+1));
                genesIndex+=2;
            }
            playAllParties();
            generations++;
        }
        try {
            PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
            Double[] gene = parties[0].winner.getGene();
            for(int i = 0; i < gene.length; i++){
                writer.println(gene[i]);
            }
            writer.close();
        } catch(Exception ex) { System.out.println(ex.getMessage());}
    }

    public void playAllParties(){
        ExecutorService service = Executors.newCachedThreadPool();
        for(NeyroParty party : parties){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    party.playGame();
                }
            });
        }
        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.DAYS);
        }catch(Exception ex) {}
    }

    public ArrayList<Double[]> cross(Double[] gene1, Double[] gene2){
        int gene1halfLength = gene1.length / (int)(1 + Math.random() * (gene1.length - 1));
        int gene2halfLength = gene1halfLength;
        ArrayList<Double> gen1half1 = new ArrayList<>();
        ArrayList<Double> gen1half2 = new ArrayList<>();
        ArrayList<Double> gen2half1 = new ArrayList<>();
        ArrayList<Double> gen2half2 = new ArrayList<>();
        Arrays.stream(gene1).limit(gene1halfLength).forEach(value -> gen1half1.add(value));
        Arrays.stream(gene1).skip(gene1halfLength).forEach(value -> gen1half2.add(value));
        Arrays.stream(gene2).limit(gene2halfLength).forEach(value -> gen2half1.add(value));
        Arrays.stream(gene2).skip(gene2halfLength).forEach(value -> gen2half2.add(value));
        ArrayList<Double[]> newGenes = new ArrayList<>();
        Double[] newGene1 = new Double[gen1half1.size() + gen2half2.size()];
        Stream.concat(gen1half1.stream(), gen2half2.stream()).collect(Collectors.toList()).toArray(newGene1);
        Double[] newGene2 = new Double[gen2half1.size() + gen1half2.size()];
        Stream.concat(gen2half1.stream(), gen1half2.stream()).collect(Collectors.toList()).toArray(newGene2);
        newGenes.add(newGene1);
        newGenes.add(newGene2);
        return newGenes;
    }

    public Double[] mutateGene(Double[] origin){
        for(int i = 0; i < origin.length; i++){
            if(Math.random() > 0.90){
                origin[i] = -0.1 + Math.random() * 0.2;
            }
        }
        return origin;
    }
}
