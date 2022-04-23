package com.example.testeav1.componentes;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.security.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Alvo extends Thread {
    private int identificacao;
    private int origemx;
    private int origemy = 0;
    private int destinox;
    private float destinoy = Dados.TAMANHO_MAX_TELA_Y;
    private int localizacaoAtualizada;
    private long timestamp;
    private int freqAtualizacaoPosicao = 30;
    private Boolean chegouDestino = false;
    private Boolean atingido = false;
    private Circle circuloAlvo;
    private Random rand = new Random();
    private long tempoFinalizacao;

    public Alvo(int identificacao){
        this.timestamp = System.currentTimeMillis();
        this.identificacao = identificacao;
        int x = rand.nextInt(2);
        if(x == 1){
            this.origemx = 0;
        }else{
            this.origemx = 400;
        }
        circuloAlvo = new Circle(origemx, origemy,Dados.TAMANHO_ALVO,Color.RED);
        desenharAlvo(localizacaoAtualizada);
        start();
    }

    public void desenharAlvo(int y){

        Platform.runLater(() ->{
            this.circuloAlvo.setTranslateY(y);
        });
    }

    @Override
    public void run() {
        super.run();
        //Atualização para AV2
        //Reconciliação de dados: Cálculo
        int cont = 0;
        int tempoInterferencia = 60;
        while(destinoy >= localizacaoAtualizada && !atingido) {
            try {
                    if (cont < 5 && tempoInterferencia <= 0) {
                        int aleatorio = rand.nextInt(10,20);
                        this.localizacaoAtualizada += aleatorio;
                        this.desenharAlvo(this.localizacaoAtualizada);
                        sleep(freqAtualizacaoPosicao);
                        cont++;
                        tempoInterferencia = 50;
                    }
                    tempoInterferencia = tempoInterferencia - rand.nextInt(1,5);

                this.localizacaoAtualizada += 1;
                this.desenharAlvo(this.localizacaoAtualizada);
                sleep(freqAtualizacaoPosicao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (atingido) {
            System.out.println("Alvo " +identificacao + " foi atingido!");
        } else {
            System.out.println("Alvo " +identificacao + " não foi atingido!");
            tempoFinalizacao = System.currentTimeMillis();
        }
    }

    public Circle getCirculoAlvo() {
        return circuloAlvo;
    }

    public int getIdentificacao() {
        return identificacao;
    }

    public int getOrigemx() {
        return origemx;
    }

    public int getDestinox() {
        return destinox;
    }

    public int getOrigemy() {
        return origemy;
    }

    public int getLocalizacaoAtualizada() {
        return localizacaoAtualizada;
    }

    public float getDestinoy() {
        return destinoy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getFreqAtualizacaoPosicao() {
        return freqAtualizacaoPosicao;
    }

    public void setFreqAtualizacaoPosicao(int freqAtualizacaoPosicao) {
        this.freqAtualizacaoPosicao = freqAtualizacaoPosicao;
    }

    public boolean getAtingido() {
       return this.atingido;
    }

    public void setAtingido() {
        this.atingido = true;
        this.tempoFinalizacao = System.currentTimeMillis();
    }

    public long getTempoFinalizacao() {
        return tempoFinalizacao;
    }
}
