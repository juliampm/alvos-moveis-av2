package com.example.testeav1;

import com.example.testeav1.componentes.Alvo;

import java.util.ArrayList;
import java.util.Date;

public class Relatorio extends Thread{

        private ArrayList<Alvo> alvos;

        public Relatorio(ArrayList<Alvo> alvos){
                this.alvos = alvos;
                start();
        }

        @Override
        public void run() {
                super.run();
                try {
                        Thread.sleep(10000);
                        System.out.println("######### Relatório ########\n");
                        for(int i = 0; i < alvos.size(); i++){
                                var alvo = alvos.get(i);
                                System.out.println("--- Alvo " + alvo.getIdentificacao() + " ---");
                                System.out.println("\nTempo de início: " + new Date(alvo.getTimestamp()));
                                System.out.println("\nTempo de finalização: " + new Date(alvo.getTempoFinalizacao()));
                                System.out.println("\nTempo de trajetória: " + (alvo.getTempoFinalizacao() - alvo.getTimestamp()) + "ms");
                                System.out.println("\nFoi atingido: " + alvo.getAtingido());
                                System.out.println("-------------------------------------");
                        }
                        System.out.println("###################################\n");
                }  catch (InterruptedException ex) {
                        ex.printStackTrace();
                }
        }
}

