package com.example.testeav1.componentes;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tiro extends Thread{
    private String identificacao;
    private int municao;
    private int origemx = 200;
    private float origemy = Dados.TAMANHO_MAX_TELA_Y - Dados.ALTURA_LANCADOR;
    private double destinox;
    private double destinoy;
    private double localizacaoX = 200;
    private double localizacaoY = Dados.TAMANHO_MAX_TELA_Y - Dados.ALTURA_LANCADOR;
    private int timestamp;
    private int freqAtualizacao = 30;
    private boolean contatoAlvo = false;
    private int identificacaoAlvo;
    private Circle circuloTiro;
    private boolean sentido;

    public Tiro (double destinox, double destinoy, boolean sentido, int identificacaoAlvo, Color lancador){
        circuloTiro = new Circle(origemx,origemy,Dados.TAMANHO_TIRO, lancador);
        this.destinox = destinox;
        this.destinoy = destinoy;
        this.sentido = sentido;
        this.identificacaoAlvo = identificacaoAlvo;
        start();
    }

    public void desenharTiro(double x, double y){
        // Manter a animação mais flúida com a Thread do JavaFX
        Platform.runLater(() -> {
            this.circuloTiro.setCenterX(x);
            this.circuloTiro.setCenterY(y);
        });
    }

    @Override
    public void run() {
        super.run();
        while(true){
            long inicioTiro = System.currentTimeMillis();
            try {
                this.localizacaoY -= destinoy;
                if(sentido) {
                    this.localizacaoX -= destinox;
                } else {
                    this.localizacaoX += destinox;
                }
                if(this.contatoAlvo) {
                    System.out.println("O tiro direcionado ao alvo " + identificacaoAlvo + " acertou!");
                    this.interrupt();
                    break;
                }
                else if(this.localizacaoX >= 400 || this.localizacaoX <= 0) {
                    System.out.println("O tiro direcionado ao alvo " + identificacaoAlvo + " errou!");
                    this.interrupt();
                    break;
                }
                this.desenharTiro(localizacaoX,localizacaoY);
                sleep(freqAtualizacao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long finalTiro = System.currentTimeMillis();
            long totalTiro;
            totalTiro = finalTiro - inicioTiro;
            System.out.println("Tempo total tiro= " + totalTiro);

        }
    }

    public double getLocalizacaoX() {
        return localizacaoX;
    }

    public void setLocalizacaoX(double localizacaoX) {
        this.localizacaoX = localizacaoX;
    }

    public double getLocalizacaoY() {
        return localizacaoY;
    }

    public void setLocalizacaoY(double localizacaoY) {
        this.localizacaoY = localizacaoY;
    }

    public int getIdentificacaoAlvo() {
        return identificacaoAlvo;
    }

    public void setIdentificacaoAlvo(int identificacaoAlvo) {
        this.identificacaoAlvo = identificacaoAlvo;
    }

    public Circle getCirculoTiro() {
        return circuloTiro;
    }

    public void setContatoAlvo() {
        this.contatoAlvo = true;
    }
}
