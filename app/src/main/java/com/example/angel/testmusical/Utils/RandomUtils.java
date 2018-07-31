package com.example.angel.testmusical.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static List<Integer> generateRandomSuffledIntegerList(int nele) {

        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < nele; i++) lista.add(i);

        Collections.shuffle(lista, new Random(System.nanoTime()));
        return lista;
    }

    public static List<Integer> generateRandomIntegerArray(int maxval, int casos) {
        Random rnd = new Random(System.nanoTime());

        List<Integer> lista = new ArrayList<>();

        for (int i = 0; i < casos; i++) {
            lista.add(rnd.nextInt(maxval));
        }

        return lista;

    }
}
