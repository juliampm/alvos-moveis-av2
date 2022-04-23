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
    private Circle circuloTiro;
    private boolean sentido;
    private Alvo alvo;
    private int F1_novo;
    private int F1_antigo;
    private int velocidadeAlvo;

    public Tiro (double destinox, double destinoy, boolean sentido, Alvo alvo, Color lancador){
        circuloTiro = new Circle(origemx,origemy,Dados.TAMANHO_TIRO, lancador);
        this.destinox = destinox;
        this.destinoy = destinoy;
        this.sentido = sentido;
        this.alvo = alvo;
        F1_antigo = alvo.getLocalizacaoAtualizada();
        velocidadeAlvo = 1;
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
            try {
                if(F1_novo != 0) {
                    F1_antigo = F1_novo;
                }
                F1_novo = alvo.getLocalizacaoAtualizada();
                int velocidade = 1;
                if(F1_novo != 0 && F1_antigo != 0){
                    velocidade = F1_novo - F1_antigo;
                    if(velocidade > 1) {
                        //velocidade += 1;
                        System.out.println("Tiro andou = " + velocidade);
                    }
                }
                this.localizacaoY -= destinoy*velocidade;
                if(sentido) {
                    this.localizacaoX -= destinox*velocidade;
                } else {
                    this.localizacaoX += destinox*velocidade;
                }
                if(this.contatoAlvo) {
                    System.out.println("O tiro direcionado ao alvo " + alvo.getIdentificacao() + " acertou!");
                    this.interrupt();
                    break;
                }
                else if(this.localizacaoX >= 400 || this.localizacaoX <= 0) {
                    System.out.println("O tiro direcionado ao alvo " + alvo.getIdentificacao() + " errou!");
                    this.interrupt();
                    break;
                }




                this.desenharTiro(localizacaoX,localizacaoY);
                sleep(freqAtualizacao);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    public Circle getCirculoTiro() {
        return circuloTiro;
    }

    public void setContatoAlvo() {
        this.contatoAlvo = true;
    }

    public Alvo getAlvo() {
        return alvo;
    }
}
