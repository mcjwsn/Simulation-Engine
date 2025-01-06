package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Genes {
    public Genes(int n){
        // losowanie genow na poczatek
        List<Integer> genes = new ArrayList<>();
        for(int i = 0; i < n; i++){
            genes.add(randint(0,7));
        }
    }
    public static int randint(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
