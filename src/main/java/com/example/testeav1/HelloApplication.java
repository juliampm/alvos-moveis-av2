package com.example.testeav1;

import com.example.testeav1.componentes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class HelloApplication extends Application {
    //Lista de alvos
    ArrayList<Alvo> alvos = new ArrayList();
    ArrayList<Alvo> alvosDisponiveis = new ArrayList<>();
    ArrayList<Lancador> lancadores = new ArrayList<>();
    Semaphore semaforoManipularAlvos = new Semaphore(1);


    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        //Criar a tela
        var root = new Pane();
        var scene = new Scene(root, Dados.TAMANHO_MAX_TELA_X,Dados.TAMANHO_MAX_TELA_Y, Color.WHITESMOKE);
        stage.setTitle("AV1");
        stage.setScene(scene);
        stage.show();

        //Função que lança alvos a cada determinado tempo
        Timeline novosAlvosTimer = new Timeline(
                new KeyFrame(Duration.millis(Dados.TEMPO_GERACAO_ALVOS),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            for(int i = 0;i< Dados.QUANTIDADE_ALVOS; i++) {
                                Alvo alvo = new Alvo(alvos.size());
                                alvosDisponiveis.add(alvo);
                                alvos.add(alvo);
                            }
                        }
                    }));
        //Funcionando para 5 alvos novos
        novosAlvosTimer.setCycleCount(2);
        novosAlvosTimer.play();


        //Atualizar a tela para tentar diminuir travamentos de renderização
        Timeline refresh = new Timeline(
                new KeyFrame(Duration.millis(100),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(Platform.isFxApplicationThread()) {
                                    root.getChildren().clear();
                                    root.getChildren().add(lancadores.get(0).getRetangulo());
                                    for (Alvo alvo : alvos) {
                                        if (alvo.isAlive()) {
                                            root.getChildren().add(alvo.getCirculoAlvo());
                                        }
                                    }
                                    for (Lancador lancador : lancadores) {
                                        for (Tiro tiro : lancador.getTiros()) {
                                            if (tiro.isAlive()) {
                                                root.getChildren().add(tiro.getCirculoTiro());
                                            }
                                        }
                                    }
                                }

                            }
                        }));
        refresh.setCycleCount(Timeline.INDEFINITE);
        refresh.play();

        //Função que verifica colisão entre tiros e alvos
        Timeline colisaoTimer = new Timeline(
                new KeyFrame(Duration.millis(30),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                for (Lancador lancador : lancadores) {
                                    for (Tiro tiro : lancador.getTiros()) {
                                        if (tiro.isAlive()) {
                                            var alvo = alvos.get(tiro.getAlvo().getIdentificacao());
                                            if (Utils.distanciaEuclidiana(tiro.getLocalizacaoX(), tiro.getLocalizacaoY(), alvo.getOrigemx(), alvo.getLocalizacaoAtualizada())
                                                    <= Dados.TAMANHO_TIRO + Dados.TAMANHO_ALVO - 1) {
                                                alvo.setAtingido();

                                                tiro.setContatoAlvo();
                                                lancador.adicionarMunicaoPorAcerto();
                                            }
                                        }
                                    }
                                }
                            }
                        }));
        colisaoTimer.setCycleCount(Timeline.INDEFINITE);
        colisaoTimer.play();

        //2 Alvos no início da aplicação
        Alvo alvo = new Alvo(alvos.size());
        alvosDisponiveis.add(alvo);
        alvos.add(alvo);
//        Alvo alvo2 = new Alvo(alvos.size());
//        alvosDisponiveis.add(alvo2);
//        alvos.add(alvo2);


        for(int i = 0;i< Dados.QUANTIDADE_LANCADORES; i++) {
            this.lancadores.add(new Lancador(this.lancadores.size(), alvosDisponiveis, semaforoManipularAlvos));
            root.getChildren().add(this.lancadores.get(lancadores.size()-1).getRetangulo());
        }

//        while(true){
//            if(alvos.size() == Dados.NUMERO_ALVOS && alvos.get(Dados.NUMERO_ALVOS - 1).getTempoFinalizacao() != 0){
//                System.out.println("###### Relatório #####\n\n");
//                for(int i = 0; i < alvos.size(); i++){
//                    System.out.println("--- Alvo: " + alvo.getIdentificacao() + " ---");
//                    System.out.println("\nTempo de início: " + alvo.getTimestamp());
//                    System.out.println("\nTempo de finalização: " + alvo.getTempoFinalizacao());
//                    System.out.println("\nTempo de trajetória: " + (alvo.getTempoFinalizacao() - alvo.getTimestamp()));
//                    System.out.println("\nFoi atingido: " + alvo.getAtingido());
//                    System.out.println("-------------------------------------");
//                }
//                System.out.println("#############################\n\n");
//
//                break;
//            }
//        }
    }



    public static void main(String[] args) {
        launch();
    }

}