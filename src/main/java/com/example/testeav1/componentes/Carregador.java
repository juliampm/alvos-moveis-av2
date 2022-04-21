package com.example.testeav1.componentes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Carregador {
    private Stack<Municao> municoes;

    public Carregador(){
        municoes = new Stack();
        municoes.push(new Municao());
        municoes.push(new Municao());
        municoes.push(new Municao());
    }

    public boolean temMunicao(){
        return !municoes.empty();
    }

    public void adicionarMunicao(){
        municoes.push(new Municao());
    }

    public void removerMunicao(){
        municoes.pop();
    }
}
