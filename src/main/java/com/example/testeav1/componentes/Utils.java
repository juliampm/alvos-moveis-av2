package com.example.testeav1.componentes;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class Utils {
    public static double distanciaEuclidiana(double x1, double y1, int x2, int y2) {
        var valor = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);
        return Math.sqrt(valor);
    }

    public static Color pegarUmaCorAleatoria () {
        var cores = List.of(Color.GREEN, Color.CYAN, Color.PURPLE, Color.BLUE, Color.YELLOW);
        return cores.get(new Random().nextInt(cores.size()-1));
    }
}
